
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;

@ClassItem(clazz = "aquamirae:sea_wolf", type = "weapon")
public class PoisonedBladeItem extends SwordItem {
	public PoisonedBladeItem() {
		super(AquamiraeTiers.POISONED_BLADE, 3, -1f, new Item.Properties().tab(Aquamirae.TAB));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(Aquamirae.MODID, "poisoned_blade").cost(Ability.Cost.Type.COOLDOWN, 10).action(
			(stack, entity, target, context, values) -> {
				if (target == null) return false;
				target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * values.get(0), 1));
				return true;
			}).var(5, "s").build(PoisonedBladeItem.class);

	@Override
	public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(stack, entity, source);
		if (hurt) ABILITY.use(stack, source, entity, null);
		return hurt;
	}
}
