
package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
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
@ClassItem(clazz = "aquamirae:sea_wolf", type = "armor")
public abstract class TerribleArmorItem extends ArmorItem {

	public TerribleArmorItem(ArmorItem.Type type, Item.@NotNull Properties properties) {
		super(AquamiraeTiers.TERRIBLE_ARMOR, type, properties);
	}

	public final Ability ABILITY_HALFSET = Ability.create(Aquamirae.MODID, "terrible_armor_half").cost(Ability.Cost.Type.COOLDOWN, 10)
			.var(120, "%").var(6, "s").build(TerribleArmorItem.class);
	public final Ability ABILITY_FULLSET = Ability.create(Aquamirae.MODID, "terrible_armor_full").var(4, "s")
			.build(TerribleArmorItem.class);

	public static class Helmet extends TerribleArmorItem {

		@ClassAbility
		public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus
		public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Bonus.Type.POWER, Bonus.Operation.PERCENT).value(20).build();

		public Helmet() {
			super(Type.HELMET, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.TERRIBLE_ARMOR)).head,
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    if (living == null) return armorModel;
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

	public static class Chestplate extends TerribleArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus
		public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Bonus.Type.POWER, Bonus.Operation.PERCENT).value(10).build();

		public Chestplate() {
			super(Type.CHESTPLATE, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"body", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.TERRIBLE_ARMOR)).body,
							"left_arm", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.TERRIBLE_ARMOR)).left_arm,
							"right_arm", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.TERRIBLE_ARMOR)).right_arm,
							"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    if (living == null) return armorModel;
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

	public static class Leggings extends TerribleArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus
		public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Bonus.Type.COOLDOWN, Bonus.Operation.PERCENT).value(-10).build();

		public Leggings() {
			super(Type.LEGGINGS, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("left_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.TERRIBLE_ARMOR)).left_shoe2,
									"right_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.TERRIBLE_ARMOR)).right_shoe2,
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    if (living == null) return armorModel;
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

	public static class Boots extends TerribleArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus
		public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Bonus.Type.COOLDOWN, Bonus.Operation.PERCENT).value(-20).build();

		public Boots() {
			super(Type.BOOTS, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("left_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.TERRIBLE_ARMOR)).left_shoe,
									"right_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.TERRIBLE_ARMOR)).right_shoe,
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    if (living == null) return armorModel;
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
