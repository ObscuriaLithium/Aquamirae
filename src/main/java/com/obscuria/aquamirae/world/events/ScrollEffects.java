package com.obscuria.aquamirae.world.events;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.network.ScrollMessage;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.world.entities.Eel;
import com.obscuria.obscureapi.api.DynamicProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.Comparator;
import java.util.List;

public class ScrollEffects {

    private final Player PLAYER;
    private int TICKS;
    private ScrollEffects(Player player) {
        PLAYER = player;
    }

    public static void create(Player player) {
        if (!player.level.isClientSide) MinecraftForge.EVENT_BUS.register(new ScrollEffects(player));
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (TICKS >= 80) { MinecraftForge.EVENT_BUS.unregister(this); effect(); } TICKS++;
    }

    private void effect() {
        final int type = (int) Math.round(7.0 * Math.random());
        if (PLAYER instanceof ServerPlayer player)
            AquamiraeMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new ScrollMessage(type));
        switch (type) {
            default -> PLAYER.drop(Items.DIAMOND.getDefaultInstance(), false);
            case 1 -> abyss();
            case 2 -> mimic();
            case 3 -> MoveUp.create(PLAYER);
            case 4 -> MoveSide.create(PLAYER);
            case 5 -> { if (!shelter()) MoveUp.create(PLAYER); }
            case 6 -> chakras();
            case 7 -> Bones.create(PLAYER);
        }
    }

    private void abyss() {
        for (int ix = -1; ix <= 1; ix++)
            for (int iy = 1; iy >= -10; iy--)
                for (int iz = -1; iz <= 1; iz++) {
                    final BlockPos pos = new BlockPos(PLAYER.getBlockX() + ix, PLAYER.getBlockY() + iy, PLAYER.getBlockZ() + iz);
                    if (PLAYER.level.getBlockState(pos).is(AquamiraeMod.SCROLL_DESTROY)) PLAYER.level.destroyBlock(pos, true);
                }

    }

    private void mimic() {
        final Drowned drowned = new Drowned(EntityType.DROWNED, PLAYER.level);
        if (PLAYER.level instanceof ServerLevel serverLevel) {
            drowned.finalizeSpawn(serverLevel, PLAYER.level.getCurrentDifficultyAt(PLAYER.blockPosition()), MobSpawnType.EVENT, null, null);
            drowned.moveTo(PLAYER.position());
            drowned.setItemSlot(EquipmentSlot.HEAD, PLAYER.getItemBySlot(EquipmentSlot.HEAD));
            drowned.setItemSlot(EquipmentSlot.CHEST, PLAYER.getItemBySlot(EquipmentSlot.CHEST));
            drowned.setItemSlot(EquipmentSlot.LEGS, PLAYER.getItemBySlot(EquipmentSlot.LEGS));
            drowned.setItemSlot(EquipmentSlot.FEET, PLAYER.getItemBySlot(EquipmentSlot.FEET));
            drowned.setItemInHand(InteractionHand.MAIN_HAND, PLAYER.getItemInHand(InteractionHand.MAIN_HAND));
            drowned.setItemInHand(InteractionHand.OFF_HAND, PLAYER.getItemInHand(InteractionHand.OFF_HAND));
            drowned.setGuaranteedDrop(EquipmentSlot.HEAD);
            drowned.setGuaranteedDrop(EquipmentSlot.CHEST);
            drowned.setGuaranteedDrop(EquipmentSlot.LEGS);
            drowned.setGuaranteedDrop(EquipmentSlot.FEET);
            drowned.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            drowned.setGuaranteedDrop(EquipmentSlot.OFFHAND);
            PLAYER.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
            PLAYER.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
            PLAYER.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
            PLAYER.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
            PLAYER.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            PLAYER.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            serverLevel.addFreshEntity(drowned);
        }
    }

    private boolean shelter() {
        final Vec3 center = PLAYER.position();
        List<Eel> eels = PLAYER.level.getEntitiesOfClass(Eel.class, new AABB(center, center).inflate(128), e -> true).stream()
                .sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).toList();
        if (!eels.isEmpty()) { PLAYER.moveTo(eels.get(0).position()); return true; }
        return false;
    }

    private void chakras() {
        for (float i = 0f; i < 1f; i += 0.1F) DynamicProjectile.create(AquamiraeEntities.POISONED_CHAKRA.get(), PLAYER, PLAYER.level, null, 20, i, 6000, 1);
    }

    public static class MoveUp {

        private final Player PLAYER;
        private int TICKS;

        private MoveUp(Player player) {
            PLAYER = player;
        }

        public static void create(Player player) {
            if (!player.level.isClientSide) MinecraftForge.EVENT_BUS.register(new MoveUp(player));
        }

        @SubscribeEvent
        public void tick(TickEvent.ServerTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            if (TICKS >= 60) MinecraftForge.EVENT_BUS.unregister(this);
            if (TICKS == 0 || TICKS == 40) { PLAYER.setDeltaMovement(PLAYER.getDeltaMovement().add(new Vec3(0, 1.5f, 0))); PLAYER.hurtMarked = true; }
            if (TICKS == 20 || TICKS == 60) { PLAYER.setDeltaMovement(PLAYER.getDeltaMovement().add(new Vec3(0, -1.0f, 0))); PLAYER.hurtMarked = true; }
            TICKS++;
        }
    }

    public static class MoveSide {

        private final Player PLAYER;
        private int TICKS;

        private MoveSide(Player player) {
            PLAYER = player;
        }

        public static void create(Player player) {
            if (!player.level.isClientSide) MinecraftForge.EVENT_BUS.register(new MoveSide(player));
        }

        @SubscribeEvent
        public void tick(TickEvent.ServerTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            if (TICKS >= 20) MinecraftForge.EVENT_BUS.unregister(this);
            if (TICKS == 0) { PLAYER.setDeltaMovement(PLAYER.getDeltaMovement().add(new Vec3(0, 1.5f, 0))); PLAYER.hurtMarked = true; }
            if (TICKS == 20) {
                PLAYER.setDeltaMovement(PLAYER.getDeltaMovement().add(PLAYER.position().vectorTo(new Vec3(PLAYER.getX() +
                        Math.cos(PLAYER.getXRot()) * 3f, PLAYER.getY() + 0.5f, PLAYER.getZ() + Math.sin(PLAYER.getXRot()) * 3f))));
                PLAYER.hurtMarked = true;
            }
            TICKS++;
        }
    }

    public static class Bones {

        private final Player PLAYER;
        private int TICKS;
        private int RELOAD;

        private Bones(Player player) {
            PLAYER = player;
        }

        public static void create(Player player) {
            if (!player.level.isClientSide) MinecraftForge.EVENT_BUS.register(new Bones(player));
        }

        @SubscribeEvent
        public void tick(TickEvent.ServerTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            if (TICKS >= 200) MinecraftForge.EVENT_BUS.unregister(this);
            if (RELOAD <= 0) { PLAYER.drop(AquamiraeItems.SHARP_BONES.get().getDefaultInstance(), false); RELOAD = 20; }
            TICKS++; RELOAD--;
        }
    }
}
