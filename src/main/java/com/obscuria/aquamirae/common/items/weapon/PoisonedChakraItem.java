
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeCreativeTab;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.world.classes.*;
import com.obscuria.obscureapi.world.entities.ChakraEntity;
import com.obscuria.obscureapi.world.items.ChakraItem;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PoisonedChakraItem extends ChakraItem implements IClassItem, IAbilityItem {
	public PoisonedChakraItem() {
		super(new IItemTier() {
			public int getUses() {
				return 1200;
			}

			public float getSpeed() {
				return 2f;
			}

			public float getAttackDamageBonus() {
				return -3f;
			}

			public int getLevel() {
				return 0;
			}

			public int getEnchantmentValue() {
				return 12;
			}

			public @Nonnull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.ANGLERS_FANG.get()),
						new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
			}
		}, new Item.Properties().tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY = new ObscureAbility(this, "poisoned_chakra", ObscureAbility.Cost.COOLDOWN, 30, 3, 30);

	public List<ObscureAbility> getObscureAbilities() {
		return Collections.singletonList(ABILITY);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.WEAPON;
	}

	@Override
	public void fillItemCategory(@Nonnull ItemGroup tab, @Nonnull NonNullList<ItemStack> list) {
		super.fillItemCategory(tab, list);
		if (tab == AquamiraeMod.TAB) list.addAll(AquamiraeCreativeTab.poisonedChakra());
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		final Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if (slot == EquipmentSlotType.OFFHAND) {
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.CRITICAL_HIT.get(), new AttributeModifier(UUID.fromString("A33F51D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.1, AttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> use(@Nonnull World world, PlayerEntity entity, @Nonnull Hand hand) {
		final ItemStack stack = entity.getItemInHand(hand);
		if (world instanceof ServerWorld) {
			stack.hurt(3, entity.getRandom(), null);
			ChakraEntity.summonChakra(entity, AquamiraeEntities.POISONED_CHAKRA.get(), world, stack, ABILITY.getAmount(entity, 0), 0F,
					20 * ABILITY.getAmount(entity, 1), 1000);
			entity.getCooldowns().addCooldown(this, 20 * ABILITY.getCost(entity));
			return ActionResult.success(stack);
		}
		return ActionResult.pass(stack);
	}
}
