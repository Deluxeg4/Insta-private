package instadevelopment.kiriyaga.insta.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import instadevelopment.kiriyaga.insta.impl.feature.visuals.FreecamFeature;
import net.minecraft.client.renderer.debug.ChunkBorderRenderer;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static instadevelopment.kiriyaga.api.InstaApi.MC;
import static instadevelopment.kiriyaga.api.InstaApi.FEATURE_SERVICE;

@Mixin(ChunkBorderRenderer.class)
public abstract class MixinChunkBorderRenderer {

    @ModifyExpressionValue(method = "emitGizmos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;blockPosition()Lnet/minecraft/core/BlockPos;"))
    private BlockPos insta$modifyEntityPos(BlockPos originalPos) {
        FreecamFeature freecamFeature = FEATURE_SERVICE.getStorage().getByClass(FreecamFeature.class);

        if (freecamFeature == null || !freecamFeature.isEnabled()) {
            return originalPos;
        }

        float delta = MC.getDeltaTracker().getGameTimeDeltaPartialTick(true);

        double interpolatedX = freecamFeature.prevPos.x + (freecamFeature.pos.x - freecamFeature.prevPos.x) * delta;

        double interpolatedZ = freecamFeature.prevPos.z + (freecamFeature.pos.z - freecamFeature.prevPos.z) * delta;

        return new BlockPos((int) Math.floor(interpolatedX), originalPos.getY(), (int) Math.floor(interpolatedZ));
    }
}
