package net.backflip.server.commands;

import net.nonswag.tnl.api.command.Command;
import net.nonswag.tnl.api.command.executor.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DifficultyCommand extends Command {

    public DifficultyCommand(@NotNull String command, @Nullable String... aliases) {
        super(command, aliases);
    }

    @Override
    public void execute(@NotNull CommandSender<?> sender, @NotNull String command, @Nullable String[] args) {
    }
}
