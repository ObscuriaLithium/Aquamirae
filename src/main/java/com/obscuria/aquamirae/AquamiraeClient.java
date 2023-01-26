package com.obscuria.aquamirae;

import com.obscuria.obscureapi.client.collection.ObscuriaCollection;
import com.obscuria.obscureapi.utils.ItemUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class AquamiraeClient {

    @Mod.EventBusSubscriber(modid = AquamiraeMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
    public static class ModEvents {

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerMod(FMLClientSetupEvent event) {
            ObscuriaCollection.register(AquamiraeMod.MODID);
        }
    }

    @Mod.EventBusSubscriber(value = {Dist.CLIENT})
    public static class ForgeEvents {

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void fixOldPerk(ItemTooltipEvent event) {
            if (ItemUtils.hasPerk(event.getItemStack(), "rune_of_the_storm")) {
                ItemUtils.removePerk(event.getItemStack(), "rune_of_the_storm");
                ItemUtils.addPerk(event.getItemStack(), new ResourceLocation("aquamirae", "rune_of_the_storm"), 1);
            }
        }
    }
}
