package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.core.Registry;
//? if >=1.20.5 {
import net.minecraft.core.component.DataComponents;
//?}
//? if >=1.20 {
import net.minecraft.core.registries.Registries;
//?}
//? if <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
*///?} else {
import net.minecraft.resources.Identifier;
//?}
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.Objects;

public class FoodsDataGenerator implements IDataGenerator {
    public static JsonObject generateFoodDescriptor(Registry<Item> registry, Item foodItem) {
        JsonObject foodDesc = new JsonObject();
        //? if <1.16 {
        /*ResourceLocation registryKey = registry.getKey(foodItem);
        *///?} else if >=1.16 <1.21.5 {
        /*ResourceLocation registryKey = registry.getResourceKey(foodItem).orElseThrow().location();
        *///?} else if >=1.21.5 <1.21.11 {
        /*ResourceLocation registryKey = registry.getKey(foodItem);
        *///?} else {
        Identifier registryKey = registry.getKey(foodItem);
        //?}

        foodDesc.addProperty("id", registry.getId(foodItem));
        //? if <1.16 {
        /*foodDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        *///?} else {
        foodDesc.addProperty("name", registryKey.getPath());
        //?}

        //? if <1.20.5 {
        /*foodDesc.addProperty("stackSize", foodItem.getMaxStackSize());
        *///?} else {
        foodDesc.addProperty("stackSize", foodItem.getDefaultMaxStackSize());
        //?}
        foodDesc.addProperty("displayName", DGU.translateText(foodItem.getDescriptionId()));

        //? if <1.20.5 {
        /*FoodProperties foodComponent = Objects.requireNonNull(foodItem.getFoodProperties());
        float foodPoints = foodComponent.getNutrition();
        float saturationRatio = foodComponent.getSaturationModifier() * 2.0F;
        float saturation = foodPoints * saturationRatio;
        *///?} else {
        FoodProperties foodComponent = Objects.requireNonNull(foodItem.components().get(DataComponents.FOOD));
        float foodPoints = foodComponent.nutrition();
        float saturation = foodComponent.saturation();
        float saturationRatio = saturation / foodPoints;
        //?}

        foodDesc.addProperty("foodPoints", foodPoints);
        foodDesc.addProperty("saturation", saturation);

        foodDesc.addProperty("effectiveQuality", foodPoints + saturation);
        foodDesc.addProperty("saturationRatio", saturationRatio);
        return foodDesc;
    }

    @Override
    public String getDataName() {
        return "foods";
    }

    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        //? if <1.20 {
        /*Registry<Item> itemRegistry = Registry.ITEM;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Item> itemRegistry = DGU.getWorld().registryAccess().registryOrThrow(Registries.ITEM);
        *///?} else {
        Registry<Item> itemRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.ITEM);
        //?}
        itemRegistry.stream()
                //? if <1.20.5 {
                /*.filter(Item::isEdible)
                *///?} else {
                .filter(i -> i.components().has(DataComponents.FOOD))
                //?}
                .forEach(food -> resultsArray.add(generateFoodDescriptor(itemRegistry, food)));
        return resultsArray;
    }
}
