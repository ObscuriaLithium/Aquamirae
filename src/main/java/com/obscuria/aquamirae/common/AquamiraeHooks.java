package com.obscuria.aquamirae.common;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.item.armor.TerribleArmor;
import com.obscuria.aquamirae.common.item.armor.ThreeBoltArmor;
import com.obscuria.aquamirae.common.item.weapon.TerribleSword;
import com.obscuria.core.api.ability.AbilityHelper;
import com.obscuria.core.api.extension.entity.MobAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public interface AquamiraeHooks {
    static void onTooltip(ItemStack stack, List<Component> tooltip) {
        if (DeadSeaCurse.isCursed(stack))
            tooltip.add(1, Component
                    .translatable("lore.aquamirae.dead_sea_curse")
                    .withStyle(ChatFormatting.RED));
    }

    static void onFoodEaten(LivingEntity entity, ItemStack stack) {
        if (DeadSeaCurse.isCursed(stack))
            entity.setTicksFrozen(entity.getTicksFrozen()
                    + (int) (entity.getTicksRequiredToFreeze() * 2f));
    }

    static void onPlayerTick(Player player) {
        if (player.level().isClientSide || player.isCreative() || player.isSpectator()) return;
        if (Aquamirae.isInIceMaze(player))
            if (player.isInWaterOrBubble() && player.getTicksFrozen() <= player.getTicksRequiredToFreeze() * 3)
                if (ThreeBoltArmor.ABILITY.setupContext(player.getItemBySlot(EquipmentSlot.HEAD), player).getTier() < 2)
                    player.setTicksFrozen(player.getTicksFrozen() + 4);
    }

    static void onMobSpawn(Mob mob) {
        ResourceLocation lootTable = null;
        if (mob instanceof Pillager) lootTable = Aquamirae.key("entities/maze_pillager");
        if (mob instanceof Vindicator) lootTable = Aquamirae.key("entities/maze_vindicator");
        if (lootTable == null
                || !Aquamirae.isInIceMaze(mob)
                || MobAccessor.getLootTable(mob).isPresent()) return;
        MobAccessor.setLootTable(mob, lootTable);
    }

    static float getLivingDamage(LivingEntity entity, float amount, DamageSource source) {
        if (entity instanceof Player player && source.getEntity() instanceof LivingEntity attacker) {
            for (var pair : AbilityHelper.getArmorAbilities(player)) {
                final var stack = pair.getFirst();
                if (stack.getItem() instanceof TerribleArmor)
                    TerribleArmor.applyAbility(stack, player, attacker);
            }
        } else if (source.getEntity() instanceof Player player) {
            final var stack = player.getMainHandItem();
            if (stack.getItem() instanceof TerribleSword) {
                amount += TerribleSword.getDamageBonus(player, amount);
            }
        }
        amount *= DeadSeaCurse.getDamageFactorOf(entity);
        return amount;
    }

    static boolean canBreathe(Player player, boolean origin) {
        if (!Aquamirae.isInIceMaze(player)) return origin;
        if (player.isCreative() || player.isSpectator()) return true;
        if (player.isUnderWater()) return false;
        return origin;
    }
}
