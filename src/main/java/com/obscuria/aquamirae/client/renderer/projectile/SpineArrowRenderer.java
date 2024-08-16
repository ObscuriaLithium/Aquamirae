package com.obscuria.aquamirae.client.renderer.projectile;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.projectile.SpineArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpineArrowRenderer extends ArrowRenderer<SpineArrow> {
    public static final ResourceLocation TEXTURE = Aquamirae.key("textures/entity/projectiles/spine_arrow.png");

    public SpineArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SpineArrow arrow) {
        return TEXTURE;
    }
}
