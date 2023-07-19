package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.NotNull;

public final class AquamiraeMaterials {
    public static final ToolMaterial REMNANTS_SABER = new ToolMaterial() {
        public int getDurability() {
            return 100;
        }
        public float getMiningSpeedMultiplier() {
            return 4f;
        }
        public float getAttackDamage() {
            return 1f;
        }
        public int getMiningLevel() {
            return 1;
        }
        public int getEnchantability() {
            return 5;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.SHARP_BONES, AquamiraeItems.SHIP_GRAVEYARD_ECHO);
        }
    };

    public static final ToolMaterial CORAL_LANCE = new ToolMaterial() {
        public int getDurability() {
            return 1400;
        }
        public float getMiningSpeedMultiplier() {
            return 6f;
        }
        public float getAttackDamage() {
            return 10f;
        }
        public int getMiningLevel() {
            return 3;
        }
        public int getEnchantability() {
            return 14;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.SHIP_GRAVEYARD_ECHO);
        }
    };

    public static final ToolMaterial DAGGER_OF_GREED = new ToolMaterial() {
        public int getDurability() {
            return 100;
        }
        public float getMiningSpeedMultiplier() {
            return 8f;
        }
        public float getAttackDamage() {
            return 0f;
        }
        public int getMiningLevel() {
            return 3;
        }
        public int getEnchantability() {
            return 30;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
    };

    public static final ToolMaterial DIVIDER = new ToolMaterial() {
        public int getDurability() {
            return 2200;
        }
        public float getMiningSpeedMultiplier() {
            return 6f;
        }
        public float getAttackDamage() {
            return 3f;
        }
        public int getMiningLevel() {
            return 3;
        }
        public int getEnchantability() {
            return 14;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.SHIP_GRAVEYARD_ECHO, AquamiraeItems.ABYSSAL_AMETHYST);
        }
    };

    public static final ToolMaterial FIN_CUTTER = new ToolMaterial() {
        public int getDurability() {
            return 1000;
        }
        public float getMiningSpeedMultiplier() {
            return 8f;
        }
        public float getAttackDamage() {
            return 2f;
        }
        public int getMiningLevel() {
            return 3;
        }
        public int getEnchantability() {
            return 20;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(Items.DIAMOND, AquamiraeItems.SHIP_GRAVEYARD_ECHO);
        }
    };

    public static final ToolMaterial MAZE_ROSE = new ToolMaterial() {
        public int getDurability() {
            return 1800;
        }
        public float getMiningSpeedMultiplier() {
            return 4f;
        }
        public float getAttackDamage() {
            return -1f;
        }
        public int getMiningLevel() {
            return 2;
        }
        public int getEnchantability() {
            return 12;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.SHIP_GRAVEYARD_ECHO, AquamiraeItems.ABYSSAL_AMETHYST);
        }
    };

    public static final ToolMaterial POISONED_BLADE = new ToolMaterial() {
        public int getDurability() {
            return 500;
        }
        public float getMiningSpeedMultiplier() {
            return 4f;
        }
        public float getAttackDamage() {
            return 1f;
        }
        public int getMiningLevel() {
            return 2;
        }
        public int getEnchantability() {
            return 6;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.ANGLERS_FANG, AquamiraeItems.SHIP_GRAVEYARD_ECHO);
        }
    };

    public static final ToolMaterial POISONED_CHAKRA = new ToolMaterial() {
        public int getDurability() {
            return 1200;
        }
        public float getMiningSpeedMultiplier() {
            return 2f;
        }
        public float getAttackDamage() {
            return -3f;
        }
        public int getMiningLevel() {
            return 0;
        }
        public int getEnchantability() {
            return 12;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.ANGLERS_FANG, AquamiraeItems.SHIP_GRAVEYARD_ECHO);
        }
    };

    public static final ToolMaterial SWEET_LANCE = new ToolMaterial() {
        public int getDurability() {
            return 1400;
        }
        public float getMiningSpeedMultiplier() {
            return 6f;
        }
        public float getAttackDamage() {
            return 10f;
        }
        public int getMiningLevel() {
            return 3;
        }
        public int getEnchantability() {
            return 14;
        }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.SHIP_GRAVEYARD_ECHO);
        }
    };

    public static final ToolMaterial TERRIBLE_SWORD = new ToolMaterial() {
        public int getDurability() {
            return 750;
        }
        public float getMiningSpeedMultiplier() {
            return 4f;
        }
        public float getAttackDamage() {
            return 2f;
        }
        public int getMiningLevel() {
            return 3;
        }
        public int getEnchantability() {
            return 18;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.ANGLERS_FANG, AquamiraeItems.SHIP_GRAVEYARD_ECHO);
        }
    };

    public static final ToolMaterial WHISPER_OF_tHE_ABYSS = new ToolMaterial() {
        public int getDurability() {
            return 1400;
        }
        public float getMiningSpeedMultiplier() {
            return 6f;
        }
        public float getAttackDamage() {
            return 10f;
        }
        public int getMiningLevel() {
            return 3;
        }
        public int getEnchantability() {
            return 14;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.SHIP_GRAVEYARD_ECHO, AquamiraeItems.ABYSSAL_AMETHYST);
        }
    };

    public static final ArmorMaterial TERRIBLE_ARMOR = new ArmorMaterial() {
        public int getDurability(ArmorItem.Type type) {
            return new int[]{13, 15, 16, 11}[type.getEquipmentSlot().getEntitySlotId()] * 25;
        }
        public int getProtection(ArmorItem.Type type) {
            return new int[]{2, 3, 7, 5}[type.getEquipmentSlot().getEntitySlotId()];
        }
        public int getEnchantability() {
            return 12;
        }
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.SHIP_GRAVEYARD_ECHO, AquamiraeItems.ANGLERS_FANG);
        }
        public String getName() {
            return "terrible";
        }
        public float getToughness() {
            return 0f;
        }
        public float getKnockbackResistance() {
            return 0f;
        }
    };

    public static final ArmorMaterial THREE_BOLT_ARMOR = new ArmorMaterial() {
        public int getDurability(ArmorItem.Type type) {
            return new int[]{13, 15, 16, 11}[type.getEquipmentSlot().getEntitySlotId()] * 75;
        }
        public int getProtection(ArmorItem.Type type) {
            return new int[]{3, 5, 5, 7}[type.getEquipmentSlot().getEntitySlotId()];
        }
        public int getEnchantability() {
            return 9;
        }
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
        public String getName() {
            return "three_bolt";
        }
        public float getToughness() {
            return 2f;
        }
        public float getKnockbackResistance() {
            return 0.1f;
        }
    };

    public static final ArmorMaterial ABYSSAL_ARMOR = new ArmorMaterial() {
        public int getDurability(ArmorItem.Type type) {
            return new int[]{13, 15, 16, 11}[type.getEquipmentSlot().getEntitySlotId()] * 40;
        }
        public int getProtection(ArmorItem.Type type) {
            return new int[]{2, 4, 4, 6}[type.getEquipmentSlot().getEntitySlotId()];
        }
        public int getEnchantability() {
            return 12;
        }
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.SHIP_GRAVEYARD_ECHO, AquamiraeItems.ABYSSAL_AMETHYST);
        }
        public String getName() {
            return "abyssal";
        }
        public float getToughness() {
            return 3f;
        }
        public float getKnockbackResistance() {
            return 0.1f;
        }
    };

    public static final ArmorMaterial ABYSSAL_ARMOR_EXTRA = new ArmorMaterial() {
        public int getDurability(ArmorItem.Type type) {
            return new int[]{13, 15, 16, 11}[type.getEquipmentSlot().getEntitySlotId()] * 40;
        }
        public int getProtection(ArmorItem.Type type) {
            return new int[]{3, 6, 8, 2}[type.getEquipmentSlot().getEntitySlotId()];
        }
        public int getEnchantability() {
            return 12;
        }
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
        }
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(AquamiraeItems.SHIP_GRAVEYARD_ECHO, AquamiraeItems.ABYSSAL_AMETHYST);
        }
        public String getName() {
            return "abyssal";
        }
        public float getToughness() {
            return 0f;
        }
        public float getKnockbackResistance() {
            return 0f;
        }
    };
}
