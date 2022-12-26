
package com.obscuria.aquamirae.world.items.weapon;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.world.classes.ObscureRarity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class SweetLanceItem extends SwordItem {
	public SweetLanceItem() {
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
				return Ingredient.of(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get().getDefaultInstance());
			}
		}, 3, -3.0f, new Properties().fireResistant().rarity(ObscureRarity.MYTHIC)
				.food((new Food.Builder()).nutrition(2).saturationMod(0.3f).build()));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity entity) {
		if (entity instanceof PlayerEntity) {
			final PlayerEntity player = (PlayerEntity) entity;
			world.playSound(player, player.getX(), player.getY(), player.getZ(), player.getEatingSound(itemstack), SoundCategory.NEUTRAL,
					1.0F, 1.0F + (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.4F);
			final Food foodProperties = itemstack.getItem().getFoodProperties();
			if (foodProperties != null) player.getFoodData().eat(foodProperties.getNutrition(), foodProperties.getSaturationModifier());
		}
		itemstack.setDamageValue(itemstack.getDamageValue() + 10);
		if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) itemstack.shrink(1);
		return itemstack;
	}

	@Override
	public ItemStack getDefaultInstance() {
		final ItemStack stack = new ItemStack(this);
		stack.enchant(Enchantments.MOB_LOOTING, 1);
		return stack;
	}
}
