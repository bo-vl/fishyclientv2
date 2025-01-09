package utils.lists;

import net.minecraft.entity.passive.*;

public enum Passive {
    COW(EntityCow.class),
    SHEEP(EntitySheep.class),
    PIG(EntityPig.class),
    CHICKEN(EntityChicken.class),
    RABBIT(EntityRabbit.class),
    HORSE(EntityHorse.class);

    private final Class<? extends EntityAnimal> entityClass;

    Passive(Class<? extends EntityAnimal> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<? extends EntityAnimal> getEntityClass() {
        return entityClass;
    }
}