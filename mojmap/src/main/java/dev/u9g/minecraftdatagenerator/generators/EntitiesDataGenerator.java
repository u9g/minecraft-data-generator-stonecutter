package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.FieldHelper;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
//? if <1.17 {
/*import net.minecraft.world.entity.AgableMob;
*///?} else {
import net.minecraft.world.entity.AgeableMob;
//?}
import net.minecraft.world.entity.Entity;
//? if >=1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?}
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
//? if <1.21.11 {
/*import net.minecraft.world.entity.animal.WaterAnimal;
*///?} else {
import net.minecraft.world.entity.animal.fish.WaterAnimal;
//?}
import net.minecraft.world.entity.monster.Monster;
//? if <1.16 {
/*import net.minecraft.world.entity.projectile.AbstractArrow;
*///?}
import net.minecraft.world.entity.projectile.Projectile;
//? if <1.17 {
/*import org.jetbrains.annotations.NotNull;
*///?}

import java.lang.reflect.ParameterizedType;

public class EntitiesDataGenerator implements IDataGenerator {
    public static JsonObject generateEntity(Registry<EntityType<?>> entityRegistry, EntityType<?> entityType) {
        JsonObject entityDesc = new JsonObject();
        var registryKey = entityRegistry.getKey(entityType);
        int entityRawId = entityRegistry.getId(entityType);

        entityDesc.addProperty("id", entityRawId);
        entityDesc.addProperty("internalId", entityRawId);
        entityDesc.addProperty("name", registryKey.getPath());

        entityDesc.addProperty("displayName", DGU.translateText(entityType.getDescriptionId()));
        //? if <1.20.5 {
        /*entityDesc.addProperty("width", entityType.getDimensions().width);
        entityDesc.addProperty("height", entityType.getDimensions().height);
        *///?} else {
        entityDesc.addProperty("width", entityType.getDimensions().width());
        entityDesc.addProperty("height", entityType.getDimensions().height());
        //?}

        //? if <1.16 {
        /*Entity entityObject = entityType.create(DGU.getWorld());
        *///?} else if >=1.17 {
        String entityTypeString = "UNKNOWN";
        //?}
        //? if >=1.16 {
        MinecraftServer minecraftServer = DGU.getCurrentlyRunningServer();
        //?}
        //? if =1.16 {
        /*Entity entityObject = entityType.create(minecraftServer.overworld());
        *///?}
        //? if <1.17 {
        /*String entityTypeString = entityObject != null ? getEntityTypeForClass(entityObject.getClass()) : "player";
        *///?} else {

        if (minecraftServer != null) {
        //?}
            //? if >=1.17 <1.21.3 {
            /*Entity entityObject = entityType.create(minecraftServer.overworld());
            *///?}
            //? if >=1.17 <1.20.4 {
            /*entityTypeString = entityObject != null ? getEntityTypeForClass(entityObject.getClass()) : "player";
            *///?} else if >=1.21.3 {
            Entity entityObject = entityType.create(minecraftServer.overworld(), EntitySpawnReason.NATURAL);
            //?}
            //? if >=1.20.4 {
            entityTypeString = entityObject != null ? getEntityTypeForClass(entityObject.getClass()) : "unknown";
            //?}
        //? if >=1.17 {
        }
        //?}
        //? if >=1.20.4 {
        if (entityType == EntityType.PLAYER) {
            entityTypeString = "player";
        }

        //?}
        entityDesc.addProperty("type", entityTypeString);
        entityDesc.addProperty("category", getCategoryFrom(entityType));

        return entityDesc;
    }

    //? if <1.17 {
    /*private static String getCategoryFrom(@NotNull EntityType<?> entityType) {
    *///?} else {
    private static String getCategoryFrom(EntityType<?> entityType) {
    //?}
        //? if <1.18 {
        /*ParameterizedType entityTypeClass = (ParameterizedType) FieldHelper.findStaticFieldWithValue(EntityType.class, entityType).getGenericType();
        Class<?> entityClass = (Class<?>) entityTypeClass.getActualTypeArguments()[0];
        String packageName = entityClass.getPackageName();
        String className = entityClass.getSimpleName();
        *///?} else if =1.18 {
        /*if (entityType == EntityType.PLAYER) return "UNKNOWN"; // fail early for player entities
        Entity entity = EntityType.create(DGU.<EntityType<?>>registry("entity_type").getId(entityType), DGU.getWorld());
        *///?} else if >1.18 <1.19 {
        /*if (entityType == EntityType.PLAYER) return "other"; // fail early for player entities
        *///?} else {
        if (entityType == EntityType.PLAYER) return "UNKNOWN";
        //?}
        //? if >1.18 <1.21.3 {
        /*Entity entity = entityType.create(DGU.getWorld());
        *///?} else if >=1.21.3 {
        Entity entity = entityType.create(DGU.getWorld(), EntitySpawnReason.NATURAL);
        //?}
        //? if >=1.18 {
        if (entity == null)
            throw new IllegalStateException("Entity was null after trying to create a: " + DGU.translateText(entityType.getDescriptionId()));
        entity.discard();
        String packageName = entity.getClass().getPackageName();
        //?}
        //? if >=1.18 <1.21.5 {
        /*String className = entity.getClass().getSimpleName();
        *///?}


        String family = packageName.replaceFirst("^net\\.minecraft\\.world\\.entity\\.?", "").split("\\.")[0];
        return switch (family) {
            case "decoration" -> "Immobile";
            //? if <1.21.5 {
            /*case "boss", "monster" -> className.equals("EndCrystal") ? "Immobile"
                    : className.equals("Strider") ? "Passive mobs" : "Hostile mobs";
            case "projectile", "fishing" -> className.equals("EvokerFangs") ? "Hostile mobs"
            *///?}
                    //? if <1.16 {
                    /*: className.equals("FireworkRocketEntity") ? "other"
                    *///?}
                    //? if <1.18 {
                    /*: className.equals("EyeOfEnder") ? "other" : "Projectiles";
                    *///?} else if >=1.18 <1.21.5 {
                    /*: className.equals("EyeOfEnder") ? "UNKNOWN" : "Projectiles";
                    *///?}
            //? if <1.21.5 {
            /*case "animal", "ambient", "npc" -> className.equals("SkeletonHorse") || className.equals("ZombieHorse") ? "Hostile mobs" : "Passive mobs";
            *///?} else {
            case "boss", "monster" -> "Hostile mobs";
            case "projectile" -> "Projectiles";
            case "animal" -> "Passive mobs";
            //?}
            case "vehicle" -> "Vehicles";
            //? if <1.21.5 {
            /*case "player", "item", "global", "" -> className.equals("Interaction") || className.endsWith("Display") ? "Immobile"
            *///?}
                    //? if <1.18 {
                    /*: className.equals("GlowSquid") ? "Passive mobs" : "other";
                    *///?} else if >=1.18 <1.21.5 {
                    /*: className.equals("GlowSquid") ? "Passive mobs" : "UNKNOWN";
                    *///?}
            //? if <1.21.5 {
            /*default -> throw new IllegalStateException("Unexpected entity type: " + packageName);
            *///?} else {
            default -> "UNKNOWN";
            //?}
        };
    }

    //Honestly, both "type" and "category" fields in the schema and examples do not contain any useful information
    //Since category is optional, I will just leave it out, and for type I will assume general entity classification
    //by the Entity class hierarchy (which has some weirdness too by the way)
    private static String getEntityTypeForClass(Class<? extends Entity> entityClass) {
        //Top-level classifications
        if (WaterAnimal.class.isAssignableFrom(entityClass)) {
            return "water_creature";
        }
        if (Animal.class.isAssignableFrom(entityClass)) {
            return "animal";
        }
        if (Monster.class.isAssignableFrom(entityClass)) {
            return "hostile";
        }
        if (AmbientCreature.class.isAssignableFrom(entityClass)) {
            return "ambient";
        }

        //Second level classifications. PathAwareEntity is not included because it
        //doesn't really make much sense to categorize by it
        //? if <1.17 {
        /*if (AgableMob.class.isAssignableFrom(entityClass)) {
        *///?} else {
        if (AgeableMob.class.isAssignableFrom(entityClass)) {
        //?}
            return "passive";
        }
        if (Mob.class.isAssignableFrom(entityClass)) {
            return "mob";
        }

        //Other classifications only include living entities and projectiles. everything else is categorized as other
        if (LivingEntity.class.isAssignableFrom(entityClass)) {
            return "living";
        }
        //? if <1.16 {
        /*if (AbstractArrow.class.isAssignableFrom(entityClass)) {
        *///?} else {
        if (Projectile.class.isAssignableFrom(entityClass)) {
        //?}
            return "projectile";
        }
        return "other";
    }

    @Override
    public String getDataName() {
        return "entities";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultArray = new JsonArray();
        Registry<EntityType<?>> entityTypeRegistry = DGU.registry("entity_type");
        entityTypeRegistry.forEach(entity -> resultArray.add(generateEntity(entityTypeRegistry, entity)));
        return resultArray;
    }
}
