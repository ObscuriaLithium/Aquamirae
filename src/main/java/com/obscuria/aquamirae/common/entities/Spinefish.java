package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.api.ShipGraveyardEntity;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

@ShipGraveyardEntity
public class Spinefish extends AbstractGroupFishEntity {

    public Spinefish(EntityType<? extends Spinefish> type, World level) {
        super(type, level);
    }

    public Spinefish(FMLPlayMessages.SpawnEntity packet, World level) {
        this(AquamiraeEntities.SPINEFISH.get(), level);
    }

    public ItemStack getBucketItemStack() {
        return new ItemStack(AquamiraeItems.SPINEFISH_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }
}
