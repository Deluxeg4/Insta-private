package instadevelopment.kiriyaga.api.core.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.util.ClasspathScanner;

import java.util.Set;
import static instadevelopment.kiriyaga.api.InstaApi.*;

public class CommandRegistry {

    public static void registerAnnotatedCommands(CommandStorage storage) {
        Set<Class<? extends Command>> classes = ClasspathScanner.findAnnotated(Command.class, RegisterCommand.class);

        for (Class<? extends Command> clazz : classes) {
            try {
                Command command = clazz.getDeclaredConstructor().newInstance();
                storage.addCommand(command);
            } catch (Exception e) {
                LOGGER.error("Failed to instantiate command: " + clazz.getName(), e);
            }
        }
    }
}