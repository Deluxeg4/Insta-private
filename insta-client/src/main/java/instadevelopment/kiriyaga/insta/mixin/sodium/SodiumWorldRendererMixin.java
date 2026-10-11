package instadevelopment.kiriyaga.insta.mixin.sodium;

import instadevelopment.kiriyaga.insta.impl.feature.visuals.NoRenderFeature;
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
import net.caffeinemc.mods.sodium.client.util.FogParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static instadevelopment.kiriyaga.api.InstaApi.FEATURE_SERVICE;

@Mixin(SodiumWorldRenderer.class)
public class SodiumWorldRendererMixin {
    @Unique
    private static final FogParameters DISABLED_FOG = new FogParameters(0, 0, 0, 0, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);

    @ModifyVariable(method = "setupTerrain", at = @At("HEAD"), argsOnly = true)
    private FogParameters setupTerrain(FogParameters fogParameters) {
        if (FEATURE_SERVICE.getStorage() != null && FEATURE_SERVICE.getStorage().getByClass(NoRenderFeature.class) != null && FEATURE_SERVICE.getStorage().getByClass(NoRenderFeature.class).isEnabled() && FEATURE_SERVICE.getStorage().getByClass(NoRenderFeature.class).noFog.get()) return DISABLED_FOG;
        return fogParameters;
    }
}