package com.obscuria.aquamirae.client.renderer.projectile;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.projectile.AbyssalArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AbyssalArrowRenderer extends ArrowRenderer<AbyssalArrow> {
    public static final ResourceLocation TEXTURE = Aquamirae.key("textures/entity/projectiles/abyssal_arrow.png");

    public AbyssalArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AbyssalArrow arrow) {
        return TEXTURE;
    }
}
