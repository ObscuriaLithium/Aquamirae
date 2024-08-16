
package com.obscuria.aquamirae.common.item.weapon;

import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Vanishable;

public class MazeRoseItem extends TieredItem implements Vanishable {

	public MazeRoseItem(Properties properties) {
		super(Tiers.IRON, properties);
	}

//	@Override
//	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
//		final ItemStack stack = entity.getItemInHand(hand);
//		if (world instanceof ServerLevel) {
//			ACTIVE.use(new SimpleAbilityContext(entity, stack));
//			return InteractionResultHolder.success(stack);
//		}
//		return InteractionResultHolder.pass(stack);
//	}

//	@Override
//	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
//		final Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
//		if (slot == EquipmentSlot.OFFHAND) {
//			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
//			builder.putAll(multimap);
//			builder.put(ObscureAPIAttributes.CRITICAL_HIT, new AttributeModifier(UUID.fromString("A14F51D3-645C-4F38-A497-9C13A33DB5CF"),
//					"Weapon modifier", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
//			return builder.build();
//		}
//		return multimap;
//	}

//	public static boolean summonChakra(AbilityContext context, List<Integer> vars) {
//		context.getStack().hurtAndBreak(3, context.getUser(), e -> {});
//		CompoundProjectileEntity.create(AquamiraeEntityTypes.MAZE_ROSE.get(), context.getUser(), context.getUser().level(),
//				context.getStack(), vars.get(0), 0F, 20 * vars.get(1), 1000);
//		return true;
//	}
//
//	static {
//		ACTIVE = Ability.create(Aquamirae.MODID, "maze_rose")
//				.cost(Ability.CostType.COOLDOWN, 40)
//				.action(MazeRoseItem::summonChakra)
//				.var(5)
//				.sec(40)
//				.build(MazeRoseItem.class);
//	}
}
