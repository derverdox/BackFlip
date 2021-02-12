package net.backflip.server.commands;

import net.backflip.server.world.World;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;

public class SaveAllCommand extends Command {
    public SaveAllCommand() {
        super("save-all");
        setCondition((commandSender, s) -> true);
        setDefaultExecutor(this::execute);
    }

    private void execute(CommandSender player, Arguments arguments) {
        MinecraftServer.getInstanceManager().getInstances().forEach(i -> {
            if(!(i instanceof World))
                return;
            World world = (World) i;
            world.saveWorld();
        });
    }
}
