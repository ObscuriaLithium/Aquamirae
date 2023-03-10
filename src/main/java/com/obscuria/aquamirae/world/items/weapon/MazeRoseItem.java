
package com.obscuria.aquamirae.world.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeCreativeTab;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.api.DynamicProjectile;
import com.obscuria.obscureapi.api.DynamicProjectileItem;
import com.obscuria.obscureapi.api.classes.Ability;
import com.obscuria.obscureapi.api.classes.ClassAbility;
import com.obscuria.obscureapi.api.classes.ClassItem;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "weapon")
@DynamicProjectileItem(mirror = true, distance = true, fastSpin = true)
public class MazeRoseItem extends TieredItem implements Vanishable {
	public MazeRoseItem() {
		super(new Tier() {
			public int getUses() {
				return 1800;
			}

			public float getSpeed() {
				return 4f;
			}

			public float getAttackDamageBonus() {
				return -1f;
			}

			public int getLevel() {
				return 2;
			}

			public int getEnchantmentValue() {
				return 12;
			}

			public @NotNull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
						new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
			}
		}, new Item.Properties().tab(AquamiraeMod.TAB).rarity(Rarity.UNCOMMON));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.Builder.create(AquamiraeMod.MODID).description("maze_rose").cost(Ability.Cost.COOLDOWN, 40)
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
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player entity, @NotNull InteractionHand hand) {
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
