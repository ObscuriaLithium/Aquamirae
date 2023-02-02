package com.obscuria.aquamirae.world.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.api.ShipGraveyardEntity;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.animations.AnimationProvider;
import com.obscuria.obscureapi.api.animations.IAnimatedEntity;
import com.obscuria.obscureapi.world.ai.MeleeAttackGoal;
import com.obscuria.obscureapi.world.ai.attack.SimpleMeleeAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
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
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

@ShipGraveyardEntity
public class Anglerfish extends Monster implements IAnimatedEntity {
	private final AnimationProvider ANIMATIONS = new AnimationProvider(this);
	private int attackTick = 0;
	public Anglerfish(PlayMessages.SpawnEntity packet, Level world) {
		this(AquamiraeEntities.ANGLERFISH.get(), world);
	}

	public Anglerfish(EntityType<Anglerfish> type, Level world) {
		super(type, world);
		xpReward = 12;
		this.setPathfindingMalus(BlockPathTypes.WATER, 0);
		this.moveControl = new MoveControl(this) {
			@Override public void tick() {
				if (Anglerfish.this.isInWater())
					Anglerfish.this.setDeltaMovement(Anglerfish.this.getDeltaMovement().add(0, 0.005, 0));
				if (this.operation == MoveControl.Operation.MOVE_TO && !Anglerfish.this.getNavigation().isDone()) {
					double dx = this.wantedX - Anglerfish.this.getX();
					double dy = this.wantedY - Anglerfish.this.getY();
					double dz = this.wantedZ - Anglerfish.this.getZ();
					float f = (float) (Mth.atan2(dz, dx) * (180 / Math.PI)) - 90;
					float f1 = (float) (this.speedModifier * Objects.requireNonNull(Anglerfish.this.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
					Anglerfish.this.setYRot(this.rotlerp(Anglerfish.this.getYRot(), f, 10));
					Anglerfish.this.yBodyRot = Anglerfish.this.getYRot();
					Anglerfish.this.yHeadRot = Anglerfish.this.getYRot();
					if (Anglerfish.this.isInWater()) {
						Anglerfish.this.setSpeed((float) Objects.requireNonNull(Anglerfish.this.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
						float f2 = -(float) (Mth.atan2(dy, (float) Math.sqrt(dx * dx + dz * dz)) * (180 / Math.PI));
						f2 = Mth.clamp(Mth.wrapDegrees(f2), -85, 85);
						Anglerfish.this.setXRot(this.rotlerp(Anglerfish.this.getXRot(), f2, 5));
						float f3 = Mth.cos(Anglerfish.this.getXRot() * (float) (Math.PI / 180.0));
						Anglerfish.this.setZza(f3 * f1);
						Anglerfish.this.setYya((float) (f1 * dy));
					} else {
						Anglerfish.this.setSpeed(f1 * 0.05F);
					}
				} else {
					Anglerfish.this.setSpeed(0);
					Anglerfish.this.setYya(0);
					Anglerfish.this.setZza(0);
				}
			}
		};
	}

	@Override public AnimationProvider getAnimationProvider() {
		return this.ANIMATIONS;
	}

	@Override
	public boolean doHurtTarget(@NotNull Entity entity) {
		final boolean hurt = super.doHurtTarget(entity);
		if (hurt && entity instanceof LivingEntity living) living.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
		return hurt;
	}

	@Override protected @NotNull PathNavigation createNavigation(@NotNull Level world) {
		return new WaterBoundPathNavigation(this, world);
	}

	@Override protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false,
				new SimpleMeleeAttack("attack", 30, 9, 10, 30, 4, 3.5)));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Animal.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
		this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1, 40) {
			@Override protected Vec3 getPosition() {
				return BehaviorUtils.getRandomSwimmablePos(this.mob, 32, 7);
			}
		});
	}

	@Override public @NotNull MobType getMobType() {
		return MobType.WATER;
	}

	@Override public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
	}

	@Override public SoundEvent getHurtSound(@NotNull DamageSource source) {
		return AquamiraeSounds.ENTITY_DEEP_HURT.get();
	}

	@Override public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_DEEP_DEATH.get();
	}

	@Override public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
		this.playSound(SoundEvents.GUARDIAN_FLOP, 0.15f, 1);
	}

	@Override public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
		if (world instanceof ServerLevel server && spawnType == MobSpawnType.NATURAL && this.random.nextInt(1, 200) == 1) {
			MazeMother mazeMother = new MazeMother(AquamiraeEntities.MAZE_MOTHER.get(), server);
			mazeMother.moveTo(this.position());
			mazeMother.finalizeSpawn(server, difficulty, spawnType, null, null);
			world.addFreshEntity(mazeMother);
		}
		AquamiraeMod.loadFromConfig(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.anglerfishSwimSpeed.get());
		AquamiraeMod.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.anglerfishMaxHealth.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.anglerfishArmor.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.anglerfishAttackDamage.get());
		AquamiraeMod.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.anglerfishFollowRange.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.anglerfishAttackKnockback.get());
		AquamiraeMod.loadFromConfig(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.anglerfishKnockbackResistance.get());
		return super.finalizeSpawn(world, difficulty, spawnType, spawnGroupData, tag);
	}

	@Override public void baseTick() {
		ANIMATIONS.playSound("attack", 24, "aquamirae:entity.eel.bite", SoundSource.HOSTILE, 1F, 1F);
		if (this.isInWater()) this.setDeltaMovement(this.getDeltaMovement().add(0, -0.001F, 0));
		else {
			if (ANIMATIONS.isPlaying("onGround")) {
				if (ANIMATIONS.getTick("onGround") <= 2) ANIMATIONS.play("onGround", 20);
			} else {
				if (this.tickCount > 1 && this.attackTick <= 0) ANIMATIONS.play("onGround", 20);
			}
		}
		if (ANIMATIONS.isPlaying("attack")) {
			attackTick = 10;
			if (this.getTarget() != null) this.lookControl.setLookAt(this.getTarget());
			if (ANIMATIONS.getTick("attack") > 12) this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
			if (ANIMATIONS.getTick("attack") == 12 && this.getTarget() != null) this.setDeltaMovement(this.getDeltaMovement()
					.add(this.getPosition(1F).vectorTo(this.getTarget().getPosition(1F)).scale(0.4F)));
		} else this.attackTick--;
		super.baseTick();
	}

	@Override public boolean canBreatheUnderwater() {
		return true;
	}

	@Override public boolean checkSpawnObstruction(LevelReader world) {
		return world.isUnobstructed(this);
	}

	@Override public boolean isPushedByFluid() {
		return false;
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.DEFAULT_ANGLERFISH_SWIM_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_ANGLERFISH_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_ANGLERFISH_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_ANGLERFISH_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_ANGLERFISH_FOLLOW_RANGE)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_ANGLERFISH_KNOCKBACK_RESISTANCE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_ANGLERFISH_ATTACK_KNOCKBACK);
	}
}
