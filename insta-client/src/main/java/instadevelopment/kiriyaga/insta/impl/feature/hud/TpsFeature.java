package instadevelopment.kiriyaga.insta.impl.feature.hud;

import instadevelopment.kiriyaga.api.model.feature.HudElementFeature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import net.minecraft.network.chat.Component;

import java.util.Locale;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterFeature
public class TpsFeature extends HudElementFeature {

    public final BoolSetting displayLabel = addSetting(new BoolSetting("Label", true));
    public final BoolSetting avgTps = addSetting(new BoolSetting("AvgTPS", false));

    public TpsFeature() {
        super("TPS", "Displays average server TPS.", 0, 0, 50, 9);
    }

    @Override
    public Component getDisplayText() {
        float tps = SERVER_SERVICE.getLatestTPS();
        String tpsText = String.format(Locale.US, "%.2f", tps);
        if (avgTps.get()) {
            tpsText += String.format(Locale.US, " {secondary}[{white}%.2f{secondary}]", SERVER_SERVICE.getAverageTPS());
        }
        String text = displayLabel.get() ? "{global}TPS: {white}" + tpsText : "{white}" + tpsText;

        width = FONT_SERVICE.getWidth(text.replace("{global}", "").replace("{white}", "").replace("{secondary}", ""));
        height = FONT_SERVICE.getHeight();

        return CAT_FORMAT.format(text);
    }
}
