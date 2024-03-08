package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.core.api.extension.BossEventStyles;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface AquamiraeBossBars {

    final class CaptainCornelia implements BossEventStyles.Renderer {
        public static final ResourceLocation TEXTURE = Aquamirae.key("textures/gui/bossbars/cornelia.png");

        @Override
        public int render(GuiGraphics graphics, BossEvent bossEvent, int x, int y, int increment, LivingEntity entity) {
            graphics.blit(TEXTURE, x - 37, y - 20, 0, 0,
                    256, 64, 256, 64);
            return increment;
        }
    }
}
