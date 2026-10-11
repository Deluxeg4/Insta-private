package instadevelopment.kiriyaga.insta.mixin;

import instadevelopment.kiriyaga.insta.impl.feature.miscellaneous.UnfocusedFpsFeature;
import com.mojang.blaze3d.platform.FramerateLimitTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static instadevelopment.kiriyaga.api.InstaApi.MC;
import static instadevelopment.kiriyaga.api.InstaApi.FEATURE_SERVICE;

@Mixin(FramerateLimitTracker.class)
public class MixinFramerateLimitTracker {
    @Inject(method = "getFramerateLimit", at = @At("HEAD"), cancellable = true)
    private void updateHead(CallbackInfoReturnable<Integer> info) {
        if (FEATURE_SERVICE.getStorage() == null) return;

        UnfocusedFpsFeature Feature = FEATURE_SERVICE.getStorage().getByClass(UnfocusedFpsFeature.class);
        if (Feature == null) return;

        if (Feature.isEnabled() && (MC == null || !MC.isWindowActive())) {
            info.setReturnValue(Feature.limit.get());
        }
    }
}