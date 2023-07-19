package com.obscuria.aquamirae.network;

import com.obscuria.aquamirae.Aquamirae;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record ScrollUsePacket(int entity) implements FabricPacket {
    public static final PacketType<ScrollUsePacket> TYPE = PacketType.create(Aquamirae.key("animation_play"), ScrollUsePacket::new);

    public ScrollUsePacket(PacketByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(entity);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
