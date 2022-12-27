package com.obscuria.aquamirae.client.renderers;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GlowingLayer<T extends Entity, M extends EntityModel<T>> extends AbstractEyesLayer<T, M> {
    private final ResourceLocation LOCATION;

    public GlowingLayer(IEntityRenderer<T, M> renderer, ResourceLocation location) {
        super(renderer);
        this.LOCATION = location;
    }

    @Nonnull
    public RenderType renderType() {
        return RenderType.eyes(LOCATION);
    }
}
