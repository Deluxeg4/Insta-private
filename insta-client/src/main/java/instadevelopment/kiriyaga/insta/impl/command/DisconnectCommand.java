package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;

import static instadevelopment.kiriyaga.api.InstaApi.MC;

@RegisterCommand
public class DisconnectCommand extends Command {

    public DisconnectCommand() {
        super("disconnect");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[] {
                new CommandRoute(null)
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        if (MC.player != null && MC.getConnection() != null) {
            MC.getConnection().handleDisconnect(new ClientboundDisconnectPacket(Component.empty()));
        }
    }
}
