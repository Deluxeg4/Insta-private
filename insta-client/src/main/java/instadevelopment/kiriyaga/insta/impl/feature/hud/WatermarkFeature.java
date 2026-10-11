package instadevelopment.kiriyaga.insta.impl.feature.hud;

import instadevelopment.kiriyaga.api.model.feature.HudElementFeature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import net.minecraft.network.chat.Component;

import static instadevelopment.kiriyaga.insta.Insta.*;
import static instadevelopment.kiriyaga.api.InstaApi.*;
@RegisterFeature
public class WatermarkFeature extends HudElementFeature {
    public final BoolSetting plain = addSetting(new BoolSetting("Plain", false));

    public WatermarkFeature() {
        super("Watermark", "Displays client watermark.", 0, 0, 50, 9);
    }

    @Override
    public Component getDisplayText() {
        String v = "v"+VERSION;
        String watermarkStr = DISPLAY_NAME + " " + v;
        if (watermarkStr.isEmpty()) {
            return CAT_FORMAT.format("{global}NaN");
        }

        width = FONT_SERVICE.getWidth(watermarkStr);
        height = FONT_SERVICE.getHeight();

        return plain.get() ? CAT_FORMAT.format("{global}" + DISPLAY_NAME + v) : CAT_FORMAT.format("{global}" + DISPLAY_NAME + " {white}"+ v);
    }
}
