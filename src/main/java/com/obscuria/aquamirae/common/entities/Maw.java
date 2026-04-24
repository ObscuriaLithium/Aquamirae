
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

@ShipGraveyardEntity
public class Maw extends Monster implements IAnimated {

    private static final ResourceLocation RANDOM_ITEM_TABLE = Aquamirae.identifier("entities/maw_random_item");
    private static final EntityDataAccessor<ItemStack> MOUTH_ITEM = SynchedEntityData.defineId(Maw.class, EntityDataSerializers.ITEM_STACK);
    private static final String MOUTH_ITEM_TAG = "MouthItem";

    public final Animation attackAnimation = new Animation(1);
    public final Animation deathAnimation = new Animation(2);

    public Maw(EntityType<Maw> type, Level world) {
        super(type, world);
        this.xpReward = 10;
    }

    public void setMouthItem(ItemStack stack) {
        this.getEntityData().set(MOUTH_ITEM, stack);
    }

    public ItemStack getMouthItem() {
        return this.getEntityData().get(MOUTH_ITEM);
    }

    @Override
    public Optional<Animation> getAnimation(byte id) {
        return switch (id) {
            case 1 -> Optional.of(attackAnimation);
            case 2 -> Optional.of(deathAnimation);
            default -> Optional.empty();
        };
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.put(MOUTH_ITEM_TAG, this.getMouthItem().save(new CompoundTag()));
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains(MOUTH_ITEM_TAG, CompoundTag.TAG_COMPOUND))
            this.setMouthItem(ItemStack.of(tag.getCompound(MOUTH_ITEM_TAG)));
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void tick() {
        AnimationHelper.handleDeath(this, deathAnimation, 40);
        AnimationHelper.handle(attackAnimation, deathAnimation);
        super.tick();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.GUARDIAN_FLOP, 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return AquamiraeSounds.ENTITY_DEEP_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AquamiraeSounds.ENTITY_DEEP_DEATH.get();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.DROWN)) return false;
        return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.attackAnimation.play(this, 20);
        return super.doHurtTarget(entity);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @Nullable SpawnGroupData finalizeSpawn(
            ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason,
            @Nullable SpawnGroupData data, @Nullable CompoundTag tag
    ) {
        Aquamirae.setBaseValue(this, ForgeMod.SWIM_SPEED.get(), AquamiraeConfig.Common.mawSwimSpeed.get());
        Aquamirae.setBaseValue(this, Attributes.MOVEMENT_SPEED, AquamiraeConfig.Common.mawSpeed.get());
        Aquamirae.setBaseValue(this, Attributes.MAX_HEALTH, AquamiraeConfig.Common.mawMaxHealth.get());
        Aquamirae.setBaseValue(this, Attributes.ARMOR, AquamiraeConfig.Common.mawArmor.get());
        Aquamirae.setBaseValue(this, Attributes.ATTACK_DAMAGE, AquamiraeConfig.Common.mawAttackDamage.get());
        Aquamirae.setBaseValue(this, Attributes.FOLLOW_RANGE, AquamiraeConfig.Common.mawFollowRange.get());
        Aquamirae.setBaseValue(this, Attributes.ATTACK_KNOCKBACK, AquamiraeConfig.Common.mawAttackKnockback.get());
        Aquamirae.setBaseValue(this, Attributes.KNOCKBACK_RESISTANCE, AquamiraeConfig.Common.mawKnockbackResistance.get());
        this.randomizeItemInMouth(level);
        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(MOUTH_ITEM, ItemStack.EMPTY);
        super.defineSynchedData();
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        if (getMouthItem().isEmpty()) return;
        this.spawnAtLocation(getMouthItem());
        this.setMouthItem(ItemStack.EMPTY);
    }

    @Override
    protected void registerGoals() {
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

    private void randomizeItemInMouth(ServerLevelAccessor accessor) {
        if (!(accessor instanceof ServerLevel level)) return;
        var lootContext = new LootParams.Builder(level)
                .withParameter(LootContextParams.THIS_ENTITY, this)
                .withParameter(LootContextParams.ORIGIN, this.position())
                .create(LootContextParamSets.GIFT);
        var lootTable = level.getServer().getLootData().getLootTable(RANDOM_ITEM_TABLE);
        var items = lootTable.getRandomItems(lootContext);
        if (items.isEmpty()) return;
        this.setMouthItem(items.get(0));
    }

    @SuppressWarnings("deprecation")
    public static boolean checkSpawnRules(
            EntityType<Maw> type, ServerLevelAccessor levelAccessor,
            MobSpawnType spawnType, BlockPos pos, RandomSource random
    ) {
        return levelAccessor.getDifficulty() != Difficulty.PEACEFUL
                && pos.getY() < levelAccessor.getSeaLevel() + 4
                && levelAccessor.getBrightness(LightLayer.BLOCK, pos) <= 4;
    }

    public static AttributeSupplier.Builder createAttributes() {
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
