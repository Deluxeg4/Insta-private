package instadevelopment.kiriyaga.insta.impl.feature.movement;

import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.IntSetting;

import static instadevelopment.kiriyaga.api.InstaApi.MC;
import static instadevelopment.kiriyaga.api.util.RotationUtils.alignYRot;

@RegisterFeature
public class YawFeature extends Feature {

    public final IntSetting directions = addSetting(new IntSetting("Directions", 8, 4, 16));

    public YawFeature() {
        super("Yaw", "Snap player yaw to nearest fixed angle.", FeatureCategory.of("Movement"));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPreTick(PreTickEvent event) {
        if (MC.player == null || MC.level == null) return;

        float s = 360f / directions.get();
        float targetYaw = Math.round(MC.player.getYRot() / s) * s;

        targetYaw = alignYRot(targetYaw, MC.player.getYRot());
        MC.player.setYRot(targetYaw);
    }

}