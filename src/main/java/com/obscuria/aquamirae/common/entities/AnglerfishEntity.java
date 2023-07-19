package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@ShipGraveyardEntity
public class AnglerfishEntity extends HostileEntity implements IAnimated {
	public final Animation ATTACK = new Animation(1);
	public int attackTick = 0;
	public float groundMod = 0;
	public float groundModLerp = 0;

	public AnglerfishEntity(EntityType<AnglerfishEntity> type, World world) {
		super(type, world);
		experiencePoints = 12;
		setPathfindingPenalty(PathNodeType.WATER, 0);
		moveControl = new AquaticMoveControl(this, 85, 10, 0.02F, 0.1F, true);
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(2, new MeleeAttackGoal(this, 3f, false) {
			@Override
			protected double getSquaredMaxAttackDistance(LivingEntity entity) {
				return 0;
			}
		});
		targetSelector.add(1, new RevengeGoal(this));
		targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		targetSelector.add(3, new ActiveTargetGoal<>(this, AnimalEntity.class, true));
		targetSelector.add(3, new ActiveTargetGoal<>(this, IllagerEntity.class, true));
		targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
	}

	@Override
	public void travel(Vec3d movementInput) {
		if (canMoveVoluntarily() && isTouchingWater()) {
			updateVelocity(getMovementSpeed(), movementInput);
			move(MovementType.SELF, getVelocity());
			setVelocity(getVelocity().multiply(0.9));
			if (getTarget() == null) setVelocity(getVelocity().add(0.0, -0.005, 0.0));
			if (!isSubmergedInWater()) setVelocity(getVelocity().add(0, -0.05, 0));

			try {
				final EntityNavigation navigation = getNavigation();
				final Path path = navigation.getCurrentPath();
				if (path != null) {
					final Vec3d pos = path.getCurrentNodePos().toCenterPos();
					if (getPos().squaredDistanceTo(pos) <= 2.5 && !path.isFinished())
						path.next();
				}
				if (navigation.getTargetPos() != null && navigation.getTargetPos().toCenterPos().squaredDistanceTo(getPos()) <= 2.5)
					navigation.stop();
			} catch (Exception ignored) {}
		} else super.travel(movementInput);
	}

	@Override
	public Optional<Animation> getAnimation(byte id) {
		return id == 1 ? Optional.of(ATTACK) : Optional.empty();
	}

	@Override
	public boolean tryAttack(Entity target) {
		final boolean hurt = super.tryAttack(target);
		if (hurt && target instanceof LivingEntity living)
			living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0));
		return hurt;
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new SwimNavigation(this, world);
	}

	@Override
	public void tick() {
		AnimationHelper.handle(ATTACK);
		ATTACK.sound(this, 6, () -> AquamiraeSounds.ENTITY_EEL_BITE, SoundCategory.HOSTILE, 2f, 1f);
		groundModLerp = groundMod;
		if (isTouchingWater()) groundMod += (0f - groundMod) * 0.2f;
		else groundMod += (1f - groundMod) * 0.2f;
		if (getTarget() == null && age % 200 == 0) {
			final Vec3d target = LookTargetUtil.find(this, 32, 5);
			if (target != null) getNavigation().startMovingTo(target.x, target.y, target.z, 1.2);
		}

		if (!isTouchingWater() && isOnGround() && verticalCollision) {
			setVelocity(getVelocity().add(((random.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4, ((random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
			setOnGround(false);
			velocityDirty = true;
			playSound(SoundEvents.ENTITY_GUARDIAN_FLOP, getSoundVolume(), getSoundPitch());
		}

		if (ATTACK.isPlaying()) {
			attackTick = 10;
			if (getTarget() != null) lookControl.lookAt(getTarget());
			if (ATTACK.getTick() < 18) setVelocity(getVelocity().multiply(0.9F));
			if (ATTACK.getTick() == 18 && getTarget() != null) setVelocity(getVelocity().add(getPos().relativize(getTarget().getPos()).multiply(0.4F)));
			if (ATTACK.getTick() == 21 && getTarget() != null && getTarget().getPos().squaredDistanceTo(getPos()) <= 2.5D) tryAttack(getTarget());
		} else if (attackTick <= 0 && getTarget() != null && getTarget().getPos().squaredDistanceTo(getPos()) <= 5D) {
			ATTACK.play(this, 40);
		} else attackTick--;
		super.tick();
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.AQUATIC;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_DEEP_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return AquamiraeSounds.ENTITY_DEEP_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_DEEP_DEATH;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_GUARDIAN_FLOP, 0.15f, 1);
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		if (spawnReason == SpawnReason.NATURAL && random.nextBetween(1, 200) == 1) {
			MazeMotherEntity mazeMother = new MazeMotherEntity(AquamiraeEntities.MAZE_MOTHER, (World) world);
			mazeMother.setPosition(getPos());
			mazeMother.initialize(world, difficulty, spawnReason, null, null);
			world.spawnEntity(mazeMother);
		}
		//Aquamirae.setAttribute(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.anglerfishSwimSpeed.get());
		//Aquamirae.setAttribute(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.anglerfishMaxHealth.get());
		//Aquamirae.setAttribute(this, Attributes.ARMOR, AquamiraeConfig.Common.anglerfishArmor.get());
		//Aquamirae.setAttribute(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.anglerfishAttackDamage.get());
		//Aquamirae.setAttribute(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.anglerfishFollowRange.get());
		//Aquamirae.setAttribute(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.anglerfishAttackKnockback.get());
		//Aquamirae.setAttribute(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.anglerfishKnockbackResistance.get());
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	@Override
	public boolean canBreatheInWater() {
		return true;
	}

	@Override
	public boolean canSpawn(WorldView world) {
		return world.doesNotIntersectEntities(this);
	}

	@Override
	public boolean isPushedByFluids() {
		return false;
	}

	public static SpawnRestriction.SpawnPredicate<AnglerfishEntity> getSpawnRules() {
		return (entityType, level, spawnType, pos, random) -> level.getFluidState(pos).isIn(FluidTags.WATER) &&
				level.getDifficulty() != Difficulty.PEACEFUL;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, AquamiraeConfig.DEFAULT_ANGLERFISH_MAX_HEALTH)
				.add(EntityAttributes.GENERIC_ARMOR, AquamiraeConfig.DEFAULT_ANGLERFISH_ARMOR)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_ANGLERFISH_ATTACK_DAMAGE)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, AquamiraeConfig.DEFAULT_ANGLERFISH_FOLLOW_RANGE)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_ANGLERFISH_KNOCKBACK_RESISTANCE)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_ANGLERFISH_ATTACK_KNOCKBACK);
	}
}
