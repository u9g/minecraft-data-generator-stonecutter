package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.17 {
/*import dev.u9g.minecraftdatagenerator.mixin.StatusEffectAccessor;
*///?}
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?}
import net.minecraft.entity.effect.StatusEffect;
//? if >=1.14 <1.17 {
/*import net.minecraft.entity.effect.StatusEffectType;
*///?}
//? if >=1.9.4 {
import net.minecraft.entity.effect.StatusEffects;
//?}
//? if >=1.20 {
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
//?}
//? if >=1.8.9 {
import net.minecraft.util.Identifier;
//?}
//? if >=1.13 <1.20 {
/*import net.minecraft.util.registry.Registry;
*///?}
//? if >=1.8.9 {
import org.apache.commons.lang3.StringUtils;
//?}
//? if <1.14 {
/*import org.jetbrains.annotations.NotNull;
*///?}

//? if >=1.8.9 {
import java.util.Arrays;
//?}
//? if <1.14 {
/*import java.util.Objects;
*///?}
//? if >=1.8.9 {
import java.util.stream.Collectors;
//?}

public class EffectsDataGenerator implements IDataGenerator {
    //? if <1.13 {
    /*public static JsonObject generateEffect(StatusEffect statusEffect) {
    *///?} else {
    public static JsonObject generateEffect(Registry<StatusEffect> registry, StatusEffect statusEffect) {
    //?}
        JsonObject effectDesc = new JsonObject();
        //? if <1.8.9 {
        /*@NotNull String name = Objects.requireNonNull(Registries.STATUS_EFFECTS.getId(statusEffect));
        *///?} else if >=1.8.9 <1.13 {
        /*@NotNull Identifier registryKey = Objects.requireNonNull(Registries.STATUS_EFFECTS.getIdentifier(statusEffect));
        *///?} else if >=1.13 <1.14 {
        /*@NotNull Identifier registryKey = Objects.requireNonNull(registry.getId(statusEffect));
        *///?} else if >=1.14 <1.16 {
        /*Identifier registryKey = registry.getId(statusEffect);
        *///?} else {
        Identifier registryKey = registry.getKey(statusEffect).orElseThrow().getValue();
        //?}

        //? if <1.13 {
        /*effectDesc.addProperty("id", Registries.STATUS_EFFECTS.getRawId(statusEffect));
        *///?}
        //? if <1.8.9 {
        /*effectDesc.addProperty("name", name);
        *///?} else if >=1.8.9 <1.9.4 {
        /*effectDesc.addProperty("name", Arrays.stream(registryKey.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining()));
        *///?}
        //? if <1.9.4 {
        /*effectDesc.addProperty("displayName", DGU.translateText(statusEffect.getTranslationKey()));
        *///?} else if >=1.13 {
        effectDesc.addProperty("id", registry.getRawId(statusEffect));
        //?}
        //? if >=1.9.4 {
        if (statusEffect == StatusEffects.UNLUCK) {
            effectDesc.addProperty("name", "BadLuck");
            effectDesc.addProperty("displayName", "Bad Luck");
        } else {
            effectDesc.addProperty("name", Arrays.stream(registryKey.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining()));
            effectDesc.addProperty("displayName", DGU.translateText(statusEffect.getTranslationKey()));
        }
        //?}

        //? if <1.14 {
        /*effectDesc.addProperty("type", !((StatusEffectAccessor) statusEffect).negative() ? "good" : "bad");
        *///?} else if >=1.14 <1.17 {
        /*effectDesc.addProperty("type", ((StatusEffectAccessor) statusEffect).type() == StatusEffectType.BENEFICIAL ? "good" : "bad");
        *///?} else {
        effectDesc.addProperty("type", statusEffect.isBeneficial() ? "good" : "bad");
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
        //? if <1.13 {
        /*for (StatusEffect effect : Registries.STATUS_EFFECTS) {
            resultsArray.add(generateEffect(effect));
        *///?} else if >=1.13 <1.14 {
        /*Registry<StatusEffect> statusEffectRegistry = Registry.MOB_EFFECT;
        for (StatusEffect effect : (Iterable<StatusEffect>) statusEffectRegistry) {
            resultsArray.add(generateEffect(statusEffectRegistry, effect));
        *///?}
        //? if <1.14 {
        /*}
        *///?} else if >=1.14 <1.20 {
        /*Registry<StatusEffect> statusEffectRegistry = Registry.STATUS_EFFECT;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<StatusEffect> statusEffectRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.STATUS_EFFECT);
        *///?} else {
        Registry<StatusEffect> statusEffectRegistry = DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.STATUS_EFFECT);
        //?}
        //? if >=1.14 {
        statusEffectRegistry.forEach(effect -> resultsArray.add(generateEffect(statusEffectRegistry, effect)));
        //?}
        return resultsArray;
    }
}
