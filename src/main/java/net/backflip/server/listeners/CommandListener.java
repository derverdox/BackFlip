package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.backflip.server.api.message.Message;
import net.backflip.server.api.player.Player;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerCommandEvent;
import net.nonswag.tnl.api.command.CommandManager;

public class CommandListener implements Listener {

    public CommandListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerCommandEvent.class, event -> {
            if (!CommandManager.onCommand(event.getCommand(), new Player(event.getPlayer()).getExecutor())) {
                event.getPlayer().sendMessage(Message.UNKNOWN_COMMAND.getText().replace("%command%", event.getCommand().split(" ")[0]));
            }
        });
    }
}
