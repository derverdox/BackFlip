package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.backflip.server.api.message.MessageKey;
import net.backflip.server.api.message.Placeholder;
import net.backflip.server.api.player.Player;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.event.player.PlayerCommandEvent;

public class CommandListener implements Listener {

    public CommandListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerCommandEvent.class, event -> {
            String name = event.getCommand().split(" ")[0];
            if (!new CommandManager().commandExists(name)) {
                event.setCancelled(true);
                new Player(event.getPlayer()).sendMessage(MessageKey.UNKNOWN_COMMAND, new Placeholder("command", name));
            }
        });
    }
}
