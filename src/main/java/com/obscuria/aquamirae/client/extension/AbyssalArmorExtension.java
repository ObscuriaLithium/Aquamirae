package com.obscuria.aquamirae.client.extension;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.common.item.armor.AbyssalArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class AbyssalArmorExtension implements IClientItemExtensions {
    @NotNull
    @Override
    public HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity entity, ItemStack stack,
                                                                       EquipmentSlot slot, HumanoidModel origin) {
        final var armor = new ModelAbyssalArmor<>(Minecraft.getInstance()
                .getEntityModels().bakeLayer(AquamiraeLayers.ABYSSAL_ARMOR));
        final var model = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
                "hat", empty(),
                "head", isHead(slot) ? (isTiara(stack) ? armor.tiara : armor.helmet) : empty(),
                "body", isChest(slot) ? armor.body : empty(),
                "right_arm", isChest(slot) ? armor.rightArm : empty(),
                "left_arm", isChest(slot) ? armor.leftArm : empty(),
                "right_leg", isLegs(slot) ? armor.rightLeg : isFeet(slot) ? armor.rightBoot : empty(),
                "left_leg", isLegs(slot) ? armor.leftLeg : isFeet(slot) ? armor.leftBoot : empty())));
        model.crouching = entity.isShiftKeyDown();
        model.riding = origin.riding;
        model.young = entity.isBaby();
        return model;
    }

    private ModelPart empty() {
        return new ModelPart(Collections.emptyList(), Collections.emptyMap());
    }

    private boolean isTiara(ItemStack stack) {
        return stack.getItem() instanceof AbyssalArmor.Tiara;
    }

    private boolean isHead(EquipmentSlot slot) {
        return slot == EquipmentSlot.HEAD;
    }

    private boolean isChest(EquipmentSlot slot) {
        return slot == EquipmentSlot.CHEST;
    }

    private boolean isLegs(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    private boolean isFeet(EquipmentSlot slot) {
        return slot == EquipmentSlot.FEET;
    }
}
