
package com.obscuria.aquamirae.common.entities.projectiles;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.world.entities.ChakraEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class MazeRose extends ChakraEntity {
	public MazeRose(FMLPlayMessages.SpawnEntity packet, World world) {
		this(AquamiraeEntities.MAZE_ROSE.get(), world);
	}

	public MazeRose(EntityType<MazeRose> type, World world) {
		super(type, world);
	}

	@Override public void moveChakra(Entity entity, Vector3d center) {
		final float radius = this.getRadius(6F);
		final float speed = this.getSpinSpeed(0.03F);
		final float offset = this.getSpinOffset();
		final Vector3d orbit = new Vector3d((center.x + Math.cos(speed + offset) * radius) + Math.sin(speed * 6F + offset) * (radius * 0.5F), center.y,
				(center.z + Math.sin(speed + offset) * radius) + Math.cos(speed * 6F + offset) * (radius * 0.5F));
		this.moveTo(orbit);
	}

	@Override public float getAttackRange() {
		return 1.3F;
	}
}
