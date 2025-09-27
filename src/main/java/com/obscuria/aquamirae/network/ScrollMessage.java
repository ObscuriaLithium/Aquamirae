package com.obscuria.aquamirae.network;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ScrollMessage {
    int type;

    public ScrollMessage(int type) {
        this.type = type;
    }

    public ScrollMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
    }

    public static void buffer(ScrollMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
    }

    public static void handler(ScrollMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(AquamiraeClient::scrollSound);
        context.setPacketHandled(true);
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Aquamirae.addNetworkMessage(ScrollMessage.class, ScrollMessage::buffer, ScrollMessage::new, ScrollMessage::handler);
    }
}
