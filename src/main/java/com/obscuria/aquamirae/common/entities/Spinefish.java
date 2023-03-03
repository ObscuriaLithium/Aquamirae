package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.api.ShipGraveyardEntity;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class Spinefish extends AbstractSchoolingFish {

    public Spinefish(EntityType<? extends Spinefish> type, Level level) {
        super(type, level);
    }

    public Spinefish(PlayMessages.SpawnEntity packet, Level level) {
        this(AquamiraeEntities.SPINEFISH.get(), level);
    }

    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(AquamiraeItems.SPINEFISH_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.COD_HURT;
    }

    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    public static SpawnPlacements.SpawnPredicate<Spinefish> getSpawnRules() {
        return WaterAnimal::checkSurfaceWaterAnimalSpawnRules;
    }
}
