package net.backflip.server.world.entity;

import net.backflip.server.world.entity.callback.EntityCallback;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EntityLoader {
    public static Logger LOGGER = LoggerFactory.getLogger(EntityLoader.class);
    //TODO: String = folderName of Chunk // contains entity datas
    private ConcurrentHashMap<String, EntitySaveStorage> alreadyLoaded = new ConcurrentHashMap<>();
    private Instance instance;

    public EntityLoader(Instance instance){
        this.instance = instance;
    }

    public boolean loadChunkEntities(int chunkX, int chunkZ, EntityCallback callback){
        Set<EntityCreature> set = loadEntityStorage(instance,chunkX,chunkZ,callback);
        return set != null && !set.isEmpty();
    }

    public void saveChunkEntities(Chunk chunk, EntityCallback callback){
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            int chunkX = chunk.getChunkX();
            int chunkZ = chunk.getChunkZ();
            EntitySaveStorage entitySaveStorage;
            synchronized (alreadyLoaded){
                entitySaveStorage = getEntityStorage(instance, chunkX, chunkZ);
                instance.getChunkEntities(chunk).forEach(entity -> {
                    if(!(entity instanceof EntityCreature))
                        return;
                    EntityCreature entityCreature = (EntityCreature) entity;
                    entitySaveStorage.saveEntity(entityCreature);
                    if(callback != null)
                        callback.accept(entityCreature);
                });
            }
        }).schedule();
    }

    private Set<EntityCreature> loadEntityStorage(Instance instance, int chunkX, int chunkZ, EntityCallback callback){
        EntitySaveStorage entitySaveStorage = getEntityStorage(instance,chunkX,chunkZ);
        if(entitySaveStorage == null)
            return null;

        Set<EntityCreature> set = entitySaveStorage.loadEntities();
        if(set == null)
            return null;
        if(callback != null)
            set.forEach(callback);
        return set;
    }

    private EntitySaveStorage getEntityStorage(Instance instance, int chunkX, int chunkZ){
        return alreadyLoaded.computeIfAbsent(EntitySaveStorage.createFolderName(instance,chunkX,chunkZ),
                folderName -> new EntitySaveStorage(instance,chunkX,chunkZ));
    }
}
