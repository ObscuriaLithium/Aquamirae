package com.obscuria.aquamirae.registry;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum AquamiraeTiers implements Tier {
    TERRIBLE_SWORD(3, 750, 4f, 0, 18, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            AquamiraeItems.ANGLERS_FANG.get().getDefaultInstance())),
    POISONED_BLADE(2, 500, 4f, 0, 6, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            AquamiraeItems.ANGLERS_FANG.get().getDefaultInstance())),
    REMNANTS_SABER(1, 100, 4f, 0, 5, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            AquamiraeItems.SHARP_BONES.get().getDefaultInstance())),
    FIN_CUTTER(3, 1000, 8f, 0, 20, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            Items.DIAMOND.getDefaultInstance())),
    DIVIDER(3, 2200, 6f, 0, 14, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            AquamiraeItems.ABYSSAL_AMETHYST.get().getDefaultInstance())),
    WHISPER_OF_tHE_ABYSS(3, 1400, 6f, 0, 14, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance(),
            AquamiraeItems.ABYSSAL_AMETHYST.get().getDefaultInstance())),
    DAGGER_OF_GREED(3, 100, 8f, 0, 1, () -> Ingredient.EMPTY),
    CORAL_LANCE(3, 1400, 6f, 0, 14, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance())),
    SWEET_LANCE(3, 1400, 6f, 0, 14, () -> Ingredient.of(
            AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    AquamiraeTiers(int level, int uses, float speed, float damage, int enchantmentValue,
                   Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
