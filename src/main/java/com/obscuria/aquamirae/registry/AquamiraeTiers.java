package com.obscuria.aquamirae.registry;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum AquamiraeTiers implements Tier {
    TERRIBLE_SWORD(3, 750, 4, 0, 18, () ->
            Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())),
    FIN_CUTTER(3, 1000, 4, 0, 20, () ->
            Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())),
    DIVIDER(3, 2200, 6, 0, 14, () ->
            Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())),
    WHISPER_OF_tHE_ABYSS(3, 1400, 6, 0, 14, () ->
            Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())),
    REMNANTS_SABER(1, 100, 2, 0, 5, () ->
            Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())),
    POISONED_BLADE(2, 500, 4, 0, 8, () ->
            Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())),
    CORAL_LANCE(3, 1400, 6, 0, 14, () ->
            Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())),
    DAGGER_OF_GREED(3, 100, 8, 0, 1, () ->
            Ingredient.EMPTY),
    SWEET_LANCE(3, 1400, 6f, 0, 14, () ->
            Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())),
    AXE_OF_FROSTFIRE(2, 500, 4f, 0, 6, () ->
            Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));

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
