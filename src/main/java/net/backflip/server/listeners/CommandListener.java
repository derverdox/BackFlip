package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.backflip.server.api.message.MessageKey;
import net.backflip.server.api.message.Placeholder;
import net.backflip.server.api.player.Messenger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.event.player.PlayerCommandEvent;

public class CommandListener implements Listener {

    public CommandListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerCommandEvent.class, event -> {
            String command = event.getCommand().split(" ")[0];
            if (!MinecraftServer.getCommandManager().commandExists(command)) {
                event.setCancelled(true);
                if (!command.replace("/", "").isEmpty()) {
                    new Messenger(event.getPlayer()).sendMessage(MessageKey.UNKNOWN_COMMAND, new Placeholder("command", command));
                }
            } else {
                Command arg = MinecraftServer.getCommandManager().getCommand(command);
                if (arg != null && arg.getCondition() != null) {
                    if (!arg.getCondition().canUse(event.getPlayer(), null)) {
                        new Messenger(event.getPlayer()).sendMessage(MessageKey.NO_PERMISSION, new Placeholder("permission", "backflip.command." + arg.getName()));
                    }
                }
            }
        });
    }
}
