
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import com.obscuria.obscureapi.api.tools.ExceptionFilter;
import com.obscuria.obscureapi.api.utils.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@ShipGraveyardEntity
public class MawEntity extends HostileEntity implements IAnimated {
	private static final TrackedData<ItemStack> ITEM_IN_MOUTH = DataTracker.registerData(MawEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
	public final Animation ATTACK = new Animation(1);
	public final Animation DEATH = new Animation(2);

	public MawEntity(EntityType<MawEntity> type, World world) {
		super(type, world);
		experiencePoints = 10;
		generateStackInMouth();
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(1, new PounceAtTargetGoal(this, 0.4f));
		goalSelector.add(2, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getSquaredMaxAttackDistance(LivingEntity entity) {
				return 4f + Math.pow(entity.getWidth(), 2);
			}
		});
		goalSelector.add(4, new WanderAroundFarGoal(this, 0.8));
		goalSelector.add(7, new SwimGoal(this));
		targetSelector.add(3, new RevengeGoal(this));
		targetSelector.add(5, new ActiveTargetGoal<>(this, PlayerEntity.class, false, false));
		targetSelector.add(6, new ActiveTargetGoal<>(this, IllagerEntity.class, false, false));
		targetSelector.add(6, new ActiveTargetGoal<>(this, VillagerEntity.class, false, false));
	}

	@Override
	protected void initDataTracker() {
		dataTracker.startTracking(ITEM_IN_MOUTH, ItemStack.EMPTY);
		super.initDataTracker();
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.put("ItemInMouth", getStackInMouth().writeNbt(new NbtCompound()));
		super.writeCustomDataToNbt(nbt);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		if (nbt.contains("ItemInMouth")) setStackInMouth(ItemStack.fromNbt(nbt.getCompound("ItemInMouth")));
		super.readCustomDataFromNbt(nbt);
	}

	public void generateStackInMouth() {
		if (getWorld().isClient()) return;
		final MinecraftServer server = getServer();
		if (server != null && getWorld() instanceof ServerWorld serverWorld) {
			final LootContextParameterSet lootContext = new LootContextParameterSet.Builder(serverWorld)
					.add(LootContextParameters.THIS_ENTITY, this)
					.add(LootContextParameters.ORIGIN, getPos())
					.build(LootContextTypes.GIFT);
			final LootTable treasure = server.getLootManager().getLootTable(Aquamirae.key("entities/maw_random_item"));
			ExceptionFilter.of(() -> setStackInMouth(treasure.generateLoot(lootContext).get(0)));
		}
	}

	public Optional<Animation> getAnimation(byte id) {
		return id == 1 ? Optional.of(ATTACK) : id == 2 ? Optional.of(DEATH) : Optional.empty();
	}

	@Override
	public void tick() {
		AnimationHelper.handleDeath(this, DEATH, 40);
		AnimationHelper.handle(ATTACK, DEATH);
		super.tick();
	}

	public void setStackInMouth(ItemStack stack) {
		dataTracker.set(ITEM_IN_MOUTH, stack);
	}

	public ItemStack getStackInMouth() {
		return dataTracker.get(ITEM_IN_MOUTH);
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.DEFAULT;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_DEEP_AMBIENT;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.ENTITY_GUARDIAN_FLOP, 0.15f, 1);
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
	protected void dropInventory() {
		if (getWorld() instanceof ServerWorld server && !getStackInMouth().isEmpty()) {
			WorldUtils.dropItem(server, getBlockPos(), getStackInMouth());
			setStackInMouth(ItemStack.EMPTY);
		}
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (source.isOf(DamageTypes.DROWN)) return false;
		return super.damage(source, amount);
	}

	@Override
	public boolean tryAttack(Entity target) {
		ATTACK.play(this, 20);
		return super.tryAttack(target);
	}

	@SuppressWarnings("all")
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		//Aquamirae.loadFromConfig(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.mawSwimSpeed.get());
		//Aquamirae.loadFromConfig(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.mawSpeed.get());
		//Aquamirae.loadFromConfig(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.mawMaxHealth.get());
		//Aquamirae.loadFromConfig(this, Attributes.ARMOR, AquamiraeConfig.Common.mawArmor.get());
		//Aquamirae.loadFromConfig(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.mawAttackDamage.get());
		//Aquamirae.loadFromConfig(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.mawFollowRange.get());
		//Aquamirae.loadFromConfig(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.mawAttackKnockback.get());
		//Aquamirae.loadFromConfig(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.mawKnockbackResistance.get());
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	@SuppressWarnings("deprecation")
	public static SpawnRestriction.SpawnPredicate<MawEntity> getSpawnRules() {
		return (entityType, level, spawnType, pos, random) -> pos.getY() < level.getSeaLevel() + 6 &&
				HostileEntity.canSpawnIgnoreLightLevel(entityType, level, spawnType, pos, random);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				//.add(Attributes.SWIM_SPEED.get(), AquamiraeConfig.DEFAULT_MAW_SWIM_SPEED)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, AquamiraeConfig.DEFAULT_MAW_MOVEMENT_SPEED)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, AquamiraeConfig.DEFAULT_MAW_MAX_HEALTH)
				.add(EntityAttributes.GENERIC_ARMOR, AquamiraeConfig.DEFAULT_MAW_ARMOR)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_MAW_ATTACK_DAMAGE)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, AquamiraeConfig.DEFAULT_MAW_FOLLOW_RANGE)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_MAW_ATTACK_KNOCKBACK)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_MAW_KNOCKBACK_RESISTANCE);
	}
}
