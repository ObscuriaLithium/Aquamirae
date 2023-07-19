
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.SwordItem;

import java.util.UUID;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "weapon")
public class RemnantsSaberItem extends SwordItem {
	@RegisterAbility public static final Ability PASSIVE;

	public RemnantsSaberItem(Settings settings) {
		super(AquamiraeMaterials.REMNANTS_SABER, 3, -2f, settings);
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		final Multimap<EntityAttribute, EntityAttributeModifier> multimap = super.getAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.CRITICAL_HIT, new EntityAttributeModifier(UUID.fromString("AB3F55D3-644C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		} else {
			return multimap;
		}
	}

	public static float calculateDamageBonus(LivingEntity entity, float damage) {
		return entity.isTouchingWater() ? damage * (0.01f * PASSIVE.getVariable(entity, 1)) : 0;
	}

	static {
		PASSIVE = Ability.create(Aquamirae.MODID, "remnants_saber")
				.mod(100)
				.build(RemnantsSaberItem.class);
	}
}
