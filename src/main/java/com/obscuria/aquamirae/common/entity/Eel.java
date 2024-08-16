
package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.common.AquamiraeTags;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.common.animation.EntityAnimations;
import com.obscuria.core.common.animation.IAnimatedEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@ShipGraveyardEntity
public class Eel extends Monster implements IAnimatedEntity {
	private static final EntityDataAccessor<Integer> MOVE_COOLDOWN = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> POS_X = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> POS_Y = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> POS_Z = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> HIT_SERIES = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.INT);
	private final EntityAnimations<Eel> animations = EntityAnimations.create(this);
	public final String RARE_IDLE = "rare_idle";
	public final String ATTACK = "attack";
	public final String ROAR = "roar";
	public final String DEATH = "death";
	public final String MOVE = "move";
	private int rareIdle;

	public Eel(EntityType<Eel> type, Level world) {
		super(type, world);
		xpReward = 120;
		setPersistenceRequired();
	}

	@Override
	public AABB getBoundingBoxForCulling() {
		return super.getBoundingBoxForCulling().inflate(2F);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(MOVE_COOLDOWN, 200);
		this.getEntityData().define(POS_X, (float) this.getX());
		this.getEntityData().define(POS_Y, (float) this.getY());
		this.getEntityData().define(POS_Z, (float) this.getZ());
		this.getEntityData().define(HIT_SERIES, 0);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		CompoundTag data = new CompoundTag();
		data.putInt("MoveCooldown", this.getEntityData().get(MOVE_COOLDOWN));
		data.putInt("Hits", this.getEntityData().get(HIT_SERIES));
		data.putFloat("x", this.getEntityData().get(POS_X));
		data.putFloat("y", this.getEntityData().get(POS_Y));
		data.putFloat("z", this.getEntityData().get(POS_Z));
		tag.put("EelData", data);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		CompoundTag data = (CompoundTag) tag.get("EelData");
		if (data == null) return;
		this.getEntityData().set(MOVE_COOLDOWN, data.getInt("MoveCooldown"));
		this.getEntityData().set(HIT_SERIES, data.getInt("Hits"));
		this.getEntityData().set(POS_X, data.getFloat("x"));
		this.getEntityData().set(POS_Y, data.getFloat("y"));
		this.getEntityData().set(POS_Z, data.getFloat("z"));
	}

	@Override
	public EntityAnimations<? extends Entity> getAnimations() {
		return animations;
	}

	@Override
	public void baseTick() {
//		AnimationHelper.handleDeath(this, DEATH, 60);
//		AnimationHelper.handle(RARE_IDLE, ATTACK, ROAR, DEATH, MOVE);
//		ATTACK.sound(this, 1, AquamiraeSounds.ENTITY_EEL_BITE, SoundSource.HOSTILE, 2, 1);
//		ROAR.sound(this, 1, AquamiraeSounds.ENTITY_EEL_ROAR, SoundSource.HOSTILE, 2, 0.8f + random.nextFloat() * 0.2f);
		this.rareIdle--;
		final int moveCooldown = this.getEntityData().get(MOVE_COOLDOWN);
		final int hitSeries = this.getEntityData().get(HIT_SERIES);
		this.getEntityData().set(MOVE_COOLDOWN, moveCooldown - 1);

//		if (this.rareIdle <= 0 && !this.RARE_IDLE.isPlaying() && random.nextInt(200) == 1) {
//			this.RARE_IDLE.play(this, random.nextInt(150, 300));
//			this.rareIdle = 400;
//		}

//		if (this.MOVE.isPlaying()) {
//			if (this.MOVE.getTick() == 25) this.teleportTo(this.getEntityData().get(POS_X), this.getEntityData().get(POS_Y), this.getEntityData().get(POS_Z));
//			for (int i = 0; i < 5; i++) this.spawnBlockParticles();
//
//		} else {
//			if (this.getTarget() != null) {
//				final LivingEntity target = this.getTarget();
//				final double distance = this.distanceToSqr(target);
//				this.lookControl.setLookAt(target);
//				//ATTACK
//				if (this.getEntityData().get(HIT_SERIES) <= 0 && distance <= 30 && random.nextInt(60) == 1) {
//					this.getEntityData().set(HIT_SERIES, random.nextInt(1, 3));
//				} else if (this.getEntityData().get(HIT_SERIES) > 0 && !this.ATTACK.isPlaying() && !this.ROAR.isPlaying()) {
//					this.getEntityData().set(HIT_SERIES, hitSeries - 1);
//					this.ATTACK.play(this, 30);
//				}
//				if (!this.ATTACK.isPlaying() && !this.ROAR.isPlaying() && random.nextInt(100) == 1)
//					this.ROAR.play(this, 100);
//				if (distance <= 24 && this.ATTACK.getTick() == 14) {
//					final float hp = target.getHealth();
//					this.doHurtTarget(target);
//					if (target.getHealth() < hp) this.heal((hp - target.getHealth()) * 2);
//				}
//				//ROAR
//				if (!target.level().isClientSide() && this.ROAR.isPlaying() && this.ROAR.getTick() > 12 && this.ROAR.getTick() < 52) {
//					if (this.tickCount % 5 == 0) {
//						final Vec3 pos = EntityUtils.getRelativePos(this, 4f, 0f, 0f);
//						TextureFX.Builder.create(20).owner(this).texture(Aquamirae.MODID, "roar")
//								.pos(pos.add(0, this.getEyeY() - pos.y - 0.2, 0))
//								.relativeRot(this, true, true)
//								.xRot(90f, 0f, 0f)
//								.zRot((float) (360 * Math.random()), 0, 0)
//								.moveForward(0f, 3f, 0.03f)
//								.scale(0.4f, 0.1f, 0.01f)
//								.alpha(0.25f, -0.0025f, -0.0025f)
//								.build(this.level());
//					}
//					if (distance < 150) {
//						final Vec3 thisVec = new Vec3(this.getX(), this.getY(), this.getZ());
//						final Vec3 targetVec = new Vec3(target.getX(), target.getY(), target.getZ());
//						final Vec3 targetDelta = target.getDeltaMovement();
//						final Vec3 roarVec = thisVec.vectorTo(targetVec).scale(0.07).scale(Math.max(0, 1.0 - distance / 150.0));
//						target.setDeltaMovement(targetDelta.x + roarVec.x, targetDelta.y, targetDelta.z + roarVec.z);
//						if (target instanceof Player player)
//							player.hurtMarked = true;
//					}
//					if (target instanceof Mob mob) mob.setTarget(this);
//				}
//			}
//			//MOVE
//			if (!this.ATTACK.isPlaying() && !this.ROAR.isPlaying() && moveCooldown < 0) {
//				final Vec3 pos = this.getTarget() != null && this.distanceToSqr(this.getTarget()) > 40 ? this.targetEelMove() : this.randomEelMove();
//				if (pos.y() == 0) {
//					this.getEntityData().set(MOVE_COOLDOWN, 2);
//				} else {
//					this.getEntityData().set(POS_X, (float) pos.x());
//					this.getEntityData().set(POS_Y, (float) pos.y());
//					this.getEntityData().set(POS_Z, (float) pos.z());
//					this.getEntityData().set(MOVE_COOLDOWN, 400);
//					this.MOVE.play(this, 100);
//				}
//			}
//		}
		super.baseTick();
	}

	public void spawnBlockParticles() {
		final BlockState state = this.level().getBlockState(this.blockPosition().below());
		if (state.getRenderShape() != RenderShape.INVISIBLE) {
			this.level().addParticle(
					new BlockParticleOption(ParticleTypes.BLOCK, state).setPos(this.blockPosition().below()),
					this.position().x + (this.random.nextDouble() - 0.5D) * 1.6D,
					this.position().y + 0.03D,
					this.position().z + (this.random.nextDouble() - 0.5D) * 1.6D,
					0, 1.5D, 0);
		}
	}

	public Vec3 randomEelMove() {
		final RandomSource random = RandomSource.create();
		final int x = this.getBlockX() + random.nextInt(5, 16) * (Math.random() > 0.5 ? -1 : 1);
		final int z = this.getBlockZ() + random.nextInt(5, 16) * (Math.random() > 0.5 ? -1 : 1);
		for (int i = -8; i < 8; i++)
			if (checkGround(x, this.getBlockY() - i, z) && checkSpace(x, this.getBlockY() - i + 1, z) && checkSpace(x, this.getBlockY() - i + 2, z)
					&& checkSpace(x, this.getBlockY() - i + 3, z) && checkSpace(x, this.getBlockY() - i + 4, z))
				return new Vec3(x + 0.5, this.getBlockY() - i + 1.0, z + 0.5);
		return Vec3.ZERO;
	}

	public Vec3 targetEelMove() {
		if (this.getTarget() == null) return Vec3.ZERO;
		final RandomSource random = RandomSource.create();
		final int x = this.getTarget().getBlockX() + random.nextInt(-3, 3);
		final int z = this.getTarget().getBlockZ() + random.nextInt(-3, 3);
		for (int i = -4; i < 4; i++)
			if (checkGround(x, this.getBlockY() - i, z) && checkSpace(x, this.getBlockY() - i + 1, z) && checkSpace(x, this.getBlockY() - i + 2, z)
					&& checkSpace(x, this.getBlockY() - i + 3, z) && checkSpace(x, this.getBlockY() - i + 4, z))
				return new Vec3(x + 0.5, this.getBlockY() - i + 1.0, z + 0.5);
		return Vec3.ZERO;
	}

	public boolean checkGround(int x, int y, int z) {
		return this.level().getBlockState(new BlockPos(x, y, z)).is(AquamiraeTags.EEL_CAN_DIG)
				&& this.level().getBlockState(new BlockPos(x + 1, y, z)).is(AquamiraeTags.EEL_CAN_DIG)
				&& this.level().getBlockState(new BlockPos(x - 1, y, z)).is(AquamiraeTags.EEL_CAN_DIG)
				&& this.level().getBlockState(new BlockPos(x, y, z + 1)).is(AquamiraeTags.EEL_CAN_DIG)
				&& this.level().getBlockState(new BlockPos(x, y, z - 1)).is(AquamiraeTags.EEL_CAN_DIG);
	}

	public boolean checkSpace(int x, int y, int z) {
		return this.level().isEmptyBlock(new BlockPos(x, y, z)) && this.level().isEmptyBlock(new BlockPos(x + 1, y, z))
				&& this.level().isEmptyBlock(new BlockPos(x - 1, y, z)) && this.level().isEmptyBlock(new BlockPos(x, y, z + 1))
				&& this.level().isEmptyBlock(new BlockPos(x, y, z - 1)) && this.level().isEmptyBlock(new BlockPos(x + 1, y, z + 1))
				&& this.level().isEmptyBlock(new BlockPos(x - 1, y, z - 1)) && this.level().isEmptyBlock(new BlockPos(x + 1, y, z - 1))
				&& this.level().isEmptyBlock(new BlockPos(x - 1, y, z + 1));
	}

	@Override
	@SuppressWarnings("deprecation")
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		Aquamirae.setAttribute(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.eelMaxHealth.get());
		Aquamirae.setAttribute(this, Attributes.ARMOR, AquamiraeConfig.Common.eelArmor.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.eelAttackDamage.get());
		Aquamirae.setAttribute(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.eelFollowRange.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.eelAttackKnockback.get());
		return livingdata;
	}

	@Override
	public boolean isPushedByFluid(FluidType type) {
		return false;
	}

	@Override
	public void push(double d1, double d2, double d3) {
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return AquamiraeSounds.ENTITY_DEEP_HURT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_DEEP_DEATH.get();
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (animations.isPlaying(MOVE)) return false;
		if (source.getDirectEntity() instanceof AbstractArrow) return false;
		if (source.is(DamageTypes.FALL) || source.is(DamageTypes.CACTUS) || source.is(DamageTypes.DROWN)) return false;
		return super.hurt(source, amount);
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_EEL_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_EEL_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_EEL_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_EEL_FOLLOW_RANGE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_EEL_ATTACK_KNOCKBACK)
				.add(Attributes.KNOCKBACK_RESISTANCE, 100.0);
	}
}
