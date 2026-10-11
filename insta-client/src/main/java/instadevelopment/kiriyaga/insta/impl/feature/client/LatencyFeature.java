package instadevelopment.kiriyaga.insta.impl.feature.client;

import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.contract.feature.LatencyFeatureConfig;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.setting.EnumSetting;
import instadevelopment.kiriyaga.api.model.setting.IntSetting;

@RegisterFeature
public class LatencyFeature extends Feature implements LatencyFeatureConfig {

    public final EnumSetting<Mode> mode = addSetting(new EnumSetting<>("Mode", Mode.NEW));
    public final IntSetting smoothingStrength = addSetting(new IntSetting("Smooth", 10, 1, 50));
    public final IntSetting unstableConnectionTimeout = addSetting(new IntSetting("Unstable", 3, 1, 60));
    public final IntSetting keepAliveInterval = addSetting(new IntSetting("Interval", 900, 250, 2500));

    public LatencyFeature() {
        super("Latency", "Defines how ping should be calculated.", FeatureCategory.of("Client"), "ping", "SERVICE", "managr", "png");
        if (!this.isEnabled())
            this.toggle();

        smoothingStrength.setShowCondition(() -> mode.get() == Mode.OLD);
        unstableConnectionTimeout.setShowCondition(() -> mode.get() != Mode.OFF);
        keepAliveInterval.setShowCondition(() -> mode.get() == Mode.OLD);
    }

    @Override
    public Mode getMode() {
        return mode.get();
    }

    @Override
    public int getSmoothingStrength() {
        return smoothingStrength.get();
    }

    @Override
    public int getUnstableTimeout() {
        return unstableConnectionTimeout.get();
    }

    @Override
    public int getKeepAliveInterval() {
        return keepAliveInterval.get();
    }

    @Override
    public void onDisable(){
        if (!this.isEnabled())
            this.toggle();
    }
}
