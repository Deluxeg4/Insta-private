package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandArgument;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class YawCommand extends Command {

    public YawCommand() {
        super("yaw");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[]{new CommandRoute(null, new CommandArgument[]{new CommandArgument.DoubleArg("value", -180, 180)})
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        double yawDouble = (double) args[0];
        float yaw = (float) yawDouble;

        if (MC.player == null) return;
        MC.player.setYRot(yaw);
        CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Yaw set to: {global}" + yaw + "{gray}."));
    }
}
