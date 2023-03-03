
package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.api.common.classes.*;
import com.obscuria.obscureapi.util.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbyssalArmorItem extends ArmorItem {

	public AbyssalArmorItem(EquipmentSlot slot, Item.@NotNull Properties properties) {
		super(AquamiraeTiers.ABYSSAL_ARMOR, slot, properties.rarity(Rarity.EPIC).tab(AquamiraeMod.TAB));
	}

	public final Ability ABILITY_HALFSET = Ability.create(AquamiraeMod.MODID).description("abyssal_armor_half")
			.style(Ability.Style.ATTRIBUTE).build(this);
	public final Ability ABILITY_FULLSET_1 = Ability.create(AquamiraeMod.MODID).description("abyssal_armor_full_1").variables(90)
			.modifiers("s").build(this);
	public final Ability ABILITY_FULLSET_2 = Ability.create(AquamiraeMod.MODID).description("abyssal_armor_full_2").build(this);
	public final Bonus BONUS_1 = Bonus.create().target(AquamiraeMod.SEA_WOLF, "weapon").type(Bonus.Type.POWER, Bonus.Operation.AMOUNT).value(3).build();
	public final Bonus BONUS_2 = Bonus.create().target(AquamiraeMod.SEA_WOLF, "weapon").type(Bonus.Type.POWER, Bonus.Operation.PERCENT).value(25).build();

	@Override
	public void onArmorTick(ItemStack itemstack, Level world, Player player) {
		if (ItemUtils.getArmorPieces(player, AbyssalArmorItem.class, AbyssalArmorExtraItem.class) >= 2) {
			final MobEffectInstance EFFECT = player.getEffect(AquamiraeMobEffects.STRONG_ARMOR.get());
			if (EFFECT != null) EFFECT.update(new MobEffectInstance(AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
			else player.addEffect(new MobEffectInstance(AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
		}
	}

	@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "armor")
	public static class Heaume extends AbyssalArmorItem {

		@ClassAbility
		public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
		@ClassAbility public final Ability ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
		@ClassBonus
		public final Bonus BONUS = super.BONUS_1;

		public Heaume() {
			super(EquipmentSlot.HEAD, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).helmet,
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/abyssal_heaume.png";
		}
	}

	@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "armor")
	public static class Brigantine extends AbyssalArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
		@ClassAbility public final Ability ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
		@ClassBonus public final Bonus BONUS = super.BONUS_2;

		public Brigantine() {
			super(EquipmentSlot.CHEST, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"body", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).body,
							"left_arm", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).left_arm,
							"right_arm", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).right_arm,
							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/abyssal_brigantine.png";
		}
	}

	@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "armor")
	public static class Leggings extends AbyssalArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
		@ClassAbility public final Ability ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
		@ClassBonus public final Bonus BONUS = super.BONUS_2;

		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"left_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).left_leg,
							"right_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).right_leg,
							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/abyssal_leggings.png";
		}
	}

	@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "armor")
	public static class Boots extends AbyssalArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
		@ClassAbility public final Ability ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
		@ClassBonus public final Bonus BONUS = super.BONUS_1;

		public Boots() {
			super(EquipmentSlot.FEET, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"left_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).left_boot,
							"right_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).right_boot,
							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/abyssal_boots.png";
		}
	}
}
