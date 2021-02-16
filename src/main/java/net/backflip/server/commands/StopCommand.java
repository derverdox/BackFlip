package net.backflip.server.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop");
        setCondition(this::condition);
        setDefaultExecutor(this::execute);
    }

    private void execute(CommandSender commandSender, Arguments arguments) {
        MinecraftServer.stopCleanly();
    }

    private boolean condition(CommandSender commandSender, String s) {
        return commandSender.hasPermission("backflip.command.stop");
    }
}
