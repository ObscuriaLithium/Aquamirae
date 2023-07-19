
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
import com.obscuria.obscureapi.common.entities.DynamicProjectile;
import com.obscuria.obscureapi.common.entities.DynamicProjectileEntity;
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
@DynamicProjectile(mirror = true, distance = true, fastSpin = true)
public class MazeRoseItem extends ToolItem implements Vanishable {
	@RegisterAbility public static final Ability ACTIVE;

	public MazeRoseItem(Settings settings) {
		super(AquamiraeMaterials.MAZE_ROSE, settings);
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
			builder.put(ObscureAPIAttributes.CRITICAL_HIT, new EntityAttributeModifier(UUID.fromString("A14F51D3-645C-4F38-A497-9C13A33DB5CF"),
					"Weapon modifier", 0.15, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			return builder.build();
		}
		return multimap;
	}

	public static boolean summonChakra(AbilityContext context, List<Integer> vars) {
		context.getStack().damage(3, context.getUser().getRandom(), null);
		DynamicProjectileEntity.create(AquamiraeEntities.MAZE_ROSE, context.getUser(), context.getUser().getWorld(),
				context.getStack(), vars.get(0), 0F, 20 * vars.get(1), 1000);
		return true;
	}

	static {
		ACTIVE = Ability.create(Aquamirae.MODID, "maze_rose")
				.cost(Ability.CostType.COOLDOWN, 40)
				.action(MazeRoseItem::summonChakra)
				.var(5)
				.sec(40)
				.build(MazeRoseItem.class);
	}
}
