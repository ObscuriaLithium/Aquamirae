
package com.obscuria.aquamirae.world.entities.chakras;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.world.entities.ChakraEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

public class MazeRose extends ChakraEntity {
	public MazeRose(PlayMessages.SpawnEntity packet, Level world) {
		this(AquamiraeEntities.MAZE_ROSE.get(), world);
	}

	public MazeRose(EntityType<MazeRose> type, Level world) {
		super(type, world);
	}

	@Override public void moveChakra(Entity entity, Vec3 center) {
		final float radius = this.getRadius(6F);
		final float speed = this.getSpinSpeed(0.03F);
		final float offset = this.getSpinOffset();
		final Vec3 orbit = new Vec3((center.x + Math.cos(speed + offset) * radius) + Math.sin(speed * 6F + offset) * (radius * 0.5F), center.y,
				(center.z + Math.sin(speed + offset) * radius) + Math.cos(speed * 6F + offset) * (radius * 0.5F));
		this.moveTo(orbit);
	}

	@Override public float getAttackRange() {
		return 1.3F;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes();
	}
}
