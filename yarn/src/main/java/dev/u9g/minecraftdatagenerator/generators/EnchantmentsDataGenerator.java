package dev.u9g.minecraftdatagenerator.generators;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.12.2 {
/*import com.google.gson.JsonPrimitive;
*///?}
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?}
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
//? if >=1.8.9 {
import net.minecraft.util.Identifier;
//?}
//? if >=1.13 {
import net.minecraft.util.registry.Registry;
//?}

//? if <1.14 {
/*import java.util.ArrayList;
*///?}
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class EnchantmentsDataGenerator implements IDataGenerator {

    private static final ImmutableMap<EnchantmentTarget, String> ENCHANTMENT_TARGET_NAMES = ImmutableMap.<EnchantmentTarget, String>builder()
            //? if <1.8.9 {
            /*.put(EnchantmentTarget.ARMOR, "armor")
            .put(EnchantmentTarget.ARMOR_FEET, "armor_feet")
            .put(EnchantmentTarget.ARMOR_LEGS, "armor_legs")
            .put(EnchantmentTarget.ARMOR_TORSO, "armor_chest")
            .put(EnchantmentTarget.ARMOR_HEAD, "armor_head")
            *///?} else if >=1.8.9 <1.14 {
            /*.put(EnchantmentTarget.ALL_ARMOR, "armor")
            .put(EnchantmentTarget.FEET, "armor_feet")
            .put(EnchantmentTarget.LEGS, "armor_legs")
            *///?}
            //? if >=1.8.9 <1.9.4 {
            /*.put(EnchantmentTarget.TORSO, "armor_chest")
            *///?} else if >=1.9.4 <1.14 {
            /*.put(EnchantmentTarget.ARMOR_CHEST, "armor_chest")
            *///?}
            //? if >=1.8.9 <1.14 {
            /*.put(EnchantmentTarget.HEAD, "armor_head")
            *///?}
            //? if <1.14 {
            /*.put(EnchantmentTarget.WEAPON, "weapon")
            .put(EnchantmentTarget.DIGGER, "digger")
            .put(EnchantmentTarget.FISHING_ROD, "fishing_rod")
            *///?}
            //? if >=1.13 <1.14 {
            /*.put(EnchantmentTarget.TRIDENT, "trident")
            *///?}
            //? if <1.14 {
            /*.put(EnchantmentTarget.BREAKABLE, "breakable")
            .put(EnchantmentTarget.BOW, "bow")
            *///?}
            //? if >=1.11.2 <1.14 {
            /*.put(EnchantmentTarget.WEARABLE, "wearable")
            *///?}
            //? if >=1.11.2 {
            .put(EnchantmentTarget.ALL, "vanishable") // according to VanishingCurseEnchantment
            //?}
            .build();

    public static String getEnchantmentTargetName(EnchantmentTarget target) {
        return ENCHANTMENT_TARGET_NAMES.getOrDefault(target, target.name().toLowerCase(Locale.ROOT));
    }

    // Equation enchantment costs follow is a * level + b, so we can easily retrieve a and b by passing zero level
    private static JsonObject generateEnchantmentMinPowerCoefficients(Enchantment enchantment) {
        int b = enchantment.getMinimumPower(0);
        int a = enchantment.getMinimumPower(1) - b;

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    private static JsonObject generateEnchantmentMaxPowerCoefficients(Enchantment enchantment) {
        //? if <1.13 {
        /*int b = enchantment.getMaximumPower(0);
        int a = enchantment.getMaximumPower(1) - b;
        *///?} else {
        int b = getMaximumPower(enchantment, 0);
        int a = getMaximumPower(enchantment, 1) - b;
        //?}

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    //? if <1.13 {
    /*public static JsonObject generateEnchantment(Enchantment enchantment) {
    *///?} else {
    public static JsonObject generateEnchantment(Registry<Enchantment> registry, Enchantment enchantment) {
    //?}
        JsonObject enchantmentDesc = new JsonObject();
        //? if <1.8.9 {
        /*String registryKey = Registries.ENCHANTMENTS.getId(enchantment);
        *///?} else if >=1.8.9 <1.13 {
        /*Identifier registryKey = Registries.ENCHANTMENTS.getIdentifier(enchantment);
        *///?} else {
        Identifier registryKey = registry.getId(enchantment);
        //?}

        //? if <1.13 {
        /*enchantmentDesc.addProperty("id", Registries.ENCHANTMENTS.getRawId(enchantment));
        *///?}
        //? if <1.8.9 {
        /*enchantmentDesc.addProperty("name", Objects.requireNonNull(registryKey));
        *///?} else if >=1.13 {
        enchantmentDesc.addProperty("id", registry.getRawId(enchantment));
        //?}
        //? if >=1.8.9 {
        enchantmentDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        //?}
        enchantmentDesc.addProperty("displayName", DGU.translateText(enchantment.getTranslationKey()));

        enchantmentDesc.addProperty("maxLevel", enchantment.getMaximumLevel());
        enchantmentDesc.add("minCost", generateEnchantmentMinPowerCoefficients(enchantment));
        enchantmentDesc.add("maxCost", generateEnchantmentMaxPowerCoefficients(enchantment));

        //? if <1.9.4 {
        /*enchantmentDesc.addProperty("treasureOnly", false); // 1.9 added treasure enchants
        *///?} else {
        enchantmentDesc.addProperty("treasureOnly", enchantment.isTreasure());
        //?}
        //? if <1.11.2 {
        /*enchantmentDesc.addProperty("curse", false); // 1.10 added curse enchants
        *///?} else {
        enchantmentDesc.addProperty("curse", enchantment.isCursed());
        //?}

        //? if <1.14 {
        /*List<Enchantment> incompatibleEnchantments = new ArrayList<>();
        *///?}
        //? if <1.13 {
        /*for (Enchantment other : Registries.ENCHANTMENTS) {
        *///?}
            //? if <1.11.2 {
            /*if (!enchantment.differs(other) && !other.differs(enchantment) && other != enchantment) {
            *///?} else if >=1.11.2 <1.13 {
            /*if (!enchantment.isDifferent(other) && other != enchantment) {
            *///?} else if >=1.13 <1.14 {
        /*for (Enchantment other : (Iterable<Enchantment>) registry) {
            if (enchantment.isDifferent(other) && other != enchantment) {
            *///?}
                //? if <1.14 {
                /*incompatibleEnchantments.add(other);
            }
        }
                *///?} else {
        List<Enchantment> incompatibleEnchantments = registry.stream()
                .filter(other -> !enchantment.isDifferent(other))
                .filter(other -> other != enchantment).toList();
                //?}

        JsonArray excludes = new JsonArray();
        for (Enchantment excludedEnchantment : incompatibleEnchantments) {
            //? if <1.8.9 {
            /*String otherKey = Registries.ENCHANTMENTS.getId(excludedEnchantment);
            excludes.add(new JsonPrimitive(Objects.requireNonNull(otherKey)));
            *///?} else if >=1.8.9 <1.13 {
            /*Identifier otherKey = Registries.ENCHANTMENTS.getIdentifier(excludedEnchantment);
            *///?}
            //? if >=1.8.9 <1.12.2 {
            /*excludes.add(new JsonPrimitive(Objects.requireNonNull(otherKey).getPath()));
            *///?} else if >=1.13 {
            Identifier otherKey = registry.getId(excludedEnchantment);
            //?}
            //? if >=1.12.2 {
            excludes.add(Objects.requireNonNull(otherKey).getPath());
            //?}
        }
        enchantmentDesc.add("exclude", excludes);

        //? if <1.14 {
        /*enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.target));
        *///?}
        //? if <1.9.4 {
        /*enchantmentDesc.addProperty("weight", enchantment.getEnchantmentType()); // see AnvilScreenhandler L209
        *///?} else if >=1.9.4 <1.14 {
        /*enchantmentDesc.addProperty("weight", enchantment.getRarity().getChance());
        *///?} else {
        enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.type));
        enchantmentDesc.addProperty("weight", enchantment.getWeight().getWeight());
        //?}
        enchantmentDesc.addProperty("tradeable", true); // the first non-tradeable enchant came in 1.16, soul speed
        enchantmentDesc.addProperty("discoverable", true); // the first non-enchantable enchant came in 1.16, soul speed

        return enchantmentDesc;
    }

    //? if >=1.13 {
    private static int getMaximumPower(Enchantment ench, int level) {
        return ench.getMinimumPower(level) + 5;
    }

    //?}
    @Override
    public String getDataName() {
        return "enchantments";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        //? if <1.13 {
        /*for (Enchantment enchantment : Registries.ENCHANTMENTS) {
            resultsArray.add(generateEnchantment(enchantment));
        }
        *///?} else {
        Registry<Enchantment> enchantmentRegistry = Registry.ENCHANTMENT;
        enchantmentRegistry.stream()
                .forEach(enchantment -> resultsArray.add(generateEnchantment(enchantmentRegistry, enchantment)));
        //?}
        return resultsArray;
    }
}
