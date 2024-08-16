package com.obscuria.aquamirae.client.renderer.projectile;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.projectile.ModelShard;
import com.obscuria.aquamirae.common.entity.projectile.AbyssalShard;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AbyssalShardRenderer extends ShardRenderer<AbyssalShard> {
    public static final ResourceLocation TEXTURE = Aquamirae.key("textures/entity/projectiles/abyssal_shard.png");

    public AbyssalShardRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelShard<>(context.bakeLayer(AquamiraeLayers.ABYSSAL_SHARD)));
    }

    @Override
    public ResourceLocation getTextureLocation(AbyssalShard shard) {
        return TEXTURE;
    }
}
