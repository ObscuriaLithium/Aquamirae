
package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.common.classes.ability.context.AbilityContext;
import com.obscuria.obscureapi.common.classes.ability.context.SimpleAbilityContext;
import com.obscuria.obscureapi.common.classes.bonus.Bonus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

import java.util.List;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "armor")
public class ThreeBoltArmorItem extends ArmorItem {
	@RegisterAbility public static final Ability HALFSET;
	@RegisterAbility public static final Ability FULLSET;
	@RegisterAbility public static final Bonus BONUS;

	public ThreeBoltArmorItem(ArmorItem.Type type, Settings settings) {
		super(AquamiraeMaterials.THREE_BOLT_ARMOR, type, settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if (entity instanceof LivingEntity living) HALFSET.use(new SimpleAbilityContext(living, stack));
	}

	static {
		HALFSET = Ability.create(Aquamirae.MODID, "three_bolt_armor_half")
				.action(ThreeBoltArmorItem::applyEffect)
				.build(ThreeBoltArmorItem.class);
		FULLSET = Ability.create(Aquamirae.MODID, "three_bolt_armor_full")
				.build(ThreeBoltArmorItem.class);
		BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "armor")
				.type(Bonus.Type.POWER, Bonus.Operation.PERCENT)
				.value(30)
				.build();
	}

	public static boolean applyEffect(AbilityContext context, List<Integer> vars) {
		if (context.getUser().getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ThreeBoltArmorItem
				&& context.getUser().getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ThreeBoltArmorItem) {
			if (context.getUser().getAir() <= 0) {
				context.getUser().setAir(280);
				context.getStack().damage(10, context.getUser(), e -> {});
				if (!context.getUser().getWorld().isClient())
					context.getUser().getWorld().playSound(null, context.getUser().getBlockPos(), AquamiraeSounds.EFFECT_OXYGEN, SoundCategory.PLAYERS, 1, 1);
			}
			return true;
		}
		return false;
	}
}
