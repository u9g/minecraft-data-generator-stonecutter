//? if <=1.18 || >=1.19 {
package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
//? if <1.16 {
/*import dev.u9g.minecraftdatagenerator.mixin.LanguageAccessor;
*///?}
//? if <1.9.4 {
/*import net.minecraft.util.Language;
*///?} else if >=1.9.4 <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?} else if >=1.13 <1.16 {
/*import net.minecraft.util.Language;
*///?}
//? if <1.16 {

/*import java.util.Map;
*///?} else if >=1.16 <1.17 {
/*import dev.u9g.minecraftdatagenerator.mixin.ClampedEntityAttributeAccessor;
*///?} else if >=1.20 {
import dev.u9g.minecraftdatagenerator.util.DGU;
//?}
//? if >=1.17 {
import net.minecraft.entity.attribute.ClampedEntityAttribute;
//?}
//? if >=1.16 {
import net.minecraft.entity.attribute.EntityAttribute;
//?}
//? if >=1.16 <1.20 {
/*import net.minecraft.util.registry.Registry;
*///?} else if >=1.20 {
import net.minecraft.registry.RegistryKeys;
//?}
//? if >=1.21 {

import java.util.Objects;
//?}

public class AttributesDataGenerator implements IDataGenerator {
    @Override
    public String getDataName() {
        return "attributes";
    }

    @Override
    public JsonElement generateDataJson() {
        //? if <1.9.4 {
        /*Language lang = new Language();
        Map<String, String> translations = ((LanguageAccessor) lang).translations();
        *///?} else if >=1.9.4 <1.13 {
        /*Map<String, String> translations = ((LanguageAccessor) Registries.LANGUAGE).translations();
        *///?} else if >=1.13 <1.16 {
        /*Map<String, String> translations = ((LanguageAccessor) new Language()).translations();
        *///?}
        JsonArray arr = new JsonArray();
        //? if <1.16 {
        /*for (Map.Entry<String, String> translation : translations.entrySet()) {
            String key = translation.getKey();
            if (!key.startsWith("attribute.name.")) continue;
        *///?} else if >=1.16 <1.20 {
        /*for (EntityAttribute attribute : Registry.ATTRIBUTE) {
        *///?} else if >=1.20 <1.21.3 {
        /*var registry = DGU.getWorld().getRegistryManager().get(RegistryKeys.ATTRIBUTE);
        *///?} else {
        var registry = DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ATTRIBUTE);
        //?}
        //? if >=1.20 {
        for (EntityAttribute attribute : registry) {
        //?}
            JsonObject obj = new JsonObject();
            //? if <1.16 {
            /*key = key.replace("attribute.name.", "");
            obj.addProperty("name", key.split("\\.")[1]);
            obj.addProperty("resource", key);
            *///?} else if >=1.16 <1.20 {
            /*obj.addProperty("name", Registry.ATTRIBUTE.getId(attribute).getPath().split("\\.")[1]);
            obj.addProperty("resource", Registry.ATTRIBUTE.getId(attribute).getPath());
            *///?}
            //? if >=1.16 <1.17 {
            /*obj.addProperty("min", ((ClampedEntityAttributeAccessor) attribute).getMinValue());
            obj.addProperty("max", ((ClampedEntityAttributeAccessor) attribute).getMaxValue());
            *///?} else if >=1.20 <1.21 {
            /*obj.addProperty("name", registry.getId(attribute).getPath().split("\\.")[1]);
            obj.addProperty("resource", registry.getId(attribute).getPath());
            *///?} else if >=1.21 <1.21.3 {
            /*String[] parts = Objects.requireNonNull(registry.getId(attribute)).getPath().split("\\.");
            String name = parts[0].equals("generic") ? parts[1] : parts[0] + "_" + parts[1];
            *///?} else if >=1.21.3 {
            String name = Objects.requireNonNull(registry.getId(attribute)).getPath();
            //?}
            //? if >=1.21 {
            while(name.contains("_")) {
                name = name.replaceFirst("_[a-z]", String.valueOf(Character.toUpperCase(name.charAt(name.indexOf("_") + 1))));
            }
            obj.addProperty("name", name);
            obj.addProperty("resource", Objects.requireNonNull(registry.getId(attribute)).toString());
            //?}
            //? if >=1.17 {
            obj.addProperty("min", ((ClampedEntityAttribute) attribute).getMinValue());
            obj.addProperty("max", ((ClampedEntityAttribute) attribute).getMaxValue());
            //?}
            //? if >=1.16 {
            obj.addProperty("default", attribute.getDefaultValue());
            //?}
            arr.add(obj);
        }
        return arr;
    }
}
//?}
