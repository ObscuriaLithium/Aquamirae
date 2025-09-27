
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.common.items.ObscureRarity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SweetLanceItem extends SwordItem {
	public SweetLanceItem() {
		super(AquamiraeTiers.SWEET_LANCE, 3, -3.0f, new Properties().fireResistant().rarity(ObscureRarity.MYTHIC)
				.food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3f).build()));
	}

	public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("AB3F54D3-645C-4F36-A467-9C11A33DB1CF"),
					"Weapon modifier", 0.25, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
		if (entity instanceof Player player) {
			world.playSound(player, player.getX(), player.getY(), player.getZ(), player.getEatingSound(itemstack), SoundSource.NEUTRAL,
					1.0F, 1.0F + (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.4F);
			final FoodProperties foodProperties = itemstack.getFoodProperties(entity);
			if (foodProperties != null) player.getFoodData().eat(foodProperties.getNutrition(), foodProperties.getSaturationModifier());
		}
		itemstack.setDamageValue(itemstack.getDamageValue() + 10);
		if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) itemstack.shrink(1);
		return itemstack;
	}

	@Override
	public @NotNull ItemStack getDefaultInstance() {
		final ItemStack stack = new ItemStack(this);
		stack.enchant(Enchantments.MOB_LOOTING, 1);
		return stack;
	}
}
