package net.backflip.server.api.entity;

import net.minestom.server.data.SerializableDataImpl;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.event.entity.*;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;

public abstract class CustomEntity {

    EntityCreature spawnEntity(Position spawnPosition, Instance spawnInstance){
        if(!spawnInstance.getChunkAt(spawnPosition).isLoaded())
            return null;
        EntityCreature entityCreature = setupEntity(spawnPosition);
        entityCreature.setData(new SerializableDataImpl());
        entityCreature.getData().set("entity_customID",getCustomID(),Integer.class);
        setCustomData(entityCreature);
        entityCreature.setInstance(spawnInstance);
        addEventCallbacks(entityCreature);
        return entityCreature;
    }

    private void addEventCallbacks(Entity entity){
        entity.addEventCallback(PlayerEntityInteractEvent.class,playerEntityInteractEvent -> {
            if(!playerEntityInteractEvent.getTarget().equals(entity))
                return;
            onInteract(playerEntityInteractEvent);
        });
        entity.addEventCallback(EntitySpawnEvent.class,entitySpawnEvent -> {
            if(!entitySpawnEvent.getEntity().equals(entity))
                return;
            onSpawn(entitySpawnEvent);
        });
        entity.addEventCallback(EntityDeathEvent.class,entityDeathEvent -> {
            if(!entityDeathEvent.getEntity().equals(entity))
                return;
            onDeath(entityDeathEvent);
        });
        entity.addEventCallback(EntityAttackEvent.class,entityAttackEvent -> {
            if(!entityAttackEvent.getEntity().equals(entity))
                return;
            onAttack(entityAttackEvent);
        });
        entity.addEventCallback(EntityDamageEvent.class, entityDamageEvent -> {
            if(!entityDamageEvent.getEntity().equals(entity))
                return;
            onDamage(entityDamageEvent);
        });
        entity.addEventCallback(EntityFireEvent.class, entityFireEvent -> {
            if(!entityFireEvent.getEntity().equals(entity))
                return;
            onFire(entityFireEvent);
        });
        entity.addEventCallback(EntityTickEvent.class, entityTickEvent -> {
            if(!entityTickEvent.getEntity().equals(entity))
                return;
            onTick(entityTickEvent);
        });
        entity.addEventCallback(EntityVelocityEvent.class, entityVelocityEvent -> {
            if(!entityVelocityEvent.getEntity().equals(entity))
                return;
            onVelocity(entityVelocityEvent);
        });
        entity.addEventCallback(EntityItemMergeEvent.class, entityItemMergeEvent -> {
            if(!entityItemMergeEvent.getEntity().equals(entity))
                return;
            onItemMerge(entityItemMergeEvent);
        });
    }

    protected abstract EntityCreature setupEntity(Position spawnPosition);
    protected abstract void setCustomData(EntityCreature entityCreature);
    protected abstract void onInteract(PlayerEntityInteractEvent playerEntityInteractEvent);
    protected abstract void onSpawn(EntitySpawnEvent entitySpawnEvent);
    protected abstract void onDeath(EntityDeathEvent entityDeathEvent);
    protected abstract void onAttack(EntityAttackEvent entityAttackEvent);
    protected abstract void onDamage(EntityDamageEvent entityDamageEvent);
    protected abstract void onFire(EntityFireEvent entityFireEvent);
    protected abstract void onTick(EntityTickEvent entityTickEvent);
    protected abstract void onVelocity(EntityVelocityEvent entityVelocityEvent);
    protected abstract void onItemMerge(EntityItemMergeEvent entityItemMergeEvent);

    public abstract int getCustomID();
}
