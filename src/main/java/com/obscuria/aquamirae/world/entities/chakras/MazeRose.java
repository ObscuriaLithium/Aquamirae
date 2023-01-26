
package com.obscuria.aquamirae.world.entities.chakras;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.world.entities.ChakraEntity;
import net.minecraft.world.entity.EntityType;
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

	@Override
	public void updateMotion() {
		final Vec3 center = this.OWNER.position().add(0.0, (double)this.OWNER.getBbHeight() * 0.33, 0.0);
		final float radius = this.getRadius();
		final float speed = this.getSpinSpeed();
		final float offset = this.getSpinOffset();
		final Vec3 orbit = new Vec3((center.x + Math.cos(speed + offset) * radius) + Math.sin(speed * 6F + offset) * (radius * 0.5F), center.y,
				(center.z + Math.sin(speed + offset) * radius) + Math.cos(speed * 6F + offset) * (radius * 0.5F));
		this.moveTo(orbit);
	}

	public float getAttackRange() {
		return 1.3F;
	}

	@Override
	protected float getDefaultRadius() {
		return 6f;
	}

	@Override
	protected float getDefaultSpinSpeed() {
		return 0.03f;
	}
}
