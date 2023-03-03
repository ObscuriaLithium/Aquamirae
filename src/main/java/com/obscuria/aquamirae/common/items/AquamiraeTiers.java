package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public final class AquamiraeTiers {
    public static final Tier REMNANTS_SABER = new Tier() {
        public int getUses() {
            return 100;
        }
        public float getSpeed() {
            return 4f;
        }
        public float getAttackDamageBonus() {
            return 1f;
        }
        public int getLevel() {
            return 1;
        }
        public int getEnchantmentValue() {
            return 5;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.SHARP_BONES.get()),
                    new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
        }
    };

    public static final Tier CORAL_LANCE = new Tier() {
        public int getUses() {
            return 1400;
        }
        public float getSpeed() {
            return 6f;
        }
        public float getAttackDamageBonus() {
            return 10f;
        }
        public int getLevel() {
            return 3;
        }
        public int getEnchantmentValue() {
            return 14;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
        }
    };

    public static final Tier DAGGER_OF_GREED = new Tier() {
        public int getUses() {
            return 100;
        }
        public float getSpeed() {
            return 8f;
        }
        public float getAttackDamageBonus() {
            return 0f;
        }
        public int getLevel() {
            return 3;
        }
        public int getEnchantmentValue() {
            return 30;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
    };

    public static final Tier DIVIDER = new Tier() {
        public int getUses() {
            return 2200;
        }
        public float getSpeed() {
            return 6f;
        }
        public float getAttackDamageBonus() {
            return 3f;
        }
        public int getLevel() {
            return 3;
        }
        public int getEnchantmentValue() {
            return 14;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
                    new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
        }
    };

    public static final Tier FIN_CUTTER = new Tier() {
        public int getUses() {
            return 1000;
        }
        public float getSpeed() {
            return 8f;
        }
        public float getAttackDamageBonus() {
            return 2f;
        }
        public int getLevel() {
            return 3;
        }
        public int getEnchantmentValue() {
            return 20;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(Items.DIAMOND), new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
        }
    };

    public static final Tier MAZE_ROSE = new Tier() {
        public int getUses() {
            return 1800;
        }
        public float getSpeed() {
            return 4f;
        }
        public float getAttackDamageBonus() {
            return -1f;
        }
        public int getLevel() {
            return 2;
        }
        public int getEnchantmentValue() {
            return 12;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
                    new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
        }
    };

    public static final Tier POISONED_BLADE = new Tier() {
        public int getUses() {
            return 500;
        }
        public float getSpeed() {
            return 4f;
        }
        public float getAttackDamageBonus() {
            return 1f;
        }
        public int getLevel() {
            return 2;
        }
        public int getEnchantmentValue() {
            return 6;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.ANGLERS_FANG.get()),
                    new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
        }
    };

    public static final Tier POISONED_CHAKRA = new Tier() {
        public int getUses() {
            return 1200;
        }
        public float getSpeed() {
            return 2f;
        }
        public float getAttackDamageBonus() {
            return -3f;
        }
        public int getLevel() {
            return 0;
        }
        public int getEnchantmentValue() {
            return 12;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.ANGLERS_FANG.get()),
                    new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
        }
    };

    public static final Tier SWEET_LANCE = new Tier() {
        public int getUses() {
            return 1400;
        }
        public float getSpeed() {
            return 6f;
        }
        public float getAttackDamageBonus() {
            return 10f;
        }
        public int getLevel() {
            return 3;
        }
        public int getEnchantmentValue() {
            return 14;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance());
        }
    };

    public static final Tier TERRIBLE_SWORD = new Tier() {
        public int getUses() {
            return 750;
        }
        public float getSpeed() {
            return 4f;
        }
        public float getAttackDamageBonus() {
            return 2f;
        }
        public int getLevel() {
            return 3;
        }
        public int getEnchantmentValue() {
            return 18;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.ANGLERS_FANG.get()), new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
        }
    };

    public static final Tier WHISPER_OF_tHE_ABYSS = new Tier() {
        public int getUses() {
            return 1400;
        }
        public float getSpeed() {
            return 6f;
        }
        public float getAttackDamageBonus() {
            return 10f;
        }
        public int getLevel() {
            return 3;
        }
        public int getEnchantmentValue() {
            return 14;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
                    new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
        }
    };

    public static final ArmorMaterial TERRIBLE_ARMOR = new ArmorMaterial() {
        @Override public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.getIndex()] * 25;
        }
        @Override public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
            return new int[]{2, 3, 7, 5}[slot.getIndex()];
        }
        @Override public int getEnchantmentValue() {
            return 12;
        }
        @Override public @NotNull SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_IRON;
        }
        @Override public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
                    new ItemStack(AquamiraeItems.ANGLERS_FANG.get()));
        }
        @Override public @NotNull String getName() {
            return "terrible";
        }
        @Override public float getToughness() {
            return 0f;
        }
        @Override public float getKnockbackResistance() {
            return 0f;
        }
    };

    public static final ArmorMaterial THREE_BOLT_ARMOR = new ArmorMaterial() {
        @Override public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.getIndex()] * 75;
        }
        @Override public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
            return new int[]{3, 5, 5, 7}[slot.getIndex()];
        }
        @Override public int getEnchantmentValue() {
            return 9;
        }
        @Override public @NotNull SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_IRON;
        }
        @Override public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
        @Override public @NotNull String getName() {
            return "three_bolt";
        }
        @Override public float getToughness() {
            return 2f;
        }
        @Override public float getKnockbackResistance() {
            return 0.1f;
        }
    };

    public static final ArmorMaterial ABYSSAL_ARMOR = new ArmorMaterial() {
        @Override public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.getIndex()] * 40;
        }
        @Override public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
            return new int[]{2, 4, 4, 6}[slot.getIndex()];
        }
        @Override public int getEnchantmentValue() {
            return 12;
        }
        @Override public @NotNull SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_NETHERITE;
        }
        @Override public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
                    new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
        }
        @Override public @NotNull String getName() {
            return "abyssal";
        }
        @Override public float getToughness() {
            return 3f;
        }
        @Override public float getKnockbackResistance() {
            return 0.1f;
        }
    };

    public static final ArmorMaterial ABYSSAL_ARMOR_EXTRA = new ArmorMaterial() {
        @Override public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
            return new int[]{13, 15, 16, 11}[slot.getIndex()] * 40;
        }
        @Override public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
            return new int[]{3, 6, 8, 2}[slot.getIndex()];
        }
        @Override public int getEnchantmentValue() {
            return 12;
        }
        @Override public @NotNull SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_NETHERITE;
        }
        @Override public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
                    new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
        }
        @Override public @NotNull String getName() {
            return "abyssal";
        }
        @Override public float getToughness() {
            return 0f;
        }
        @Override public float getKnockbackResistance() {
            return 0f;
        }
    };
}
