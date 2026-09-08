package dev.u9g.minecraftdatagenerator.generators;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if >=1.21 {
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
//?}
import net.minecraft.core.Registry;
//? if >=1.20 {
import net.minecraft.core.registries.Registries;
//?}
//? if <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
*///?}
//? if >=1.21 <1.21.3 {
/*import net.minecraft.tags.EnchantmentTags;
*///?} else if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?}
//? if >=1.20.5 {
import net.minecraft.tags.TagKey;
//?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
//? if <1.20.5 {
/*import net.minecraft.world.item.enchantment.EnchantmentCategory;
*///?}

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class EnchantmentsDataGenerator implements IDataGenerator {
    //? if <1.16 {

    /*private static final ImmutableMap<EnchantmentCategory, String> ENCHANTMENT_TARGET_NAMES = ImmutableMap.<EnchantmentCategory, String>builder()
            .put(EnchantmentCategory.ALL, "vanishable") // according to VanishingCurseEnchantment
            .build();

    *///?} else if >1.18 <1.19 {

    /*private static final ImmutableMap<EnchantmentCategory, String> ENCHANTMENT_TARGET_NAMES = ImmutableMap.<EnchantmentCategory, String>builder()
            .put(EnchantmentCategory.VANISHABLE, "vanishable")
            .build();

    *///?}

    //? if <1.20.5 {
    /*public static String getEnchantmentTargetName(EnchantmentCategory target) {
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
        return target.location().getPath().split("/")[1];
        *///?} else {
    public static String getEnchantmentTargetName(HolderSet<Item> target) {
        TagKey<Item> tagKey = target.unwrapKey().orElseThrow();
        return tagKey.location().getPath().split("/")[1];
        //?}
    //? if >=1.21.3 {
    }

    private static boolean isEnchantmentInTag(Enchantment enchantment, String tag) {
        return DGU.getWorld()
                .registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
    //?}
                //? if >=1.21.3 <1.21.11 {
                /*.getOrThrow(TagKey.create(Registries.ENCHANTMENT, ResourceLocation.parse(tag)))
                *///?} else if >=1.21.11 {
                .getOrThrow(TagKey.create(Registries.ENCHANTMENT, Identifier.parse(tag)))
                //?}
                //? if >=1.21.3 {
                .stream()
                .anyMatch(enchantmentRegistryEntry -> enchantmentRegistryEntry.value() == enchantment);
                //?}
    }

    // Equation enchantment costs follow is a * level + b, so we can easily retrieve a and b by passing zero level
    //Equation enchantment costs follow is a * level + b, so we can easily retrieve a and b by passing zero level
    private static JsonObject generateEnchantmentMinPowerCoefficients(Enchantment enchantment) {
        //? if <1.21.5 {
        /*int b = enchantment.getMinCost(0);
        int a = enchantment.getMinCost(1) - b;
        *///?} else {
        int b = enchantment.getMinLevel();
        int a = enchantment.getMaxLevel() - b;
        //?}

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    private static JsonObject generateEnchantmentMaxPowerCoefficients(Enchantment enchantment) {
        //? if <1.21.5 {
        /*int b = enchantment.getMaxCost(0);
        int a = enchantment.getMaxCost(1) - b;
        *///?} else {
        int b = enchantment.getMinLevel();
        int a = enchantment.getMaxLevel() - b;
        //?}

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    public static JsonObject generateEnchantment(Registry<Enchantment> registry, Enchantment enchantment) {
        JsonObject enchantmentDesc = new JsonObject();
        //? if <1.16 {
        /*ResourceLocation registryKey = registry.getKey(enchantment);
        *///?} else if >=1.21 <1.21.3 {
        /*Holder<Enchantment> enchantmentEntry = registry.wrapAsHolder(enchantment);
        *///?}
        //? if >=1.16 <1.21.5 {
        /*ResourceLocation registryKey = registry.getResourceKey(enchantment).orElseThrow().location();
        *///?} else if >=1.21.5 <1.21.11 {
        /*ResourceLocation registryKey = registry.getKey(enchantment);
        *///?} else if >=1.21.11 {
        Identifier registryKey = registry.getKey(enchantment);
        //?}

        enchantmentDesc.addProperty("id", registry.getId(enchantment));
        //? if <1.16 {
        /*enchantmentDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        *///?} else {
        enchantmentDesc.addProperty("name", registryKey.getPath());
        //?}
        //? if <1.21 {
        /*enchantmentDesc.addProperty("displayName", DGU.translateText(enchantment.getDescriptionId()));
        *///?} else if >=1.21 <1.21.3 {
        /*String displayName = Enchantment.getFullname(registry.wrapAsHolder(enchantment), 1).getString();
        displayName = displayName.replaceAll(" I$", "");
        enchantmentDesc.addProperty("displayName", displayName);
        *///?} else {
        enchantmentDesc.addProperty("displayName", enchantment.description().getString());
        //?}

        enchantmentDesc.addProperty("maxLevel", enchantment.getMaxLevel());
        enchantmentDesc.add("minCost", generateEnchantmentMinPowerCoefficients(enchantment));
        enchantmentDesc.add("maxCost", generateEnchantmentMaxPowerCoefficients(enchantment));

        //? if <1.21 {
        /*enchantmentDesc.addProperty("treasureOnly", enchantment.isTreasureOnly());
        enchantmentDesc.addProperty("curse", enchantment.isCurse());
        *///?} else if >=1.21 <1.21.3 {
        /*enchantmentDesc.addProperty("treasureOnly", enchantmentEntry.is(EnchantmentTags.TREASURE));
        *///?} else {
        enchantmentDesc.addProperty("treasureOnly", isEnchantmentInTag(enchantment, "treasure"));
        //?}

        //? if >=1.21 <1.21.3 {
        /*enchantmentDesc.addProperty("curse", enchantmentEntry.is(EnchantmentTags.CURSE));
        *///?} else if >=1.21.3 {
        enchantmentDesc.addProperty("curse", isEnchantmentInTag(enchantment, "curse"));
        //?}

        List<Enchantment> incompatibleEnchantments = registry.stream()
                //? if <1.21 {
                /*.filter(other -> !enchantment.isCompatibleWith(other))
                *///?}
                //? if <1.16 {
                /*.filter(other -> other != enchantment).toList();
                *///?} else if >=1.21 <1.21.3 {
                /*.filter(other -> !Enchantment.areCompatible(enchantmentEntry, registry.wrapAsHolder(other)))
                *///?} else if >=1.21.3 {
                .filter(other -> {
                    Holder<Enchantment> enchantmentEntry = registry.wrapAsHolder(enchantment);
                    Holder<Enchantment> otherEntry = registry.wrapAsHolder(other);
                    return !Enchantment.areCompatible(enchantmentEntry, otherEntry);
                })
                //?}
                //? if >=1.16 {
                .filter(other -> other != enchantment)
                .toList();
                //?}

        JsonArray excludes = new JsonArray();
        for (Enchantment excludedEnchantment : incompatibleEnchantments) {
            //? if <1.16 {
            /*ResourceLocation otherKey = registry.getKey(excludedEnchantment);
            excludes.add(Objects.requireNonNull(otherKey).getPath());
            *///?} else if >=1.16 <1.21.5 {
            /*ResourceLocation otherKey = registry.getResourceKey(excludedEnchantment).orElseThrow().location();
            *///?} else if >=1.21.5 <1.21.11 {
            /*ResourceLocation otherKey = registry.getKey(excludedEnchantment);
            *///?} else {
            Identifier otherKey = registry.getKey(excludedEnchantment);
            //?}
            //? if >=1.16 {
            excludes.add(otherKey.getPath());
            //?}
        }
        enchantmentDesc.add("exclude", excludes);

        //? if <1.20.5 {
        /*enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.category));
        enchantmentDesc.addProperty("weight", enchantment.getRarity().getWeight());
        *///?}
        //? if <1.16 {
        /*enchantmentDesc.addProperty("tradeable", true); // the first non-tradeable enchant came in 1.16, soul speed
        enchantmentDesc.addProperty("discoverable", true); // the first non-enchantable enchant came in 1.16, soul speed
        *///?} else if >=1.20.5 <1.21.5 {
        /*enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.getSupportedItems()));
        enchantmentDesc.addProperty("weight", enchantment.getWeight());
        *///?}
        //? if >=1.16 <1.21 {
        /*enchantmentDesc.addProperty("tradeable", enchantment.isTradeable());
        enchantmentDesc.addProperty("discoverable", enchantment.isDiscoverable());
        *///?} else if >=1.21 <1.21.3 {
        /*enchantmentDesc.addProperty("tradeable", enchantmentEntry.is(EnchantmentTags.TRADEABLE));
        enchantmentDesc.addProperty("discoverable", enchantmentEntry.is(EnchantmentTags.ON_RANDOM_LOOT));
        *///?} else if >=1.21.5 {
        enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.definition().supportedItems()));
        enchantmentDesc.addProperty("weight", enchantment.definition().weight());
        //?}
        //? if >=1.21.3 {
        enchantmentDesc.addProperty("tradeable", isEnchantmentInTag(enchantment, "tradeable"));
        enchantmentDesc.addProperty("discoverable", isEnchantmentInTag(enchantment, "on_random_loot"));
        //?}

        return enchantmentDesc;
    }

    @Override
    public String getDataName() {
        return "enchantments";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        //? if <1.20 {
        /*Registry<Enchantment> enchantmentRegistry = Registry.ENCHANTMENT;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Enchantment> enchantmentRegistry = DGU.getWorld().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        *///?} else {
        Registry<Enchantment> enchantmentRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        //?}
        enchantmentRegistry.stream()
                .forEach(enchantment -> resultsArray.add(generateEnchantment(enchantmentRegistry, enchantment)));
        return resultsArray;
    }


}
