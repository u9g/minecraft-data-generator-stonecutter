package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.12.2 {
/*import com.google.gson.JsonPrimitive;
*///?}
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.mixin.BiomeAccessor;
*///?}
//? if >=1.9.4 <1.10.2 {
/*import dev.u9g.minecraftdatagenerator.util.DGU;
*///?} else if >=1.12.2 {
import dev.u9g.minecraftdatagenerator.util.DGU;
//?}
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?} else if >=1.14 {
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
//?}
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
//? if >=1.8.9 {
import net.minecraft.block.RedstoneWireBlock;
//?}
//? if >=1.9.4 <1.14 {
/*import net.minecraft.client.BlockColors;
*///?} else if >=1.14 {
import net.minecraft.client.color.block.BlockColors;
//?}
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
//? if >=1.8.9 {
import net.minecraft.util.Identifier;
//?}
//? if >=1.9.4 <1.10.2 {
/*import net.minecraft.util.math.BlockPos;
*///?} else if >=1.12.2 {
import net.minecraft.util.math.BlockPos;
//?}
import net.minecraft.util.math.MathHelper;
//? if >=1.13 {
import net.minecraft.util.registry.Registry;
//?}
import net.minecraft.world.biome.Biome;

//? if >=1.8.9 <1.9.4 {
/*import java.lang.reflect.Method;
*///?}
import java.util.*;

public class TintsDataGenerator implements IDataGenerator {
    //? if <1.13 {
    /*public static BiomeTintColors generateBiomeTintColors() {
    *///?} else {
    public static BiomeTintColors generateBiomeTintColors(Registry<Biome> biomeRegistry) {
    //?}
        BiomeTintColors colors = new BiomeTintColors();
        //? if <1.13 {

        /*for (Biome biome : Registries.BIOMES) {
        *///?}
            //? if <1.8.9 {
            /*double d = MathHelper.clamp(biome.temperature, 0.0f, 1.0f);
            double e = MathHelper.clamp(biome.downfall, 0.0f, 1.0f);
            *///?} else if >=1.8.9 <1.9.4 {
            /*double d = MathHelper.clamp(biome.temperature, 0.0F, 1.0F);
            double e = MathHelper.clamp(biome.downfall, 0.0F, 1.0F);
            *///?} else if >=1.13 <1.14 {
        /*for (Biome biome : (Iterable<Biome>) biomeRegistry) {
            *///?} else if >=1.14 {

        biomeRegistry.forEach(biome -> {
            //?}
            //? if >=1.9.4 {
            double d = MathHelper.clamp(biome.getTemperature(), 0.0f, 1.0f);
            double e = MathHelper.clamp(biome.getRainfall(), 0.0f, 1.0f);
            //?}

            int biomeGrassColor = GrassColors.getColor(d, e);
            int biomeFoliageColor = FoliageColors.getColor(d, e);
            //? if <1.13 {
            /*int biomeWaterColor = ((BiomeAccessor) biome).waterColor();
            *///?} else {
            int biomeWaterColor = biome.getWaterColor();
            //?}

            colors.grassColoursMap.computeIfAbsent(biomeGrassColor, k -> new ArrayList<>()).add(biome);
            colors.foliageColoursMap.computeIfAbsent(biomeFoliageColor, k -> new ArrayList<>()).add(biome);
            colors.waterColourMap.computeIfAbsent(biomeWaterColor, k -> new ArrayList<>()).add(biome);
        //? if <1.14 {
        /*}
        *///?} else {
        });
        //?}
        return colors;
    }

    //? if >=1.8.9 {
    public static Map<Integer, Integer> generateRedstoneTintColors() {
        Map<Integer, Integer> resultColors = new LinkedHashMap<>();

    //?}
        //? if >=1.8.9 <1.9.4 {
        /*RedstoneWireBlock redstoneWireBlock = new RedstoneWireBlock();
        try {
            Method colorIntensity = RedstoneWireBlock.class.getDeclaredMethod("getColorIntensity", int.class);
            colorIntensity.setAccessible(true);
            for (int redstoneLevel : RedstoneWireBlock.POWER.getValues()) {
                int color = (int) colorIntensity.invoke(redstoneWireBlock, redstoneLevel);
                resultColors.put(redstoneLevel, color);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        *///?} else if >=1.9.4 {
        for (int redstoneLevel : RedstoneWireBlock.POWER.getValues()) {
        //?}
            //? if >=1.9.4 <1.14 {
            /*int color = RedstoneWireBlock.getColorIntensity(redstoneLevel);
            *///?} else if >=1.14 {
            int color = RedstoneWireBlock.getWireColor(redstoneLevel);
            //?}
            //? if >=1.9.4 {
            resultColors.put(redstoneLevel, color);
            //?}
        //? if >=1.8.9 {
        }
        //?}
        //? if >=1.9.4 {
        return resultColors;
    }
        //?}

        //? if >=1.8.9 <1.9.4 {
        /*return resultColors;
        *///?} else if >=1.9.4 {
    private static int getBlockColor(Block block) {
        //?}
        //? if >=1.9.4 <1.10.2 {
        /*return BlockColors.create().method_12157(block.getDefaultState(), DGU.getWorld(), BlockPos.ORIGIN, 0);
        *///?} else if >=1.10.2 <1.12.2 {
        /*return BlockColors.create().method_13410(block.getDefaultState());
        *///?} else if >=1.12.2 <1.14 {
        /*return BlockColors.create().method_13410(block.getDefaultState(), DGU.getWorld(), BlockPos.ORIGIN);
        *///?} else if >=1.14 {
        return BlockColors.create().getColor(block.getDefaultState(), DGU.getWorld(), BlockPos.ORIGIN);
        //?}
    //? if >=1.8.9 {
    }

    //?}
    public static Map<Block, Integer> generateConstantTintColors() {
        Map<Block, Integer> resultColors = new LinkedHashMap<>();
        // FIXME: ?
        // resultColors.put(Blocks.BIRCH_LEAVES, FoliageColors.getBirchColor());
        // resultColors.put(Blocks.SPRUCE_LEAVES, FoliageColors.getSpruceColor());
        //? if >=1.13 {

        resultColors.put(Blocks.BIRCH_LEAVES, FoliageColors.getBirchColor());
        resultColors.put(Blocks.SPRUCE_LEAVES, FoliageColors.getSpruceColor());
        //?}

        //? if <1.8.9 {
        /*resultColors.put(Registries.BLOCKS.get("waterlily"), Blocks.LILY_PAD.getColor());
        *///?} else if >=1.8.9 <1.9.4 {
        /*resultColors.put(Blocks.LILY_PAD, Blocks.LILY_PAD.getColor());
        *///?} else {
        resultColors.put(Blocks.LILY_PAD, getBlockColor(Blocks.LILY_PAD));
        //?}
        // FIXME: ?
        // resultColors.put(Blocks.ATTACHED_MELON_STEM, getBlockColor(Blocks.ATTACHED_MELON_STEM));
        // resultColors.put(Blocks.ATTACHED_PUMPKIN_STEM, getBlockColor(Blocks.ATTACHED_PUMPKIN_STEM));
        //? if >=1.13 {
        resultColors.put(Blocks.ATTACHED_MELON_STEM, getBlockColor(Blocks.ATTACHED_MELON_STEM));
        resultColors.put(Blocks.ATTACHED_PUMPKIN_STEM, getBlockColor(Blocks.ATTACHED_PUMPKIN_STEM));
        //?}

        //not really constant, depend on the block age, but kinda have to be handled since textures are literally white without them
        //? if <1.8.9 {
        /*resultColors.put(Registries.BLOCKS.get("melon_stem"), Blocks.MELON_STEM.getColor());
        resultColors.put(Registries.BLOCKS.get("pumpkin_stem"), Blocks.PUMPKIN_STEM.getColor());
        *///?} else if >=1.8.9 <1.9.4 {
        /*resultColors.put(Blocks.MELON_STEM, Blocks.MELON_STEM.getColor());
        resultColors.put(Blocks.PUMPKIN_STEM, Blocks.PUMPKIN_STEM.getColor());
        *///?} else {
        resultColors.put(Blocks.MELON_STEM, getBlockColor(Blocks.MELON_STEM));
        resultColors.put(Blocks.PUMPKIN_STEM, getBlockColor(Blocks.PUMPKIN_STEM));
        //?}

        return resultColors;
    }

    //? if <1.13 {
    /*private static JsonObject encodeBiomeColorMap(Map<Integer, List<Biome>> colorsMap) {
    *///?} else {
    private static JsonObject encodeBiomeColorMap(Registry<Biome> biomeRegistry, Map<Integer, List<Biome>> colorsMap) {
    //?}
        JsonArray resultColorsArray = new JsonArray();
        //? if <1.14 {
        /*for (Map.Entry<Integer, List<Biome>> entry : colorsMap.entrySet()) {
        *///?} else {
        for (var entry : colorsMap.entrySet()) {
        //?}
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            for (Biome biome : entry.getValue()) {
                //? if <1.9.4 {
                /*keysArray.add(new JsonPrimitive(biome.name));
                *///?} else if >=1.9.4 <1.13 {
                /*Identifier registryKey = Registries.BIOMES.getIdentifier(biome);
                *///?}
                //? if >=1.9.4 <1.12.2 {
                /*keysArray.add(new JsonPrimitive(Objects.requireNonNull(registryKey).getPath()));
                *///?} else if >=1.13 {
                Identifier registryKey = biomeRegistry.getId(biome);
                //?}
                //? if >=1.12.2 {
                keysArray.add(Objects.requireNonNull(registryKey).getPath());
                //?}
            }

            entryObject.add("keys", keysArray);
            entryObject.addProperty("color", entry.getKey());
            resultColorsArray.add(entryObject);
        }

        JsonObject resultObject = new JsonObject();
        resultObject.add("data", resultColorsArray);
        return resultObject;
    }

    private static JsonObject encodeRedstoneColorMap(Map<Integer, Integer> colorsMap) {
        JsonArray resultColorsArray = new JsonArray();
        //? if <1.14 {
        /*for (Map.Entry<Integer, Integer> entry : colorsMap.entrySet()) {
        *///?} else {
        for (var entry : colorsMap.entrySet()) {
        //?}
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            //? if <1.12.2 {
            /*keysArray.add(new JsonPrimitive(entry.getKey()));
            *///?} else {
            keysArray.add(entry.getKey());
            //?}

            entryObject.add("keys", keysArray);
            entryObject.addProperty("color", entry.getValue());
            resultColorsArray.add(entryObject);
        }

        JsonObject resultObject = new JsonObject();
        resultObject.add("data", resultColorsArray);
        return resultObject;
    }

    //? if <1.13 {
    /*private static JsonObject encodeBlocksColorMap(Map<Block, Integer> colorsMap) {
    *///?} else {
    private static JsonObject encodeBlocksColorMap(Registry<Block> blockRegistry, Map<Block, Integer> colorsMap) {
    //?}
        JsonArray resultColorsArray = new JsonArray();
        //? if <1.14 {
        /*for (Map.Entry<Block, Integer> entry : colorsMap.entrySet()) {
        *///?} else {
        for (var entry : colorsMap.entrySet()) {
        //?}
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            //? if <1.8.9 {
            /*String registryKey = Registries.BLOCKS.getId(entry.getKey());
            keysArray.add(new JsonPrimitive(Objects.requireNonNull(registryKey).replace("minecraft:", "")));
            *///?} else if >=1.8.9 <1.13 {
            /*Identifier registryKey = Registries.BLOCKS.getIdentifier(entry.getKey());
            *///?}
            //? if >=1.8.9 <1.12.2 {
            /*keysArray.add(new JsonPrimitive(Objects.requireNonNull(registryKey).getPath()));
            *///?} else if >=1.13 {
            Identifier registryKey = blockRegistry.getId(entry.getKey());
            //?}
            //? if >=1.12.2 {
            keysArray.add(Objects.requireNonNull(registryKey).getPath());
            //?}

            entryObject.add("keys", keysArray);
            entryObject.addProperty("color", entry.getValue());
            resultColorsArray.add(entryObject);
        }

        JsonObject resultObject = new JsonObject();
        resultObject.add("data", resultColorsArray);
        return resultObject;
    }

    @Override
    public String getDataName() {
        return "tints";
    }

    @Override
    public JsonObject generateDataJson() {
        //? if <1.13 {
        /*BiomeTintColors biomeTintColors = generateBiomeTintColors();
        *///?} else {
//        DynamicRegistryManager registryManager = DynamicRegistryManager.create();
        Registry<Biome> biomeRegistry = Registry.BIOME;
        Registry<Block> blockRegistry = Registry.BLOCK;

        BiomeTintColors biomeTintColors = generateBiomeTintColors(biomeRegistry);
        //?}
        //? if >=1.8.9 {
        Map<Integer, Integer> redstoneColors = generateRedstoneTintColors();
        //?}
        //? if <1.14 {
        /*Map<Block, Integer> constantTintColors = generateConstantTintColors();
        *///?} else {
        Map<Block, Integer> constantTintColors = Collections.emptyMap();

        EnvType currentEnvironment = FabricLoader.getInstance().getEnvironmentType();
        if (currentEnvironment == EnvType.CLIENT) {
            constantTintColors = generateConstantTintColors();
        }
        //?}

        JsonObject resultObject = new JsonObject();

        //? if <1.13 {
        /*resultObject.add("grass", encodeBiomeColorMap(biomeTintColors.grassColoursMap));
        resultObject.add("foliage", encodeBiomeColorMap(biomeTintColors.foliageColoursMap));
        resultObject.add("water", encodeBiomeColorMap(biomeTintColors.waterColourMap));
        *///?} else {
        resultObject.add("grass", encodeBiomeColorMap(biomeRegistry, biomeTintColors.grassColoursMap));
        resultObject.add("foliage", encodeBiomeColorMap(biomeRegistry, biomeTintColors.foliageColoursMap));
        resultObject.add("water", encodeBiomeColorMap(biomeRegistry, biomeTintColors.waterColourMap));
        //?}

        //? if >=1.8.9 {
        resultObject.add("redstone", encodeRedstoneColorMap(redstoneColors));
        //?}
        //? if <1.13 {
        /*resultObject.add("constant", encodeBlocksColorMap(constantTintColors));
        *///?} else {
        resultObject.add("constant", encodeBlocksColorMap(blockRegistry, constantTintColors));
        //?}

        return resultObject;
    }

    public static class BiomeTintColors {
        final Map<Integer, List<Biome>> grassColoursMap = new LinkedHashMap<>();
        final Map<Integer, List<Biome>> foliageColoursMap = new LinkedHashMap<>();
        final Map<Integer, List<Biome>> waterColourMap = new LinkedHashMap<>();
    }
}
