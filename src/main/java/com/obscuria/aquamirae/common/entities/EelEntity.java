
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import com.obscuria.obscureapi.api.utils.EntityUtils;
import com.obscuria.obscureapi.common.entities.TextureFX;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@ShipGraveyardEntity
public class EelEntity extends HostileEntity implements IAnimated {
	private static final TrackedData<Integer> MOVE_COOLDOWN = DataTracker.registerData(EelEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Float> POS_X = DataTracker.registerData(EelEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> POS_Y = DataTracker.registerData(EelEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> POS_Z = DataTracker.registerData(EelEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Integer> HIT_SERIES = DataTracker.registerData(EelEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public final Animation RARE_IDLE = new Animation(1);
	public final Animation ATTACK = new Animation(2);
	public final Animation ROAR = new Animation(3);
	public final Animation DEATH = new Animation(4);
	public final Animation MOVE = new Animation(5);
	private int rareIdle;

	public EelEntity(EntityType<EelEntity> type, World world) {
		super(type, world);
		experiencePoints = 120;
		setPersistent();
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		targetSelector.add(1, new RevengeGoal(this));
		targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false, false));
		targetSelector.add(3, new ActiveTargetGoal<>(this, IllagerEntity.class, false, false));
		targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, false, false));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(MOVE_COOLDOWN, 200);
		dataTracker.startTracking(POS_X, (float) getX());
		dataTracker.startTracking(POS_Y, (float) getY());
		dataTracker.startTracking(POS_Z, (float) getZ());
		dataTracker.startTracking(HIT_SERIES, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		final NbtCompound data = new NbtCompound();
		data.putInt("MoveCooldown", dataTracker.get(MOVE_COOLDOWN));
		data.putInt("Hits", dataTracker.get(HIT_SERIES));
		data.putFloat("x", dataTracker.get(POS_X));
		data.putFloat("y", dataTracker.get(POS_Y));
		data.putFloat("z", dataTracker.get(POS_Z));
		nbt.put("EelData", data);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		final NbtCompound data = nbt.getCompound("EelData");
		if (data.isEmpty()) return;
		dataTracker.set(MOVE_COOLDOWN, data.getInt("MoveCooldown"));
		dataTracker.set(HIT_SERIES, data.getInt("Hits"));
		dataTracker.set(POS_X, data.getFloat("x"));
		dataTracker.set(POS_Y, data.getFloat("y"));
		dataTracker.set(POS_Z, data.getFloat("z"));
	}

	@Override
	public Box getVisibilityBoundingBox() {
		return super.getVisibilityBoundingBox().expand(2f);
	}

	@Override
	public Optional<Animation> getAnimation(byte id) {
		return switch (id) {
			case 1 -> Optional.of(RARE_IDLE);
			case 2 -> Optional.of(ATTACK);
			case 3 -> Optional.of(ROAR);
			case 4 -> Optional.of(DEATH);
			case 5 -> Optional.of(MOVE);
			default -> Optional.empty();
		};
	}

	@Override
	public void baseTick() {
		AnimationHelper.handleDeath(this, DEATH, 60);
		AnimationHelper.handle(RARE_IDLE, ATTACK, ROAR, DEATH, MOVE);
		ATTACK.sound(this, 1, () -> AquamiraeSounds.ENTITY_EEL_BITE, SoundCategory.HOSTILE, 2, 1);
		ROAR.sound(this, 1, () -> AquamiraeSounds.ENTITY_EEL_ROAR, SoundCategory.HOSTILE, 2, 0.8f + random.nextFloat() * 0.2f);
		this.rareIdle--;
		final int moveCooldown = dataTracker.get(MOVE_COOLDOWN);
		final int hitSeries = dataTracker.get(HIT_SERIES);
		dataTracker.set(MOVE_COOLDOWN, moveCooldown - 1);

		if (this.rareIdle <= 0 && !this.RARE_IDLE.isPlaying() && random.nextInt(200) == 1) {
			this.RARE_IDLE.play(this, random.nextBetween(150, 300));
			this.rareIdle = 400;
		}

		if (this.MOVE.isPlaying()) {
			if (this.MOVE.getTick() == 25) teleport(dataTracker.get(POS_X), dataTracker.get(POS_Y), dataTracker.get(POS_Z));
			for (int i = 0; i < 5; i++) spawnBlockParticles();

		} else {
			if (getTarget() != null) {
				final LivingEntity target = getTarget();
				final double distance = squaredDistanceTo(target);
				lookControl.lookAt(target);
				//ATTACK
				if (dataTracker.get(HIT_SERIES) <= 0 && distance <= 30 && random.nextInt(60) == 1) {
					dataTracker.set(HIT_SERIES, random.nextBetween(1, 3));
				} else if (dataTracker.get(HIT_SERIES) > 0 && !ATTACK.isPlaying() && !ROAR.isPlaying()) {
					dataTracker.set(HIT_SERIES, hitSeries - 1);
					ATTACK.play(this, 30);
				}
				if (!ATTACK.isPlaying() && !ROAR.isPlaying() && random.nextInt(100) == 1)
					ROAR.play(this, 100);
				if (distance <= 24 && ATTACK.getTick() == 14) {
					final float hp = target.getHealth();
					tryAttack(target);
					if (target.getHealth() < hp) this.heal((hp - target.getHealth()) * 2);
				}
				//ROAR
				if (!target.getWorld().isClient() && ROAR.isPlaying() && ROAR.getTick() > 12 && ROAR.getTick() < 52) {
					if (age % 5 == 0) {
						final Vec3d pos = EntityUtils.getRelativePos(this, 4f, 0f, 0f);
						TextureFX.Builder.create(20).owner(this).texture(Aquamirae.MODID, "roar")
								.pos(pos.add(0, getEyeY() - pos.y - 0.2, 0))
								.relativeRot(this, true, true)
								.xRot(90f, 0f, 0f)
								.zRot((float) (360 * Math.random()), 0, 0)
								.moveForward(0f, 3f, 0.03f)
								.scale(0.4f, 0.1f, 0.01f)
								.alpha(0.25f, -0.0025f, -0.0025f)
								.build(getWorld());
					}
					if (distance < 150) {
						final Vec3d roarVec = getPos().relativize(target.getPos()).multiply(0.07).multiply(Math.max(0, 1.0 - distance / 150.0));
						target.setVelocity(target.getVelocity().add(roarVec.x, 0, roarVec.z));
						if (target instanceof PlayerEntity player) player.velocityModified = true;
					}
					if (target instanceof MobEntity mob) mob.setTarget(this);
				}
			}
			//MOVE
			if (!ATTACK.isPlaying() && !ROAR.isPlaying() && moveCooldown < 0) {
				final Vec3d pos = getTarget() != null && distanceTo(getTarget()) > 40 ? nearTargetMovePosition() : randomMovePosition();
				if (pos.y == 0) dataTracker.set(MOVE_COOLDOWN, 2);
				else {
					dataTracker.set(POS_X, (float) pos.x);
					dataTracker.set(POS_Y, (float) pos.y);
					dataTracker.set(POS_Z, (float) pos.z);
					dataTracker.set(MOVE_COOLDOWN, 400);
					MOVE.play(this, 100);
				}
			}
		}
		super.baseTick();
	}

	public void spawnBlockParticles() {
		final BlockState state = getWorld().getBlockState(getBlockPos().down());
		if (!state.isAir())
			getWorld().addParticle(
					new BlockStateParticleEffect(ParticleTypes.BLOCK, state),
					getX() + (random.nextDouble()-0.5D) * 1.6D,
					getY() + 0.03D,
					getZ() + (random.nextDouble()-0.5D) * 1.6D,
					0, 1.5D, 0);
	}

	public Vec3d randomMovePosition() {
		final int x = getBlockX() + random.nextBetween(5, 16) * (Math.random() > 0.5 ? -1 : 1);
		final int z = getBlockZ() + random.nextBetween(5, 16) * (Math.random() > 0.5 ? -1 : 1);
		for (int i = -8; i < 8; i++)
			if (checkGround(x, getBlockY() - i, z)
					&& checkSpace(x, getBlockY() - i + 1, z)
					&& checkSpace(x, getBlockY() - i + 2, z)
					&& checkSpace(x, getBlockY() - i + 3, z)
					&& checkSpace(x, getBlockY() - i + 4, z))
				return new Vec3d(x + 0.5, getBlockY() - i + 1.0, z + 0.5);
		return Vec3d.ZERO;
	}

	public Vec3d nearTargetMovePosition() {
		if (getTarget() == null) return Vec3d.ZERO;
		final int x = getTarget().getBlockX() + random.nextBetween(-3, 3);
		final int z = getTarget().getBlockZ() + random.nextBetween(-3, 3);
		for (int i = -4; i < 4; i++)
			if (checkGround(x, getBlockY() - i, z)
					&& checkSpace(x, getBlockY() - i + 1, z)
					&& checkSpace(x, getBlockY() - i + 2, z)
					&& checkSpace(x, getBlockY() - i + 3, z)
					&& checkSpace(x, getBlockY() - i + 4, z))
				return new Vec3d(x + 0.5, getBlockY() - i + 1.0, z + 0.5);
		return Vec3d.ZERO;
	}

	public boolean checkGround(int x, int y, int z) {
		return getWorld().getBlockState(new BlockPos(x, y, z)).isIn(Aquamirae.EEL_MOVE)
				&& getWorld().getBlockState(new BlockPos(x + 1, y, z)).isIn(Aquamirae.EEL_MOVE)
				&& getWorld().getBlockState(new BlockPos(x - 1, y, z)).isIn(Aquamirae.EEL_MOVE)
				&& getWorld().getBlockState(new BlockPos(x, y, z + 1)).isIn(Aquamirae.EEL_MOVE)
				&& getWorld().getBlockState(new BlockPos(x, y, z - 1)).isIn(Aquamirae.EEL_MOVE);
	}

	public boolean checkSpace(int x, int y, int z) {
		return getWorld().isAir(new BlockPos(x, y, z)) && getWorld().isAir(new BlockPos(x + 1, y, z))
				&& getWorld().isAir(new BlockPos(x - 1, y, z)) && getWorld().isAir(new BlockPos(x, y, z + 1))
				&& getWorld().isAir(new BlockPos(x, y, z - 1)) && getWorld().isAir(new BlockPos(x + 1, y, z + 1))
				&& getWorld().isAir(new BlockPos(x - 1, y, z - 1)) && getWorld().isAir(new BlockPos(x + 1, y, z - 1))
				&& getWorld().isAir(new BlockPos(x - 1, y, z + 1));
	}

	@SuppressWarnings("all")
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		//Aquamirae.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.eelMaxHealth.get());
		//Aquamirae.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.eelArmor.get());
		//Aquamirae.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.eelAttackDamage.get());
		//Aquamirae.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.eelFollowRange.get());
		//Aquamirae.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.eelAttackKnockback.get());
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	@Override
	public boolean isPushedByFluids() {
		return false;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.AQUATIC;
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return false;
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
	public boolean damage(DamageSource source, float amount) {
		if (this.MOVE.isPlaying()) return false;
		if (source.getSource() instanceof ArrowEntity) return false;
		if (source.isOf(DamageTypes.FALL) || source.isOf(DamageTypes.CACTUS) || source.isOf(DamageTypes.DROWN)) return false;
		return super.damage(source, amount);
	}

	@Override
	public boolean canUsePortals() {
		return false;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, AquamiraeConfig.DEFAULT_EEL_MAX_HEALTH)
				.add(EntityAttributes.GENERIC_ARMOR, AquamiraeConfig.DEFAULT_EEL_ARMOR)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_EEL_ATTACK_DAMAGE)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, AquamiraeConfig.DEFAULT_EEL_FOLLOW_RANGE)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_EEL_ATTACK_KNOCKBACK)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100.0);
	}
}
