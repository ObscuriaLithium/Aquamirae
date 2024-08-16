package com.obscuria.aquamirae.client.renderer.layer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.model.ModelMaw;
import com.obscuria.aquamirae.common.entity.Maw;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MawEyesLayer extends EyesLayer<Maw, ModelMaw> {

    public MawEyesLayer(RenderLayerParent<Maw, ModelMaw> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(Aquamirae.key("textures/entity/maw_eyes.png"));
    }
}
