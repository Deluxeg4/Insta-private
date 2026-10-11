package instadevelopment.kiriyaga.insta.impl.feature.world;

import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;

@RegisterFeature
public class NoBreakDelayFeature extends Feature {

    public NoBreakDelayFeature() {
        super("NoBreakDelay", "Removes vanilla break delay which increases break speed.", FeatureCategory.of("World"), "nobreakdelay");
    }
}
