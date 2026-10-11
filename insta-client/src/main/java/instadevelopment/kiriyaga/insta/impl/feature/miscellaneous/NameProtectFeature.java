package instadevelopment.kiriyaga.insta.impl.feature.miscellaneous;

import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;

@RegisterFeature
public class NameProtectFeature extends Feature {

    public NameProtectFeature() {
        super("NameProtect", "Changes client name on all client side accessible sides.", FeatureCategory.of("Miscellaneous"), "nameprotect");
    }
}
