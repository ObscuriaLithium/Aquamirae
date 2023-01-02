package com.obscuria.aquamirae;

import com.obscuria.obscureapi.client.collection.ObscuriaCollection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class AquamiraeClient {

    @Mod.EventBusSubscriber(modid = AquamiraeMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
    public static class ModEvents {

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void clientSetupHighest(FMLClientSetupEvent event) {
            ObscuriaCollection.register(AquamiraeMod.MODID);
        }
    }
}
