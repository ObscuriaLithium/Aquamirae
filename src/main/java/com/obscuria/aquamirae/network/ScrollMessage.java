package com.obscuria.aquamirae.network;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.events.ScrollClient;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ScrollMessage {
    int type;

    public ScrollMessage(int type) {
        this.type = type;
    }

    public ScrollMessage(PacketBuffer buffer) {
        this.type = buffer.readInt();
    }

    public static void buffer(ScrollMessage message, PacketBuffer buffer) {
        buffer.writeInt(message.type);
    }

    public static void handler(ScrollMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> ScrollClient.effect(message.type));
        context.setPacketHandled(true);
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        AquamiraeMod.addNetworkMessage(ScrollMessage.class, ScrollMessage::buffer, ScrollMessage::new, ScrollMessage::handler);
    }
}
