
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.SwordItem;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "weapon")
public class FinCutterItem extends SwordItem {
	@RegisterAbility public static final Ability PASSIVE;

	public FinCutterItem(Settings settings) {
		super(AquamiraeMaterials.FIN_CUTTER, 3, -2f, settings);
	}

	public static float calculateDamageBonus(LivingEntity entity, float damage) {
		final int emptyHearts = (int) Math.floor((entity.getMaxHealth() - entity.getHealth()) / 2);
		return damage * Math.min(emptyHearts * PASSIVE.getVariable(entity, 1) * 0.01F, PASSIVE.getVariable(entity, 2) * 0.01F);
	}

	static {
		PASSIVE = Ability.create(Aquamirae.MODID, "fin_cutter")
				.mod(15)
				.mod(150)
				.build(FinCutterItem.class);
	}
}
