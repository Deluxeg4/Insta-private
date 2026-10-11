package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandArgument;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;

import static instadevelopment.kiriyaga.api.InstaApi.*;
import static instadevelopment.kiriyaga.insta.Insta.DISPLAY_NAME;

@RegisterCommand
public class NameCommand extends Command {

    public NameCommand() {
        super("name");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[] {
                new CommandRoute("set", new CommandArgument[] {new CommandArgument.StringArg("name", 1, 24)}
                )
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        String newName = (String) args[0];

        DISPLAY_NAME = newName;
        CONFIG_SERVICE.saveName(newName);

        CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Name set to: {global}" + newName + "{gray}."));
    }
}
