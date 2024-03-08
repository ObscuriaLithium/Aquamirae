
package com.obscuria.aquamirae.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelPycnogonida;
import com.obscuria.aquamirae.common.entity.Pycnogonida;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class PycnogonidaRenderer extends MobRenderer<Pycnogonida, ModelPycnogonida> {
	public PycnogonidaRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelPycnogonida(context.bakeLayer(AquamiraeLayers.PYCNOGONIDA)), 0.5f);
	}

	@Override
	public void render(Pycnogonida entity, float f1, float f2, PoseStack pose, MultiBufferSource source, int i) {
		super.render(entity, f1, f2, pose, source, i);
	}

	@Override
	public ResourceLocation getTextureLocation(Pycnogonida entity) {
		return Aquamirae.key("textures/entity/pycnogonida.png");
	}

	@Nullable
	@Override
	protected RenderType getRenderType(Pycnogonida entity, boolean b1, boolean b2, boolean b3) {
		final var texture = this.getTextureLocation(entity);
		if (b2) {
			return RenderType.itemEntityTranslucentCull(texture);
		} else if (b1) {
			return RenderType.entityTranslucent(texture);
		} else {
			return b3 ? RenderType.outline(texture) : null;
		}
	}
}
