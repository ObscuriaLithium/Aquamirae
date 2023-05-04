
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.*;
import com.obscuria.obscureapi.common.items.ObscureRarity;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ClassItem(clazz = "aquamirae:sea_wolf", type = "weapon")
public class CoralLanceItem extends SwordItem {
	public CoralLanceItem() {
		super(AquamiraeTiers.CORAL_LANCE, 3, -2.8f, new Item.Properties().fireResistant().rarity(ObscureRarity.MYTHIC).tab(Aquamirae.TAB));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(Aquamirae.MODID, "coral_lance").var(50, "%").build(CoralLanceItem.class);
	@ClassBonus
	public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "armor").type(Bonus.Type.POWER, Bonus.Operation.PERCENT).value(50).build();

	@Override
	public void fillItemCategory(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> list) {
		if (this.allowedIn(tab)) list.add(getDefaultInstance());
	}

	public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.ACCURACY.get(), new AttributeModifier(UUID.fromString("AB3F55D3-646C-4F38-A497-1C13A33DB5CF"),
					"Weapon modifier", 0.3, AttributeModifier.Operation.MULTIPLY_BASE));
			builder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(UUID.fromString("A23F54D3-645C-4F36-A497-9C11A337B5CF"),
					"Weapon modifier", 0.25, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;

	}

	@Override
	public @NotNull ItemStack getDefaultInstance() {
		final ItemStack stack = new ItemStack(this);
		stack.enchant(Enchantments.UNBREAKING, 4);
		return stack;
	}
}
