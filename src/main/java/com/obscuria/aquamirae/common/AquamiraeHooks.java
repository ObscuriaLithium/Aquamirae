package com.obscuria.aquamirae.common;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.item.RuneOfTheStormItem;
import com.obscuria.aquamirae.common.item.armor.ThreeBoltArmor;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.core.common.extension.entity.MobAccessor;
import com.obscuria.core.common.item.perk.PerkHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.NonExtendable
public interface AquamiraeHooks {

    static void onTooltip(ItemStack stack, List<Component> tooltip) {
        if (DeadSeaCurse.isCursed(stack))
            tooltip.add(1, Component
                    .translatable("lore.aquamirae.dead_sea_curse")
                    .withStyle(ChatFormatting.RED));
    }

    static void onItemUsed(LivingEntity entity, ItemStack stack) {
        if (!stack.getItem().isEdible()) return;
        if (DeadSeaCurse.isCursed(stack))
            entity.setTicksFrozen(entity.getTicksFrozen()
                    + (int) (entity.getTicksRequiredToFreeze() * 2f));
    }

    static void onPlayerTick(Player player) {
        if (player.level().isClientSide || player.isCreative() || player.isSpectator()) return;
        if (Aquamirae.isInIceMaze(player))
            if (player.isInWaterOrBubble() && player.getTicksFrozen() <= player.getTicksRequiredToFreeze() * 3)
                if (ThreeBoltArmor.Helmet.ABILITY.setupContext(
                        player.getItemBySlot(EquipmentSlot.HEAD), player).tier() < 2)
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

    static void onLivingDeath(LivingEntity entity, DamageSource source) {
        if (source.getEntity() instanceof Player player
                && player.level() instanceof ServerLevel level
                && PerkHelper.has(player.getMainHandItem(), RuneOfTheStormItem.PERK))
            RuneOfTheStormItem.onPerkTrigger(level, player, entity);
    }

    static void acceptCustomAttributes(TriConsumer<EntityType<? extends LivingEntity>, Attribute, Double> consumer) {
        consumer.accept(EntityType.PLAYER, AquamiraeAttributes.DEPTHS_FURY.get(), 0.0);
    }

    static float modifyHurtDamage(LivingEntity entity, float amount) {
        return amount * DeadSeaCurse.getDamageModifier(entity);
    }

    static boolean canBreathe(LivingEntity entity, boolean origin) {
        if (entity instanceof Player player) {
            if (!Aquamirae.isInIceMaze(player)) return origin;
            if (player.isCreative() || player.isSpectator()) return true;
            if (player.isUnderWater()) return false;
        }
        return origin;
    }
}
