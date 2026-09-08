package dev.u9g.minecraftdatagenerator.generators;

//? if >=1.13 {
import com.google.gson.Gson;
//?}
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.mixin.LanguageAccessor;
import dev.u9g.minecraftdatagenerator.util.Registries;
*///?}

//? if <1.13 {
/*import java.util.Map;
*///?} else {
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
//?}

public class LanguageDataGenerator implements IDataGenerator {
    @Override
    public String getDataName() {
        return "language";
    }

    @Override
    public JsonElement generateDataJson() {
        try {
            //? if <1.13 {
            /*JsonObject obj = new JsonObject();
            Map<String, String> translations = ((LanguageAccessor) Registries.LANGUAGE).translations();
            for (Map.Entry<String, String> entry : translations.entrySet()) {
                obj.addProperty(entry.getKey(), entry.getValue());
            }
            return obj;
            *///?} else {
            InputStream inputStream = Objects.requireNonNull(this.getClass().getResourceAsStream("/assets/minecraft/lang/en_us.json"));
            return new Gson().fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);
            //?}
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate language file", e);
        }
    }
}
