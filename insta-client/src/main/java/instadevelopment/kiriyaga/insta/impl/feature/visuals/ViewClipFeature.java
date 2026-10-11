package instadevelopment.kiriyaga.insta.impl.feature.visuals;

import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.Render2DEvent;
import instadevelopment.kiriyaga.api.event.impl.VisGraphEvent;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.api.model.setting.DoubleSetting;
import net.minecraft.client.CameraType;
import net.minecraft.client.renderer.chunk.VisGraph;

import static instadevelopment.kiriyaga.api.InstaApi.MC;

@RegisterFeature
public class ViewClipFeature extends Feature {

    public final DoubleSetting distance = addSetting(new DoubleSetting("Distance", 3.5, 1, 9));
    public final BoolSetting animate = addSetting(new BoolSetting("Animation", true));
    public final BoolSetting visGraph = addSetting(new BoolSetting("NoCull", true));

    private float currentDistance = 3.5f;

    public ViewClipFeature() {
        super("ViewClip", "Disables block clipping and extends camera distance.", FeatureCategory.of("Render"), "viewclip");
    }

    @SubscribeEvent
    public void onRender2D(Render2DEvent ev) {
        CameraType perspective = MC.options.getCameraType();

        if (perspective == CameraType.FIRST_PERSON) {
            currentDistance = 1f;
        } else {
            if (animate.get()) {
                currentDistance += (float) (distance.get() - currentDistance) * 0.12f;
            } else {
                currentDistance = distance.get().floatValue();
            }
        }
    }

    @SubscribeEvent
    public void onVisGraph(VisGraphEvent ev) {
        if (visGraph.get()) {
            ev.cancel();
        }
    }

    public float getAnimatedDistance() {
        return currentDistance;
    }
}