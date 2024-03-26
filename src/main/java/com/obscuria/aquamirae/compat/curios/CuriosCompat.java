package com.obscuria.aquamirae.compat.curios;

import com.obscuria.aquamirae.compat.curios.item.DeadSeaRingItem;
import com.obscuria.aquamirae.compat.curios.item.IceMazeRingItem;
import com.obscuria.aquamirae.compat.curios.item.ShipGraveyardRingItem;
import com.obscuria.aquamirae.compat.curios.item.ShoeSpikesItem;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import top.theillusivec4.curios.api.CuriosApi;

public final class CuriosCompat {

    public static void setup(IEventBus bus) {
        bus.addListener(CuriosCompat::register);
    }

    private static void register(FMLCommonSetupEvent event) {
        CuriosApi.registerCurio(AquamiraeItems.SHOE_SPIKES.get(), new ShoeSpikesItem.Curio());
        CuriosApi.registerCurio(AquamiraeItems.DEAD_SEA_RING.get(), new DeadSeaRingItem.Curio());
        CuriosApi.registerCurio(AquamiraeItems.SHIP_GRAVEYARD_RING.get(), new ShipGraveyardRingItem.Curio());
        CuriosApi.registerCurio(AquamiraeItems.ICE_MAZE_RING.get(), new IceMazeRingItem.Curio());
    }

    public interface LivingEntityExtension {
        static void shoeSpikesTick(Entity entity) {
            if (entity instanceof LivingEntityExtension extension)
                extension.aquamirae$shoeSpikesTick();
        }

        void aquamirae$shoeSpikesTick();
    }
}
