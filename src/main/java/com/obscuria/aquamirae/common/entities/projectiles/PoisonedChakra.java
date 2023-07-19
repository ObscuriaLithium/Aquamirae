
package com.obscuria.aquamirae.common.entities.projectiles;

import com.obscuria.obscureapi.common.entities.DynamicProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

public class PoisonedChakra extends DynamicProjectileEntity {

	public PoisonedChakra(EntityType<PoisonedChakra> type, World world) {
		super(type, world);
		ignoreCameraFrustum = true;
	}

	@Override
	public boolean attack(LivingEntity entity) {
		final boolean attack = super.attack(entity);
		if (attack) entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 1));
		return attack;
	}

	@Override
	public float getAttackRange() {
		return 1.3F;
	}
}
