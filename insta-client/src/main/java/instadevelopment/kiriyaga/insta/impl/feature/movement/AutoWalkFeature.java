package instadevelopment.kiriyaga.insta.impl.feature.movement;

import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.insta.mixin.DuckKeyMapping;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterFeature
public class AutoWalkFeature extends Feature {

    public final BoolSetting setbackStop = addSetting(new BoolSetting("SetbackStop", true));

    public AutoWalkFeature() {
        super("AutoWalk", "Automatically makes you walk.", FeatureCategory.of("Movement"),"autowalk");
    }

    @Override
    public void onDisable() {
        if (MC.player == null || MC.level == null)
            return;

        INPUT_SERVICE.getClientHandler().clearOverride(this.name);
        INPUT_SERVICE.getInputCache().setForward(false);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPreTick(PreTickEvent event) {
        if (MC.player == null || MC.level == null)
            return;

        if (setbackStop.get() && !SERVER_SERVICE.hasElapsedSinceSetback(5000))
            return;

        INPUT_SERVICE.getClientHandler().overrideMovement(this.name, true, false, false, false);
        INPUT_SERVICE.getInputCache().setForward(true);
    }
}
