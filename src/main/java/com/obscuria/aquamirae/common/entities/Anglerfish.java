package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.api.ShipGraveyardEntity;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.client.animations.HekateProvider;
import com.obscuria.obscureapi.client.animations.IHekateProvider;
import com.obscuria.obscureapi.world.ai.MeleeAttackGoal;
import com.obscuria.obscureapi.world.ai.attack.SimpleMeleeAttack;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

@ShipGraveyardEntity
public class Anglerfish extends MonsterEntity implements IHekateProvider {
	private final HekateProvider ANIMATIONS = new HekateProvider(this);
	private int attackTick = 0;
	public Anglerfish(FMLPlayMessages.SpawnEntity packet, World world) {
		this(AquamiraeEntities.ANGLERFISH.get(), world);
	}

	public Anglerfish(EntityType<Anglerfish> type, World world) {
		super(type, world);
		xpReward = 12;
		this.setPathfindingMalus(PathNodeType.WATER, 0);
		this.moveControl = new MovementController(this) {
			@Override public void tick() {
				if (Anglerfish.this.isInWater())
					Anglerfish.this.setDeltaMovement(Anglerfish.this.getDeltaMovement().add(0, 0.005, 0));
				if (this.operation == MovementController.Action.MOVE_TO && !Anglerfish.this.getNavigation().isDone()) {
					double dx = this.wantedX - Anglerfish.this.getX();
					double dy = this.wantedY - Anglerfish.this.getY();
					double dz = this.wantedZ - Anglerfish.this.getZ();
					float f = (float) (MathHelper.atan2(dz, dx) * (180 / Math.PI)) - 90;
					float f1 = (float) (this.speedModifier * Objects.requireNonNull(Anglerfish.this.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
					Anglerfish.this.yRot = (this.rotlerp(Anglerfish.this.yRot, f, 10));
					Anglerfish.this.yBodyRot = Anglerfish.this.yRot;
					Anglerfish.this.yHeadRot = Anglerfish.this.yRot;
					if (Anglerfish.this.isInWater()) {
						Anglerfish.this.setSpeed((float) Objects.requireNonNull(Anglerfish.this.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
						float f2 = -(float) (MathHelper.atan2(dy, (float) Math.sqrt(dx * dx + dz * dz)) * (180 / Math.PI));
						f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85, 85);
						Anglerfish.this.xRot = (this.rotlerp(Anglerfish.this.xRot, f2, 5));
						float f3 = MathHelper.cos(Anglerfish.this.xRot * (float) (Math.PI / 180.0));
						Anglerfish.this.setZza(f3 * f1);
						Anglerfish.this.setYya((float) (f1 * dy));
					} else {
						Anglerfish.this.setSpeed(f1 * 0.05F);
					}
				} else {
					Anglerfish.this.setSpeed(0);
					Anglerfish.this.setYya(0);
					Anglerfish.this.setZza(0);
				}
			}
		};
	}

	@Override public HekateProvider getHekateProvider() {
		return this.ANIMATIONS;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		final boolean hurt = super.doHurtTarget(entity);
		if (hurt && entity instanceof LivingEntity) ((LivingEntity)entity).addEffect(new EffectInstance(Effects.POISON, 100, 0));
		return hurt;
	}

	@Override
	protected PathNavigator createNavigation(World world) {
		return new SwimmerPathNavigator(this, world);
	}

	@Override protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false,
				new SimpleMeleeAttack("attack", 30, 9, 10, 30, 4, 3.5)));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false, false));
		this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1, 40) {
			@Override protected Vector3d getPosition() {
				Vector3d vector3d = RandomPositionGenerator.getPos(this.mob, 32, 7);
				for(int i = 0;
					vector3d != null && !this.mob.level.getBlockState(new BlockPos(vector3d)).isPathfindable(this.mob.level, new BlockPos(vector3d), PathType.WATER) && i++ < 10;
					vector3d = RandomPositionGenerator.getPos(this.mob, 10, 7)) {}
				return vector3d;
			}
		});
	}

	@Override public CreatureAttribute getMobType() {
		return CreatureAttribute.WATER;
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

	@Override public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.GUARDIAN_FLOP, 0.15f, 1);
	}

	@Override public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty,SpawnReason spawnType, @Nullable ILivingEntityData spawnGroupData, @Nullable CompoundNBT tag) {
		if (world instanceof ServerWorld && spawnType == SpawnReason.NATURAL && this.random.nextInt(200) == 1) {
			final ServerWorld server = (ServerWorld) world;
			MazeMother mazeMother = new MazeMother(AquamiraeEntities.MAZE_MOTHER.get(), server);
			mazeMother.moveTo(this.position());
			mazeMother.finalizeSpawn(server, difficulty, spawnType, null, null);
			world.addFreshEntity(mazeMother);
		}
		AquamiraeMod.loadFromConfig(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.anglerfishSwimSpeed.get());
		AquamiraeMod.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.anglerfishMaxHealth.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.anglerfishArmor.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.anglerfishAttackDamage.get());
		AquamiraeMod.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.anglerfishFollowRange.get());
		AquamiraeMod.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.anglerfishAttackKnockback.get());
		AquamiraeMod.loadFromConfig(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.anglerfishKnockbackResistance.get());
		return super.finalizeSpawn(world, difficulty, spawnType, spawnGroupData, tag);
	}

	@Override public void baseTick() {
		ANIMATIONS.playSound("attack", 24, "aquamirae:entity.eel.bite", SoundCategory.HOSTILE, 1F, 1F);
		if (this.isInWater()) this.setDeltaMovement(this.getDeltaMovement().add(0, -0.001F, 0));
		else {
			if (ANIMATIONS.isPlaying("onGround")) {
				if (ANIMATIONS.getTick("onGround") <= 2) ANIMATIONS.play("onGround", 20);
			} else {
				if (this.tickCount > 1 && this.attackTick <= 0) ANIMATIONS.play("onGround", 20);
			}
		}
		if (ANIMATIONS.isPlaying("attack")) {
			attackTick = 10;
			if (this.getTarget() != null) this.lookControl.setLookAt(this.getTarget().position());
			if (ANIMATIONS.getTick("attack") > 12) this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
			if (ANIMATIONS.getTick("attack") == 12 && this.getTarget() != null) this.setDeltaMovement(this.getDeltaMovement()
					.add(this.position().vectorTo(this.getTarget().position()).scale(0.4F)));
		} else this.attackTick--;
		super.baseTick();
	}

	@Override public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean checkSpawnObstruction(IWorldReader world) {
		return world.isUnobstructed(this);
	}

	@Override public boolean isPushedByFluid() {
		return false;
	}

	public static boolean checkAnglerfishSpawnRules(EntityType<Anglerfish> type, IServerWorld serverWorld, SpawnReason reason, BlockPos pos, Random random) {
		return serverWorld.getDifficulty() != Difficulty.PEACEFUL && (reason == SpawnReason.SPAWNER || serverWorld.getFluidState(pos).is(FluidTags.WATER));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
				.add(ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.DEFAULT_ANGLERFISH_SWIM_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_ANGLERFISH_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_ANGLERFISH_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_ANGLERFISH_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_ANGLERFISH_FOLLOW_RANGE)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_ANGLERFISH_KNOCKBACK_RESISTANCE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_ANGLERFISH_ATTACK_KNOCKBACK);
	}
}
