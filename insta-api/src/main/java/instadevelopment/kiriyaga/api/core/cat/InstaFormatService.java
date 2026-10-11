package instadevelopment.kiriyaga.api.core.cat;

import net.minecraft.ChatFormatting;

public class InstaFormatService extends InstaFormat {

    public InstaFormatService() {
        this(true);
    }

    public InstaFormatService(boolean vanilla) {
        super();
        if (vanilla) {
            addVanilla();
        }
    }

    private void addVanilla() {
        for (ChatFormatting value : ChatFormatting.values()) {
            if (!value.isColor() || value.getColor() == null) continue;

            add(value.getName(), value.getColor());
        }
    }
}
