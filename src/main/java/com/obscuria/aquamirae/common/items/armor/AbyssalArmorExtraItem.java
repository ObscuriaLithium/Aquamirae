
package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.*;
import com.obscuria.obscureapi.util.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbyssalArmorExtraItem extends ArmorItem {
	public AbyssalArmorExtraItem(ArmorItem.Type type, Item.@NotNull Properties properties) {
		super(AquamiraeTiers.ABYSSAL_ARMOR_EXTRA, type, properties.rarity(Rarity.EPIC));
	}

	public final Ability ABILITY_HALFSET = Ability.create(Aquamirae.MODID, "abyssal_armor_half").style(Ability.Style.ATTRIBUTE)
			.build(AbyssalArmorExtraItem.class);
	public final Ability ABILITY_FULLSET_1 = Ability.create(Aquamirae.MODID, "abyssal_armor_full_1").var(90, "s")
			.build(AbyssalArmorExtraItem.class);
	public final Ability ABILITY_FULLSET_2 = Ability.create(Aquamirae.MODID, "abyssal_armor_full_2")
			.build(AbyssalArmorExtraItem.class);
	public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "weapon").type(Bonus.Type.POWER, Bonus.Operation.AMOUNT).value(3).build();

	@Override
	public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
		if (ItemUtils.getArmorPieces(entity, AbyssalArmorItem.class) == 3) {
			final Vec3 center = entity.position().add(0, 1, 0);
			List<Monster> list = world.getEntitiesOfClass(Monster.class, new AABB(center, center).inflate(4), e -> true).stream()
					.sorted(Comparator.comparingDouble(entities -> entities.distanceToSqr(center))).toList();
			for (Monster monster : list) monster.addEffect(new MobEffectInstance(AquamiraeMobEffects.ABYSS_BLINDNESS.get(), 10, 0, false, false));
			final double radius = 4;
			world.addParticle(ParticleTypes.DRAGON_BREATH, entity.getX() + Math.cos(entity.tickCount * 0.05) * radius, entity.getY() + 0.5,
					entity.getZ() + Math.sin(entity.tickCount * 0.05) * radius, 0, 0, 0);
			world.addParticle(ParticleTypes.DRAGON_BREATH, entity.getX() + Math.cos(entity.tickCount * 0.05 + 3.12) * radius, entity.getY() + 0.5,
					entity.getZ() + Math.sin(entity.tickCount * 0.05 + 3.12) * radius, 0, 0, 0);
		}
	}

	@ClassItem(clazz = "aquamirae:sea_wolf", type = "armor")
	public static class Tiara extends AbyssalArmorExtraItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET_1 = super.ABILITY_FULLSET_1;
		@ClassAbility public final Ability ABILITY_FULLSET_2 = super.ABILITY_FULLSET_2;
		@ClassBonus public final Bonus BONUS = super.BONUS;

		public Tiara() {
			super(Type.HELMET, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR)).tiara,
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
			return "aquamirae:textures/entity/armor/abyssal_tiara.png";
		}
	}
}
