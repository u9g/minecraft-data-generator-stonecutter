//? if <1.20.4 || >=1.21 {
package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if >=1.20 {
import net.minecraft.core.registries.BuiltInRegistries;
//?}
//? if >=1.21.5 {
import net.minecraft.core.registries.Registries;
//?}
import net.minecraft.sounds.SoundEvent;

public class SoundsDataGenerator implements IDataGenerator {
    public static JsonObject generateSound(SoundEvent soundEvent) {
        JsonObject soundDesc = new JsonObject();

        //? if <1.19.2 {
        /*soundDesc.addProperty("id", Registry.SOUND_EVENT.getId(soundEvent));
        *///?} else if >=1.19.2 <1.20 {
        /*soundDesc.addProperty("id", Registry.SOUND_EVENT.getId(soundEvent) + 1); // the plus 1 is required for 1.19.2+ due to Mojang using 0 in the packet to say that you should read a string id instead.
        *///?} else {
        soundDesc.addProperty("id", BuiltInRegistries.SOUND_EVENT.getId(soundEvent) + 1); // the plus 1 is required for 1.19.2+ due to Mojang using 0 in the packet to say that you should read a string id instead.
        //?}
        //? if <1.21.3 {
        /*soundDesc.addProperty("name", soundEvent.getLocation().getPath());
        *///?} else {
        soundDesc.addProperty("name", soundEvent.location().getPath());
        //?}

        return soundDesc;
    }

    @Override
    public String getDataName() {
        return "sounds";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        //? if <1.20 {
        /*Registry.SOUND_EVENT.forEach(sound -> resultsArray.add(generateSound(sound)));
        *///?} else if >=1.20 <1.21.5 {
        /*BuiltInRegistries.SOUND_EVENT.forEach(sound -> resultsArray.add(generateSound(sound)));
        *///?} else {
        Registry<SoundEvent> soundEventRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.SOUND_EVENT);
        soundEventRegistry.forEach(sound -> resultsArray.add(generateSound(sound)));
        //?}
        return resultsArray;
    }
}
//?}
