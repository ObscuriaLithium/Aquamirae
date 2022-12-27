
package com.obscuria.aquamirae.client.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelCaptainCornelia;
import com.obscuria.aquamirae.world.entities.CaptainCornelia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class CaptainCorneliaRenderer extends MobRenderer<CaptainCornelia, ModelCaptainCornelia<CaptainCornelia>> {
	public CaptainCorneliaRenderer(EntityRendererManager context) {
		super(context, new ModelCaptainCornelia<>(), 0.5f);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(AquamiraeMod.MODID,"textures/entity/captain_cornelia_overlay.png")));
		this.addLayer(new CaptainCorneliaItemLayer<>(this));
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull CaptainCornelia entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/captain_cornelia.png");
	}

	@OnlyIn(Dist.CLIENT)
	public static class CaptainCorneliaItemLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
		public CaptainCorneliaItemLayer(IEntityRenderer<T, M> renderer) {
			super(renderer);
		}

		@Override
		public void render(@Nonnull MatrixStack pose, @Nonnull IRenderTypeBuffer source, int i1, T entity, float f1, float f2, float f3, float f4, float f5, float f6) {
			ItemStack stack = entity.getItemBySlot(EquipmentSlotType.MAINHAND);
			if (!stack.isEmpty()) {
				pose.pushPose();
				if (this.getParentModel() instanceof ModelCaptainCornelia) ((ModelCaptainCornelia<?>)this.getParentModel()).translateToHand(HandSide.RIGHT, pose);
				pose.scale(1F, 1F, 1F);
				pose.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
				pose.mulPose(Vector3f.YP.rotationDegrees(180.0F));
				pose.translate(0.0D, 0.1D, 0.0D);
				Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false, pose, source, i1);
				pose.popPose();
			}
		}
	}
}
