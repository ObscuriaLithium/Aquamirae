package com.obscuria.aquamirae.client.renderer.layer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.model.ModelMaw;
import com.obscuria.aquamirae.client.model.ModelMazeMother;
import com.obscuria.aquamirae.common.entity.Maw;
import com.obscuria.aquamirae.common.entity.MazeMother;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MazeMotherCrystalsLayer extends EyesLayer<MazeMother, ModelMazeMother> {

    public MazeMotherCrystalsLayer(RenderLayerParent<MazeMother, ModelMazeMother> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(Aquamirae.key("textures/entity/maze_mother_crystals.png"));
    }
}
