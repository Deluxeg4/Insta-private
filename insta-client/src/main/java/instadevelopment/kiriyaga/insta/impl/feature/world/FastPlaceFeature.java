package instadevelopment.kiriyaga.insta.impl.feature.world;

import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.IntSetting;
import instadevelopment.kiriyaga.api.model.setting.WhitelistSetting;

@RegisterFeature
public class FastPlaceFeature extends Feature {

    public final IntSetting delay = addSetting(new IntSetting("Delay", 1, 0, 5));
    public final IntSetting startDelay = addSetting(new IntSetting("StartDelay", 10, 0, 50));
    public final WhitelistSetting whitelist = addSetting(new WhitelistSetting("WhiteList", false, WhitelistSetting.Type.ANY));
    public final WhitelistSetting blacklist = addSetting(new WhitelistSetting("BlackList", false, WhitelistSetting.Type.ANY));

    public FastPlaceFeature() {
        super("FastPlace", "Decreases cooldown between any type of use.", FeatureCategory.of("World"), "fastplace");
    }
}
