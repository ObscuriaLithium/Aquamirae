package com.obscuria.aquamirae.registry;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum AquamiraeArmorMaterials implements ArmorMaterial {
    TERRIBLE("terrible", 25, List.of(2, 3, 7, 5), 12,
            SoundEvents.ARMOR_EQUIP_IRON, 0, 0, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            AquamiraeItems.ANGLERS_FANG.get().getDefaultInstance())),
    THREE_BOLT("three_bolt", 75, List.of(2, 4, 0, 7), 9,
            SoundEvents.ARMOR_EQUIP_IRON, 1, 0, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance())),
    THREE_BOLT_SUIT("three_bolt", 0, List.of(0, 0, 5, 0), 9,
            SoundEvents.ARMOR_EQUIP_IRON, 1, 0, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance())),
    ABYSSAL("abyssal", 40, List.of(3, 5, 7, 0), 12,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 3, 0.1f, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            AquamiraeItems.ABYSSAL_AMETHYST.get().getDefaultInstance())),
    ABYSSAL_HEAUME("abyssal", 40, List.of(0, 0, 0, 5), 12,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 9, 0.3f, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            AquamiraeItems.ABYSSAL_AMETHYST.get().getDefaultInstance())),
    ABYSSAL_TIARA("abyssal", 40, List.of(0, 0, 0, 0), 12,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 0, 0, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            AquamiraeItems.ABYSSAL_AMETHYST.get().getDefaultInstance()));

    private final String name;
    private final int durabilityMultiplier;
    private final List<Integer> protectionForIndex;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    AquamiraeArmorMaterials(String name, int durabilityMultiplier, List<Integer> protectionForIndex,
                            int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance,
                            Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionForIndex = protectionForIndex;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE =
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 13);
                map.put(ArmorItem.Type.LEGGINGS, 15);
                map.put(ArmorItem.Type.CHESTPLATE, 16);
                map.put(ArmorItem.Type.HELMET, 11);
            });

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionForIndex.get(type.getSlot().getIndex());
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
