package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.PillagersPatrol;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@SuppressWarnings("all")
public class PillagersPatrolRenderer extends MobRenderer<PillagersPatrol, SlimeModel<PillagersPatrol>> {
    public PillagersPatrolRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel<>(context.bakeLayer(ModelLayers.SLIME)), 0);
    }

    @Override
    public ResourceLocation getTextureLocation(PillagersPatrol entity) {
        return Aquamirae.key("textures/entity/blank.png");
    }
}
