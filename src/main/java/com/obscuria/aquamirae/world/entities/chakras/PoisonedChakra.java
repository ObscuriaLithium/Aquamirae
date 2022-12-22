
package com.obscuria.aquamirae.world.entities.chakras;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.world.entities.ChakraEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

public class PoisonedChakra extends ChakraEntity {
	public PoisonedChakra(PlayMessages.SpawnEntity packet, Level world) {
		this(AquamiraeEntities.POISONED_CHAKRA.get(), world);
	}

	public PoisonedChakra(EntityType<PoisonedChakra> type, Level world) {
		super(type, world);
		this.noCulling = true;
	}

	@Override public void attackChakra(LivingEntity entity) {
		super.attackChakra(entity);
		entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
	}

	@Override public float getAttackRange() {
		return 1.3F;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes();
	}
}
