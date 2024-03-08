package com.obscuria.aquamirae.network;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record StackCursedPacket(ItemStack stack) {

    public static void encode(StackCursedPacket packet, FriendlyByteBuf buffer) {
        buffer.writeItemStack(packet.stack, false);
    }

    public static StackCursedPacket decode(FriendlyByteBuf buffer) {
        return new StackCursedPacket(buffer.readItem());
    }

    public static void consume(StackCursedPacket packet, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> AquamiraeClient.showCurseToast(packet.stack));
        context.setPacketHandled(true);
    }

    @SuppressWarnings("unused")
    public static void register(FMLCommonSetupEvent event) {
        Aquamirae.CHANNEL.messageBuilder(StackCursedPacket.class)
                .encoder(StackCursedPacket::encode)
                .decoder(StackCursedPacket::decode)
                .consumerMainThread(StackCursedPacket::consume)
                .add();
    }
}
