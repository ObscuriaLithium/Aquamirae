
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.effects.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AquamiraeEffects {
	CrystallizationEffect CRYSTALLIZATION = new CrystallizationEffect();
	HealthDecreaseEffect HEALTH_DECREASE = new HealthDecreaseEffect();
	ArmorDecreaseEffect ARMOR_DECREASE = new ArmorDecreaseEffect();
	StrongArmorEffect STRONG_ARMOR = new StrongArmorEffect();
	AbyssBlindnessEffect ABYSS_BLINDNESS = new AbyssBlindnessEffect();

	static void register() {
		Registry.register(Registries.STATUS_EFFECT, Aquamirae.key("crystallization"), CRYSTALLIZATION);
		Registry.register(Registries.STATUS_EFFECT, Aquamirae.key("health_decrease"), HEALTH_DECREASE);
		Registry.register(Registries.STATUS_EFFECT, Aquamirae.key("armor_decrease"), ARMOR_DECREASE);
		Registry.register(Registries.STATUS_EFFECT, Aquamirae.key("strong_armor"), STRONG_ARMOR);
		Registry.register(Registries.STATUS_EFFECT, Aquamirae.key("abyss_blindness"), ABYSS_BLINDNESS);
	}
}
