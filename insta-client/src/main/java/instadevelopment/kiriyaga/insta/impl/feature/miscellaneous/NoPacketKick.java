package instadevelopment.kiriyaga.insta.impl.feature.miscellaneous;


import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;

@RegisterFeature
public class NoPacketKick extends Feature {

    public NoPacketKick() {
        super("NoPacketKick", "Prevents from kicking because of netty exceptions.", FeatureCategory.of("Miscellaneous"), "npacketkick", "antipacketkick");
    }
}
