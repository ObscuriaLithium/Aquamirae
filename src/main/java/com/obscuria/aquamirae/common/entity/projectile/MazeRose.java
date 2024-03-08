
package com.obscuria.aquamirae.common.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class MazeRose extends Projectile {

	public MazeRose(EntityType<MazeRose> type, Level world) {
		super(type, world);
	}

//	@Override
//	public void updateMotion() {
//		if (owner == null) return;
//		final Vec3 center = owner.position().add(0.0, (double)owner.getBbHeight() * 0.33, 0.0);
//		final float radius = this.getRadius();
//		final float speed = this.getSpinSpeed();
//		final float offset = this.getSpinOffset();
//		final Vec3 orbit = new Vec3((center.x + Math.cos(speed + offset) * radius) + Math.sin(speed * 6F + offset) * (radius * 0.5F), center.y,
//				(center.z + Math.sin(speed + offset) * radius) + Math.cos(speed * 6F + offset) * (radius * 0.5F));
//		this.moveTo(orbit);
//	}
//
//	public float getAttackRange() {
//		return 1.3F;
//	}
//
//	@Override
//	protected float getDefaultRadius() {
//		return 6f;
//	}
//
//	@Override
//	protected float getDefaultSpinSpeed() {
//		return 0.03f;
//	}

	@Override
	protected void defineSynchedData() {}
}
