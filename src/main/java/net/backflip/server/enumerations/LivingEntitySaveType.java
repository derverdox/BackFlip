package net.backflip.server.enumerations;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.type.ambient.EntityBat;
import net.minestom.server.entity.type.animal.*;
import net.minestom.server.entity.type.monster.EntityBlaze;
import net.minestom.server.entity.type.monster.EntityCaveSpider;
import net.minestom.server.entity.type.monster.EntityCreeper;
import net.minestom.server.entity.type.monster.EntityGuardian;

public enum LivingEntitySaveType {

    BAT(0, EntityBat.class),
    BEE(1, EntityBee.class),
    BLAZE(2, EntityBlaze.class),
    CAT(3, EntityCat.class),
    CAVE_SPIDER(4, EntityCaveSpider.class),
    CHICKEN(4, EntityChicken.class),
    COW(4, EntityCow.class),

    CREEPER(4, EntityCreeper.class),
    DOLPHIN(5, EntityDolphin.class),
    // DONKEY
    ELDER_GUARDIAN(5, EntityGuardian.class),
    // EnderDragon
    // Enderman


    ;
    private final int id;
    private final Class<? extends EntityCreature> entityClass;

    LivingEntitySaveType(int id, Class<? extends EntityCreature> entityClass){
        this.id = id;
        this.entityClass = entityClass;
    }

    public int getId() {
        return id;
    }

    public Class<? extends EntityCreature> getEntityClass() {
        return entityClass;
    }
}
