
package com.obscuria.aquamirae.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.client.extension.AbyssalArmorExtension;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.common.entity.projectile.AbyssalShard;
import com.obscuria.aquamirae.registry.AquamiraeArmorMaterials;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.core.common.bundle.ItemBundle;
import com.obscuria.core.common.item.ability.*;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@DeadSeaCurse.ByDefault
public abstract class AbyssalArmor extends ArmorItem {
	public static final TagKey<Item> TAG;
	public static final ItemBundle PIECES;
	private static final Map<Class<?>, String> TEXTURES;
	private static final Map<Class<?>, UUID> FURY_UUIDS;
	private static final UUID TIARA_DAMAGE_UUID;

	public AbyssalArmor(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
		super(material, type, properties.rarity(Rarity.EPIC).fireResistant());
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new AbyssalArmorExtension());
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return TEXTURES.get(this.getClass());
	}

	public static class Tiara extends AbyssalArmor implements IAbilitable {
		public static final Ability ABILITY;

		public Tiara() {
			super(AquamiraeArmorMaterials.ABYSSAL_TIARA, Type.HELMET, new Properties());
		}

		@Override
		public Optional<Ability> getAbility(ItemStack stack) {
			return Optional.of(ABILITY);
		}

		@Override
		@SuppressWarnings("removal")
		public void onArmorTick(ItemStack stack, Level level, Player player) {
			if (!ABILITY.canBeUsedBy(player)) return;
			final var context = ABILITY.setupContext(stack, player);
			if (context.tier() < 2) return;
			if (level instanceof ServerLevel serverLevel) {
				final var random = player.getRandom();
				final var origin = player.position().add(player.getEyePosition()).scale(0.5);
				final var motion = new Vec3(0, 1, 0).normalize()
						.scale(random.triangle(0.2, 0.05))
						.xRot((float) random.triangle(0, 1))
						.yRot((float) random.triangle(0, 1))
						.zRot((float) random.triangle(0, 1));
				AbyssalShard.create(serverLevel, player, origin, motion);
			}
			final var cooldownFactor = Math.pow(player.getHealth() / 1.0 / player.getMaxHealth(), 2);
			context.forceCooldown(10 + (int) (190.0 * cooldownFactor));
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
			if (slot == this.type.getSlot())
				return ImmutableMultimap.<Attribute, AttributeModifier>builder()
						.putAll(super.getAttributeModifiers(slot, stack))
						.put(AquamiraeAttributes.DEPTHS_FURY.get(), new AttributeModifier(FURY_UUIDS.get(this.getClass()),
								"Bonus", 0.4, AttributeModifier.Operation.MULTIPLY_BASE))
						.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(TIARA_DAMAGE_UUID,
								"Bonus", 0.2, AttributeModifier.Operation.MULTIPLY_BASE))
						.build();
			return super.getAttributeModifiers(slot, stack);
		}

		static {
			final var descriptionTier1 = Component.translatable("ability.aquamirae.abyssal_armor.tiara_tier_1");
			final var descriptionTier2 = Component.translatable("ability.aquamirae.abyssal_armor.tiara_tier_2");
			final var armorPiecesHint = Component.translatable("ability_goal.aquamirae.abyssal_armor");
			ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
					.setRelatedItems(PIECES)
					.addTier(AbilityTier.create(descriptionTier1))
					.addTier(AbilityTier.create(descriptionTier2)
							.requiringArmor(4, armorPiecesHint, PIECES))
					.build();
		}
	}

	public static class Heaume extends AbyssalArmor implements IAbilitable {
		public static final String DEBUFF_STRENGTH;
		public static final String DEBUFF_DURATION;
		public static final String COOLDOWN;
		public static final Ability ABILITY;

		public Heaume() {
			super(AquamiraeArmorMaterials.ABYSSAL_HEAUME, Type.HELMET, new Properties());
		}

		public static boolean checkDeathProtection(Player player) {
			final var stack = player.getItemBySlot(EquipmentSlot.HEAD);
			if (!stack.is(AquamiraeItems.ABYSSAL_HEAUME.get())) return false;
			if (!ABILITY.canBeUsedBy(player)) return false;
			final var context = ABILITY.setupContext(stack, player);
			final var debuffStrength = context.get(DEBUFF_STRENGTH) - 1;
			final var debuffDuration = 20 * context.get(DEBUFF_DURATION);
			final var cooldown = 20 * context.get(COOLDOWN);
			player.setHealth(1);
			player.removeAllEffects();
			if (debuffStrength >= 0 && debuffDuration > 0)
				player.addEffect(new MobEffectInstance(
						AquamiraeMobEffects.CRYSTALLIZATION.get(), debuffDuration, debuffStrength));
			player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 4));
			player.level().broadcastEntityEvent(player, (byte) 35);
			context.forceCooldown(cooldown);
			return true;
		}

		@Override
		public Optional<Ability> getAbility(ItemStack stack) {
			return Optional.of(ABILITY);
		}

		static {
			DEBUFF_STRENGTH = "DEBUFF_STRENGTH";
			DEBUFF_DURATION = "DEBUFF_DURATION";
			COOLDOWN = "COOLDOWN";
			final var descriptionTier1 = Component.translatable("ability.aquamirae.abyssal_armor.heaume_tier_1");
			final var descriptionTier2 = Component.translatable("ability.aquamirae.abyssal_armor.heaume_tier_2");
			final var armorPiecesHint = Component.translatable("ability_goal.aquamirae.abyssal_armor");
			ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
					.setRelatedItems(PIECES)
					.addTier(AbilityTier.create(descriptionTier1))
					.addTier(AbilityTier.create(descriptionTier2)
							.with(DEBUFF_STRENGTH, Variable.create(AquamiraeConfig.AbyssalArmor.heaumeDebuffStrength).withSuffix(Variable.PERCENT))
							.with(DEBUFF_DURATION, Variable.create(AquamiraeConfig.AbyssalArmor.heaumeDebuffDuration).withSuffix(Variable.SECONDS))
							.with(COOLDOWN, Variable.create(AquamiraeConfig.AbyssalArmor.heaumeCooldown).withSuffix(Variable.SECONDS))
							.requiringArmor(4, armorPiecesHint, PIECES))
					.build();
		}
	}

	public static class Brigantine extends AbyssalArmor {

		public Brigantine() {
			super(AquamiraeArmorMaterials.ABYSSAL, Type.CHESTPLATE, new Properties());
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
			if (slot == this.type.getSlot())
				return ImmutableMultimap.<Attribute, AttributeModifier>builder()
						.putAll(super.getAttributeModifiers(slot, stack))
						.put(AquamiraeAttributes.DEPTHS_FURY.get(),
								new AttributeModifier(FURY_UUIDS.get(this.getClass()), "Armor modifier",
										0.1, AttributeModifier.Operation.MULTIPLY_BASE))
						.build();
			return super.getAttributeModifiers(slot, stack);
		}
	}

	public static class Leggings extends AbyssalArmor {

		public Leggings() {
			super(AquamiraeArmorMaterials.ABYSSAL, Type.LEGGINGS, new Properties());
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
			if (slot == this.type.getSlot())
				return ImmutableMultimap.<Attribute, AttributeModifier>builder()
						.putAll(super.getAttributeModifiers(slot, stack))
						.put(AquamiraeAttributes.DEPTHS_FURY.get(),
								new AttributeModifier(FURY_UUIDS.get(this.getClass()), "Armor modifier",
										0.1, AttributeModifier.Operation.MULTIPLY_BASE))
						.build();
			return super.getAttributeModifiers(slot, stack);
		}
	}

	public static class Boots extends AbyssalArmor {

		public Boots() {
			super(AquamiraeArmorMaterials.ABYSSAL, Type.BOOTS, new Properties());
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
			if (slot == this.type.getSlot())
				return ImmutableMultimap.<Attribute, AttributeModifier>builder()
						.putAll(super.getAttributeModifiers(slot, stack))
						.put(AquamiraeAttributes.DEPTHS_FURY.get(),
								new AttributeModifier(FURY_UUIDS.get(this.getClass()), "Armor modifier",
										0.1, AttributeModifier.Operation.MULTIPLY_BASE))
						.build();
			return super.getAttributeModifiers(slot, stack);
		}
	}

	static {
		TAG = ItemTags.create(Aquamirae.key("armor/abyssal_pieces"));
		PIECES = ItemBundle.fromTag(TAG);
		TEXTURES = Util.make(Maps.newHashMap(), map -> {
			map.put(AbyssalArmor.Tiara.class, "aquamirae:textures/entity/armor/abyssal_tiara.png");
			map.put(AbyssalArmor.Heaume.class, "aquamirae:textures/entity/armor/abyssal_heaume.png");
			map.put(AbyssalArmor.Brigantine.class, "aquamirae:textures/entity/armor/abyssal_brigantine.png");
			map.put(AbyssalArmor.Leggings.class, "aquamirae:textures/entity/armor/abyssal_leggings.png");
			map.put(AbyssalArmor.Boots.class, "aquamirae:textures/entity/armor/abyssal_boots.png");
		});
		FURY_UUIDS = Util.make(Maps.newHashMap(), map -> {
			map.put(AbyssalArmor.Tiara.class, UUID.fromString("94A7ECAE-A784-4EFB-82B7-060ECFEBEEB3"));
			map.put(AbyssalArmor.Heaume.class, UUID.fromString("3ACCA274-25B6-46DC-8183-E4935554788A"));
			map.put(AbyssalArmor.Brigantine.class, UUID.fromString("0ACF8154-A4E2-4D55-BB7E-955BEC90B26C"));
			map.put(AbyssalArmor.Leggings.class, UUID.fromString("052E21E4-E800-4B86-8A2E-7D7406056B84"));
			map.put(AbyssalArmor.Boots.class, UUID.fromString("60577820-2976-4B40-8581-098BD9940EBD"));
		});
		TIARA_DAMAGE_UUID = UUID.fromString("21C7129E-0B29-48A1-8315-5298D9FD144F");
	}
}
