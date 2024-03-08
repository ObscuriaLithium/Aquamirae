
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.core.api.registry.RegistryHandler;
import com.obscuria.core.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AquamiraeAttributes {
	RegistryHandler<Attribute> HANDLER = RegistryHandler.create(Registries.ATTRIBUTE, Aquamirae.MODID);

	RegistrySupplier<Attribute> DEPTHS_FURY = simple("depths_fury", 0, 0, 100000, true);

	@SuppressWarnings("all")
	private static RegistrySupplier<Attribute> simple(String key, float base, float min, float max, boolean syncable) {
		return HANDLER.register(key, () -> new RangedAttribute("attribute.aquamirae." + key,
				base, min, max).setSyncable(syncable));
	}
}
