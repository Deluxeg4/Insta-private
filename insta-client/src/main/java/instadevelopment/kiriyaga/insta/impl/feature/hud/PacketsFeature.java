package instadevelopment.kiriyaga.insta.impl.feature.hud;

import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.event.impl.PacketReceiveEvent;
import instadevelopment.kiriyaga.api.event.impl.PacketSendEvent;
import instadevelopment.kiriyaga.api.model.feature.HudElementFeature;
import net.minecraft.network.protocol.Packet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterFeature
public class PacketsFeature extends HudElementFeature {

    private final List<PacketCount> packets = new ArrayList<>();

    public PacketsFeature() {
        super("Packets", "Displays incoming and outgoing packet counts.", 0, 0, 100, 10);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPacketReceive(PacketReceiveEvent event) {
        addPacket(event.getPacket(), true);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPacketSend(PacketSendEvent event) {
        addPacket(event.getPacket(), false);
    }

    private void addPacket(Packet<?> packet, boolean incoming) {
        if (packet == null) return;

        long now = System.currentTimeMillis();
        MC.execute(() -> {
            packets.add(new PacketCount(incoming, now));
            cleanup(now);
        });
    }

    private void cleanup(long now) {
        long cutoff = now - 1000L;
        Iterator<PacketCount> iterator = packets.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().time < cutoff) {
                iterator.remove();
            }
        }
    }

    @Override
    public net.minecraft.network.chat.Component getDisplayText() {
        cleanup(System.currentTimeMillis());

        int incoming = 0;
        int outgoing = 0;
        for (PacketCount packet : packets) {
            if (packet.incoming) incoming++;
            else outgoing++;
        }

        String text = "{global}Packet {white}" + incoming + " {secondary}({white}" + outgoing + "{secondary})";
        width = FONT_SERVICE.getWidth(text.replaceAll("\\{.*?}", ""));
        height = FONT_SERVICE.getHeight();
        return CAT_FORMAT.format(text);
    }

    private record PacketCount(boolean incoming, long time) {}
}
