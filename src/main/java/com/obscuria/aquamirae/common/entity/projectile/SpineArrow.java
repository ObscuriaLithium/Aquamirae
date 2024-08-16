package com.obscuria.aquamirae.common.entity.projectile;

import com.google.common.base.Suppliers;
import com.obscuria.aquamirae.registry.AquamiraeEntityTypes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class SpineArrow extends AbstractArrow {
    private static final Supplier<ItemStack> DEFAULT_ARROW_STACK = Suppliers.memoize(
            () -> AquamiraeItems.SPINE_ARROW.get().getDefaultInstance());

    public SpineArrow(EntityType<? extends SpineArrow> type, Level level) {
        super(type, level, DEFAULT_ARROW_STACK.get());
        this.setBaseDamage(4);
    }

    public SpineArrow(Level level, LivingEntity entity, ItemStack stack) {
        super(AquamiraeEntityTypes.SPINE_ARROW.get(), entity, level, stack);
        this.setBaseDamage(4);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isInWater() && !this.level().isClientSide) {
            this.setDeltaMovement(this.getDeltaMovement()
                    .xRot(-0.1f + 0.2f * this.random.nextFloat())
                    .yRot(-0.1f + 0.2f * this.random.nextFloat())
                    .zRot(-0.1f + 0.2f * this.random.nextFloat()));
            this.hurtMarked = true;
        }
        this.makeParticles();
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
    }

    private void makeParticles() {
        if (!this.level().isClientSide || this.inGround) return;
        this.level().addParticle(ParticleTypes.ENTITY_EFFECT,
                this.getRandomX(0.25), this.getY(), this.getRandomZ(0.25),
                0.3, 1.0, 0.3);
    }
}
