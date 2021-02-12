package net.backflip.server.world;

import net.backflip.server.BackFlip;
import net.backflip.server.api.logger.Logger;
import net.backflip.server.world.chunk.FileSystemStorage;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.storage.StorageManager;
import net.minestom.server.world.DimensionType;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WorldManager {
    private final BackFlip backFlip;
    private final int viewDistance;
    private final ConcurrentHashMap<Chunk, Long> chunkLastChange = new ConcurrentHashMap<>();

    public WorldManager(BackFlip backFlip, int viewDistance){
        this.backFlip = backFlip;
        this.viewDistance = viewDistance;
    }

    public InstanceContainer loadOrCreateWorld(String worldName, DimensionType dimensionType){
        StorageManager storageManager = MinecraftServer.getStorageManager();
        storageManager.defineDefaultStorageSystem(FileSystemStorage::new);
        Logger.info("Creating or Loading world: "+worldName);

        World world = new World(worldName,dimensionType);
        MinecraftServer.getInstanceManager().registerInstance(world);

        return world;
    }

    public Instance findInstance(UUID uuid){
        return MinecraftServer.getInstanceManager().getInstances().stream().filter(instance1 -> instance1.getUniqueId().equals(uuid)).findAny().orElse(null);
    }

}
