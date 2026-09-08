//? if >=1.8.9 {
package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.14 {
/*import net.minecraft.client.particle.ParticleType;
*///?} else {
import net.minecraft.particle.ParticleType;
//?}
//? if >=1.13 {
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;
//?}

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
        *///?} else if >=1.14 {
        Identifier registryKey = registry.getId(particleType);
        //?}

        //? if <1.13 {
        /*effectDesc.addProperty("id", id);
        effectDesc.addProperty("name", particleType.getName());
        *///?} else if >=1.13 <1.14 {
        /*effectDesc.addProperty("id", Registry.PARTICLE_TYPE.getRawId(particleType));
        *///?} else {
        effectDesc.addProperty("id", registry.getRawId(particleType));
        //?}
        //? if >=1.13 {
        effectDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
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
        *///?} else {
        Registry<ParticleType<?>> particleTypeRegistry = Registry.PARTICLE_TYPE;
        particleTypeRegistry.forEach(particleType -> resultsArray.add(generateParticleType(particleTypeRegistry, particleType)));
        //?}
        return resultsArray;
    }
}
//?}
