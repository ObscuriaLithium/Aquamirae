
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelCaptainCornelia;
import com.obscuria.aquamirae.common.entities.CaptainCorneliaEntity;
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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;

public class CaptainCorneliaRenderer extends MobEntityRenderer<CaptainCorneliaEntity, ModelCaptainCornelia> {
	public CaptainCorneliaRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelCaptainCornelia(context.getPart(AquamiraeLayers.CAPTAIN_CORNELIA)), 0.5f);
		this.addFeature(new EyesFeatureRenderer<>(this) {
			@Override
			public RenderLayer getEyesTexture() {
				return RenderLayer.getEyes(Aquamirae.key("textures/entity/captain_cornelia_overlay.png"));
			}
		});
		this.addFeature(new CaptainCorneliaItemRenderer(this));
	}

	@Override
	public Identifier getTexture(CaptainCorneliaEntity entity) {
		return Aquamirae.key("textures/entity/captain_cornelia.png");
	}

	public static class CaptainCorneliaItemRenderer extends FeatureRenderer<CaptainCorneliaEntity, ModelCaptainCornelia> {
		public CaptainCorneliaItemRenderer(FeatureRendererContext<CaptainCorneliaEntity, ModelCaptainCornelia> layer) {
			super(layer);
		}

		public void render(@NotNull MatrixStack stack, VertexConsumerProvider provider, int i1, CaptainCorneliaEntity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
			final ItemStack right = entity.getEquippedStack(EquipmentSlot.MAINHAND);
			if (!right.isEmpty()) {
				stack.push();
				this.getContextModel().translateToHand(Arm.RIGHT, stack);
				stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
				stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
				stack.translate(0.0D, 0.1D, 0.0D);
				MinecraftClient.getInstance().gameRenderer.firstPersonRenderer.renderItem(entity, right,
						ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, false, stack, provider, i1);
				stack.pop();
			}
			final ItemStack left = entity.getEquippedStack(EquipmentSlot.OFFHAND);
			if (!left.isEmpty()) {
				stack.push();
				this.getContextModel().translateToHand(Arm.LEFT, stack);
				stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45.0F));
				stack.translate(0.0D, -0.15D, -0.65D);
				MinecraftClient.getInstance().gameRenderer.firstPersonRenderer.renderItem(entity, left,
						ModelTransformationMode.THIRD_PERSON_LEFT_HAND, false, stack, provider, i1);
				stack.pop();
			}
		}
	}
}
