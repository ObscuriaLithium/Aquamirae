
package com.obscuria.aquamirae.common.entity;

import com.google.common.collect.Streams;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.network.StackCursedPacket;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.core.api.graphic.world.Trail3D;
import com.obscuria.core.api.util.easing.Easing;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

@IceMazeEntity
public class CursedMoth extends AbstractMoth {
	protected @Nullable LivingEntity chasedEntity = null;

	protected static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat()
			.selector(entity -> entity instanceof Player player && !player.isCreative() && !player.isSpectator());

	public CursedMoth(EntityType<CursedMoth> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerMothGoals() {
		this.goalSelector.addGoal(1, new CursedMothStrollGoal(this, 1.2f, 14));
	}

	@Override
	protected Trail3D createTrail() {
		return Trail3D.create(20, 3)
				.setWidth(0.05f, Easing.EASE_OUT_CUBIC.mergeOut(Easing.EASE_OUT_CUBIC, 0.05f))
				.setColor(0xffff2050, 0x00ff0010, Easing.EASE_OUT_CUBIC);
	}

	@Override
	protected ItemStack getJar() {
		return AquamiraeItems.CURSED_MOTH_IN_A_JAR.get().getDefaultInstance();
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (level().isClientSide) return;
		if (isDeadOrDying()) return;

		chasedEntity = null;
		for (var player : level().getNearbyPlayers(TARGETING_CONDITIONS, this,
						getBoundingBox().inflate(18)).stream()
				.filter(this::hasLineOfSight)
				.sorted(Comparator.comparingDouble(player -> position().distanceTo(player.getEyePosition())))
				.toList()) {
			final var playerPos = player.getEyePosition();
			if (chasedEntity == null) {
				addDeltaMovement(position().vectorTo(playerPos)
						.normalize()
						.scale(0.01f));
				if (tickCount % 20 == 0)
					navigation.moveTo(player, 1);
				lookControl.setLookAt(player);
				chasedEntity = player;
			}
			if (position().distanceTo(playerPos) <= 0.8 && curse(player))
				discard();
		}
	}

	protected boolean curse(Player player) {
		final var inventory = player.getInventory();
		final var items = Streams.concat(
						inventory.items.stream(),
						inventory.armor.stream(),
						inventory.offhand.stream())
				.filter(stack -> DeadSeaCurse.canBeCursed(stack) && !DeadSeaCurse.isCursed(stack))
				.toList();
		if (items.isEmpty()) return false;
		final var stack = items.get(random.nextInt(0, items.size()));
		DeadSeaCurse.makeCursed(stack);
		if (player instanceof ServerPlayer serverPlayer)
			Aquamirae.CHANNEL.send(new StackCursedPacket(stack.copy()),
					PacketDistributor.PLAYER.with(serverPlayer));
		return true;
	}

	protected static class CursedMothStrollGoal extends MothStrollGoal {
		protected final CursedMoth moth;

		public CursedMothStrollGoal(CursedMoth moth, double speed, int interval) {
			super(moth, speed, interval);
			this.moth = moth;
		}

		@Override
		public boolean canUse() {
			return moth.chasedEntity == null && super.canUse();
		}

		@Override
		public boolean canContinueToUse() {
			return moth.chasedEntity == null && super.canContinueToUse();
		}
	}
}
