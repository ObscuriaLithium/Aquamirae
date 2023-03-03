
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.api.ShipGraveyardEntity;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.client.animations.HekateProvider;
import com.obscuria.obscureapi.client.animations.IHekateProvider;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;

@ShipGraveyardEntity
public class TorturedSoul extends MonsterEntity implements IHekateProvider {
	private final HekateProvider ANIMATIONS = new HekateProvider(this);
	public TorturedSoul(FMLPlayMessages.SpawnEntity packet, World world) {
		this(AquamiraeEntities.TORTURED_SOUL.get(), world);
	}

	public TorturedSoul(EntityType<TorturedSoul> type, World world) {
		super(type, world);
		xpReward = 0;
	}

	@Override protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			@Override protected double getAttackReachSqr(LivingEntity entity) {
				return 4.0 + entity.getBbWidth() * entity.getBbWidth();
			}
		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(5, new LookAtGoal(this, MonsterEntity.class, (float) 6));
		this.goalSelector.addGoal(6, new SwimGoal(this));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, false, false));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false, false));
	}

	@Override public HekateProvider getHekateProvider() {
		return this.ANIMATIONS;
	}

	@Override
	public CreatureAttribute getMobType() {
		return CreatureAttribute.ILLAGER;
	}

	@Override public SoundEvent getAmbientSound() {
		return SoundEvents.ILLUSIONER_AMBIENT;
	}

	@Override public SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ILLUSIONER_HURT;
	}

	@Override public SoundEvent getDeathSound() {
		return SoundEvents.ILLUSIONER_DEATH;
	}

	@Override public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof PotionEntity || source.getDirectEntity() instanceof AreaEffectCloudEntity) return false;
		if (source == DamageSource.DROWN) return false;
		return super.hurt(source, amount);
	}

	@Override public boolean doHurtTarget(Entity entity) {
		ANIMATIONS.play("attack", 5);
		return super.doHurtTarget(entity);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnType,
										   @Nullable ILivingEntityData spawnGroupData, @Nullable CompoundNBT tag) {
		AquamiraeMod.loadFromConfig(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.soulSwimSpeed.get());
		AquamiraeMod.loadFromConfig(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.soulSpeed.get());
		AquamiraeMod.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.soulMaxHealth.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.soulArmor.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.soulAttackDamage.get());
		AquamiraeMod.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.soulFollowRange.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.soulAttackKnockback.get());
		AquamiraeMod.loadFromConfig(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.soulKnockbackResistance.get());
		return super.finalizeSpawn(world, difficulty, spawnType, spawnGroupData, tag);
	}

	@Override public void baseTick() {
		if (this.getTarget() != null && !this.hasEffect(Effects.INVISIBILITY)) {
			this.getPersistentData().putDouble("charge", this.getPersistentData().getDouble("charge") + 1);
			if (this.getPersistentData().getDouble("charge") > 200) {
				this.getPersistentData().putDouble("charge", 0);
				this.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 200, 2, (true), (false)));
				this.addEffect(new EffectInstance(Effects.INVISIBILITY, 200, 0, (true), (false)));
				if (this.level instanceof ServerWorld) ((ServerWorld) this.level).sendParticles(
						ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1, this.getZ(), 10, 0.4, 0.8, 0.4, 0);
				this.setDeltaMovement(new Vector3d(this.getDeltaMovement().x(), 0.2, this.getDeltaMovement().z()));
			}
		}
		if (this.hasEffect(Effects.INVISIBILITY)) {
			this.getPersistentData().putDouble("flame", this.getPersistentData().getDouble("flame") + 1);
			this.getPersistentData().putDouble("smoke", this.getPersistentData().getDouble("smoke") + 1);
			if (this.getPersistentData().getDouble("smoke") > 1) {
				this.getPersistentData().putDouble("smoke", 0);
				if (this.level instanceof ServerWorld)
					((ServerWorld) this.level).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.3, this.getZ(), 1, 0.14, 0.2, 0.14, 0);
			}
			if (this.getPersistentData().getDouble("flame") > 6) {
				this.getPersistentData().putDouble("flame", 0);
				if (this.level instanceof ServerWorld)
					((ServerWorld) this.level).sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY() + 1.3, this.getZ(), 1, 0.1, 0.15, 0.1, 0);
			}
		}
		super.baseTick();
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
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
