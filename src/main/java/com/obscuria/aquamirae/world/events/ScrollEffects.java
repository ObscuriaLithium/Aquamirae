package com.obscuria.aquamirae.world.events;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.network.ScrollMessage;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.world.entities.Eel;
import com.obscuria.obscureapi.world.entities.ChakraEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScrollEffects {

    private final PlayerEntity PLAYER;
    private int TICKS;
    private ScrollEffects(PlayerEntity player) {
        PLAYER = player;
    }

    public static void create(PlayerEntity player) {
        if (!player.level.isClientSide) MinecraftForge.EVENT_BUS.register(new ScrollEffects(player));
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (TICKS >= 80) { MinecraftForge.EVENT_BUS.unregister(this); effect(); } TICKS++;
    }

    private void effect() {
        final int type = (int) Math.round(7.0 * Math.random());
        if (PLAYER instanceof ServerPlayerEntity)
            AquamiraeMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) PLAYER), new ScrollMessage(type));
        switch (type) {
            case 1 : { abyss(); break; }
            case 2 : { mimic(); break; }
            case 3 : { MoveUp.create(PLAYER); break; }
            case 4 : { MoveSide.create(PLAYER); break; }
            case 5 : { if (!shelter()) MoveUp.create(PLAYER); break; }
            case 6 : { chakras(); break; }
            case 7 : { Bones.create(PLAYER); break; }
            default : { PLAYER.drop(Items.DIAMOND.getDefaultInstance(), false); break; }
        }
    }

    private void abyss() {
        for (int ix = -1; ix <= 1; ix++)
            for (int iy = 1; iy >= -10; iy--)
                for (int iz = -1; iz <= 1; iz++) {
                    final BlockPos pos = new BlockPos(PLAYER.blockPosition().getX() + ix, PLAYER.blockPosition().getY() + iy, PLAYER.blockPosition().getZ() + iz);
                    if (PLAYER.level.getBlockState(pos).is(AquamiraeMod.SCROLL_DESTROY))
                        PLAYER.level.destroyBlock(pos, true);
                }

    }

    private void mimic() {
        final DrownedEntity drowned = new DrownedEntity(EntityType.DROWNED, PLAYER.level);
        if (PLAYER.level instanceof ServerWorld) {
            final ServerWorld serverLevel = (ServerWorld) PLAYER.level;
            drowned.finalizeSpawn(serverLevel, PLAYER.level.getCurrentDifficultyAt(PLAYER.blockPosition()), SpawnReason.EVENT, null, null);
            drowned.moveTo(PLAYER.getPosition(0f));
            drowned.setItemSlot(EquipmentSlotType.HEAD, PLAYER.getItemBySlot(EquipmentSlotType.HEAD));
            drowned.setItemSlot(EquipmentSlotType.CHEST, PLAYER.getItemBySlot(EquipmentSlotType.CHEST));
            drowned.setItemSlot(EquipmentSlotType.LEGS, PLAYER.getItemBySlot(EquipmentSlotType.LEGS));
            drowned.setItemSlot(EquipmentSlotType.FEET, PLAYER.getItemBySlot(EquipmentSlotType.FEET));
            drowned.setItemInHand(Hand.MAIN_HAND, PLAYER.getItemInHand(Hand.MAIN_HAND));
            drowned.setItemInHand(Hand.OFF_HAND, PLAYER.getItemInHand(Hand.OFF_HAND));
            drowned.setGuaranteedDrop(EquipmentSlotType.HEAD);
            drowned.setGuaranteedDrop(EquipmentSlotType.CHEST);
            drowned.setGuaranteedDrop(EquipmentSlotType.LEGS);
            drowned.setGuaranteedDrop(EquipmentSlotType.FEET);
            drowned.setGuaranteedDrop(EquipmentSlotType.MAINHAND);
            drowned.setGuaranteedDrop(EquipmentSlotType.OFFHAND);
            PLAYER.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
            PLAYER.setItemSlot(EquipmentSlotType.CHEST, ItemStack.EMPTY);
            PLAYER.setItemSlot(EquipmentSlotType.LEGS, ItemStack.EMPTY);
            PLAYER.setItemSlot(EquipmentSlotType.FEET, ItemStack.EMPTY);
            PLAYER.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            PLAYER.setItemInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            serverLevel.addFreshEntity(drowned);
        }
    }

    private boolean shelter() {
        final Vector3d center = PLAYER.getPosition(1F);
        List<Eel> eels = PLAYER.level.getEntitiesOfClass(Eel.class, new AxisAlignedBB(center, center).inflate(128), e -> true).stream()
                .sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).collect(Collectors.toList());
        if (!eels.isEmpty()) { PLAYER.moveTo(eels.get(0).position()); return true; }
        return false;
    }

    private void chakras() {
        for (float i = 0f; i < 1f; i += 0.1F)
            ChakraEntity.summonChakra(PLAYER, AquamiraeEntities.POISONED_CHAKRA.get(), PLAYER.level, null, 20, i, 6000, 1);
    }

    public static class MoveUp {

        private final PlayerEntity PLAYER;
        private int TICKS;

        private MoveUp(PlayerEntity player) {
            PLAYER = player;
        }

        public static void create(PlayerEntity player) {
            if (!player.level.isClientSide) MinecraftForge.EVENT_BUS.register(new MoveUp(player));
        }

        @SubscribeEvent
        public void tick(TickEvent.ServerTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            if (TICKS >= 60) MinecraftForge.EVENT_BUS.unregister(this);
            if (TICKS == 0 || TICKS == 40) { PLAYER.setDeltaMovement(PLAYER.getDeltaMovement().add(new Vector3d(0, 1.5f, 0))); PLAYER.hurtMarked = true; }
            if (TICKS == 20 || TICKS == 60) { PLAYER.setDeltaMovement(PLAYER.getDeltaMovement().add(new Vector3d(0, -1.0f, 0))); PLAYER.hurtMarked = true; }
            TICKS++;
        }
    }

    public static class MoveSide {

        private final PlayerEntity PLAYER;
        private int TICKS;

        private MoveSide(PlayerEntity player) {
            PLAYER = player;
        }

        public static void create(PlayerEntity player) {
            if (!player.level.isClientSide) MinecraftForge.EVENT_BUS.register(new MoveSide(player));
        }

        @SubscribeEvent
        public void tick(TickEvent.ServerTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            if (TICKS >= 20) MinecraftForge.EVENT_BUS.unregister(this);
            if (TICKS == 0) { PLAYER.setDeltaMovement(PLAYER.getDeltaMovement().add(new Vector3d(0, 1.5f, 0))); PLAYER.hurtMarked = true; }
            if (TICKS == 20) {
                PLAYER.setDeltaMovement(PLAYER.getDeltaMovement().add(PLAYER.getPosition(1f).vectorTo(new Vector3d(PLAYER.getX() +
                        Math.cos(PLAYER.xRot) * 3f, PLAYER.getY() + 0.5f, PLAYER.getZ() + Math.sin(PLAYER.xRot) * 3f))));
                PLAYER.hurtMarked = true;
            }
            TICKS++;
        }
    }

    public static class Bones {

        private final PlayerEntity PLAYER;
        private int TICKS;
        private int RELOAD;

        private Bones(PlayerEntity player) {
            PLAYER = player;
        }

        public static void create(PlayerEntity player) {
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
