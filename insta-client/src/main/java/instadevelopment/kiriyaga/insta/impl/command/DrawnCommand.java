package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandArgument;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;
import instadevelopment.kiriyaga.api.model.feature.Feature;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class DrawnCommand extends Command {

    public DrawnCommand() {
        super("drawn");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[] {
                new CommandRoute(null, new CommandArgument.FeatureArg("Feature"))
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        String input = (String) args[0];

        Feature found = null;
        for (Feature m : FEATURE_SERVICE.getStorage().getAll()) {
            if (m.matches(input)) {
                found = m;
                break;
            }
        }

        if (found == null) {
            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Feature {global}" + input + "{gray} not found."));
            return;
        }

        found.setDrawn(!found.isDrawn());
        CHAT_SERVICE.sendPersistent(this.getName(), "{gray}Feature {global}" + input + " {gray}drawn changed.");
    }
}
