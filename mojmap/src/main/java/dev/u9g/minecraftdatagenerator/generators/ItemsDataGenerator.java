package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if =1.20.5 {
/*import net.minecraft.core.Holder;
*///?}
import net.minecraft.core.Registry;
//? if >=1.20.5 {
import net.minecraft.core.component.DataComponents;
//?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//? if <1.20.5 {
/*import net.minecraft.world.item.enchantment.EnchantmentCategory;
*///?}
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemsDataGenerator implements IDataGenerator {

    private static List<Item> calculateItemsToRepairWith(Registry<Item> itemRegistry, Item sourceItem) {
        ItemStack sourceItemStack = new ItemStack(sourceItem);
        return itemRegistry.stream()
                //? if <1.21.3 {
                /*.filter(otherItem -> sourceItem.isValidRepairItem(sourceItemStack, new ItemStack(otherItem)))
                *///?} else {
                .filter(otherItem -> sourceItemStack.isValidRepairItem(new ItemStack(otherItem)))
                //?}
                .collect(Collectors.toList());
    }

    //? if <1.20.5 {
    /*private static Set<String> getApplicableEnchantmentTargets(Item sourceItem) {
        return Arrays.stream(EnchantmentCategory.values())
                .filter(target -> target.canEnchant(sourceItem))
    *///?} else if =1.20.5 {
    /*private static Set<String> getApplicableEnchantmentTargets(Holder<Item> sourceItem) {
        return DGU.<Enchantment>registry("enchantment").stream()
                .map(Enchantment::getSupportedItems)
                .filter(sourceItem::is)
    *///?}
                //? if <1.21 {
                /*.map(EnchantmentsDataGenerator::getEnchantmentTargetName)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
                *///?}


    public static JsonObject generateItem(Registry<Item> itemRegistry, Item item) {
        JsonObject itemDesc = new JsonObject();
        var registryKey = itemRegistry.getKey(item);

        itemDesc.addProperty("id", itemRegistry.getId(item));
        //? if =1.18 {
        /*itemDesc.addProperty("displayName", DGU.translateText(item.getDescriptionId()));
        *///?}
        itemDesc.addProperty("name", registryKey.getPath());

        //? if <1.18 || >1.18 {
        itemDesc.addProperty("displayName", DGU.translateText(item.getDescriptionId()));
        //?}
        //? if <1.20.5 {
        /*itemDesc.addProperty("stackSize", item.getMaxStackSize());
        *///?} else {
        itemDesc.addProperty("stackSize", item.getDefaultMaxStackSize());
        //?}

        JsonArray enchantCategoriesArray = new JsonArray();
        //? if <1.20.5 {
        /*getApplicableEnchantmentTargets(item).forEach(enchantCategoriesArray::add);
        *///?}
        //? if =1.18 {

        /*if (item.canBeDepleted()) itemDesc.addProperty("maxDurability", item.getMaxDamage());
        *///?} else if =1.20.5 {
        /*getApplicableEnchantmentTargets(itemRegistry.wrapAsHolder(item)).forEach(enchantCategoriesArray::add);
        *///?} else if =1.21 {
        /*for (Enchantment enchant : DGU.<Enchantment>registry("enchantment")) {
            if (enchant.getSupportedItems().contains(item.builtInRegistryHolder())) {
                String enchantTarget = enchant.getSupportedItems().unwrapKey().get().location().getPath().split("/")[1];
                if (!enchantCategoriesArray.contains(new JsonPrimitive(enchantTarget))) {
                    enchantCategoriesArray.add(enchantTarget);
                }
            }
        }
        *///?} else if >=1.21.3 {
        DGU.<Enchantment>registry("enchantment").stream()
                .map(Enchantment::getSupportedItems)
                .filter(applicableItems -> applicableItems.contains(itemRegistry.wrapAsHolder(item)))
                .map(EnchantmentsDataGenerator::getEnchantmentTargetName)
                .distinct()
                .forEach(enchantCategoriesArray::add);

        //?}
        if (!enchantCategoriesArray.isEmpty()) {
            itemDesc.add("enchantCategories", enchantCategoriesArray);
        }

        //? if <1.20.5 {
        /*if (item.canBeDepleted()) {
        *///?} else if >=1.20.5 <1.21.5 {
        /*if (item.components().has(DataComponents.DAMAGE)) {
        *///?} else {
        if (item.components().has(DataComponents.MAX_DAMAGE)) {
        //?}
            List<Item> repairWithItems = calculateItemsToRepairWith(itemRegistry, item);

            JsonArray fixedWithArray = new JsonArray();
            for (Item repairWithItem : repairWithItems) {
                var repairWithName = itemRegistry.getKey(repairWithItem);
                fixedWithArray.add(repairWithName.getPath());
            }
            if (!fixedWithArray.isEmpty()) {
                itemDesc.add("repairWith", fixedWithArray);
            }
            //? if <1.18 {

            /*int maxDurability = item.getMaxDamage();
            itemDesc.addProperty("maxDurability", maxDurability);
            *///?}

            //? if >1.18 <1.20.5 {
            /*int maxDurability = item.getMaxDamage();
            *///?} else if >=1.20.5 {
            int maxDurability = Objects.requireNonNull(item.components().get(DataComponents.MAX_DAMAGE));
            //?}
            //? if >1.18 {
            itemDesc.addProperty("maxDurability", maxDurability);
            //?}
        }
        return itemDesc;
    }

    @Override
    public String getDataName() {
        return "items";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultArray = new JsonArray();
        Registry<Item> itemRegistry = DGU.registry("item");
        itemRegistry.stream().forEach(item -> resultArray.add(generateItem(itemRegistry, item)));
        return resultArray;
    }
}
