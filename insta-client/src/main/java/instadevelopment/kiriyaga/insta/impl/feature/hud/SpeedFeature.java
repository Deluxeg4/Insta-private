package instadevelopment.kiriyaga.insta.impl.feature.hud;

import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.feature.HudElementFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.api.model.setting.EnumSetting;
import instadevelopment.kiriyaga.insta.impl.feature.exploits.TimerFeature;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterFeature
public class SpeedFeature extends HudElementFeature {

    public enum SpeedMode {
        KMH, BPS
    }

    public final BoolSetting displayLabel = addSetting(new BoolSetting("Label", true));
    public final EnumSetting<SpeedMode> mode = addSetting(new EnumSetting<>("Mode", SpeedMode.KMH));

    private final DecimalFormat speedFormat = new DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.ROOT));

    public SpeedFeature() {
        super("Speed", "Displays current player speed.", 0, 0, 50, 9);
    }

    @Override
    public Component getDisplayText() {
        if (MC.player == null) {
            width = FONT_SERVICE.getWidth("Speed 0km/h");
            height = FONT_SERVICE.getHeight();
            return CAT_FORMAT.format(displayLabel.get() ? "{global}Speed {white}0km/h" : "{white}0km/h");
        }

        double dx = MC.player.getX() - MC.player.xo;
        double dz = MC.player.getZ() - MC.player.zo;
        double distance = Math.sqrt(dx * dx + dz * dz);
        double speed;
        String unit;

        if (mode.get() == SpeedMode.KMH) {
            speed = distance * 72.0;
            unit = "km/h";
        } else {
            speed = distance * 20.0;
            unit = "b/s";
        }

        speed *= getTimerTicks();
        String value = speedFormat.format(speed) + unit;
        String text = displayLabel.get() ? "Speed " + value : value;
        width = FONT_SERVICE.getWidth(text);
        height = FONT_SERVICE.getHeight();

        return CAT_FORMAT.format(displayLabel.get() ? "{global}Speed {white}" + value : "{white}" + value);
    }

    private float getTimerTicks() {
        TimerFeature timerFeature = FEATURE_SERVICE.getStorage().getByClass(TimerFeature.class);
        if (timerFeature == null || !timerFeature.isEnabled()) return 1.0f;
        return timerFeature.getTimerTicks();
    }
}
