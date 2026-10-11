package instadevelopment.kiriyaga.insta.impl.feature.movement;

import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.DoubleSetting;

@RegisterFeature
public class HighJumpFeature extends Feature {

    public final DoubleSetting height = addSetting(new DoubleSetting("Height", 0.42, 0.00, 1.0));

    public HighJumpFeature() {
        super("HighJump", "Modifies jump strength.", FeatureCategory.of("Movement"), "highjump");
    }
}
