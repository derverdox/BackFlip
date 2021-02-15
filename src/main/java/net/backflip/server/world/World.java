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
import net.backflip.server.api.logger.Logger;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class World extends InstanceContainer {

    @Nonnull private final ConcurrentHashMap<Chunk, Long> chunkLastChange = new ConcurrentHashMap<>();
    @Nonnull private final EntityLoader entityLoader;
    @Nonnull private final AnvilChunkLoader anvilChunkLoader;
    @Nonnull private final String name;

    //TODO: Einheitliche UUID für jede Instanz! -> UUID irgendwo abspeichern und neu reinladen

    public World(String name, @NotNull DimensionType dimensionType) {
        super(UUID.randomUUID(), dimensionType, MinecraftServer.getStorageManager().getLocation(name + "/data", new StorageOptions(), new FileSystemStorage()));
        this.name = name;
        this.entityLoader = new EntityLoader(this);
        this.anvilChunkLoader = new AnvilChunkLoader(MinecraftServer.getStorageManager().getLocation(getName() + "/region", new StorageOptions(), new FileSystemStorage()), this);
        this.enableAutoChunkLoad(true);
        this.setChunkGenerator(new WorldGenerator());
        this.setData(new SerializableDataImpl());
        this.setChunkLoader(anvilChunkLoader);
        MinecraftServer.getSchedulerManager().buildShutdownTask(this::save).schedule();
        createEventCallbacks();
    }

    @Nonnull
    public ConcurrentHashMap<Chunk, Long> getChunkLastChange() {
        return chunkLastChange;
    }

    @Nonnull
    public EntityLoader getEntityLoader() {
        return entityLoader;
    }

    @Nonnull
    public AnvilChunkLoader getAnvilChunkLoader() {
        return anvilChunkLoader;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void save() {
        Logger.debug("§aSaving world§8: §6" + getName());
        this.saveInstance(() -> Logger.debug("§aSaved world§8: §6" + getName()));
        this.getChunks().forEach(chunk -> {
            this.entityLoader.getEntityStorage(chunk.getChunkX(), chunk.getChunkZ()).saveCachedData();
            entityLoader.saveChunkEntities(chunk, null);
        });
        this.saveInstance(() -> Logger.debug("§aSaved entities in world§8: §6" + getName()));
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
            if (chunk != null) {
                if (chunkLastChange.containsKey(chunk) && chunkLastChange.get(chunk) != chunk.getLastChangeTime()) {
                    eventInstance.saveChunkToStorage(chunk);
                }
                chunkLastChange.remove(chunk);
                entityLoader.saveChunkEntities(chunk, null);
            }
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
}
