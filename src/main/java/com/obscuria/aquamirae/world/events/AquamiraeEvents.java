package com.obscuria.aquamirae.world.events;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.world.entities.IShipGraveyardEntity;
import com.obscuria.aquamirae.world.items.armor.AbyssalArmorItem;
import com.obscuria.aquamirae.world.items.armor.TerribleArmorItem;
import com.obscuria.aquamirae.world.items.armor.ThreeBoltArmorItem;
import com.obscuria.aquamirae.world.items.weapon.CoralLanceItem;
import com.obscuria.aquamirae.world.items.weapon.FinCutterItem;
import com.obscuria.aquamirae.world.items.weapon.RemnantsSaberItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AquamiraeEvents {

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level.isClientSide || event.player.isCreative() || event.player.isSpectator()) return;
        if (event.player.level.getBiome(event.player.blockPosition()).is(AquamiraeMod.ICE_MAZE))
            if (event.player.isInWaterOrBubble() && event.player.getTicksFrozen() <= event.player.getTicksRequiredToFreeze() * 3)
                if (countArmor(event.player, ThreeBoltArmorItem.class) < 4)
                    event.player.setTicksFrozen(event.player.getTicksFrozen() + 4);
    }

    public static void onEntityAttacked(LivingHurtEvent event) {
        //Fin Cutter
        if (event.getSource().getEntity() instanceof LivingEntity source && source.getMainHandItem().getItem() instanceof FinCutterItem item) {
            final int emptyHP = (int) Math.floor((source.getMaxHealth() - source.getHealth()) / 2);
            event.setAmount(event.getAmount() + event.getAmount() *
                    Math.min(item.ABILITY.getAmount(source, 1) * 0.01F, emptyHP * item.ABILITY.getAmount(source, 0) * 0.01F));
        }
        //Terrible Armor
        if (event.getEntity() instanceof Player player) {
            final int TOTAL = countArmor(player, TerribleArmorItem.class);
            if (TOTAL >= 2) {
                final ItemStack piece = getArmor(player, TerribleArmorItem.class);
                if (player.isInWater() && !player.getCooldowns().isOnCooldown(piece.getItem()) && piece.getItem() instanceof TerribleArmorItem item) {
                    player.addEffect(new MobEffectInstance(AquamiraeMobEffects.SWIM_SPEED.get(), 20 * item.ABILITY_HALFSET.getAmount(player, 1),
                            Math.min(19, item.ABILITY_HALFSET.getAmount(player, 0) / 10 - 1), false, false));
                    final int cooldown = 20 * item.ABILITY_HALFSET.getCost(player);
                    cooldown(player, TerribleArmorItem.class, cooldown);
                }
                if (TOTAL >= 4 && event.getSource().getEntity() instanceof LivingEntity source && piece.getItem() instanceof TerribleArmorItem item) {
                    source.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * item.ABILITY_FULLSET.getAmount(player, 0), 1, false, false));
                }
            }
        }
    }

    public static void onEntityHurt(LivingHurtEvent event) {
        //Remnants Saber
        if (event.getSource().getEntity() instanceof LivingEntity source && source.isInWater()
                && source.getMainHandItem().getItem() instanceof RemnantsSaberItem item)
            event.setAmount(event.getAmount() * (1F + item.ABILITY.getAmount(source, 0) * 0.01F));
        //Coral Lance
        if (event.getSource().getEntity() instanceof LivingEntity source && source.getMainHandItem().getItem() instanceof CoralLanceItem item
                && event.getEntity() instanceof IShipGraveyardEntity) {
            event.setAmount(event.getAmount() * (1F + item.ABILITY.getAmount(source, 0) * 0.01F));
        }
    }

    public static void onEntityDeath(LivingDeathEvent event) {
        //Abyssal Armor
        if (event != null && event.getEntity() != null) {
            final LivingEntity entity = event.getEntity();
            final int TOTAL = countArmor(entity, AbyssalArmorItem.class);
            if (TOTAL >= 4 && !entity.hasEffect(AquamiraeMobEffects.CRYSTALLIZATION.get())) {
                final AbyssalArmorItem item = (AbyssalArmorItem) getArmor(entity, AbyssalArmorItem.class).getItem();
                if (!entity.getPersistentData().getBoolean("crystallization")) {
                    event.setCanceled(true);
                    entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.CRYSTALLIZATION.get(),
                            20 * item.ABILITY_FULLSET_1.getAmount(entity, 0), 0, true, true));
                    entity.setHealth(entity.getMaxHealth());
                    if (entity.getLevel() instanceof ServerLevel level) level.playSound(null, new BlockPos(entity.getX(),
                                    entity.getY() + 1, entity.getZ()), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1, 1);
                    final ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
                    final ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
                    final ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
                    final ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
                    if (head.hurt(50, entity.getRandom(), null)) { head.shrink(1); head.setDamageValue(0); }
                    if (chest.hurt(50, entity.getRandom(), null)) { chest.shrink(1); chest.setDamageValue(0); }
                    if (legs.hurt(50, entity.getRandom(), null)) { legs.shrink(1); legs.setDamageValue(0); }
                    if (feet.hurt(50, entity.getRandom(), null)) { feet.shrink(1); feet.setDamageValue(0); }
                }
            }
        }
    }

    private static int countArmor(LivingEntity entity, Class<?> armor) {
        final boolean HEAD = armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.HEAD).getItem().getClass());
        final boolean CHEST = armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.CHEST).getItem().getClass());
        final boolean LEGS = armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.LEGS).getItem().getClass());
        final boolean FEET = armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.FEET).getItem().getClass());
        return (HEAD ? 1 : 0) + (CHEST ? 1 : 0) + (LEGS ? 1 : 0) + (FEET ? 1 : 0);
    }

    private static ItemStack getArmor(LivingEntity entity, Class<?> armor) {
        return armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.HEAD).getItem().getClass()) ?
                entity.getItemBySlot(EquipmentSlot.HEAD) :
                armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.CHEST).getItem().getClass()) ?
                        entity.getItemBySlot(EquipmentSlot.CHEST) :
                        armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.LEGS).getItem().getClass()) ?
                                entity.getItemBySlot(EquipmentSlot.LEGS) :
                                armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.FEET).getItem().getClass()) ?
                                        entity.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY;
    }

    private static void cooldown(Player player, Class<?> armor, int cooldown) {
        if (armor.isAssignableFrom(player.getItemBySlot(EquipmentSlot.HEAD).getItem().getClass()))
            player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlot.HEAD).getItem(), cooldown);
        if (armor.isAssignableFrom(player.getItemBySlot(EquipmentSlot.CHEST).getItem().getClass()))
            player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlot.CHEST).getItem(), cooldown);
        if (armor.isAssignableFrom(player.getItemBySlot(EquipmentSlot.LEGS).getItem().getClass()))
            player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlot.LEGS).getItem(), cooldown);
        if (armor.isAssignableFrom(player.getItemBySlot(EquipmentSlot.FEET).getItem().getClass()))
            player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlot.FEET).getItem(), cooldown);
    }
}
