package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.obscureapi.api.Icons;
import com.obscuria.obscureapi.api.utils.TextUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@ShipGraveyardEntity
public class MazeMotherEntity extends HostileEntity {

	public MazeMotherEntity(EntityType<MazeMotherEntity> type, World world) {
		super(type, world);
		experiencePoints = 6;
		setPathfindingPenalty(PathNodeType.WATER, 0);
		moveControl = new MazeMotherMoveControl(this);
		lookControl = new YawAdjustingLookControl(this, 10);
	}

	@Override
	public Box getVisibilityBoundingBox() {
	return super.getBoundingBox().expand(10f);
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return distanceSquared > 200;
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new SwimNavigation(this, world);
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(1, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getSquaredMaxAttackDistance(LivingEntity entity) {
				return 44.0;
			}
		});
		goalSelector.add(6, new SwimAroundGoal(this, 1, 40));
		targetSelector.add(2, new RevengeGoal(this));
		targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false, false));
		targetSelector.add(4, new ActiveTargetGoal<>(this, IllagerEntity.class, false, false));
		targetSelector.add(5, new ActiveTargetGoal<>(this, VillagerEntity.class, false, false));
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.AQUATIC;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_ELDER_GUARDIAN_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (source.isOf(DamageTypes.DROWN)) return false;
		return super.damage(source, amount);
	}

	@SuppressWarnings("all")
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		//Aquamirae.setAttribute(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.motherSwimSpeed.get());
		//Aquamirae.setAttribute(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.motherMaxHealth.get());
		//Aquamirae.setAttribute(this, Attributes.ARMOR, AquamiraeConfig.Common.motherArmor.get());
		//Aquamirae.setAttribute(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.motherAttackDamage.get());
		//Aquamirae.setAttribute(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.motherFollowRange.get());
		//Aquamirae.setAttribute(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.motherAttackKnockback.get());
		//Aquamirae.setAttribute(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.motherKnockbackResistance.get());
		getWorld().getEntitiesByClass(PlayerEntity.class, new Box(getPos(), getPos()).expand(100), e -> true).forEach(player ->
				player.sendMessage(TextUtils.component(Icons.INFO + Text.translatable("info.maze_mother_spawn").getString())));
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	@Override
	protected void updateDespawnCounter() {
		if (!getWorld().getEntitiesByClass(PlayerEntity.class, new Box(getPos(), getPos()).expand(128), e -> true).isEmpty()) setDespawnCounter(0);
	}

	@Override
	public void tick() {
		if (!isSubmergedInWater() && isTouchingWater()) setVelocity(getVelocity().add(0, -0.1, 0));
		if (!getWorld().isClient() && age % 10 == 0) {
			breakIce(-1);
			breakIce(0);
			breakIce(1);
			breakIce(2);
			breakIce(3);
			breakIce(4);
		}
		super.tick();
	}

	private void breakIce(int offset) {
		for (int ix = -8; ix <= 8; ix++)
			for (int iz = -8; iz <= 8; iz++) {
				final BlockPos pos = getBlockPos().add(ix, offset, iz);
				if ((getWorld().getBlockState(pos.up()).isAir() || getWorld().getBlockState(pos.up(2)).isAir())
						&& (getWorld().getBlockState(pos).isIn(Aquamirae.MAZE_MOTHER_DESTROY) || getWorld().getBlockState(pos).isIn(BlockTags.SNOW))) {
					if (random.nextBoolean())
						if (getWorld().getBlockState(pos).isOf(Blocks.ICE) || getWorld().getBlockState(pos).isOf(Blocks.FROSTED_ICE)) {
							getWorld().breakBlock(pos, true);
							getWorld().setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
						} else getWorld().breakBlock(pos, true);
				}
			}
	}

	@Override
	public boolean canBreatheInWater() {
		return true;
	}

	@Override
	public boolean canSpawn(WorldView world) {
		return world.isSpaceEmpty(this);
	}

	@Override
	public boolean isPushedByFluids() {
		return false;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 3)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, AquamiraeConfig.DEFAULT_MOTHER_MAX_HEALTH)
				.add(EntityAttributes.GENERIC_ARMOR, AquamiraeConfig.DEFAULT_MOTHER_ARMOR)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_MOTHER_ATTACK_DAMAGE)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, AquamiraeConfig.DEFAULT_MOTHER_FOLLOW_RANGE)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_MOTHER_KNOCKBACK_RESISTANCE)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_MOTHER_ATTACK_KNOCKBACK);
	}

	static class MazeMotherMoveControl extends MoveControl {
		private final MazeMotherEntity entity;

		public MazeMotherMoveControl(MazeMotherEntity entity) {
			super(entity);
			this.entity = entity;
		}

		public void tick() {
			if (state == State.MOVE_TO && !entity.getNavigation().isIdle()) {
				final Vec3d vec = new Vec3d(targetX - entity.getX(), targetY - entity.getY(), targetZ - entity.getZ());
				final float upward = (float) (entity.getTarget() != null
						? entity.getTarget().getY() - 4f - entity.getY()
						: vec.y);
				final float yaw = (float)(MathHelper.atan2(vec.z, vec.x) * 57.2957763671875) - 180.0F;
				entity.setYaw(this.wrapDegrees(entity.getYaw(), yaw, 2.0F));
				entity.bodyYaw = entity.getYaw();
				entity.setVelocity(entity.getRotationVector().multiply(0.25f));
				entity.setVelocity(entity.getVelocity().x, entity.isTouchingWater()
						? Math.max(Math.min(upward, 0.1f), -0.1f)
						: -0.1, entity.getVelocity().z);
			}
		}
	}
}
