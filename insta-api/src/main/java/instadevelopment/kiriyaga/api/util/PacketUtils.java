package instadevelopment.kiriyaga.api.util;

import instadevelopment.kiriyaga.api.mixin.DuckClientLevel;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.multiplayer.prediction.PredictiveAction;

import static instadevelopment.kiriyaga.api.InstaApi.*;

public class PacketUtils {

    public static void sendSequencedPacket(PredictiveAction packetCreator) {
        if (MC.level == null || MC.getConnection() == null) {
            return;
        }

        BlockStatePredictionHandler p = ((DuckClientLevel) MC.level).getBlockStatePredictionHandler().startPredicting();

        try (p) {
            int sequence = p.currentSequence();
            MC.getConnection().send(packetCreator.predict(sequence));
        }
    }
}
