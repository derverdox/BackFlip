package net.backflip.server.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;

public class GameModeCommand extends Command {
    public GameModeCommand() {
        super("gamemode", "gm");
        this.setDefaultExecutor(this::execute);
    }

    public void execute(CommandSender player, Arguments arguments) {

    }
}
