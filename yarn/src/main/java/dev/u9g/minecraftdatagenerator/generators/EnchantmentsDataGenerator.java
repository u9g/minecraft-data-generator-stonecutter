package dev.u9g.minecraftdatagenerator.generators;

//? if <1.16 {
/*import com.google.common.collect.ImmutableMap;
*///?} else if >1.18 <1.19 {
/*import com.google.common.collect.ImmutableMap;
*///?}
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
//? if <1.20.5 {
/*import net.minecraft.enchantment.EnchantmentTarget;
*///?} else {
import net.minecraft.item.Item;
//?}
//? if >=1.20 {
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
//?}
//? if >=1.21 {
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
//?}
//? if >=1.21 <1.21.3 {
/*import net.minecraft.registry.tag.EnchantmentTags;
*///?}
//? if >=1.20.5 {
import net.minecraft.registry.tag.TagKey;
//?}
//? if >=1.8.9 {
import net.minecraft.util.Identifier;
//?}
//? if >=1.13 <1.20 {
/*import net.minecraft.util.registry.Registry;
*///?}

//? if <1.14 {
/*import java.util.ArrayList;
*///?}
import java.util.List;
//? if <1.20.5 {
/*import java.util.Locale;
*///?}
//? if <1.16 {
/*import java.util.Objects;
*///?}

public class EnchantmentsDataGenerator implements IDataGenerator {
    //? if <1.16 {

    /*private static final ImmutableMap<EnchantmentTarget, String> ENCHANTMENT_TARGET_NAMES = ImmutableMap.<EnchantmentTarget, String>builder()
    *///?}
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
            //? if >=1.11.2 <1.16 {
            /*.put(EnchantmentTarget.ALL, "vanishable") // according to VanishingCurseEnchantment
            *///?}
            //? if <1.16 {
            /*.build();

            *///?} else if >1.18 <1.19 {

    /*private static final ImmutableMap<EnchantmentTarget, String> ENCHANTMENT_TARGET_NAMES = ImmutableMap.<EnchantmentTarget, String>builder()
            .put(EnchantmentTarget.VANISHABLE, "vanishable")
            .build();

            *///?}

    //? if <1.20.5 {
    /*public static String getEnchantmentTargetName(EnchantmentTarget target) {
    *///?}
        //? if <1.16 {
        /*return ENCHANTMENT_TARGET_NAMES.getOrDefault(target, target.name().toLowerCase(Locale.ROOT));
        *///?} else if >=1.16 <=1.18 {
        /*return target.name().toLowerCase(Locale.ROOT);
        *///?} else if >1.18 <1.19 {
        /*return ENCHANTMENT_TARGET_NAMES.getOrDefault(target, target.name().toLowerCase(Locale.ROOT));
        *///?} else if >=1.19 <1.20.5 {
        /*return target.name().toLowerCase(Locale.ROOT);
        *///?} else if >=1.20.5 <1.21 {
    /*public static String getEnchantmentTargetName(TagKey<Item> target) {
        return target.id().getPath().split("/")[1];
        *///?} else {
    public static String getEnchantmentTargetName(RegistryEntryList<Item> target) {
        TagKey<Item> tagKey = target.getTagKey().orElseThrow();
        return tagKey.id().getPath().split("/")[1];
        //?}
    //? if >=1.21.3 {
    }

    private static boolean isEnchantmentInTag(Enchantment enchantment, String tag) {
        return DGU.getWorld()
                .getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .getOrThrow(TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(tag)))
                .stream()
                .anyMatch(enchantmentRegistryEntry -> enchantmentRegistryEntry.value() == enchantment);
    //?}
    }

    // Equation enchantment costs follow is a * level + b, so we can easily retrieve a and b by passing zero level
    //Equation enchantment costs follow is a * level + b, so we can easily retrieve a and b by passing zero level
    private static JsonObject generateEnchantmentMinPowerCoefficients(Enchantment enchantment) {
        //? if <1.16 {
        /*int b = enchantment.getMinimumPower(0);
        int a = enchantment.getMinimumPower(1) - b;
        *///?} else {
        int b = enchantment.getMinPower(0);
        int a = enchantment.getMinPower(1) - b;
        //?}

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    private static JsonObject generateEnchantmentMaxPowerCoefficients(Enchantment enchantment) {
        //? if <1.13 {
        /*int b = enchantment.getMaximumPower(0);
        int a = enchantment.getMaximumPower(1) - b;
        *///?} else if >=1.13 <1.15 {
        /*int b = getMaximumPower(enchantment, 0);
        int a = getMaximumPower(enchantment, 1) - b;
        *///?} else if >=1.15 <1.16 {
        /*int b = enchantment.getMaximumPower(0);
        int a = enchantment.getMaximumPower(1) - b;
        *///?} else {
        int b = enchantment.getMaxPower(0);
        int a = enchantment.getMaxPower(1) - b;
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
        *///?} else if >=1.13 <1.16 {
        /*Identifier registryKey = registry.getId(enchantment);
        *///?} else if >=1.21 <1.21.3 {
        /*RegistryEntry<Enchantment> enchantmentEntry = registry.getEntry(enchantment);
        *///?}
        //? if >=1.16 {
        Identifier registryKey = registry.getKey(enchantment).orElseThrow().getValue();
        //?}

        //? if <1.13 {
        /*enchantmentDesc.addProperty("id", Registries.ENCHANTMENTS.getRawId(enchantment));
        *///?}
        //? if <1.8.9 {
        /*enchantmentDesc.addProperty("name", Objects.requireNonNull(registryKey));
        *///?} else if >=1.13 {
        enchantmentDesc.addProperty("id", registry.getRawId(enchantment));
        //?}
        //? if >=1.8.9 <1.16 {
        /*enchantmentDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        *///?} else if >=1.16 {
        enchantmentDesc.addProperty("name", registryKey.getPath());
        //?}
        //? if <1.21 {
        /*enchantmentDesc.addProperty("displayName", DGU.translateText(enchantment.getTranslationKey()));
        *///?} else if >=1.21 <1.21.3 {
        /*String displayName = Enchantment.getName(registry.getEntry(enchantment), 1).getString();
        displayName = displayName.replaceAll(" I$", "");
        enchantmentDesc.addProperty("displayName", displayName);
        *///?} else {
        enchantmentDesc.addProperty("displayName", enchantment.description().getString());
        //?}

        //? if <1.16 {
        /*enchantmentDesc.addProperty("maxLevel", enchantment.getMaximumLevel());
        *///?} else {
        enchantmentDesc.addProperty("maxLevel", enchantment.getMaxLevel());
        //?}
        enchantmentDesc.add("minCost", generateEnchantmentMinPowerCoefficients(enchantment));
        enchantmentDesc.add("maxCost", generateEnchantmentMaxPowerCoefficients(enchantment));

        //? if <1.9.4 {
        /*enchantmentDesc.addProperty("treasureOnly", false); // 1.9 added treasure enchants
        *///?} else if >=1.9.4 <1.21 {
        /*enchantmentDesc.addProperty("treasureOnly", enchantment.isTreasure());
        *///?}
        //? if <1.11.2 {
        /*enchantmentDesc.addProperty("curse", false); // 1.10 added curse enchants
        *///?} else if >=1.11.2 <1.21 {
        /*enchantmentDesc.addProperty("curse", enchantment.isCursed());
        *///?} else if >=1.21 <1.21.3 {
        /*enchantmentDesc.addProperty("treasureOnly", enchantmentEntry.isIn(EnchantmentTags.TREASURE));
        *///?} else {
        enchantmentDesc.addProperty("treasureOnly", isEnchantmentInTag(enchantment, "treasure"));
        //?}

        //? if >=1.21 <1.21.3 {
        /*enchantmentDesc.addProperty("curse", enchantmentEntry.isIn(EnchantmentTags.CURSE));
        *///?} else if >=1.21.3 {
        enchantmentDesc.addProperty("curse", isEnchantmentInTag(enchantment, "curse"));
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
                //?}
                //? if >=1.14 <1.16 {
                /*.filter(other -> !enchantment.isDifferent(other))
                .filter(other -> other != enchantment).toList();
                *///?} else if >=1.16 <1.21 {
                /*.filter(other -> !enchantment.canCombine(other))
                *///?} else if >=1.21 <1.21.3 {
                /*.filter(other -> !Enchantment.canBeCombined(enchantmentEntry, registry.getEntry(other)))
                *///?} else if >=1.21.3 {
                .filter(other -> {
                    RegistryEntry<Enchantment> enchantmentEntry = registry.getEntry(enchantment);
                    RegistryEntry<Enchantment> otherEntry = registry.getEntry(other);
                    return !Enchantment.canBeCombined(enchantmentEntry, otherEntry);
                })
                //?}
                //? if >=1.16 {
                .filter(other -> other != enchantment)
                .toList();
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
            *///?} else if >=1.13 <1.16 {
            /*Identifier otherKey = registry.getId(excludedEnchantment);
            *///?}
            //? if >=1.12.2 <1.16 {
            /*excludes.add(Objects.requireNonNull(otherKey).getPath());
            *///?} else if >=1.16 {
            Identifier otherKey = registry.getKey(excludedEnchantment).orElseThrow().getValue();
            excludes.add(otherKey.getPath());
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
        *///?} else if >=1.14 <1.20 {
        /*enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.type));
        *///?}
        //? if >=1.14 <1.16 {
        /*enchantmentDesc.addProperty("weight", enchantment.getWeight().getWeight());
        *///?}
        //? if <1.16 {
        /*enchantmentDesc.addProperty("tradeable", true); // the first non-tradeable enchant came in 1.16, soul speed
        enchantmentDesc.addProperty("discoverable", true); // the first non-enchantable enchant came in 1.16, soul speed
        *///?} else if >=1.20 <1.20.5 {
        /*enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.target));
        *///?}
        //? if >=1.16 <1.20.5 {
        /*enchantmentDesc.addProperty("weight", enchantment.getRarity().getWeight());
        *///?} else if >=1.20.5 {
        enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.getApplicableItems()));
        enchantmentDesc.addProperty("weight", enchantment.getWeight());
        //?}
        //? if >=1.16 <1.21 {
        /*enchantmentDesc.addProperty("tradeable", enchantment.isAvailableForEnchantedBookOffer());
        enchantmentDesc.addProperty("discoverable", enchantment.isAvailableForRandomSelection());
        *///?} else if >=1.21 <1.21.3 {
        /*enchantmentDesc.addProperty("tradeable", enchantmentEntry.isIn(EnchantmentTags.TRADEABLE));
        enchantmentDesc.addProperty("discoverable", enchantmentEntry.isIn(EnchantmentTags.ON_RANDOM_LOOT));
        *///?} else if >=1.21.3 {
        enchantmentDesc.addProperty("tradeable", isEnchantmentInTag(enchantment, "tradeable"));
        enchantmentDesc.addProperty("discoverable", isEnchantmentInTag(enchantment, "on_random_loot"));
        //?}

        return enchantmentDesc;
    }

    //? if >=1.13 <1.15 {
    /*private static int getMaximumPower(Enchantment ench, int level) {
        return ench.getMinimumPower(level) + 5;
    }

    *///?}
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
        *///?} else if >=1.13 <1.20 {
        /*Registry<Enchantment> enchantmentRegistry = Registry.ENCHANTMENT;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Enchantment> enchantmentRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT);
        *///?} else {
        Registry<Enchantment> enchantmentRegistry = DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        //?}
        //? if >=1.13 {
        enchantmentRegistry.stream()
                .forEach(enchantment -> resultsArray.add(generateEnchantment(enchantmentRegistry, enchantment)));
        //?}
        return resultsArray;
    }


}
