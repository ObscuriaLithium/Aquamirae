package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.common.entity.projectile.IceShard;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import com.obscuria.core.common.item.Lore;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@Lore("lore.aquamirae.blessed_powder")
public class BlessedPowderItem extends Item {

    public BlessedPowderItem() {
        super(new Properties().rarity(Rarity.UNCOMMON).stacksTo(8));
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack self, Slot slot, ClickAction action, Player player) {
        if (!slot.mayPickup(player)) return false;
        if (action != ClickAction.SECONDARY) return false;
        final var stack = slot.getItem();
        if (stack.is(AquamiraeItems.ABYSSAL_AMETHYST.get())) return false;
        if (DeadSeaCurse.isCursed(stack)) {
            player.playSound(SoundEvents.BOTTLE_EMPTY, 1,
                    (float) player.getRandom().triangle(1, 0.2));
            DeadSeaCurse.makeSuppressed(stack);
            self.shrink(1);
            return true;
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        final var stack = player.getItemInHand(hand);

        if (level instanceof ServerLevel serverLevel) {
            final var random = player.getRandom();
            final var direction = player.getViewVector(1f).normalize();
            final var origin = player.getRopeHoldPosition(1f)
                    .add(player.getEyePosition())
                    .scale(0.5f);
            //this.makeShineParticles(serverLevel, random, origin, direction);
            //this.makePoofParticles(serverLevel, random, origin, direction);

            IceShard.create(serverLevel, player, origin, direction.scale(0.4f));
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) stack.shrink(1);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void makeShineParticles(ServerLevel level, RandomSource random, Vec3 origin, Vec3 direction) {
        for (var i = 0; i < 32; i++) {
            final var speed = (float) random.triangle(4f, 1f);
            final var position = origin.add(
                    random.triangle(0, 0.1f),
                    random.triangle(0, 0.1f),
                    random.triangle(0, 0.1f));
            final var motion = direction
                    .xRot((float) random.triangle(0, 0.6f))
                    .yRot((float) random.triangle(0, 0.6f))
                    .zRot((float) random.triangle(0, 0.6f));
            level.sendParticles(AquamiraeParticleTypes.SHINE.get(),
                    position.x(), position.y(), position.z(), 0,
                    motion.x(), motion.y(), motion.z(), speed);
        }
    }

    private void makePoofParticles(ServerLevel level, RandomSource random, Vec3 origin, Vec3 direction) {
        for (var i = 0; i < 8; i++) {
            final var speed = (float) random.triangle(0.5f, 0.1f);
            final var position = origin.add(
                    random.triangle(0, 0.1f),
                    random.triangle(0, 0.1f),
                    random.triangle(0, 0.1f));
            final var motion = direction
                    .xRot((float) random.triangle(0, 0.3f))
                    .yRot((float) random.triangle(0, 0.3f))
                    .zRot((float) random.triangle(0, 0.3f));
            level.sendParticles(ParticleTypes.POOF,
                    position.x(), position.y(), position.z(), 0,
                    motion.x(), motion.y(), motion.z(), speed);
        }
    }
}
