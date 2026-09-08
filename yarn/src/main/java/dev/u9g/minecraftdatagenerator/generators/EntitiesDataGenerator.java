package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.11.2 {
/*import dev.u9g.minecraftdatagenerator.mixin.EntityTypeAccessor;
*///?}
import com.google.gson.reflect.TypeToken;
import dev.u9g.minecraftdatagenerator.FieldHelper;
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?}
import net.minecraft.block.Blocks;
//? if <1.11.2 {
/*import net.minecraft.entity.*;
*///?}
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
//? if >=1.14 {
import net.minecraft.entity.WaterCreatureEntity;
//?}
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
//? if <1.14 {
/*import net.minecraft.entity.mob.WaterCreatureEntity;
*///?}
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
//? if <1.13 {
/*import net.minecraft.entity.projectile.FishingBobberEntity;
*///?}
import net.minecraft.entity.projectile.Projectile;
import net.minecraft.item.ItemStack;
//? if >=1.14 {
import net.minecraft.entity.projectile.ProjectileEntity;
//?}
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
//? if <1.14 {
/*import org.jetbrains.annotations.Nullable;
*///?}

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public class EntitiesDataGenerator implements IDataGenerator {
    //? if <1.13 {
    /*public static JsonObject generateEntity(Class<? extends Entity> entityClass) {
    *///?} else {
    public static JsonObject generateEntity(Registry<EntityType<?>> entityRegistry, EntityType<?> entityType) {
    //?}
        JsonObject entityDesc = new JsonObject();
        //? if <1.8.9 {
        /*String registryKey = Registries.ENTITY_TYPES.getId(entityClass);
        *///?} else if >=1.8.9 <1.13 {
        /*Identifier registryKey = Registries.ENTITY_TYPES.getIdentifier(entityClass);
        *///?}
        //? if <1.13 {
        /*int entityRawId = Registries.ENTITY_TYPES.getRawId(entityClass);
        @Nullable Entity entity = makeEntity(entityClass);
        // FIXME: ENTITY ID IS WRONG
        int id = entityId(entity);
        entityDesc.addProperty("id", id);
        entityDesc.addProperty("internalId", id);
        *///?}
        //? if <1.8.9 {
        /*entityDesc.addProperty("name", Objects.requireNonNull(registryKey));
        *///?} else if >=1.13 {
        Identifier registryKey = entityRegistry.getId(entityType);
        int entityRawId = entityRegistry.getRawId(entityType);
        //?}
        //? if =1.13 {
        /*Class<? extends Entity> entityClass = getEntityClass(entityType);
        @Nullable Entity entity = makeEntity(entityType);
        *///?}
        //? if >=1.13 {

        entityDesc.addProperty("id", entityRawId);
        entityDesc.addProperty("internalId", entityRawId);
        //?}
        //? if >=1.8.9 {
        entityDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        //?}
        //? if =1.8.9 {
        /*if (entity instanceof ItemEntity itemEntity) {
            // Same as 1.9.4
            itemEntity.setItemStack(new ItemStack(Blocks.STONE));
        }
        *///?}
        //? if <1.9.4 {
        /*String displayName = entity != null ? entity.getTranslationKey() : null;
        *///?} else if >=1.9.4 <1.11.2 {
        /*String displayName = entity != null ? DGU.translateText(entity.getTranslationKey()) : null;
        *///?}
        //? if <1.11.2 {
        /*if (displayName != null && !displayName.startsWith("entity.")) {
            entityDesc.addProperty("displayName", displayName);
        }
        *///?}

        //? if >=1.11.2 <1.13 {
        /*if (entity != null) entityDesc.addProperty("displayName", DGU.translateText(entity.getTranslationKey()));
        *///?} else if >=1.13 {
        entityDesc.addProperty("displayName", DGU.translateText(entityType.getTranslationKey()));
        //?}
        //? if <1.14 {
        /*entityDesc.addProperty("width", entity == null ? 0 : entity.width);
        entityDesc.addProperty("height", entity == null ? 0 : entity.height);
        *///?} else {
        entityDesc.addProperty("width", entityType.getWidth());
        entityDesc.addProperty("height", entityType.getHeight());
        //?}

        //? if <1.14 {
        /*String entityTypeString = getEntityTypeForClass(entityClass);
        *///?} else {
        Entity entityObject = entityType.create(DGU.getWorld());
        String entityTypeString = entityObject != null ? getEntityTypeForClass(entityObject.getClass()) : "player";
        //?}
        entityDesc.addProperty("type", entityTypeString);
        //? if <1.13 {
        /*entityDesc.addProperty("category", getCategoryFrom(entityClass));
        *///?} else {
        entityDesc.addProperty("category", getCategoryFrom(entityType));
        //?}

        return entityDesc;
    }

    //? if <1.13 {
    /*private static Entity makeEntity(Class<? extends Entity> type) {
    *///?}
        //? if <1.11.2 {
        /*String name = EntityTypeAccessor.CLASS_NAME_MAP().get(type);
        return EntityType.createInstanceFromName(name, DGU.getWorld());
        *///?} else if >=1.11.2 <1.13 {
        /*return EntityType.createInstanceFromClass(type, DGU.getWorld());
        *///?} else if =1.13 {
    /*private static Entity makeEntity(EntityType<?> type) {
        Entity entity;
        try {
            entity = type.spawn(DGU.getWorld());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return entity;
        *///?}
    //? if <1.14 {
    /*}

    *///?}
    //? if <1.13 {
    /*private static String getCategoryFrom(@NotNull Class<?> entityClass) {
        if (entityClass == PlayerEntity.class) return "other"; // fail early for player entities
        String packageName = entityClass.getPackage().getName();
    *///?} else if =1.13 {
    /*private static Class<? extends Entity> getEntityClass(EntityType<?> entityType) {
        Class<? extends Entity> entityClazz = null;
        try {
            for (Field field : EntityType.class.getFields())
                if (entityType == field.get(EntityType.class))
                    entityClazz = (Class<? extends Entity>) ((ParameterizedType) TypeToken.get(field.getGenericType()).getType()).getActualTypeArguments()[0];
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (entityClazz == null) throw new RuntimeException("Shouldn't be null...");
        return entityClazz;
    }

    *///?}
    //? if >=1.13 {
    private static String getCategoryFrom(@NotNull EntityType<?> entityType) {
    //?}
        //? if =1.13 {
        /*if (entityType == EntityType.PLAYER) return "other"; // fail early for player entities
        Class<? extends Entity> entityClazz = getEntityClass(entityType);
        String packageName = entityClazz.getPackage().getName();
        *///?}
        //? if <1.14 {
        /*return switch (packageName) {
        *///?} else {
        ParameterizedType entityTypeClass = (ParameterizedType) FieldHelper.findStaticFieldWithValue(EntityType.class, entityType).getGenericType();
        Class<?> entityClass = (Class<?>) entityTypeClass.getActualTypeArguments()[0];
        return switch (entityClass.getPackageName()) {
        //?}
            case "net.minecraft.entity.decoration", "net.minecraft.entity.decoration.painting" -> "Immobile";
            case "net.minecraft.entity.boss", "net.minecraft.entity.mob", "net.minecraft.entity.boss.dragon" ->
                    "Hostile mobs";
            case "net.minecraft.entity.projectile", "net.minecraft.entity.thrown" -> "Projectiles";
            case "net.minecraft.entity.passive" -> "Passive mobs";
            case "net.minecraft.entity.vehicle" -> "Vehicles";
            //? if <1.14 {
            /*case "net.minecraft.entity" -> "other";
            default -> throw new IllegalStateException("Unexpected entity type: " + packageName);
            *///?} else {
            case "net.minecraft.entity.player", "net.minecraft.entity" -> "other";
            default -> throw new IllegalStateException("Unexpected entity type: " + entityClass.getPackageName());
            //?}
        };
    }

    //Honestly, both "type" and "category" fields in the schema and examples do not contain any useful information
    //Since category is optional, I will just leave it out, and for type I will assume general entity classification
    //by the Entity class hierarchy (which has some weirdness too by the way)
    private static String getEntityTypeForClass(Class<? extends Entity> entityClass) {
        //Top-level classifications
        if (WaterCreatureEntity.class.isAssignableFrom(entityClass)) {
            return "water_creature";
        }
        if (AnimalEntity.class.isAssignableFrom(entityClass)) {
            return "animal";
        }
        if (HostileEntity.class.isAssignableFrom(entityClass)) {
            return "hostile";
        }
        if (AmbientEntity.class.isAssignableFrom(entityClass)) {
            return "ambient";
        }

        //Second level classifications. PathAwareEntity is not included because it
        //doesn't really make much sense to categorize by it
        //? if <1.11.2 {
        /*if (PathAwareEntity.class.isAssignableFrom(entityClass)) {
        *///?} else {
        if (PassiveEntity.class.isAssignableFrom(entityClass)) {
        //?}
            return "passive";
        }
        if (MobEntity.class.isAssignableFrom(entityClass)) {
            return "mob";
        }

        //Other classifications only include living entities and projectiles. everything else is categorized as other
        if (LivingEntity.class.isAssignableFrom(entityClass)) {
            return "living";
        }
        //? if <1.14 {
        /*if (Projectile.class.isAssignableFrom(entityClass)) {
        *///?} else {
        if (ProjectileEntity.class.isAssignableFrom(entityClass)) {
        //?}
            return "projectile";
        }
        return "other";
    }

    //? if <1.13 {
    /*private static int entityId(Entity entity) {
    *///?}
        //? if <1.8.9 {
        /*if (!DGU.getCurrentlyRunningServer().getVersion().equals("1.7")) {
            throw new IllegalStateException("These ids were gotten manually for 1.7, remake for " + DGU.getCurrentlyRunningServer().getVersion());
        *///?} else if =1.8.9 {
        /*if (!DGU.getCurrentlyRunningServer().getVersion().equals("1.8.9")) {
            throw new IllegalStateException("These ids were gotten manually for 1.8.9, remake for " + DGU.getCurrentlyRunningServer().getVersion());
        *///?} else if =1.9.4 {
        /*if (!DGU.getCurrentlyRunningServer().getVersion().equals("1.9.4")) {
            throw new IllegalStateException("These ids were gotten manually for 1.9.4, remake for " + DGU.getCurrentlyRunningServer().getVersion());
        *///?} else if =1.10.2 {
        /*if (!DGU.getCurrentlyRunningServer().getVersion().equals("1.10.2")) {
            throw new IllegalStateException("These ids were gotten manually for 1.10.2, remake for " + DGU.getCurrentlyRunningServer().getVersion());
        *///?} else if =1.11.2 {
        /*if (!DGU.getCurrentlyRunningServer().getVersion().equals("1.11.2")) {
            throw new IllegalStateException("These ids were gotten manually for 1.11.2, remake for " + DGU.getCurrentlyRunningServer().getVersion());
        *///?} else if =1.12.2 {
        /*if (!DGU.getCurrentlyRunningServer().getVersion().equals("1.12.2")) {
            throw new IllegalStateException("These ids were gotten manually for 1.12.2, remake for " + DGU.getCurrentlyRunningServer().getVersion());
        *///?}
        //? if <1.13 {
        /*}
        int rawId = Registries.ENTITY_TYPES.getRawId(entity.getClass());
        if (rawId == -1) { // see TrackedEntityInstance
            if (entity instanceof ItemEntity) {
        *///?}
                //? if <1.11.2 {
                /*return 2;
                *///?} else if >=1.11.2 <1.13 {
                /*return 1;
                *///?}
            //? if <1.13 {
            /*} else if (entity instanceof FishingBobberEntity) {
                return 90;
            } else {
            *///?}
                //? if <1.8.9 {
                /*throw new IllegalStateException("unable to find rawId for entity: " + entity.getClass().getName());
                *///?} else if =1.8.9 {
                /*throw new IllegalStateException("unable to find rawId for entity: " + entity.getEntity().getClass().getName());
                *///?} else if >=1.9.4 <1.13 {
                /*throw new IllegalStateException("unable to find rawId for entity: " + entity.getEntityName());
                *///?}
            //? if <1.13 {
            /*}
        }
        return rawId;
    }

            *///?}
    @Override
    public String getDataName() {
        return "entities";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultArray = new JsonArray();
        //? if <1.13 {
        /*for (Class<? extends Entity> entityType : Registries.ENTITY_TYPES) {
            resultArray.add(generateEntity(entityType));
        *///?} else {
        Registry<EntityType<?>> entityTypeRegistry = Registry.ENTITY_TYPE;
        //?}
        //? if =1.13 {
        /*for (EntityType<?> entityType : (Iterable<EntityType<?>>) entityTypeRegistry) {
            resultArray.add(generateEntity(entityTypeRegistry, entityType));
        *///?}
        //? if <1.14 {
        /*}
        *///?} else {
        entityTypeRegistry.forEach(entity -> resultArray.add(generateEntity(entityTypeRegistry, entity)));
        //?}
        return resultArray;
    }
}
