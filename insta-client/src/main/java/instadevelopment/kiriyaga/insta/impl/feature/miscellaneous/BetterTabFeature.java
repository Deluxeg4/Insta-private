package instadevelopment.kiriyaga.insta.impl.feature.miscellaneous;

import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.api.model.setting.DoubleSetting;
import instadevelopment.kiriyaga.api.model.setting.IntSetting;

@RegisterFeature
public class BetterTabFeature extends Feature {

    public final IntSetting limit = addSetting(new IntSetting("Limit", 300, 25, 2500));
    public final DoubleSetting scale = addSetting(new DoubleSetting("Scale", 1.00, 0.50, 1.50));
    //public final IntSetting columns = addSetting(new IntSetting("columns", 4, 1, 20));
    //public final IntSetting rows = addSetting(new IntSetting("rows", 5, 1, 20));
    public final BoolSetting socialsOnly = addSetting(new BoolSetting("OnlyFriends", false));
    public final BoolSetting highlight = addSetting(new BoolSetting("Highlight", true));

    public BetterTabFeature() {
        super("BetterTab", "Extends tab limits and tweaks.", FeatureCategory.of("Miscellaneous"), "bettertab");
    }
}
