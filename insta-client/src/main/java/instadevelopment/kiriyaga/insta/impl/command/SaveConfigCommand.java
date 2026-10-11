package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.core.config.ConfigMode;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandArgument;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class SaveConfigCommand extends Command {

    public SaveConfigCommand() {
        super("saveconfig");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[] {
                new CommandRoute(null, new CommandArgument[] {
                        new CommandArgument.ConfigNameArg("configName"),
                        new CommandArgument.ActionArg("mode", "all", "settings", "keybind", "color") {
                            @Override
                            public boolean isRequired() {
                                return false;
                            }
                        }
                })
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        String configName = args[0].toString();

        String modeArg = args.length > 1 && args[1] != null ? args[1].toString() : "all";
        ConfigMode mode = ConfigMode.valueOf(modeArg.toUpperCase());

        try {
            CONFIG_SERVICE.saveConfig(configName, mode);
            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Config {global}" + configName + "{gray} saved with {global}" + mode.name().toLowerCase() + "{gray}."));
        } catch (Exception e) {
            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Failed to save config {global}" + configName + "{gray}: {global}" + e + "{gray}."));
        }
    }
}
