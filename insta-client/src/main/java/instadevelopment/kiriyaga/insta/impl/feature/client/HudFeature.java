package instadevelopment.kiriyaga.insta.impl.feature.client;

import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.event.impl.Render2DEvent;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.HudElementFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.api.model.setting.ColorSetting;
import instadevelopment.kiriyaga.api.model.setting.IntSetting;
import instadevelopment.kiriyaga.api.util.ChatAnimationHelper;
import net.minecraft.client.gui.screens.ChatScreen;

import java.awt.*;
import java.util.ArrayList;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterFeature
public class HudFeature extends Feature {

    public final BoolSetting chatAnimation = addSetting(new BoolSetting("ChatAnimation", true));

    public HudFeature() {
        super("HUD", "Renders in-game hud.", FeatureCategory.of("Client"));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRender2D(Render2DEvent event) {
        boolean chatOpen = MC.screen instanceof ChatScreen;
        ChatAnimationHelper.setChatOpen(chatOpen);
        ChatAnimationHelper.tick();

        if (chatAnimation.get()) {
            int offset = (int) ChatAnimationHelper.getAnimationOffset();
            if (offset > 0) {
                event.getDrawContext().fill(
                        2,
                        MC.getWindow().getGuiScaledHeight() - offset,
                        MC.getWindow().getGuiScaledWidth() - 2,
                        MC.getWindow().getGuiScaledHeight() - 2,
                        MC.options.getBackgroundColor(Integer.MIN_VALUE)
                );
            }
        }

        int screenHeight = MC.getWindow().getGuiScaledHeight();
        int chatZoneTop = screenHeight - (screenHeight / 8);
        int chatAnimationOffset = (int) ChatAnimationHelper.getAnimationOffset();

        if (MC.level == null || MC.getDebugOverlay().showDebugScreen() || MC.options.hideGui)
            return;

        for (Feature Feature : FEATURE_SERVICE.getStorage().getAll()) {
            if (Feature instanceof HudElementFeature hudElement && hudElement.isEnabled()) {
                int baseY = hudElement.getRenderY();
                hudElement.pushAutoScale(event.getDrawContext());

                for (HudElementFeature.TextElement element : new ArrayList<>(hudElement.getTextElements())) { // its better be outdated then concurrent, btw maybe some atomic impl?
                    int drawX = hudElement.getRenderXForElement(element);
                    int drawY = baseY + element.offsetY();

                    boolean isInChatZone = (drawY + MC.font.lineHeight) >= chatZoneTop;
                    if (isInChatZone) {
                        drawY -= chatAnimationOffset;
                    }

//                    event.getDrawContext().drawText(
//                            MC.textRenderer,
//                            element.text(),
//                            drawX,
//                            drawY,
//                            0xFFFFFFFF,
//                            shadow.get()
//                    );

                    FONT_SERVICE.drawText(event.getDrawContext(), element.text(), drawX, drawY, true);
                }

                hudElement.renderItems(event.getDrawContext());
                hudElement.popAutoScale(event.getDrawContext());
            }
        }
    }
}
