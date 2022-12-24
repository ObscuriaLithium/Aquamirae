
package com.obscuria.aquamirae.world.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DaggerOfGreedItem extends SwordItem implements IClassItem, IAbilityItem {
	public DaggerOfGreedItem() {
		super(new Tier() {
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

			public @NotNull Ingredient getRepairIngredient() {
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

	public void inventoryTick(ItemStack stack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
		if (stack.getDamageValue() != stack.getOrCreateTag().getInt("DamageValue"))
			stack.setDamageValue(stack.getOrCreateTag().getInt("DamageValue"));
	}

	@Override
	public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(stack, entity, source);
		final Level level = entity.getLevel();
		if (level.isClientSide()) return hurt;
		stack.getOrCreateTag().putInt("DamageValue", stack.getOrCreateTag().getInt("DamageValue") + 1);
		if (entity instanceof AbstractVillager || entity instanceof AbstractIllager) {
			ItemEntity emerald = new ItemEntity(level, entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ(),
					new ItemStack(Items.EMERALD));
			emerald.setPickUpDelay(10);
			level.addFreshEntity(emerald);
			if (!entity.isAlive()) {
				ItemEntity emeralds = new ItemEntity(level, entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ(),
						new ItemStack(Items.EMERALD, new Random().nextInt(3, 8)));
				emeralds.setPickUpDelay(10);
				level.addFreshEntity(emeralds);
			}
			if (entity instanceof AbstractVillager && new Random().nextInt(0, 20) == 20)
				source.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 24000, 0));
		}
		return hurt;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@NotNull ItemStack itemstack) {
		return true;
	}
}
