
package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.common.classes.ability.context.AbilityContext;
import com.obscuria.obscureapi.common.classes.ability.context.SimpleAbilityContext;
import com.obscuria.obscureapi.common.entities.CompoundProjectile;
import com.obscuria.obscureapi.common.entities.CompoundProjectileEntity;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.Vanishable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "weapon")
@CompoundProjectile(mirror = true, distance = true, fastSpin = true)
public class PoisonedChakraItem extends ToolItem implements Vanishable {
	@RegisterAbility public static final Ability ACTIVE;

	public PoisonedChakraItem(Settings settings) {
		super(AquamiraeMaterials.POISONED_CHAKRA, settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		final ItemStack stack = user.getStackInHand(hand);
		if (world instanceof ServerWorld) {
			ACTIVE.use(new SimpleAbilityContext(user, stack));
			return TypedActionResult.success(stack);
		}
		return TypedActionResult.pass(stack);
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
		final Multimap<EntityAttribute, EntityAttributeModifier> multimap = super.getAttributeModifiers(stack, slot);
		if (slot == EquipmentSlot.OFFHAND) {
			Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(multimap);
			builder.put(ObscureAPIAttributes.CRITICAL_HIT, new EntityAttributeModifier(UUID.fromString("A33F51D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	public static boolean summonChakra(AbilityContext context, List<Integer> vars) {
		context.getStack().damage(3, context.getUser().getRandom(), null);
		CompoundProjectileEntity.create(AquamiraeEntities.POISONED_CHAKRA, context.getUser(), context.getUser().getWorld(),
				context.getStack(), vars.get(0), 0F, 20 * vars.get(1), 1000);
		return true;
	}

	static {
		ACTIVE = Ability.create(Aquamirae.MODID, "poisoned_chakra")
				.cost(Ability.CostType.COOLDOWN, 30)
				.action(PoisonedChakraItem::summonChakra)
				.var(3)
				.sec(30)
				.build(PoisonedBladeItem.class);
	}
}
