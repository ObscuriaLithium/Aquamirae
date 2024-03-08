package com.obscuria.aquamirae.network;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record ScrollEffectPacket(int type) {

    public static void encode(ScrollEffectPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.type);
    }

    public static ScrollEffectPacket decode(FriendlyByteBuf buffer) {
        return new ScrollEffectPacket(buffer.readInt());
    }

    public static void consume(ScrollEffectPacket packet, CustomPayloadEvent.Context context) {
        context.enqueueWork(AquamiraeClient::playScrollSound);
        context.setPacketHandled(true);
    }

    @SuppressWarnings("unused")
    public static void register(FMLCommonSetupEvent event) {
        Aquamirae.CHANNEL.messageBuilder(ScrollEffectPacket.class)
                .encoder(ScrollEffectPacket::encode)
                .decoder(ScrollEffectPacket::decode)
                .consumerMainThread(ScrollEffectPacket::consume)
                .add();
    }
}
