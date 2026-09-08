//? if <1.20.4 || >=1.21 {
package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.sounds.SoundEvent;

public class SoundsDataGenerator implements IDataGenerator {
    public static JsonObject generateSound(SoundEvent soundEvent) {
        JsonObject soundDesc = new JsonObject();

        //? if <1.19.2 {
        /*soundDesc.addProperty("id", DGU.<SoundEvent>registry("sound_event").getId(soundEvent));
        *///?} else {
        soundDesc.addProperty("id", DGU.<SoundEvent>registry("sound_event").getId(soundEvent) + 1); // the plus 1 is required for 1.19.2+ due to Mojang using 0 in the packet to say that you should read a string id instead.
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
        DGU.<SoundEvent>registry("sound_event").forEach(sound -> resultsArray.add(generateSound(sound)));
        return resultsArray;
    }
}
//?}
