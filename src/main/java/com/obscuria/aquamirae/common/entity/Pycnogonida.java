package com.obscuria.aquamirae.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

@IceMazeEntity
public class Pycnogonida extends Monster {
    public Pycnogonida(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }
}
