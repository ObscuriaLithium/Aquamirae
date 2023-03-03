
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class FinCutterItem extends SwordItem implements IClassItem, IAbilityItem {
	public FinCutterItem() {
		super(new IItemTier() {
			public int getUses() {
				return 1000;
			}

			public float getSpeed() {
				return 8f;
			}

			public float getAttackDamageBonus() {
				return 2f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 20;
			}

			public @Nonnull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.DIAMOND), new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
			}
		}, 3, -2f, new Item.Properties().tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY = new ObscureAbility(this, "fin_cutter", ObscureAbility.Cost.NONE, 0, 15, 150);

	public List<ObscureAbility> getObscureAbilities() {
		return Collections.singletonList(ABILITY);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.WEAPON;
	}
}
