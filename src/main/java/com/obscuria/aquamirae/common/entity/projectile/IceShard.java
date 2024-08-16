package com.obscuria.aquamirae.common.entity.projectile;

import com.obscuria.aquamirae.registry.AquamiraeEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class IceShard extends AbstractHomingShard {

    public IceShard(EntityType<? extends IceShard> type, Level level) {
        super(type, level);
    }

    public static void create(Level level, @Nullable Entity owner, Vec3 origin, Vec3 motion) {
        final var shard = new IceShard(AquamiraeEntityTypes.ICE_SHARD.get(), level);
        shard.setOwner(owner);
        shard.setDeltaMovement(motion);
        shard.setPos(origin);
        level.addFreshEntity(shard);
    }

    @Override
    protected int getHomingRadius() {
        return 16;
    }

    @Override
    protected float getDamage(Entity entity) {
        return 2;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;
        this.setHomingStrength(0.1f * (float) Math.pow(Math.min(1, this.tickCount / 40.0), 3.0));
    }

    @Override
    protected void makeParticles() {
        if (!this.level().isClientSide
                || this.isInWaterOrBubble()
                || this.random.nextBoolean()) return;
        final var motion = this.getDeltaMovement();
        this.level().addParticle(ParticleTypes.WHITE_ASH,
                this.getX() + this.random.triangle(0, 0.25),
                this.getY() + 0.2 + this.random.triangle(0, 0.25),
                this.getZ() + this.random.triangle(0, 0.25),
                motion.x, motion.y, motion.z);
    }

    @Override
    protected boolean canTargetEntity(Entity entity) {
        return super.canTargetEntity(entity) && entity instanceof Monster;
    }

    @Override
    protected void postHitEffects(EntityHitResult result) {
        if (result.getEntity() instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
            living.setTicksFrozen(Math.min(living.getTicksRequiredToFreeze() * 2, living.getTicksFrozen() + 100));
        }
    }

    @Override
    protected void onBreak() {
        if (this.level() instanceof ServerLevel level) {
            level.playSound(this, this.blockPosition(),
                    SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.NEUTRAL,
                    1f, 1f);
            level.sendParticles(ParticleTypes.ITEM_SNOWBALL,
                    this.getX(), this.getY(), this.getZ(),
                    10, 0, 0, 0, 0);
        }
    }
}
