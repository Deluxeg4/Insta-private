package instadevelopment.kiriyaga.insta.impl.feature.visuals;

import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import net.minecraft.client.CameraType;

import static instadevelopment.kiriyaga.api.InstaApi.MC;

@RegisterFeature
public class NoWeatherFeature extends Feature {
    public NoWeatherFeature() {
        super("NoWeather", "Disables rendering of weather.", FeatureCategory.of("Render"), "noweather", "nowether", "nowather");
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    private void onUpdate (PreTickEvent ev){
        if (MC == null || MC.level == null || MC.player == null)
            return;
        this.clearDisplayInfo();

        String weather;

        if (MC.level.isRaining()) {
            if (MC.level.isThundering()) {
                weather = "thunder";
            } else {
                weather = "rain";
            }
        } else {
            weather = "clear";
        }

        this.addDisplayInfo(weather);
    }
}
