package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.core.Registry;
//? if >=1.20.5 {
import net.minecraft.core.component.DataComponents;
//?}
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.Objects;

public class FoodsDataGenerator implements IDataGenerator {
    public static JsonObject generateFoodDescriptor(Registry<Item> registry, Item foodItem) {
        JsonObject foodDesc = new JsonObject();
        var registryKey = registry.getKey(foodItem);

        foodDesc.addProperty("id", registry.getId(foodItem));
        foodDesc.addProperty("name", registryKey.getPath());

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
        Registry<Item> itemRegistry = DGU.registry("item");
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
