package instadevelopment.kiriyaga.insta.impl.feature.miscellaneous;

import instadevelopment.kiriyaga.api.event.EventPriority;
import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.OpenScreenEvent;
import instadevelopment.kiriyaga.api.event.impl.PreTickEvent;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.Component;

import static instadevelopment.kiriyaga.insta.Insta.*;
import static instadevelopment.kiriyaga.api.InstaApi.*;
@RegisterFeature
public class RespawnFeature extends Feature {

    public final BoolSetting sendCords = addSetting(new BoolSetting("LogCords", true)); //todo: this doesnt work
    public final BoolSetting autoRespawn = addSetting(new BoolSetting("AutoRespawn", false));

    public RespawnFeature() {
        super("Respawn", "Death screen tweaks.", FeatureCategory.of("Miscellaneous"), "autorespawn");
    }

    boolean b = false;

    @Override
    public void onEnable() {
        b = false;
    }
    @SubscribeEvent
    public void onTick(PreTickEvent ev) {
        if (MC.player == null || !MC.player.isDeadOrDying() || b)
            return;

        b = true;
        if (sendCords.get()) {
                String coords = String.format("X: %d Y: %d Z: %d", Math.round(MC.player.position().x), Math.round(MC.player.position().y), Math.round(MC.player.position().z));
                Component reason = CAT_FORMAT.format("Death coordinates: {global}" + coords+"{gray}.");
            }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onOpenScreen(OpenScreenEvent event) {
        if (!(event.getScreen() instanceof DeathScreen)) return;
        if (MC == null) return;

        if (autoRespawn.get()) {

            MC.player.respawn();
            event.cancel();
        }
        b = false;
    }
}
