
package com.obscuria.aquamirae.world.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.client.animations.HekateLib;
import com.obscuria.obscureapi.client.animations.HekateProvider;
import com.obscuria.obscureapi.client.animations.IHekateProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class Eel extends Monster implements IShipGraveyardEntity, IHekateProvider {
	private final HekateProvider ANIMATIONS = new HekateProvider(this);
	private static final EntityDataAccessor<Integer> MOVE_COOLDOWN = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> POS_X = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> POS_Y = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> POS_Z = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Integer> HIT_SERIES = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SCALE_SPEED = SynchedEntityData.defineId(Eel.class, EntityDataSerializers.FLOAT);

	public Eel(PlayMessages.SpawnEntity packet, Level world) {
		this(AquamiraeEntities.EEL.get(), world);
	}

	public Eel(EntityType<Eel> type, Level world) {
		super(type, world);
		xpReward = 120;
		setPersistenceRequired();
	}

	@Override public @NotNull AABB getBoundingBoxForCulling() {
		return super.getBoundingBoxForCulling().inflate(2F);
	}

	@Override protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
	}

	@Override protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(MOVE_COOLDOWN, 200);
		this.getEntityData().define(POS_X, (float) this.getX());
		this.getEntityData().define(POS_Y, (float) this.getY());
		this.getEntityData().define(POS_Z, (float) this.getZ());
		this.getEntityData().define(HIT_SERIES, 0);
		this.getEntityData().define(SCALE, 1F);
		this.getEntityData().define(SCALE_SPEED, 0.1F);
	}

	@Override public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		CompoundTag data = new CompoundTag();
		data.putInt("MoveCooldown", this.getEntityData().get(MOVE_COOLDOWN));
		data.putInt("Hits", this.getEntityData().get(HIT_SERIES));
		data.putFloat("Scale", this.getEntityData().get(SCALE));
		data.putFloat("ScaleSpeed", this.getEntityData().get(SCALE_SPEED));
		data.putFloat("x", this.getEntityData().get(POS_X));
		data.putFloat("y", this.getEntityData().get(POS_Y));
		data.putFloat("z", this.getEntityData().get(POS_Z));
		tag.put("EelData", data);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		CompoundTag data = tag.getCompound("EelData");
		this.getEntityData().set(MOVE_COOLDOWN, data.getInt("MoveCooldown"));
		this.getEntityData().set(HIT_SERIES, data.getInt("Hits"));
		this.getEntityData().set(SCALE, data.getFloat("Scale"));
		this.getEntityData().set(SCALE_SPEED, data.getFloat("ScaleSpeed"));
		this.getEntityData().set(POS_X, data.getFloat("x"));
		this.getEntityData().set(POS_Y, data.getFloat("y"));
		this.getEntityData().set(POS_Z, data.getFloat("z"));
	}

	public HekateProvider getHekateProvider() {
		return this.ANIMATIONS;
	}

	@Override public void baseTick() {
		HekateLib.updateScale(this, this.getEntityData().get(SCALE), this.getEntityData().get(SCALE_SPEED));
		final int moveCooldown = this.getEntityData().get(MOVE_COOLDOWN);
		final int hitSeries = this.getEntityData().get(HIT_SERIES);
		this.getEntityData().set(MOVE_COOLDOWN, moveCooldown - 1);

		if (ANIMATIONS.isPlaying("move")) {
			if (ANIMATIONS.getTick("move") == 25)
				this.teleportTo(this.getEntityData().get(POS_X), this.getEntityData().get(POS_Y), this.getEntityData().get(POS_Z));
			if (ANIMATIONS.getTick("move") >= 20 && ANIMATIONS.getTick("move") <= 27) {
				this.getEntityData().set(SCALE, 0F);
				this.getEntityData().set(SCALE_SPEED, 1F);
			} else if (ANIMATIONS.getTick("move") >= 17 && ANIMATIONS.getTick("move") <= 19) {
				this.getEntityData().set(SCALE, 1F);
				this.getEntityData().set(SCALE_SPEED, 1F);
			} else if (ANIMATIONS.getTick("move") == 16) {
				this.getEntityData().set(SCALE_SPEED, 0.1F);
			}
		} else {
			if (this.getTarget() != null) {
				final LivingEntity target = this.getTarget();
				final double distance = this.distanceToSqr(target);
				this.lookControl.setLookAt(target);
				//ATTACK
				if (this.getEntityData().get(HIT_SERIES) <= 0 && distance <= 30 && random.nextInt(60) == 1) {
					this.getEntityData().set(HIT_SERIES, new Random().nextInt(1, 3));
				} else if (this.getEntityData().get(HIT_SERIES) > 0 && !ANIMATIONS.isPlaying("attack") && !ANIMATIONS.isPlaying("roar")) {
					this.getEntityData().set(HIT_SERIES, hitSeries - 1);
					ANIMATIONS.play("attack", 20);
				}
				if (!ANIMATIONS.isPlaying("attack") && !ANIMATIONS.isPlaying("roar") && random.nextInt(100) == 1)
					ANIMATIONS.play("roar", 52);
				if (distance <= 24 && ANIMATIONS.getTick("attack") == 6) {
					final float hp = target.getHealth();
					this.doHurtTarget(target);
					if (target.getHealth() < hp) this.heal((hp - target.getHealth()) * 2);
				}
				//KNOCKBACK
				if (!target.getLevel().isClientSide() && ANIMATIONS.isPlaying("roar") && ANIMATIONS.getTick("roar") <= 40) {
					if (distance < 150) {
						final Vec3 thisVec = new Vec3(this.getX(), this.getY(), this.getZ());
						final Vec3 targetVec = new Vec3(target.getX(), target.getY(), target.getZ());
						final Vec3 targetDelta = target.getDeltaMovement();
						final Vec3 roarVec = thisVec.vectorTo(targetVec).scale(0.07).scale(Math.max(0, 1.0 - distance / 150.0));
						target.setDeltaMovement(targetDelta.x + roarVec.x, targetDelta.y, targetDelta.z + roarVec.z);
						if (target instanceof Player player)
							player.hurtMarked = true;
					}
					if (target instanceof Mob mob) mob.setTarget(this);
				}
			}
			//MOVE
			if (!ANIMATIONS.isPlaying("attack") && !ANIMATIONS.isPlaying("roar") && moveCooldown < 0) {
				final Vec3 pos = this.getTarget() != null && this.distanceToSqr(this.getTarget()) > 40 ? this.targetEelMove() : this.randomEelMove();
				if (pos.y() == 0) {
					this.getEntityData().set(MOVE_COOLDOWN, 2);
				} else {
					this.getEntityData().set(POS_X, (float) pos.x());
					this.getEntityData().set(POS_Y, (float) pos.y());
					this.getEntityData().set(POS_Z, (float) pos.z());
					this.getEntityData().set(MOVE_COOLDOWN, 400);
					ANIMATIONS.play("move", 50);
					ANIMATIONS.play("moveMain", 50);
				}
			}
			//IDLE
			if (!ANIMATIONS.isPlaying("rareIdle") && random.nextInt(80) == 1)
				ANIMATIONS.play("rareIdle", random.nextInt(80, 160));
		}
		ANIMATIONS.playSound("attack", 20, "aquamirae:entity.eel.bite", SoundSource.HOSTILE, 2F, 1F);
		ANIMATIONS.playSound("roar", 52, "aquamirae:entity.eel.roar", SoundSource.HOSTILE, 2F, 1F);

		super.baseTick();
	}

	public Vec3 randomEelMove() {
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
		final int x = this.getTarget().getBlockX() + random.nextInt(-3, 3);
		final int z = this.getTarget().getBlockZ() + random.nextInt(-3, 3);
		for (int i = -4; i < 4; i++)
			if (checkGround(x, this.getBlockY() - i, z) && checkSpace(x, this.getBlockY() - i + 1, z) && checkSpace(x, this.getBlockY() - i + 2, z)
					&& checkSpace(x, this.getBlockY() - i + 3, z) && checkSpace(x, this.getBlockY() - i + 4, z))
				return new Vec3(x + 0.5, this.getBlockY() - i + 1.0, z + 0.5);
		return Vec3.ZERO;
	}

	public boolean checkGround(int x, int y, int z) {
		return this.getLevel().getBlockState(new BlockPos(x, y, z)).is(AquamiraeMod.EEL_MOVE)
				&& this.getLevel().getBlockState(new BlockPos(x + 1, y, z)).is(AquamiraeMod.EEL_MOVE)
				&& this.getLevel().getBlockState(new BlockPos(x - 1, y, z)).is(AquamiraeMod.EEL_MOVE)
				&& this.getLevel().getBlockState(new BlockPos(x, y, z + 1)).is(AquamiraeMod.EEL_MOVE)
				&& this.getLevel().getBlockState(new BlockPos(x, y, z - 1)).is(AquamiraeMod.EEL_MOVE);
	}

	public boolean checkSpace(int x, int y, int z) {
		return this.getLevel().isEmptyBlock(new BlockPos(x, y, z)) && this.getLevel().isEmptyBlock(new BlockPos(x + 1, y, z))
				&& this.getLevel().isEmptyBlock(new BlockPos(x - 1, y, z)) && this.getLevel().isEmptyBlock(new BlockPos(x, y, z + 1))
				&& this.getLevel().isEmptyBlock(new BlockPos(x, y, z - 1)) && this.getLevel().isEmptyBlock(new BlockPos(x + 1, y, z + 1))
				&& this.getLevel().isEmptyBlock(new BlockPos(x - 1, y, z - 1)) && this.getLevel().isEmptyBlock(new BlockPos(x + 1, y, z - 1))
				&& this.getLevel().isEmptyBlock(new BlockPos(x - 1, y, z + 1));
	}

	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason,
										@Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		AquamiraeMod.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.eelMaxHealth.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.eelArmor.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.eelAttackDamage.get());
		AquamiraeMod.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.eelFollowRange.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.eelAttackKnockback.get());
		return super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
	}

	@Override public boolean isPushedByFluid() {
		return false;
	}

	@Override public void push(double d1, double d2, double d3) {
	}

	@Override public @NotNull MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
	}

	@Override public SoundEvent getHurtSound(@NotNull DamageSource source) {
		return AquamiraeSounds.ENTITY_DEEP_HURT.get();
	}

	@Override public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_DEEP_DEATH.get();
	}

	@Override public boolean hurt(@NotNull DamageSource source, float amount) {
		if (ANIMATIONS.isPlaying("move")) return false;
		if (source.getDirectEntity() instanceof AbstractArrow) return false;
		if (source == DamageSource.FALL || source == DamageSource.CACTUS || source == DamageSource.DROWN) return false;
		return super.hurt(source, amount);
	}

	@Override public boolean canChangeDimensions() {
		return false;
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_EEL_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_EEL_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_EEL_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_EEL_FOLLOW_RANGE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_EEL_ATTACK_KNOCKBACK);
	}
}
