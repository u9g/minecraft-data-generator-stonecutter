package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if >=1.20.5 {
import net.minecraft.core.Holder;
//?}
import net.minecraft.core.Registry;
//? if >=1.20.5 {
import net.minecraft.core.component.DataComponents;
//?}
//? if >=1.20 {
import net.minecraft.core.registries.Registries;
//?}
//? if >=1.20.5 <1.21 {
/*import net.minecraft.data.registries.VanillaRegistries;
*///?}
//? if <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
*///?}
//? if >=1.20.5 <1.21 {
/*import net.minecraft.tags.TagKey;
*///?} else if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//? if <1.20.5 {
/*import net.minecraft.world.item.enchantment.EnchantmentCategory;
*///?}
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemsDataGenerator implements IDataGenerator {

    private static List<Item> calculateItemsToRepairWith(Registry<Item> itemRegistry, Item sourceItem) {
        //? if <1.16 {
        /*ItemStack sourceItemStack = DGU.asStack(sourceItem);
        *///?} else if >=1.16 <1.21.5 {
        /*ItemStack sourceItemStack = sourceItem.getDefaultInstance();
        *///?} else {
        ItemStack sourceItemStack = new ItemStack(sourceItem);
        //?}
        return itemRegistry.stream()
                //? if <1.16 {
                /*.filter(otherItem -> sourceItem.isValidRepairItem(sourceItemStack, DGU.asStack(otherItem)))
                *///?} else if >=1.16 <1.21.3 {
                /*.filter(otherItem -> sourceItem.isValidRepairItem(sourceItemStack, otherItem.getDefaultInstance()))
                *///?} else if >=1.21.3 <1.21.5 {
                /*.filter(otherItem -> sourceItemStack.isValidRepairItem(otherItem.getDefaultInstance()))
                *///?} else {
                .filter(otherItem -> sourceItemStack.isValidRepairItem(new ItemStack(otherItem)))
                //?}
                .collect(Collectors.toList());
    }

    //? if <1.20.5 {
    /*private static Set<String> getApplicableEnchantmentTargets(Item sourceItem) {
        return Arrays.stream(EnchantmentCategory.values())
                .filter(target -> target.canEnchant(sourceItem))
    *///?} else if >=1.20.5 <1.21 {
    /*private static Set<String> getApplicableEnchantmentTargets(Holder<Item> sourceItem) {
        return DGU.getWorld().registryAccess().registryOrThrow(Registries.ENCHANTMENT).stream()
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
        //? if <1.16 {
        /*ResourceLocation registryKey = itemRegistry.getKey(item);
        *///?} else if >=1.16 <1.21.5 {
        /*ResourceLocation registryKey = itemRegistry.getResourceKey(item).orElseThrow().location();
        *///?} else if >=1.21.5 <1.21.11 {
        /*ResourceLocation registryKey = itemRegistry.getKey(item);
        *///?} else {
        Identifier registryKey = itemRegistry.getKey(item);
        //?}

        itemDesc.addProperty("id", itemRegistry.getId(item));
        //? if <1.16 {
        /*itemDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        *///?} else if >=1.18 <=1.18 {
        /*itemDesc.addProperty("displayName", DGU.translateText(item.getDescriptionId()));
        *///?}
        //? if >=1.16 {
        itemDesc.addProperty("name", registryKey.getPath());
        //?}

        //? if <1.18 {
        /*itemDesc.addProperty("displayName", DGU.translateText(item.getDescriptionId()));
        *///?} else if >1.18 {
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
        //? if >=1.18 <=1.18 {

        /*if (item.canBeDepleted()) itemDesc.addProperty("maxDurability", item.getMaxDamage());
        *///?} else if >=1.20.5 <1.21 {
        /*getApplicableEnchantmentTargets(itemRegistry.wrapAsHolder(item)).forEach(enchantCategoriesArray::add);
        *///?}
        //? if <1.21 {
        /*if (!enchantCategoriesArray.isEmpty()) {
        *///?} else if >=1.21 <1.21.3 {
        /*Registry<Enchantment> enchants = DGU.getWorld().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        for (Enchantment enchant : enchants) {
            if (enchant.getSupportedItems().contains(item.builtInRegistryHolder())) {
                String enchantTarget = enchant.getSupportedItems().unwrapKey().get().location().getPath().split("/")[1];
                if (!enchantCategoriesArray.contains(new JsonPrimitive(enchantTarget))) {
                    enchantCategoriesArray.add(enchantTarget);
                }
            }
        }
        *///?} else {
        DGU.getWorld().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).stream()
                .map(Enchantment::getSupportedItems)
                .filter(applicableItems -> applicableItems.contains(itemRegistry.wrapAsHolder(item)))
                .map(EnchantmentsDataGenerator::getEnchantmentTargetName)
                .distinct()
                .forEach(enchantCategoriesArray::add);

        //?}
        //? if >=1.21 {
        if (enchantCategoriesArray.size() > 0) {
        //?}
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
                //? if <1.16 {
                /*ResourceLocation repairWithName = itemRegistry.getKey(repairWithItem);
                fixedWithArray.add(Objects.requireNonNull(repairWithName).getPath());
                *///?} else if >=1.16 <1.21.5 {
                /*ResourceLocation repairWithName = itemRegistry.getResourceKey(repairWithItem).orElseThrow().location();
                *///?} else if >=1.21.5 <1.21.11 {
                /*ResourceLocation repairWithName = itemRegistry.getKey(repairWithItem);
                *///?} else {
                Identifier repairWithName = itemRegistry.getKey(repairWithItem);
                //?}
                //? if >=1.16 {
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
        //? if <1.20 {
        /*Registry<Item> itemRegistry = Registry.ITEM;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Item> itemRegistry = DGU.getWorld().registryAccess().registryOrThrow(Registries.ITEM);
        *///?} else {
        Registry<Item> itemRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.ITEM);
        //?}
        itemRegistry.stream().forEach(item -> resultArray.add(generateItem(itemRegistry, item)));
        return resultArray;
    }
}
