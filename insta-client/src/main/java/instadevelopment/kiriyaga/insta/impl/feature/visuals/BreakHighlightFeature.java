package instadevelopment.kiriyaga.insta.impl.feature.visuals;

import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.core.breakprediction.PlayerBreakState;
import instadevelopment.kiriyaga.api.event.impl.Render3DEvent;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;

import static instadevelopment.kiriyaga.api.InstaApi.BREAKPREDICT_SERVICE;
import static instadevelopment.kiriyaga.api.InstaApi.MC;

@RegisterFeature
public class BreakHighlightFeature extends Feature {

    public final BoolSetting fill = addSetting(new BoolSetting("Fill", true));

    public BreakHighlightFeature() {
        super("BreakHighlight", "Highlights block being broken.", FeatureCategory.of("Render"));
    }

    @SubscribeEvent
    public void onRender3DEvent(Render3DEvent event) {
        if (MC.level == null || MC.player == null || BREAKPREDICT_SERVICE == null)
            return;

        for (PlayerBreakState state : BREAKPREDICT_SERVICE.all()) {
            state.render(event, fill.get());
        }
    }
}
