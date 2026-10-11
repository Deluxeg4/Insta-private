package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandArgument;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;
import instadevelopment.kiriyaga.api.model.feature.Feature;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[]{new CommandRoute(null, new CommandArgument[]{new CommandArgument.FeatureArg("feature")})
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        String input = args[0].toString();

        Feature found = FEATURE_SERVICE.getStorage().getAll().stream()
                .filter(f -> f.matches(input))
                .findFirst()
                .orElse(null);

        if (found == null) {
            CHAT_SERVICE.sendPersistent(this.getName(), CAT_FORMAT.format("{gray}Feature {global}" + input + "{gray} not found."));
            return;
        }
        found.toggle();
    }
}
