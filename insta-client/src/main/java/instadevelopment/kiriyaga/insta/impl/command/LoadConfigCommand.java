package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.core.config.ConfigMode;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandArgument;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class LoadConfigCommand extends Command {

    public LoadConfigCommand() {
        super("loadconfig");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[] {

                new CommandRoute("default", new CommandArgument[] {new CommandArgument.ConfigNameArg("configName")}),

                new CommandRoute("mode", new CommandArgument[] {new CommandArgument.ConfigNameArg("configName"), new CommandArgument.ActionArg("mode", "all", "settings", "keybind", "color")})
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        String configName = args[0].toString();

        String modeArg = "all";
        if ("mode".equals(route)) {
            modeArg = args[1].toString();
        }

        ConfigMode mode = ConfigMode.valueOf(modeArg.toUpperCase());

        try {
            CONFIG_SERVICE.loadConfig(configName, mode);

            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Config {global}" + configName + "{gray} loaded with mode {global}" + mode.name().toLowerCase() + "{gray}."));

        } catch (Exception e) {
            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Failed to load config {global}" + configName + "{gray}: {r}" + e.getMessage() + "{gray}."));
        }
    }
}
