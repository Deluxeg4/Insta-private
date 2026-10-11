package instadevelopment.kiriyaga.insta.impl.feature.hud;

import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.feature.HudElementFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import net.minecraft.network.chat.Component;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterFeature
public class ServerBrandFeature extends HudElementFeature {

    public final BoolSetting displayLabel = addSetting(new BoolSetting("Label", true));

    public ServerBrandFeature() {
        super("ServerBrand", "Displays the current server brand.", 0, 0, 100, 9);
    }

    @Override
    public Component getDisplayText() {
        String brand = SERVER_SERVICE.getServerBrand();
        String text = displayLabel.get()
                ? "{global}ServerBrand: {white}" + brand
                : "{white}" + brand;

        width = FONT_SERVICE.getWidth(text.replace("{global}", "").replace("{white}", ""));
        height = FONT_SERVICE.getHeight();

        return CAT_FORMAT.format(text);
    }
}
