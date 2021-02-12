package net.backflip.server.entity;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

public class EntityCreatureImpl extends EntityCreature {
    public EntityCreatureImpl(@NotNull EntityType entityType, @NotNull Position spawnPosition, double boundingBox_x, double boundingBox_y, double boundingBox_z) {
        super(entityType, spawnPosition);
        this.setBoundingBox(boundingBox_x,boundingBox_y,boundingBox_z);
    }
}
