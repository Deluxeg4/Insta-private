package instadevelopment.kiriyaga.insta.impl.feature.movement;

import instadevelopment.kiriyaga.api.core.rotation.model.RotationRequest;
import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.insta.impl.feature.client.RotationsFeature;
import instadevelopment.kiriyaga.api.model.setting.DoubleSetting;

import static instadevelopment.kiriyaga.api.InstaApi.MC;
import static instadevelopment.kiriyaga.api.InstaApi.ROTATION_SERVICE;

@RegisterFeature
public class AntiAimFeature extends Feature {

    public final DoubleSetting rotationSpeed = addSetting(new DoubleSetting("Speed", 5.0, 0.1, 50.0));
    public final DoubleSetting pitchSetting = addSetting(new DoubleSetting("Pitch", 0.0, -90.0, 90.0));

    private float currentYaw = 0.0f;

    public AntiAimFeature() {
        super("AntiAim", "Make you, spin!.", FeatureCategory.of("Movement"), "antiaim");
    }

    @Override
    public void onEnable() {
        currentYaw = MC.player != null ? MC.player.getYRot() : 0.0f;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPreTick(PreTickEvent event) {
        if (MC.player == null) return;

        currentYaw += rotationSpeed.get().floatValue();
        if (currentYaw > 360.0f) {
            currentYaw -= 360.0f;
        }

        float yaw = currentYaw;
        float pitch = pitchSetting.get().floatValue();

        ROTATION_SERVICE.getRequestHandler().submit(new RotationRequest(
                AntiAimFeature.class.getName(),
                0,
                yaw,
                pitch,
                RotationsFeature.RotationMode.MOTION
        ));
    }
}
