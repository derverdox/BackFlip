package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerChatEvent.class, event -> {
            event.setCancelled(true);
            for (net.minestom.server.entity.Player all : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                all.sendMessage("§f" + all.getUsername() + " §8» §f" + event.getMessage());
            }
        });
    }
}
