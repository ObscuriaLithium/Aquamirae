
package com.obscuria.aquamirae.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.client.extension.TerribleArmorExtension;
import com.obscuria.aquamirae.registry.AquamiraeArmorMaterials;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.core.common.item.ability.*;
import com.obscuria.core.common.item.ability.event.AbilityEvent;
import com.obscuria.core.common.item.ability.event.DefenseAbilityEvent;
import com.obscuria.core.common.bundle.ItemBundle;
import com.obscuria.core.common.extension.item.AttributeCollector;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class TerribleArmor extends ArmorItem implements IAbilitable {
	public static final String POISON_DURATION;
	public static final String BOOST_STRENGTH;
	public static final String BOOST_DURATION;
	public static final TagKey<Item> TAG;
	public static final ItemBundle PIECES;
	public static final Ability ABILITY;
	private static final Map<Class<?>, String> TEXTURES;

	public TerribleArmor(ArmorItem.Type type, Item.Properties properties) {
		super(AquamiraeArmorMaterials.TERRIBLE, type, properties);
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	@Override
	public void onAbilityEvent(AbilityEvent rawEvent) {
		if (rawEvent instanceof DefenseAbilityEvent event
				&& event.attacker instanceof LivingEntity attacker
				&& event.context.player().level() instanceof ServerLevel
				&& event.slot.isArmor()) {
			final var context = event.context;
			final var player = context.player();
			final var poisonDuration = 20 * context.get(POISON_DURATION);
			final var boostStrength = context.get(BOOST_STRENGTH);
			final var boostDuration = 20 * context.get(BOOST_DURATION);
			attacker.addEffect(new MobEffectInstance(MobEffects.POISON, poisonDuration));
			if (context.tier() == 2 && player.isInWater())
				player.addEffect(new MobEffectInstance(AquamiraeMobEffects.TERRIBLE_ARMOR.get(), boostDuration, boostStrength));
			context.forceCooldown(10);
		}
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		if (slot == this.type.getSlot())
			return ImmutableMultimap.<Attribute, AttributeModifier>builder()
					.putAll(super.getAttributeModifiers(slot, stack))
					.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(AttributeCollector.uuidFor(this.type.getSlot()),
							"Armor modifier", 0.1, AttributeModifier.Operation.MULTIPLY_BASE))
					.put(AquamiraeAttributes.DEPTHS_FURY.get(), new AttributeModifier(AttributeCollector.uuidFor(this.type.getSlot()),
							"Armor modifier", 0.1, AttributeModifier.Operation.MULTIPLY_BASE))
					.build();
		return super.getAttributeModifiers(slot, stack);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new TerribleArmorExtension());
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return TEXTURES.get(this.getClass());
	}

	static {
		POISON_DURATION = "POISON_DURATION";
		BOOST_STRENGTH = "BOOST_STRENGTH";
		BOOST_DURATION = "BOOST_DURATION";
		TAG = ItemTags.create(Aquamirae.key("armor/terrible_pieces"));
		PIECES = ItemBundle.fromTag(TAG);
		final var descriptionTier1 = Component.translatable("ability.aquamirae.terrible_armor_tier_1");
		final var descriptionTier2 = Component.translatable("ability.aquamirae.terrible_armor_tier_2");
		final var terribleArmorHint = Component.translatable("ability_goal.aquamirae.terrible_armor");
		ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
				.setRelatedItems(PIECES)
				.addTier(AbilityTier.create(descriptionTier1)
						.with(POISON_DURATION, Variable.create(AquamiraeConfig.TerribleArmor.poisonDurationTier1).withSuffix(Variable.SECONDS)))
				.addTier(AbilityTier.create(descriptionTier2)
						.with(POISON_DURATION, Variable.create(AquamiraeConfig.TerribleArmor.poisonDurationTier2).withSuffix(Variable.SECONDS))
						.with(BOOST_STRENGTH, Variable.create(AquamiraeConfig.TerribleArmor.boostStrength).withSuffix(Variable.PERCENT))
						.with(BOOST_DURATION, Variable.create(AquamiraeConfig.TerribleArmor.boostDuration).withSuffix(Variable.SECONDS))
						.requiringArmor(4, terribleArmorHint, PIECES))
				.build();
		TEXTURES = Util.make(Maps.newHashMap(), map -> {
			map.put(TerribleArmor.Helmet.class, "aquamirae:textures/entity/armor/terrible_helmet.png");
			map.put(TerribleArmor.Chestplate.class, "aquamirae:textures/entity/armor/terrible_chestplate.png");
			map.put(TerribleArmor.Leggings.class, "aquamirae:textures/entity/armor/terrible_leggings.png");
			map.put(TerribleArmor.Boots.class, "aquamirae:textures/entity/armor/terrible_boots.png");
		});
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
