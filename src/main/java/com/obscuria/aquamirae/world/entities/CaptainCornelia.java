
package com.obscuria.aquamirae.world.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.AquamiraeAmbient;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.client.animations.HekateProvider;
import com.obscuria.obscureapi.client.animations.IHekateProvider;
import com.obscuria.obscureapi.utils.TextHelper;
import com.obscuria.obscureapi.world.entities.ChakraEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class CaptainCornelia extends Monster implements IShipGraveyardEntity, IHekateProvider {
	private final HekateProvider ANIMATIONS = new HekateProvider(this);
	private static final EntityDataAccessor<Integer> ATTACK = SynchedEntityData.defineId(CaptainCornelia.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> REGENERATION = SynchedEntityData.defineId(CaptainCornelia.class,
			EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = new ServerBossEvent(AquamiraeConfig.Common.stylizedBossbar.get() ? TextHelper.component("1")
			.withStyle(Style.EMPTY.withFont(new ResourceLocation(AquamiraeMod.MODID, "bossbars"))) : this.getDisplayName(),
			ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);

	public CaptainCornelia(PlayMessages.SpawnEntity packet, Level world) {
		this(AquamiraeEntities.CAPTAIN_CORNELIA.get(), world);
	}

	public CaptainCornelia(EntityType<CaptainCornelia> type, Level world) {
		super(type, world);
		xpReward = 100;
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, AquamiraeMod.winterEvent() ?
				AquamiraeItems.SWEET_LANCE.get().getDefaultInstance() :
				AquamiraeItems.CORAL_LANCE.get().getDefaultInstance());
	}

	@Override protected void registerGoals() {
		this.goalSelector.addGoal(4, new FloatGoal(this));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.2));
		super.registerGoals();
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(@NotNull LivingEntity entity) {
				return 0;
			}
		});
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
	}

	@Override protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK, 0);
		this.getEntityData().define(REGENERATION, AquamiraeConfig.Common.corneliaSkillRegeneration.get());
	}

	@Override public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		CompoundTag data = new CompoundTag();
		data.putInt("Attack", this.getEntityData().get(ATTACK));
		data.putInt("Regeneration", this.getEntityData().get(REGENERATION));
		tag.put("CorneliaData", data);
	}

	@Override public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		CompoundTag data = (CompoundTag) tag.get("CorneliaData");
		if (data == null) return;
		this.getEntityData().set(ATTACK, data.getInt("Attack"));
		this.getEntityData().set(REGENERATION, data.getInt("Regeneration"));
	}

	public HekateProvider getHekateProvider() {
		return this.ANIMATIONS;
	}

	@Override public void baseTick() {
		final int attack = this.getEntityData().get(ATTACK);
		this.getEntityData().set(ATTACK, attack - 1);

		if (this.getHealth() < this.getMaxHealth() * 0.3 && this.getEntityData().get(REGENERATION) > 0 && !this.hasEffect(MobEffects.LEVITATION)) this.rage();
		if (this.getHealth() <= this.getMaxHealth() * 0.1 && !this.hasEffect(MobEffects.WEAKNESS)) this.heal(0.5F);

		if (this.getTarget() != null) {
			final LivingEntity target = this.getTarget();
			final double distance = this.distanceToSqr(target);
			this.lookControl.setLookAt(target);
			this.yBodyRot = this.yHeadRot;
			if (!ANIMATIONS.isPlaying("attack")) {
				if ((distance < 3 || this.hasEffect(MobEffects.LEVITATION))) ANIMATIONS.play("attack", Math.random() < 0.5 ? 40 : 20);
				else if (distance < 25 && random.nextInt(100) == 1) ANIMATIONS.play("attack", Math.random() < 0.5 ? 40 : 20);
				else if (distance < 600 && random.nextInt(200) == 1) ANIMATIONS.play("attack", 60);
			}
			if (ANIMATIONS.getTick("attack") == 50) {
				target.setDeltaMovement(new Vec3(target.getX(), target.getY(), target.getZ())
						.vectorTo(new Vec3(this.getX(), this.getY() + 0.5F, this.getZ())).scale(0.25F));
				if (target instanceof Player player) player.hurtMarked = true;
			}
			if (ANIMATIONS.getTick("attack") == 8 || ANIMATIONS.getTick("attack") == 28)
				this.setDeltaMovement(new Vec3(this.getX(), this.getY(), this.getZ())
						.vectorTo(new Vec3(target.getX(), target.getY() + 0.1F, target.getZ())).scale(0.2F));
			if (ANIMATIONS.getTick("attack") == 26 && distance < 9) this.doHurtTarget(target);
			if (ANIMATIONS.getTick("attack") == 6 && distance < 12) this.doHurtTarget(target);

			if (this.isUnderWater() && !this.hasEffect(MobEffects.LEVITATION)) this.setDeltaMovement(new Vec3(this.getX(), this.getY(), this.getZ())
					.vectorTo(new Vec3(target.getX(), target.getY(), target.getZ())).scale(0.04F));
			else if (this.isInWater() && !this.hasEffect(MobEffects.LEVITATION)) this.setDeltaMovement(new Vec3(this.getX(), this.getY(), this.getZ())
					.vectorTo(new Vec3(target.getX(), target.getY(), target.getZ())).scale(0.04F).add(0F, 0.08F, 0F));
		}

		ANIMATIONS.playSound("attack", 40, "aquamirae:entity.captain_cornelia.attack_1", SoundSource.HOSTILE, 2F, 1F);
		ANIMATIONS.playSound("attack", 20, "aquamirae:entity.captain_cornelia.attack_2", SoundSource.HOSTILE, 2F, 1F);

		if (ANIMATIONS.getTick("attack") > 10)
			this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 3, false, false));

		if (this.hasEffect(MobEffects.LEVITATION) && !this.getLevel().isClientSide()) {
			final Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());
			List<LivingEntity> list = this.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(10), e -> true).stream()
					.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).toList();
			final MobEffectInstance LEVITATION = this.getEffect(MobEffects.LEVITATION);
			final int DURATION = LEVITATION == null ? 0 : LEVITATION.getDuration();
			list.forEach(entity -> {
				if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) return;
				if (entity.getMaxHealth() <= 100) {
					if (DURATION > 1) {
						final double radius = 5;
						final Vec3 orbit = new Vec3(center.x + Math.cos(entity.tickCount * -0.1F) * radius, center.y,
								center.z + Math.sin(entity.tickCount * -0.1F) * radius);
						entity.setDeltaMovement(new Vec3(entity.getX(), entity.getY(), entity.getZ()).vectorTo(orbit).scale(0.2F));
						if (entity instanceof Player _player) _player.hurtMarked = true;
					} else if (DURATION == 1) {
						entity.setDeltaMovement(new Vec3(this.getX(), this.getY(), this.getZ())
								.vectorTo(new Vec3(entity.getX(), entity.getY(), entity.getZ())).scale(0.2F));
						if (entity instanceof Player _player) _player.hurtMarked = true;
					}
				}
			});

			this.getPersistentData().putDouble("Par1", this.getPersistentData().getDouble("Par1") + 1);
			if (this.getLevel() instanceof ServerLevel server && this.getPersistentData().getDouble("Par1") > 1) {
				this.getPersistentData().putDouble("Par1", 0);
				server.sendParticles(AquamiraeParticleTypes.GHOST.get(), this.getX(), this.getY() - 0.2, this.getZ(), 1,
						0.3, 0.1, 0.3, 0.1);
			}
			if (this.isInWater()) this.setDeltaMovement(new Vec3(0F, 0.4F, 0F));
		}
		this.getPersistentData().putDouble("Par2", this.getPersistentData().getDouble("Par2") + 1);
		if (this.getLevel() instanceof ServerLevel server && this.getPersistentData().getDouble("Par2") > 9) {
			this.getPersistentData().putDouble("Par2", 0);
			server.sendParticles(AquamiraeParticleTypes.GHOST_SHINE.get(), this.getX(), this.getY() + 1.7, this.getZ(), 1,
					0.15, 0.1, 0.15, 0.1);
		}
		//
		final Vec3 center = this.getPosition(1F);
		List<Player> players = this.getLevel().getEntitiesOfClass(Player.class, new AABB(center, center).inflate(32), e -> true).stream()
				.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).toList();
		players.forEach(player -> { if (player.getLevel().isClientSide()) AquamiraeAmbient.playCorneliaMusic(player); });
		super.baseTick();
	}

	public void rage() {
		this.getEntityData().set(REGENERATION, this.getEntityData().get(REGENERATION) - 1);
		this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200, 0, false, false));
		this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 8, false, false));
		if (this.level instanceof ServerLevel serverLevel) {
			final BlockPos pos = new BlockPos(this.getX(), this.getY(), this.getZ());
			LightningBolt entityToSpawn = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
			entityToSpawn.moveTo(Vec3.atBottomCenterOf(pos));
			entityToSpawn.setVisualOnly(true);
			serverLevel.addFreshEntity(entityToSpawn);
			serverLevel.playSound(null, pos, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN.get(), SoundSource.HOSTILE, 3, 1);
			serverLevel.playSound(null, pos, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_RAGE.get(), SoundSource.HOSTILE, 4, 1);
			ChakraEntity.summonChakra(this, AquamiraeEntities.POISONED_CHAKRA.get(), this.level, null, 5, 0F, 600, 1000);
			ChakraEntity.summonChakra(this, AquamiraeEntities.POISONED_CHAKRA.get(), this.level, null, 5, 0.33F, 600, 1000);
			ChakraEntity.summonChakra(this, AquamiraeEntities.POISONED_CHAKRA.get(), this.level, null, 5, 0.66F, 600, 1000);
		}
	}

	@Override protected void dropEquipment() {
		if (Math.random() <= 0.2F) {
			final ItemEntity item = new ItemEntity(EntityType.ITEM, this.level);
			item.setItem(this.getMainHandItem());
			item.moveTo(this.position());
			this.level.addFreshEntity(item);
		}
	}

	@Override public @NotNull MobType getMobType() {
		return MobType.WATER;
	}

	@Override public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_AMBIENT.get();
	}

	@Override public SoundEvent getHurtSound(@NotNull DamageSource source) {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HURT.get();
	}

	@Override public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_DEATH.get();
	}

	@Override public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof AbstractArrow) return false;
		if (source == DamageSource.FALL) return false;
		if (source == DamageSource.CACTUS) return false;
		if (source == DamageSource.DROWN) return false;
		if (source == DamageSource.LIGHTNING_BOLT) return false;
		if (source.isExplosion()) return false;
		if (source.getMsgId().equals("trident")) return false;
		return super.hurt(source, amount);
	}

	@Override public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		if (this.getLevel() instanceof ServerLevel serverLevel) serverLevel.playSound(null, new BlockPos(this.getX(), this.getY(), this.getZ()),
					AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN.get(), SoundSource.HOSTILE, 3, 1);
		this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 120, 0, false, false));
		AquamiraeMod.loadFromConfig(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.corneliaMovementSpeed.get());
		AquamiraeMod.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.corneliaMaxHealth.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.corneliaArmor.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.corneliaAttackDamage.get());
		AquamiraeMod.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.corneliaFollowRange.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.corneliaAttackKnockback.get());
		AquamiraeMod.loadFromConfig(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.corneliaKnockbackResistance.get());
		return super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
	}

	@Override public boolean canChangeDimensions() {
		return false;
	}

	@Override public void startSeenByPlayer(@NotNull ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override public void stopSeenByPlayer(@NotNull ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override public void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, AquamiraeConfig.DEFAULT_CORNELIA_MOVEMENT_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_CORNELIA_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_CORNELIA_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_CORNELIA_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_CORNELIA_FOLLOW_RANGE)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_CORNELIA_KNOCKBACK_RESISTANCE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_CORNELIA_ATTACK_KNOCKBACK);
	}
}
