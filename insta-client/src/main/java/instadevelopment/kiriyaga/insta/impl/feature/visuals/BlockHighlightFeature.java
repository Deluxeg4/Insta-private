package instadevelopment.kiriyaga.insta.impl.feature.visuals;

import instadevelopment.kiriyaga.api.annotation.SubscribeEvent;
import instadevelopment.kiriyaga.api.event.impl.Render3DEvent;
import instadevelopment.kiriyaga.api.model.feature.Feature;
import instadevelopment.kiriyaga.api.model.feature.FeatureCategory;
import instadevelopment.kiriyaga.api.annotation.RegisterFeature;
import instadevelopment.kiriyaga.insta.impl.feature.client.ColorFeature;
import instadevelopment.kiriyaga.api.model.setting.BoolSetting;
import instadevelopment.kiriyaga.api.util.render.RenderUtil;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import static instadevelopment.kiriyaga.api.InstaApi.MC;
import static instadevelopment.kiriyaga.api.InstaApi.FEATURE_SERVICE;

@RegisterFeature
public class BlockHighlightFeature extends Feature {

    public final BoolSetting fill = addSetting(new BoolSetting("Fill", true));

    public BlockHighlightFeature() {
        super("BlockHighlight", "Highlights block you look at.", FeatureCategory.of("Render"));
    }

    @SubscribeEvent
    public void onRender3dEvent(Render3DEvent event) {
        if (MC.hitResult != null && MC.hitResult instanceof BlockHitResult blockHitResult) {
            if (blockHitResult.getType() == HitResult.Type.MISS || blockHitResult.getType() == HitResult.Type.ENTITY)
                return;

            RenderUtil.drawBlockPosLines(MC.level, blockHitResult.getBlockPos(), MC.level.getBlockState(blockHitResult.getBlockPos()), FEATURE_SERVICE.getStorage().getByClass(ColorFeature.class).getStyledGlobalColor(), fill.get(), true, 1.5f);
        }
    }
}
