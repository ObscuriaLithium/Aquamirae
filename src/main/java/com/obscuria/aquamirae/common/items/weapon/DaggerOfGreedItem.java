
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DaggerOfGreedItem extends SwordItem implements IClassItem, IAbilityItem {
	public DaggerOfGreedItem() {
		super(new IItemTier() {
			public int getUses() {
				return 100;
			}

			public float getSpeed() {
				return 8f;
			}

			public float getAttackDamageBonus() {
				return 0f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 30;
			}

			public @Nonnull Ingredient getRepairIngredient() {
				return Ingredient.EMPTY;
			}
		}, 3, -2f, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON).tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY_1 = new ObscureAbility(this, "dagger_of_greed_1", ObscureAbility.Cost.NONE, 0);
	public final ObscureAbility ABILITY_2 = new ObscureAbility(this, "dagger_of_greed_2", ObscureAbility.Cost.NONE, 0);
	public final ObscureAbility ABILITY_3 = new ObscureAbility(this, "dagger_of_greed_3", ObscureAbility.Cost.NONE, 0);

	public List<ObscureAbility> getObscureAbilities() {
		return Arrays.asList(ABILITY_3, ABILITY_2, ABILITY_1);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.WEAPON;
	}

	public void inventoryTick(ItemStack stack, @Nonnull World level, @Nonnull Entity entity, int i, boolean flag) {
		if (stack.getDamageValue() != stack.getOrCreateTag().getInt("DamageValue"))
			stack.setDamageValue(stack.getOrCreateTag().getInt("DamageValue"));
	}

	@Override
	public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity entity, @Nonnull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(stack, entity, source);
		final World level = entity.level;
		if (level.isClientSide()) return hurt;
		stack.getOrCreateTag().putInt("DamageValue", stack.getOrCreateTag().getInt("DamageValue") + 1);
		if (entity instanceof AbstractVillagerEntity || entity instanceof AbstractIllagerEntity) {
			ItemEntity emerald = new ItemEntity(level, entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ(),
					new ItemStack(Items.EMERALD));
			emerald.setPickUpDelay(10);
			level.addFreshEntity(emerald);
			if (!entity.isAlive()) {
				ItemEntity emeralds = new ItemEntity(level, entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ(),
						new ItemStack(Items.EMERALD, 3 + new Random().nextInt(5)));
				emeralds.setPickUpDelay(10);
				level.addFreshEntity(emeralds);
			}
			if (entity instanceof AbstractVillagerEntity && new Random().nextInt(20) == 0)
				source.addEffect(new EffectInstance(Effects.BAD_OMEN, 24000, 0));
		}
		return hurt;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@Nonnull ItemStack itemstack) {
		return true;
	}
}
