
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;

@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "weapon")
public class PoisonedBladeItem extends SwordItem {
	public PoisonedBladeItem() {
		super(AquamiraeTiers.POISONED_BLADE, 3, -1f, new Item.Properties().tab(AquamiraeMod.TAB));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(AquamiraeMod.MODID).description("poisoned_blade").cost(Ability.Cost.COOLDOWN, 10)
			.variables(5).modifiers("s").build(this);

	@Override
	public boolean hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(itemstack, entity, source);
		if (hurt)
			if (source instanceof Player player) if (!player.getCooldowns().isOnCooldown(this)) {
					player.getCooldowns().addCooldown(this, 20 * ABILITY.getCost(source));
					entity.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * ABILITY.getVariable(source, 1), 1)); }
			else entity.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * ABILITY.getVariable(source, 1), 1));
		return hurt;
	}
}
