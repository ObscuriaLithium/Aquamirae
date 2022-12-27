
package com.obscuria.aquamirae.world.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RemnantsSaberItem extends SwordItem implements IClassItem, IAbilityItem {
	public RemnantsSaberItem() {
		super(new IItemTier() {
			public int getUses() {
				return 100;
			}

			public float getSpeed() {
				return 4f;
			}

			public float getAttackDamageBonus() {
				return 1f;
			}

			public int getLevel() {
				return 1;
			}

			public int getEnchantmentValue() {
				return 5;
			}

			public @Nonnull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHARP_BONES.get()),
						new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
			}
		}, 3, -2f, new Item.Properties().tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY = new ObscureAbility(this, "remnants_saber", ObscureAbility.Cost.NONE, 0, 100);

	public List<ObscureAbility> getObscureAbilities() {
		return Collections.singletonList(ABILITY);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.WEAPON;
	}

	@Nonnull
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@Nonnull EquipmentSlotType slot) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlotType.MAINHAND) {
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
