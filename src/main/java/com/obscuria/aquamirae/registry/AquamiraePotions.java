
package com.obscuria.aquamirae.registry;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import com.obscuria.aquamirae.AquamiraeMod;

public class AquamiraePotions {
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, AquamiraeMod.MODID);
	public static final RegistryObject<Potion> SPECTRAL_POTION = REGISTRY.register("spectral_potion",
			() -> new Potion(new MobEffectInstance(MobEffects.GLOWING, 2400, 0, false, true)));
	public static final RegistryObject<Potion> POTION_OF_TENACITY = REGISTRY.register("potion_of_tenacity",
			() -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 0, false, true),
					new MobEffectInstance(MobEffects.ABSORPTION, 2400, 2, false, true),
					new MobEffectInstance(MobEffects.CONFUSION, 400, 1, false, true)));
}
