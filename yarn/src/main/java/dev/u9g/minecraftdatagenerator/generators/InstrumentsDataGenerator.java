package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
//? if <1.8.9 {
/*import net.minecraft.block.material.Material;
*///?} else if >=1.8.9 <1.13 {
/*import dev.u9g.minecraftdatagenerator.mixin.NoteBlockAccessor;
*///?}
//? if >=1.9.4 <1.13 {
/*import net.minecraft.sound.Sound;
*///?}

import java.util.Objects;
//? if >=1.13 {
import net.minecraft.block.enums.Instrument;
//?}

public class InstrumentsDataGenerator implements IDataGenerator {
    //? if <1.8.9 {
    /*public static Material currentMaterial = null;
    public static int LAST_WORLD_BLOCKDATA = -1;

    *///?}
    @Override
    public String getDataName() {
        return "instruments";
    }

    @Override
    public JsonElement generateDataJson() {
        JsonArray array = new JsonArray();
        //? if <1.8.9 {
        /*// copied from NoteBlock#syncedBlockAction
        array.add(makeInstrumentObj(0, "harp"));
        array.add(makeInstrumentObj(1, "bd"));
        array.add(makeInstrumentObj(2, "snare"));
        array.add(makeInstrumentObj(3, "hat"));
        array.add(makeInstrumentObj(4, "bassattack"));
        *///?} else if >=1.8.9 <1.13 {
        /*int i = 0;
        *///?}
        //? if >=1.8.9 <1.9.4 {
        /*for (String soundName : Objects.requireNonNull(NoteBlockAccessor.TUNES())) {
        *///?} else if >=1.9.4 <1.13 {
        /*for (Sound sound : Objects.requireNonNull(NoteBlockAccessor.TUNES())) {
        *///?} else if >=1.13 {
        for (Instrument instrument : Instrument.values()) {
        //?}
            //? if >=1.8.9 {
            JsonObject object = new JsonObject();
            //?}
            //? if >=1.8.9 <1.13 {
            /*object.addProperty("id", i++);
            *///?}
            //? if >=1.8.9 <1.9.4 {
            /*object.addProperty("name", soundName);
            *///?} else if >=1.9.4 <1.13 {
            /*object.addProperty("name", sound.getId().getPath().split("\\.")[2]);
            *///?} else if >=1.13 {
            object.addProperty("id", instrument.ordinal());
            //?}
            //? if >=1.13 <1.14 {
            /*object.addProperty("name", instrument.asString());
            *///?} else if >=1.14 {
            object.addProperty("name", instrument.toSnakeCase());
            //?}
            //? if >=1.8.9 {
            array.add(object);
        }
            //?}
        return array;
    }
    //? if <1.8.9 {

    /*private JsonObject makeInstrumentObj(int i, String s) {
        JsonObject obj = new JsonObject();
        obj.add("id", new JsonPrimitive(i));
        obj.add("name", new JsonPrimitive(s));
        return obj;
    }
    *///?}
}
