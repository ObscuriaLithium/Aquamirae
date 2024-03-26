package com.obscuria.aquamirae.common;

import com.google.common.collect.Maps;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.core.api.ObscureAPI;
import net.minecraft.Util;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Vanishable;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.UUID;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SuppressWarnings("all")
public final class DeadSeaCurse {

    @Inherited
    @Target(value = TYPE)
    @Retention(value = RUNTIME)
    public @interface ByDefault {
    }

    public static boolean canBeCursed(Player player) {
        return true;
    }

    public static boolean canBeCursed(ItemStack stack) {
        return stack.getItem() instanceof Vanishable || stack.isEdible();
    }

    public static float getDamageFactorOf(LivingEntity entity) {
        var result = 1f;
        for (var slot : EquipmentSlot.values())
            if (isCursed(entity.getItemBySlot(slot)))
                result += 0.25f;
        return result;
    }

    public static boolean isCursed(ItemStack stack) {
        if (isCursedByAnnotation(stack)) return !isSuppressedByNBT(stack);
        return isCursedByNBT(stack);
    }

    public static boolean isSuppressed(ItemStack stack) {
        return isSuppressedByNBT(stack);
    }

    public static void makeCursed(ItemStack stack) {
        if (isCursedByAnnotation(stack) && isSuppressedByNBT(stack)) {
            setSuppressedByNBT(stack, false);
        } else if (!isCursedByNBT(stack)) {
            setCursedByNBT(stack, true);
        }
    }

    public static void makeSuppressed(ItemStack stack) {
        if (isCursedByNBT(stack))
            removeNBT(stack);
        if (isCursedByAnnotation(stack) && !isSuppressedByNBT(stack))
            setSuppressedByNBT(stack, true);
    }

    private static final String CURSE_TAG = "DeadSeaCurse";
    private static final String CURSED_TAG = "cursed";
    private static final String SUPPRESSED_TAG = "suppressed";

    private static boolean isCursedByAnnotation(ItemStack stack) {
        return stack.getItem().getClass().isAnnotationPresent(DeadSeaCurse.ByDefault.class);
    }

    private static boolean isCursedByNBT(ItemStack stack) {
        final var tag = stack.getTag();
        if (tag == null) return false;
        return tag.getCompound(CURSE_TAG).getBoolean(CURSED_TAG);
    }

    private static boolean isSuppressedByNBT(ItemStack stack) {
        final var tag = stack.getTag();
        if (tag == null) return false;
        return tag.getCompound(CURSE_TAG).getBoolean(SUPPRESSED_TAG);
    }

    private static void setCursedByNBT(ItemStack stack, boolean flag) {
        stack.getOrCreateTagElement(CURSE_TAG).putBoolean(CURSED_TAG, flag);
    }

    private static void setSuppressedByNBT(ItemStack stack, boolean flag) {
        stack.getOrCreateTagElement(CURSE_TAG).putBoolean(SUPPRESSED_TAG, flag);
    }

    private static void removeNBT(ItemStack stack) {
        final var tag = stack.getTag();
        if (tag == null) return;
        tag.remove(CURSE_TAG);
        if (tag.isEmpty())
            stack.setTag(null);
    }

    private static final Map<EquipmentSlot, UUID> UUID_BY_SLOT = Util.make(Maps.newHashMap(), map -> {
        map.put(EquipmentSlot.MAINHAND, UUID.fromString("02e529ac-3077-4dcb-a6d4-62e671b57bf2"));
        map.put(EquipmentSlot.HEAD, UUID.fromString("12e529ac-3077-4dcb-a6d4-62e671b57bf2"));
        map.put(EquipmentSlot.CHEST, UUID.fromString("22e529ac-3077-4dcb-a6d4-62e671b57bf2"));
        map.put(EquipmentSlot.LEGS, UUID.fromString("32e529ac-3077-4dcb-a6d4-62e671b57bf2"));
        map.put(EquipmentSlot.FEET, UUID.fromString("42e529ac-3077-4dcb-a6d4-62e671b57bf2"));
    });

    @ApiStatus.Internal
    public static void registerModifiers() {
        registerWeaponModifier(EquipmentSlot.MAINHAND);
        registerArmorModifier(EquipmentSlot.HEAD);
        registerArmorModifier(EquipmentSlot.CHEST);
        registerArmorModifier(EquipmentSlot.LEGS);
        registerArmorModifier(EquipmentSlot.FEET);
    }

    private static void registerWeaponModifier(EquipmentSlot slot) {
        ObscureAPI.DYNAMIC_ATTRIBUTES.register(Aquamirae.key("dead_sea_curse/weapon"),
                stack -> stack.getItem() instanceof SwordItem item
                        && DeadSeaCurse.isCursed(stack),
                collector -> collector.append(EquipmentSlot.MAINHAND, Attributes.ATTACK_DAMAGE,
                        context -> new AttributeModifier(UUID_BY_SLOT.get(slot), "Dead Sea Curse",
                                -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL)));
    }

    private static void registerArmorModifier(EquipmentSlot slot) {
        ObscureAPI.DYNAMIC_ATTRIBUTES.register(Aquamirae.key("dead_sea_curse/" + slot.getName()),
                stack -> stack.getItem() instanceof ArmorItem item
                        && item.getEquipmentSlot() == slot
                        && DeadSeaCurse.isCursed(stack),
                collector -> collector.append(slot, Attributes.ARMOR,
                        context -> new AttributeModifier(UUID_BY_SLOT.get(slot), "Dead Sea Curse",
                                -0.25, AttributeModifier.Operation.MULTIPLY_BASE)));
    }
}
