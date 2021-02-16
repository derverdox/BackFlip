package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.backflip.server.api.message.Message;
import net.backflip.server.api.message.Placeholder;
import net.backflip.server.world.World;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerChatEvent.class, event -> {
            event.setCancelled(true);
            for (net.minestom.server.entity.Player all : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                World world = null;
                if (event.getPlayer().getInstance() instanceof World) {
                    world = ((World) event.getPlayer().getInstance());
                }
                all.sendMessage(Message.CHAT_FORMAT.getText(new Placeholder("player", event.getPlayer().getUsername()), new Placeholder("world", (world == null ? null : world.getName())), new Placeholder("message", event.getMessage())));
            }
        });
    }
}
