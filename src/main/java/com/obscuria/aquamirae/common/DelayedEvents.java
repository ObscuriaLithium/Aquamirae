package com.obscuria.aquamirae.common;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entities.CaptainCorneliaEntity;
import com.obscuria.aquamirae.common.entities.EelEntity;
import com.obscuria.aquamirae.network.ScrollUsePacket;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.api.Icons;
import com.obscuria.obscureapi.api.utils.TextUtils;
import com.obscuria.obscureapi.common.entities.DynamicProjectileEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class DelayedEvents {
    private static final List<Event> EVENTS = new ArrayList<>();

    public static void register() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            EVENTS.forEach(Event::tick);
            List.copyOf(EVENTS).forEach(event -> {
                event.tick++;
                if (event.tick > event.length)
                    EVENTS.remove(event);
            });
        });
    }

    public static void createScrollEvent(PlayerEntity player) {
        EVENTS.add(new ScrollEvent(player));
    }

    public static void createCorneliaEvent(PlayerEntity player, BlockPos pos, boolean summon) {
        EVENTS.add(new CorneliaSummonEvent(player, pos, summon));
    }

    static class ScrollEvent extends Event {
        private final PlayerEntity PLAYER;
        private Runnable subEvent = () -> {};

        private ScrollEvent(PlayerEntity player) {
            super(80, ScrollEvent::onTick);
            this.PLAYER = player;
        }

        @Override
        protected void tick() {
            super.tick();
            subEvent.run();
        }

        private static void onTick(Event e) {
            final ScrollEvent event = (ScrollEvent) e;
            if (event.tick == 80) {
                ServerPlayNetworking.send((ServerPlayerEntity) event.PLAYER, new ScrollUsePacket(event.PLAYER.getId()));
                final int type = (int) Math.round(7.0 * Math.random());
                switch (type) {
                    default -> event.PLAYER.dropItem(Items.DIAMOND.getDefaultStack(), false);
                    case 1 -> event.abyss();
                    case 2 -> event.mimic();
                    case 3 -> event.moveUp();
                    case 4 -> event.moveSide();
                    case 5 -> { if (!event.shelter()) event.moveUp(); }
                    case 6 -> event.chakras();
                    case 7 -> event.bones();
                }
            }
        }

        private void abyss() {
            for (int ix = -1; ix <= 1; ix++)
                for (int iy = 1; iy >= -10; iy--)
                    for (int iz = -1; iz <= 1; iz++) {
                        final BlockPos pos = new BlockPos(PLAYER.getBlockX() + ix, PLAYER.getBlockY() + iy, PLAYER.getBlockZ() + iz);
                        if (PLAYER.getWorld().getBlockState(pos).isIn(Aquamirae.SCROLL_DESTROY)) PLAYER.getWorld().breakBlock(pos, true);
                    }

        }

        private void mimic() {
            final DrownedEntity drowned = new DrownedEntity(EntityType.DROWNED, PLAYER.getWorld());
            drowned.initialize((ServerWorldAccess) PLAYER.getWorld(), PLAYER.getWorld().getLocalDifficulty(PLAYER.getBlockPos()), SpawnReason.EVENT, null, null);
            drowned.setPosition(PLAYER.getPos());
            drowned.equipStack(EquipmentSlot.HEAD, PLAYER.getEquippedStack(EquipmentSlot.HEAD).copy());
            drowned.equipStack(EquipmentSlot.CHEST, PLAYER.getEquippedStack(EquipmentSlot.CHEST).copy());
            drowned.equipStack(EquipmentSlot.LEGS, PLAYER.getEquippedStack(EquipmentSlot.LEGS).copy());
            drowned.equipStack(EquipmentSlot.FEET, PLAYER.getEquippedStack(EquipmentSlot.FEET).copy());
            drowned.equipStack(EquipmentSlot.MAINHAND, PLAYER.getEquippedStack(EquipmentSlot.MAINHAND).copy());
            drowned.equipStack(EquipmentSlot.OFFHAND, PLAYER.getEquippedStack(EquipmentSlot.OFFHAND).copy());
            drowned.setEquipmentDropChance(EquipmentSlot.HEAD, 0);
            drowned.setEquipmentDropChance(EquipmentSlot.CHEST, 0);
            drowned.setEquipmentDropChance(EquipmentSlot.LEGS, 0);
            drowned.setEquipmentDropChance(EquipmentSlot.FEET, 0);
            drowned.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
            drowned.setEquipmentDropChance(EquipmentSlot.OFFHAND, 0);
            PLAYER.getWorld().spawnEntity(drowned);
        }

        private boolean shelter() {
            List<EelEntity> eels = PLAYER.getWorld().getEntitiesByClass(EelEntity.class, new Box(PLAYER.getPos(), PLAYER.getPos()).expand(128), e -> true);
            if (!eels.isEmpty()) {
                PLAYER.setPosition(eels.get(0).getPos());
                return true;
            }
            return false;
        }

        private void chakras() {
            for (float i = 0f; i < 1f; i += 0.1F) DynamicProjectileEntity.create(AquamiraeEntities.POISONED_CHAKRA,
                    PLAYER, PLAYER.getWorld(), null, 20, i, 6000, 1);
        }

        private void moveUp() {
            length = 140;
            subEvent = () -> {
                if (tick == 80 || tick == 120) {
                    PLAYER.setVelocity(PLAYER.getVelocity().add(0, 1.5f, 0));
                    PLAYER.velocityModified = true;
                }
                if (tick == 100 || tick == 140) {
                    PLAYER.setVelocity(PLAYER.getVelocity().add(0, -1.0f, 0));
                    PLAYER.velocityModified = true;
                }
            };
        }

        private void moveSide() {
            length = 100;
            subEvent = () -> {
                if (tick == 80) {
                    PLAYER.setVelocity(PLAYER.getVelocity().add(0, 1.5f, 0));
                    PLAYER.velocityModified = true;
                }
                if (tick == 100) {
                    PLAYER.setVelocity(PLAYER.getVelocity().add(PLAYER.getPos().relativize(new Vec3d(
                            PLAYER.getX() + Math.cos(PLAYER.getYaw()) * 3f,
                            PLAYER.getY() + 0.5f,
                            PLAYER.getZ() + Math.sin(PLAYER.getYaw()) * 3f))));
                    PLAYER.velocityModified = true;
                }
            };
        }

        private void bones() {
            length = 280;
            subEvent = () -> {
                if (tick % 20 == 0) {
                    PLAYER.getWorld().playSound(null, PLAYER.getBlockPos(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS,
                            1f, 0.9f + 0.2f * PLAYER.getRandom().nextFloat());
                    PLAYER.getHungerManager().add(1, 0);
                    PLAYER.dropItem(AquamiraeItems.SHARP_BONES.getDefaultStack(), false);
                }
            };
        }
    }

    static class CorneliaSummonEvent extends Event {
        private final PlayerEntity PLAYER;
        private final BlockPos POS;
        private final boolean SUMMON;

        private CorneliaSummonEvent(PlayerEntity player, BlockPos pos, boolean summon) {
            super(60, CorneliaSummonEvent::onTick);
            this.PLAYER = player;
            this.POS = pos;
            this.SUMMON = summon;
        }

        private static void onTick(Event rawEvent) {
            final CorneliaSummonEvent event = (CorneliaSummonEvent) rawEvent;
            if (event.tick == event.length) {
                if (event.SUMMON) {
                    CaptainCorneliaEntity cornelia = new CaptainCorneliaEntity(AquamiraeEntities.CAPTAIN_CORNELIA, event.PLAYER.getWorld());
                    cornelia.updatePositionAndAngles(event.POS.getX()+0.5, event.POS.getY(), event.POS.getZ()+0.5, Random.create().nextFloat() * 360F, 0f);
                    cornelia.initialize((ServerWorldAccess) event.PLAYER.getWorld(), event.PLAYER.getWorld().getLocalDifficulty(event.POS), SpawnReason.MOB_SUMMONED, null, null);
                    event.PLAYER.getWorld().spawnEntity(cornelia);
                    event.PLAYER.sendMessage(TextUtils.component(Icons.BOSS + Text.translatable("info.captain_spawn").getString()));

                } else event.PLAYER.sendMessage(TextUtils.component(Icons.BOSS + Text.translatable("info.captain_spawn_fail").getString()));
            }
        }
    }

    static class Event {
        private final Consumer<Event> ACTION;
        protected int length;
        protected int tick;

        private Event(int length, Consumer<Event> action) {
            this.ACTION = action;
            this.length = length;
        }

        protected void tick() {
            ACTION.accept(this);
        }
    }
}
