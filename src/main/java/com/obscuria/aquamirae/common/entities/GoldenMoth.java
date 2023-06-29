
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class GoldenMoth extends PathfinderMob {
	public GoldenMoth(PlayMessages.SpawnEntity packet, Level world) {
		this(AquamiraeEntities.GOLDEN_MOTH.get(), world);
	}

	public GoldenMoth(EntityType<GoldenMoth> type, Level world) {
		super(type, world);
		xpReward = 10;
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override protected @NotNull PathNavigation createNavigation(@NotNull Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1, 20) {
			@Override
			protected Vec3 getPosition() {
				RandomSource random = GoldenMoth.this.getRandom();
				double dir_x = GoldenMoth.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = GoldenMoth.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = GoldenMoth.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}
		});
	}

	@Override public @NotNull MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_GOLDEN_MOTH_AMBIENT.get();
	}

	@Override public SoundEvent getHurtSound(@NotNull DamageSource source) {
		return SoundEvents.GENERIC_HURT;
	}

	@Override public SoundEvent getDeathSound() {
		return SoundEvents.GENERIC_DEATH;
	}

	@Override public boolean causeFallDamage(float l, float d, @NotNull DamageSource source) {
		return false;
	}

	@Override public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud) return false;
		if (source.is(DamageTypes.FALL)) return false;
		if (source.is(DamageTypes.CACTUS)) return false;
		if (source.is(DamageTypes.DROWN)) return false;
		if (source.is(DamageTypes.LIGHTNING_BOLT)) return false;
		if (this.level instanceof ServerLevel server) server.sendParticles(AquamiraeParticleTypes.SHINE.get(),
				this.getX(), this.getY(), this.getZ(), 6, 0.05, 0.05, 0.05, 0.8);
		return super.hurt(source, amount);
	}

	@Override public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
		super.mobInteract(player, hand);
		final ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() == Items.GLASS_BOTTLE) {
			stack.shrink(1);
			if (!this.level.isClientSide()) {
				this.level.playSound(null, this.blockPosition(),
						AquamiraeSounds.ENTITY_GOLDEN_MOTH_CATCH.get(), SoundSource.AMBIENT, 1, 1);
				ItemEntity item = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(),
						new ItemStack(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR.get()));
				item.setPickUpDelay(10);
				this.level.addFreshEntity(item);
				this.discard();
			}
		}
		return InteractionResult.sidedSuccess(this.level.isClientSide());
	}

	@Override public void baseTick() {
		this.getPersistentData().putDouble("shine", this.getPersistentData().getDouble("shine") + 1);
		if (this.getPersistentData().getDouble("shine") > 2) {
			this.getPersistentData().putDouble("shine", 0);
			if (this.level instanceof ServerLevel server)
				server.sendParticles(AquamiraeParticleTypes.SHINE.get(), this.getX(), this.getY(), this.getZ(),
						1, 0.1, 0.1, 0.1, 0.1);
		}
		if (this.isInWaterOrBubble()) this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05f, 0));
		super.baseTick();
	}

	@Override protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
	}

	@Override public void setNoGravity(boolean ignored) {
		super.setNoGravity(true);
	}

	@Override public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);
	}

	public static SpawnPlacements.SpawnPredicate<GoldenMoth> getSpawnRules() {
		return (entityType, level, spawnType, pos, random) -> level instanceof Level world && !world.isDay();
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.FLYING_SPEED, 0.4)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.MAX_HEALTH, 3);
	}
}
