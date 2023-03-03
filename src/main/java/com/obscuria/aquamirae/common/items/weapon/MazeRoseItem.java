
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeCreativeTab;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.DynamicProjectile;
import com.obscuria.obscureapi.api.common.DynamicProjectileItem;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "weapon")
@DynamicProjectileItem(mirror = true, distance = true, fastSpin = true)
public class MazeRoseItem extends TieredItem implements Vanishable {
	public MazeRoseItem() {
		super(AquamiraeTiers.MAZE_ROSE, new Item.Properties().tab(AquamiraeMod.TAB).rarity(Rarity.UNCOMMON));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(AquamiraeMod.MODID).description("maze_rose").cost(Ability.Cost.COOLDOWN, 40)
			.variables(5, 40).modifiers("", "s").build(this);

	@Override
	public void fillItemCategory(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> list) {
		super.fillItemCategory(tab, list);
		if (tab == AquamiraeMod.TAB) list.addAll(AquamiraeCreativeTab.mazeRose());
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if (slot == EquipmentSlot.OFFHAND) {
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.CRITICAL_HIT.get(), new AttributeModifier(UUID.fromString("A14F51D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		final ItemStack stack = entity.getItemInHand(hand);
		if (world instanceof ServerLevel) {
			stack.hurt(3, entity.getRandom(), null);
			DynamicProjectile.create(AquamiraeEntities.MAZE_ROSE.get(), entity, world, stack, ABILITY.getVariable(entity, 1), 0F,
					20 * ABILITY.getVariable(entity, 2), 1000);
			entity.getCooldowns().addCooldown(this, 20 * ABILITY.getCost(entity));
			return InteractionResultHolder.success(stack);
		}
		return InteractionResultHolder.pass(stack);
	}
}
