package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerLoginEvent;

public class JoinListener implements Listener {

    public JoinListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerLoginEvent.class, event -> {
            event.getPlayer().setGameMode(GameMode.CREATIVE);
            event.getPlayer().setAllowFlying(true);
            event.getPlayer().setFlyingSpeed(0.5f);
            event.getPlayer().setPermissionLevel(4);
        });
    }
}
