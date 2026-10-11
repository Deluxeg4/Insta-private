package instadevelopment.kiriyaga.insta.impl.feature.hud;

import instadevelopment.kiriyaga.api.model.feature.HudElementFeature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import net.minecraft.network.chat.Component;

import java.util.Locale;

import static instadevelopment.kiriyaga.insta.Insta.*;
import static instadevelopment.kiriyaga.api.InstaApi.*;import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterFeature
public class DirectionFeature extends HudElementFeature {

    public final BoolSetting displayLabel = addSetting(new BoolSetting("Label", true));
    public final BoolSetting precise = addSetting(new BoolSetting("Precise", false));

    public DirectionFeature() {
        super("Direction", "Displays accurate yaw/pitch.", 0, 0, 50, 9);
    }

    @Override
    public Component getDisplayText() {
        String dirText;
        float yaw = 0;
        float pitch = 0;
        if (MC.getCameraEntity() != null) {
            yaw = MC.getCameraEntity().getYRot();
            pitch = MC.getCameraEntity().getXRot();
        }

        if (precise.get())
        dirText = String.format(Locale.US, "%.3f {secondary}[{white}%.3f{secondary}]", yaw, pitch); // locale is wild
        else
            dirText = String.format(Locale.US, "%.0f {secondary}[{white}%.0f{secondary}]", yaw, pitch); // locale is wild

        String text = displayLabel.get() ? "{global}Direction: {white}" + dirText : "{white}" + dirText;


        width = FONT_SERVICE.getWidth(text.replace("{global}", "").replace("{white}", "").replace("{secondary}", ""));
        height = FONT_SERVICE.getHeight();

        return CAT_FORMAT.format(text);
    }
}