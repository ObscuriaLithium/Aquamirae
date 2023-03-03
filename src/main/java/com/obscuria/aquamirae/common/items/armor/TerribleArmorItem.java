
package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

@Mod.EventBusSubscriber
public abstract class TerribleArmorItem extends ArmorItem {

	public TerribleArmorItem(EquipmentSlot slot, Item.@NotNull Properties properties) {
		super(AquamiraeTiers.TERRIBLE_ARMOR, slot, properties.tab(AquamiraeMod.TAB));
	}

	public final Ability ABILITY_HALFSET = Ability.create(AquamiraeMod.MODID).description("terrible_armor_half").cost(Ability.Cost.COOLDOWN, 10)
			.variables(120, 6).modifiers("%", "s").build(this);
	public final Ability ABILITY_FULLSET = Ability.create(AquamiraeMod.MODID).description("terrible_armor_full").variables(4)
			.modifiers("s").build(this);

	@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "armor")
	public static class Helmet extends TerribleArmorItem {

		@ClassAbility
		public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus
		public final Bonus BONUS = Bonus.create().target(AquamiraeMod.SEA_WOLF, "weapon").type(Bonus.Type.POWER, Bonus.Operation.PERCENT).value(20).build();

		public Helmet() {
			super(EquipmentSlot.HEAD, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).head,
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
			return "aquamirae:textures/entity/armor/terrible_helmet.png";
		}
	}

	@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "armor")
	public static class Chestplate extends TerribleArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus
		public final Bonus BONUS = Bonus.create().target(AquamiraeMod.SEA_WOLF, "weapon").type(Bonus.Type.POWER, Bonus.Operation.PERCENT).value(10).build();

		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"body", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).body,
							"left_arm", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).left_arm,
							"right_arm", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).right_arm,
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
			return "aquamirae:textures/entity/armor/terrible_chestplate.png";
		}
	}

	@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "armor")
	public static class Leggings extends TerribleArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus
		public final Bonus BONUS = Bonus.create().target(AquamiraeMod.SEA_WOLF, "weapon").type(Bonus.Type.COOLDOWN, Bonus.Operation.PERCENT).value(-10).build();

		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("left_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).left_shoe2,
									"right_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).right_shoe2,
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
			return "aquamirae:textures/entity/armor/terrible_leggings.png";
		}
	}

	@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "armor")
	public static class Boots extends TerribleArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus
		public final Bonus BONUS = Bonus.create().target(AquamiraeMod.SEA_WOLF, "weapon").type(Bonus.Type.COOLDOWN, Bonus.Operation.PERCENT).value(-20).build();

		public Boots() {
			super(EquipmentSlot.FEET, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("left_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).left_shoe,
									"right_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).right_shoe,
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
			return "aquamirae:textures/entity/armor/terrible_boots.png";
		}
	}
}
