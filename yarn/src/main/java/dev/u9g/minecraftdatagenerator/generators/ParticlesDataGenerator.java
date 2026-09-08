//? if >=1.8.9 {
package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.14 {
/*import net.minecraft.client.particle.ParticleType;
*///?} else if >=1.20 {
import dev.u9g.minecraftdatagenerator.util.DGU;
//?}
//? if >=1.14 {
import net.minecraft.particle.ParticleType;
//?}
//? if >=1.20 {
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
//?}
//? if >=1.13 {
import net.minecraft.util.Identifier;
//?}
//? if >=1.13 <1.20 {
/*import net.minecraft.util.registry.Registry;
*///?}

//? if >=1.13 <1.16 {
/*import java.util.Objects;
*///?}

public class ParticlesDataGenerator implements IDataGenerator {
    //? if <1.13 {
    /*public static JsonObject generateParticleType(int id, ParticleType particleType) {
    *///?} else if >=1.13 <1.14 {
    /*public static JsonObject generateParticleType(ParticleType<?> particleType) {
    *///?} else {
    public static JsonObject generateParticleType(Registry<ParticleType<?>> registry, ParticleType<?> particleType) {
    //?}
        JsonObject effectDesc = new JsonObject();
        //? if >=1.13 <1.14 {
        /*Identifier registryKey = Registry.PARTICLE_TYPE.getId(particleType);
        *///?} else if >=1.14 <1.16 {
        /*Identifier registryKey = registry.getId(particleType);
        *///?} else if >=1.16 {
        Identifier registryKey = registry.getKey(particleType).orElseThrow().getValue();
        //?}

        //? if <1.13 {
        /*effectDesc.addProperty("id", id);
        effectDesc.addProperty("name", particleType.getName());
        *///?} else if >=1.13 <1.14 {
        /*effectDesc.addProperty("id", Registry.PARTICLE_TYPE.getRawId(particleType));
        *///?} else {
        effectDesc.addProperty("id", registry.getRawId(particleType));
        //?}
        //? if >=1.13 <1.16 {
        /*effectDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        *///?} else if >=1.16 {
        effectDesc.addProperty("name", registryKey.getPath());
        //?}
        return effectDesc;
    }

    @Override
    public String getDataName() {
        return "particles";
    }

    //? if >=1.13 <1.14 {
    /*@SuppressWarnings("unchecked")
    *///?}
    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        //? if <1.13 {
        /*int i = 0;
        for (ParticleType particleType : ParticleType.values()) {
            resultsArray.add(generateParticleType(i++, particleType));
        }
        *///?} else if >=1.13 <1.14 {
        /*Registry.PARTICLE_TYPE.forEach(particleType ->
                resultsArray.add(generateParticleType((ParticleType<?>) particleType)));
        *///?} else if >=1.14 <1.20 {
        /*Registry<ParticleType<?>> particleTypeRegistry = Registry.PARTICLE_TYPE;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<ParticleType<?>> particleTypeRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.PARTICLE_TYPE);
        *///?} else {
        Registry<ParticleType<?>> particleTypeRegistry = DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.PARTICLE_TYPE);
        //?}
        //? if >=1.14 {
        particleTypeRegistry.forEach(particleType -> resultsArray.add(generateParticleType(particleTypeRegistry, particleType)));
        //?}
        return resultsArray;
    }
}
//?}
