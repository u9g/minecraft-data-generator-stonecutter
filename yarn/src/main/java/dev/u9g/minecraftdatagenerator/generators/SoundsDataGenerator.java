//? if (>=1.9.4 <1.20.4) || >=1.21 {
package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.14 {
/*import net.minecraft.sound.Sound;
*///?} else if >=1.20 {
import net.minecraft.registry.Registries;
//?}
//? if >=1.14 {
import net.minecraft.sound.SoundEvent;
//?}
//? if >=1.13 <1.20 {
/*import net.minecraft.util.registry.Registry;
*///?}

public class SoundsDataGenerator implements IDataGenerator {
    //? if <1.13 {
    /*public static JsonObject generateSound(Sound soundEvent) {
    *///?} else if >=1.13 <1.14 {
    /*public static JsonObject generateSound(Registry<Sound> registry, Sound soundEvent) {
    *///?} else {
    public static JsonObject generateSound(SoundEvent soundEvent) {
    //?}
        JsonObject soundDesc = new JsonObject();

        //? if <1.13 {
        /*soundDesc.addProperty("id", Sound.REGISTRY.getRawId(soundEvent));
        *///?} else if >=1.13 <1.14 {
        /*soundDesc.addProperty("id", registry.getRawId(soundEvent));
        *///?} else if >=1.14 <1.19.2 {
        /*soundDesc.addProperty("id", Registry.SOUND_EVENT.getRawId(soundEvent));
        *///?} else if >=1.19.2 <1.20 {
        /*soundDesc.addProperty("id", Registry.SOUND_EVENT.getRawId(soundEvent) + 1); // the plus 1 is required for 1.19.2+ due to Mojang using 0 in the packet to say that you should read a string id instead.
        *///?} else {
        soundDesc.addProperty("id", Registries.SOUND_EVENT.getRawId(soundEvent) + 1); // the plus 1 is required for 1.19.2+ due to Mojang using 0 in the packet to say that you should read a string id instead.
        //?}
        //? if <1.21.3 {
        /*soundDesc.addProperty("name", soundEvent.getId().getPath());
        *///?} else {
        soundDesc.addProperty("name", soundEvent.id().getPath());
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
        //? if <1.13 {
        /*Sound.REGISTRY.forEach(sound -> resultsArray.add(generateSound(sound)));
        *///?} else if >=1.13 <1.14 {
        /*Registry<Sound> soundEventRegistry = Registry.SOUND_EVENT;
        soundEventRegistry.forEach(sound -> resultsArray.add(generateSound(soundEventRegistry, (Sound) sound)));
        *///?} else if >=1.14 <1.20 {
        /*Registry.SOUND_EVENT.forEach(sound -> resultsArray.add(generateSound(sound)));
        *///?} else {
        Registries.SOUND_EVENT.forEach(sound -> resultsArray.add(generateSound(sound)));
        //?}
        return resultsArray;
    }
}
//?}
