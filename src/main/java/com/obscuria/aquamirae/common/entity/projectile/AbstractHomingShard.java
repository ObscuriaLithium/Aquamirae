package com.obscuria.aquamirae.common.entity.projectile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public abstract class AbstractHomingShard extends AbstractShard {
    protected static final EntityDataAccessor<Float> HOMING_STRENGTH =
            SynchedEntityData.defineId(AbstractHomingShard.class, EntityDataSerializers.FLOAT);

    public AbstractHomingShard(EntityType<? extends AbstractHomingShard> type, Level level) {
        super(type, level);
    }


    public void setHomingStrength(float value) {
        if (this.getHomingStrength() == value) return;
        this.entityData.set(HOMING_STRENGTH, value);
    }

    public float getHomingStrength() {
        return this.entityData.get(HOMING_STRENGTH);
    }

    protected abstract int getHomingRadius();

    protected boolean canTargetEntity(Entity entity) {
        return entity != this.getOwner()
                && entity instanceof LivingEntity living
                && living.hasLineOfSight(this)
                && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(living);
    }

    protected @Nullable LivingEntity findTarget() {
        final var area = new AABB(this.position(), this.position()).inflate(this.getHomingRadius());
        return this.level().getEntitiesOfClass(LivingEntity.class, area).stream()
                .filter(this::canHitEntity)
                .min(Comparator.comparingDouble(entity -> entity.getEyePosition().distanceToSqr(this.position())))
                .orElse(null);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide || this.tickCount % 30 != 0) return;
        this.markHurt();
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity)
                && this.canTargetEntity(entity);
    }

    @Override
    protected Vec3 modifyMotion(Vec3 motion) {
        final var target = this.findTarget();
        if (target != null) {
            final var direction = this.position().vectorTo(target.getEyePosition()).normalize();
            motion = motion.lerp(direction, this.getHomingStrength());
        }
        return motion;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HOMING_STRENGTH, 0f);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("HomingStrength", this.entityData.get(HOMING_STRENGTH));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(HOMING_STRENGTH, tag.getFloat("HomingStrength"));
    }
}
