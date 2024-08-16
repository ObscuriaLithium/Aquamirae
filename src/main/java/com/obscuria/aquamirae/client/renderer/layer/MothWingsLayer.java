package com.obscuria.aquamirae.client.renderer.layer;

import com.obscuria.aquamirae.client.model.ModelMoth;
import com.obscuria.aquamirae.common.entity.AbstractMoth;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MothWingsLayer<M extends AbstractMoth> extends EyesLayer<M, ModelMoth<M>> {
	private final ResourceLocation texture;

	public MothWingsLayer(RenderLayerParent<M, ModelMoth<M>> parent, ResourceLocation texture) {
		super(parent);
		this.texture = texture;
	}

	@Override
	public RenderType renderType() {
		return RenderType.eyes(texture);
	}
}