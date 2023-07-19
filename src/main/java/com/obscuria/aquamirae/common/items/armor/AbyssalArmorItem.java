
package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.aquamirae.registry.AquamiraeEffects;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.api.utils.ItemUtils;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.common.classes.ability.context.AbilityContext;
import com.obscuria.obscureapi.common.classes.ability.context.SimpleAbilityContext;
import com.obscuria.obscureapi.common.classes.bonus.Bonus;
import com.obscuria.obscureapi.common.classes.bonus.RegisterBonus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "armor")
public abstract class AbyssalArmorItem extends ArmorItem {
	public static final Ability HALFSET;
	public static final Ability FULLSET_HEAUME;
	public static final Ability FULLSET_TIARA;
	public static final Bonus BONUS_AMOUNT;
	public static final Bonus BONUS_PERCENT;

	public AbyssalArmorItem(ArmorMaterial material, ArmorItem.Type type, Settings settings) {
		super(material, type, settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if (entity instanceof LivingEntity living) {
			HALFSET.use(new SimpleAbilityContext(living, stack));
			FULLSET_TIARA.use(new SimpleAbilityContext(living, stack));
		}
	}

	public static boolean applyStrongArmor(AbilityContext context, List<Integer> vars) {
		if (ItemUtils.countArmorPieces(context.getUser(), AbyssalArmorItem.class) >= 2) {
			final StatusEffectInstance effect = context.getUser().getStatusEffect(AquamiraeEffects.STRONG_ARMOR);
			if (effect != null) effect.upgrade(new StatusEffectInstance(AquamiraeEffects.STRONG_ARMOR, 4, 0, false, false));
			else context.getUser().addStatusEffect(new StatusEffectInstance(AquamiraeEffects.STRONG_ARMOR, 4, 0, false, false));
			return true;
		}
		return false;
	}

	public static boolean tryAvoidDeath(LivingEntity entity) {
		if (entity instanceof PlayerEntity player) {
			if (player.getItemCooldownManager().isCoolingDown(AquamiraeItems.ABYSSAL_HEAUME)) return false;
			final int cooldown = 20 * FULLSET_HEAUME.getCost(entity);
			player.getItemCooldownManager().set(AquamiraeItems.ABYSSAL_HEAUME, cooldown);
			entity.addStatusEffect(new StatusEffectInstance(AquamiraeEffects.CRYSTALLIZATION, cooldown, 0, true, true));
			entity.setHealth(entity.getMaxHealth());
			final ItemStack head = entity.getEquippedStack(EquipmentSlot.HEAD);
			final ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
			final ItemStack legs = entity.getEquippedStack(EquipmentSlot.LEGS);
			final ItemStack feet = entity.getEquippedStack(EquipmentSlot.FEET);
			head.damage(50, entity, e -> {});
			chest.damage(50, entity, e -> {});
			legs.damage(50, entity, e -> {});
			feet.damage(50, entity, e -> {});
			entity.getWorld().playSound(null, entity.getBlockPos().up(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1, 1);
			return true;
		}
		return false;
	}

	static {
		HALFSET = Ability.create(Aquamirae.MODID, "abyssal_armor_half")
				.action(AbyssalArmorItem::applyStrongArmor)
				.style(Ability.Style.ATTRIBUTE)
				.build(AbyssalArmorItem.class);
		FULLSET_HEAUME = Ability.create(Aquamirae.MODID, "abyssal_armor_full_1")
				.cost(Ability.CostType.COOLDOWN, 60)
				.build(AbyssalArmorItem.class);
		FULLSET_TIARA = Ability.create(Aquamirae.MODID, "abyssal_armor_full_2")
				.action(AbyssalArmorItem::applyTiaraEffect)
				.build(AbyssalArmorItem.class);
		BONUS_AMOUNT = Bonus.create()
				.target(Aquamirae.SEA_WOLF, "weapon")
				.type(Bonus.Type.POWER, Bonus.Operation.AMOUNT)
				.value(3)
				.build();
		BONUS_PERCENT = Bonus.create()
				.target(Aquamirae.SEA_WOLF, "weapon")
				.type(Bonus.Type.POWER, Bonus.Operation.PERCENT)
				.value(25)
				.build();
	}

	public static boolean applyTiaraEffect(AbilityContext context, List<Integer> vars) {
		if (context.getUser().getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof AbyssalArmorItem.Tiara
				&& ItemUtils.countArmorPieces(context.getUser(), AbyssalArmorItem.class) == 4) {
			final Vec3d center = context.getUser().getPos().add(0, 1, 0);
			final List<HostileEntity> list = context.getUser().getWorld().getEntitiesByClass(HostileEntity.class, new Box(center, center).expand(4), e -> true);
			for (HostileEntity monster: list) monster.addStatusEffect(new StatusEffectInstance(AquamiraeEffects.ABYSS_BLINDNESS, 10, 0, false, false));
			final double radius = 4;
			context.getUser().getWorld().addParticle(ParticleTypes.DRAGON_BREATH,
					center.getX() + Math.cos(context.getUser().age * 0.05) * radius,
					center.getY() - 0.5,
					center.getZ() + Math.sin(context.getUser().age * 0.05) * radius,
					0, 0, 0);
			context.getUser().getWorld().addParticle(ParticleTypes.DRAGON_BREATH,
					center.getX() + Math.cos(context.getUser().age * 0.05 + 3.12) * radius,
					center.getY() - 0.5,
					center.getZ() + Math.sin(context.getUser().age * 0.05 + 3.12) * radius,
					0, 0, 0);
			return true;
		}
		return false;
	}

	public static class Heaume extends AbyssalArmorItem {
		@RegisterAbility public static final Ability HALFSET;
		@RegisterAbility public static final Ability FULLSET_HEAUME;
		@RegisterBonus public static final Bonus BONUS;

		public Heaume(Settings settings) {
			super(AquamiraeMaterials.ABYSSAL_ARMOR, Type.HELMET, settings);
		}

		static {
			HALFSET = AbyssalArmorItem.HALFSET;
			FULLSET_HEAUME = AbyssalArmorItem.FULLSET_HEAUME;
			BONUS = AbyssalArmorItem.BONUS_AMOUNT;
		}
	}

	public static class Brigantine extends AbyssalArmorItem {
		@RegisterAbility public static final Ability HALFSET;
		@RegisterBonus public static final Bonus BONUS;

		public Brigantine(Settings settings) {
			super(AquamiraeMaterials.ABYSSAL_ARMOR, Type.CHESTPLATE, settings);
		}

		static {
			HALFSET = AbyssalArmorItem.HALFSET;
			BONUS = AbyssalArmorItem.BONUS_PERCENT;
		}
	}

	public static class Leggings extends AbyssalArmorItem {
		@RegisterAbility public static final Ability HALFSET;
		@RegisterBonus public static final Bonus BONUS;

		public Leggings(Settings settings) {
			super(AquamiraeMaterials.ABYSSAL_ARMOR, Type.LEGGINGS, settings);
		}

		static {
			HALFSET = AbyssalArmorItem.HALFSET;
			BONUS = AbyssalArmorItem.BONUS_PERCENT;
		}
	}

	public static class Boots extends AbyssalArmorItem {
		@RegisterAbility public static final Ability HALFSET;
		@RegisterBonus public static final Bonus BONUS;

		public Boots(Settings settings) {
			super(AquamiraeMaterials.ABYSSAL_ARMOR, Type.BOOTS, settings);
		}

		static {
			HALFSET = AbyssalArmorItem.HALFSET;
			BONUS = AbyssalArmorItem.BONUS_AMOUNT;
		}
	}

	public static class Tiara extends AbyssalArmorItem {
		@RegisterAbility public static final Ability HALFSET;
		@RegisterAbility public static final Ability FULLSET_TIARA;
		@RegisterBonus public static final Bonus BONUS;

		public Tiara(Settings settings) {
			super(AquamiraeMaterials.ABYSSAL_ARMOR_EXTRA, Type.HELMET, settings);
		}

		static {
			HALFSET = AbyssalArmorItem.HALFSET;
			FULLSET_TIARA = AbyssalArmorItem.FULLSET_TIARA;
			BONUS = AbyssalArmorItem.BONUS_AMOUNT;
		}
	}
}
