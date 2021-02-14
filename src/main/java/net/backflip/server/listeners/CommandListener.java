package net.backflip.server.listeners;

import net.backflip.server.api.event.Listener;
import net.backflip.server.api.player.Player;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerCommandEvent;
import net.nonswag.tnl.api.command.CommandManager;

public class CommandListener implements Listener {

    public CommandListener() {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerCommandEvent.class, event -> {
            if (!CommandManager.onCommand(event.getCommand(), new Player(event.getPlayer()).getExecutor())) {
                event.getPlayer().sendMessage("§cThe Command §8(§4" + event.getCommand().split(" ")[0] + "§8)§c doesn't exist");
            }
        });
    }
}
