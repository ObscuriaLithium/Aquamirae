
package com.obscuria.aquamirae.common.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.level.Level;

public class PillagersPatrol extends Monster {

	public PillagersPatrol(EntityType<PillagersPatrol> type, Level world) {
		super(type, world);
	}

	@Override
	@SuppressWarnings("all")
	public void baseTick() {
		if (this.level() instanceof ServerLevel server) {
			Mob entity1 = new Pillager(EntityType.PILLAGER, server);
			Mob entity2 = new Pillager(EntityType.PILLAGER, server);
			Mob entity3 = new Vindicator(EntityType.VINDICATOR, server);
			entity1.moveTo(this.getX() + 0.2, this.getY(), this.getZ() + 0.2, this.level().getRandom().nextFloat() * 360F, 0);
			entity2.moveTo(this.getX(), this.getY(), this.getZ(), this.level().getRandom().nextFloat() * 360F, 0);
			entity3.moveTo(this.getX() - 0.2, this.getY(), this.getZ() + 0.2, this.level().getRandom().nextFloat() * 360F, 0);
			entity1.finalizeSpawn(server, this.level().getCurrentDifficultyAt(entity1.blockPosition()), MobSpawnType.NATURAL, null, null);
			entity2.finalizeSpawn(server, this.level().getCurrentDifficultyAt(entity2.blockPosition()), MobSpawnType.NATURAL, null, null);
			entity3.finalizeSpawn(server, this.level().getCurrentDifficultyAt(entity3.blockPosition()), MobSpawnType.NATURAL, null, null);
			this.level().addFreshEntity(entity1);
			this.level().addFreshEntity(entity2);
			this.level().addFreshEntity(entity3);
		}
		if (!this.level().isClientSide()) this.discard();
		super.baseTick();
	}

	public static SpawnPlacements.SpawnPredicate<PillagersPatrol> getSpawnRules() {
		return Monster::checkAnyLightMonsterSpawnRules;
	}
}
