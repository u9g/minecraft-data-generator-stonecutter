package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
//? if <1.8.9 {
/*import dev.u9g.minecraftdatagenerator.mixin.VariantBlockItemAccessor;
import dev.u9g.minecraftdatagenerator.mixin.ItemAccessor;
*///?}
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?}
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
//? if <1.8.9 {
/*import net.minecraft.item.VariantBlockItem;
*///?}
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;
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
        ItemStack sourceItemStack = DGU.stackFor(sourceItem);
        return itemRegistry.stream()
                .filter(otherItem -> sourceItem.canRepair(sourceItemStack, DGU.stackFor(otherItem)))
                .collect(Collectors.toList());
    //?}
    }

    private static Set<String> getApplicableEnchantmentTargets(Item sourceItem) {
        return Arrays.stream(EnchantmentTarget.values())
                //? if <1.14 {
                /*.filter(target -> target.isCompatible(sourceItem))
                *///?} else {
                .filter(target -> target.isAcceptableItem(sourceItem))
                //?}
                .map(EnchantmentsDataGenerator::getEnchantmentTargetName)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    //? if <1.13 {
    /*public static JsonObject generateItem(Item item) {
    *///?} else {
    public static JsonObject generateItem(Registry<Item> itemRegistry, Item item) {
    //?}
        JsonObject itemDesc = new JsonObject();
        //? if >=1.8.9 <1.13 {
        /*Identifier registryKey = Registries.ITEMS.getIdentifier(item);
        *///?} else if >=1.13 {
        Identifier registryKey = itemRegistry.getId(item);
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
        //? if >=1.8.9 {
        itemDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        //?}

        //? if <1.11.2 {
        /*itemDesc.addProperty("displayName", item.getDisplayName(DGU.stackFor(item)));
        *///?} else {
        itemDesc.addProperty("displayName", DGU.translateText(item.getTranslationKey()));
        //?}
        //? if <1.14 {
        /*itemDesc.addProperty("stackSize", item.getMaxCount());
        *///?} else {
        itemDesc.addProperty("stackSize", item.getMaxAmount());
        //?}

        JsonArray enchantCategoriesArray = new JsonArray();
        getApplicableEnchantmentTargets(item).forEach(enchantCategoriesArray::add);
        if (!enchantCategoriesArray.isEmpty()) {
            itemDesc.add("enchantCategories", enchantCategoriesArray);
        }

        //? if <1.14 {
        /*if (item.isDamageable()) {
        *///?}
            //? if <1.13 {
            /*List<Item> repairWithItems = calculateItemsToRepairWith(item);
            *///?} else if >=1.14 {
        if (item.canDamage()) {
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
                *///?} else if >=1.13 {
                Identifier repairWithName = itemRegistry.getId(repairWithItem);
                //?}
                //? if >=1.12.2 {
                fixedWithArray.add(Objects.requireNonNull(repairWithName).getPath());
                //?}
            }
            if (!fixedWithArray.isEmpty()) {
                itemDesc.add("repairWith", fixedWithArray);
            }

            //? if <1.14 {
            /*int maxDurability = item.getMaxDamage();
            *///?} else {
            int maxDurability = item.getMaxAmount();
            //?}
            itemDesc.addProperty("maxDurability", maxDurability);
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
        *///?} else {
        Registry<Item> itemRegistry = Registry.ITEM;
        //?}
        //? if >=1.13 <1.14 {
        /*for (Item item : (Iterable<Item>) itemRegistry) {
            resultArray.add(generateItem(itemRegistry, item));
        *///?}
        //? if <1.14 {
        /*}
        *///?} else {
        itemRegistry.stream().forEach(item -> resultArray.add(generateItem(itemRegistry, item)));
        //?}
        return resultArray;
    }
}
