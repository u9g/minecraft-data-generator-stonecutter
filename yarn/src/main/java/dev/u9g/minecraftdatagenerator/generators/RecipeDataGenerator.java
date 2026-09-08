package dev.u9g.minecraftdatagenerator.generators;

//? if >=1.18 {
import com.google.common.collect.ArrayListMultimap;
//?}
//? if >=1.18 <1.20 {
/*import com.google.common.collect.Lists;
*///?}
//? if >=1.18 {
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
//?}
import com.google.gson.JsonElement;
//? if <1.18 {
/*import com.google.gson.JsonNull;
*///?} else {
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
//?}
//? if >=1.13 {
import net.minecraft.item.Item;
//?}
//? if >=1.18 <1.20.4 {
/*import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
*///?}
//? if >=1.13 <1.20 {
/*import net.minecraft.util.registry.Registry;
*///?} else if >=1.20.4 {
import net.minecraft.recipe.*;
//?}
//? if >=1.21.3 {
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
//?}
//? if >=1.20 {
import net.minecraft.registry.DynamicRegistryManager;
//?}
//? if >=1.21.3 {
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
//?}
//? if >=1.20 {
import net.minecraft.registry.RegistryKeys;
//?}
//? if >=1.18 {

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//?}

public class RecipeDataGenerator implements IDataGenerator {
    //? if >=1.13 {

    private static int getRawIdFor(Item item) {
    //?}
        //? if >=1.13 <1.20 {
        /*return Registry.ITEM.getRawId(item);
        *///?} else if >=1.20 <1.21.3 {
        /*return DGU.getWorld().getRegistryManager().get(RegistryKeys.ITEM).getRawId(item);
        *///?} else if >=1.21.3 {
        return DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ITEM).getRawId(item);
        //?}
    //? if >=1.13 {
    }
    //?}

    @Override
    public String getDataName() {
        return "recipes";
    //? if >=1.21 {
    }

    @Override
    public JsonElement generateDataJson() {
        DynamicRegistryManager registryManager = DGU.getWorld().getRegistryManager();
        JsonObject finalObj = new JsonObject();
        Multimap<Integer, JsonObject> recipes = ArrayListMultimap.create();
    //?}
        //? if >=1.21 <1.21.3 {
        /*for (RecipeEntry<?> recipeE : Objects.requireNonNull(DGU.getWorld()).getRecipeManager().values()) {
        *///?} else if >=1.21.3 {
        for (RecipeEntry<?> recipeE : Objects.requireNonNull(DGU.getWorld()).getServer().getRecipeManager().values()) {
        //?}
            //? if >=1.21 {
            Recipe<?> recipe = recipeE.value();
            if (recipe instanceof ShapedRecipe sr) {
                generateShapedRecipe(registryManager, finalObj, sr, 0);
            } else if (recipe instanceof ShapelessRecipe sl) {
                var ingredients = new JsonArray();
            //?}
                //? if >=1.21 <1.21.3 {
                /*for (Ingredient ingredient : sl.getIngredients()) {
                    if (ingredient.isEmpty()) continue;
                    ingredients.add(getRawIdFor(ingredient.getMatchingStacks()[0].getItem()));
                *///?} else if >=1.21.3 {
                for (Ingredient ingredient : sl.getIngredientPlacement().getIngredients()) {
                    if (ingredient.getMatchingItems().isEmpty()) continue;
                    ingredients.add(getRawIdFor(ingredient.getMatchingItems().getFirst().value()));
                //?}
                //? if >=1.21 {
                }
                var rootRecipeObject = new JsonObject();
                rootRecipeObject.add("ingredients", ingredients);
                var resultObject = new JsonObject();
                //?}
                //? if >=1.21 <1.21.3 {
                /*resultObject.addProperty("id", getRawIdFor(sl.getResult(registryManager).getItem()));
                resultObject.addProperty("count", sl.getResult(registryManager).getCount());
                *///?} else if >=1.21.3 {
                resultObject.addProperty("id", getRawIdFor(sl.craft(CraftingRecipeInput.EMPTY, registryManager).getItem()));
                resultObject.addProperty("count", sl.craft(CraftingRecipeInput.EMPTY, registryManager).getCount());
                //?}
                //? if >=1.21 {
                rootRecipeObject.add("result", resultObject);
                //?}
                //? if >=1.21 <1.21.3 {
                /*recipes.put(getRawIdFor(sl.getResult(registryManager).getItem()), rootRecipeObject);
                *///?} else if >=1.21.3 {
                recipes.put(getRawIdFor(sl.craft(CraftingRecipeInput.EMPTY, registryManager).getItem()), rootRecipeObject);
                //?}
            //? if >=1.21 {
            }
        }
        recipes.forEach((a, b) -> {
            if (!finalObj.has(a.toString())) {
                finalObj.add(a.toString(), new JsonArray());
            }
            finalObj.get(a.toString()).getAsJsonArray().add(b);
        });
        return finalObj;
            //?}
    }

    //? if >=1.20.5 {
    private void generateShapedRecipe(DynamicRegistryManager registryManager, JsonObject finalObj, ShapedRecipe sr, int n) {
        boolean hasIncremented = false;
        var ingredients = sr.getIngredients();
        List<Integer> ingr = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (i >= ingredients.size()) {
    //?}
                //? if >=1.20.5 <1.21.3 {
                /*ingr.add(-1);
                *///?} else if >=1.21.3 {
                ingr.add(null);
                //?}
                //? if >=1.20.5 {
                continue;
            }
            var stacks = ingredients.get(i);
                //?}
            //? if >=1.20.5 <1.21.3 {
            /*var matching = stacks.getMatchingStacks(); // FIXME: fix when there are more than one matching stack
            if (matching.length == 0) {
                ingr.add(-1);
            } else if (matching.length > n){
                ingr.add(getRawIdFor(matching[n].getItem()));
            *///?} else if >=1.21.3 {
            if (stacks.isEmpty()) {
                ingr.add(null);
                continue;
            }
            var matching = stacks.get().getMatchingItems(); // FIXME: fix when there are more than one matching stack
            if (matching.isEmpty()) {
                ingr.add(null);
                continue;
            } else if (matching.size() > n){
                ingr.add(getRawIdFor(matching.get(n).value()));
            //?}
            //? if >=1.20.5 {
            } else {
            //?}
                //? if >=1.20.5 <1.21.3 {
                /*ingr.add(getRawIdFor(matching[0].getItem()));
                *///?} else if >=1.21.3 {
                ingr.add(getRawIdFor(matching.getFirst().value()));
                //?}
            //? if >=1.20.5 {
            }
            //?}
            //? if >=1.20.5 <1.21.3 {
            /*if (matching.length-1 > n && !hasIncremented) {
            *///?} else if >=1.21.3 {
            if (matching.size()-1 > n && !hasIncremented) {
            //?}
                //? if >=1.20.5 {
                generateShapedRecipe(registryManager, finalObj, sr, n+1);
                hasIncremented = true;
            }
        }

                //?}
            //? if >=1.20.5 <1.21.3 {
            /*JsonArray inShape = new JsonArray();
            *///?} else if >=1.21.3 {
        JsonArray inShape = new JsonArray();
            //?}

            //? if >=1.20.5 <1.21.3 {
            /*var iter = ingr.iterator();
            for (int y = 0; y < sr.getHeight(); y++) {
                var jsonRow = new JsonArray();
                for (int z = 0; z < sr.getWidth(); z++) {
            *///?}
                    //? if >=1.20.5 <1.21 {
                    /*jsonRow.add(iter.next());
                    *///?} else if >=1.21 <1.21.3 {
                    /*int value = iter.next();
                    jsonRow.add(value == -1 ? null : value);
                    *///?}
                //? if >=1.20.5 <1.21.3 {
                /*}
                inShape.add(jsonRow);
                *///?} else if >=1.21.3 {
        var iter = ingr.iterator();
        for (int y = 0; y < sr.getHeight(); y++) {
            var jsonRow = new JsonArray();
            for (int z = 0; z < sr.getWidth(); z++) {
                jsonRow.add(iter.next());
                //?}
            //? if >=1.20.5 {
            }
            //?}
            //? if >=1.21.3 {
            inShape.add(jsonRow);
        }
            //?}
        //? if >=1.20.5 {

        JsonObject finalRecipe = new JsonObject();
        finalRecipe.add("inShape", inShape);

        var resultObject = new JsonObject();
        //?}
        //? if >=1.20.5 <1.21.3 {
        /*resultObject.addProperty("id", getRawIdFor(sr.getResult(registryManager).getItem()));
        resultObject.addProperty("count", sr.getResult(registryManager).getCount());
        *///?} else if >=1.21.3 {
        resultObject.addProperty("id", getRawIdFor(sr.craft(CraftingRecipeInput.EMPTY, registryManager).getItem()));
        resultObject.addProperty("count", sr.craft(CraftingRecipeInput.EMPTY, registryManager).getCount());
        //?}
        //? if >=1.20.5 {
        finalRecipe.add("result", resultObject);

        //?}
        //? if >=1.20.5 <1.21.3 {
        /*String id = ((Integer) getRawIdFor(sr.getResult(registryManager).getItem())).toString();
        *///?} else if >=1.21.3 {
        String id = ((Integer) getRawIdFor(sr.craft(CraftingRecipeInput.EMPTY, registryManager).getItem())).toString();
        //?}
        //? if >=1.20.5 {

        if (!finalObj.has(id)) {
            finalObj.add(id, new JsonArray());
        }
        finalObj.get(id).getAsJsonArray().add(finalRecipe);
    }
        //?}

    //? if <1.21 {
    /*@Override
    public JsonElement generateDataJson() {
    *///?}
//        JsonObject finalObj = new JsonObject();
//        Multimap<Integer, JsonObject> recipes = ArrayListMultimap.create();
//        for (Recipe<?> recipe : Objects.requireNonNull(DGU.getWorld()).getRecipeManager().values()) {
//            if (recipe instanceof ShapedRecipe sr) {
//                var ingredients = sr.getIngredients();
//                List<Integer> ingr = new ArrayList<>();
//                for (int i = 0; i < 9; i++) {
//                    if (i >= ingredients.size()) {
//                        ingr.add(-1);
//                        continue;
        //? if >=1.20 <1.21 {
        /*DynamicRegistryManager registryManager = DGU.getWorld().getRegistryManager();
        *///?}
        //? if >=1.18 <1.21 {
        /*JsonObject finalObj = new JsonObject();
        Multimap<Integer, JsonObject> recipes = ArrayListMultimap.create();
        *///?}
        //? if >=1.18 <1.20.4 {
        /*for (Recipe<?> recipe : Objects.requireNonNull(DGU.getWorld()).getRecipeManager().values()) {
        *///?} else if >=1.20.4 <1.21 {
        /*for (RecipeEntry<?> recipeE : Objects.requireNonNull(DGU.getWorld()).getRecipeManager().values()) {
            Recipe<?> recipe = recipeE.value();
        *///?}
            //? if >=1.18 <1.21 {
            /*if (recipe instanceof ShapedRecipe sr) {
            *///?}

                //? if >=1.18 <1.21 {
                /*var ingredients = sr.getIngredients();
                List<Integer> ingr = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    if (i >= ingredients.size()) {
                *///?}
                        //? if >=1.18 <1.20.5 {
                        /*ingr.add(-1);
                        *///?} else if >=1.20.5 <1.21 {
                        /*ingr.add(null);
                        *///?}
                        //? if >=1.18 <1.21 {
                        /*continue;
                    }
                    var stacks = ingredients.get(i);
                    var matching = stacks.getMatchingStacks();
                    if (matching.length == 0) {
                        *///?}
                        //? if >=1.18 <1.20.5 {
                        /*ingr.add(-1);
                        *///?} else if >=1.20.5 <1.21 {
                        /*ingr.add(null);
                        *///?}
                    //? if >=1.18 <1.21 {
                    /*} else {
                        ingr.add(getRawIdFor(matching[0].getItem()));
                    }
                }
                    *///?}
                //? if >=1.18 <1.20 {
                /*Lists.reverse(ingr);
                *///?}
                //Lists.reverse(ingr);
                //? if >=1.18 <1.21 {

                /*JsonArray inShape = new JsonArray();

                *///?}

                //? if >=1.18 <1.21 {
                /*var iter = ingr.iterator();
                *///?}
                //? if >=1.18 <1.20 {
                /*for (int y = 0; y < 3; y++) {
                *///?} else if >=1.20 <1.21 {
                /*for (int y = 0; y < sr.getHeight(); y++) {
                *///?}
                    //? if >=1.18 <1.21 {
                    /*var jsonRow = new JsonArray();
                    *///?}
                    //? if >=1.18 <1.20 {
                    /*int one = iter.next();
                    int two = iter.next();
                    int three = iter.next();
                    if (y > 0 && one == -1 && two == -1 && three == -1) continue;
                    jsonRow.add(one);
                    jsonRow.add(two);
                    jsonRow.add(three);
                    *///?} else if >=1.20 <1.21 {
                    /*for (int z = 0; z < sr.getWidth(); z++) {
                        jsonRow.add(iter.next());
                    }
                    *///?}
                    //? if >=1.18 <1.21 {
                    /*inShape.add(jsonRow);
                }

                JsonObject finalRecipe = new JsonObject();
                finalRecipe.add("inShape", inShape);

                var resultObject = new JsonObject();
                    *///?}
                //? if >=1.18 <1.20 {
                /*resultObject.addProperty("id", getRawIdFor(sr.getOutput().getItem()));
                resultObject.addProperty("count", sr.getOutput().getCount());
                *///?} else if >=1.20 <1.20.4 {
                /*resultObject.addProperty("id", getRawIdFor(sr.getOutput(registryManager).getItem()));
                resultObject.addProperty("count", sr.getOutput(registryManager).getCount());
                *///?} else if >=1.20.4 <1.21 {
                /*resultObject.addProperty("id", getRawIdFor(sr.getResult(registryManager).getItem()));
                resultObject.addProperty("count", sr.getResult(registryManager).getCount());
                *///?}
                //? if >=1.18 <1.21 {
                /*finalRecipe.add("result", resultObject);

                *///?}
                //? if >=1.18 <1.20 {
                /*String id = ((Integer) getRawIdFor(sr.getOutput().getItem())).toString();
                *///?} else if >=1.20 <1.20.4 {
                /*String id = ((Integer) getRawIdFor(sr.getOutput(registryManager).getItem())).toString();
                *///?} else if >=1.20.4 <1.21 {
                /*String id = ((Integer) getRawIdFor(sr.getResult(registryManager).getItem())).toString();
                *///?}
                //? if >=1.18 <1.21 {

                /*if (!finalObj.has(id)) {
                    finalObj.add(id, new JsonArray());
                }
                finalObj.get(id).getAsJsonArray().add(finalRecipe);
                *///?}


//                var input = new JsonArray();
//                var ingredients = sr.getIngredients().stream().toList();
//                for (int y = 0; y < sr.getHeight(); y++) {
//                    var arr = new JsonArray();
//                    for (int x = 0; x < sr.getWidth(); x++) {
//                        if ((y*3)+x >= ingredients.size()) {
//                            arr.add(JsonNull.INSTANCE);
//                            continue;
//                        }
//                        var ingredient = ingredients.get((y*3)+x).getMatchingStacks(); // FIXME: fix when there are more than one matching stack
//                        if (ingredient.length == 0) {
//                            arr.add(JsonNull.INSTANCE);
//                        } else {
//                            arr.add(getRawIdFor(ingredient[0].getItem()));
//                        }
//                    }
//                    var stacks = ingredients.get(i);
////                    var matching = stacks.getMatchingStacks();
////                    if (matching.length == 0) {
////                        ingr.add(-1);
////                    } else {
////                        ingr.add(getRawIdFor(matching[0].getItem()));
////                    }
//                    input.add(arr);
//                }
//                Lists.reverse(ingr);
//
//                JsonArray inShape = new JsonArray();
//
//                var iter = ingr.iterator();
//                for (int y = 0; y < 3; y++) {
//                    var jsonRow = new JsonArray();
//                    int one = iter.next();
//                    int two = iter.next();
//                    int three = iter.next();
//                    if (y > 0 && one == -1 && two == -1 && three == -1) continue;
//                    jsonRow.add(one);
//                    jsonRow.add(two);
//                    jsonRow.add(three);
//                    inShape.add(jsonRow);
//                }
//
//                JsonObject finalRecipe = new JsonObject();
//                finalRecipe.add("inShape", inShape);
//
//                var rootRecipeObject = new JsonObject();
//                rootRecipeObject.add("inShape", input);
//                var resultObject = new JsonObject();
//                resultObject.addProperty("id", getRawIdFor(sr.getOutput().getItem()));
//                resultObject.addProperty("count", sr.getOutput().getCount());
//                finalRecipe.add("result", resultObject);
//
//                String id = ((Integer) getRawIdFor(sr.getOutput().getItem())).toString();
//
//                if (!finalObj.has(id)) {
//                    finalObj.add(id, new JsonArray());
//                }
//                finalObj.get(id).getAsJsonArray().add(finalRecipe);
////                var input = new JsonArray();
////                var ingredients = sr.getIngredients().stream().toList();
////                for (int y = 0; y < sr.getHeight(); y++) {
////                    var arr = new JsonArray();
////                    for (int x = 0; x < sr.getWidth(); x++) {
////                        if ((y*3)+x >= ingredients.size()) {
////                            arr.add(JsonNull.INSTANCE);
////                            continue;
////                        }
////                        var ingredient = ingredients.get((y*3)+x).getMatchingStacks(); // FIXME: fix when there are more than one matching stack
////                        if (ingredient.length == 0) {
////                            arr.add(JsonNull.INSTANCE);
////                        } else {
////                            arr.add(getRawIdFor(ingredient[0].getItem()));
////                        }
////                    }
////                    input.add(arr);
////                }
////                var rootRecipeObject = new JsonObject();
////                rootRecipeObject.add("inShape", input);
////                var resultObject = new JsonObject();
////                resultObject.addProperty("id", getRawIdFor(sr.getOutput().getItem()));
////                resultObject.addProperty("count", sr.getOutput().getCount());
////                rootRecipeObject.add("result", resultObject);
////                recipes.put(getRawIdFor(sr.getOutput().getItem()), rootRecipeObject);
//            } else if (recipe instanceof ShapelessRecipe sl) {

//                var ingredients = new JsonArray();
//                for (Ingredient ingredient : sl.getIngredients()) {
//                    if (ingredient.isEmpty()) continue;
////                    ingredients.add(getRawIdFor(ingredient.getMatchingStacks()[0].getItem()));
//                }
//                var rootRecipeObject = new JsonObject();
//                rootRecipeObject.add("ingredients", ingredients);
//                var resultObject = new JsonObject();
//                resultObject.addProperty("id", getRawIdFor(sl.getOutput().getItem()));
//                resultObject.addProperty("count", sl.getOutput().getCount());
//                rootRecipeObject.add("result", resultObject);
//? if <1.18 {
/*//                recipes.put(getRawIdFor(sl.getOutput().getItem()), rootRecipeObject);
//            }
//        }
//        recipes.forEach((a, b) -> {
//            if (!finalObj.has(a.toString())) {
//                finalObj.add(a.toString(), new JsonArray());
//            }
//            finalObj.get(a.toString()).getAsJsonArray().add(b);
//        });
//        return finalObj;
        return JsonNull.INSTANCE;
    }

    @Override
    public boolean isEnabled() {
        return false; // TODO: Implement this
*///?} else if >=1.18 <1.21 {
/*//                recipes.put(getRawIdFor(sr.getOutput().getItem()), rootRecipeObject);
            } else if (recipe instanceof ShapelessRecipe sl) {
                var ingredients = new JsonArray();
                for (Ingredient ingredient : sl.getIngredients()) {
                    if (ingredient.isEmpty()) continue;
                    ingredients.add(getRawIdFor(ingredient.getMatchingStacks()[0].getItem()));
                }
                var rootRecipeObject = new JsonObject();
                rootRecipeObject.add("ingredients", ingredients);
                var resultObject = new JsonObject();
*///?}
                //? if >=1.18 <1.20 {
                /*resultObject.addProperty("id", getRawIdFor(sl.getOutput().getItem()));
                resultObject.addProperty("count", sl.getOutput().getCount());
                *///?} else if >=1.20 <1.20.4 {
                /*resultObject.addProperty("id", getRawIdFor(sl.getOutput(registryManager).getItem()));
                resultObject.addProperty("count", sl.getOutput(registryManager).getCount());
                *///?} else if >=1.20.4 <1.21 {
                /*resultObject.addProperty("id", getRawIdFor(sl.getResult(registryManager).getItem()));
                resultObject.addProperty("count", sl.getResult(registryManager).getCount());
                *///?}
                //? if >=1.18 <1.21 {
                /*rootRecipeObject.add("result", resultObject);
                *///?}
                //? if >=1.18 <1.20 {
                /*recipes.put(getRawIdFor(sl.getOutput().getItem()), rootRecipeObject);
                *///?} else if >=1.20 <1.20.4 {
                /*recipes.put(getRawIdFor(sl.getOutput(registryManager).getItem()), rootRecipeObject);
                *///?} else if >=1.20.4 <1.21 {
                /*recipes.put(getRawIdFor(sl.getResult(registryManager).getItem()), rootRecipeObject);
                *///?}
            //? if >=1.18 <1.21 {
            /*}
        }
        recipes.forEach((a, b) -> {
            if (!finalObj.has(a.toString())) {
                finalObj.add(a.toString(), new JsonArray());
            }
            finalObj.get(a.toString()).getAsJsonArray().add(b);
        });
        return finalObj;
            *///?}
    //? if <1.21 {
    /*}
    *///?}

//
//    private static int getRawIdFor (Item item) {
//        return Registry.ITEM.getRawId(item);
//    }
}
