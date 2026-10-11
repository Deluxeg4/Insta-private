package instadevelopment.kiriyaga.api.event.impl;

import instadevelopment.kiriyaga.api.event.Event;
import net.minecraft.client.gui.screens.Screen;

public class OpenScreenEvent extends Event {
    private final Screen screen;

    public OpenScreenEvent(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }
}
