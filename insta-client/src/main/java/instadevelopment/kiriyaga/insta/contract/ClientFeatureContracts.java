package instadevelopment.kiriyaga.insta.contract;

import instadevelopment.kiriyaga.api.contract.FeatureContractService;
import instadevelopment.kiriyaga.api.contract.feature.*;
import instadevelopment.kiriyaga.api.core.feature.FeatureStorage;
import instadevelopment.kiriyaga.insta.impl.feature.client.*;

public final class ClientFeatureContracts {

    private ClientFeatureContracts() {}

    public static void register(FeatureStorage storage) {
        TargetFeature target = storage.getByClass(TargetFeature.class);
        if (target != null) {
            FeatureContractService.register(TargetFeatureConfig.class, target);
        }

        FontFeature font = storage.getByClass(FontFeature.class);
        if (font != null) {
            FeatureContractService.register(FontFeatureConfig.class, font);
        }

        ColorFeature color = storage.getByClass(ColorFeature.class);
        if (color != null) {
            FeatureContractService.register(ColorFeatureConfig.class, color);
        }

        RotationsFeature rotations = storage.getByClass(RotationsFeature.class);
        if (rotations != null) {
            FeatureContractService.register(RotationsFeatureConfig.class, rotations);
        }

        // Latency
        LatencyFeature latency = storage.getByClass(LatencyFeature.class);
        if (latency != null) {
            FeatureContractService.register(
                    LatencyFeatureConfig.class,
                    latency
            );
        }
    }
}
