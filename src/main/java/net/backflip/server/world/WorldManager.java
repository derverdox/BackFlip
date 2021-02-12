package net.backflip.server.world;

import net.backflip.server.BackFlip;
import net.backflip.server.world.chunk.AnvilChunkLoader;
import net.backflip.server.world.chunk.FileSystemStorage;
import net.backflip.server.world.entity.EntityLoader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.data.SerializableDataImpl;
import net.minestom.server.event.instance.InstanceChunkLoadEvent;
import net.minestom.server.event.instance.InstanceChunkUnloadEvent;
import net.minestom.server.event.player.PlayerChunkLoadEvent;
import net.minestom.server.event.player.PlayerChunkUnloadEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.storage.StorageManager;
import net.minestom.server.storage.StorageOptions;
import net.minestom.server.world.DimensionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WorldManager {

    public static Logger LOGGER = LoggerFactory.getLogger(WorldManager.class);
    private final BackFlip backFlip;
    private final int viewDistance;
    private final ConcurrentHashMap<Chunk, Long> chunkLastChange = new ConcurrentHashMap<>();

    public WorldManager(BackFlip backFlip, int viewDistance){
        this.backFlip = backFlip;
        this.viewDistance = viewDistance;
    }

    private void registerEventCallbacks(){

        //TODO: Chunks in View Distance laden
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerChunkLoadEvent.class,playerChunkLoadEvent -> {

        });

        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerChunkUnloadEvent.class,playerChunkUnloadEvent -> {
            Instance eventInstance = playerChunkUnloadEvent.getPlayer().getInstance();
            if(eventInstance == null)
                return;
            Chunk chunk = eventInstance.getChunk(playerChunkUnloadEvent.getChunkX(), playerChunkUnloadEvent.getChunkZ());
            if(chunk == null)
                return;
            if(!chunk.getViewers().isEmpty())
                return;

            //TODO: View Distance einbauen

            eventInstance.unloadChunk(chunk);
        });

        MinecraftServer.getGlobalEventHandler().addEventCallback(InstanceChunkUnloadEvent.class, instanceChunkUnloadEvent -> {
            Instance eventInstance = instanceChunkUnloadEvent.getInstance();
            Chunk chunk = eventInstance.getChunk(instanceChunkUnloadEvent.getChunkX(), instanceChunkUnloadEvent.getChunkZ());
            // The Chunk has been changed since it was loaded
            if(chunk == null)
                return;
            if(chunkLastChange.containsKey(chunk) && chunkLastChange.get(chunk) != chunk.getLastChangeTime())
                eventInstance.saveChunkToStorage(chunk);
            chunkLastChange.remove(chunk);
            new EntityLoader(eventInstance).saveChunkEntities(chunk,null);
        });

        MinecraftServer.getGlobalEventHandler().addEventCallback(InstanceChunkLoadEvent.class, instanceChunkLoadEvent -> {
            Instance eventInstance = instanceChunkLoadEvent.getInstance();
            Chunk chunk = eventInstance.getChunk(instanceChunkLoadEvent.getChunkX(), instanceChunkLoadEvent.getChunkZ());
            if(chunk == null)
                return;
            chunkLastChange.put(chunk,chunk.getLastChangeTime());
            new EntityLoader(eventInstance).loadChunkEntities(chunk.getChunkX(),chunk.getChunkZ(),null);
        });

        MinecraftServer.getSchedulerManager().buildShutdownTask(() -> {
            MinecraftServer.getInstanceManager().getInstances().forEach(shutdownInstance -> {
                LOGGER.info("Saving entities of "+shutdownInstance);
                shutdownInstance.getChunks().forEach(chunk -> {
                    new EntityLoader(shutdownInstance).saveChunkEntities(chunk,null);
                });
            });
        }).schedule();
    }

    public InstanceContainer loadOrCreateWorld(String worldName, DimensionType dimensionType){
        StorageManager storageManager = MinecraftServer.getStorageManager();
        storageManager.defineDefaultStorageSystem(FileSystemStorage::new);

        InstanceContainer world = MinecraftServer.getInstanceManager().createInstanceContainer(dimensionType, storageManager.getLocation(worldName + "/data", new StorageOptions(), new FileSystemStorage()));
        AnvilChunkLoader anvilChunkLoader = new AnvilChunkLoader(storageManager.getLocation(worldName + "/region", new StorageOptions(), new FileSystemStorage()), world);
        world.enableAutoChunkLoad(true);
        world.setChunkGenerator(new WorldGenerator());
        world.setData(new SerializableDataImpl());
        world.setChunkLoader(anvilChunkLoader);

        for(int x = 0; x < 2; x++){
            for(int z = 0; z < 2; z++){
                LOGGER.debug("Attempting to preLoad Chunk at "+x+", "+z);
                world.loadChunk(x,z);
            }
        }

        MinecraftServer.getSchedulerManager().buildShutdownTask(() -> {
            LOGGER.info("Saving world "+worldName+" ...");
            world.saveInstance(() -> LOGGER.info("World "+worldName+" saved"));
        }).schedule();
        return world;
    }

    public Instance findInstance(UUID uuid){
        return MinecraftServer.getInstanceManager().getInstances().stream().filter(instance1 -> instance1.getUniqueId().equals(uuid)).findAny().orElse(null);
    }

}
