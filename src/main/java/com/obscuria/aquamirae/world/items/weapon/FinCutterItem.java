
package com.obscuria.aquamirae.world.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.classes.Ability;
import com.obscuria.obscureapi.api.classes.ClassAbility;
import com.obscuria.obscureapi.api.classes.ClassItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "weapon")
public class FinCutterItem extends SwordItem {
	public FinCutterItem() {
		super(AquamiraeTiers.FIN_CUTTER, 3, -2f, new Item.Properties().tab(AquamiraeMod.TAB));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(AquamiraeMod.MODID).description("fin_cutter").variables(15, 150).modifiers("%", "%").build(this);
}
