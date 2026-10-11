package instadevelopment.kiriyaga.api.core.config;

import instadevelopment.kiriyaga.api.model.setting.ColorSetting;
import instadevelopment.kiriyaga.api.model.setting.KeyBindSetting;
import instadevelopment.kiriyaga.api.model.setting.Setting;

import static instadevelopment.kiriyaga.api.InstaApi.*;

public enum ConfigMode {
    SETTINGS {
        @Override
        public boolean accept(Setting<?> setting) {
            return !(setting instanceof KeyBindSetting)
                    && !(setting instanceof ColorSetting);
        }
    },
    KEYBIND {
        @Override
        public boolean accept(Setting<?> setting) {
            return setting instanceof KeyBindSetting;
        }
    },
    COLOR {
        @Override
        public boolean accept(Setting<?> setting) {
            return setting instanceof ColorSetting;
        }
    },
    ALL {
        @Override
        public boolean accept(Setting<?> setting) {
            return true;
        }
    };

    public abstract boolean accept(Setting<?> setting);
}
