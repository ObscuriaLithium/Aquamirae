
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.api.common.DynamicProjectile;
import com.obscuria.obscureapi.api.common.DynamicProjectileItem;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ClassItem(clazz = "aquamirae:sea_wolf", type = "weapon")
@DynamicProjectileItem(mirror = true, distance = true, fastSpin = true)
public class PoisonedChakraItem extends TieredItem implements Vanishable {
	public PoisonedChakraItem() {
		super(AquamiraeTiers.POISONED_CHAKRA, new Item.Properties());
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(Aquamirae.MODID, "poisoned_chakra").cost(Ability.Cost.Type.COOLDOWN, 30).action(
			(stack, entity, target, context, values) -> {
				stack.hurt(3, entity.getRandom(), null);
				DynamicProjectile.create(AquamiraeEntities.POISONED_CHAKRA.get(), entity, entity.level(), stack, values.get(0), 0F,
						20 * values.get(1), 1000);
				return true;
			}).var(3, "").var(30, "s").build(PoisonedBladeItem.class);

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if (slot == EquipmentSlot.OFFHAND) {
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.CRITICAL_HIT.get(), new AttributeModifier(UUID.fromString("A33F51D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.1, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		final ItemStack stack = entity.getItemInHand(hand);
		if (world instanceof ServerLevel) {
			ABILITY.use(stack, entity, null, null);
			return InteractionResultHolder.success(stack);
		}
		return InteractionResultHolder.pass(stack);
	}
}
