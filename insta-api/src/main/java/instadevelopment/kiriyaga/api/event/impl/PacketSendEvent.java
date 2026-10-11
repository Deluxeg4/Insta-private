package instadevelopment.kiriyaga.api.event.impl;

import instadevelopment.kiriyaga.api.event.Event;
import net.minecraft.network.protocol.Packet;

public class PacketSendEvent extends Event {
    private final Packet<?> packet;

    public PacketSendEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
