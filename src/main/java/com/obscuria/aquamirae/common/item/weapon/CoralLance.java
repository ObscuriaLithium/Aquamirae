
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.common.item.ObscureRarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantments;

public class CoralLance extends SwordItem {

	public CoralLance() {
		super(AquamiraeTiers.CORAL_LANCE, 3, -2.8f,
				new Properties().fireResistant().rarity(ObscureRarity.MYTHIC));
	}

//	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
//		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
//		if (slot == EquipmentSlot.MAINHAND) {
//			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
//			builder.putAll(multimap);
//			builder.put(ObscureAPIAttributes.ACCURACY, new AttributeModifier(UUID.fromString("AB3F55D3-646C-4F38-A497-1C13A33DB5CF"),
//					"Weapon modifier", 0.3, AttributeModifier.Operation.MULTIPLY_BASE));
//			builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("A23F54D3-645C-4F36-A497-9C11A337B5CF"),
//					"Weapon modifier", 0.25, AttributeModifier.Operation.MULTIPLY_BASE));
//			return builder.build();
//		}
//		return multimap;
//
//	}

	@Override
	public ItemStack getDefaultInstance() {
		final ItemStack stack = new ItemStack(this);
		stack.enchant(Enchantments.UNBREAKING, 4);
		return stack;
	}

//	public static float calculateDamageBonus(Entity target, LivingEntity entity, ItemStack stack, float damage) {
//		final AbilityContext context
//		return AquamiraeUtils.isShipGraveyardEntity(target) ? damage * (0.01f * PASSIVE.getVariable(entity, 1)) : 0;
//	}
//
//	static {
//		PASSIVE = Ability.create(Aquamirae.MODID, "coral_lance")
//				.mod(50)
//				.build(CoralLance.class);
//		BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "armor")
//				.type(Bonus.Type.POWER, Bonus.Operation.PERCENT)
//				.value(50).build();
//	}
}
