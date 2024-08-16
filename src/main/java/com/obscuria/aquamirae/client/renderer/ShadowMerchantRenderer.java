package com.obscuria.aquamirae.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.common.entity.npc.ShadowMerchant;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShadowMerchantRenderer extends MobRenderer<ShadowMerchant, VillagerModel<ShadowMerchant>> {
    private static final ResourceLocation TEXTURE = Aquamirae.key("textures/entity/shadow_merchant.png");

    public ShadowMerchantRenderer(EntityRendererProvider.Context context) {
        super(context, new VillagerModel<>(context.bakeLayer(AquamiraeLayers.SHADOW_MERCHANT)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowMerchant merchant) {
        return TEXTURE;
    }

    @Override
    protected void scale(ShadowMerchant merchant, PoseStack pose, float partialTick) {
        final var scale = 0.9375F;
        pose.scale(scale, scale, scale);
    }
}
