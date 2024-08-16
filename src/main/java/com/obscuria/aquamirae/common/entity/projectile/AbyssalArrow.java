package com.obscuria.aquamirae.common.entity.projectile;

import com.google.common.base.Suppliers;
import com.obscuria.aquamirae.registry.AquamiraeEntityTypes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

public class AbyssalArrow extends AbstractArrow {
    private static final Supplier<ItemStack> DEFAULT_ARROW_STACK = Suppliers.memoize(
            () -> AquamiraeItems.ABYSSAL_ARROW.get().getDefaultInstance());

    public AbyssalArrow(EntityType<? extends AbyssalArrow> type, Level level) {
        super(type, level, DEFAULT_ARROW_STACK.get());
        this.setBaseDamage(2);
    }

    public AbyssalArrow(Level level, LivingEntity entity, ItemStack stack) {
        super(AquamiraeEntityTypes.ABYSSAL_ARROW.get(), entity, level, stack);
        this.setBaseDamage(2);
    }

    protected void makeBreakParticles() {
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(
                    new ItemParticleOption(ParticleTypes.ITEM, AbyssalShard.PARTICLE_ITEM.get()),
                    this.getX(), this.getY(), this.getZ(),
                    8, 0, 0, 0, 0.15);
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.AMETHYST_BLOCK_BREAK;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        final var random = this.random;
        final var origin = this.position();
        final var amount = this.random.nextInt(2, 5);
        for (var i = 0; i < amount; i++) {
            final var motion = this.getDeltaMovement()
                    .reverse().normalize()
                    .scale(random.triangle(0.3, 0.1))
                    .xRot((float) random.triangle(0, 0.5))
                    .yRot((float) random.triangle(0, 0.5))
                    .zRot((float) random.triangle(0, 0.5));
            AbyssalShard.create(this.level(), this.getOwner(), origin, motion);
        }
        this.makeBreakParticles();
        this.discard();
    }
}
