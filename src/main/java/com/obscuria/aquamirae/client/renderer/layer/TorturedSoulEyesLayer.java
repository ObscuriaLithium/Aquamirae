package com.obscuria.aquamirae.client.renderer.layer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.model.ModelTorturedSoul;
import com.obscuria.aquamirae.common.entity.TorturedSoul;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TorturedSoulEyesLayer extends EyesLayer<TorturedSoul, ModelTorturedSoul> {

    public TorturedSoulEyesLayer(RenderLayerParent<TorturedSoul, ModelTorturedSoul> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(Aquamirae.key("textures/entity/tortured_soul_eyes.png"));
    }
}
