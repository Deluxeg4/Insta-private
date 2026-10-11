package instadevelopment.kiriyaga.insta.impl.gui.component.panel.settings;

import instadevelopment.kiriyaga.api.model.setting.DoubleSetting;
import instadevelopment.kiriyaga.insta.impl.feature.client.ColorFeature;
import instadevelopment.kiriyaga.insta.impl.gui.base.BasePanel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

import static instadevelopment.kiriyaga.api.InstaApi.FONT_SERVICE;
import static instadevelopment.kiriyaga.api.util.ColorUtils.toRGBA;

public class DoubleSettingPanel extends NumberSettingPanel<Double> {

    private final DoubleSetting setting;

    public DoubleSettingPanel(DoubleSetting setting) {
        this.setting = setting;
    }

    @Override
    protected String getSettingName() {
        return setting.getName();
    }

    @Override
    protected double getValue() {
        return setting.get();
    }

    @Override
    protected double getMin() {
        return setting.getMin();
    }

    @Override
    protected double getMax() {
        return setting.getMax();
    }

    @Override
    protected void setValueFromDouble(double value) {
        setting.set(value);
    }

    @Override
    protected String formatValue(double value) {
        return String.format("%.2f", value);
    }
}