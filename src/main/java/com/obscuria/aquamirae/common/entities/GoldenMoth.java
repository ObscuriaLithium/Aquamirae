
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.api.ShipGraveyardEntity;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.FMLPlayMessages;

import java.util.Random;

@ShipGraveyardEntity
public class GoldenMoth extends CreatureEntity {
	public GoldenMoth(FMLPlayMessages.SpawnEntity packet, World world) {
		this(AquamiraeEntities.GOLDEN_MOTH.get(), world);
	}

	public GoldenMoth(EntityType<GoldenMoth> type, World world) {
		super(type, world);
		xpReward = 10;
		this.moveControl = new FlyingMovementController(this, 10, true);
	}

	@Override
	protected PathNavigator createNavigation(World world) {
		return new FlyingPathNavigator(this, world);
	}

	@Override protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new RandomWalkingGoal(this, 1, 20) {
			@Override
			protected Vector3d getPosition() {
				double dir_x = GoldenMoth.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = GoldenMoth.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = GoldenMoth.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vector3d(dir_x, dir_y, dir_z);
			}
		});
	}

	public static boolean checkGoldenMothSpawnRules(EntityType<? extends MobEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		return world instanceof World && !((World)world).isDay();
	}

	@Override public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEFINED;
	}

	@Override public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_GOLDEN_MOTH_AMBIENT.get();
	}

	@Override public SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.GENERIC_HURT;
	}

	@Override public SoundEvent getDeathSound() {
		return SoundEvents.GENERIC_DEATH;
	}

	@Override public boolean causeFallDamage(float l, float d) {
		return false;
	}

	@Override public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof PotionEntity || source.getDirectEntity() instanceof AreaEffectCloudEntity) return false;
		if (source == DamageSource.FALL) return false;
		if (source == DamageSource.CACTUS) return false;
		if (source == DamageSource.DROWN) return false;
		if (source == DamageSource.LIGHTNING_BOLT) return false;
		if (this.level instanceof ServerWorld) ((ServerWorld) this.level).sendParticles(AquamiraeParticles.SHINE.get(),
				this.getX(), this.getY(), this.getZ(), 6, 0.05, 0.05, 0.05, 0.8);
		return super.hurt(source, amount);
	}

	@Override public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		super.mobInteract(player, hand);
		final ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() == Items.GLASS_BOTTLE) {
			stack.shrink(1);
			if (!this.level.isClientSide()) {
				this.level.playSound(null, new BlockPos(this.getX(), this.getY(), this.getZ()),
						AquamiraeSounds.ENTITY_GOLDEN_MOTH_CATCH.get(), SoundCategory.PLAYERS, 1, 1);
				ItemEntity item = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(),
						new ItemStack(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR.get()));
				item.setPickUpDelay(10);
				this.level.addFreshEntity(item);
				this.remove();
			}
		}
		return ActionResultType.sidedSuccess(this.level.isClientSide());
	}

	@Override public void baseTick() {
		this.getPersistentData().putDouble("shine", this.getPersistentData().getDouble("shine") + 1);
		if (this.getPersistentData().getDouble("shine") > 2) {
			this.getPersistentData().putDouble("shine", 0);
			if (this.level instanceof ServerWorld)
				((ServerWorld) this.level).sendParticles(AquamiraeParticles.SHINE.get(), this.getX(), this.getY(), this.getZ(),
						1, 0.1, 0.1, 0.1, 0.1);
		}
		if (this.isInWaterOrBubble()) this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05f, 0));
		super.baseTick();
	}

	@Override protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override public void setNoGravity(boolean ignored) {
		super.setNoGravity(true);
	}

	@Override public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.FLYING_SPEED, 0.4)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.MAX_HEALTH, 3);
	}
}
