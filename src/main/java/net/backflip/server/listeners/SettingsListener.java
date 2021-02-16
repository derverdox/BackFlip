package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.backflip.server.api.message.MessageKey;
import net.backflip.server.api.message.Placeholder;
import net.backflip.server.api.player.Messenger;
import net.backflip.server.api.settings.Settings;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerSettingsChangeEvent;

public class SettingsListener implements Listener {

    public SettingsListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerSettingsChangeEvent.class, event -> {
            if (event.getPlayer().getSettings().getViewDistance() > Settings.CLIENT_MAX_CHUNK_RENDER_DISTANCE.getValue()) {
                new Messenger(event.getPlayer()).kick(MessageKey.KICKED, new Placeholder("reason", "§6Your view distance is higher then allowed%nl%§8(§7" + Settings.CLIENT_MAX_CHUNK_RENDER_DISTANCE.getValue() + "§8)"));
            }
        });
    }
}
