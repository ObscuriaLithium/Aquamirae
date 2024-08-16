
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.core.registry.RegistryHandler;
import com.obscuria.core.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface AquamiraePotions {
	RegistryHandler<Potion> HANDLER = RegistryHandler.create(Registries.POTION, Aquamirae.MODID);

	RegistrySupplier<Potion> SPECTRAL_POTION = simple("spectral_potion", () -> new Potion(
			new MobEffectInstance(MobEffects.GLOWING, 2400, 0, false, true)));
	RegistrySupplier<Potion> POTION_OF_TENACITY = simple("potion_of_tenacity", () -> new Potion(
			new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 0, false, true),
			new MobEffectInstance(MobEffects.ABSORPTION, 2400, 2, false, true),
			new MobEffectInstance(MobEffects.CONFUSION, 400, 1, false, true)));

	private static RegistrySupplier<Potion> simple(String key, Supplier<Potion> supplier) {
		return HANDLER.register(key, supplier);
	}
}
