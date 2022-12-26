
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AquamiraePotions {
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTION_TYPES, AquamiraeMod.MODID);
	public static final RegistryObject<Potion> SPECTRAL_POTION = REGISTRY.register("spectral_potion",
			() -> new Potion(new EffectInstance(Effects.GLOWING, 2400, 0, false, true)));
	public static final RegistryObject<Potion> POTION_OF_TENACITY = REGISTRY.register("potion_of_tenacity",
			() -> new Potion(new EffectInstance(Effects.DAMAGE_BOOST, 2400, 0, false, true),
					new EffectInstance(Effects.ABSORPTION, 2400, 2, false, true),
					new EffectInstance(Effects.CONFUSION, 400, 1, false, true)));
}
