
package com.obscuria.aquamirae.world.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.api.classes.Ability;
import com.obscuria.obscureapi.api.classes.ClassAbility;
import com.obscuria.obscureapi.api.classes.ClassItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "weapon")
public class WhisperOfTheAbyssItem extends SwordItem {
	public WhisperOfTheAbyssItem() {
		super(new Tier() {
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

			public @NotNull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
						new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
			}
		}, 3, -3.2f, new Item.Properties().fireResistant().rarity(Rarity.EPIC).tab(AquamiraeMod.TAB));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.Builder.create(AquamiraeMod.MODID).description("whisper_of_the_abyss").variables(10).modifiers("s").build(this);

	public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(UUID.fromString("AB3F54D3-645C-4F36-A497-9C11A33DB5CF"),
					"Weapon modifier", 0.25, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	@Override
	public boolean hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(itemstack, entity, source);
		if (hurt) {
			final MobEffectInstance EFFECT = entity.getEffect(AquamiraeMobEffects.ARMOR_DECREASE.get());
			if (EFFECT != null) entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.ARMOR_DECREASE.get(),
						20 * ABILITY.getVariable(source, 1), Math.min(4, EFFECT.getAmplifier() + 1), false, false));
			else entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.ARMOR_DECREASE.get(),
						20 * ABILITY.getVariable(source, 1), 0, false, false));
		}
		return hurt;
	}
}
