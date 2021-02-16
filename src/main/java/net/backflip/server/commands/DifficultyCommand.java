package net.backflip.server.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;

public class DifficultyCommand extends Command {
    public DifficultyCommand() {
        super("difficulty");
        this.setCondition(this::condition);
    }

    private boolean condition(CommandSender commandSender, String s) {
        return commandSender.hasPermission("backflip.command.difficulty");
    }
}
