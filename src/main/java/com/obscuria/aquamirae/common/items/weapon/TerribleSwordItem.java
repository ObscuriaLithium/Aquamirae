
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TerribleSwordItem extends SwordItem implements IClassItem, IAbilityItem {
	public TerribleSwordItem() {
		super(new IItemTier() {
			public int getUses() {
				return 750;
			}

			public float getSpeed() {
				return 4f;
			}

			public float getAttackDamageBonus() {
				return 2f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 18;
			}

			public @Nonnull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.ANGLERS_FANG.get()), new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
			}
		}, 3, -3f, new Item.Properties().tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY = new ObscureAbility(this, "terrible_sword", ObscureAbility.Cost.NONE, 0, 1);

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
			builder.put(ObscureAPIAttributes.CRITICAL_HIT.get(), new AttributeModifier(UUID.fromString("AB3F55D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.5, AttributeModifier.Operation.MULTIPLY_BASE));
			builder.put(ObscureAPIAttributes.CRITICAL_DAMAGE.get(), new AttributeModifier(UUID.fromString("AA3F55D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 2.0, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	@Override
	public boolean hurtEnemy(@Nonnull ItemStack itemstack, LivingEntity entity, @Nonnull LivingEntity source) {
		if (entity.getHealth() > 0) source.hurt(DamageSource.DRAGON_BREATH, ABILITY.getAmount(source, 0));
		return super.hurtEnemy(itemstack, entity, source);
	}
}
