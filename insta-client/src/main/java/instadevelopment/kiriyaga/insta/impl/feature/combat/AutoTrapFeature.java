package instadevelopment.kiriyaga.insta.impl.feature.combat;

import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.event.impl.Render3DEvent;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.api.util.entity.TargetUtils;
import instadevelopment.kiriyaga.insta.impl.feature.combat.component.TrapComponent;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import static instadevelopment.kiriyaga.api.InstaApi.MC;
import static instadevelopment.kiriyaga.api.util.BlockUtils.getSurround;

@RegisterFeature
public class AutoTrapFeature extends Feature {

    public final BoolSetting face = addSetting(new BoolSetting("Face", true));
    public final BoolSetting selfToggle = addSetting(new BoolSetting("SelfToggle", false));

    private final TrapComponent trap;

    public AutoTrapFeature() {
        super("AutoTrap", "Traps your target to prevent their movement.", FeatureCategory.of("Combat"), "autotrap");
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

        Entity target = TargetUtils.getTarget();
        if (!(target instanceof Player player)) {
            trap.getTargetPositions().clear();
            return;
        }
        List<BlockPos> targets = getTrapTargets(player);
        this.addDisplayInfo(targets.size() + "");
        if (targets.isEmpty() && selfToggle.get()) {
            this.toggle();
            return;
        }

        trap.onTick(event, this, targets);
    }

    @SubscribeEvent
    public void onRender(Render3DEvent event) {
        trap.onRender(event);
    }

    private List<BlockPos> getTrapTargets(Player target) {
        return getSurround(target, face.get() ? 1 : 0, false);
    }
}
