package instadevelopment.kiriyaga.insta.impl.feature.visuals;

import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.event.impl.WorldTimeUpdateEvent;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.IntSetting;

import static instadevelopment.kiriyaga.api.InstaApi.MC;

@RegisterFeature
public class ClientTimeFeature extends Feature {
    public final IntSetting value = addSetting(new IntSetting("Time", 25000, 0, 25000));

    public ClientTimeFeature() {
        super("ClientTime", "Sets game time client side.", FeatureCategory.of("Render"));
    }

    @SubscribeEvent
    private void onPacketReceiveEvent(WorldTimeUpdateEvent event) {
        event.cancel();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    private void onPreTickEvent(PreTickEvent event) {
        if (MC.level == null || MC.player == null)
            return;

        MC.level.getLevelData().setDayTime((long)value.get());
    }
}