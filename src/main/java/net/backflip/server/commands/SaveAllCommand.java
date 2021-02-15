package net.backflip.server.commands;

import net.backflip.server.api.logger.Logger;
import net.backflip.server.api.message.Message;
import net.backflip.server.api.message.MessageKey;
import net.backflip.server.world.World;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class SaveAllCommand extends Command {
    public SaveAllCommand() {
        super("save-all");
        setDefaultExecutor(this::execute);
        setCondition(this::condition);
    }

    private void execute(CommandSender commandSender, Arguments arguments) {
        MinecraftServer.getInstanceManager().getInstances().forEach(i -> {
            if (i instanceof World) {
                ((World) i).save();
                if (commandSender instanceof Player) {
                    new net.backflip.server.api.player.Player(((Player) commandSender)).sendMessage(MessageKey.WORLD_SAVED);
                } else {
                    Logger.debug(Message.WORLD_SAVED_EN.getText());
                }
            }
        });
    }

    private boolean condition(CommandSender commandSender, String s) {
        return commandSender.hasPermission("backflip.command." + super.getName());
    }
}
