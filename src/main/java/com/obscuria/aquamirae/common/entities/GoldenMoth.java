
package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@ShipGraveyardEntity
public class GoldenMoth extends PathfinderMob {

    public GoldenMoth(EntityType<GoldenMoth> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.xpReward = 10;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AquamiraeSounds.ENTITY_GOLDEN_MOTH_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof ThrownPotion) return false;
        if (source.getDirectEntity() instanceof AreaEffectCloud) return false;
        if (source.is(DamageTypes.FALL)) return false;
        if (source.is(DamageTypes.CACTUS)) return false;
        if (source.is(DamageTypes.DROWN)) return false;
        if (source.is(DamageTypes.LIGHTNING_BOLT)) return false;
        if (level() instanceof ServerLevel level) {
            level.sendParticles(AquamiraeParticleTypes.SHINE.get(), getX(), getY(), getZ(), 6, 0.05, 0.05, 0.05, 0.8);
        }
        return super.hurt(source, amount);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        super.mobInteract(player, hand);
        final ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == Items.GLASS_BOTTLE) {
            stack.shrink(1);
            if (!this.level().isClientSide()) {
                this.level().playSound(null, this.blockPosition(),
                        AquamiraeSounds.ENTITY_GOLDEN_MOTH_CATCH.get(), SoundSource.AMBIENT, 1, 1);
                var item = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(),
                        new ItemStack(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR.get()));
                item.setPickUpDelay(10);
                this.level().addFreshEntity(item);
                this.discard();
            }
        }
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    @Override
    public void tick() {
        if (level().isClientSide && tickCount % 4 == 0) {
            var motion = getDeltaMovement().scale(3);
            var particleType = AquamiraeParticleTypes.SHINE.get();
            level().addParticle(particleType,
                    getX() + random.triangle(0.0, 0.25),
                    getY() + random.triangle(0.0, 0.25),
                    getZ() + random.triangle(0.0, 0.25),
                    motion.x, motion.y, motion.z);
        }
        super.tick();
    }

    @Override
    public void baseTick() {
        if (isInWaterOrBubble()) {
            addDeltaMovement(new Vec3(0, 0.05, 0));
        }
        super.baseTick();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1, 20) {
            @Override
            protected Vec3 getPosition() {
                var random = GoldenMoth.this.getRandom();
                var dirX = GoldenMoth.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
                var dirY = GoldenMoth.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
                var dirZ = GoldenMoth.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
                return new Vec3(dirX, dirY, dirZ);
            }
        });
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {}

    public static boolean checkSpawnRules(
            EntityType<GoldenMoth> type, ServerLevelAccessor levelAccessor,
            MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return levelAccessor instanceof Level level && !level.isDay();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, 3);
    }
}
