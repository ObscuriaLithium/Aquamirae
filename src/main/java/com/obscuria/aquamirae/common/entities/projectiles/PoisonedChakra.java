
package com.obscuria.aquamirae.common.entities.projectiles;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.api.common.DynamicProjectile;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

public class PoisonedChakra extends DynamicProjectile {
	public PoisonedChakra(PlayMessages.SpawnEntity packet, Level world) {
		this(AquamiraeEntities.POISONED_CHAKRA.get(), world);
	}

	public PoisonedChakra(EntityType<PoisonedChakra> type, Level world) {
		super(type, world);
		this.noCulling = true;
	}

	@Override
	public boolean attack(LivingEntity entity) {
		final boolean attack = super.attack(entity);
		if (attack) entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
		return attack;
	}

	@Override
	public float getAttackRange() {
		return 1.3F;
	}
}
