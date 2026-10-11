package instadevelopment.kiriyaga.insta.impl.feature.combat;

import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.event.impl.Render3DEvent;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.util.entity.TargetUtils;
import instadevelopment.kiriyaga.insta.impl.feature.combat.component.TrapComponent;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static instadevelopment.kiriyaga.api.InstaApi.MC;
import static instadevelopment.kiriyaga.api.util.BlockUtils.isPlaceable;

@RegisterFeature
public class CrawlTrapFeature extends Feature {

    private final TrapComponent trap;

    public CrawlTrapFeature() {
        super("CrawlTrap", "Places a block above target head to hold them in crawling position.", FeatureCategory.of("Combat"), "crawltrap");
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
        Entity target = TargetUtils.getTarget();
        if (target == null || !(target instanceof Player) || !target.isVisuallyCrawling())
            return Collections.emptyList();

        BlockPos basePos = target.blockPosition();
        BlockPos blockAboveHead = basePos.above(1);

        if (!isPlaceable(blockAboveHead))
            return Collections.singletonList(blockAboveHead);

        return Collections.emptyList();
    }
}