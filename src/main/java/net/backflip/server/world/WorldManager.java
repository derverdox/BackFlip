package net.backflip.server.world;

import net.backflip.server.BackFlip;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerChunkLoadEvent;
import net.minestom.server.event.player.PlayerChunkUnloadEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;

public class WorldManager {

    private final BackFlip backFlip;
    private final int viewDistance;

    public WorldManager(BackFlip backFlip, int viewDistance){
        this.backFlip = backFlip;
        this.viewDistance = viewDistance;
    }

    private void registerEventCallbacks(){

        //TODO: Chunks in View Distance laden
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerChunkLoadEvent.class,playerChunkLoadEvent -> {

        });

        //TODO: Chunks außerhalb View Distance laden in dem sich keine Viewer mehr befinden
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerChunkUnloadEvent.class,playerChunkUnloadEvent -> {

        });
    }

}
