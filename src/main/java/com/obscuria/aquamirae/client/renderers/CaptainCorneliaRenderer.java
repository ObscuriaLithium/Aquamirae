
package com.obscuria.aquamirae.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelCaptainCornelia;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class CaptainCorneliaRenderer extends MobRenderer<CaptainCornelia, ModelCaptainCornelia> {
	public CaptainCorneliaRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelCaptainCornelia(context.bakeLayer(ModelCaptainCornelia.LAYER_LOCATION)), 0.5f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(AquamiraeMod.MODID,"textures/entity/captain_cornelia_overlay.png"));
			}
		});
		this.addLayer(new CaptainCorneliaItemLayer<>(this));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull CaptainCornelia entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/captain_cornelia.png");
	}

	@OnlyIn(Dist.CLIENT)
	public static class CaptainCorneliaItemLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
		public CaptainCorneliaItemLayer(RenderLayerParent<T, M> layer) {
			super(layer);
		}

		public void render(@NotNull PoseStack pose, @NotNull MultiBufferSource source, int i1, T entity, float f1, float f2, float f3, float f4, float f5, float f6) {
			ItemStack stack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
			if (!stack.isEmpty()) {
				pose.pushPose();
				if (this.getParentModel() instanceof ModelCaptainCornelia model) model.translateToHand(HumanoidArm.RIGHT, pose);
				pose.scale(1F, 1F, 1F);
				pose.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
				pose.mulPose(Vector3f.YP.rotationDegrees(180.0F));
				pose.translate(0.0D, 0.1D, 0.0D);
				Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(entity, stack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false, pose, source, i1);
				pose.popPose();
			}
		}
	}
}
