package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class ListConfigCommand extends Command {

    public ListConfigCommand() {
        super("listconfig");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[] {
                new CommandRoute(null)
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        try {
            var configs = CONFIG_SERVICE.getConfigSerializer().listConfigs();

            if (configs.isEmpty()) {
                CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}No configs found."));
                return;
            }

            StringBuilder builder = new StringBuilder("Configs: ");
            for (int i = 0; i < configs.size(); i++) {
                builder.append("{global}").append(configs.get(i)).append("{gray}");
                if (i < configs.size() - 1) builder.append(", ");
            }

            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format(builder.toString()));

        } catch (Exception e) {
            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Failed to list configs: {global}" + e + "{gray}."));
        }
    }
}
