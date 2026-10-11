package instadevelopment.kiriyaga.insta.impl.command;

import instadevelopment.kiriyaga.api.annotation.RegisterCommand;
import instadevelopment.kiriyaga.api.model.command.Command;
import instadevelopment.kiriyaga.api.model.command.CommandRoute;
import instadevelopment.kiriyaga.api.util.container.ContainerUtils;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;

import static instadevelopment.kiriyaga.api.InstaApi.*;

@RegisterCommand
public class PeekCommand extends Command {

    public PeekCommand() {
        super("peek");
    }

    @Override
    public CommandRoute[] getRoutes() {
        return new CommandRoute[] {
                new CommandRoute(null)
        };
    }

    @Override
    public void execute(String route, Object[] args) {
        MC.execute(() -> {
            ItemStack main = MC.player.getMainHandItem();
            ItemStack off = MC.player.getOffhandItem();

            if (ContainerUtils.openContainer(main)) return;
            if (ContainerUtils.openContainer(off)) return;
            if (MC.crosshairPickEntity instanceof ItemFrame entity) {
                ContainerUtils.openContainer(entity.getItem());
            }
        });
    }
}