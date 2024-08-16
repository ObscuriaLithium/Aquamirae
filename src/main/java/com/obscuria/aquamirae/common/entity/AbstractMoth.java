
package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.client.graphic.world.Trail3D;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
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

	public static <T extends AbstractMoth> SpawnPlacements.SpawnPredicate<T> getSpawnRules() {
		return (entityType, level, spawnType, pos, random) -> level instanceof Level world && !world.isDay();
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.FOLLOW_RANGE, 32)
				.add(Attributes.FLYING_SPEED, 0.6)
				.add(Attributes.MOVEMENT_SPEED, 0.1)
				.add(Attributes.MAX_HEALTH, 3);
	}

	protected abstract Trail3D createTrail();

	protected abstract ItemStack getJarItem();

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new FlyingPathNavigation(this, level);
	}

	@Override
	public void baseTick() {
		if (level().isClientSide) {
			trail.addPoint(position());
			trail.tick();
		} else if (isInWaterOrBubble()) {
			this.addDeltaMovement(new Vec3(0, 0.05f, 0));
		}
		super.baseTick();
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.FALL)) return false;
		if (source.is(DamageTypes.DROWN)) return false;
		return super.hurt(source, amount);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		super.mobInteract(player, hand);
		final var stack = player.getItemInHand(hand);
		if (stack.getItem() == Items.GLASS_BOTTLE) {
			stack.shrink(1);
			if (!level().isClientSide()) {
				this.level().playSound(null, this.blockPosition(),
						AquamiraeSounds.ENTITY_MOTH_CATCH.get(), SoundSource.AMBIENT,
						1, (float) random.triangle(1, 0.2));
				final var itemEntity = new ItemEntity(level(), getX(), getY(), getZ(), getJarItem());
				itemEntity.setPickUpDelay(10);
				this.level().addFreshEntity(itemEntity);
				this.discard();
			}
		}
		return InteractionResult.sidedSuccess(level().isClientSide());
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.AMETHYST_BLOCK_CHIME;
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
