package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.17 {
/*import dev.u9g.minecraftdatagenerator.mixin.StatusEffectAccessor;
*///?}
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EffectsDataGenerator implements IDataGenerator {
    public static JsonObject generateEffect(Registry<MobEffect> registry, MobEffect mobEffect) {
        JsonObject effectDesc = new JsonObject();
        var registryKey = registry.getKey(mobEffect);

        effectDesc.addProperty("id", registry.getId(mobEffect));
        //? if <1.21.5 {
        /*if (mobEffect == MobEffects.UNLUCK) {
        *///?} else {
        if (mobEffect == MobEffects.UNLUCK.value()) {
        //?}
            effectDesc.addProperty("name", "BadLuck");
            effectDesc.addProperty("displayName", "Bad Luck");
        } else {
            effectDesc.addProperty("name", Arrays.stream(registryKey.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining()));
            effectDesc.addProperty("displayName", DGU.translateText(mobEffect.getDescriptionId()));
        }

        //? if <1.17 {
        /*effectDesc.addProperty("type", ((StatusEffectAccessor) mobEffect).type() == MobEffectCategory.BENEFICIAL ? "good" : "bad");
        *///?} else {
        effectDesc.addProperty("type", mobEffect.isBeneficial() ? "good" : "bad");
        //?}
        return effectDesc;
    }

    @Override
    public String getDataName() {
        return "effects";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        Registry<MobEffect> mobEffectRegistry = DGU.registry("mob_effect");
        mobEffectRegistry.forEach(effect -> resultsArray.add(generateEffect(mobEffectRegistry, effect)));
        return resultsArray;
    }
}
