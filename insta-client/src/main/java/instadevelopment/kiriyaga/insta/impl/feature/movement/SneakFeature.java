package instadevelopment.kiriyaga.insta.impl.feature.movement;

import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.LedgeClipEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.insta.impl.feature.movement.component.SafeWalkComponent;
import instadevelopment.kiriyaga.insta.mixin.DuckKeyMapping;
import instadevelopment.kiriyaga.api.model.setting.EnumSetting;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static instadevelopment.kiriyaga.api.InstaApi.MC;

@RegisterFeature
public class SneakFeature extends Feature {

    private final SafeWalkComponent safeWalk = new SafeWalkComponent();

    public SneakFeature() {
        super("Sneak", "Automatically makes you sneak.", FeatureCategory.of("Movement"));
        safeWalk.register(this);
    }

    @Override
    public void onDisable() {
        safeWalk.onDisable();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPreTickEvent(PreTickEvent event) {
        safeWalk.onTick();

        clearDisplayInfo();
        addDisplayInfo(safeWalk.mode.get().toString());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLedgeClip(LedgeClipEvent event) {
        safeWalk.onLedgeClip(event);
    }
}
