package instadevelopment.kiriyaga.insta.impl.feature.movement;

import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.api.model.setting.KeyBindSetting;

import static instadevelopment.kiriyaga.insta.Insta.*;
import static instadevelopment.kiriyaga.api.InstaApi.*;
@RegisterFeature
public class AirJumpFeature extends Feature {

    public final KeyBindSetting useKey = addSetting(new KeyBindSetting("Use", KeyBindSetting.KEY_NONE));
    public final BoolSetting setOnGround = addSetting(new BoolSetting("SetOnGround", true));

    public AirJumpFeature() {
        super("AirJump", "Allows jumping in air when pressing key.", FeatureCategory.of("Movement"), "airjump");
    }

    @Override
    public void onEnable() {
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    private void onTick(PreTickEvent ev) {
        if (MC.level == null || MC.player == null) return;

        boolean pressed = KEYBIND_SERVICE.isPressedToggle(useKey);

        if (pressed) {
            performAirJump();
        }
    }

    private void performAirJump() {
        if (setOnGround.get())
            MC.player.setOnGround(true);

        MC.player.jumpFromGround();
    }
}
