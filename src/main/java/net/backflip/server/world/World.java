package net.backflip.server.world;

import net.backflip.server.world.chunk.AnvilChunkLoader;
import net.backflip.server.world.chunk.FileSystemStorage;
import net.backflip.server.world.entity.EntityLoader;
import net.backflip.server.world.generator.WorldGenerator;
import net.minestom.server.MinecraftServer;
import net.minestom.server.data.SerializableDataImpl;
import net.minestom.server.event.instance.InstanceChunkLoadEvent;
import net.minestom.server.event.instance.InstanceChunkUnloadEvent;
import net.minestom.server.event.player.PlayerChunkUnloadEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.storage.StorageOptions;
import net.minestom.server.world.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class World extends InstanceContainer {
    public static Logger LOGGER = LoggerFactory.getLogger(WorldManager.class);

    private final ConcurrentHashMap<Chunk, Long> chunkLastChange = new ConcurrentHashMap<>();
    private final EntityLoader entityLoader;
    private final AnvilChunkLoader anvilChunkLoader;
    private String worldName;

    public World(String worldName, @NotNull DimensionType dimensionType) {
        super(UUID.randomUUID(), dimensionType, MinecraftServer.getStorageManager().getLocation(worldName + "/data", new StorageOptions(), new FileSystemStorage()));
        this.worldName = worldName;
        this.entityLoader = new EntityLoader(this);
        this.anvilChunkLoader = new AnvilChunkLoader(MinecraftServer.getStorageManager().getLocation(worldName + "/region", new StorageOptions(), new FileSystemStorage()), this);

        this.enableAutoChunkLoad(true);
        this.setChunkGenerator(new WorldGenerator());
        this.setData(new SerializableDataImpl());
        this.setChunkLoader(anvilChunkLoader);

        MinecraftServer.getSchedulerManager().buildShutdownTask(this::saveWorld).schedule();
        createEventCallbacks();
    }

    private void preLoadChunks(){
        for(int x = 0; x < 2; x++){
            for(int z = 0; z < 2; z++){
                LOGGER.debug("Attempting to preLoad Chunk at "+x+", "+z);
                this.loadChunk(x,z);
            }
        }
    }

    public void saveWorld(){
        LOGGER.info("Saving world "+worldName+" ...");
        this.saveInstance(() -> LOGGER.info("World "+worldName+" chunks saved"));
        this.getChunks().forEach(chunk -> {
            this.entityLoader.getEntityStorage(chunk.getChunkX(),chunk.getChunkZ()).saveCachedData();
            entityLoader.saveChunkEntities(chunk, null);
        });
        this.saveInstance(() -> LOGGER.info("World "+worldName+" entities saved"));
    }

    private void createEventCallbacks(){

        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerChunkUnloadEvent.class, playerChunkUnloadEvent -> {
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
            entityLoader.saveChunkEntities(chunk,null);
        });

        MinecraftServer.getGlobalEventHandler().addEventCallback(InstanceChunkLoadEvent.class, instanceChunkLoadEvent -> {
            Instance eventInstance = instanceChunkLoadEvent.getInstance();
            Chunk chunk = eventInstance.getChunk(instanceChunkLoadEvent.getChunkX(), instanceChunkLoadEvent.getChunkZ());
            if(chunk == null)
                return;
            chunkLastChange.put(chunk,chunk.getLastChangeTime());
            entityLoader.loadChunkEntities(chunk.getChunkX(),chunk.getChunkZ(),null);
        });

    }

    public EntityLoader getEntityLoader() {
        return entityLoader;
    }
}
