package instadevelopment.kiriyaga.insta.impl.feature.movement;

import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;

@RegisterFeature
public class NoLevitationFeature extends Feature {

    public final BoolSetting noSlowFall = addSetting(new BoolSetting("NoSlowFall", false));

    public NoLevitationFeature() {
        super("NoLevitation", "Removes levitation status effect.", FeatureCategory.of("Movement"), "antilevitation");
    }
}
