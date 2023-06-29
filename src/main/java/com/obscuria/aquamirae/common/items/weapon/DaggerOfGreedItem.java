
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@ClassItem(clazz = "aquamirae:sea_wolf", type = "weapon")
public class DaggerOfGreedItem extends SwordItem {
	public DaggerOfGreedItem() {
		super(AquamiraeTiers.DAGGER_OF_GREED, 3, -2f, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON));
	}

	@ClassAbility
	public final Ability ABILITY_1 = Ability.create(Aquamirae.MODID, "dagger_of_greed_1").build(DaggerOfGreedItem.class);
	@ClassAbility
	public final Ability ABILITY_2 = Ability.create(Aquamirae.MODID, "dagger_of_greed_2").build(DaggerOfGreedItem.class);
	@ClassAbility
	public final Ability ABILITY_3 = Ability.create(Aquamirae.MODID, "dagger_of_greed_3").style(Ability.Style.EPIC).build(DaggerOfGreedItem.class);

	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
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
