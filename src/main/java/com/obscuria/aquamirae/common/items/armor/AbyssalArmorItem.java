
package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
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

@ClassItem(clazz = "aquamirae:sea_wolf", type = "armor")
public abstract class AbyssalArmorItem extends ArmorItem {

	public AbyssalArmorItem(ArmorItem.Type type, Item.@NotNull Properties properties) {
		super(AquamiraeTiers.ABYSSAL_ARMOR, type, properties.rarity(Rarity.EPIC));
	}

	public final Ability ABILITY_HALFSET = Ability.create(Aquamirae.MODID, "abyssal_armor_half").style(Ability.Style.ATTRIBUTE)
			.build(AbyssalArmorItem.class);
	public final Ability ABILITY_FULLSET_1 = Ability.create(Aquamirae.MODID, "abyssal_armor_full_1").var(90, "s")
			.build(AbyssalArmorItem.class);
	public final Ability ABILITY_FULLSET_2 = Ability.create(Aquamirae.MODID, "abyssal_armor_full_2")
			.build(AbyssalArmorItem.class);
	public final Bonus BONUS_1 = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Bonus.Type.POWER, Bonus.Operation.AMOUNT).value(3).build();
	public final Bonus BONUS_2 = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Bonus.Type.POWER, Bonus.Operation.PERCENT).value(25).build();

	@Override
	public void onArmorTick(ItemStack itemstack, Level world, Player player) {
		if (ItemUtils.getArmorPieces(player, AbyssalArmorItem.class, AbyssalArmorExtraItem.class) >= 2) {
			final MobEffectInstance EFFECT = player.getEffect(AquamiraeMobEffects.STRONG_ARMOR.get());
			if (EFFECT != null) EFFECT.update(new MobEffectInstance(AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
			else player.addEffect(new MobEffectInstance(AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
		}
	}

	public static class Heaume extends AbyssalArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
		@ClassAbility public final Ability ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
		@ClassBonus public final Bonus BONUS = super.BONUS_1;

		public Heaume() {
			super(Type.HELMET, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR)).helmet,
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

	public static class Brigantine extends AbyssalArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
		@ClassAbility public final Ability ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
		@ClassBonus public final Bonus BONUS = super.BONUS_2;

		public Brigantine() {
			super(Type.CHESTPLATE, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"body", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR)).body,
							"left_arm", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR)).left_arm,
							"right_arm", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR)).right_arm,
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

	public static class Leggings extends AbyssalArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
		@ClassAbility public final Ability ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
		@ClassBonus public final Bonus BONUS = super.BONUS_2;

		public Leggings() {
			super(Type.LEGGINGS, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"left_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR)).left_leg,
							"right_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR)).right_leg,
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

	public static class Boots extends AbyssalArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
		@ClassAbility public final Ability ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
		@ClassBonus public final Bonus BONUS = super.BONUS_1;

		public Boots() {
			super(Type.BOOTS, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"left_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR)).left_boot,
							"right_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR)).right_boot,
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
