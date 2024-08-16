package com.obscuria.aquamirae.client.renderer.layer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.model.ModelAnglerfish;
import com.obscuria.aquamirae.common.entity.Anglerfish;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnglerfishEscaLayer extends EyesLayer<Anglerfish, ModelAnglerfish> {

    public AnglerfishEscaLayer(RenderLayerParent<Anglerfish, ModelAnglerfish> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(Aquamirae.key("textures/entity/anglerfish_esca.png"));
    }
}
