
package com.obscuria.aquamirae.common.entities.projectiles;

import com.obscuria.obscureapi.common.entities.CompoundProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MazeRose extends CompoundProjectileEntity {

	public MazeRose(EntityType<MazeRose> type, World world) {
		super(type, world);
	}

	@Override
	public void updateMotion() {
		if (owner == null) return;
		final Vec3d center = owner.getPos().add(0.0, owner.getHeight() * 0.33, 0.0);
		final float radius = getRadius();
		final float speed = getSpinSpeed();
		final float offset = getSpinOffset();
		final Vec3d orbit = new Vec3d((center.x + Math.cos(speed + offset) * radius) + Math.sin(speed * 6F + offset) * (radius * 0.5F), center.y,
				(center.z + Math.sin(speed + offset) * radius) + Math.cos(speed * 6F + offset) * (radius * 0.5F));
		this.setPosition(orbit);
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
