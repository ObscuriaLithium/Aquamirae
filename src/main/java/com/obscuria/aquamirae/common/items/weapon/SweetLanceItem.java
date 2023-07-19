
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class SweetLanceItem extends SwordItem {
	public SweetLanceItem(Settings settings) {
		super(AquamiraeMaterials.SWEET_LANCE, 3, -3.0f, settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof PlayerEntity player) {
			world.playSound(player, player.getX(), player.getY(), player.getZ(), player.getEatSound(stack), SoundCategory.NEUTRAL,
					1.0F, 1.0F + (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.4F);
			final FoodComponent foodComponent = stack.getItem().getFoodComponent();
			if (foodComponent != null) player.getHungerManager().add(foodComponent.getHunger(), foodComponent.getSaturationModifier());
		}
		stack.damage(10, user.getRandom(), null);
		return stack;
	}

	@Override
	public ItemStack getDefaultStack() {
		final ItemStack stack = new ItemStack(this);
		stack.addEnchantment(Enchantments.LOOTING, 1);
		return stack;
	}
}
