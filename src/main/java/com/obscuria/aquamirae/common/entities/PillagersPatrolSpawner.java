
package com.obscuria.aquamirae.common.entities;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PillagersPatrolSpawner extends HostileEntity {

	public PillagersPatrolSpawner(EntityType<PillagersPatrolSpawner> type, World world) {
		super(type, world);
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		if (getWorld() instanceof ServerWorld server) {
			MobEntity entity1 = new PillagerEntity(EntityType.PILLAGER, server);
			MobEntity entity2 = new PillagerEntity(EntityType.PILLAGER, server);
			MobEntity entity3 = new VindicatorEntity(EntityType.VINDICATOR, server);
			entity1.updatePositionAndAngles(getX() + 0.2, getY(), getZ() + 0.2, getWorld().getRandom().nextFloat() * 360F, 0);
			entity2.updatePositionAndAngles(getX(), getY(), getZ(), getWorld().getRandom().nextFloat() * 360F, 0);
			entity3.updatePositionAndAngles(getX() - 0.2, getY(), getZ() + 0.2, getWorld().getRandom().nextFloat() * 360F, 0);
			entity1.initialize(server, difficulty, spawnReason, null, null);
			entity2.initialize(server, difficulty, spawnReason, null, null);
			entity3.initialize(server, difficulty, spawnReason, null, null);
			getWorld().spawnEntity(entity1);
			getWorld().spawnEntity(entity2);
			getWorld().spawnEntity(entity3);
		}
		if (!getWorld().isClient()) discard();
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public static SpawnRestriction.SpawnPredicate<PillagersPatrolSpawner> getSpawnRules() {
		return HostileEntity::canSpawnIgnoreLightLevel;
	}
}
