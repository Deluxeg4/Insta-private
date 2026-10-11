package instadevelopment.kiriyaga.api.core.font;

import instadevelopment.kiriyaga.api.contract.FeatureContractService;
import instadevelopment.kiriyaga.api.contract.feature.FontFeatureConfig;

import static instadevelopment.kiriyaga.api.InstaApi.*;

public class FontMetrics {

    private final FontRendererProvider rendererProvider;

    public FontMetrics(FontRendererProvider rendererProvider) {
        this.rendererProvider = rendererProvider;
    }

    public int getHeight() {
        FontFeatureConfig fontFeature = FeatureContractService.get(FontFeatureConfig.class);
        if (!fontFeature.isEnabled())
            return rendererProvider.getRenderer().lineHeight;
        return Math.round(fontFeature.getGlyphSize() * 0.85f);
    }
}
