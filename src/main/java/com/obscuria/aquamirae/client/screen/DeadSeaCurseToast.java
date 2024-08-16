package com.obscuria.aquamirae.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class DeadSeaCurseToast implements Toast {
    private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("toast/advancement");
    private static final Component TITLE_TEXT = Component.translatable("toast.aquamirae.curse.title").withStyle(ChatFormatting.RED);
    private static final long DISPLAY_TIME = 5000L;
    private final ItemStack stack;

    public DeadSeaCurseToast(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public Visibility render(GuiGraphics graphics, ToastComponent component, long time) {
        graphics.blitSprite(BACKGROUND_SPRITE, 0, 0, this.width(), this.height());

        final var title = component.getMinecraft().font.split(Component.empty()
                .append(stack.getHoverName())
                .withStyle(stack.getRarity().getStyleModifier()), 125);

        if (time < 2500L) {
            final var color = Mth.floor(Mth.clamp((float) (2500L - time) / 300.0F,
                    0.0F, 1.0F) * 255.0F) << 24 | 67108864;
            graphics.drawString(component.getMinecraft().font, TITLE_TEXT,
                    30, 11, color, false);
        } else {
            final var color = Mth.floor(Mth.clamp((float) (time - 2500L) / 300.0F,
                    0.0F, 1.0F) * 252.0F) << 24 | 67108864;
            var offset = this.height() / 2 - title.size() * 9 / 2;
            for (FormattedCharSequence formattedcharsequence : title) {
                graphics.drawString(component.getMinecraft().font, formattedcharsequence,
                        30, offset, 16777215 | color, false);
                offset += 9;
            }
        }

        graphics.renderFakeItem(stack, 8, 8);
        return time >= DISPLAY_TIME * component.getNotificationDisplayTimeMultiplier() ? Visibility.HIDE : Visibility.SHOW;
    }
}
