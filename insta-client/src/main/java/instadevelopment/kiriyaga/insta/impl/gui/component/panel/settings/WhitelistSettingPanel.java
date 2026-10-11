package instadevelopment.kiriyaga.insta.impl.gui.component.panel.settings;

import instadevelopment.kiriyaga.api.model.setting.WhitelistSetting;
import instadevelopment.kiriyaga.insta.impl.feature.client.ColorFeature;
import instadevelopment.kiriyaga.insta.impl.gui.base.BasePanel;
import instadevelopment.kiriyaga.insta.impl.gui.screen.WhitelistScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static instadevelopment.kiriyaga.api.InstaApi.*;
import static instadevelopment.kiriyaga.api.util.ColorUtils.toRGBA;

public class WhitelistSettingPanel extends BasePanel {

    public static final int HEIGHT = 13;
    private static final int PADDING = 3;

    private final WhitelistSetting setting;

    public WhitelistSettingPanel(WhitelistSetting setting) {
        this.setting = setting;
        this.height = HEIGHT;
    }

    @Override
    public void render(GuiGraphics context, Font font, int mouseX, int mouseY) {
        boolean hovered = isHovered(mouseX, mouseY);

        ColorFeature colorFeature = getColorFeature();
        Color textColor = isEnabled()
                ? colorFeature.getStyledTextColor(255)
                : colorFeature.getStyledTextSecondColor(255);

        int textY = y + (HEIGHT - 8) / 2 + 1;
        int textX = x + PADDING + (hovered ? 1 : 0);

        FONT_SERVICE.drawText(context, setting.getName(), textX, textY, toRGBA(textColor), true);
    }

    @Override
    public void onLeftClick() {
        setting.toggle();
    }

    @Override
    public void onRightClick() {
        List<String> allIds = new ArrayList<>();

        BuiltInRegistries.ITEM.keySet().forEach(id -> allIds.add(id.toString()));
        BuiltInRegistries.BLOCK.keySet().forEach(id -> allIds.add(id.toString()));
        BuiltInRegistries.ENTITY_TYPE.keySet().forEach(id -> allIds.add(id.toString()));
        BuiltInRegistries.SOUND_EVENT.keySet().forEach(id -> allIds.add(id.toString()));
        BuiltInRegistries.PARTICLE_TYPE.keySet().forEach(id -> allIds.add(id.toString()));

        MC.setScreen(new WhitelistScreen(setting, allIds));
    }

    @Override
    protected String getName() {
        return setting.getName();
    }

    @Override
    protected boolean isEnabled() {
        return setting.get();
    }

    @Override
    protected Color getTextColor() {
        ColorFeature colorFeature = getColorFeature();
        return isEnabled()
                ? colorFeature.getStyledTextColor(255)
                : colorFeature.getStyledTextSecondColor(255);
    }
}
