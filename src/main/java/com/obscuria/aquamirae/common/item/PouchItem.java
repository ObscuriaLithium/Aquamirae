package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.api.graphic.Icons;
import com.obscuria.core.api.util.PlayerUtil;
import com.obscuria.core.api.util.Splitter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class PouchItem extends Item {
    private final Supplier<SoundEvent> sound;
    private final Component lore;

    public static List<ItemStack> generateLoot(ResourceLocation key, Player player) {
        return Optional.ofNullable(player.level().getServer()).map(server -> {
            final var table = server.getLootData().getLootTable(key);
            final var params = new LootParams.Builder((ServerLevel) player.level())
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .create(LootContextParamSets.GIFT);
            return table.getRandomItems(params);
        }).orElse(ObjectArrayList.of());
    }

    public PouchItem(Supplier<SoundEvent> sound, Component lore, Properties properties) {
        super(properties);
        this.sound = sound;
        this.lore = lore;
    }

    abstract ResourceLocation getLootTable(ItemStack stack);

    abstract Component getContent(ItemStack stack);

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        Aquamirae.addIconTooltip(Icons.MOUSE_RIGHT_BUTTON, Component.translatable("lore.aquamirae.common.press_to_open"), tooltip);
        Aquamirae.addTooltip(this.lore, tooltip);
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("lore.aquamirae.common.can_contain").withStyle(ChatFormatting.GRAY));
        tooltip.addAll(Splitter.of(getContent(stack)).setLineLength(40).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY)).build());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        final var stack = player.getItemInHand(hand);
        player.getCooldowns().addCooldown(this, 10);
        level.playSound(null, player, this.sound.get(), SoundSource.PLAYERS, 1, 1);
        player.swing(hand);

        if (!level.isClientSide()) {
            generateLoot(getLootTable(stack), player).forEach(loot -> PlayerUtil.giveItem(player, loot));
            if (!player.getAbilities().instabuild) stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    public static class Pirate extends PouchItem {

        public Pirate() {
            super(AquamiraeSounds.ITEM_PIRATE_POUCH_OPEN,
                    Component.translatable("lore.aquamirae.pirate_pouch"),
                    new Properties().stacksTo(16).rarity(Rarity.COMMON));
        }

        @Override
        ResourceLocation getLootTable(ItemStack stack) {
            return Aquamirae.key("gameplay/pirate_pouch");
        }

        @Override
        Component getContent(ItemStack stack) {
            return Component.translatable("lore.aquamirae.pirate_pouch.content");
        }
    }

    public static class Treasure extends PouchItem {

        public Treasure() {
            super(AquamiraeSounds.ITEM_TREASURE_POUCH_OPEN,
                    Component.translatable("lore.aquamirae.treasure_pouch"),
                    new Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
        }

        @Override
        ResourceLocation getLootTable(ItemStack stack) {
            return Aquamirae.key("gameplay/treasure_pouch");
        }

        @Override
        Component getContent(ItemStack stack) {
            return Component.translatable("lore.aquamirae.treasure_pouch.content");
        }
    }
}
