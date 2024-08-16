
package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.common.animation.EntityAnimations;
import com.obscuria.core.common.animation.IAnimatedEntity;
import com.obscuria.core.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@ShipGraveyardEntity
public final class Maw extends Monster implements IAnimatedEntity {
	private static final EntityDataAccessor<ItemStack> MOUTH_ITEM = SynchedEntityData.defineId(Maw.class, EntityDataSerializers.ITEM_STACK);
	private static final String ANIMATION_SPECIAL_IDLE = "special_idle";
	private static final String BITE = "bite";
	private final EntityAnimations<Maw> animations = EntityAnimations.create(this)
			.withAnimation(ANIMATION_SPECIAL_IDLE, 60)
			.withAnimation(BITE, 20);

	public Maw(EntityType<Maw> type, Level world) {
		super(type, world);
		xpReward = 10;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, (float) 0.4));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.9));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(MOUTH_ITEM, ItemStack.EMPTY);
		super.defineSynchedData();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag data) {
		data.put("MouthItem", this.getMouthItem().save(new CompoundTag()));
		super.addAdditionalSaveData(data);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag data) {
		if (data.contains("MouthItem", Tag.TAG_COMPOUND))
			this.setMouthItem(ItemStack.of(data.getCompound("MouthItem")));
		super.readAdditionalSaveData(data);
	}

	@Override
	public void tick() {
		super.tick();
		this.randomIdleAnimation();
	}

	public void setMouthItem(ItemStack stack) {
		this.getEntityData().set(MOUTH_ITEM, stack);
	}

	public ItemStack getMouthItem() {
		return this.getEntityData().get(MOUTH_ITEM);
	}

	@Override
	public EntityAnimations<? extends Entity> getAnimations() {
		return animations;
	}

	@Override
	public float getWalkAnimationPower() {
		return 5f;
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.GUARDIAN_FLOP, 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return AquamiraeSounds.ENTITY_DEEP_HURT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_DEEP_DEATH.get();
	}

	@Override
	protected void dropEquipment() {
		if (!this.getMouthItem().isEmpty()) {
			WorldUtil.drop(this.level(), this.position(), this.getMouthItem());
			this.setMouthItem(ItemStack.EMPTY);
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN)) return false;
		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		this.animations.play(BITE);
		return super.doHurtTarget(entity);
	}

	@Override
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float f) {
		return new Vector3f(0.0F, dimensions.height - 0.2F, -0.6F);
	}

	private void randomIdleAnimation() {
		if (this.tickCount % 20 == 0 && this.random.nextFloat() <= 0.1f)
			this.animations.playIfStopped(ANIMATION_SPECIAL_IDLE);
	}

	private void generateMouthItem() {
		if (this.level().isClientSide) return;
		final MinecraftServer server = this.level().getServer();
		if (server != null && this.level() instanceof ServerLevel level) {
			final var context = new LootParams.Builder(level)
					.withParameter(LootContextParams.THIS_ENTITY, this)
					.withParameter(LootContextParams.ORIGIN, this.position())
					.create(LootContextParamSets.GIFT);
			final var items = server.getLootData()
					.getLootTable(Aquamirae.key("entities/maw_random_item"))
					.getRandomItems(context);
			if (items.isEmpty()) return;
			this.setMouthItem(items.get(0));
		}
	}

	private SpawnGroupData createSpawnGroupData(RandomSource random, int specialChance) {
		final var data = new MawEffectsGroupData();
		if (random.nextInt(100) <= specialChance)
			data.setRandomEffect(random);
		return data;
	}

	@Override
	@SuppressWarnings("all")
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
										MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		Aquamirae.setAttribute(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.mawSwimSpeed.get());
		Aquamirae.setAttribute(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.mawSpeed.get());
		Aquamirae.setAttribute(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.mawMaxHealth.get());
		Aquamirae.setAttribute(this, Attributes.ARMOR, AquamiraeConfig.Common.mawArmor.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.mawAttackDamage.get());
		Aquamirae.setAttribute(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.mawFollowRange.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.mawAttackKnockback.get());
		Aquamirae.setAttribute(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.mawKnockbackResistance.get());

		this.generateMouthItem();
		final var random = level.getRandom();
		final var specialChance = switch (difficulty.getDifficulty()) {
			case HARD -> 20;
			case NORMAL -> 10;
			default -> 2;
		};
		if (data == null) data = this.createSpawnGroupData(random, specialChance);
		if (data instanceof MawEffectsGroupData mawData && mawData.effect != null)
			this.addEffect(new MobEffectInstance(mawData.effect, -1, mawData.amplifier));

		if (random.nextInt(100) <= specialChance) {
			final var rider = EntityType.STRAY.create(this.level());
			if (rider != null) {
				rider.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
				ForgeEventFactory.onFinalizeSpawn(rider, level, difficulty, reason, null, null);
				rider.startRiding(this);
			}
		}

		return super.finalizeSpawn(level, difficulty, reason, data, tag);
	}

	@SuppressWarnings("deprecation")
	public static SpawnPlacements.SpawnPredicate<Maw> getSpawnRules() {
		return (entityType, level, spawnType, pos, random) -> pos.getY() < level.getSeaLevel() + 6 &&
				Monster.checkAnyLightMonsterSpawnRules(entityType, level, spawnType, pos, random);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.DEFAULT_MAW_SWIM_SPEED)
				.add(Attributes.MOVEMENT_SPEED, AquamiraeConfig.DEFAULT_MAW_MOVEMENT_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_MAW_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_MAW_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_MAW_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_MAW_FOLLOW_RANGE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_MAW_ATTACK_KNOCKBACK)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_MAW_KNOCKBACK_RESISTANCE);
	}

	public static class MawEffectsGroupData implements SpawnGroupData {
		public @Nullable MobEffect effect;
		public int amplifier;

		public void setRandomEffect(RandomSource random) {
			int i = random.nextInt(5);
			if (i <= 1) {
				this.effect = MobEffects.MOVEMENT_SPEED;
				this.amplifier = 2;
			} else if (i == 2) {
				this.effect = MobEffects.DAMAGE_BOOST;
				this.amplifier = 1;
			} else if (i == 3) {
				this.effect = MobEffects.REGENERATION;
				this.amplifier = 0;
			} else if (i == 4) {
				this.effect = MobEffects.INVISIBILITY;
				this.amplifier = 0;
			}
		}
	}
}
