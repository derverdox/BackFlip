package net.backflip.server.api.entity;

import net.backflip.server.BackFlip;
import net.backflip.server.api.entity.exceptions.EntityIDInvalidException;
import net.backflip.server.api.entity.exceptions.EntityIDNotUniqueException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CustomEntityManager {

    private final ConcurrentHashMap<Integer,Class<? extends CustomEntity>> entities;

    public CustomEntityManager(BackFlip backFlip){
        this.entities = new ConcurrentHashMap<>();
    }

    public void registerCustomEntity(Class<? extends CustomEntity> customEntityClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor =  customEntityClass.getDeclaredConstructor(UUID.class);
        CustomEntity customEntity = (CustomEntity) constructor.newInstance(UUID.randomUUID());

        if(exist(customEntity.getCustomID()))
            throw new EntityIDNotUniqueException("EntityID already registered. (customID: "+customEntity.getCustomID()+")");
        if(customEntity.getCustomID() < 0)
            throw new EntityIDInvalidException("EntityIDs have to be >= 0");
        entities.put(customEntity.getCustomID(),customEntityClass);
    }

    public boolean exist(Integer id){
        return entities.containsKey(id);
    }

}
