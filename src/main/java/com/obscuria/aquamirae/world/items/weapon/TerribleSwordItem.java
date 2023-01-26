
package com.obscuria.aquamirae.world.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.api.classes.Ability;
import com.obscuria.obscureapi.api.classes.ClassAbility;
import com.obscuria.obscureapi.api.classes.ClassItem;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "weapon")
public class TerribleSwordItem extends SwordItem {
	public TerribleSwordItem() {
		super(new Tier() {
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

			public @NotNull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.ANGLERS_FANG.get()), new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
			}
		}, 3, -3f, new Item.Properties().tab(AquamiraeMod.TAB));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.Builder.create(AquamiraeMod.MODID).description("terrible_sword").variables(1).build(this);

	public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
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
	public boolean hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
		if (entity.getHealth() > 0) source.hurt(DamageSource.DRAGON_BREATH, ABILITY.getVariable(source, 1));
		return super.hurtEnemy(itemstack, entity, source);
	}
}
