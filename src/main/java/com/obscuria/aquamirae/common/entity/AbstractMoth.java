
package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.api.graphic.world.Trail3D;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@IceMazeEntity
public abstract class AbstractMoth extends PathfinderMob {
	public final Trail3D trail = createTrail();

	public AbstractMoth(EntityType<? extends AbstractMoth> type, Level world) {
		super(type, world);
		this.moveControl = new FlyingMoveControl(this, 10, true);
		this.setNoGravity(true);
		this.xpReward = 10;
	}

	protected abstract Trail3D createTrail();

	protected abstract void registerMothGoals();

	protected abstract ItemStack getJar();

	@Override
	protected void registerGoals() {
		super.registerGoals();
		registerMothGoals();
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	public void baseTick() {
		if (level().isClientSide) {
			trail.addPoint(position());
			trail.tick();
		} else if (isInWaterOrBubble()) {
			addDeltaMovement(new Vec3(0, 0.05f, 0));
		}
		super.baseTick();
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud) return false;
		if (source.is(DamageTypes.FALL)) return false;
		if (source.is(DamageTypes.CACTUS)) return false;
		if (source.is(DamageTypes.DROWN)) return false;
		if (source.is(DamageTypes.LIGHTNING_BOLT)) return false;
		if (this.level() instanceof ServerLevel server) server.sendParticles(AquamiraeParticles.SHINE.get(),
				this.getX(), this.getY(), this.getZ(), 6, 0.05, 0.05, 0.05, 0.8);
		return super.hurt(source, amount);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		super.mobInteract(player, hand);
		final var stack = player.getItemInHand(hand);
		if (stack.getItem() == Items.GLASS_BOTTLE) {
			stack.shrink(1);
			if (!level().isClientSide()) {
				level().playSound(null, this.blockPosition(),
						AquamiraeSounds.ENTITY_MOTH_CATCH.get(), SoundSource.AMBIENT,
						1, (float) random.triangle(1, 0.2));
				final var itemEntity = new ItemEntity(level(), getX(), getY(), getZ(), getJar());
				itemEntity.setPickUpDelay(10);
				level().addFreshEntity(itemEntity);
				discard();
			}
		}
		return InteractionResult.sidedSuccess(level().isClientSide());
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.AMETHYST_BLOCK_CHIME;
		//return AquamiraeSounds.ENTITY_MOTH_AMBIENT.get();
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.AMETHYST_BLOCK_HIT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.AMETHYST_BLOCK_RESONATE;
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {}

	@Override
	public AABB getBoundingBoxForCulling() {
		return super.getBoundingBoxForCulling()
				.inflate(trail.getMaxDistanceFrom(position()));
	}

	public static <T extends AbstractMoth> SpawnPlacements.SpawnPredicate<T> getSpawnRules() {
		return (entityType, level, spawnType, pos, random) -> level instanceof Level world && !world.isDay();
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.FLYING_SPEED, 0.4)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.MAX_HEALTH, 3);
	}

	protected static class MothStrollGoal extends RandomStrollGoal {
		public MothStrollGoal(AbstractMoth moth, double speed, int interval) {
			super(moth, speed, interval);
		}

		@Override
		protected Vec3 getPosition() {
			final var random = mob.getRandom();
			final var x = mob.getX() + ((random.nextFloat() * 2 - 1) * 16);
			final var y = mob.getY() + ((random.nextFloat() * 2 - 1) * 16);
			final var z = mob.getZ() + ((random.nextFloat() * 2 - 1) * 16);
			return new Vec3(x, y, z);
		}
	}
}
