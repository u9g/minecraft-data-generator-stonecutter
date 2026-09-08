//? if >=1.9.4 {
package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.14 {
/*import net.minecraft.sound.Sound;
*///?} else {
import net.minecraft.sound.SoundEvent;
//?}
import net.minecraft.util.registry.Registry;

public class SoundsDataGenerator implements IDataGenerator {
    //? if <1.13 {
    /*public static JsonObject generateSound(Sound soundEvent) {
    *///?} else if =1.13 {
    /*public static JsonObject generateSound(Registry<Sound> registry, Sound soundEvent) {
    *///?} else {
    public static JsonObject generateSound(SoundEvent soundEvent) {
    //?}
        JsonObject soundDesc = new JsonObject();

        //? if <1.13 {
        /*soundDesc.addProperty("id", Sound.REGISTRY.getRawId(soundEvent));
        *///?} else if =1.13 {
        /*soundDesc.addProperty("id", registry.getRawId(soundEvent));
        *///?} else {
        soundDesc.addProperty("id", Registry.SOUND_EVENT.getRawId(soundEvent));
        //?}
        soundDesc.addProperty("name", soundEvent.getId().getPath());

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
        *///?} else if =1.13 {
        /*Registry<Sound> soundEventRegistry = Registry.SOUND_EVENT;
        soundEventRegistry.forEach(sound -> resultsArray.add(generateSound(soundEventRegistry, (Sound) sound)));
        *///?} else {
        Registry.SOUND_EVENT.forEach(sound -> resultsArray.add(generateSound(sound)));
        //?}
        return resultsArray;
    }
}
//?}
