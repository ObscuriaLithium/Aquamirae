package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeEntityTypes;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.common.animation.EntityAnimations;
import com.obscuria.core.common.animation.IAnimatedEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;

@IceMazeEntity
public final class Anglerfish extends Monster implements IAnimatedEntity {
	private static final String ANIMATION_BITE = "bite";
	private final EntityAnimations<Anglerfish> animations = EntityAnimations.create(this)
			.withAnimation(ANIMATION_BITE, 40);
	private int attackCooldown = 0;
	public float onGroundAnim = 0;
	public float onGroundAnimO = 0;

	public Anglerfish(EntityType<Anglerfish> type, Level world) {
		super(type, world);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.3F, true);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
		this.xpReward = 12;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new AnglerfishAttackGoal(this, 3f, false));
		this.goalSelector.addGoal(2, new AnglerfishCircleAroundGoal(this));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D, 80));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, false, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WaterBoundPathNavigation(this, level);
	}

	@Override
	public void tick() {
		super.tick();
		this.updateOnGroundAnimation();
		this.handleAttackState();
	}

	@Override
	public void aiStep() {
		this.attackCooldown--;
		if (!this.isInWater() && this.onGround() && this.verticalCollision) {
			this.setDeltaMovement(this.getDeltaMovement().add(
					this.random.triangle(0, 0.2f), 0.4F,
					this.random.triangle(0, 0.2f)));
			this.setOnGround(false);
			this.hasImpulse = true;
			this.playSound(SoundEvents.GUARDIAN_FLOP, this.getSoundVolume(), this.getVoicePitch());
		}
		super.aiStep();
	}

	@Override
	public EntityAnimations<? extends Entity> getAnimations() {
		return animations;
	}

	@Override
	public float getWalkAnimationPower() {
		return 2f;
	}

	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity entity) {
		return this.getAttackBoundingBox().inflate(3f).intersects(entity.getBoundingBox());
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		final boolean hurt = super.doHurtTarget(entity);
		if (hurt && entity instanceof LivingEntity living) {
			final var effect = this.getRandom().nextInt(100);
			if (effect <= 20) {
				living.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
			} else if (effect <= 40) {
				living.addEffect(new MobEffectInstance(AquamiraeMobEffects.HYDROPHOBIA.get(), 200, 0));
			}
		}
		return hurt;
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
	public SoundEvent getHurtSound(DamageSource source) {
		return AquamiraeSounds.ENTITY_DEEP_HURT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_DEEP_DEATH.get();
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.GUARDIAN_FLOP, 0.15f, 1);
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get()) return false;
		return super.canDrownInFluidType(type);
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader world) {
		return world.isUnobstructed(this);
	}

	@Override
	public boolean isPushedByFluid(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get()) return false;
		return super.isPushedByFluid(type);
	}

	private void updateOnGroundAnimation() {
		this.onGroundAnimO = this.onGroundAnim;
		if (!this.isUnderWater() && this.isInWater())
			this.addDeltaMovement(new Vec3(0, -0.003F, 0));
		if (this.isInWater()) {
			this.onGroundAnim += (0f - this.onGroundAnim) * 0.2f;
		} else {
			this.onGroundAnim += (1f - this.onGroundAnim) * 0.2f;
		}
	}

	private void handleAttackState() {
		if (this.isUnderWater() && this.animations.isPlaying(ANIMATION_BITE))
			this.setDeltaMovement(this.getDeltaMovement().scale(0.75f));
		this.animations.playSound(ANIMATION_BITE, 6, AquamiraeSounds.ENTITY_EEL_BITE.get(), 2f, 1f);
		if (this.getTarget() == null) return;
		if (this.attackCooldown <= 0) this.lookControl.setLookAt(this.getTarget().getEyePosition());
		this.getAnimations().doInTick(ANIMATION_BITE, 17, () -> this.dashToTarget(this.getTarget()));
		this.getAnimations().doInTick(ANIMATION_BITE, 21, () -> this.attackTarget(this.getTarget()));
	}

	private void dashToTarget(LivingEntity entity) {
		this.addDeltaMovement(this.position().vectorTo(entity.getEyePosition()).scale(0.33f));
	}

	private void attackTarget(LivingEntity entity) {
		if (entity.position().distanceTo(this.position()) <= 3f) {
			this.doHurtTarget(entity);
			if (entity.isInWater()) {
				this.attackCooldown = this.random.nextInt(100, 300);
				this.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100, 0));
			}
		}
	}

	@SuppressWarnings("all")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
										MobSpawnType type, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		Aquamirae.setAttribute(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.anglerfishSwimSpeed.get());
		Aquamirae.setAttribute(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.anglerfishMaxHealth.get());
		Aquamirae.setAttribute(this, Attributes.ARMOR, AquamiraeConfig.Common.anglerfishArmor.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.anglerfishAttackDamage.get());
		Aquamirae.setAttribute(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.anglerfishFollowRange.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.anglerfishAttackKnockback.get());
		Aquamirae.setAttribute(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.anglerfishKnockbackResistance.get());
		if (level instanceof ServerLevel server && type == MobSpawnType.NATURAL && random.nextInt(1, 200) == 1) {
			final var mother = new MazeMother(AquamiraeEntityTypes.MAZE_MOTHER.get(), server);
			ForgeEventFactory.onFinalizeSpawn(mother, server, difficulty, type, null, null);
			level.addFreshEntity(mother);
			if (mother.isAddedToWorld())
				mother.moveTo(position());
		}
		return super.finalizeSpawn(level, difficulty, type, data, tag);
	}

	public static SpawnPlacements.SpawnPredicate<Anglerfish> getSpawnRules() {
		return (entityType, level, spawnType, pos, random) -> level.getFluidState(pos).is(FluidTags.WATER)
				&& level.getDifficulty() != Difficulty.PEACEFUL;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.DEFAULT_ANGLERFISH_SWIM_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_ANGLERFISH_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_ANGLERFISH_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_ANGLERFISH_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_ANGLERFISH_FOLLOW_RANGE)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_ANGLERFISH_KNOCKBACK_RESISTANCE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_ANGLERFISH_ATTACK_KNOCKBACK);
	}

	private static class AnglerfishAttackGoal extends MeleeAttackGoal {
		private final Anglerfish anglerfish;

		@Override
		public boolean canUse() {
			return this.anglerfish.attackCooldown <= 0 && this.anglerfish.isInWater() && super.canUse();
		}

		@Override
		public boolean canContinueToUse() {
			return this.anglerfish.attackCooldown <= 0 && this.anglerfish.isInWater() && super.canContinueToUse();
		}

		public AnglerfishAttackGoal(Anglerfish anglerfish, double speed, boolean alwaysFollow) {
			super(anglerfish, speed, alwaysFollow);
			this.anglerfish = anglerfish;
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity entity) {
			if (this.anglerfish.getAnimations().isPlaying(ANIMATION_BITE)) return;
			if (this.canPerformAttack(entity)) {
				this.resetAttackCooldown();
				this.anglerfish.getAnimations().play(ANIMATION_BITE);
			}
		}
	}

	private static class AnglerfishCircleAroundGoal extends Goal {
		private final Anglerfish anglerfish;

		public AnglerfishCircleAroundGoal(Anglerfish anglerfish) {
			this.anglerfish = anglerfish;
		}

		@Override
		public boolean canUse() {
			return this.anglerfish.attackCooldown > 0
					&& this.anglerfish.getTarget() != null;
		}

		@Override
		public boolean canContinueToUse() {
			return this.anglerfish.attackCooldown > 0
					&& !this.anglerfish.getNavigation().isDone()
					&& this.anglerfish.tickCount % 10 != 0;
		}

		@Override
		public void start() {
			final var target = this.anglerfish.getTarget();
			if (target == null) return;
			final var timer = this.anglerfish.tickCount * 0.025f;
			this.anglerfish.getNavigation().moveTo(
					target.getX() + Math.cos(timer) * 12,
					target.getY() + 1,
					target.getZ() + Math.sin(timer) * 12,
					1f);
		}

		@Override
		public void stop() {
			this.anglerfish.getNavigation().stop();
		}
	}
}
