package com.obscuria.aquamirae.world.events;

import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.world.entities.IShipGraveyardEntity;
import com.obscuria.aquamirae.world.items.armor.AbyssalArmorItem;
import com.obscuria.aquamirae.world.items.armor.TerribleArmorItem;
import com.obscuria.aquamirae.world.items.weapon.CoralLanceItem;
import com.obscuria.aquamirae.world.items.weapon.FinCutterItem;
import com.obscuria.aquamirae.world.items.weapon.RemnantsSaberItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AquamiraeEvents {

    public static void onEntityAttacked(LivingHurtEvent event) {
        //Fin Cutter
        if (event.getSource().getEntity() instanceof LivingEntity && ((LivingEntity)event.getSource().getEntity()).getMainHandItem().getItem() instanceof FinCutterItem) {
            final LivingEntity source = (LivingEntity) event.getSource().getEntity();
            final FinCutterItem item = (FinCutterItem) source.getMainHandItem().getItem();
            final int emptyHP = (int) Math.floor((source.getMaxHealth() - source.getHealth()) / 2);
            event.setAmount(event.getAmount() + event.getAmount() *
                    Math.min(item.ABILITY.getAmount(source, 1) * 0.01F, emptyHP * item.ABILITY.getAmount(source, 0) * 0.01F));
        }
        //Terrible Armor
        if (event.getEntity() instanceof PlayerEntity) {
            final PlayerEntity player = (PlayerEntity) event.getEntity();
            final int TOTAL = countArmor(player, TerribleArmorItem.class);
            if (TOTAL >= 2) {
                final ItemStack piece = getArmor(player, TerribleArmorItem.class);
                if (player.isInWater() && !player.getCooldowns().isOnCooldown(piece.getItem()) && piece.getItem() instanceof TerribleArmorItem) {
                    final TerribleArmorItem item = (TerribleArmorItem) piece.getItem();
                    player.addEffect(new EffectInstance(AquamiraeMobEffects.SWIM_SPEED.get(), 20 * item.ABILITY_HALFSET.getAmount(player, 1),
                            Math.min(19, item.ABILITY_HALFSET.getAmount(player, 0) / 10 - 1), false, false));
                    final int cooldown = 20 * item.ABILITY_HALFSET.getCost(player);
                    cooldown(player, TerribleArmorItem.class, cooldown);
                }
                if (TOTAL >= 4 && event.getSource().getEntity() instanceof LivingEntity && piece.getItem() instanceof TerribleArmorItem) {
                    final LivingEntity source = (LivingEntity) event.getSource().getEntity();
                    final TerribleArmorItem item = (TerribleArmorItem) piece.getItem();
                    source.addEffect(new EffectInstance(Effects.POISON, 20 * item.ABILITY_FULLSET.getAmount(player, 0), 1, false, false));
                }
            }
        }
    }

    public static void onEntityHurt(LivingHurtEvent event) {
        //Remnants Saber
        if (event.getSource().getEntity() instanceof LivingEntity && event.getSource().getEntity().isInWater()
                && ((LivingEntity)event.getSource().getEntity()).getMainHandItem().getItem() instanceof RemnantsSaberItem) {
            final LivingEntity source = (LivingEntity)event.getSource().getEntity();
            final RemnantsSaberItem item = (RemnantsSaberItem) source.getMainHandItem().getItem();
            event.setAmount(event.getAmount() * (1F + item.ABILITY.getAmount(source, 0) * 0.01F));
        }
        //Coral Lance
        if (event.getSource().getEntity() instanceof LivingEntity && ((LivingEntity) event.getSource().getEntity()).getMainHandItem().getItem() instanceof CoralLanceItem
                && event.getEntity() instanceof IShipGraveyardEntity) {
            final LivingEntity source = (LivingEntity) event.getSource().getEntity();
            final CoralLanceItem item = (CoralLanceItem) source.getMainHandItem().getItem();
            event.setAmount(event.getAmount() * (1F + item.ABILITY.getAmount(source, 0) * 0.01F));
        }
    }

    public static void onEntityDeath(LivingDeathEvent event) {
        //Abyssal Armor
        if (event != null && event.getEntity() != null) {
            final LivingEntity entity = event.getEntityLiving();
            final int TOTAL = countArmor(entity, AbyssalArmorItem.class);
            if (TOTAL >= 4 && !entity.hasEffect(AquamiraeMobEffects.CRYSTALLIZATION.get())) {
                final AbyssalArmorItem item = (AbyssalArmorItem) getArmor(entity, AbyssalArmorItem.class).getItem();
                if (!entity.getPersistentData().getBoolean("crystallization")) {
                    event.setCanceled(true);
                    entity.addEffect(new EffectInstance(AquamiraeMobEffects.CRYSTALLIZATION.get(),
                            20 * item.ABILITY_FULLSET_1.getAmount(entity, 0), 0, true, true));
                    entity.setHealth(entity.getMaxHealth());
                    if (entity.level instanceof ServerWorld) entity.level.playSound(null, new BlockPos(entity.getX(),
                                    entity.getY() + 1, entity.getZ()), SoundEvents.TOTEM_USE, SoundCategory.PLAYERS, 1, 1);
                    final ItemStack head = entity.getItemBySlot(EquipmentSlotType.HEAD);
                    final ItemStack chest = entity.getItemBySlot(EquipmentSlotType.CHEST);
                    final ItemStack legs = entity.getItemBySlot(EquipmentSlotType.LEGS);
                    final ItemStack feet = entity.getItemBySlot(EquipmentSlotType.FEET);
                    if (head.hurt(50, entity.getRandom(), null)) { head.shrink(1); head.setDamageValue(0); }
                    if (chest.hurt(50, entity.getRandom(), null)) { chest.shrink(1); chest.setDamageValue(0); }
                    if (legs.hurt(50, entity.getRandom(), null)) { legs.shrink(1); legs.setDamageValue(0); }
                    if (feet.hurt(50, entity.getRandom(), null)) { feet.shrink(1); feet.setDamageValue(0); }
                }
            }
        }
    }

    private static int countArmor(LivingEntity entity, Class<?> armor) {
        final boolean HEAD = armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlotType.HEAD).getItem().getClass());
        final boolean CHEST = armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().getClass());
        final boolean LEGS = armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlotType.LEGS).getItem().getClass());
        final boolean FEET = armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlotType.FEET).getItem().getClass());
        return (HEAD ? 1 : 0) + (CHEST ? 1 : 0) + (LEGS ? 1 : 0) + (FEET ? 1 : 0);
    }

    private static ItemStack getArmor(LivingEntity entity, Class<?> armor) {
        return armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlotType.HEAD).getItem().getClass()) ?
                entity.getItemBySlot(EquipmentSlotType.HEAD) :
                armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().getClass()) ?
                        entity.getItemBySlot(EquipmentSlotType.CHEST) :
                        armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlotType.LEGS).getItem().getClass()) ?
                                entity.getItemBySlot(EquipmentSlotType.LEGS) :
                                armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlotType.FEET).getItem().getClass()) ?
                                        entity.getItemBySlot(EquipmentSlotType.FEET) : ItemStack.EMPTY;
    }

    private static void cooldown(PlayerEntity player, Class<?> armor, int cooldown) {
        if (armor.isAssignableFrom(player.getItemBySlot(EquipmentSlotType.HEAD).getItem().getClass()))
            player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlotType.HEAD).getItem(), cooldown);
        if (armor.isAssignableFrom(player.getItemBySlot(EquipmentSlotType.CHEST).getItem().getClass()))
            player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlotType.CHEST).getItem(), cooldown);
        if (armor.isAssignableFrom(player.getItemBySlot(EquipmentSlotType.LEGS).getItem().getClass()))
            player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlotType.LEGS).getItem(), cooldown);
        if (armor.isAssignableFrom(player.getItemBySlot(EquipmentSlotType.FEET).getItem().getClass()))
            player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlotType.FEET).getItem(), cooldown);
    }
}
