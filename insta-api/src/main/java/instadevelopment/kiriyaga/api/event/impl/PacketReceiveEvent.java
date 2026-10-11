package instadevelopment.kiriyaga.api.event.impl;

import instadevelopment.kiriyaga.api.event.Event;
import net.minecraft.network.protocol.Packet;

public class PacketReceiveEvent extends Event {
    public Packet<?> packet;

    public PacketReceiveEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
