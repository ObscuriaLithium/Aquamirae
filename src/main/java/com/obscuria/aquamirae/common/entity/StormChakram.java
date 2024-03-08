package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.AquamiraeClient;
import com.obscuria.aquamirae.common.item.RuneOfTheStormItem;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.api.ability.AbilityHelper;
import com.obscuria.core.api.graphic.world.Trail3D;
import com.obscuria.core.api.util.WorldUtil;
import com.obscuria.core.api.util.easing.Easing;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public class StormChakram extends ThrowableProjectile {
    public final Trail3D trail = Trail3D.create(20, 3)
            .setWidth(0.05f, Easing.EASE_OUT_CUBIC.mergeOut(Easing.EASE_OUT_CUBIC, 0.05f))
            .setColor(0xffbbbbff, 0x000050ff, Easing.EASE_OUT_CUBIC);
    public float spin = 0f, spinFactor = 0f, spinFactorO = 0f;
    private ItemStack stack = new ItemStack(AquamiraeItems.RUNE_OF_THE_STORM.get());
    private @Nullable LivingEntity attackTarget = null;
    private Vec3 attackMotion = Vec3.ZERO;
    private int attackTick = 0;
    private float damageFactor = 0f;

    public StormChakram(EntityType<StormChakram> type, Level level) {
        super(type, level);
    }

    public static StormChakram create(Level level, LivingEntity owner, ItemStack stack) {
        final var result = new StormChakram(AquamiraeEntities.STORM_CHAKRAM.get(), level);
        result.setOwner(owner);
        result.stack = stack.copy();
        return result;
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Rune", Tag.TAG_COMPOUND))
            this.stack = ItemStack.of(tag.getCompound("Rune"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Rune", this.stack.save(new CompoundTag()));
    }

    @Override
    public boolean isPickable() {
        return level().isClientSide
                && getOwner() instanceof Player player
                && AquamiraeClient.isLocalPlayer(player);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (getOwner() != player) return InteractionResult.FAIL;
        if (!level().isClientSide) {
            if (!player.getInventory().add(stack))
                WorldUtil.dropItem(level(), position(), stack);
            playSound(SoundEvents.TRIDENT_RETURN);
            discard();
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    @Override
    public void tick() {
        if (level().isClientSide) {
            updateSpinFactor();
            if (tickCount % 10 == 0) level().addParticle(AquamiraeParticles.GHOST_SHINE.get(),
                    getX() + random.triangle(0, 0.25), getY(),
                    getZ() + random.triangle(0, 0.25),
                    0, -0.25, 0);
        }

        updateOwner();
        if (getOwner() instanceof Player player) {
            if (position().distanceTo(player.position()) > 256) return;
            findTargetAround(player);
            if (attackTarget != null) {
                huntTo(attackTarget);
                hurtEntities(player);
            } else {
                followTo(player);
            }
        }

        updateTrail();
        final var position = position();
        super.tick();
        damageFactor = (float) position.distanceTo(position());
    }

    protected void findTargetAround(Player player) {
        if (attackTarget != null && !isCorrectTarget(attackTarget, player))
            attackTarget = null;

        if (attackTarget == null) {
            final var targets = level().getEntitiesOfClass(LivingEntity.class, areaAround(player, 16)).stream()
                    .filter(entity -> isCorrectTarget(entity, player))
                    .sorted(Comparator.comparingDouble(monster -> monster.position().distanceTo(player.position())))
                    .toList();
            if (!targets.isEmpty()) {
                attackTarget = targets.get(0);
                attackMotion = Vec3.ZERO;
                attackTick = 60;
            }
        }
    }

    protected boolean isCorrectTarget(LivingEntity entity, Player player) {
        if (entity.isDeadOrDying()) return false;
        if (entity == player) return false;
        if (player.position().distanceTo(entity.position()) > 16) return false;
        if (!entity.hasLineOfSight(player)) return false;
        if (entity instanceof Mob mob && mob.getTarget() == player) return true;
        return entity instanceof Monster;
    }

    protected void updateOwner() {
        if (getOwner() != null) return;
        level().getEntitiesOfClass(Player.class,
                        new AABB(this.position().add(-16, -16, -16),
                                this.position().add(16, 16, 16)))
                .forEach(this::setOwner);
    }

    protected void updateTrail() {
        if (!level().isClientSide) return;
        trail.addPoint(position().add(0, 0.1f, 0));
        trail.tick();
    }

    protected void followTo(Entity entity) {
        final var targetPos = entity.getEyePosition().add(0, -1, 0);
        setDeltaMovement(getDeltaMovement().scale(0.9f));
        if (position().distanceTo(targetPos) > 5f) {
            addDeltaMovement(position()
                    .vectorTo(targetPos)
                    .scale(0.01f));
        }
    }

    protected void huntTo(Entity entity) {
        final var targetPosition = entity.position().add(0,
                entity.getBbHeight() / 2, 0);
        attackTick += 1;
        if (attackTick >= 40) {
            attackMotion = position().vectorTo(targetPosition)
                    .normalize().scale(0.06f);
            attackTick = 0;
        }
        if (attackTick <= 15) {
            addDeltaMovement(attackMotion);
        } else {
            setDeltaMovement(getDeltaMovement().scale(0.9f));
        }
        addDeltaMovement(position().vectorTo(targetPosition)
                .normalize().scale(0.05f));
    }

    protected void hurtEntities(Player player) {
        level().getEntitiesOfClass(LivingEntity.class, areaAround(this, 4)).stream()
                .filter(entity -> isCorrectTarget(entity, player) && getBoundingBox().intersects(entity.getBoundingBox()))
                .forEach(entity -> doHurt(entity, player));
    }

    protected void doHurt(LivingEntity entity, Player player) {
        final var damage = RuneOfTheStormItem.getChakramDamage(this.stack, player);
        final var trueDamage = Mth.clamp(damage * damageFactor, 1, damage);
        if (entity.invulnerableTime > 0) return;
        if (entity.hurt(damageSources().mobProjectile(this, player), trueDamage)) {
            RuneOfTheStormItem.depleteCharges(stack, player);
            AbilityHelper.addCustomProgress(stack, "deal_damage", Math.round(trueDamage));
            if (level() instanceof ServerLevel level) {
                level.playSound(null, this, AquamiraeSounds.ENTITY_CHAKRAM_HIT.get(),
                        SoundSource.NEUTRAL, 1f, 1f);
                level.playSound(null, this, SoundEvents.AMETHYST_CLUSTER_HIT,
                        SoundSource.NEUTRAL, 1f, 1f);
            }
        }
    }

    protected AABB areaAround(Entity entity, int radius) {
        return new AABB(entity.position().add(-radius, -radius, -radius),
                entity.position().add(radius, radius, radius));
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return false;
    }

    public float getSpinFactor(float partialTicks) {
        return Mth.lerp(partialTicks, spinFactorO, spinFactor);
    }

    private void updateSpinFactor() {
        spinFactorO = spinFactor;
        spinFactor = (float) getDeltaMovement().length() * 0.25f;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling()
                .inflate(trail.getMaxDistanceFrom(position()));
    }
}
