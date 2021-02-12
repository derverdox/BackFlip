package net.backflip.server.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

public class StopCommand extends Command {
    public StopCommand() {
        super("stop", "s");
        setCondition((commandSender, s) -> true);
        // Default Command Executor
        setDefaultExecutor((commandSender, arguments) -> {
            MinecraftServer.stopCleanly();
        });
    }
}
