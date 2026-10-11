package instadevelopment.kiriyaga.api.contract.feature;

import instadevelopment.kiriyaga.api.core.font.FontType;

public interface FontFeatureConfig {

    FontType getFontType();

    int getGlyphSize();
    int getOversample();

    float getShiftX();
    float getShiftY();
    FontAntialiasMode getAntialiasMode();
    boolean useAutoHint();
    int getShadowDarken();
    boolean isEnabled();

    enum FontAntialiasMode {
        NORMAL,
        LIGHT
    }
}
