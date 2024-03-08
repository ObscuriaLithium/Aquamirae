
package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@IceMazeEntity
public class MazeMother extends Monster {

	public MazeMother(EntityType<MazeMother> type, Level world) {
		super(type, world);
		xpReward = 6;
		this.setPathfindingMalus(BlockPathTypes.WATER, 0);
		this.moveControl = new MoveControl(this) {
			@Override
			public void tick() {
				if (MazeMother.this.isInWater())
					MazeMother.this.setDeltaMovement(MazeMother.this.getDeltaMovement().add(0, 0.005, 0));
				if (this.operation == MoveControl.Operation.MOVE_TO && !MazeMother.this.getNavigation().isDone()) {
					double dx = this.wantedX - MazeMother.this.getX();
					double dy = this.wantedY - MazeMother.this.getY();
					double dz = this.wantedZ - MazeMother.this.getZ();
					float f = (float) (Mth.atan2(dz, dx) * (180 / Math.PI)) - 90;
					float f1 = (float) (this.speedModifier * Objects.requireNonNull(MazeMother.this.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
					MazeMother.this.setYRot(this.rotlerp(MazeMother.this.getYRot(), f, 10));
					MazeMother.this.yBodyRot = MazeMother.this.getYRot();
					MazeMother.this.yHeadRot = MazeMother.this.getYRot();
					if (MazeMother.this.isInWater()) {
						MazeMother.this.setSpeed((float) Objects.requireNonNull(MazeMother.this.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
						float f2 = -(float) (Mth.atan2(dy, (float) Math.sqrt(dx * dx + dz * dz)) * (180 / Math.PI));
						f2 = Mth.clamp(Mth.wrapDegrees(f2), -85, 85);
						MazeMother.this.setXRot(this.rotlerp(MazeMother.this.getXRot(), f2, 5));
						float f3 = Mth.cos(MazeMother.this.getXRot() * (float) (Math.PI / 180.0));
						MazeMother.this.setZza(f3 * f1);
						MazeMother.this.setYya((float) (f1 * dy));
					} else {
						MazeMother.this.setSpeed(f1 * 0.05F);
					}
				} else {
					MazeMother.this.setSpeed(0);
					MazeMother.this.setYya(0);
					MazeMother.this.setZza(0);
				}
			}
		};
	}

	@Override
	public @NotNull AABB getBoundingBoxForCulling() {
		return super.getBoundingBoxForCulling().inflate(10F);
	}

	@Override
	public boolean removeWhenFarAway(double distance) {
		return distance > 200;
	}

	@Override
	protected @NotNull PathNavigation createNavigation(@NotNull Level world) {
		return new WaterBoundPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
		this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1, 40));
	}

	@Override
	public @NotNull MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ELDER_GUARDIAN_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(@NotNull DamageSource source) {
		return SoundEvents.ELDER_GUARDIAN_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ELDER_GUARDIAN_DEATH;
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN)) return false;
		return super.hurt(source, amount);
	}

	@Override
	@SuppressWarnings("deprecation")
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		Aquamirae.setAttribute(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.motherSwimSpeed.get());
		Aquamirae.setAttribute(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.motherMaxHealth.get());
		Aquamirae.setAttribute(this, Attributes.ARMOR, AquamiraeConfig.Common.motherArmor.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.motherAttackDamage.get());
		Aquamirae.setAttribute(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.motherFollowRange.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.motherAttackKnockback.get());
		Aquamirae.setAttribute(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.motherKnockbackResistance.get());
		final Vec3 center = this.position();
		List<Player> players = this.level().getEntitiesOfClass(Player.class, new AABB(center, center).inflate(100), e -> true).stream()
				.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).toList();
//		if (AquamiraeConfig.Common.notifications.get()) players.forEach(player -> player.sendSystemMessage(TextUtils.component(
//				Icons.INFO + TextUtils.translation("info.maze_mother_spawn"))));
		return livingdata;
	}

	@Override
	protected void updateNoActionTime() {
		final Vec3 center = this.position();
		List<Player> players = this.level().getEntitiesOfClass(Player.class, new AABB(center, center).inflate(128), e -> true).stream()
				.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).toList();
		if (!players.isEmpty()) this.setNoActionTime(0);
		super.updateNoActionTime();
	}

	@Override
	public void baseTick() {
		if (this.isInWater()) this.setDeltaMovement(this.getDeltaMovement().add(0, -0.001, 0));
		if (!this.level().isClientSide) {
			this.getPersistentData().putDouble("breakIce", (this.getPersistentData().getDouble("breakIce") + 1));
			if (this.getPersistentData().getDouble("breakIce") > 10) {
				this.getPersistentData().putDouble("breakIce", 0);
				this.breakIce(-1);
				this.breakIce(4);
				this.breakIce(5);
			}
		}
		super.baseTick();
	}

	private void breakIce(int offset) {
		for (int ix = -6; ix <= 6; ix++)
			for (int iz = -6; iz <= 6; iz++) {
				final BlockPos pos = new BlockPos(this.getBlockX() + ix, this.getBlockY() + offset, this.getBlockZ() + iz);
				if (this.level().getBlockState(pos.above()).isAir() && this.level().getBlockState(pos).is(Aquamirae.MAZE_MOTHER_CAN_DESTROY)) {
					if (this.random.nextBoolean())
						if (this.level().getBlockState(pos).is(Blocks.ICE) || this.level().getBlockState(pos).is(Blocks.FROSTED_ICE)) {
							this.level().destroyBlock(pos, true);
							this.level().setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
						} else this.level().destroyBlock(pos, true);
				}
			}
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get()) return false;
		return super.canDrownInFluidType(type);
	}

	@Override
	public boolean checkSpawnObstruction(@NotNull LevelReader world) {
		return world.isUnobstructed(this);
	}

	@Override
	public boolean isPushedByFluid(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get()) return false;
		return super.isPushedByFluid(type);
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.DEFAULT_MOTHER_SWIM_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_MOTHER_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_MOTHER_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_MOTHER_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_MOTHER_FOLLOW_RANGE)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_MOTHER_KNOCKBACK_RESISTANCE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_MOTHER_ATTACK_KNOCKBACK);
	}
}
