package com.obscuria.aquamirae.client.extension;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.armor.ModelThreeBoltArmor;
import com.obscuria.aquamirae.common.item.armor.ThreeBoltArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class ThreeBoltArmorExtension implements IClientItemExtensions {
    @NotNull
    @Override
    public HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity entity, ItemStack stack,
                                                                       EquipmentSlot slot, HumanoidModel origin) {
        final var armor = new ModelThreeBoltArmor<>(Minecraft.getInstance()
                .getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR));
        final var model = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
                "hat", empty(),
                "head", isHead(slot) ? armor.head : empty(),
                "body", isChest(slot) ? armor.body : isLegs(slot) ? armor.leggingsBody : empty(),
                "right_arm", isChest(slot) ? armor.rightArm : empty(),
                "left_arm", isChest(slot) ? armor.leftArm : empty(),
                "right_leg", isLegs(slot) ? armor.rightLeg : isFeet(slot) ? armor.rightBoot : empty(),
                "left_leg", isLegs(slot) ? armor.leftLeg : isFeet(slot) ? armor.leftBoot : empty())));
        if (isChest(slot)) {
            final var tanks = ThreeBoltArmor.Suit.getTanks(stack);
            armor.firstTank.visible = !tanks.getFirst().isEmpty();
            armor.secondTank.visible = !tanks.getSecond().isEmpty();
        }
        model.crouching = entity.isShiftKeyDown();
        model.riding = origin.riding;
        model.young = entity.isBaby();
        return model;
    }

    @Override
    public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTick) {
        if (!AquamiraeConfig.Client.overlay.get()) return;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Aquamirae.key("textures/gui/three_bolt_overlay.png"));
        final var tesselator = Tesselator.getInstance();
        final var builder = tesselator.getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.vertex(0.0D, height, -90.0D).uv(0.0F, 1.0F).endVertex();
        builder.vertex(width, height, -90.0D).uv(1.0F, 1.0F).endVertex();
        builder.vertex(width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        builder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private ModelPart empty() {
        return new ModelPart(Collections.emptyList(), Collections.emptyMap());
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
