package net.backflip.server.commands;

import net.backflip.server.api.message.MessageKey;
import net.backflip.server.api.message.Placeholder;
import net.backflip.server.api.player.Messenger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;

public class GameModeCommand extends Command {
    public GameModeCommand() {
        super("gamemode", "gm");
        setCondition(this::isAllowed);
        setDefaultExecutor(this::usage);
        ArgumentEntity entities = ArgumentType.Entities("player");
        GameMode[] gameModes = GameMode.values();
        String[] names = new String[gameModes.length];
        for (int i = 0; i < gameModes.length; i++) {
            names[i] = gameModes[i].name().toLowerCase();
        }
        ArgumentWord mode = ArgumentType.Word("gamemode").from(names);
        addSyntax(this::executeOnSelf, mode);
        addSyntax(this::executeOnOther, mode, entities);
    }

    private void usage(CommandSender commandSender, Arguments arguments) {
        if (commandSender.isPlayer()) {
            Messenger messenger = new Messenger(commandSender.asPlayer());
            messenger.sendMessage("§4%prefix%§c /gamemode §8[§6gamemode§8] §8(§6player§8)");
        }
    }

    private void executeOnSelf(CommandSender commandSender, Arguments arguments) {
        if (commandSender.isPlayer()) {
            Messenger messenger = new Messenger(commandSender.asPlayer());
            try {
                GameMode gamemode = GameMode.valueOf(arguments.getObject("gamemode").toString().toUpperCase());
                messenger.getPlayer().setGameMode(gamemode);
                messenger.sendMessage(MessageKey.CHANGED_GAMEMODE, new Placeholder("gamemode", gamemode.name().toLowerCase()));
            } catch (Exception e) {
                messenger.sendMessage(MessageKey.COMMAND_EXCEPTION);
            }
        }
    }

    private void executeOnOther(CommandSender commandSender, Arguments arguments) {
        if (commandSender.isPlayer()) {
            Messenger messenger = new Messenger(commandSender.asPlayer());
            try {
                ArgumentWord finder = ((ArgumentWord) arguments.getObject("player"));
                GameMode gamemode = GameMode.valueOf(arguments.getObject("gamemode").toString().toUpperCase());
                Player arg = MinecraftServer.getConnectionManager().getPlayer(finder.toString());
                if (arg != null) {
                    arg.setGameMode(gamemode);
                    new Messenger(arg).sendMessage(MessageKey.CHANGED_GAMEMODE, new Placeholder("gamemode", gamemode.name().toLowerCase()));
                } else {
                    messenger.sendMessage(MessageKey.PLAYER_NOT_ONLINE, new Placeholder("player", finder.toString()));
                }
            } catch (Exception e) {
                messenger.sendMessage(MessageKey.COMMAND_EXCEPTION);
            }
        }
    }

    private boolean isAllowed(CommandSender commandSender, String commandString) {
        return commandSender.hasPermission(new Permission("backflip.command.gamemode"));
    }
}
