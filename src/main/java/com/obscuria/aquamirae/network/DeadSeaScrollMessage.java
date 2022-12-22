package com.obscuria.aquamirae.network;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.events.ScrollClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DeadSeaScrollMessage {
    int type;

    public DeadSeaScrollMessage(int type) {
        this.type = type;
    }

    public DeadSeaScrollMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
    }

    public static void buffer(DeadSeaScrollMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
    }

    public static void handler(DeadSeaScrollMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> ScrollClient.effect(message.type));
        context.setPacketHandled(true);
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        AquamiraeMod.addNetworkMessage(DeadSeaScrollMessage.class, DeadSeaScrollMessage::buffer, DeadSeaScrollMessage::new, DeadSeaScrollMessage::handler);
    }
}
