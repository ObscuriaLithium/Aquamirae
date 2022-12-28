
package com.obscuria.aquamirae.world.items.armor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.client.models.armor.ModelThreeBoltArmor;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class ThreeBoltArmorItem extends ArmorItem implements IClassItem, IAbilityItem, IBonusItem {
	public final EquipmentSlotType BONUS_SLOT;

	public ThreeBoltArmorItem(EquipmentSlotType slot, Item.Properties properties) {
		super(new IArmorMaterial() {
			@Override
			public int getDurabilityForSlot(@Nonnull EquipmentSlotType slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 75;
			}

			@Override
			public int getDefenseForSlot(@Nonnull EquipmentSlotType slot) {
				return new int[]{3, 5, 5, 7}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public @Nonnull SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_IRON;
			}

			@Override
			public @Nonnull Ingredient getRepairIngredient() {
				return Ingredient.EMPTY;
			}

			@Override
			public @Nonnull String getName() {
				return "three_bolt";
			}

			@Override
			public float getToughness() {
				return 2f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.1f;
			}
		}, slot, properties.tab(AquamiraeMod.TAB));
		this.BONUS_SLOT = slot;
	}

	public final ObscureAbility ABILITY_HALFSET = new ObscureAbility(this, "three_bolt_armor_half", ObscureAbility.Cost.NONE, 0);
	public final ObscureAbility ABILITY_FULLSET = new ObscureAbility(this, "three_bolt_armor_full", ObscureAbility.Cost.NONE, 0);
	public final ObscureBonus BONUS_TOP = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.ARMOR, ObscureBonus.Type.POWER, ObscureBonus.Operation.PERCENT, 30);
	public final ObscureBonus BONUS_BOTTOM = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.POWER, ObscureBonus.Operation.PERCENT, 30);

	public List<ObscureAbility> getObscureAbilities() {
		return Arrays.asList(ABILITY_FULLSET, ABILITY_HALFSET);
	}

	public List<ObscureBonus> getObscureBonuses() {
		if (this.BONUS_SLOT == EquipmentSlotType.HEAD || this.BONUS_SLOT == EquipmentSlotType.CHEST) return Collections.singletonList(BONUS_TOP);
		return Collections.singletonList(BONUS_BOTTOM);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.ARMOR;
	}

	public static class Helmet extends ThreeBoltArmorItem {
		public Helmet() {
			super(EquipmentSlotType.HEAD, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.head = new ModelThreeBoltArmor<>().head;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public void renderHelmetOverlay(ItemStack stack, PlayerEntity player, int width, int height, float partialTicks) {
			if (!AquamiraeConfig.Client.overlay.get()) return;
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.defaultBlendFunc();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableAlphaTest();
			Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(AquamiraeMod.MODID,"textures/screens/three_bolt_overlay.png"));
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuilder();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.vertex(0.0D, height, -90.0D).uv(0.0F, 1.0F).endVertex();
			bufferbuilder.vertex(width,height, -90.0D).uv(1.0F, 1.0F).endVertex();
			bufferbuilder.vertex(width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
			bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
			tessellator.end();
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/three_bolt_helmet.png";
		}
	}

	public static class Chestplate extends ThreeBoltArmorItem {
		public Chestplate() {
			super(EquipmentSlotType.CHEST, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.body = new ModelThreeBoltArmor<>().body;
			model.leftArm = new ModelThreeBoltArmor<>().leftArm;
			model.rightArm = new ModelThreeBoltArmor<>().rightArm;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/three_bolt_chestplate.png";
		}

		@Override
		public void onArmorTick(ItemStack stack, World world, PlayerEntity entity) {
			if (entity.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof ThreeBoltArmorItem) {
				if (entity.getAirSupply() <= 0) {
					entity.setAirSupply(280);
					if (stack.hurt(10, new Random(), null)) {
						stack.shrink(1);
						stack.setDamageValue(0);
					}
					if (!entity.level.isClientSide()) entity.level.playSound(null, new BlockPos(entity.getX(),
							entity.getY(), entity.getZ()), AquamiraeSounds.EFFECT_OXYGEN.get(), SoundCategory.PLAYERS, 1, 1);
				}
			}
		}
	}

	public static class Leggings extends ThreeBoltArmorItem {
		public Leggings() {
			super(EquipmentSlotType.LEGS, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.body = new ModelThreeBoltArmor<>().leggingsBody;
			model.leftLeg = new ModelThreeBoltArmor<>().leftLeg;
			model.rightLeg = new ModelThreeBoltArmor<>().rightLeg;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/three_bolt_leggings.png";
		}
	}

	public static class Boots extends ThreeBoltArmorItem {
		public Boots() {
			super(EquipmentSlotType.FEET, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.leftLeg = new ModelThreeBoltArmor<>().leftBoot;
			model.rightLeg = new ModelThreeBoltArmor<>().rightBoot;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/three_bolt_boots.png";
		}
	}
}
