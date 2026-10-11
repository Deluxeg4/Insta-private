package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class SaveCommand extends Command {

    public SaveCommand() {
        super("save");
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
            CONFIG_SERVICE.saveFeatures();
            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Config has been saved."));
        } catch (Exception e) {
            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Config has not been saved: {global}" + e + "{gray}."));
        }
    }
}
