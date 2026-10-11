package instadevelopment.kiriyaga.insta.impl.gui.entry;

import instadevelopment.kiriyaga.api.model.plugin.Plugin;
import instadevelopment.kiriyaga.insta.impl.gui.base.BaseEntry;
import net.minecraft.network.chat.Component;

import static instadevelopment.kiriyaga.api.InstaApi.CAT_FORMAT;

public class PluginEntry extends BaseEntry {

    private final Plugin plugin;

    public PluginEntry(Plugin plugin) {
        this.plugin = plugin;

        String status = plugin.isEnabled() ? "{green}Loaded" : "{red}Unloaded";
        this.displayText = CAT_FORMAT.format(
                "{gray}Name: {global}" + plugin.getName()
                        + " {gray}ID: {global}" + plugin.getId()
                        + " {gray}Status: " + status
                        + " {gray}Modules: {global}" + plugin.getRegisteredFeatures().size()
                        + " {gray}Commands: {global}" + plugin.getRegisteredCommands().size()
                        + " {gray}Author: {global}" + plugin.getAuthors()
                        + " {gray}Version: {global}" + plugin.getVersion()
        );
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public int getId() {
        return plugin.getId();
    }

    public String getName() {
        return plugin.getName();
    }

    public boolean isEnabled() {
        return plugin.isEnabled();
    }

    @Override
    public Component getDisplayText() {
        return this.displayText;
    }

    @Override
    public void refreshEntry() {}
}
