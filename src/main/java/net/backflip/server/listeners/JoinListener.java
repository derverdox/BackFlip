package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.backflip.server.api.message.MessageKey;
import net.backflip.server.api.message.Placeholder;
import net.backflip.server.api.player.Messenger;
import net.backflip.server.api.settings.Settings;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.permission.Permission;

public class JoinListener implements Listener {

    public JoinListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerLoginEvent.class, event -> {
            if (event.getPlayer().getSettings().getViewDistance() > Settings.CLIENT_MAX_CHUNK_RENDER_DISTANCE.getValue()) {
                new Messenger(event.getPlayer()).kick(MessageKey.KICKED, new Placeholder("reason", "§6Your view distance is higher then allowed%nl%§8(§7" + Settings.CLIENT_MAX_CHUNK_RENDER_DISTANCE.getValue() + "§8)"));
            } else {
                event.getPlayer().setReducedDebugScreenInformation(Settings.REDUCE_DEBUG_SCREEN_INFORMATION.getValue());
                GameMode gameMode = GameMode.fromId(Settings.DEFAULT_GAMEMODE.getValue());
                if (gameMode != null) {
                    event.getPlayer().setGameMode(gameMode);
                }
                if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE) || event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
                    event.getPlayer().setAllowFlying(true);
                }
                event.getPlayer().addPermission(new Permission("backflip.command.gamemode"));
                event.getPlayer().addPermission(new Permission("backflip.command.difficulty"));
                event.getPlayer().addPermission(new Permission("backflip.command.stop"));
                event.getPlayer().addPermission(new Permission("backflip.command.save-all"));
                // check first join
                if (Settings.JOIN_MESSAGE.getValue()) {
                    for (Player all : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                        new Messenger(all).sendMessage(MessageKey.JOIN_MESSAGE, new Placeholder("player", event.getPlayer().getUsername()));
                    }
                }
            }
        });
    }
}
