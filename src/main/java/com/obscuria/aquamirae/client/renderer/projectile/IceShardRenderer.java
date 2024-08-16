package com.obscuria.aquamirae.client.renderer.projectile;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.projectile.ModelShard;
import com.obscuria.aquamirae.common.entity.projectile.IceShard;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class IceShardRenderer extends ShardRenderer<IceShard> {
    public static final ResourceLocation TEXTURE = Aquamirae.key("textures/entity/projectiles/ice_shard.png");

    public IceShardRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelShard<>(context.bakeLayer(AquamiraeLayers.ICE_SHARD)));
    }

    @Override
    public ResourceLocation getTextureLocation(IceShard shard) {
        return TEXTURE;
    }
}
