
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.api.ShipGraveyardEntity;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.client.animations.HekateLib;
import com.obscuria.obscureapi.client.animations.HekateProvider;
import com.obscuria.obscureapi.client.animations.IHekateProvider;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;
import java.util.Random;

@ShipGraveyardEntity
public class Eel extends MonsterEntity implements IHekateProvider {
	private final HekateProvider ANIMATIONS = new HekateProvider(this);
	private static final DataParameter<Integer> MOVE_COOLDOWN = EntityDataManager.defineId(Eel.class, DataSerializers.INT);
	private static final DataParameter<Float> POS_X = EntityDataManager.defineId(Eel.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> POS_Y = EntityDataManager.defineId(Eel.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> POS_Z = EntityDataManager.defineId(Eel.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> HIT_SERIES = EntityDataManager.defineId(Eel.class, DataSerializers.INT);
	private static final DataParameter<Float> SCALE = EntityDataManager.defineId(Eel.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> SCALE_SPEED = EntityDataManager.defineId(Eel.class, DataSerializers.FLOAT);

	public Eel(FMLPlayMessages.SpawnEntity packet, World world) {
		this(AquamiraeEntities.EEL.get(), world);
	}

	public Eel(EntityType<Eel> type, World world) {
		super(type, world);
		xpReward = 120;
		setPersistenceRequired();
	}

	@Override
	public AxisAlignedBB getBoundingBoxForCulling() {
		return super.getBoundingBoxForCulling().inflate(2F);
	}

	@Override protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false, false));
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

	@Override public void addAdditionalSaveData(CompoundNBT tag) {
		super.addAdditionalSaveData(tag);
		CompoundNBT data = new CompoundNBT();
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
	public void readAdditionalSaveData(CompoundNBT tag) {
		super.readAdditionalSaveData(tag);
		CompoundNBT data = tag.getCompound("EelData");
		if (data.isEmpty()) return;
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
				this.lookControl.setLookAt(target.position());
				//ATTACK
				if (this.getEntityData().get(HIT_SERIES) <= 0 && distance <= 30 && random.nextInt(60) == 1) {
					this.getEntityData().set(HIT_SERIES, 1 + new Random().nextInt(2));
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
				if (!target.level.isClientSide() && ANIMATIONS.isPlaying("roar") && ANIMATIONS.getTick("roar") <= 40) {
					if (distance < 150) {
						final Vector3d thisVec = new Vector3d(this.getX(), this.getY(), this.getZ());
						final Vector3d targetVec = new Vector3d(target.getX(), target.getY(), target.getZ());
						final Vector3d targetDelta = target.getDeltaMovement();
						final Vector3d roarVec = thisVec.vectorTo(targetVec).scale(0.07).scale(Math.max(0, 1.0 - distance / 150.0));
						target.setDeltaMovement(targetDelta.x + roarVec.x, targetDelta.y, targetDelta.z + roarVec.z);
						if (target instanceof PlayerEntity) target.hurtMarked = true;
					}
					if (target instanceof MobEntity) ((MobEntity)target).setTarget(this);
				}
			}
			//MOVE
			if (!ANIMATIONS.isPlaying("attack") && !ANIMATIONS.isPlaying("roar") && moveCooldown < 0) {
				final Vector3d pos = this.getTarget() != null && this.distanceToSqr(this.getTarget()) > 40 ? this.targetEelMove() : this.randomEelMove();
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
				ANIMATIONS.play("rareIdle", 80 + random.nextInt(80));
		}
		ANIMATIONS.playSound("attack", 20, "aquamirae:entity.eel.bite", SoundCategory.HOSTILE, 2F, 1F);
		ANIMATIONS.playSound("roar", 52, "aquamirae:entity.eel.roar", SoundCategory.HOSTILE, 2F, 1F);

		super.baseTick();
	}

	public Vector3d randomEelMove() {
		final int x = this.blockPosition().getX() + 5 + random.nextInt(11) * (Math.random() > 0.5 ? -1 : 1);
		final int z = this.blockPosition().getZ() + 5 + random.nextInt(11) * (Math.random() > 0.5 ? -1 : 1);
		for (int i = -8; i < 8; i++)
			if (checkGround(x, this.blockPosition().getY() - i, z) && checkSpace(x, this.blockPosition().getY()- i + 1, z) && checkSpace(x, this.blockPosition().getY() - i + 2, z)
					&& checkSpace(x, this.blockPosition().getY() - i + 3, z) && checkSpace(x, this.blockPosition().getY() - i + 4, z))
				return new Vector3d(x + 0.5, this.blockPosition().getY()- i + 1.0, z + 0.5);
		return Vector3d.ZERO;
	}

	public Vector3d targetEelMove() {
		if (this.getTarget() == null) return Vector3d.ZERO;
		final int x = this.getTarget().blockPosition().getX() - 3 + random.nextInt(6);
		final int z = this.getTarget().blockPosition().getZ() - 3 + random.nextInt(6);
		for (int i = -4; i < 4; i++)
			if (checkGround(x, this.blockPosition().getY() - i, z) && checkSpace(x, this.blockPosition().getY() - i + 1, z) && checkSpace(x, this.blockPosition().getY() - i + 2, z)
					&& checkSpace(x, this.blockPosition().getY() - i + 3, z) && checkSpace(x, this.blockPosition().getY() - i + 4, z))
				return new Vector3d(x + 0.5, this.blockPosition().getY()- i + 1.0, z + 0.5);
		return Vector3d.ZERO;
	}

	public boolean checkGround(int x, int y, int z) {
		return this.level.getBlockState(new BlockPos(x, y, z)).is(AquamiraeMod.EEL_MOVE)
				&& this.level.getBlockState(new BlockPos(x + 1, y, z)).is(AquamiraeMod.EEL_MOVE)
				&& this.level.getBlockState(new BlockPos(x - 1, y, z)).is(AquamiraeMod.EEL_MOVE)
				&& this.level.getBlockState(new BlockPos(x, y, z + 1)).is(AquamiraeMod.EEL_MOVE)
				&& this.level.getBlockState(new BlockPos(x, y, z - 1)).is(AquamiraeMod.EEL_MOVE);
	}

	public boolean checkSpace(int x, int y, int z) {
		return this.level.isEmptyBlock(new BlockPos(x, y, z)) && this.level.isEmptyBlock(new BlockPos(x + 1, y, z))
				&& this.level.isEmptyBlock(new BlockPos(x - 1, y, z)) && this.level.isEmptyBlock(new BlockPos(x, y, z + 1))
				&& this.level.isEmptyBlock(new BlockPos(x, y, z - 1)) && this.level.isEmptyBlock(new BlockPos(x + 1, y, z + 1))
				&& this.level.isEmptyBlock(new BlockPos(x - 1, y, z - 1)) && this.level.isEmptyBlock(new BlockPos(x + 1, y, z - 1))
				&& this.level.isEmptyBlock(new BlockPos(x - 1, y, z + 1));
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason,
										   @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT tag) {
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

	@Override public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEFINED;
	}

	@Override public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
	}

	@Override public SoundEvent getHurtSound(DamageSource source) {
		return AquamiraeSounds.ENTITY_DEEP_HURT.get();
	}

	@Override public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_DEEP_DEATH.get();
	}

	@Override public boolean hurt(DamageSource source, float amount) {
		if (ANIMATIONS.isPlaying("move")) return false;
		if (source.getDirectEntity() instanceof AbstractArrowEntity) return false;
		if (source == DamageSource.FALL || source == DamageSource.CACTUS || source == DamageSource.DROWN) return false;
		return super.hurt(source, amount);
	}

	@Override public boolean canChangeDimensions() {
		return false;
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_EEL_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_EEL_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_EEL_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_EEL_FOLLOW_RANGE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_EEL_ATTACK_KNOCKBACK)
				.add(Attributes.KNOCKBACK_RESISTANCE, 100);
	}
}
