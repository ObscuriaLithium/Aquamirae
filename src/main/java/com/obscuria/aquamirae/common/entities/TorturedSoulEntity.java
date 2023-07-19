
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@ShipGraveyardEntity
public class TorturedSoulEntity extends HostileEntity implements IAnimated {
	public final Animation ATTACK = new Animation(1);
	private int charge = 0;

	public TorturedSoulEntity(EntityType<TorturedSoulEntity> type, World world) {
		super(type, world);
		experiencePoints = 10;
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(1, new MeleeAttackGoal(this, 1.2, false));
		goalSelector.add(3, new WanderAroundFarGoal(this, 1));
		goalSelector.add(4, new LookAroundGoal(this));
		goalSelector.add(5, new LookAtEntityGoal(this, HostileEntity.class, 6));
		goalSelector.add(6, new SwimGoal(this));
		targetSelector.add(2, new RevengeGoal(this));
		targetSelector.add(7, new ActiveTargetGoal<>(this, PlayerEntity.class, false, false));
		targetSelector.add(8, new ActiveTargetGoal<>(this, IllagerEntity.class, false, false));
		targetSelector.add(9, new ActiveTargetGoal<>(this, VillagerEntity.class, false, false));
	}

	@Override
	public Optional<Animation> getAnimation(byte id) {
		return id == 1 ? Optional.of(ATTACK) : Optional.empty();
	}


	@Override
	public EntityGroup getGroup() {
		return EntityGroup.ILLAGER;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_ILLUSIONER_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ILLUSIONER_DEATH;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (source.getSource() instanceof PotionEntity || source.getSource() instanceof AreaEffectCloudEntity) return false;
		if (source.isOf(DamageTypes.DROWN)) return false;
		return super.damage(source, amount);
	}

	@Override
	public boolean tryAttack(Entity entity) {
		ATTACK.play(this, 20);
		return super.tryAttack(entity);
	}

	@Override
	public void tick() {
		AnimationHelper.handle(ATTACK);
		if (!getWorld().isClient() && getTarget() != null && !hasStatusEffect(StatusEffects.INVISIBILITY)) {
			charge++;
			if (charge > 200) {
				charge = 0;
				addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 2, true, false));
				addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 200, 0, (true), (false)));
				if (getWorld() instanceof ServerWorld server)
					server.spawnParticles(ParticleTypes.LARGE_SMOKE, getX(), getY()+1, getZ(), 10, 0.4, 0.8, 0.4, 0);
				setVelocity(getVelocity().add(0, 0.2, 0));
				velocityModified = true;
			}
		}
		if (hasStatusEffect(StatusEffects.INVISIBILITY) && getWorld() instanceof ServerWorld server) {
			if (age % 2 == 0) server.spawnParticles(ParticleTypes.LARGE_SMOKE, getX(), getY()+1.3, getZ(), 1, 0.14, 0.2, 0.14, 0);
			if (age % 6 == 0) server.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY()+1.3, getZ(), 1, 0.1, 0.15, 0.1, 0);
		}
		super.tick();
	}

	@SuppressWarnings("all")
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		//Aquamirae.loadFromConfig(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.soulSwimSpeed.get());
		//Aquamirae.loadFromConfig(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.soulSpeed.get());
		//Aquamirae.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.soulMaxHealth.get());
		//Aquamirae.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.soulArmor.get());
		//Aquamirae.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.soulAttackDamage.get());
		//Aquamirae.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.soulFollowRange.get());
		//Aquamirae.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.soulAttackKnockback.get());
		//Aquamirae.loadFromConfig(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.soulKnockbackResistance.get());
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public static SpawnRestriction.SpawnPredicate<TorturedSoulEntity> getSpawnRules() {
		return HostileEntity::canSpawnInDark;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, AquamiraeConfig.DEFAULT_SOUL_MOVEMENT_SPEED)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, AquamiraeConfig.DEFAULT_SOUL_MAX_HEALTH)
				.add(EntityAttributes.GENERIC_ARMOR, AquamiraeConfig.DEFAULT_SOUL_ARMOR)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_SOUL_ATTACK_DAMAGE)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, AquamiraeConfig.DEFAULT_SOUL_FOLLOW_RANGE)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_SOUL_ATTACK_KNOCKBACK)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_SOUL_KNOCKBACK_RESISTANCE);
	}
}
