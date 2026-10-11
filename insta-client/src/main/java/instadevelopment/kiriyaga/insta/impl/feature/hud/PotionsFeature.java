package instadevelopment.kiriyaga.insta.impl.feature.hud;

import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.feature.HudElementFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.api.model.setting.EnumSetting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static instadevelopment.kiriyaga.api.InstaApi.*;
import static net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED;

@RegisterFeature
public class PotionsFeature extends HudElementFeature {

    public enum PotionColors {
        DEFAULT, OLD, THEME
    }

    public final EnumSetting<PotionColors> potionColors = addSetting(new EnumSetting<>("Color", PotionColors.DEFAULT));
    public final BoolSetting potionIcons = addSetting(new BoolSetting("Icons", false));

    private List<PotionLine> displayedLines = List.of();

    public PotionsFeature() {
        super("Potions", "Displays the active potion effects.", 80, 80, 100, 9);
    }

    @Override
    public List<TextElement> getTextElements() {
        if (MC.player == null) {
            displayedLines = List.of();
            width = 0;
            height = 0;
            return List.of();
        }

        List<PotionLine> lines = new ArrayList<>();
        for (MobEffectInstance instance : MC.player.getActiveEffects()) {
            Holder<MobEffect> effect = instance.getEffect();
            String text = effect.value().getDisplayName().getString();
            if (instance.getAmplifier() > 0) {
                text += " " + (instance.getAmplifier() + 1);
            }
            text += " " + getPotionDuration(instance);

            Component component = formatEffectText(text, effect);
            lines.add(new PotionLine(effect, new TextElement(component, 0, lines.size() * (FONT_SERVICE.getHeight() + 1)), text));
        }

        lines.sort(Comparator.comparing(PotionLine::sortText, String.CASE_INSENSITIVE_ORDER));
        int lineHeight = FONT_SERVICE.getHeight() + 1;
        int maxTextWidth = 0;
        List<PotionLine> positionedLines = new ArrayList<>(lines.size());
        for (int i = 0; i < lines.size(); i++) {
            PotionLine line = lines.get(i);
            maxTextWidth = Math.max(maxTextWidth, FONT_SERVICE.getWidth(line.element().text()));
            positionedLines.add(new PotionLine(line.effect(), new TextElement(line.element().text(), 0, i * lineHeight), line.sortText()));
        }

        displayedLines = positionedLines;
        width = maxTextWidth + (potionIcons.get() ? 13 : 0);
        height = lineHeight * positionedLines.size();
        return positionedLines.stream().map(PotionLine::element).toList();
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, width, height);
    }

    @Override
    public int getRenderXForElement(TextElement element) {
        if (!potionIcons.get()) {
            return super.getRenderXForElement(element);
        }

        int textWidth = FONT_SERVICE.getWidth(element.text());
        int iconSpace = 13;
        int baseX = getRenderX();
        return switch (alignment.get()) {
            case LEFT -> baseX + iconSpace;
            case CENTER -> baseX + (width - textWidth - iconSpace) / 2 + iconSpace;
            case RIGHT -> baseX + width - textWidth;
        };
    }

    @Override
    public void renderItems(GuiGraphics context) {
        if (!potionIcons.get()) return;

        int baseY = getRenderY();
        for (PotionLine line : displayedLines) {
            int textX = getRenderXForElement(line.element());
            int iconY = baseY + line.element().offsetY() + (FONT_SERVICE.getHeight() - 11) / 2;
            context.blitSprite(GUI_TEXTURED, MC.gui.getMobEffectSprite(line.effect()), textX - 13, iconY, 11, 11);
        }
    }

    private Component formatEffectText(String text, Holder<MobEffect> effect) {
        if (potionColors.get() == PotionColors.THEME) {
            return CAT_FORMAT.format("{global}" + text);
        }

        int color = potionColors.get() == PotionColors.OLD
                ? getOldPotionColor(effect)
                : effect.value().getColor();
        return Component.literal(text).withStyle(style -> style.withColor(TextColor.fromRgb(color)));
    }

    private int getOldPotionColor(Holder<MobEffect> effect) {
        Color base = new Color(effect.value().getColor());
        float[] hsb = Color.RGBtoHSB(base.getRed(), base.getGreen(), base.getBlue(), null);
        int rgb = Color.HSBtoRGB(hsb[0], hsb[1] * 0.78f, hsb[2] * 0.88f);
        return rgb & 0xFFFFFF;
    }

    private String getPotionDuration(MobEffectInstance instance) {
        int duration = instance.getDuration();
        if (duration < 0) return "*:*";

        int minutes = duration / 1200;
        int seconds = (duration % 1200) / 20;
        return minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }

    private record PotionLine(Holder<MobEffect> effect, TextElement element, String sortText) {}
}
