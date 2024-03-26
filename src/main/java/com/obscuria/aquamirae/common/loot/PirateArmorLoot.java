package com.obscuria.aquamirae.common.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeLootPoolEntryTypes;
import com.obscuria.core.api.graphic.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PirateArmorLoot extends LootPoolSingletonContainer {
    public static final Codec<PirateArmorLoot> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                    Codec.INT.fieldOf("min_bonus").forGetter(loot -> loot.minBonus),
                    Codec.INT.fieldOf("max_bonus").forGetter(loot -> loot.maxBonus))
            .and(singletonFields(builder)).apply(builder, PirateArmorLoot::new));
    private static final List<Option> OPTIONS = List.of(
            new Option(EquipmentSlot.HEAD, () -> Items.LEATHER_HELMET, 1, Component.translatable("item.aquamirae.pirate_cap")),
            new Option(EquipmentSlot.CHEST, () -> Items.LEATHER_CHESTPLATE, 3, Component.translatable("item.aquamirae.pirate_tunic")),
            new Option(EquipmentSlot.LEGS, () -> Items.LEATHER_LEGGINGS, 2, Component.translatable("item.aquamirae.pirate_pants")),
            new Option(EquipmentSlot.FEET, () -> Items.LEATHER_BOOTS, 1, Component.translatable("item.aquamirae.pirate_boots")));
    private final int minBonus;
    private final int maxBonus;

    private PirateArmorLoot(int minBonus, int maxBonus, int weight, int quality,
                            List<LootItemCondition> conditions, List<LootItemFunction> functions) {
        super(weight, quality, conditions, functions);
        this.minBonus = Math.max(minBonus, 0);
        this.maxBonus = Mth.clamp(maxBonus, minBonus, 100);
    }

    @Override
    protected void createItemStack(Consumer<ItemStack> consumer, LootContext context) {
        consumer.accept(create(OPTIONS.get(context.getRandom().nextInt(OPTIONS.size())), context));
    }

    protected ItemStack create(Option option, LootContext context) {
        final var stack = option.itemSupplier.get().getDefaultInstance();
        final var random = context.getRandom();
        stack.setHoverName(option.name);
        stack.getOrCreateTagElement(DyeableLeatherItem.TAG_DISPLAY).putInt(DyeableLeatherItem.TAG_COLOR,
                Color.RGB.from(0.04f,
                        (float) random.triangle(0.65, 0.15),
                        (float) random.triangle(0.7, 0.15)).toHex());
        stack.addAttributeModifier(Attributes.ARMOR, new AttributeModifier("Base Bonus",
                option.armor, AttributeModifier.Operation.ADDITION), option.slot);
        stack.addAttributeModifier(AquamiraeAttributes.DEPTHS_FURY.get(), new AttributeModifier("Bonus",
                random.nextIntBetweenInclusive(minBonus, maxBonus) * 0.01,
                AttributeModifier.Operation.MULTIPLY_BASE), option.slot);
        return stack;
    }

    @Override
    public LootPoolEntryType getType() {
        return AquamiraeLootPoolEntryTypes.PIRATE_ARMOR.get();
    }

    private record Option(EquipmentSlot slot, Supplier<Item> itemSupplier, int armor, Component name) {}
}
