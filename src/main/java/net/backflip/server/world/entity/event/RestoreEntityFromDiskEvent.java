package net.backflip.server.world.entity.event;

import net.minestom.server.data.SerializableData;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.event.Event;
import net.minestom.server.instance.Instance;

import java.util.function.Supplier;

public class RestoreEntityFromDiskEvent extends Event {

    private final Instance instance;
    private final SerializableData serializableData;
    private Supplier<EntityCreature> supplier;

    public RestoreEntityFromDiskEvent(Instance instance, SerializableData serializableData){
        this.instance = instance;
        this.serializableData = serializableData;
    }

    public void setSupplier(Supplier<EntityCreature> supplier) {
        this.supplier = supplier;
    }

    public Instance getInstance() {
        return instance;
    }

    public Supplier<EntityCreature> getSupplier() {
        return supplier;
    }

    public SerializableData getSerializableData() {
        return serializableData;
    }
}
