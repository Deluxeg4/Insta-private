package instadevelopment.kiriyaga.insta.impl.feature.movement;

import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.DoubleSetting;
import net.minecraft.world.phys.Vec3;

import static instadevelopment.kiriyaga.api.InstaApi.INPUT_SERVICE;
import static instadevelopment.kiriyaga.api.InstaApi.MC;

@RegisterFeature
public class FastFallFeature extends Feature {

    public final DoubleSetting speed = addSetting(new DoubleSetting("Speed", 1.00, 0.10, 5.00));

    public FastFallFeature() {
        super("FastFall", "Fall from blocks faster.", FeatureCategory.of("Movement"), "fastfall");
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    private void onTick(PreTickEvent ev) {
        if (MC.level == null || MC.player == null) return;

        if (!INPUT_SERVICE.getInputCache().hasAnyInput() || INPUT_SERVICE.getInputCache().jump() || MC.player.isInPowderSnow || MC.player.isUnderWater() || MC.player.isInLava() || MC.player.isInWater() || MC.player.isFallFlying() || MC.player.isNoGravity())
            return;

        if (MC.player.onGround()) {
            Vec3 prev = MC.player.getDeltaMovement();
            MC.player.setDeltaMovement(prev.x, -speed.get(), prev.z);
        }
    }
}
