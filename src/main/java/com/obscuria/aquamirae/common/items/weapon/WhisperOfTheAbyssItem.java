
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ClassItem(clazz = "aquamirae:sea_wolf", type = "weapon")
public class WhisperOfTheAbyssItem extends SwordItem {
	public WhisperOfTheAbyssItem() {
		super(AquamiraeTiers.WHISPER_OF_tHE_ABYSS, 3, -3.2f, new Item.Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(Aquamirae.MODID, "whisper_of_the_abyss").action(
			(stack, entity, target, context, values) -> {
				if (target == null) return false;
				final MobEffectInstance EFFECT = target.getEffect(AquamiraeMobEffects.ARMOR_DECREASE.get());
				if (EFFECT != null) target.addEffect(new MobEffectInstance(AquamiraeMobEffects.ARMOR_DECREASE.get(),
						20 * values.get(0), Math.min(4, EFFECT.getAmplifier() + 1), false, false));
				else target.addEffect(new MobEffectInstance(AquamiraeMobEffects.ARMOR_DECREASE.get(),
						20 * values.get(0), 0, false, false));
				return true;
			}).var(10, "s").build(WhisperOfTheAbyssItem.class);

	public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("AB3F54D3-645C-4F36-A497-9C11A33DB5CF"),
					"Weapon modifier", 0.25, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	@Override
	public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(stack, entity, source);
		if (hurt) ABILITY.use(stack, source, entity, null);
		return hurt;
	}
}
