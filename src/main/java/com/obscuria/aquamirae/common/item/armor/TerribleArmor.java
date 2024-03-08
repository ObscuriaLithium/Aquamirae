
package com.obscuria.aquamirae.common.item.armor;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.extension.TerribleArmorExtension;
import com.obscuria.aquamirae.registry.AquamiraeArmorMaterials;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.util.bundle.ItemBundle;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Mod.EventBusSubscriber
public abstract class TerribleArmor extends ArmorItem implements IAbilitable {
	public static final TagKey<Item> ARMOR_PIECES = ItemTags.create(Aquamirae.key("armor/terrible_pieces"));
	public static final ItemBundle ARMOR_BUNDLE = ItemBundle.fromTag(ARMOR_PIECES);
	public static final Ability ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
			.setRelatedItems(ARMOR_BUNDLE)
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.terrible_armor"))
					.addVariable(Variable.create(3).withSuffix(Variable.SECONDS)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.terrible_armor_tier_4"))
					.addVariable(Variable.create(9).withSuffix(Variable.SECONDS))
					.addVariable(Variable.create(150).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(10).withSuffix(Variable.SECONDS))
					.addGoal(AbilityGoal.equippedArmor(4,
							Component.translatable("ability_goal.aquamirae.terrible_armor"),
							ARMOR_BUNDLE)))
			.build();
	private static final Map<Class<? extends TerribleArmor>, String> TEXTURES = Util.make(Maps.newHashMap(), map -> {
		map.put(TerribleArmor.Helmet.class, "aquamirae:textures/entity/armor/terrible_helmet.png");
		map.put(TerribleArmor.Chestplate.class, "aquamirae:textures/entity/armor/terrible_chestplate.png");
		map.put(TerribleArmor.Leggings.class, "aquamirae:textures/entity/armor/terrible_leggings.png");
		map.put(TerribleArmor.Boots.class, "aquamirae:textures/entity/armor/terrible_boots.png");
	});
	private static final Map<Class<? extends TerribleArmor>, UUID> FURY_UUIDS = Util.make(Maps.newHashMap(), map -> {
		map.put(TerribleArmor.Helmet.class, UUID.fromString("B52E4EFC-FC7D-44FD-98E0-8DF9FED2380C"));
		map.put(TerribleArmor.Chestplate.class, UUID.fromString("FDB5C555-6F2A-45A8-92BE-11492155716E"));
		map.put(TerribleArmor.Leggings.class, UUID.fromString("F912605F-4890-46AB-816A-DD2BAF6445A7"));
		map.put(TerribleArmor.Boots.class, UUID.fromString("E7E36A81-91BA-4336-A63D-1EE433B669B3"));
	});

	public static void applyAbility(ItemStack stack, Player player, LivingEntity attacker) {
		if (!ABILITY.canBeUsedBy(player)) return;
		final var context = ABILITY.setupContext(stack, player);
		attacker.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * context.getVariable(1)));
		if (context.getTier() == 2 && player.isInWater())
			player.addEffect(new MobEffectInstance(AquamiraeMobEffects.TERRIBLE_ARMOR.get(), 20 * 10, 14));
		context.forceCustomCooldown(20);
	}

	public TerribleArmor(ArmorItem.Type type, Item.Properties properties) {
		super(AquamiraeArmorMaterials.TERRIBLE, type, properties);
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		return Aquamirae.addAttribute(super.getDefaultAttributeModifiers(slot), AquamiraeAttributes.DEPTHS_FURY.get(),
				FURY_UUIDS.get(this.getClass()), AttributeModifier.Operation.MULTIPLY_BASE,
				0.1, slot == this.type.getSlot());
	}

	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new TerribleArmorExtension());
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return TEXTURES.get(this.getClass());
	}

	public static class Helmet extends TerribleArmor {
		public Helmet() {
			super(Type.HELMET, new Item.Properties());
		}
	}

	public static class Chestplate extends TerribleArmor {
		public Chestplate() {
			super(Type.CHESTPLATE, new Item.Properties());
		}
	}

	public static class Leggings extends TerribleArmor {
		public Leggings() {
			super(Type.LEGGINGS, new Item.Properties());
		}
	}

	public static class Boots extends TerribleArmor {
		public Boots() {
			super(Type.BOOTS, new Item.Properties());
		}
	}
}
