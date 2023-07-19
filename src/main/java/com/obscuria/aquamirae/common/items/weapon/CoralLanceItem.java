
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeUtils;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.common.classes.bonus.Bonus;
import com.obscuria.obscureapi.common.classes.bonus.RegisterBonus;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.UUID;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "weapon")
public class CoralLanceItem extends SwordItem {
	@RegisterAbility public static final Ability PASSIVE;
	@RegisterBonus public static final Bonus BONUS;

	public CoralLanceItem(Settings settings) {
		super(AquamiraeMaterials.CORAL_LANCE, 3, -2.8f, settings);
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		final Multimap<EntityAttribute, EntityAttributeModifier> multimap = super.getAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.ACCURACY, new EntityAttributeModifier(UUID.fromString("AB3F55D3-646C-4F38-A497-1C13A33DB5CF"),
					"Weapon modifier", 0.3, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;

	}

	@Override
	public ItemStack getDefaultStack() {
		final ItemStack stack = new ItemStack(this);
		stack.addEnchantment(Enchantments.UNBREAKING, 4);
		return stack;
	}

	public static float calculateDamageBonus(LivingEntity entity, Entity target, float damage) {
		return AquamiraeUtils.isShipGraveyardEntity(target) ? damage * (0.01f * PASSIVE.getVariable(entity, 1)) : 0;
	}

	static {
		PASSIVE = Ability.create(Aquamirae.MODID, "coral_lance")
				.mod(50)
				.build(CoralLanceItem.class);
		BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "armor")
				.type(Bonus.Type.POWER, Bonus.Operation.PERCENT)
				.value(50).build();
	}
}
