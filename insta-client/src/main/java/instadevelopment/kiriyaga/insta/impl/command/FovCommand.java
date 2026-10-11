package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.CommandArgument;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.insta.mixininterface.ISimpleOption;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class FovCommand extends Command {

    public FovCommand() {
        super("fov");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[] {
                new CommandRoute(null, new CommandArgument.IntArg("value", 0, 162))
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        int newFov = (int) args[0];

        ((ISimpleOption) (Object) MC.options.fov()).setValue(newFov);

        CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}FOV set to: {global}" + newFov + "{gray}."));
    }
}
