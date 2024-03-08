
package com.obscuria.aquamirae.common.entity;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.core.api.graphic.world.Trail3D;
import com.obscuria.core.api.util.easing.Easing;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

@IceMazeEntity
public class GoldenMoth extends AbstractMoth {

	public GoldenMoth(EntityType<GoldenMoth> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerMothGoals() {
		this.goalSelector.addGoal(1, new MothStrollGoal(this, 1, 20));
	}

	@Override
	protected Trail3D createTrail() {
		return Trail3D.create(20, 3)
				.setWidth(0.05f, Easing.EASE_OUT_CUBIC.mergeOut(Easing.EASE_OUT_CUBIC, 0.05f))
				.setColor(0xffffff90, 0x00ffcc00, Easing.EASE_OUT_CUBIC);
	}

	@Override
	protected ItemStack getJar() {
		return AquamiraeItems.GOLDEN_MOTH_IN_A_JAR.get().getDefaultInstance();
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (level().isClientSide && tickCount % 5 == 0)
			level().addParticle(AquamiraeParticles.SHINE.get(),
					getX() + random.triangle(0, 0.2f),
					getY() + random.triangle(0, 0.2f),
					getZ() + random.triangle(0, 0.2f),
					0, 0, 0);
	}

	@SuppressWarnings("all")
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficulty, MobSpawnType spawnType,
										@Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
		if (levelAccessor instanceof ServerLevel server
				&& spawnType == MobSpawnType.NATURAL
				&& random.nextFloat() <= 0.2f) {
			final var cursed = AquamiraeEntities.CURSED_MOTH.get().create(server);
			cursed.moveTo(position());
			cursed.finalizeSpawn(server, difficulty, spawnType, null, null);
			server.addFreshEntity(cursed);
		}
		return groupData;
	}
}
