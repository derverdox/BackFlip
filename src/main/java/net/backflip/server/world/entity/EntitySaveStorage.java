package net.backflip.server.world.entity;

import net.backflip.server.api.entity.EntityCreatureImpl;
import net.backflip.server.world.chunk.FileSystemStorage;
import net.backflip.server.world.entity.event.RestoreEntityFromDiskEvent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.data.SerializableData;
import net.minestom.server.data.SerializableDataImpl;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.instance.Instance;
import net.minestom.server.storage.StorageLocation;
import net.minestom.server.storage.StorageOptions;
import net.minestom.server.utils.Position;
import org.jglrxavpok.hephaistos.mca.RegionFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EntitySaveStorage {

    public static Logger LOGGER = LoggerFactory.getLogger(EntitySaveStorage.class);

    private String folderName;
    private StorageLocation storage;
    private Instance instance;
    private int chunkX;
    private int chunkZ;

    public EntitySaveStorage(Instance instance, int chunkX, int chunkZ){
        this.instance = instance;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.folderName = createFolderName(instance,chunkX,chunkZ);
        this.storage = MinecraftServer.getStorageManager().getLocation(folderName, new StorageOptions(), new FileSystemStorage());
    }

    public void saveEntity(EntityCreature entityCreature){
        if(entityCreature.getData() == null)
            entityCreature.setData(new SerializableDataImpl());

        entityCreature.getData().set("entityType",entityCreature.getEntityType().getId(), Integer.class);

        entityCreature.getData().set("entity_pos_x",entityCreature.getPosition().getX(), Double.class);
        entityCreature.getData().set("entity_pos_y",entityCreature.getPosition().getY(), Double.class);
        entityCreature.getData().set("entity_pos_z",entityCreature.getPosition().getZ(), Double.class);

        // X
        entityCreature.getData().set("bBox_width",entityCreature.getBoundingBox().getWidth(), Double.class);
        // Y
        entityCreature.getData().set("bBox_height",entityCreature.getBoundingBox().getHeight(), Double.class);
        // Z
        entityCreature.getData().set("bBox_depth",entityCreature.getBoundingBox().getDepth(), Double.class);

        //TODO: Zusätzliche NBT Tags
        // > DisplayName ?

        SerializableDataImpl serializableData = (SerializableDataImpl) entityCreature.getData();
        storage.set(entityCreature.getUuid().toString(), serializableData.getIndexedSerializedData());
    }

    private EntityCreature restoreEntity(Instance instance, SerializableData serializableData){
        if(serializableData == null) {
            LOGGER.error("Couldn't restore Entity: Data Object null");
            return null;
        }
        try{
            Integer entityID = serializableData.getOrDefault("entityType",-1);

            if(entityID == null)
                throw new RuntimeException("Error while restoring EntityType.. skipping");

            Double posX = serializableData.getOrDefault("entity_pos_x",0d);
            Double posY = serializableData.getOrDefault("entity_pos_y",42d);
            Double posZ = serializableData.getOrDefault("entity_pos_z",0d);

            Double bBox_width = serializableData.getOrDefault("bBox_width",-1d);
            Double bBox_height = serializableData.getOrDefault("bBox_height",-1d);
            Double bBox_depth = serializableData.getOrDefault("bBox_depth",-1d);

            if(bBox_width.doubleValue() == -1 || bBox_depth.doubleValue() == -1 || bBox_depth.doubleValue() == -1)
                throw new RuntimeException("Error while restoring BoundingBox for entity.. skipping");

            RestoreEntityFromDiskEvent restoreEntityFromDiskEvent = new RestoreEntityFromDiskEvent(instance,serializableData);

            MinecraftServer.getGlobalEventHandler().callEvent(RestoreEntityFromDiskEvent.class,restoreEntityFromDiskEvent);

            EntityCreature entityCreature = null;

            if(restoreEntityFromDiskEvent.getSupplier() != null)
                entityCreature = restoreEntityFromDiskEvent.getSupplier().get();

            if(entityCreature == null) {
                Position position = new Position(posX,posY,posZ);
                entityCreature = new EntityCreatureImpl(EntityType.fromId(entityID), position, bBox_width, bBox_height, bBox_depth);
            }
            entityCreature.setData(serializableData);

            LOGGER.debug("Entity restored");
            entityCreature.setInstance(instance);
            return entityCreature;
        }
        catch (Exception e){
            LOGGER.error("Error initializing entity.. Skipping ("+e.getMessage()+")");
            e.printStackTrace();
            return null;
        }
    }

    public Set<EntityCreature> loadEntities() {
        try {
            if(isEmpty()) {
                Files.delete(Path.of(storage.getLocation()));
                return null;
            }

            if(!Files.exists(Path.of(storage.getLocation())))
                return null;

            Set<EntityCreature> set = new HashSet<>();

            Files.walk(Path.of(storage.getLocation()),3)
                    .skip(1)
                    .forEach(path -> {
                String fileName = path.toFile().getName();
                if(!fileName.contains(".dat"))
                    return;

                UUID uuid = UUID.fromString(fileName.replace(".dat",""));
                SerializableData serializableData = storage.get(uuid.toString(), SerializableData.class);
                EntityCreature entityCreature = restoreEntity(instance, serializableData);
                set.add(entityCreature);
                storage.delete(uuid.toString());
            });
            return set;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if the Storage has entities stored
     * @return
     */
    public boolean isEmpty(){
        try {
            return Files.exists(Path.of(storage.getLocation())) && !Files.newDirectoryStream(Path.of(storage.getLocation())).iterator().hasNext();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteEntity(EntityCreature entityCreature){
        storage.delete(entityCreature.getUuid().toString());
    }

    public static String createFolderName(Instance instance, int chunkX, int chunkZ){
        return instance.getStorageLocation().getLocation()+"/entities/"+RegionFile.Companion.createFileName(chunkX,chunkZ);
    }

    public void saveCachedData(){
        this.storage.saveAndRemoveCachedData("entities");
    }
}
