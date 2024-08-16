package com.obscuria.aquamirae.common.entity.projectile;

import com.google.common.base.Suppliers;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeEntityTypes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AbyssalShard extends AbstractHomingShard {
    static final Supplier<ItemStack> PARTICLE_ITEM = Suppliers.memoize(
            () -> AquamiraeItems.ABYSSAL_AMETHYST.get().getDefaultInstance());

    public AbyssalShard(EntityType<? extends AbyssalShard> type, Level level) {
        super(type, level);
    }

    public static void create(Level level, @Nullable Entity owner, Vec3 origin, Vec3 motion) {
        final var shard = new AbyssalShard(AquamiraeEntityTypes.ABYSSAL_SHARD.get(), level);
        shard.setShardScale((float) shard.random.triangle(1, 0.75));
        shard.setOwner(owner);
        shard.setDeltaMovement(motion);
        shard.setPos(origin);
        level.addFreshEntity(shard);
    }

    protected boolean isMonsterOrHostile(Entity entity) {
        return entity instanceof Monster
                || this.isHostile(entity);
    }

    protected boolean isHostile(Entity entity) {
        return this.getOwner() != null
                && entity instanceof Mob mob
                && mob.getTarget() == this.getOwner();
    }

    @Override
    protected int getHomingRadius() {
        return 16;
    }

    @Override
    protected float getDamage(Entity entity) {
        final var damage = 2.5f * this.getShardScale();
        if (Aquamirae.isIceMazeEntity(entity)) return damage * 3;
        return damage;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;
        this.setHomingStrength(0.1f * (float) Math.pow(Math.min(1, this.tickCount / (30.0 * this.getShardScale())), 3.0));
    }

    @Override
    protected void makeParticles() {
    }

    @Override
    protected boolean canTargetEntity(Entity entity) {
        return super.canTargetEntity(entity)
                && this.isMonsterOrHostile(entity);
    }

    @Override
    protected void postHitEffects(EntityHitResult result) {
        final var entity = result.getEntity();
        if (entity instanceof LivingEntity living)
            living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, 0));
    }

    @Override
    protected void onBreak() {
        if (this.level() instanceof ServerLevel level) {
            level.playSound(this, this.blockPosition(),
                    SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.NEUTRAL,
                    1f, 1f);
            level.sendParticles(
                    new ItemParticleOption(ParticleTypes.ITEM, PARTICLE_ITEM.get()),
                    this.getX(), this.getY(), this.getZ(),
                    8, 0, 0, 0, 0.15);
        }
    }
}
