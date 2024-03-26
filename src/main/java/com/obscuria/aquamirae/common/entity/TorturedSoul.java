
package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.core.api.animation.entity.EntityAnimations;
import com.obscuria.core.api.animation.entity.IAnimatedEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@IceMazeEntity
public class TorturedSoul extends Monster implements IAnimatedEntity {
	private final EntityAnimations<TorturedSoul> animations = EntityAnimations.create(this);
	public final String ATTACK = "attack";

	public TorturedSoul(EntityType<TorturedSoul> type, Level world) {
		super(type, world);
		xpReward = 0;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Monster.class, (float) 6));
		this.goalSelector.addGoal(6, new FloatGoal(this));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
	}

	@Override
	public EntityAnimations<? extends Entity> getAnimations() {
		return animations;
	}

	@Override
	public @NotNull MobType getMobType() {
		return MobType.ILLAGER;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ILLUSIONER_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(@NotNull DamageSource source) {
		return SoundEvents.ILLUSIONER_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ILLUSIONER_DEATH;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud) return false;
		if (source.is(DamageTypes.DROWN)) return false;
		return super.hurt(source, amount);
	}

	@Override public boolean doHurtTarget(@NotNull Entity entity) {
		animations.play(ATTACK);
		return super.doHurtTarget(entity);
	}

	@Override
	@SuppressWarnings("all")
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
		Aquamirae.setAttribute(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.soulSwimSpeed.get());
		Aquamirae.setAttribute(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.soulSpeed.get());
		Aquamirae.setAttribute(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.soulMaxHealth.get());
		Aquamirae.setAttribute(this, Attributes.ARMOR, AquamiraeConfig.Common.soulArmor.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.soulAttackDamage.get());
		Aquamirae.setAttribute(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.soulFollowRange.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.soulAttackKnockback.get());
		Aquamirae.setAttribute(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.soulKnockbackResistance.get());
		getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random spawn bonus",
				random.triangle(0.0, 0.12), AttributeModifier.Operation.MULTIPLY_BASE));
		return spawnGroupData;
	}

	@Override
	public void baseTick() {
		if (this.getTarget() != null && !this.hasEffect(MobEffects.INVISIBILITY)) {
			this.getPersistentData().putDouble("charge", this.getPersistentData().getDouble("charge") + 1);
			if (this.getPersistentData().getDouble("charge") > 200) {
				this.getPersistentData().putDouble("charge", 0);
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2, (true), (false)));
				this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200, 0, (true), (false)));
				if (this.level() instanceof ServerLevel server)
					server.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1, this.getZ(), 10, 0.4, 0.8, 0.4, 0);
				this.setDeltaMovement(new Vec3(this.getDeltaMovement().x(), 0.2, this.getDeltaMovement().z()));
			}
		}
		if (this.hasEffect(MobEffects.INVISIBILITY)) {
			this.getPersistentData().putDouble("flame", this.getPersistentData().getDouble("flame") + 1);
			this.getPersistentData().putDouble("smoke", this.getPersistentData().getDouble("smoke") + 1);
			if (this.getPersistentData().getDouble("smoke") > 1) {
				this.getPersistentData().putDouble("smoke", 0);
				if (this.level() instanceof ServerLevel server)
					server.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.3, this.getZ(), 1, 0.14, 0.2, 0.14, 0);
			}
			if (this.getPersistentData().getDouble("flame") > 6) {
				this.getPersistentData().putDouble("flame", 0);
				if (this.level() instanceof ServerLevel server)
					server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY() + 1.3, this.getZ(), 1, 0.1, 0.15, 0.1, 0);
			}
		}
		super.baseTick();
	}

	public static SpawnPlacements.SpawnPredicate<TorturedSoul> getSpawnRules() {
		return Monster::checkMonsterSpawnRules;
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.DEFAULT_SOUL_SWIM_SPEED)
				.add(Attributes.MOVEMENT_SPEED, AquamiraeConfig.DEFAULT_SOUL_MOVEMENT_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_SOUL_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_SOUL_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_SOUL_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_SOUL_FOLLOW_RANGE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_SOUL_ATTACK_KNOCKBACK)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_SOUL_KNOCKBACK_RESISTANCE);
	}
}
