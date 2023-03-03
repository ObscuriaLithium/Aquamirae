
package com.obscuria.aquamirae.common.entities.chakras;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.world.entities.ChakraEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class PoisonedChakra extends ChakraEntity {
	public PoisonedChakra(FMLPlayMessages.SpawnEntity packet, World world) {
		this(AquamiraeEntities.POISONED_CHAKRA.get(), world);
	}

	public PoisonedChakra(EntityType<PoisonedChakra> type, World world) {
		super(type, world);
		this.noCulling = true;
	}

	@Override public void attackChakra(LivingEntity entity) {
		super.attackChakra(entity);
		entity.addEffect(new EffectInstance(Effects.POISON, 60, 1));
	}

	@Override public float getAttackRange() {
		return 1.3F;
	}
}
