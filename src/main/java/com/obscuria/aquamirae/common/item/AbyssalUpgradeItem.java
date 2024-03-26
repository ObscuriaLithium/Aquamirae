package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.Aquamirae;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class AbyssalUpgradeItem extends SmithingTemplateItem {
    private static final Component DESCRIPTION = Component.translatable(Util.makeDescriptionId("upgrade", Aquamirae.key("abyssal_upgrade"))).withStyle(ChatFormatting.GRAY);
    private static final Component APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", Aquamirae.key("smithing_template.abyssal_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE);
    private static final Component INGREDIENTS = Component.translatable(Util.makeDescriptionId("item", Aquamirae.key("smithing_template.abyssal_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE);
    private static final Component BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", Aquamirae.key("smithing_template.abyssal_upgrade.base_slot_description")));
    private static final Component ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item", Aquamirae.key("smithing_template.abyssal_upgrade.additions_slot_description")));

    public AbyssalUpgradeItem() {
        super(APPLIES_TO, INGREDIENTS, DESCRIPTION, BASE_SLOT_DESCRIPTION, ADDITIONS_SLOT_DESCRIPTION,
                List.of(new ResourceLocation("item/empty_armor_slot_helmet"),
                        new ResourceLocation("item/empty_armor_slot_chestplate"),
                        new ResourceLocation("item/empty_armor_slot_leggings"),
                        new ResourceLocation("item/empty_armor_slot_boots")),
                List.of(new ResourceLocation("item/empty_slot_amethyst_shard")));
    }
}
