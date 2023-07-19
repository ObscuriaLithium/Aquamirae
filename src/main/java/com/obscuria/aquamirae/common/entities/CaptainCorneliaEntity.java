
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeClient;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.common.items.weapon.DividerItem;
import com.obscuria.aquamirae.common.items.weapon.PoisonedBladeItem;
import com.obscuria.aquamirae.common.items.weapon.RemnantsSaberItem;
import com.obscuria.aquamirae.common.items.weapon.WhisperOfTheAbyssItem;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import com.obscuria.obscureapi.api.utils.EntityUtils;
import com.obscuria.obscureapi.common.entities.DynamicProjectileEntity;
import com.obscuria.obscureapi.common.entities.TextureFX;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ShipGraveyardEntity
public class CaptainCorneliaEntity extends HostileEntity implements IAnimated {
	private static final TrackedData<Integer> REGENERATION = DataTracker.registerData(CaptainCorneliaEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private final ServerBossBar bossInfo = new ServerBossBar(this.getDisplayName(), BossBar.Color.BLUE, BossBar.Style.PROGRESS);
	public final Animation PULL_ATTACK = new Animation(1);
	public final Animation SWING_ATTACK = new Animation(2);
	public final Animation THRUST_ATTACK = new Animation(3);
	public final Animation SWITCH_WEAPON = new Animation(4);
	public final Animation DEATH = new Animation(5);
	private static final int WEAPON_SWITCH_INTERVAL = 400;
	private int weaponSwitchTick;

	public CaptainCorneliaEntity(EntityType<CaptainCorneliaEntity> type, World world) {
		super(type, world);
		experiencePoints = 100;
		setPersistent();
		equipStack(EquipmentSlot.MAINHAND, getMainWeapon().getDefaultStack());
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(4, new SwimGoal(this));
		goalSelector.add(5, new LookAroundGoal(this));
		goalSelector.add(6, new WanderAroundFarGoal(this, 1.2));
		goalSelector.add(2, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getSquaredMaxAttackDistance(LivingEntity entity) {
				return 0;
			}
		});

		targetSelector.add(1, new RevengeGoal(this));
		targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false, false));
		targetSelector.add(3, new ActiveTargetGoal<>(this, IllagerEntity.class, false, false));
		targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, false, false));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(REGENERATION, 2);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		final NbtCompound data = new NbtCompound();
		data.putInt("Regeneration", dataTracker.get(REGENERATION));
		nbt.put("CorneliaData", data);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		final NbtCompound data = nbt.getCompound("CorneliaData");
		if (data.isEmpty()) return;
		dataTracker.set(REGENERATION, data.getInt("Regeneration"));
	}

	@Override
	public Optional<Animation> getAnimation(byte id) {
		return switch (id) {
			case 1 -> Optional.of(PULL_ATTACK);
			case 2 -> Optional.of(SWING_ATTACK);
			case 3 -> Optional.of(THRUST_ATTACK);
			case 4 -> Optional.of(SWITCH_WEAPON);
			case 5 -> Optional.of(DEATH);
			default -> Optional.empty();
		};
	}

	public boolean isAttacks() {
		return PULL_ATTACK.isPlaying() || SWING_ATTACK.isPlaying() || THRUST_ATTACK.isPlaying() || SWITCH_WEAPON.isPlaying();
	}

	@Override
	public void tick() {
		AnimationHelper.handleDeath(this, DEATH, 60);
		AnimationHelper.handle(PULL_ATTACK, SWING_ATTACK, THRUST_ATTACK, SWITCH_WEAPON, DEATH);
		if (PULL_ATTACK.hasPlayed() && getTarget() != null) SWING_ATTACK.play(this, 40);
		if (SWING_ATTACK.hasPlayed() && getTarget() != null) THRUST_ATTACK.play(this, 40);
		if (SWITCH_WEAPON.getTick() == 10) switchWeapon();
		SWING_ATTACK.sound(this, 1, () -> AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_1, SoundCategory.HOSTILE, 2f, 1f);
		THRUST_ATTACK.sound(this, 1, () -> AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_2, SoundCategory.HOSTILE, 2f, 1f);
		SWITCH_WEAPON.sound(this, 8, () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON, SoundCategory.HOSTILE, 1f, 1f);
		weaponSwitchTick++;

		if (isDead()) clearStatusEffects();
		if (getHealth() < getMaxHealth() * 0.25 && dataTracker.get(REGENERATION) > 0 && !hasStatusEffect(StatusEffects.LEVITATION)) rage();
		if (getHealth() <= 16 && !hasStatusEffect(StatusEffects.WEAKNESS)) heal(0.5F);

		if (getTarget() != null) {
			final LivingEntity target = getTarget();
			final double distance = squaredDistanceTo(target);
			modifyYRot(EntityUtils.getYAngleBetween(this, target), isOnGround() ? 0.15f : 0.3f);
			lookControl.lookAt(target);

			if (!isAttacks() && !getWorld().isClient()) {
				if (weaponSwitchTick > WEAPON_SWITCH_INTERVAL && random.nextFloat() < (weaponSwitchTick - WEAPON_SWITCH_INTERVAL) / 5000f) {
					SWITCH_WEAPON.play(this, 20);
					weaponSwitchTick = 0;
				} else if (distance < 3 || hasStatusEffect(StatusEffects.LEVITATION) || (distance < 25 && random.nextInt(100) == 1)) {
					if (random.nextBoolean()) SWING_ATTACK.play(this, 40);
					else THRUST_ATTACK.play(this, 40);
				} else if (distance < 600 && random.nextInt(200) == 1) PULL_ATTACK.play(this, 30);
			}

			if (PULL_ATTACK.getTick() == 10) {
				target.setVelocity(target.getPos().relativize(getPos().add(0, 0.5, 0)).multiply(0.25F));
				if (target instanceof PlayerEntity player) player.velocityModified = true;
			}
			if (SWING_ATTACK.getTick() == 12 || THRUST_ATTACK.getTick() == 12)
				setVelocity(getPos().relativize(target.getPos().add(0, 0.1, 0)).multiply(0.2F));
			if (SWING_ATTACK.getTick() == 14 && distance < 9) EntityUtils.getRelativeEntities(this, LivingEntity.class,
					5f, 0f, 0f, 6f, true, true).forEach(e -> { if (e != this) tryAttack(e); });
			if (THRUST_ATTACK.getTick() == 14 && distance < 12) tryAttack(target);
			if (SWING_ATTACK.getTick() == 12)
				TextureFX.Builder.create(30)
						.owner(this)
						.texture(Aquamirae.MODID, "swing")
						.relativePos(this, 1f, 0f, -0.7f)
						.relativeRot(this, false, true)
						.yRot(-90f, 0, 0)
						.moveForward(0f, 3f, -0.2f)
						.scale(0.4f, 0.4f, -0.013f)
						.alpha(1f, -0.01f, -0.01f)
						.build(getWorld());

			if (isSubmergedInWater() && !hasStatusEffect(StatusEffects.LEVITATION)) setVelocity(getPos().relativize(target.getPos()).multiply(0.04F));
			else if (isTouchingWater() && !hasStatusEffect(StatusEffects.LEVITATION)) setVelocity(getPos().relativize(target.getPos()).multiply(0.04F).add(0F, 0.08F, 0F));
		}

		if (isAttacks()) addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 5, 3, false, false));

		if (hasStatusEffect(StatusEffects.LEVITATION)) {
			heal(1);
			if (!getWorld().isClient()) {
				List<LivingEntity> list = getWorld().getEntitiesByClass(LivingEntity.class, new Box(getPos(), getPos()).expand(10), e -> true);
				final StatusEffectInstance LEVITATION = getStatusEffect(StatusEffects.LEVITATION);
				final int DURATION = LEVITATION == null ? 0 : LEVITATION.getDuration();
				list.forEach(entity -> {
					if (entity instanceof PlayerEntity player && (player.isCreative() || player.isSpectator())) return;
					if (entity.getMaxHealth() <= 100) {
						if (DURATION > 1) {
							final double radius = 5;
							final Vec3d orbit = new Vec3d(getX() + Math.cos(entity.age * -0.1F) * radius, getY(),
									getZ() + Math.sin(entity.age * -0.1F) * radius);
							entity.setVelocity(entity.getPos().relativize(orbit).multiply(0.2F));
							if (entity instanceof PlayerEntity _player) _player.velocityModified = true;
						} else if (DURATION == 1) {
							entity.setVelocity(getPos().relativize(entity.getPos()).multiply(0.2F));
							if (entity instanceof PlayerEntity _player) _player.velocityModified = true;
						}
					}
				});

				if (getWorld() instanceof ServerWorld server && age % 2 == 0)
					server.spawnParticles(AquamiraeParticles.GHOST, getX(), getY() - 0.2, getZ(),
							1, 0.3, 0.1, 0.3, 0.1);
				if (isTouchingWater()) setVelocity(new Vec3d(0F, 0.4F, 0F));
			}
		}

		if (getWorld() instanceof ServerWorld server && age % 10 == 0)
			server.spawnParticles(AquamiraeParticles.GHOST_SHINE, getX(), getY() + 1.7, getZ(), 1,
					0.15, 0.1, 0.15, 0.1);

		if (getWorld().isClient()) {
			final Vec3d center = getPos();
			getWorld().getEntitiesByClass(PlayerEntity.class, new Box(center, center).expand(32), e -> true).forEach(AquamiraeClient::playCorneliaMusic);
		}
		super.tick();
	}

	private void modifyYRot(float rotation, float mod) {
		prevYaw = getYaw();
		setYaw(getYaw() + (rotation - getYaw()) * mod);
	}

	public Item getMainWeapon() {
		return Aquamirae.isWinterEvent() ? AquamiraeItems.SWEET_LANCE : AquamiraeItems.CORAL_LANCE;
	}

	public void switchWeapon() {
		final LivingEntity target = getTarget();
		final Item current = getMainHandStack().getItem();
		final Item main = getMainWeapon();
		final Item armor = AquamiraeItems.WHISPER_OF_THE_ABYSS;
		final Item health = AquamiraeItems.DIVIDER;
		final Item poison = AquamiraeItems.POISONED_BLADE;
		final Item heal = AquamiraeItems.REMNANTS_SABER;
		final List<Item> weapons =  new ArrayList<>();
		if (!current.equals(main)) add(weapons, main, 10);
		if (!current.equals(poison)) add(weapons, poison, 10);
		if (!current.equals(health)) add(weapons, health, target == null ? 1 : (int) (target.getHealth() / 5f) + (target.getHealth() >= 200 ? 1000 : 0));
		if (!current.equals(armor)) add(weapons, armor, target == null ? 1 : target.getArmor());
		if (!current.equals(heal)) add(weapons, heal, (int) (getMaxHealth() - getMaxHealth() / 5f) + (getHealth() <= 20 ? 1000 : 0));

		if (weapons.isEmpty()) return;
		final Item item = weapons.get(random.nextBetween(0, Math.max(1, weapons.size() - 1)));
		equipStack(EquipmentSlot.MAINHAND, item.getDefaultStack());
		equipStack(EquipmentSlot.OFFHAND, item instanceof DividerItem || item instanceof PoisonedBladeItem || item instanceof RemnantsSaberItem
						? item.getDefaultStack() : ItemStack.EMPTY);
	}

	private void add(List<Item> list, Item item, int times) {
		for (int i = 1; i <= times; i++) list.add(item);
	}

	public void rage() {
		dataTracker.set(REGENERATION, dataTracker.get(REGENERATION) - 1);
		addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200, 0, false, false));
		if (getWorld() instanceof ServerWorld serverLevel) {
			final BlockPos pos = getBlockPos();
			LightningEntity entityToSpawn = new LightningEntity(EntityType.LIGHTNING_BOLT, serverLevel);
			entityToSpawn.setPosition(Vec3d.ofCenter(pos));
			entityToSpawn.setCosmetic(true);
			serverLevel.spawnEntity(entityToSpawn);
			serverLevel.playSound(null, pos, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN, SoundCategory.HOSTILE, 3, 1);
			serverLevel.playSound(null, pos, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_RAGE, SoundCategory.HOSTILE, 4, 1);
			final var type = this.random.nextBoolean() ? AquamiraeEntities.POISONED_CHAKRA : AquamiraeEntities.MAZE_ROSE;
			DynamicProjectileEntity.create(type, this, getWorld(), null, 5, 0F, 600, 1000);
			DynamicProjectileEntity.create(type, this, getWorld(), null, 5, 0.33F, 600, 1000);
			DynamicProjectileEntity.create(type, this, getWorld(), null, 5, 0.66F, 600, 1000);
		}
	}

	@Override
	public boolean tryAttack(Entity entity) {
		if (entity instanceof LivingEntity living) {
			final Item item = getMainHandStack().getItem();
			if (item instanceof PoisonedBladeItem) {
				living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 2));
				return super.tryAttack(entity);
			} else if (item instanceof WhisperOfTheAbyssItem) {
				living.setAttacker(this);
				return tryAttack(living, getDamageSources().magic(), 8);
			} else if (item instanceof DividerItem) {
				living.setAttacker(this);
				return tryAttack(living, getDamageSources().magic(), Math.max(4, living.getHealth() / 2f));
			} else if (item instanceof RemnantsSaberItem) {
				heal(Math.min(30, living.getMaxHealth() * 0.4f));
				return super.tryAttack(entity);
			}
		}
		return super.tryAttack(entity);
	}

	private boolean tryAttack(LivingEntity entity, DamageSource source, float amount) {
		final boolean hurt = entity.damage(source, amount);
		if (hurt) {
			final float knockback = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
			if (knockback > 0.0F) {
				entity.takeKnockback(knockback * 0.5F,
						MathHelper.sin(getYaw() * ((float)Math.PI / 180F)),
						-MathHelper.cos(getYaw() * ((float)Math.PI / 180F)));
				setVelocity(getVelocity().multiply(0.6D, 1.0D, 0.6D));
			}
			this.applyDamageEffects(this, entity);
			entity.setAttacker(this);
		}
		return hurt;
	}

	@Override
	protected void dropInventory() {
		if (getWorld() instanceof ServerWorld server) {
			if (Math.random() <= 0.2F) {
				final ItemEntity item = new ItemEntity(EntityType.ITEM, server);
				item.setStack(getMainWeapon().getDefaultStack());
				item.setPosition(getPos());
				server.spawnEntity(item);
			}
			final ItemStack map = Aquamirae.createStructureMap(Aquamirae.SHELTER, server, this);
			if (!map.isEmpty()) {
				final ItemEntity item = new ItemEntity(EntityType.ITEM, server);
				item.setStack(map);
				item.setPosition(getPos());
				server.spawnEntity(item);
			}
		}
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource source) {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_DEATH;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (source.getAttacker() instanceof ArrowEntity) return false;
		if (source.isOf(DamageTypes.FALL)) return false;
		if (source.isOf(DamageTypes.CACTUS)) return false;
		if (source.isOf(DamageTypes.DROWN)) return false;
		if (source.isOf(DamageTypes.LIGHTNING_BOLT)) return false;
		if (source.isOf(DamageTypes.EXPLOSION)) return false;
		if (source.isOf(DamageTypes.TRIDENT)) return false;
		return super.damage(source, amount);
	}

	@SuppressWarnings("all")
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		world.playSound(null, getBlockPos(), AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN, SoundCategory.HOSTILE, 3, 1);
		addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 120, 0, false, false));
		//Aquamirae.setAttribute(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.corneliaMovementSpeed.get());
		//Aquamirae.setAttribute(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.corneliaMaxHealth.get());
		//Aquamirae.setAttribute(this, Attributes.ARMOR, AquamiraeConfig.Common.corneliaArmor.get());
		//Aquamirae.setAttribute(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.corneliaAttackDamage.get());
		//Aquamirae.setAttribute(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.corneliaFollowRange.get());
		//Aquamirae.setAttribute(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.corneliaAttackKnockback.get());
		//Aquamirae.setAttribute(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.corneliaKnockbackResistance.get());
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	@Override
	public boolean canUsePortals() {
		return false;
	}

	@Override
	public void onStartedTrackingBy(ServerPlayerEntity player) {
		super.onStartedTrackingBy(player);
		bossInfo.addPlayer(player);
	}

	@Override
	public void onStoppedTrackingBy(ServerPlayerEntity player) {
		super.onStoppedTrackingBy(player);
		bossInfo.removePlayer(player);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (!getWorld().isClient())
			bossInfo.setPercent(getHealth() / getMaxHealth());
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				//.add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1.1)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, AquamiraeConfig.DEFAULT_CORNELIA_MOVEMENT_SPEED)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, AquamiraeConfig.DEFAULT_CORNELIA_MAX_HEALTH)
				.add(EntityAttributes.GENERIC_ARMOR, AquamiraeConfig.DEFAULT_CORNELIA_ARMOR)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_CORNELIA_ATTACK_DAMAGE)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, AquamiraeConfig.DEFAULT_CORNELIA_FOLLOW_RANGE)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_CORNELIA_KNOCKBACK_RESISTANCE)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_CORNELIA_ATTACK_KNOCKBACK);
	}
}
