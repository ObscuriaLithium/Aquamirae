
package com.obscuria.aquamirae.common.entity;

import com.mojang.serialization.Dynamic;
import com.obscuria.aquamirae.registry.*;
import com.obscuria.core.client.graphic.world.Trail3D;
import com.obscuria.core.common.easing.Easing;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

@IceMazeEntity
public class GoldenMoth extends AbstractMoth {

	public GoldenMoth(EntityType<GoldenMoth> type, Level world) {
		super(type, world);
	}

	public boolean shouldPanic() {
		return this.getBrain().hasMemoryValue(AquamiraeMemoryModules.GOLDEN_MOTH_PLAYER.get());
	}

	public boolean isInsideJar() {
		return this.level().getBlockState(this.blockPosition()).is(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR.get());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Brain<GoldenMoth> getBrain() {
		return (Brain<GoldenMoth>)super.getBrain();
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide && tickCount % 5 == 0) {
			final var motion = this.getDeltaMovement().scale(10f);
			level().addParticle(AquamiraeParticleTypes.SHINE.get(),
					getX() + random.triangle(0, 0.2f),
					getY() + random.triangle(0, 0.2f),
					getZ() + random.triangle(0, 0.2f),
					motion.x(), motion.y(), motion.z());
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (super.hurt(source, amount)) {
			if (this.level() instanceof ServerLevel server)
				server.sendParticles(AquamiraeParticleTypes.SHINE.get(),
						this.getX(), this.getY(), this.getZ(), 6,
						0.05, 0.05, 0.05, 0.8);
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("all")
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
										MobSpawnType type, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		if (level instanceof ServerLevel server
				&& type == MobSpawnType.NATURAL
				&& random.nextFloat() <= 0.2f) {
			final var cursed = AquamiraeEntityTypes.CURSED_MOTH.get().create(server);
			ForgeEventFactory.onFinalizeSpawn(this, server, difficulty, type, null, null);
			server.addFreshEntity(cursed);
			if (cursed.isAddedToWorld())
				cursed.moveTo(position());
		}
		return super.finalizeSpawn(level, difficulty, type, data, tag);
	}

	@Override
	protected Brain.Provider<GoldenMoth> brainProvider() {
		return GoldenMothAI.brainProvider();
	}

	@Override
	protected Brain<GoldenMoth> makeBrain(Dynamic<?> dynamic) {
		return GoldenMothAI.makeBrain(this.brainProvider().makeBrain(dynamic));
	}

	@Override
	protected void customServerAiStep() {
		this.getBrain().tick((ServerLevel)this.level(), this);
	}

	@Override
	protected Trail3D createTrail() {
		return Trail3D.create(20, 3)
				.setWidth(0.05f, Easing.EASE_OUT_CUBIC.mergeOut(Easing.EASE_OUT_CUBIC, 0.05f))
				.setColor(0xffffff90, 0x00ffcc00, Easing.EASE_OUT_CUBIC);
	}

	@Override
	protected ItemStack getJarItem() {
		return AquamiraeItems.GOLDEN_MOTH_IN_A_JAR.get().getDefaultInstance();
	}
}
