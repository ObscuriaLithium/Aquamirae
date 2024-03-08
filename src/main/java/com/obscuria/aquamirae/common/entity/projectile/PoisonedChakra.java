package com.obscuria.aquamirae.common.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class PoisonedChakra extends Projectile {

	public PoisonedChakra(EntityType<PoisonedChakra> type, Level world) {
		super(type, world);
		this.noCulling = true;
	}

	@Override
	protected void defineSynchedData() {
	}

//	@Override
//	public boolean attack(@NotNull LivingEntity entity) {
//		final boolean attack = super.attack(entity);
//		if (attack) entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
//		return attack;
//	}
//
//	@Override
//	public float getAttackRange() {
//		return 1.3F;
//	}
}
