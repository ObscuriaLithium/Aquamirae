
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMaw;
import com.obscuria.aquamirae.common.entities.MawEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class MawRenderer extends MobEntityRenderer<MawEntity, ModelMaw> {
	public MawRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelMaw(context.getPart(AquamiraeLayers.MAW)), 0.9f);
		this.addFeature(new EyesFeatureRenderer<>(this) {
			@Override
			public RenderLayer getEyesTexture() {
				return RenderLayer.getEyes(Aquamirae.key("textures/entity/maw_overlay.png"));
			}
		});
		this.addFeature(new MawItemRenderer(this));
	}

	@Override
	public Identifier getTexture(MawEntity entity) {
		return Aquamirae.key("textures/entity/maw.png");
	}

	public static class MawItemRenderer extends FeatureRenderer<MawEntity, ModelMaw> {
		public MawItemRenderer(FeatureRendererContext<MawEntity, ModelMaw> layer) {
			super(layer);
		}

		public void render(MatrixStack stack, VertexConsumerProvider source, int i1, MawEntity maw, float f1, float f2, float f3, float f4, float f5, float f6) {
			if (!maw.getStackInMouth().isEmpty()) {
				stack.push();
				this.getContextModel().translate(stack);
				stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(100.0F));
				stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0.0F));
				stack.translate(0.0D, -0.8D, 0.02D);
				stack.scale(0.7f, 0.7f, 0.7f);
				MinecraftClient.getInstance().gameRenderer.firstPersonRenderer.renderItem(maw, maw.getStackInMouth(),
						ModelTransformationMode.FIXED, false, stack, source, i1);
				stack.pop();
			}
		}
	}
}
