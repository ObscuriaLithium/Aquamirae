
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.common.effects.*;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AquamiraeMobEffects {
	public static final DeferredRegister<Effect> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, AquamiraeMod.MODID);
	public static final RegistryObject<Effect> CRYSTALLIZATION = REGISTRY.register("crystallization", CrystallizationMobEffect::new);
	public static final RegistryObject<Effect> HEALTH_DECREASE = REGISTRY.register("health_decrease", HealthDecreaseMobEffect::new);
	public static final RegistryObject<Effect> ARMOR_DECREASE = REGISTRY.register("armor_decrease", ArmorDecreaseMobEffect::new);
	public static final RegistryObject<Effect> SWIM_SPEED = REGISTRY.register("swim_speed", SwimSpeedMobEffect::new);
	public static final RegistryObject<Effect> STRONG_ARMOR = REGISTRY.register("strong_armor", StrongArmorMobEffect::new);
	public static final RegistryObject<Effect> ABYSS_BLINDNESS = REGISTRY.register("abyss_blindness", AbyssBlindnessMobEffect::new);
}
