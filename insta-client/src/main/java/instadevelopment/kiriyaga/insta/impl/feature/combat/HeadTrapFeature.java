package instadevelopment.kiriyaga.insta.impl.feature.combat;

import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.event.impl.Render3DEvent;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.insta.impl.feature.combat.component.TrapComponent;

import net.minecraft.core.BlockPos;

import java.util.Collections;
import java.util.List;

import static instadevelopment.kiriyaga.api.InstaApi.MC;
import static instadevelopment.kiriyaga.api.util.BlockUtils.isPlaceable;

@RegisterFeature
public class HeadTrapFeature extends Feature {

    public final BoolSetting crawl = addSetting(new BoolSetting("Crawl", true));

    private final TrapComponent trap;

    public HeadTrapFeature() {
        super("HeadTrap", "Places a block above your head.", FeatureCategory.of("Combat"), "headtrap");
        this.trap = new TrapComponent(this);
    }

    @Override
    public void onDisable() {
        trap.onDisable();
    }

    @SubscribeEvent
    public void onTick(PreTickEvent event) {
        if (MC.player == null || MC.level == null) return;
        this.clearDisplayInfo();
        List<BlockPos> targets = getTrapTargets();
        this.addDisplayInfo(targets.size() + "");
        if (targets.isEmpty()) return;
        trap.onTick(event, this, targets);
    }

    @SubscribeEvent
    public void onRender(Render3DEvent event) {
        trap.onRender(event);
    }

    private List<BlockPos> getTrapTargets() {
        if (MC.player.isVisuallyCrawling() && !crawl.get()) {
            return Collections.emptyList();
        }
        BlockPos pos = MC.player.blockPosition();
        BlockPos b = pos.above(2);

        if (MC.player.isVisuallyCrawling()) {
            b = pos.above(1);
        }

        if (!isPlaceable(b))
            return Collections.singletonList(b);

        return Collections.emptyList();
    }
}
