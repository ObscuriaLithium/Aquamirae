
package com.obscuria.aquamirae.common.item.armor;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.extension.ThreeBoltArmorExtension;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.common.item.OxygenHolder;
import com.obscuria.aquamirae.registry.*;
import com.obscuria.core.api.ability.AbilityStyles;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.util.bundle.ItemBundle;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class ThreeBoltArmor extends ArmorItem {
	public static final TagKey<Item> ARMOR_PIECES = ItemTags.create(Aquamirae.key("armor/three_bolt_pieces"));
	public static final ItemBundle ARMOR_BUNDLE = ItemBundle.fromTag(ARMOR_PIECES);
	public static final Ability ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
			.setRelatedItems(ARMOR_BUNDLE)
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.three_bolt_armor_tier_1")))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.three_bolt_armor_tier_2"))
					.addGoal(AbilityGoal.equippedArmor(4,
							Component.translatable("ability_goal.aquamirae.three_bolt_armor"),
							ARMOR_BUNDLE)))
			.build();
	private static final Map<Class<? extends ThreeBoltArmor>, String> TEXTURES = Util.make(Maps.newHashMap(), map -> {
		map.put(ThreeBoltArmor.Helmet.class, "aquamirae:textures/entity/armor/three_bolt_helmet.png");
		map.put(Suit.class, "aquamirae:textures/entity/armor/three_bolt_chestplate.png");
		map.put(ThreeBoltArmor.Leggings.class, "aquamirae:textures/entity/armor/three_bolt_leggings.png");
		map.put(ThreeBoltArmor.Boots.class, "aquamirae:textures/entity/armor/three_bolt_boots.png");
	});
	private static final String TAG_TANKS = "Tanks";
	private static final String TAG_FIRST = "first";
	private static final String TAG_SECOND = "second";

	public static void setTanks(ItemStack stack, Pair<ItemStack, ItemStack> tanks) {
		final var tag = stack.getOrCreateTagElement(TAG_TANKS);
		tag.put(TAG_FIRST, tanks.getFirst().save(new CompoundTag()));
		tag.put(TAG_SECOND, tanks.getSecond().save(new CompoundTag()));
	}

	public static Pair<ItemStack, ItemStack> getTanks(ItemStack stack) {
		if (stack.getTagElement(TAG_TANKS) == null)
			return Pair.of(ItemStack.EMPTY, ItemStack.EMPTY);
		final var tanks = stack.getOrCreateTagElement(TAG_TANKS);
		final var first = tanks.contains(TAG_FIRST, Tag.TAG_COMPOUND)
				? ItemStack.of(tanks.getCompound(TAG_FIRST))
				: ItemStack.EMPTY;
		final var second = tanks.contains(TAG_SECOND, Tag.TAG_COMPOUND)
				? ItemStack.of(tanks.getCompound(TAG_SECOND))
				: ItemStack.EMPTY;
		return Pair.of(first, second);
	}

	public static int getCapacity(ItemStack stack) {
		final var tanks = getTanks(stack);
		return OxygenHolder.getCapacity(tanks.getFirst())
				+ OxygenHolder.getCapacity(tanks.getSecond());
	}

	public static int getOxygen(ItemStack stack) {
		final var tanks = getTanks(stack);
		return OxygenHolder.getOxygen(tanks.getFirst())
				+ OxygenHolder.getOxygen(tanks.getSecond());
	}

	public static int takeOxygen(ItemStack stack, int amount) {
		final var tanks = getTanks(stack);
		var taken = OxygenHolder.takeOxygen(tanks.getFirst(), amount);
		if (taken < amount) taken += OxygenHolder.takeOxygen(tanks.getSecond(), amount - taken);
		setTanks(stack, tanks);
		return taken;
	}

	public ThreeBoltArmor(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
		super(material, type, properties);
	}

	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new ThreeBoltArmorExtension());
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return TEXTURES.get(this.getClass());
	}

	@DeadSeaCurse.ByDefault
	public static class Helmet extends ThreeBoltArmor implements IAbilitable {

		public static void onArmorBonusPressed(Player player) {
			if (!ABILITY.canBeUsedBy(player) || !isEquippedBy(player) || !player.isInWater()) return;
			final var stack = player.getItemBySlot(EquipmentSlot.HEAD);
			final var context = ABILITY.setupContext(stack, player);
			final var suit = player.getItemBySlot(EquipmentSlot.CHEST);
			if (context.getTier() >= 2 && suit.is(AquamiraeItems.THREE_BOLT_SUIT.get()))
				applyActiveEffects(suit, player);
		}

		public static void onPlayerTick(Player player) {
			if (!ABILITY.canBeUsedBy(player) || !isEquippedBy(player)) return;
			final var stack = player.getItemBySlot(EquipmentSlot.HEAD);
			final var context = ABILITY.setupContext(stack, player);
			if (context.getTier() >= 2) applyPassiveEffects(player);
		}

		private static void applyActiveEffects(ItemStack suit, Player player) {
			if (getOxygen(suit) < 10) return;
			takeOxygen(suit, 10);
			player.removeEffect(AquamiraeMobEffects.THREE_BOLT_ARMOR.get());
			player.addEffect(new MobEffectInstance(AquamiraeMobEffects.THREE_BOLT_ARMOR.get(), 200));
			player.level().playSound(null, player,
					SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_INSIDE,
					SoundSource.PLAYERS, 1f, (float) player.getRandom()
							.triangle(1f, 0.1f));
		}

		private static void applyPassiveEffects(Player player) {
			if (player.isInWater() && !player.getAbilities().flying) {
				final var factor = getWeightFactor(player, player.level());
				player.setDeltaMovement(player.getDeltaMovement()
						.multiply(0.99, 1, 0.99)
						.add(0, (player.isSwimming() ? -0.16 : -0.08) * factor, 0));
			}
			if (player.getAirSupply() <= 0 && !player.level().isClientSide) {
				final var suit = player.getItemBySlot(EquipmentSlot.CHEST);
				if (suit.is(AquamiraeItems.THREE_BOLT_SUIT.get())) {
					final var amount = (float) takeOxygen(suit, 10);
					if (amount > 0) {
						player.setAirSupply(Math.round(amount / 10f * player.getMaxAirSupply()));
						player.level().playSound(null, player,
								AquamiraeSounds.ITEM_THREE_BOLT_OXYGEN.get(),
								SoundSource.PLAYERS, 0.4f, (float) player.getRandom()
										.triangle(1f, 0.1f));
					}
				}
			}
		}

		@SuppressWarnings("all")
		private static boolean isEquippedBy(Player player) {
			return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThreeBoltArmor.Helmet;
		}

		private static float getWeightFactor(Player player, Level level) {
			final var pos = player.blockPosition();
			final var water1 = level.isWaterAt(pos);
			final var water2 = level.isWaterAt(pos.below());
			final var water3 = level.isWaterAt(pos.below(2));
			final var water4 = level.isWaterAt(pos.below(3));
			final float factor = Optional.ofNullable(player.getEffect(AquamiraeMobEffects.THREE_BOLT_ARMOR.get()))
					.map(effect -> 1f - Mth.clamp(effect.getDuration() / 120f, 0f, 1f))
					.orElse(1f);
			if (water1 && water2 && water3 && water4) return 1.4f * factor;
			if (water1 && water2 && water3) return 0.5f * factor;
			if (water1 && water2) return 0.2f * factor;
			return 0f;
		}

		public Helmet() {
			super(AquamiraeArmorMaterials.THREE_BOLT, Type.HELMET,
					new Properties().rarity(Rarity.EPIC));
		}

		@Override
		public Optional<Ability> getAbility(ItemStack stack) {
			return Optional.of(ABILITY);
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
			return Aquamirae.addAttribute(super.getDefaultAttributeModifiers(slot), AquamiraeAttributes.DEPTHS_FURY.get(),
					UUID.fromString("AD4402DF-F088-42BA-9AD0-4FA2A379EF24"), AttributeModifier.Operation.MULTIPLY_BASE,
					0.4, slot == this.type.getSlot());
		}
	}

	public static class Suit extends ThreeBoltArmor {

		public Suit() {
			super(AquamiraeArmorMaterials.THREE_BOLT_SUIT, Type.CHESTPLATE,
					new Properties().rarity(Rarity.UNCOMMON));
		}

		@Override
		public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
			if (!slot.allowModification(player) || action != ClickAction.SECONDARY) return false;
			final var tanks = getTanks(stack);
			if (OxygenHolder.is(other)) {
				if (tanks.getFirst().isEmpty()) {
					setTanks(stack, Pair.of(other, tanks.getSecond()));
					access.set(ItemStack.EMPTY);
					playTankEquipSound(player);
					return true;
				} else if (tanks.getSecond().isEmpty()) {
					setTanks(stack, Pair.of(tanks.getFirst(), other));
					access.set(ItemStack.EMPTY);
					playTankEquipSound(player);
					return true;
				} else return false;
			} else if (other.isEmpty()) {
				if (!tanks.getSecond().isEmpty()) {
					access.set(tanks.getSecond());
					setTanks(stack, Pair.of(tanks.getFirst(), ItemStack.EMPTY));
					playTankUnequipSound(player);
					return true;
				} else if (!tanks.getFirst().isEmpty()) {
					access.set(tanks.getFirst());
					setTanks(stack, Pair.of(ItemStack.EMPTY, tanks.getSecond()));
					playTankUnequipSound(player);
					return true;
				} else return false;
			} else return false;
		}

		@Override
		public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
			final var tanks = getTanks(stack);
			tooltip.add(Component.translatable("item.unbreakable").withStyle(ChatFormatting.LIGHT_PURPLE));
			tooltip.add(tanks.getFirst().isEmpty()
					? Component.literal("§8 - Oxygen Tank Slot")
					: Component.literal("§8 - §f%s §8(%s§8)".formatted(
					tanks.getFirst().getHoverName().getString(),
					OxygenHolder.getState(tanks.getFirst()))));
			tooltip.add(tanks.getSecond().isEmpty()
					? Component.literal("§8 - Oxygen Tank Slot")
					: Component.literal("§8 - §f%s §8(%s§8)".formatted(
					tanks.getSecond().getHoverName().getString(),
					OxygenHolder.getState(tanks.getSecond()))));
		}

		@Override
		public boolean isBarVisible(ItemStack stack) {
			return getCapacity(stack) > 0;
		}

		@Override
		public int getBarWidth(ItemStack stack) {
			final var capacity = getCapacity(stack);
			if (capacity == 0) return 0;
			return Math.round(13f * ((float) getOxygen(stack) / (float) capacity));
		}

		@Override
		public int getBarColor(ItemStack stack) {
			return 0xFF1FA3FF;
		}

		private void playTankEquipSound(Player player) {
			player.level().playSound(null, player, SoundEvents.ITEM_FRAME_ADD_ITEM,
					SoundSource.PLAYERS, 0.5f, 1f);
		}

		private void playTankUnequipSound(Player player) {
			player.level().playSound(null, player, SoundEvents.ITEM_FRAME_REMOVE_ITEM,
					SoundSource.PLAYERS, 0.5f, 1f);
		}
	}

	public static class Leggings extends ThreeBoltArmor {
		public Leggings() {
			super(AquamiraeArmorMaterials.THREE_BOLT, Type.LEGGINGS,
					new Properties().rarity(Rarity.UNCOMMON));
		}
	}

	public static class Boots extends ThreeBoltArmor {
		public Boots() {
			super(AquamiraeArmorMaterials.THREE_BOLT, Type.BOOTS,
					new Properties().rarity(Rarity.UNCOMMON));
		}
	}
}
