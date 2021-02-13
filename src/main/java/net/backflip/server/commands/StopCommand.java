package net.backflip.server.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop");
        setCondition((commandSender, s) -> true);
        setDefaultExecutor((commandSender, arguments) -> MinecraftServer.stopCleanly());
    }
}
