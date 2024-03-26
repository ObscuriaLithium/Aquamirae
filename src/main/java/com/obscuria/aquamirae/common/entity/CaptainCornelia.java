
package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeClient;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.common.item.weapon.Divider;
import com.obscuria.aquamirae.common.item.weapon.PoisonedBlade;
import com.obscuria.aquamirae.common.item.weapon.RemnantsSaber;
import com.obscuria.aquamirae.common.item.weapon.WhisperOfTheAbyss;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.api.animation.entity.EntityAnimations;
import com.obscuria.core.api.animation.entity.IAnimatedEntity;
import com.obscuria.core.api.extension.screen.StyledBossEvent;
import com.obscuria.core.api.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@IceMazeEntity
public class CaptainCornelia extends Monster implements IAnimatedEntity {
	private static final EntityDataAccessor<Integer> REGENERATION = SynchedEntityData.defineId(CaptainCornelia.class,
			EntityDataSerializers.INT);
	private final EntityAnimations<CaptainCornelia> animations = EntityAnimations.create(this);
	private final StyledBossEvent bossEvent;
	public final String PULL_ATTACK = "pull_attack";
	public final String SWING_ATTACK = "swing_attack";
	public final String THRUST_ATTACK = "thrust_attack";
	public final String SWITCH_WEAPON = "switch_weapon";
	public final String DEATH = "death";
	private static final int WEAPON_SWITCH_INTERVAL = 400;
	private int weaponSwitchTick;
	private int particle1;
	private int particle2;

	public CaptainCornelia(EntityType<CaptainCornelia> type, Level world) {
		super(type, world);
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, this.getMainWeapon().getDefaultInstance());

		this.xpReward = 100;
		this.bossEvent = createBossEvent();
	}

	protected StyledBossEvent createBossEvent() {
		return StyledBossEvent.create(this, Aquamirae.key("captain_cornelia"), ServerBossEvent.BossBarColor.BLUE);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new FloatGoal(this));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.2));
		super.registerGoals();
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllager.class, false, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false, false));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(REGENERATION, AquamiraeConfig.Common.corneliaRegenerationAbility.get());
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		CompoundTag data = new CompoundTag();
		data.putInt("Regeneration", this.getEntityData().get(REGENERATION));
		tag.put("CorneliaData", data);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		CompoundTag data = (CompoundTag) tag.get("CorneliaData");
		if (data == null) return;
		this.getEntityData().set(REGENERATION, data.getInt("Regeneration"));
	}

	@Override
	public EntityAnimations<CaptainCornelia> getAnimations() {
		return animations;
	}

	public boolean isAttacks() {
		return animations.isAnyPlaying(PULL_ATTACK, SWING_ATTACK, THRUST_ATTACK, SWITCH_WEAPON);
	}

	@Override
	public void baseTick() {
		if (tickCount % 4 == 0) bossEvent.update();

		//AnimationHelper.handleDeath(this, DEATH, 60);
		//AnimationHelper.handle(PULL_ATTACK, SWING_ATTACK, THRUST_ATTACK, SWITCH_WEAPON, DEATH);
//		if (animations.isAnyPlaying() PULL_ATTACK.hasPlayed() && this.getTarget() != null) SWING_ATTACK.play(this, 40);
//		if (SWING_ATTACK.hasPlayed() && this.getTarget() != null) THRUST_ATTACK.play(this, 40);
//		if (SWITCH_WEAPON.getTick() == 10) this.switchWeapon();
		animations.playSound(SWING_ATTACK, 1, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_1.get(), 2f, 1f);
		animations.playSound(THRUST_ATTACK, 1, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_2.get(), 2f, 1f);
		animations.playSound(SWITCH_WEAPON, 8, SoundEvents.ARMOR_EQUIP_IRON, 1f, 1f);
		this.weaponSwitchTick++;

		if (this.isDeadOrDying()) this.removeAllEffects();
		if (this.getHealth() < this.getMaxHealth() * 0.25 && this.getEntityData().get(REGENERATION) > 0 && !this.hasEffect(MobEffects.LEVITATION)) this.rage();
		if (this.getHealth() <= 16 && !this.hasEffect(MobEffects.WEAKNESS)) this.heal(0.5F);

		if (this.getTarget() != null) {
			final LivingEntity target = this.getTarget();
			final double distance = this.distanceToSqr(target);
			this.modifyYRot(EntityUtil.getYAngleBetween(this, target), this.onGround() ? 0.15f : 0.3f);
			this.lookControl.setLookAt(target);

			if (!this.isAttacks() && !this.level().isClientSide) {
				if (this.weaponSwitchTick > WEAPON_SWITCH_INTERVAL
						&& random.nextFloat() < (this.weaponSwitchTick - WEAPON_SWITCH_INTERVAL) / 5000f) {
					animations.play(SWITCH_WEAPON);
					this.weaponSwitchTick = 0;
				} else if (distance < 3 || this.hasEffect(MobEffects.LEVITATION) || (distance < 25 && random.nextInt(100) == 1)) {
					if (random.nextBoolean()) animations.play(SWING_ATTACK);
					else animations.play(THRUST_ATTACK);
				} else if (distance < 600 && random.nextInt(200) == 1) animations.play(PULL_ATTACK);
			}

			if (animations.getTick(PULL_ATTACK) == 10) {
				target.setDeltaMovement(new Vec3(target.getX(), target.getY(), target.getZ())
						.vectorTo(new Vec3(this.getX(), this.getY() + 0.5F, this.getZ())).scale(0.25F));
				if (target instanceof Player player) player.hurtMarked = true;
			}
			if (animations.getTick(SWING_ATTACK) == 12 || animations.getTick(THRUST_ATTACK) == 12)
				this.setDeltaMovement(new Vec3(this.getX(), this.getY(), this.getZ())
						.vectorTo(new Vec3(target.getX(), target.getY() + 0.1F, target.getZ())).scale(0.2F));
//			if (animations.getTick(SWING_ATTACK) == 14 && distance < 9) TargetUtil.getRelativeEntities(this, LivingEntity.class,
//					5f, 0f, 0f, 6f, true, true).forEach(e -> { if (e != this) this.doHurtTarget(e); });
			if (animations.getTick(THRUST_ATTACK) == 14 && distance < 12) this.doHurtTarget(target);
//			if (this.SWING_ATTACK.getTick() == 12)
//				TextureFX.Builder.create(30)
//						.owner(this)
//						.texture(Aquamirae.MODID, "swing")
//						.relativePos(this, 1f, 0f, -0.7f)
//						.relativeRot(this, false, true)
//						.yRot(-90f, 0, 0)
//						.moveForward(0f, 3f, -0.2f)
//						.scale(0.4f, 0.4f, -0.013f)
//						.alpha(1f, -0.01f, -0.01f)
//						.build(this.level());

			if (this.isUnderWater() && !this.hasEffect(MobEffects.LEVITATION)) this.setDeltaMovement(new Vec3(this.getX(), this.getY(), this.getZ())
					.vectorTo(new Vec3(target.getX(), target.getY(), target.getZ())).scale(0.04F));
			else if (this.isInWater() && !this.hasEffect(MobEffects.LEVITATION)) this.setDeltaMovement(new Vec3(this.getX(), this.getY(), this.getZ())
					.vectorTo(new Vec3(target.getX(), target.getY(), target.getZ())).scale(0.04F).add(0F, 0.08F, 0F));
		}

		if (this.isAttacks())
			this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 3, false, false));

		if (this.hasEffect(MobEffects.LEVITATION)) {
			this.heal(1);
			if (AquamiraeConfig.Common.corneliaSpinAbility.get() && !this.level().isClientSide()) {
				final Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(10), e -> true).stream()
						.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).toList();
				final MobEffectInstance LEVITATION = this.getEffect(MobEffects.LEVITATION);
				final int DURATION = LEVITATION == null ? 0 : LEVITATION.getDuration();
				list.forEach(entity -> {
					if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) return;
					if (entity.getMaxHealth() <= 100) {
						if (DURATION > 1) {
							final double radius = 5;
							final Vec3 orbit = new Vec3(center.x + Math.cos(entity.tickCount * -0.1F) * radius, center.y,
									center.z + Math.sin(entity.tickCount * -0.1F) * radius);
							entity.setDeltaMovement(new Vec3(entity.getX(), entity.getY(), entity.getZ()).vectorTo(orbit).scale(0.2F));
							if (entity instanceof Player _player) _player.hurtMarked = true;
						} else if (DURATION == 1) {
							entity.setDeltaMovement(new Vec3(this.getX(), this.getY(), this.getZ())
									.vectorTo(new Vec3(entity.getX(), entity.getY(), entity.getZ())).scale(0.2F));
							if (entity instanceof Player _player) _player.hurtMarked = true;
						}
					}
				});

				this.particle1++;
				if (this.level() instanceof ServerLevel server && this.particle1 > 1) {
					this.particle1 = 0;
					server.sendParticles(AquamiraeParticles.GHOST.get(), this.getX(), this.getY() - 0.2, this.getZ(), 1,
							0.3, 0.1, 0.3, 0.1);
				}
				if (this.isInWater()) this.setDeltaMovement(new Vec3(0F, 0.4F, 0F));
			}
		}
		this.particle2++;
		if (this.level() instanceof ServerLevel server && this.particle2 > 9) {
			this.particle2 = 0;
			server.sendParticles(AquamiraeParticles.GHOST_SHINE.get(), this.getX(), this.getY() + 1.7, this.getZ(), 1,
					0.15, 0.1, 0.15, 0.1);
		}

		if (this.level().isClientSide)
			AquamiraeClient.playCorneliaMusic(this);
		super.baseTick();
	}

	private void modifyYRot(float rotation, float mod) {
		this.yRotO = this.getYRot();
		this.setYRot(this.getYRot() + (rotation - this.getYRot()) * mod);
	}

	public Item getMainWeapon() {
		return Aquamirae.isWinterEvent() ? AquamiraeItems.SWEET_LANCE.get() : AquamiraeItems.CORAL_LANCE.get();
	}

	public void switchWeapon() {
		final LivingEntity target = this.getTarget();
		final Item current = this.getMainHandItem().getItem();
		final Item main = this.getMainWeapon();
		final Item armor = AquamiraeItems.WHISPER_OF_THE_ABYSS.get();
		final Item health = AquamiraeItems.DIVIDER.get();
		final Item poison = AquamiraeItems.POISONED_BLADE.get();
		final Item heal = AquamiraeItems.REMNANTS_SABER.get();
		final List<Item> weapons =  new ArrayList<>();
		if (!current.equals(main)) this.add(weapons, main, 10);
		if (!current.equals(poison)) this.add(weapons, poison, 10);
		if (!current.equals(health)) this.add(weapons, health, target == null ? 1 : (int) (target.getHealth() / 5f) + (target.getHealth() >= 200 ? 1000 : 0));
		if (!current.equals(armor)) this.add(weapons, armor, target == null ? 1 : target.getArmorValue());
		if (!current.equals(heal)) this.add(weapons, heal, (int) (this.getMaxHealth() - this.getMaxHealth() / 5f) + (this.getHealth() <= 20 ? 1000 : 0));

		if (weapons.isEmpty()) return;
		final Item item = weapons.get(random.nextInt(0, Math.max(1, weapons.size() - 1)));
		this.setItemSlot(EquipmentSlot.MAINHAND, item.getDefaultInstance());
		this.setItemSlot(EquipmentSlot.OFFHAND, item instanceof Divider || item instanceof PoisonedBlade || item instanceof RemnantsSaber
						? item.getDefaultInstance() : ItemStack.EMPTY);
	}

	private void add(List<Item> list, Item item, int times) {
		for (int i = 1; i <= times; i++) list.add(item);
	}

	public void rage() {
		this.getEntityData().set(REGENERATION, this.getEntityData().get(REGENERATION) - 1);
		this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200, 0, false, false));
		if (this.level() instanceof ServerLevel serverLevel) {
			final BlockPos pos = this.blockPosition();
			LightningBolt entityToSpawn = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
			entityToSpawn.moveTo(Vec3.atBottomCenterOf(pos));
			entityToSpawn.setVisualOnly(true);
			serverLevel.addFreshEntity(entityToSpawn);
			serverLevel.playSound(null, pos, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN.get(), SoundSource.HOSTILE, 3, 1);
			serverLevel.playSound(null, pos, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_RAGE.get(), SoundSource.HOSTILE, 4, 1);
			final var type = this.random.nextBoolean() ? AquamiraeEntities.POISONED_CHAKRA.get() : AquamiraeEntities.MAZE_ROSE.get();
//			CompoundProjectileEntity.create(type, this, this.level(), null, 5, 0F, 600, 1000);
//			CompoundProjectileEntity.create(type, this, this.level(), null, 5, 0.33F, 600, 1000);
//			CompoundProjectileEntity.create(type, this, this.level(), null, 5, 0.66F, 600, 1000);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (entity instanceof LivingEntity living) {
			final Item item = this.getMainHandItem().getItem();
			if (item instanceof PoisonedBlade) {
				living.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 2));
				return super.doHurtTarget(entity);
			} else if (item instanceof WhisperOfTheAbyss) {
				living.setLastHurtByMob(this);
				return this.doHurtTarget(living, this.damageSources().magic(), 8);
			} else if (item instanceof Divider) {
				living.setLastHurtByMob(this);
				return this.doHurtTarget(living, this.damageSources().magic(), Math.max(4, living.getHealth() / 2f));
			} else if (item instanceof RemnantsSaber) {
				this.heal(Math.min(30, living.getMaxHealth() * 0.4f));
				return super.doHurtTarget(entity);
			}
		}
		return super.doHurtTarget(entity);
	}

	private boolean doHurtTarget(LivingEntity entity, DamageSource source, float amount) {
		final boolean hurt = entity.hurt(source, amount);
		if (hurt) {
			final float knockback = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
			if (knockback > 0.0F) {
				(entity).knockback(knockback * 0.5F, Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)));
				this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
			}
			this.doEnchantDamageEffects(this, entity);
			this.setLastHurtMob(entity);
		}
		return hurt;
	}

	@Override
	protected void dropEquipment() {
		if (this.level() instanceof ServerLevel server) {
			if (Math.random() <= 0.2F) {
				final ItemEntity item = new ItemEntity(EntityType.ITEM, server);
				item.setItem(this.getMainWeapon().getDefaultInstance());
				item.moveTo(this.position());
				server.addFreshEntity(item);
			}
//			final ItemStack map = Aquamirae.getStructureMap(Aquamirae.SHELTER, server, this);
//			if (!map.isEmpty()) {
//				final ItemEntity item = new ItemEntity(EntityType.ITEM, server);
//				item.setItem(map);
//				item.moveTo(this.position());
//				server.addFreshEntity(item);
//			}
		}
	}

	@Override
	public @NotNull MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_AMBIENT.get();
	}

	@Override
	public SoundEvent getHurtSound(@NotNull DamageSource source) {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HURT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_DEATH.get();
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof AbstractArrow) return false;
		if (source.is(DamageTypes.FALL)) return false;
		if (source.is(DamageTypes.CACTUS)) return false;
		if (source.is(DamageTypes.DROWN)) return false;
		if (source.is(DamageTypes.LIGHTNING_BOLT)) return false;
		if (source.is(DamageTypes.EXPLOSION)) return false;
		if (source.is(DamageTypes.TRIDENT)) return false;
		return super.hurt(source, amount);
	}

	@Override
	@SuppressWarnings("deprecation")
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		if (this.level() instanceof ServerLevel serverLevel) serverLevel.playSound(null, this.blockPosition(),
					AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN.get(), SoundSource.HOSTILE, 3, 1);
		this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 120, 0, false, false));
		Aquamirae.setAttribute(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.corneliaMovementSpeed.get());
		Aquamirae.setAttribute(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.corneliaMaxHealth.get());
		Aquamirae.setAttribute(this, Attributes.ARMOR, AquamiraeConfig.Common.corneliaArmor.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.corneliaAttackDamage.get());
		Aquamirae.setAttribute(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.corneliaFollowRange.get());
		Aquamirae.setAttribute(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.corneliaAttackKnockback.get());
		Aquamirae.setAttribute(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.corneliaKnockbackResistance.get());
		return livingdata;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public void startSeenByPlayer(@NotNull ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossEvent.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(@NotNull ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossEvent.removePlayer(player);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1.1)
				.add(Attributes.MOVEMENT_SPEED, AquamiraeConfig.DEFAULT_CORNELIA_MOVEMENT_SPEED)
				.add(Attributes.MAX_HEALTH, AquamiraeConfig.DEFAULT_CORNELIA_MAX_HEALTH)
				.add(Attributes.ARMOR, AquamiraeConfig.DEFAULT_CORNELIA_ARMOR)
				.add(Attributes.ATTACK_DAMAGE, AquamiraeConfig.DEFAULT_CORNELIA_ATTACK_DAMAGE)
				.add(Attributes.FOLLOW_RANGE, AquamiraeConfig.DEFAULT_CORNELIA_FOLLOW_RANGE)
				.add(Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.DEFAULT_CORNELIA_KNOCKBACK_RESISTANCE)
				.add(Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.DEFAULT_CORNELIA_ATTACK_KNOCKBACK);
	}
}
