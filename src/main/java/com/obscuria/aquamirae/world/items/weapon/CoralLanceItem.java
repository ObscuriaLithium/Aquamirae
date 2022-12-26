
package com.obscuria.aquamirae.world.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CoralLanceItem extends SwordItem implements IClassItem, IAbilityItem, IBonusItem {
	public CoralLanceItem() {
		super(new IItemTier() {
			public int getUses() {
				return 1400;
			}

			public float getSpeed() {
				return 6f;
			}

			public float getAttackDamageBonus() {
				return 10f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 14;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
			}
		}, 3, -2.8f, new Item.Properties().fireResistant().rarity(ObscureRarity.MYTHIC).tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY = new ObscureAbility(this, "coral_lance", ObscureAbility.Cost.NONE, 0, 50);
	public final ObscureBonus BONUS = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.ARMOR, ObscureBonus.Type.POWER, ObscureBonus.Operation.PERCENT, 50);

	public List<ObscureAbility> getObscureAbilities() {
		return Collections.singletonList(ABILITY);
	}

	public List<ObscureBonus> getObscureBonuses() {
		return Collections.singletonList(BONUS);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.WEAPON;
	}

	@Override
	public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> list) {
		if (this.allowdedIn(tab)) list.add(getDefaultInstance());
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlotType.MAINHAND) {
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.ACCURACY.get(), new AttributeModifier(UUID.fromString("AB3F55D3-646C-4F38-A497-1C13A33DB5CF"),
					"Weapon modifier", 0.3, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;

	}

	@Override
	public ItemStack getDefaultInstance() {
		final ItemStack stack = new ItemStack(this);
		stack.enchant(Enchantments.UNBREAKING, 4);
		return stack;
	}
}
