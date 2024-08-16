package com.obscuria.aquamirae.client.renderer.layer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.model.ModelCaptainCornelia;
import com.obscuria.aquamirae.common.entity.CaptainCornelia;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CorneliaHelmetLayer extends EyesLayer<CaptainCornelia, ModelCaptainCornelia> {

    public CorneliaHelmetLayer(RenderLayerParent<CaptainCornelia, ModelCaptainCornelia> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(Aquamirae.key("textures/entity/captain_cornelia_helmet.png"));
    }
}
