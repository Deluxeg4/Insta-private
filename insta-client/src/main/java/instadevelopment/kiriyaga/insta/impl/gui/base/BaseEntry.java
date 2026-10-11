package instadevelopment.kiriyaga.insta.impl.gui.base;

import net.minecraft.network.chat.Component;

public abstract class BaseEntry {
    protected Component displayText;

    public abstract Component getDisplayText();

    public abstract void refreshEntry();
}
