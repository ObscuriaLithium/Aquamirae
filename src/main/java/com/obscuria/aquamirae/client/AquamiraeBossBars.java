package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.core.common.extension.screen.BossBarExtension;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.ApiStatus;

@OnlyIn(Dist.CLIENT)
@ApiStatus.NonExtendable
public interface AquamiraeBossBars {

    final class CaptainCornelia implements BossBarExtension.Renderer {

        @Override
        public int render(GuiGraphics graphics, BossEvent bossEvent, int x, int y, int increment, LivingEntity entity) {
            graphics.blit(Aquamirae.key("textures/gui/bossbars/cornelia.png"),
                    x - 37, y - 20, 0, 0,
                    256, 64, 256, 64);
            return increment;
        }
    }
}
