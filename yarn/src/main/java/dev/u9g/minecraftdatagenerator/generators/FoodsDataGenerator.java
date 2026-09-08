package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?}
//? if <1.14 {
/*import net.minecraft.item.FoodItem;
*///?}
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public class FoodsDataGenerator implements IDataGenerator {
    //? if <1.13 {
    /*public static JsonObject generateFoodDescriptor(FoodItem foodItem) {
    *///?} else if =1.13 {
    /*public static JsonObject generateFoodDescriptor(Registry<Item> registry, FoodItem foodItem) {
    *///?} else {
    public static JsonObject generateFoodDescriptor(Registry<Item> registry, Item foodItem) {
    //?}
        JsonObject foodDesc = new JsonObject();
        //? if <1.8.9 {
        /*String registryKey = Registries.ITEMS.getId(foodItem);
        *///?} else if >=1.8.9 <1.13 {
        /*Identifier registryKey = Registries.ITEMS.getIdentifier(foodItem);
        *///?} else {
        Identifier registryKey = registry.getId(foodItem);
        //?}

        //? if <1.13 {
        /*foodDesc.addProperty("id", Registries.ITEMS.getRawId(foodItem));
        *///?}
        //? if <1.8.9 {
        /*foodDesc.addProperty("name", Objects.requireNonNull(registryKey).replace("minecraft:", ""));
        *///?} else if >=1.13 {
        foodDesc.addProperty("id", registry.getRawId(foodItem));
        //?}
        //? if >=1.8.9 {
        foodDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        //?}

        //? if <1.14 {
        /*foodDesc.addProperty("stackSize", foodItem.getMaxCount());
        *///?}
        //? if <1.11.2 {
        /*foodDesc.addProperty("displayName", foodItem.getDisplayName(DGU.stackFor(foodItem)));
        *///?} else if >=1.14 {
        foodDesc.addProperty("stackSize", foodItem.getMaxAmount());
        //?}
        //? if >=1.11.2 {
        foodDesc.addProperty("displayName", DGU.translateText(foodItem.getTranslationKey()));
        //?}
        //? if <1.13 {
        /*float foodPoints = foodItem.getHungerPoints(DGU.stackFor(foodItem));
        float saturationRatio = foodItem.getSaturation(DGU.stackFor(foodItem)) * 2.0F;
        *///?} else if =1.13 {
        /*float foodPoints = foodItem.getHungerPoints(getDefaultStack(foodItem));
        float saturationRatio = foodItem.getSaturation(getDefaultStack(foodItem)) * 2.0F;
        *///?} else {

        var foodSettings = Objects.requireNonNull(foodItem.getFoodSetting());
        float foodPoints = foodSettings.getHunger();
        float saturationRatio = foodSettings.getSaturationModifier() * 2.0F;
        //?}
        float saturation = foodPoints * saturationRatio;

        foodDesc.addProperty("foodPoints", foodPoints);
        foodDesc.addProperty("saturation", saturation);

        foodDesc.addProperty("effectiveQuality", foodPoints + saturation);
        foodDesc.addProperty("saturationRatio", saturationRatio);
        return foodDesc;
    }

    //? if =1.13 {
    /*private static ItemStack getDefaultStack(FoodItem foodItem) {
        return new ItemStack(foodItem);
    }

    *///?}
    @Override
    public String getDataName() {
        return "foods";
    }

    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        //? if <1.13 {
        /*for (Item item : Registries.ITEMS) {
        *///?} else {
        Registry<Item> itemRegistry = Registry.ITEM;
        //?}
        //? if =1.13 {
        /*for (Item item : (Iterable<Item>) itemRegistry) {
        *///?}
            //? if <1.14 {
            /*if (item instanceof FoodItem) {
            *///?}
                //? if <1.13 {
                /*resultsArray.add(generateFoodDescriptor((FoodItem) item));
                *///?} else if =1.13 {
                /*resultsArray.add(generateFoodDescriptor(itemRegistry, (FoodItem) item));
                *///?}
            //? if <1.14 {
            /*}
        }
            *///?} else {
        itemRegistry.stream()
                .filter(Item::isFood)
                .forEach(food -> resultsArray.add(generateFoodDescriptor(itemRegistry, food)));
            //?}
        return resultsArray;
    }
}
