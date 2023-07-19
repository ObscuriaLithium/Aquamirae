
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.common.classes.ability.context.AbilityContext;
import com.obscuria.obscureapi.common.classes.ability.context.CombatAbilityContext;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.List;
import java.util.UUID;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "weapon")
public class TerribleSwordItem extends SwordItem {
	@RegisterAbility public static final Ability PASSIVE;

	public TerribleSwordItem(Settings settings) {
		super(AquamiraeMaterials.TERRIBLE_SWORD, 3, -3f, settings);
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		final Multimap<EntityAttribute, EntityAttributeModifier> multimap = super.getAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.CRITICAL_HIT, new EntityAttributeModifier(UUID.fromString("AB3F55D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			builder.put(ObscureAPIAttributes.CRITICAL_DAMAGE, new EntityAttributeModifier(UUID.fromString("AA3F55D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 4.0, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity entity, LivingEntity source) {
		final boolean hit = super.postHit(stack, entity, source);
		if (hit) PASSIVE.use(new CombatAbilityContext(source, stack, entity));
		return hit;
	}

	public static boolean hitUser(AbilityContext context, List<Integer> vars) {
		if (context instanceof CombatAbilityContext combat) {
			if (combat.getTarget().isAlive()) combat.getUser().damage(combat.getUser().getDamageSources().dragonBreath(), vars.get(0));
			return true;
		}
		return false;
	}

	static {
		PASSIVE = Ability.create(Aquamirae.MODID, "terrible_sword")
				.action(TerribleSwordItem::hitUser)
				.var(1)
				.build(TerribleSwordItem.class);
	}
}
