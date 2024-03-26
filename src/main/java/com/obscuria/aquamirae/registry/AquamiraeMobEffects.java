
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.effect.*;
import com.obscuria.core.api.registry.RegistryHandler;
import com.obscuria.core.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface AquamiraeMobEffects {
	RegistryHandler<MobEffect> HANDLER = RegistryHandler.create(Registries.MOB_EFFECT, Aquamirae.MODID);

	RegistrySupplier<MobEffect> DEPTHS_FURY = simple("depths_fury", DepthsFuryMobEffect::new);
	RegistrySupplier<MobEffect> HYDROPHOBIA = simple("hydrophobia", HydrophobiaModEffect::new);
	RegistrySupplier<MobEffect> CRYSTALLIZATION = simple("crystallization", CrystallizationMobEffect::new);
	RegistrySupplier<MobEffect> TERRIBLE_ARMOR = simple("terrible_armor", TerribleArmorMobEffect::new);
	RegistrySupplier<MobEffect> THREE_BOLT_ARMOR = simple("three_bolt_armor", ThreeeBoltArmorMobEffect::new);
	RegistrySupplier<MobEffect> DIVIDER = simple("divider", DividerMobEffect::new);
	RegistrySupplier<MobEffect> WHISPER_OF_THE_ABYSS = simple("whisper_of_the_abyss", WhisperOfTheAbyssMobEffect::new);
	RegistrySupplier<MobEffect> STRONG_ARMOR = simple("strong_armor", StrongArmorMobEffect::new);
	RegistrySupplier<MobEffect> ABYSS_BLINDNESS = simple("abyss_blindness", AbyssBlindnessMobEffect::new);

	private static RegistrySupplier<MobEffect> simple(String key, Supplier<MobEffect> supplier) {
		return HANDLER.register(key, supplier);
	}
}
