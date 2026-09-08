package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.12.2 {
/*import com.google.gson.JsonPrimitive;
*///?}
//? if <1.8.9 {
/*import dev.u9g.minecraftdatagenerator.mixin.VariantBlockItemAccessor;
import dev.u9g.minecraftdatagenerator.mixin.ItemAccessor;
*///?} else if >=1.21 {
import com.google.gson.JsonPrimitive;
//?}
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?}
//? if <1.20.5 {
/*import net.minecraft.enchantment.EnchantmentTarget;
*///?} else {
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
//?}
//? if >=1.20.5 <1.21 {
/*import net.minecraft.enchantment.Enchantments;
*///?}
import net.minecraft.item.Item;
//? if <1.8.9 {
/*import net.minecraft.item.ItemStack;
import net.minecraft.item.VariantBlockItem;
*///?} else if >=1.13 {
import net.minecraft.item.ItemStack;
//?}
//? if >=1.20.5 <1.21 {
/*import net.minecraft.registry.BuiltinRegistries;
*///?}
//? if >=1.20 {
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
//?}
//? if >=1.20.5 {
import net.minecraft.registry.entry.RegistryEntry;
//?}
//? if >=1.20.5 <1.21 {
/*import net.minecraft.registry.tag.TagKey;
*///?}
//? if >=1.8.9 {
import net.minecraft.util.Identifier;
//?}
//? if >=1.13 <1.20 {
/*import net.minecraft.util.registry.Registry;
*///?}

//? if <1.16 {
/*import java.util.*;
*///?} else if >=1.16 <1.20.5 {
/*import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
*///?} else if >=1.20.5 <1.21 {
/*import java.util.*;
*///?} else {
import java.util.List;
import java.util.Objects;
//?}
import java.util.stream.Collectors;

public class ItemsDataGenerator implements IDataGenerator {

    //? if <1.13 {
    /*private static List<Item> calculateItemsToRepairWith(Item sourceItem) {
        List<Item> items = new ArrayList<>();
        for (Item otherItem : Registries.ITEMS) {
            if (sourceItem.canRepair(DGU.stackFor(sourceItem), DGU.stackFor(otherItem))) {
                items.add(otherItem);
            }
        }
        return items;
    *///?} else {
    private static List<Item> calculateItemsToRepairWith(Registry<Item> itemRegistry, Item sourceItem) {
    //?}
        //? if >=1.13 <1.15 {
        /*ItemStack sourceItemStack = DGU.stackFor(sourceItem);
        *///?} else if >=1.15 <1.16 {
        /*ItemStack sourceItemStack = DGU.asStack(sourceItem);
        *///?} else if >=1.16 <1.17 {
        /*ItemStack sourceItemStack = sourceItem.getStackForRender();
        *///?} else if >=1.17 {
        ItemStack sourceItemStack = sourceItem.getDefaultStack();
        //?}
        //? if >=1.13 {
        return itemRegistry.stream()
        //?}
                //? if >=1.13 <1.15 {
                /*.filter(otherItem -> sourceItem.canRepair(sourceItemStack, DGU.stackFor(otherItem)))
                *///?} else if >=1.15 <1.16 {
                /*.filter(otherItem -> sourceItem.canRepair(sourceItemStack, DGU.asStack(otherItem)))
                *///?} else if >=1.16 <1.17 {
                /*.filter(otherItem -> sourceItem.canRepair(sourceItemStack, otherItem.getStackForRender()))
                *///?} else if >=1.17 <1.21.3 {
                /*.filter(otherItem -> sourceItem.canRepair(sourceItemStack, otherItem.getDefaultStack()))
                *///?} else if >=1.21.3 {
                .filter(otherItem -> sourceItemStack.canRepairWith(otherItem.getDefaultStack()))
                //?}
                //? if >=1.13 {
                .collect(Collectors.toList());
                //?}
    }

    //? if <1.20.5 {
    /*private static Set<String> getApplicableEnchantmentTargets(Item sourceItem) {
        return Arrays.stream(EnchantmentTarget.values())
    *///?}
                //? if <1.14 {
                /*.filter(target -> target.isCompatible(sourceItem))
                *///?} else if >=1.14 <1.20.5 {
                /*.filter(target -> target.isAcceptableItem(sourceItem))
                *///?} else if >=1.20.5 <1.21 {
    /*private static Set<String> getApplicableEnchantmentTargets(RegistryEntry<Item> sourceItem) {
        return DGU.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).stream()
                .map(Enchantment::getApplicableItems)
                .filter(sourceItem::isIn)
                *///?}
                //? if <1.21 {
                /*.map(EnchantmentsDataGenerator::getEnchantmentTargetName)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
                *///?}


    //? if <1.13 {
    /*public static JsonObject generateItem(Item item) {
    *///?} else {
    public static JsonObject generateItem(Registry<Item> itemRegistry, Item item) {
    //?}
        JsonObject itemDesc = new JsonObject();
        //? if >=1.8.9 <1.13 {
        /*Identifier registryKey = Registries.ITEMS.getIdentifier(item);
        *///?} else if >=1.13 <1.16 {
        /*Identifier registryKey = itemRegistry.getId(item);
        *///?} else if >=1.16 {
        Identifier registryKey = itemRegistry.getKey(item).orElseThrow().getValue();
        //?}

        //? if <1.13 {
        /*itemDesc.addProperty("id", Registries.ITEMS.getRawId(item));
        *///?}
        //? if <1.8.9 {

        /*String name = Registries.ITEMS.getId(item);
        itemDesc.addProperty("name", name == null ? ((ItemAccessor) item).translationKey() : name.replace("minecraft:", ""));
        *///?} else if >=1.13 {
        itemDesc.addProperty("id", itemRegistry.getRawId(item));
        //?}
        //? if >=1.8.9 <1.16 {
        /*itemDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        *///?} else if >=1.18 <=1.18 {
        /*itemDesc.addProperty("displayName", DGU.translateText(item.getTranslationKey()));
        *///?}
        //? if >=1.16 {
        itemDesc.addProperty("name", registryKey.getPath());
        //?}

        //? if <1.11.2 {
        /*itemDesc.addProperty("displayName", item.getDisplayName(DGU.stackFor(item)));
        *///?} else if >=1.11.2 <1.18 {
        /*itemDesc.addProperty("displayName", DGU.translateText(item.getTranslationKey()));
        *///?}
        //? if <1.14 {
        /*itemDesc.addProperty("stackSize", item.getMaxCount());
        *///?} else if >=1.14 <1.15 {
        /*itemDesc.addProperty("stackSize", item.getMaxAmount());
        *///?} else if >1.18 {
        itemDesc.addProperty("displayName", DGU.translateText(item.getTranslationKey()));
        //?}
        //? if >=1.15 {
        itemDesc.addProperty("stackSize", item.getMaxCount());
        //?}

        JsonArray enchantCategoriesArray = new JsonArray();
        //? if <1.20.5 {
        /*getApplicableEnchantmentTargets(item).forEach(enchantCategoriesArray::add);
        *///?}
        //? if >=1.18 <=1.18 {

        /*if (item.isDamageable()) itemDesc.addProperty("maxDurability", item.getMaxDamage());
        *///?} else if >=1.20.5 <1.21 {
        /*getApplicableEnchantmentTargets(itemRegistry.getEntry(item)).forEach(enchantCategoriesArray::add);
        *///?}
        //? if <1.21 {
        /*if (!enchantCategoriesArray.isEmpty()) {
        *///?} else if >=1.21 <1.21.3 {
        /*Registry<Enchantment> enchants = DGU.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT);
        for (Enchantment enchant : enchants) {
            if (enchant.getApplicableItems().contains(item.getRegistryEntry())) {
                String enchantTarget = enchant.getApplicableItems().getTagKey().get().id().getPath().split("/")[1];
                if (!enchantCategoriesArray.contains(new JsonPrimitive(enchantTarget))) {
                    enchantCategoriesArray.add(enchantTarget);
                }
            }
        }
        *///?} else {
        DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).stream()
                .map(Enchantment::getApplicableItems)
                .filter(applicableItems -> applicableItems.contains(itemRegistry.getEntry(item)))
                .map(EnchantmentsDataGenerator::getEnchantmentTargetName)
                .distinct()
                .forEach(enchantCategoriesArray::add);

        //?}
        //? if >=1.21 {
        if (enchantCategoriesArray.size() > 0) {
        //?}
            itemDesc.add("enchantCategories", enchantCategoriesArray);
        }

        //? if <1.14 {
        /*if (item.isDamageable()) {
        *///?}
            //? if <1.13 {
            /*List<Item> repairWithItems = calculateItemsToRepairWith(item);
            *///?} else if >=1.14 <1.15 {
        /*if (item.canDamage()) {
            *///?} else if >=1.15 <1.20.5 {
        /*if (item.isDamageable()) {
            *///?} else if >=1.20.5 {
        if (item.getComponents().contains(DataComponentTypes.DAMAGE)) {
            //?}
            //? if >=1.13 {
            List<Item> repairWithItems = calculateItemsToRepairWith(itemRegistry, item);
            //?}

            JsonArray fixedWithArray = new JsonArray();
            for (Item repairWithItem : repairWithItems) {
                //? if <1.8.9 {
                /*String repairWithName = Registries.ITEMS.getId(repairWithItem);
                fixedWithArray.add(new JsonPrimitive(Objects.requireNonNull(repairWithName)));
                *///?} else if >=1.8.9 <1.13 {
                /*Identifier repairWithName = Registries.ITEMS.getIdentifier(repairWithItem);
                *///?}
                //? if >=1.8.9 <1.12.2 {
                /*fixedWithArray.add(new JsonPrimitive(Objects.requireNonNull(repairWithName).getPath()));
                *///?} else if >=1.13 <1.16 {
                /*Identifier repairWithName = itemRegistry.getId(repairWithItem);
                *///?}
                //? if >=1.12.2 <1.16 {
                /*fixedWithArray.add(Objects.requireNonNull(repairWithName).getPath());
                *///?} else if >=1.16 {
                Identifier repairWithName = itemRegistry.getKey(repairWithItem).orElseThrow().getValue();
                fixedWithArray.add(repairWithName.getPath());
                //?}
            }
            //? if <1.21 {
            /*if (!fixedWithArray.isEmpty()) {
            *///?} else {
            if (fixedWithArray.size() > 0) {
            //?}
                itemDesc.add("repairWith", fixedWithArray);
            }

            //? if <1.14 {
            /*int maxDurability = item.getMaxDamage();
            *///?} else if >=1.14 <1.15 {
            /*int maxDurability = item.getMaxAmount();
            *///?} else if >=1.15 <1.18 {
            /*int maxDurability = item.getMaxDamage();
            *///?}
            //? if <1.18 {
            /*itemDesc.addProperty("maxDurability", maxDurability);
            *///?}

            //? if >1.18 <1.20.5 {
            /*int maxDurability = item.getMaxDamage();
            *///?} else if >=1.20.5 {
            int maxDurability = Objects.requireNonNull(item.getComponents().get(DataComponentTypes.MAX_DAMAGE));
            //?}
            //? if >1.18 {
            itemDesc.addProperty("maxDurability", maxDurability);
            //?}
        }
        //? if <1.8.9 {

        /*if (item instanceof VariantBlockItem it) {
            JsonArray variations = new JsonArray();
            int i = 0;
            JsonObject obj = new JsonObject();
            for (String variant : ((VariantBlockItemAccessor) it).variants()) {
                ItemStack stack = new ItemStack(item, 1, i);
                obj.add("id", new JsonPrimitive(i));
                obj.add("name", new JsonPrimitive(variant));
                obj.add("displayName", new JsonPrimitive(DGU.translateText(it.getTranslationKey(stack) + ".name")));
                variations.add(obj);
                i++;
            }
            itemDesc.add("variations", variations);
        }

        *///?}
        return itemDesc;
    }

    @Override
    public String getDataName() {
        return "items";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultArray = new JsonArray();
        //? if <1.13 {
        /*for (Item item : Registries.ITEMS) {
            resultArray.add(generateItem(item));
        *///?} else if >=1.13 <1.20 {
        /*Registry<Item> itemRegistry = Registry.ITEM;
        *///?}
        //? if >=1.13 <1.14 {
        /*for (Item item : (Iterable<Item>) itemRegistry) {
            resultArray.add(generateItem(itemRegistry, item));
        *///?}
        //? if <1.14 {
        /*}
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Item> itemRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.ITEM);
        *///?} else if >=1.21.3 {
        Registry<Item> itemRegistry = DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ITEM);
        //?}
        //? if >=1.14 {
        itemRegistry.stream().forEach(item -> resultArray.add(generateItem(itemRegistry, item)));
        //?}
        return resultArray;
    }
}
