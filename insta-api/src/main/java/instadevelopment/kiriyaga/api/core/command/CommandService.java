package instadevelopment.kiriyaga.api.core.command;


import instadevelopment.kiriyaga.api.model.command.Command;

import static instadevelopment.kiriyaga.api.InstaApi.*;

public class CommandService {

    private final CommandStorage storage = new CommandStorage();
    private final CommandSuggester suggester = new CommandSuggester(storage);
    private final CommandExecutor executor = new CommandExecutor(suggester);

    public void init() {
        CommandRegistry.registerAnnotatedCommands(storage);
        suggester.rebuild();
        EVENT_SERVICE.register(executor);
    }

    public void addCommand(Command cmd) {
        storage.addCommand(cmd);
        suggester.rebuild();
    }

    public void removeCommand(Command cmd) {
        storage.removeCommand(cmd);
        suggester.rebuild();
    }

    public CommandStorage getStorage() { return storage; }
    public CommandExecutor getExecutor() { return executor; }
    public CommandSuggester getSuggester() { return suggester; }
}
