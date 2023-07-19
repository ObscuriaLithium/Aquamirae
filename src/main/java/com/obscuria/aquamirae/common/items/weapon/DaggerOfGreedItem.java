
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.obscureapi.api.utils.WorldUtils;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.common.classes.ability.context.AbilityContext;
import com.obscuria.obscureapi.common.classes.ability.context.CombatAbilityContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "weapon")
public class DaggerOfGreedItem extends SwordItem {
	@RegisterAbility public static final Ability PASSIVE_1;
	@RegisterAbility public static final Ability PASSIVE_2;
	@RegisterAbility public static final Ability PASSIVE_3;

	public DaggerOfGreedItem(Settings settings) {
		super(AquamiraeMaterials.DAGGER_OF_GREED, 3, -2f, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity entity, LivingEntity source) {
		final boolean hit = super.postHit(stack, entity, source);
		if (entity.getWorld().isClient()) return hit;
		if (hit) {
			final CombatAbilityContext context = new CombatAbilityContext(source, stack, entity);
			PASSIVE_1.use(context);
			PASSIVE_2.use(context);
			PASSIVE_3.use(context);
		}
		return hit;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if (stack.getDamage() != stack.getOrCreateNbt().getInt("DamageValue"))
			stack.setDamage(stack.getOrCreateNbt().getInt("DamageValue"));
		if (stack.getDamage() <= 0 && entity instanceof LivingEntity living)
			stack.damage(1, living, e -> {});
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}

	public static boolean dropEmeralds(AbilityContext context, List<Integer> vars) {
		if (context instanceof CombatAbilityContext combat && (combat.getTarget() instanceof VillagerEntity || combat.getTarget() instanceof IllagerEntity)) {
			WorldUtils.dropItem(combat.getTarget().getWorld(), combat.getTarget().getBlockPos(), Items.EMERALD.getDefaultStack());
			if (!combat.getTarget().isAlive())
				WorldUtils.dropItem(combat.getTarget().getWorld(), combat.getTarget().getBlockPos(),
						new ItemStack(Items.EMERALD, new Random().nextInt(3, 8)));
			return true;
		}
		return false;
	}

	public static boolean applyBadOmen(AbilityContext context, List<Integer> vars) {
		if (context instanceof CombatAbilityContext combat && combat.getTarget() instanceof VillagerEntity && new Random().nextInt(0, 20) == 20) {
			combat.getUser().addStatusEffect(new StatusEffectInstance(StatusEffects.BAD_OMEN, 24000, 0));
			return true;
		}
		return false;
	}

	public static boolean updateDurability(AbilityContext context, List<Integer> vars) {
		context.getStack().getOrCreateNbt().putInt("DamageValue", context.getStack().getOrCreateNbt().getInt("DamageValue") + 1);
		return true;
	}

	static {
		PASSIVE_1 = Ability.create(Aquamirae.MODID, "dagger_of_greed_1")
				.action(DaggerOfGreedItem::dropEmeralds)
				.build(DaggerOfGreedItem.class);
		PASSIVE_2 = Ability.create(Aquamirae.MODID, "dagger_of_greed_2")
				.action(DaggerOfGreedItem::applyBadOmen)
				.build(DaggerOfGreedItem.class);
		PASSIVE_3 = Ability.create(Aquamirae.MODID, "dagger_of_greed_3")
				.action(DaggerOfGreedItem::updateDurability)
				.style(Ability.Style.EPIC)
				.build(DaggerOfGreedItem.class);
	}
}
