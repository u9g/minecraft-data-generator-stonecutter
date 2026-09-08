package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.17 {
/*import dev.u9g.minecraftdatagenerator.mixin.StatusEffectAccessor;
*///?}
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.core.Registry;
//? if >=1.20 {
import net.minecraft.core.registries.Registries;
//?}
//? if <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
*///?} else {
import net.minecraft.resources.Identifier;
//?}
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EffectsDataGenerator implements IDataGenerator {
    //? if <1.21.5 {
    /*public static JsonObject generateEffect(Registry<MobEffect> registry, MobEffect statusEffect) {
    *///?} else {
    public static JsonObject generateEffect(Registry<MobEffect> registry, MobEffect mobEffect) {
    //?}
        JsonObject effectDesc = new JsonObject();
        //? if <1.16 {
        /*ResourceLocation registryKey = registry.getKey(statusEffect);
        *///?} else if >=1.16 <1.21.5 {
        /*ResourceLocation registryKey = registry.getResourceKey(statusEffect).orElseThrow().location();
        *///?} else if >=1.21.5 <1.21.11 {
        /*ResourceLocation registryKey = registry.getKey(mobEffect);
        *///?} else {
        Identifier registryKey = registry.getKey(mobEffect);
        //?}

        //? if <1.21.5 {
        /*effectDesc.addProperty("id", registry.getId(statusEffect));
        if (statusEffect == MobEffects.UNLUCK) {
        *///?} else {
        effectDesc.addProperty("id", registry.getId(mobEffect));
        if (mobEffect == MobEffects.UNLUCK.value()) {
        //?}
            effectDesc.addProperty("name", "BadLuck");
            effectDesc.addProperty("displayName", "Bad Luck");
        } else {
            effectDesc.addProperty("name", Arrays.stream(registryKey.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining()));
            //? if <1.21.5 {
            /*effectDesc.addProperty("displayName", DGU.translateText(statusEffect.getDescriptionId()));
            *///?} else {
            effectDesc.addProperty("displayName", DGU.translateText(mobEffect.getDescriptionId()));
            //?}
        }

        //? if <1.17 {
        /*effectDesc.addProperty("type", ((StatusEffectAccessor) statusEffect).type() == MobEffectCategory.BENEFICIAL ? "good" : "bad");
        *///?} else if >=1.17 <1.21.5 {
        /*effectDesc.addProperty("type", statusEffect.isBeneficial() ? "good" : "bad");
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
        //? if <1.20 {
        /*Registry<MobEffect> statusEffectRegistry = Registry.MOB_EFFECT;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<MobEffect> statusEffectRegistry = DGU.getWorld().registryAccess().registryOrThrow(Registries.MOB_EFFECT);
        *///?} else if >=1.21.3 <1.21.5 {
        /*Registry<MobEffect> statusEffectRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.MOB_EFFECT);
        *///?}
        //? if <1.21.5 {
        /*statusEffectRegistry.forEach(effect -> resultsArray.add(generateEffect(statusEffectRegistry, effect)));
        *///?} else {
        Registry<MobEffect> mobEffectRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.MOB_EFFECT);
        mobEffectRegistry.forEach(effect -> resultsArray.add(generateEffect(mobEffectRegistry, effect)));
        //?}
        return resultsArray;
    }
}
