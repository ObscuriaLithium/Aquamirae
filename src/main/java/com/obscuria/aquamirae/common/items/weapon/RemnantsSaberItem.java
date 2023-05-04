
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ClassItem(clazz = "aquamirae:sea_wolf", type = "weapon")
public class RemnantsSaberItem extends SwordItem {
	public RemnantsSaberItem() {
		super(AquamiraeTiers.REMNANTS_SABER, 3, -2f, new Item.Properties().tab(Aquamirae.TAB));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(Aquamirae.MODID, "remnants_saber").var(100, "%").build(RemnantsSaberItem.class);

	public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.CRITICAL_HIT.get(), new AttributeModifier(UUID.fromString("AB3F55D3-644C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.1, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		} else {
			return multimap;
		}
	}
}
