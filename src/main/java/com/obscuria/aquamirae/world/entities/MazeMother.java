
package com.obscuria.aquamirae.world.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.utils.EventHelper;
import com.obscuria.obscureapi.utils.TextHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MazeMother extends MonsterEntity implements IShipGraveyardEntity {
	public MazeMother(FMLPlayMessages.SpawnEntity packet, World world) {
		this(AquamiraeEntities.MAZE_MOTHER.get(), world);
	}

	public MazeMother(EntityType<MazeMother> type, World world) {
		super(type, world);
		xpReward = 6;
		this.setPathfindingMalus(PathNodeType.WATER, 0);
		this.moveControl = new MovementController(this) {
			@Override
			public void tick() {
				if (MazeMother.this.isInWater())
					MazeMother.this.setDeltaMovement(MazeMother.this.getDeltaMovement().add(0, 0.005, 0));
				if (this.operation == MovementController.Action.MOVE_TO && !MazeMother.this.getNavigation().isDone()) {
					double dx = this.wantedX - MazeMother.this.getX();
					double dy = this.wantedY - MazeMother.this.getY();
					double dz = this.wantedZ - MazeMother.this.getZ();
					float f = (float) (MathHelper.atan2(dz, dx) * (180 / Math.PI)) - 90;
					float f1 = (float) (this.speedModifier * Objects.requireNonNull(MazeMother.this.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
					MazeMother.this.yRot = (this.rotlerp(MazeMother.this.yRot, f, 10));
					MazeMother.this.yBodyRot = MazeMother.this.yRot;
					MazeMother.this.yHeadRot = MazeMother.this.yRot;
					if (MazeMother.this.isInWater()) {
						MazeMother.this.setSpeed((float) Objects.requireNonNull(MazeMother.this.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
						float f2 = -(float) (MathHelper.atan2(dy, (float) Math.sqrt(dx * dx + dz * dz)) * (180 / Math.PI));
						f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85, 85);
						MazeMother.this.xRot = (this.rotlerp(MazeMother.this.xRot, f2, 5));
						float f3 = MathHelper.cos(MazeMother.this.xRot * (float) (Math.PI / 180.0));
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

	@Override public AxisAlignedBB getBoundingBoxForCulling() {
		return super.getBoundingBoxForCulling().inflate(10F);
	}

	@Override public boolean removeWhenFarAway(double distance) {
		return distance > 200;
	}

	@Override protected PathNavigator createNavigation(World world) {
		return new SwimmerPathNavigator(this, world);
	}

	@Override protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			@Override protected double getAttackReachSqr(LivingEntity entity) {
				return 44.0;
			}
		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, false, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false, false));
		this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1, 40));
	}

	@Override public CreatureAttribute getMobType() {
		return CreatureAttribute.WATER;
	}

	@Override public double getPassengersRidingOffset() {
		return super.getPassengersRidingOffset() + 0.8;
	}

	@Override public SoundEvent getAmbientSound() {
		return SoundEvents.ELDER_GUARDIAN_AMBIENT;
	}

	@Override public SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ELDER_GUARDIAN_HURT;
	}

	@Override public SoundEvent getDeathSound() {
		return SoundEvents.ELDER_GUARDIAN_DEATH;
	}

	@Override public boolean hurt(DamageSource source, float amount) {
		if (source == DamageSource.DROWN) return false;
		return super.hurt(source, amount);
	}

	@Override public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData livingdata, @Nullable CompoundNBT tag) {
		AquamiraeMod.loadFromConfig(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.motherSwimSpeed.get());
		AquamiraeMod.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.motherMaxHealth.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.motherArmor.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.motherAttackDamage.get());
		AquamiraeMod.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.motherFollowRange.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.motherAttackKnockback.get());
		AquamiraeMod.loadFromConfig(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.motherKnockbackResistance.get());
		final Vector3d center = this.getPosition(1F);
		List<PlayerEntity> players = this.level.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(center, center).inflate(100), e -> true).stream()
				.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).collect(Collectors.toList());
		if (AquamiraeConfig.Common.notifications.get()) players.forEach(player -> EventHelper.sendMessage(player,
				TextHelper.translation("icon.info") + TextHelper.translation("info.maze_mother_spawn")));
		return super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
	}

	@Override protected void updateNoActionTime() {
		final Vector3d center = this.getPosition(1F);
		List<PlayerEntity> players = this.level.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(center, center).inflate(128), e -> true).stream()
				.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).collect(Collectors.toList());
		if (!players.isEmpty()) this.setNoActionTime(0);
		super.updateNoActionTime();
	}

	@Override public void baseTick() {
		if (this.isInWater()) this.setDeltaMovement(this.getDeltaMovement().add(0, -0.001, 0));
		if (!this.level.isClientSide) {
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
				final BlockPos pos = new BlockPos(this.getX() + ix, this.getY() + offset, this.getZ() + iz);
				if (this.level.getBlockState(pos.above()).getMaterial() == Material.AIR && this.level.getBlockState(pos).is(AquamiraeMod.MAZE_MOTHER_DESTROY)) {
					if (this.random.nextBoolean())
						if (this.level.getBlockState(pos).is(Blocks.ICE) || this.level.getBlockState(pos).is(Blocks.FROSTED_ICE)) {
							this.level.destroyBlock(pos, true);
							this.level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
						} else this.level.destroyBlock(pos, true);
				}
			}
	}

	@Override public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean checkSpawnObstruction(IWorldReader world) {
		return world.isUnobstructed(this);
	}

	@Override public boolean isPushedByFluid() {
		return false;
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
				.add(ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.DEFAULT_MOTHER_SWIM_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_MOTHER_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_MOTHER_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_MOTHER_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_MOTHER_FOLLOW_RANGE)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_MOTHER_KNOCKBACK_RESISTANCE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_MOTHER_ATTACK_KNOCKBACK);
	}
}
