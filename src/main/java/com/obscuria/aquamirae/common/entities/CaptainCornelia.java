
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.AquamiraeAmbient;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.client.animations.HekateProvider;
import com.obscuria.obscureapi.client.animations.IHekateProvider;
import com.obscuria.obscureapi.utils.TextHelper;
import com.obscuria.obscureapi.world.entities.ChakraEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.Style;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CaptainCornelia extends MonsterEntity implements IShipGraveyardEntity, IHekateProvider {
	private final HekateProvider ANIMATIONS = new HekateProvider(this);
	private static final DataParameter<Integer> ATTACK = EntityDataManager.defineId(CaptainCornelia.class, DataSerializers.INT);
	private static final DataParameter<Integer> REGENERATION = EntityDataManager.defineId(CaptainCornelia.class, DataSerializers.INT);

	private final ServerBossInfo bossInfo = new ServerBossInfo(AquamiraeConfig.Common.stylizedBossbar.get()
			? TextHelper.component("1").withStyle(Style.EMPTY.withFont(new ResourceLocation(AquamiraeMod.MODID, "bossbars")))
			: this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.NOTCHED_6);

	public CaptainCornelia(FMLPlayMessages.SpawnEntity packet, World world) {
		this(AquamiraeEntities.CAPTAIN_CORNELIA.get(), world);
	}

	public CaptainCornelia(EntityType<CaptainCornelia> type, World world) {
		super(type, world);
		xpReward = 100;
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlotType.MAINHAND, AquamiraeMod.winterEvent() ?
				AquamiraeItems.SWEET_LANCE.get().getDefaultInstance() :
				AquamiraeItems.CORAL_LANCE.get().getDefaultInstance());
	}

	@Override protected void registerGoals() {
		this.goalSelector.addGoal(4, new SwimGoal(this));
		this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.2));
		super.registerGoals();
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(@Nonnull LivingEntity entity) {
				return 0;
			}
		});
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false, false));
	}

	@Override protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK, 0);
		this.getEntityData().define(REGENERATION, AquamiraeConfig.Common.corneliaSkillRegeneration.get());
	}

	@Override public void addAdditionalSaveData(@Nonnull CompoundNBT tag) {
		super.addAdditionalSaveData(tag);
		CompoundNBT data = new CompoundNBT();
		data.putInt("Attack", this.getEntityData().get(ATTACK));
		data.putInt("Regeneration", this.getEntityData().get(REGENERATION));
		tag.put("CorneliaData", data);
	}

	@Override public void readAdditionalSaveData(@Nonnull CompoundNBT tag) {
		super.readAdditionalSaveData(tag);
		CompoundNBT data = tag.getCompound("CorneliaData");
		if (data.isEmpty()) return;
		this.getEntityData().set(ATTACK, data.getInt("Attack"));
		this.getEntityData().set(REGENERATION, data.getInt("Regeneration"));
	}

	public HekateProvider getHekateProvider() {
		return this.ANIMATIONS;
	}

	@Override public void baseTick() {
		final int attack = this.getEntityData().get(ATTACK);
		this.getEntityData().set(ATTACK, attack - 1);

		if (this.getHealth() < this.getMaxHealth() * 0.3 && this.getEntityData().get(REGENERATION) > 0 && !this.hasEffect(Effects.LEVITATION)) this.rage();
		if (this.getHealth() <= this.getMaxHealth() * 0.1 && !this.hasEffect(Effects.WEAKNESS)) this.heal(0.5F);

		if (this.getTarget() != null) {
			final LivingEntity target = this.getTarget();
			final double distance = this.distanceToSqr(target);
			this.lookControl.setLookAt(target.getEyePosition(1F));
			this.yBodyRot = this.yHeadRot;
			if (!ANIMATIONS.isPlaying("attack")) {
				if ((distance < 3 || this.hasEffect(Effects.LEVITATION))) ANIMATIONS.play("attack", Math.random() < 0.5 ? 40 : 20);
				else if (distance < 25 && random.nextInt(100) == 1) ANIMATIONS.play("attack", Math.random() < 0.5 ? 40 : 20);
				else if (distance < 600 && random.nextInt(200) == 1) ANIMATIONS.play("attack", 60);
			}
			if (ANIMATIONS.getTick("attack") == 50) {
				target.setDeltaMovement(new Vector3d(target.getX(), target.getY(), target.getZ())
						.vectorTo(new Vector3d(this.getX(), this.getY() + 0.5F, this.getZ())).scale(0.25F));
				if (target instanceof PlayerEntity) target.hurtMarked = true;
			}
			if (ANIMATIONS.getTick("attack") == 8 || ANIMATIONS.getTick("attack") == 28)
				this.setDeltaMovement(new Vector3d(this.getX(), this.getY(), this.getZ())
						.vectorTo(new Vector3d(target.getX(), target.getY() + 0.1F, target.getZ())).scale(0.2F));
			if (ANIMATIONS.getTick("attack") == 26 && distance < 9) this.doHurtTarget(target);
			if (ANIMATIONS.getTick("attack") == 6 && distance < 12) this.doHurtTarget(target);

			if (this.isUnderWater() && !this.hasEffect(Effects.LEVITATION)) this.setDeltaMovement(new Vector3d(this.getX(), this.getY(), this.getZ())
					.vectorTo(new Vector3d(target.getX(), target.getY(), target.getZ())).scale(0.04F));
			else if (this.isInWater() && !this.hasEffect(Effects.LEVITATION)) this.setDeltaMovement(new Vector3d(this.getX(), this.getY(), this.getZ())
					.vectorTo(new Vector3d(target.getX(), target.getY(), target.getZ())).scale(0.04F).add(0F, 0.08F, 0F));
		}

		ANIMATIONS.playSound("attack", 40, "aquamirae:entity.captain_cornelia.attack_1", SoundCategory.HOSTILE, 2F, 1F);
		ANIMATIONS.playSound("attack", 20, "aquamirae:entity.captain_cornelia.attack_2", SoundCategory.HOSTILE, 2F, 1F);

		if (ANIMATIONS.getTick("attack") > 10)
			this.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5, 3, false, false));

		if (this.hasEffect(Effects.LEVITATION) && !this.level.isClientSide()) {
			final Vector3d center = new Vector3d(this.getX(), this.getY(), this.getZ());
			List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(center, center).inflate(10), e -> true).stream()
					.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).collect(Collectors.toList());
			final EffectInstance LEVITATION = this.getEffect(Effects.LEVITATION);
			final int DURATION = LEVITATION == null ? 0 : LEVITATION.getDuration();
			list.forEach(entity -> {
				if (entity instanceof PlayerEntity && (((PlayerEntity)entity).isCreative() || entity.isSpectator())) return;
				if (entity.getMaxHealth() <= 100) {
					if (DURATION > 1) {
						final double radius = 5;
						final Vector3d orbit = new Vector3d(center.x + Math.cos(entity.tickCount * -0.1F) * radius, center.y,
								center.z + Math.sin(entity.tickCount * -0.1F) * radius);
						entity.setDeltaMovement(new Vector3d(entity.getX(), entity.getY(), entity.getZ()).vectorTo(orbit).scale(0.2F));
						if (entity instanceof PlayerEntity) entity.hurtMarked = true;
					} else if (DURATION == 1) {
						entity.setDeltaMovement(new Vector3d(this.getX(), this.getY(), this.getZ())
								.vectorTo(new Vector3d(entity.getX(), entity.getY(), entity.getZ())).scale(0.2F));
						if (entity instanceof PlayerEntity) entity.hurtMarked = true;
					}
				}
			});

			this.getPersistentData().putDouble("Par1", this.getPersistentData().getDouble("Par1") + 1);
			if (this.level instanceof ServerWorld && this.getPersistentData().getDouble("Par1") > 1) {
				this.getPersistentData().putDouble("Par1", 0);
				((ServerWorld) this.level).sendParticles(AquamiraeParticles.GHOST.get(), this.getX(), this.getY() - 0.2, this.getZ(), 1,
						0.3, 0.1, 0.3, 0.1);
			}
			if (this.isInWater()) this.setDeltaMovement(new Vector3d(0F, 0.4F, 0F));
		}
		this.getPersistentData().putDouble("Par2", this.getPersistentData().getDouble("Par2") + 1);
		if (this.level instanceof ServerWorld && this.getPersistentData().getDouble("Par2") > 9) {
			this.getPersistentData().putDouble("Par2", 0);
			((ServerWorld) this.level).sendParticles(AquamiraeParticles.GHOST_SHINE.get(), this.getX(), this.getY() + 1.7, this.getZ(), 1,
					0.15, 0.1, 0.15, 0.1);
		}
		//
		final Vector3d center = this.getPosition(1F);
		List<PlayerEntity> players = this.level.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(center, center).inflate(32), e -> true).stream()
				.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).collect(Collectors.toList());
		players.forEach(player -> { if (player.level.isClientSide()) AquamiraeAmbient.playCorneliaMusic(player); });
		super.baseTick();
	}

	public void rage() {
		this.getEntityData().set(REGENERATION, this.getEntityData().get(REGENERATION) - 1);
		this.addEffect(new EffectInstance(Effects.LEVITATION, 200, 0, false, false));
		this.addEffect(new EffectInstance(Effects.REGENERATION, 200, 8, false, false));
		if (this.level instanceof ServerWorld) {
			final ServerWorld serverLevel = (ServerWorld) this.level;
			final BlockPos pos = new BlockPos(this.getX(), this.getY(), this.getZ());
			LightningBoltEntity entityToSpawn = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, serverLevel);
			entityToSpawn.moveTo(Vector3d.atBottomCenterOf(pos));
			entityToSpawn.setVisualOnly(true);
			serverLevel.addFreshEntity(entityToSpawn);
			serverLevel.playSound(null, pos, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN.get(), SoundCategory.HOSTILE, 3, 1);
			serverLevel.playSound(null, pos, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_RAGE.get(), SoundCategory.HOSTILE, 4, 1);
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

	@Override @Nonnull public CreatureAttribute getMobType() {
		return CreatureAttribute.WATER;
	}

	@Override public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_AMBIENT.get();
	}

	@Override public SoundEvent getHurtSound(@Nonnull DamageSource source) {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HURT.get();
	}

	@Override public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_DEATH.get();
	}

	@Override public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof AbstractArrowEntity) return false;
		if (source == DamageSource.FALL) return false;
		if (source == DamageSource.CACTUS) return false;
		if (source == DamageSource.DROWN) return false;
		if (source == DamageSource.LIGHTNING_BOLT) return false;
		if (source.isExplosion()) return false;
		if (source.getMsgId().equals("trident")) return false;
		return super.hurt(source, amount);
	}

	@Override public ILivingEntityData finalizeSpawn(@Nonnull IServerWorld world, @Nonnull DifficultyInstance difficulty, @Nonnull SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT tag) {
		if (this.level instanceof ServerWorld) this.level.playSound(null, new BlockPos(this.getX(), this.getY(), this.getZ()),
					AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN.get(), SoundCategory.HOSTILE, 3, 1);
		this.addEffect(new EffectInstance(Effects.LEVITATION, 120, 0, false, false));
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

	@Override public void startSeenByPlayer(@Nonnull ServerPlayerEntity player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override public void stopSeenByPlayer(@Nonnull ServerPlayerEntity player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override public void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, AquamiraeConfig.DEFAULT_CORNELIA_MOVEMENT_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_CORNELIA_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_CORNELIA_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_CORNELIA_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_CORNELIA_FOLLOW_RANGE)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_CORNELIA_KNOCKBACK_RESISTANCE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_CORNELIA_ATTACK_KNOCKBACK);
	}
}
