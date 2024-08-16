
package com.obscuria.aquamirae.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelMoth;
import com.obscuria.aquamirae.client.renderer.layer.MothWingsLayer;
import com.obscuria.aquamirae.common.entity.CursedMoth;
import com.obscuria.core.client.graphic.world.Trail3DRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CursedMothRenderer extends MobRenderer<CursedMoth, ModelMoth<CursedMoth>> {
	public CursedMothRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMoth<>(context.bakeLayer(AquamiraeLayers.MOTH)), 0.2f);
		this.addLayer(new MothWingsLayer<>(this, Aquamirae.key("textures/entity/cursed_moth_wings.png")));
	}

	@Override
	public void render(CursedMoth entity, float f1, float f2, PoseStack pose, MultiBufferSource bufferSource, int light) {
		super.render(entity, f1, f2, pose, bufferSource, light);
		Trail3DRenderer.of(entity.trail).render(entity, pose, bufferSource, Minecraft.getInstance().getPartialTick());
	}

	@Override
	public ResourceLocation getTextureLocation(CursedMoth entity) {
		return Aquamirae.key("textures/entity/cursed_moth.png");
	}
}
