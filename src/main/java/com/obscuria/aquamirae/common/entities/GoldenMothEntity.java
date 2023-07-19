
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@ShipGraveyardEntity
public class GoldenMothEntity extends PathAwareEntity {

	public GoldenMothEntity(EntityType<GoldenMothEntity> type, World world) {
		super(type, world);
		experiencePoints = 10;
		moveControl = new FlightMoveControl(this, 10, true);
		setNoGravity(true);
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new BirdNavigation(this, world);
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(1, new WanderAroundGoal(this, 1, 20) {
			@Override
			protected Vec3d getWanderTarget() {
				final Random random = GoldenMothEntity.this.getRandom();
				final double x = GoldenMothEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				final double y = GoldenMothEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				final double z = GoldenMothEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3d(x, y, z);
			}
		});
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.DEFAULT;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_GOLDEN_MOTH_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_NOTE_BLOCK_BELL.value();
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_GENERIC_DEATH;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (source.getSource() instanceof PotionEntity || source.getSource() instanceof AreaEffectCloudEntity) return false;
		if (source.isOf(DamageTypes.FALL)) return false;
		if (source.isOf(DamageTypes.CACTUS)) return false;
		if (source.isOf(DamageTypes.DROWN)) return false;
		if (source.isOf(DamageTypes.LIGHTNING_BOLT)) return false;
		if (getWorld() instanceof ServerWorld server) server.spawnParticles(AquamiraeParticles.SHINE,
				getX(), getY(), getZ(), 6, 0.05, 0.05, 0.05, 0.8);
		return super.damage(source, amount);
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		super.interactMob(player, hand);
		final ItemStack stack = player.getStackInHand(hand);
		if (stack.getItem() == Items.GLASS_BOTTLE) {
			stack.decrement(1);
			if (!getWorld().isClient()) {
				getWorld().playSound(null, getBlockPos(), AquamiraeSounds.ENTITY_GOLDEN_MOTH_CATCH, SoundCategory.AMBIENT, 1, 1);
				final ItemEntity item = new ItemEntity(getWorld(), getX(), getY(), getZ(), new ItemStack(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR));
				item.setPickupDelay(10);
				getWorld().spawnEntity(item);
				discard();
			}
		}
		return ActionResult.success(getWorld().isClient());
	}

	@Override
	protected void mobTick() {
		if (age % 3 == 0)
			if (getWorld() instanceof ServerWorld server)
				server.spawnParticles(AquamiraeParticles.SHINE, getX(), getY(), getZ(), 1, 0.1, 0.1, 0.1, 0.1);
		if (isInsideWaterOrBubbleColumn())
			setVelocity(getVelocity().add(0, 0.05f, 0));
		super.mobTick();
	}

	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {}

	public static SpawnRestriction.SpawnPredicate<GoldenMothEntity> getSpawnRules() {
		return (entityType, worldAccess, spawnType, pos, random) -> worldAccess instanceof World world && !world.isDay();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 3);
	}
}
