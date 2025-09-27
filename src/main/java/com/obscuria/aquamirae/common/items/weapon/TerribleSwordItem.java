
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ClassItem(clazz = "aquamirae:sea_wolf", type = "weapon")
public class TerribleSwordItem extends SwordItem {
	public TerribleSwordItem() {
		super(AquamiraeTiers.TERRIBLE_SWORD, 3, -3f, new Item.Properties());
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(Aquamirae.MODID, "terrible_sword").action(
			(stack, entity, target, context, values) -> {
				if (target == null) return false;
				if (target.getHealth() > 0) entity.hurt(entity.damageSources().dragonBreath(), values.get(0));
				return true;
			}).var(1).build(TerribleSwordItem.class);

	public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.CRITICAL_HIT.get(), new AttributeModifier(UUID.fromString("AB3F55D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.5, AttributeModifier.Operation.MULTIPLY_BASE));
			builder.put(ObscureAPIAttributes.CRITICAL_DAMAGE.get(), new AttributeModifier(UUID.fromString("AA3F55D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 4.0, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	@Override
	public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(stack, entity, source);
		if (hurt) ABILITY.use(stack, source, entity, null);
		return hurt;
	}
}
