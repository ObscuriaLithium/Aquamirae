
package com.obscuria.aquamirae.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.extension.AbyssalArmorExtension;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.registry.AquamiraeArmorMaterials;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.util.bundle.ItemBundle;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@DeadSeaCurse.ByDefault
public abstract class AbyssalArmor extends ArmorItem {
	public static final TagKey<Item> ARMOR_PIECES = ItemTags.create(Aquamirae.key("armor/abyssal_pieces"));
	public static final ItemBundle ARMOR_BUNDLE = ItemBundle.fromTag(ARMOR_PIECES);
	public static final Ability TIARA_ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
			.setRelatedItems(ARMOR_BUNDLE)
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.abyssal_armor.tiara_tier_1")))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.abyssal_armor.tiara_tier_2"))
					.addVariable(Variable.create(30).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(4))
					.addGoal(AbilityGoal.equippedArmor(4,
							Component.translatable("ability_goal.aquamirae.abyssal_armor"),
							ARMOR_BUNDLE)))
			.build();
	public static final Ability HEAUME_ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
			.setRelatedItems(ARMOR_BUNDLE)
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.abyssal_armor.heaume_tier_1")))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.abyssal_armor.heaume_tier_2"))
					.addVariable(Variable.create(80).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(60).withSuffix(Variable.SECONDS))
					.addGoal(AbilityGoal.equippedArmor(4,
							Component.translatable("ability_goal.aquamirae.abyssal_armor"),
							ARMOR_BUNDLE)))
			.build();
	private static final Map<Class<? extends AbyssalArmor>, String> TEXTURES = Util.make(Maps.newHashMap(), map -> {
		map.put(AbyssalArmor.Tiara.class, "aquamirae:textures/entity/armor/abyssal_tiara.png");
		map.put(AbyssalArmor.Heaume.class, "aquamirae:textures/entity/armor/abyssal_heaume.png");
		map.put(AbyssalArmor.Brigantine.class, "aquamirae:textures/entity/armor/abyssal_brigantine.png");
		map.put(AbyssalArmor.Leggings.class, "aquamirae:textures/entity/armor/abyssal_leggings.png");
		map.put(AbyssalArmor.Boots.class, "aquamirae:textures/entity/armor/abyssal_boots.png");
	});
	private static final Map<Class<? extends AbyssalArmor>, UUID> FURY_UUIDS = Util.make(Maps.newHashMap(), map -> {
		map.put(AbyssalArmor.Tiara.class, UUID.fromString("94A7ECAE-A784-4EFB-82B7-060ECFEBEEB3"));
		map.put(AbyssalArmor.Heaume.class, UUID.fromString("3ACCA274-25B6-46DC-8183-E4935554788A"));
		map.put(AbyssalArmor.Brigantine.class, UUID.fromString("0ACF8154-A4E2-4D55-BB7E-955BEC90B26C"));
		map.put(AbyssalArmor.Leggings.class, UUID.fromString("052E21E4-E800-4B86-8A2E-7D7406056B84"));
		map.put(AbyssalArmor.Boots.class, UUID.fromString("60577820-2976-4B40-8581-098BD9940EBD"));
	});
	private static final UUID TIARA_DAMAGE_UUID = UUID.fromString("21C7129E-0B29-48A1-8315-5298D9FD144F");

	public AbyssalArmor(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
		super(material, type, properties.rarity(Rarity.EPIC));
	}

	public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new AbyssalArmorExtension());
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return TEXTURES.get(this.getClass());
	}

	public static class Tiara extends AbyssalArmor implements IAbilitable {
		public Tiara() {
			super(AquamiraeArmorMaterials.ABYSSAL_TIARA, Type.HELMET, new Item.Properties());
		}

		@Override
		public Optional<Ability> getAbility(ItemStack stack) {
			return Optional.of(TIARA_ABILITY);
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
			if (slot == this.type.getSlot())
				return ImmutableMultimap.<Attribute, AttributeModifier>builder()
						.putAll(super.getDefaultAttributeModifiers(slot))
						.put(AquamiraeAttributes.DEPTHS_FURY.get(), new AttributeModifier(FURY_UUIDS.get(this.getClass()),
								"Bonus", 0.4, AttributeModifier.Operation.MULTIPLY_BASE))
						.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(TIARA_DAMAGE_UUID,
								"Bonus", 0.2, AttributeModifier.Operation.MULTIPLY_BASE))
						.build();
			return super.getDefaultAttributeModifiers(slot);
		}
	}

	public static class Heaume extends AbyssalArmor implements IAbilitable {
		public Heaume() {
			super(AquamiraeArmorMaterials.ABYSSAL_HEAUME, Type.HELMET, new Item.Properties());
		}

		@Override
		public Optional<Ability> getAbility(ItemStack stack) {
			return Optional.of(HEAUME_ABILITY);
		}
	}

	public static class Brigantine extends AbyssalArmor {
		public Brigantine() {
			super(AquamiraeArmorMaterials.ABYSSAL, Type.CHESTPLATE, new Item.Properties());
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
			return Aquamirae.addAttribute(super.getDefaultAttributeModifiers(slot), AquamiraeAttributes.DEPTHS_FURY.get(),
					FURY_UUIDS.get(this.getClass()), AttributeModifier.Operation.MULTIPLY_BASE,
					0.1, slot == this.type.getSlot());
		}
	}

	public static class Leggings extends AbyssalArmor {
		public Leggings() {
			super(AquamiraeArmorMaterials.ABYSSAL, Type.LEGGINGS, new Item.Properties());
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
			return Aquamirae.addAttribute(super.getDefaultAttributeModifiers(slot), AquamiraeAttributes.DEPTHS_FURY.get(),
					FURY_UUIDS.get(this.getClass()), AttributeModifier.Operation.MULTIPLY_BASE,
					0.1, slot == this.type.getSlot());
		}
	}

	public static class Boots extends AbyssalArmor {
		public Boots() {
			super(AquamiraeArmorMaterials.ABYSSAL, Type.BOOTS, new Item.Properties());
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
			return Aquamirae.addAttribute(super.getDefaultAttributeModifiers(slot), AquamiraeAttributes.DEPTHS_FURY.get(),
					FURY_UUIDS.get(this.getClass()), AttributeModifier.Operation.MULTIPLY_BASE,
					0.1, slot == this.type.getSlot());
		}
	}
}
