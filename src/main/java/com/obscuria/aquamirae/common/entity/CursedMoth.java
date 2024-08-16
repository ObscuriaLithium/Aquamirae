
package com.obscuria.aquamirae.common.entity;

import com.google.common.collect.Streams;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.network.StackCursedPacket;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.core.client.graphic.world.Trail3D;
import com.obscuria.core.common.easing.Easing;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

@IceMazeEntity
public class CursedMoth extends AbstractMoth {

	public CursedMoth(EntityType<CursedMoth> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new CurseTargetGoal(this, 1.6f, false));
		this.goalSelector.addGoal(1, new MothStrollGoal(this, 1.2f, 14));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
	}

	@Override
	protected Trail3D createTrail() {
		return Trail3D.create(20, 3)
				.setWidth(0.05f, Easing.EASE_OUT_CUBIC.mergeOut(Easing.EASE_OUT_CUBIC, 0.05f))
				.setColor(0xffff2050, 0x00ff0010, Easing.EASE_OUT_CUBIC);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (this.getTarget() instanceof Player player)
			this.lookControl.setLookAt(player);
	}

	@Override
	protected ItemStack getJarItem() {
		return AquamiraeItems.CURSED_MOTH_IN_A_JAR.get().getDefaultInstance();
	}

	private static class CurseTargetGoal extends MeleeAttackGoal {
		private final CursedMoth moth;

		public CurseTargetGoal(CursedMoth moth, float speed, boolean alwaysFollow) {
			super(moth, speed, alwaysFollow);
			this.moth = moth;
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity entity) {
			if (this.canPerformAttack(entity)
					&& entity instanceof Player player
					&& DeadSeaCurse.canBeCursed(player)
					&& curse(player))
				moth.discard();
		}

		private boolean curse(Player player) {
			final var inventory = player.getInventory();
			final var items = Streams.concat(inventory.items.stream(), inventory.armor.stream(), inventory.offhand.stream())
					.filter(stack -> DeadSeaCurse.canBeCursed(stack) && !DeadSeaCurse.isCursed(stack))
					.toList();
			if (items.isEmpty()) return false;
			final var stack = items.get(moth.getRandom().nextInt(0, items.size()));
			DeadSeaCurse.makeCursed(stack);
			if (player instanceof ServerPlayer serverPlayer)
				Aquamirae.CHANNEL.send(new StackCursedPacket(stack.copy()),
						PacketDistributor.PLAYER.with(serverPlayer));
			return true;
		}
	}
}
