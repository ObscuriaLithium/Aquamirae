
package com.obscuria.aquamirae.world.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.client.animations.HekateProvider;
import com.obscuria.obscureapi.client.animations.IHekateProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Maw extends Monster implements IShipGraveyardEntity, IHekateProvider {

	private final HekateProvider ANIMATIONS = new HekateProvider(this);

	public Maw(PlayMessages.SpawnEntity packet, Level world) {
		this(AquamiraeEntities.MAW.get(), world);
	}

	public Maw(EntityType<Maw> type, Level world) {
		super(type, world);
		xpReward = 10;
	}

	@Override protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, (float) 0.4));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {
			@Override protected double getAttackReachSqr(@NotNull LivingEntity entity) {
				return 4.0 + entity.getBbWidth() * entity.getBbWidth();
			}
		});
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
		this.goalSelector.addGoal(7, new FloatGoal(this));
	}

	@Override public HekateProvider getHekateProvider() {
		return this.ANIMATIONS;
	}

	@Override public @NotNull MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
	}

	@Override public void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
		this.playSound(SoundEvents.GUARDIAN_FLOP, 0.15f, 1);
	}

	@Override public SoundEvent getHurtSound(@NotNull DamageSource source) {
		return AquamiraeSounds.ENTITY_DEEP_HURT.get();
	}

	@Override public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_DEEP_DEATH.get();
	}

	@Override public boolean hurt(@NotNull DamageSource source, float amount) {
		if (source == DamageSource.DROWN) return false;
		return super.hurt(source, amount);
	}

	@Override public boolean doHurtTarget(@NotNull Entity entity) {
		ANIMATIONS.play("attack", 5);
		return super.doHurtTarget(entity);
	}

	@Override public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason,
												  @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		AquamiraeMod.loadFromConfig(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.mawSwimSpeed.get());
		AquamiraeMod.loadFromConfig(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.mawSpeed.get());
		AquamiraeMod.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.mawMaxHealth.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.mawArmor.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.mawAttackDamage.get());
		AquamiraeMod.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.mawFollowRange.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.mawAttackKnockback.get());
		AquamiraeMod.loadFromConfig(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.mawKnockbackResistance.get());
		return super.finalizeSpawn(world, difficulty, reason, data, tag);
	}

	public static SpawnPlacements.SpawnPredicate<Maw> getSpawnRules() {
		return (entityType, level, spawnType, pos, random) -> pos.getY() < level.getSeaLevel() + 6 &&
				Monster.checkAnyLightMonsterSpawnRules(entityType, level, spawnType, pos, random);
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.DEFAULT_MAW_SWIM_SPEED)
				.add(Attributes.MOVEMENT_SPEED, AquamiraeConfig.DEFAULT_MAW_MOVEMENT_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_MAW_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_MAW_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_MAW_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_MAW_FOLLOW_RANGE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_MAW_ATTACK_KNOCKBACK)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_MAW_KNOCKBACK_RESISTANCE);
	}
}
