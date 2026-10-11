package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandArgument;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;
import instadevelopment.kiriyaga.insta.mixininterface.ISimpleOption;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class GammaCommand extends Command {

    public GammaCommand() {
        super("gamma");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[] {
                new CommandRoute(null, new CommandArgument.IntArg("value", 0, 420))
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        int newGamma = (int) args[0];

        ((ISimpleOption) (Object) MC.options.gamma()).setValue((double) newGamma);

        CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Gamma set to: {global}" + newGamma + "{gray}."));
    }
}
