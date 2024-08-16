
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelCaptainCornelia;
import com.obscuria.aquamirae.client.renderer.layer.CorneliaHelmetLayer;
import com.obscuria.aquamirae.client.renderer.layer.CorneliaItemsLayer;
import com.obscuria.aquamirae.common.entity.CaptainCornelia;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CaptainCorneliaRenderer extends MobRenderer<CaptainCornelia, ModelCaptainCornelia> {
	public CaptainCorneliaRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelCaptainCornelia(context.bakeLayer(AquamiraeLayers.CAPTAIN_CORNELIA)), 0.5f);
		this.addLayer(new CorneliaItemsLayer(this, context.getItemInHandRenderer()));
		this.addLayer(new CorneliaHelmetLayer(this));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull CaptainCornelia entity) {
		return Aquamirae.key("textures/entity/captain_cornelia.png");
	}

}
