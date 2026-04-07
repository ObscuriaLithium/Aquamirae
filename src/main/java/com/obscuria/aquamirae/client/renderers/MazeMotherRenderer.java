
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.renderers.layers.OverlayLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import com.obscuria.aquamirae.common.entities.MazeMother;
import com.obscuria.aquamirae.client.models.ModelMazeMother;

public class MazeMotherRenderer extends MobRenderer<MazeMother, ModelMazeMother> {

    public static final ResourceLocation TEXTURE = Aquamirae.identifier("textures/entity/maze_mother.png");
    public static final ResourceLocation OVERLAY_TEXTURE = Aquamirae.identifier("textures/entity/maze_mother_overlay.png");

    public MazeMotherRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelMazeMother(context.bakeLayer(AquamiraeLayers.MAZE_MOTHER)), 1f);
        this.addLayer(new OverlayLayer<>(this, OVERLAY_TEXTURE));
    }

    @Override
    public ResourceLocation getTextureLocation(MazeMother entity) {
        return TEXTURE;
    }
}
