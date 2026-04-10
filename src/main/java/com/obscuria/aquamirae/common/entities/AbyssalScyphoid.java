package com.obscuria.aquamirae.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@ShipGraveyardEntity
public class AbyssalScyphoid extends WaterAnimal {

    private static final EntityDataAccessor<Float> DATA_VARIANT_ID;
    private static final String TAG_VARIANT = "Variant";

    public AbyssalScyphoid(EntityType<? extends AbyssalScyphoid> type, Level level) {
        super(type, level);
    }

    public float getVariant() {
        return entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(float variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    public boolean isSquishTick() {
        var variant = getVariant();
        var cycle = 20 + 60 * variant;
        var offset = cycle * 0.05f;
        return (int) ((tickCount + 1000 * variant) % cycle) == (int) offset;
    }

    @Override
    public void travel(Vec3 motion) {
        // No movement
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide && isSquishTick()) {
            level().playLocalSound(this.blockPosition(),
                    SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
                    SoundSource.HOSTILE,
                    3, 2f - getVariant(), false);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat(TAG_VARIANT, this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(TAG_VARIANT, Tag.TAG_FLOAT))
            setVariant(tag.getFloat(TAG_VARIANT));
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (super.hurt(source, damage)) {
            doPoison(source.getDirectEntity());
            return true;
        }
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT_ID, 1f);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.COD_HURT;
    }

    @Override
    protected void doPush(Entity entity) {
        doPoison(entity);
    }

    private void doPoison(@Nullable Entity entity) {
        if (!(entity instanceof LivingEntity living)) return;
        if (living instanceof AbyssalScyphoid) return;
        if (living instanceof Player player && (player.isCreative() || player.isSpectator())) return;
        if (living.hasEffect(MobEffects.POISON)) return;
        living.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 2));
        living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
        living.level().playSound(null, this, SoundEvents.SQUID_SQUIRT, SoundSource.HOSTILE, 0.5f, 1);
    }

    private float randomizeVariant() {
        return (float) this.random.triangle(1.0, 0.75);
    }

    public static boolean checkSpawnRules(
            EntityType<AbyssalScyphoid> type, ServerLevelAccessor levelAccessor,
            MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return WaterAnimal.checkSurfaceWaterAnimalSpawnRules(type, levelAccessor, spawnType, pos, random);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @Nullable SpawnGroupData finalizeSpawn(
            ServerLevelAccessor levelAccessor, DifficultyInstance difficulty, MobSpawnType type,
            @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        this.setVariant(randomizeVariant());
        Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).setBaseValue(Math.round(16 * getVariant()));
        return super.finalizeSpawn(levelAccessor, difficulty, type, groupData, tag);
    }

    static {
        DATA_VARIANT_ID = SynchedEntityData.defineId(AbyssalScyphoid.class, EntityDataSerializers.FLOAT);
    }
}
