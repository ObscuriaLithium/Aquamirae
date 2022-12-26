
package com.obscuria.aquamirae.world.entities;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class PillagersPatrol extends MonsterEntity {
	public PillagersPatrol(FMLPlayMessages.SpawnEntity packet, World world) {
		this(AquamiraeEntities.PILLAGERS_PATROL.get(), world);
	}

	public PillagersPatrol(EntityType<PillagersPatrol> type, World world) {
		super(type, world);
	}

	@Override public void baseTick() {
		if (this.level instanceof ServerWorld) {
			final ServerWorld server = (ServerWorld) this.level;
			MobEntity entity1 = new PillagerEntity(EntityType.PILLAGER, server);
			MobEntity entity2 = new PillagerEntity(EntityType.PILLAGER, server);
			MobEntity entity3 = new VindicatorEntity(EntityType.VINDICATOR, server);
			entity1.moveTo(this.getX() + 0.2, this.getY(), this.getZ() + 0.2, this.level.getRandom().nextFloat() * 360F, 0);
			entity2.moveTo(this.getX(), this.getY(), this.getZ(), this.level.getRandom().nextFloat() * 360F, 0);
			entity3.moveTo(this.getX() - 0.2, this.getY(), this.getZ() + 0.2, this.level.getRandom().nextFloat() * 360F, 0);
			entity1.finalizeSpawn(server, this.level.getCurrentDifficultyAt(entity1.blockPosition()), SpawnReason.NATURAL, null, null);
			entity2.finalizeSpawn(server, this.level.getCurrentDifficultyAt(entity2.blockPosition()), SpawnReason.NATURAL, null, null);
			entity3.finalizeSpawn(server, this.level.getCurrentDifficultyAt(entity3.blockPosition()), SpawnReason.NATURAL, null, null);
			this.level.addFreshEntity(entity1);
			this.level.addFreshEntity(entity2);
			this.level.addFreshEntity(entity3);
		}
		if (!this.level.isClientSide()) this.remove();
		super.baseTick();
	}
}
