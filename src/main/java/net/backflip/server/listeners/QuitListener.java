package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.backflip.server.api.message.MessageKey;
import net.backflip.server.api.message.Placeholder;
import net.backflip.server.api.player.Messenger;
import net.backflip.server.api.settings.Settings;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;

public class QuitListener implements Listener {

    public QuitListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerDisconnectEvent.class, event -> {
            if (Settings.QUIT_MESSAGE.getValue()) {
                for (Player all : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                    new Messenger(all).sendMessage(MessageKey.QUIT_MESSAGE, new Placeholder("player", event.getPlayer().getUsername()));
                }
            }
        });
    }
}
