package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if =1.16 {
/*import dev.u9g.minecraftdatagenerator.mixin.BiomeEffectsAccessor;
*///?}
import dev.u9g.minecraftdatagenerator.util.DGU;
import dev.u9g.minecraftdatagenerator.util.EmptyRenderBlockView;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
//? if >=1.17 {
import net.minecraft.core.RegistryAccess;
//?}
//? if >=1.20 {
import net.minecraft.core.registries.Registries;
//?}
//? if <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
*///?}
import net.minecraft.util.Mth;
//? if =1.21.3 {
/*import net.minecraft.util.ARGB;
*///?} else if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?}
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;

import java.util.*;



public class TintsDataGenerator implements IDataGenerator {
    public static BiomeTintColors generateBiomeTintColors(Registry<Biome> biomeRegistry) {
        BiomeTintColors colors = new BiomeTintColors();

        biomeRegistry.forEach(biome -> {
            //? if <1.17 {
            /*double d = Mth.clamp(biome.getTemperature(), 0.0f, 1.0f);
            double e = Mth.clamp(biome.getDownfall(), 0.0f, 1.0f);

            int biomeGrassColor = GrassColor.get(d, e);
            int biomeFoliageColor = FoliageColor.get(d, e);
            *///?}
            //? if <1.16 {
            /*int biomeWaterColor = biome.getWaterColor();
            *///?} else if =1.16 {
            /*int biomeWaterColor = ((BiomeEffectsAccessor) biome.getSpecialEffects()).waterColor();
            *///?} else {
            int biomeGrassColor = biome.getGrassColor(0.0, 0.0);
            int biomeFoliageColor = biome.getFoliageColor();
            int biomeWaterColor = biome.getWaterColor();
            //?}

            colors.grassColoursMap.computeIfAbsent(biomeGrassColor, k -> new ArrayList<>()).add(biome);
            colors.foliageColoursMap.computeIfAbsent(biomeFoliageColor, k -> new ArrayList<>()).add(biome);
            colors.waterColourMap.computeIfAbsent(biomeWaterColor, k -> new ArrayList<>()).add(biome);
        });
        return colors;
    }

    public static Map<Integer, Integer> generateRedstoneTintColors() {
        Map<Integer, Integer> resultColors = new LinkedHashMap<>();

        for (int redstoneLevel : RedStoneWireBlock.POWER.getPossibleValues()) {
            //? if <1.16 {
            /*int color = RedStoneWireBlock.getColorForData(redstoneLevel);
            *///?} else if >=1.16 <1.21.3 {
            /*int color = RedStoneWireBlock.getColorForPower(redstoneLevel);
            *///?} else {
            // Remove the unintended alpha channel from the redstone tint color
            int color = removeAlphaChannel(RedStoneWireBlock.getColorForPower(redstoneLevel));
            //?}
            resultColors.put(redstoneLevel, color);
        }
        return resultColors;
    //? if >=1.21.3 {
    }

    private static int removeAlphaChannel(int color) {
        float r = (float) (color >> 16 & 0xFF) / 255;
        float g = (float) (color >> 8 & 0xFF) / 255;
        float b = (float) (color & 0xFF) / 255;
    //?}
        //? if =1.21.3 {
        /*return ARGB.colorFromFloat(0, r, g, b);
        *///?} else if >=1.21.5 {
        return ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | (int)(b * 255);
        //?}
    }

    private static int getBlockColor(Block block) {
        //? if <26.1 {
        /*return BlockColors.createDefault().getColor(block.defaultBlockState(), EmptyRenderBlockView.INSTANCE, BlockPos.ZERO, 0xFFFFFF);
        *///?} else {
        return BlockColors.createDefault().getTintSource(block.defaultBlockState(), 0).colorInWorld(block.defaultBlockState(), EmptyRenderBlockView.INSTANCE, BlockPos.ZERO);
        //?}
    }

    public static Map<Block, Integer> generateConstantTintColors() {
        Map<Block, Integer> resultColors = new LinkedHashMap<>();

        //? if <1.21.5 {
        /*resultColors.put(Blocks.BIRCH_LEAVES, FoliageColor.getBirchColor());
        resultColors.put(Blocks.SPRUCE_LEAVES, FoliageColor.getEvergreenColor());
        *///?} else {
        resultColors.put(Blocks.BIRCH_LEAVES, FoliageColor.FOLIAGE_BIRCH);
        resultColors.put(Blocks.SPRUCE_LEAVES, FoliageColor.FOLIAGE_EVERGREEN);
        //?}

        resultColors.put(Blocks.LILY_PAD, getBlockColor(Blocks.LILY_PAD));
        resultColors.put(Blocks.ATTACHED_MELON_STEM, getBlockColor(Blocks.ATTACHED_MELON_STEM));
        resultColors.put(Blocks.ATTACHED_PUMPKIN_STEM, getBlockColor(Blocks.ATTACHED_PUMPKIN_STEM));

        //not really constant, depend on the block age, but kinda have to be handled since textures are literally white without them
        resultColors.put(Blocks.MELON_STEM, getBlockColor(Blocks.MELON_STEM));
        resultColors.put(Blocks.PUMPKIN_STEM, getBlockColor(Blocks.PUMPKIN_STEM));

        return resultColors;
    }

    private static JsonObject encodeBiomeColorMap(Registry<Biome> biomeRegistry, Map<Integer, List<Biome>> colorsMap) {
        JsonArray resultColorsArray = new JsonArray();
        for (var entry : colorsMap.entrySet()) {
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            for (Biome biome : entry.getValue()) {
                //? if <1.16 {
                /*ResourceLocation registryKey = biomeRegistry.getKey(biome);
                keysArray.add(Objects.requireNonNull(registryKey).getPath());
                *///?} else if >=1.16 <1.21.5 {
                /*ResourceLocation registryKey = biomeRegistry.getResourceKey(biome).orElseThrow().location();
                *///?} else if >=1.21.5 <1.21.11 {
                /*ResourceLocation registryKey = biomeRegistry.getKey(biome);
                *///?} else {
                Identifier registryKey = biomeRegistry.getKey(biome);
                //?}
                //? if >=1.16 {
                keysArray.add(registryKey.getPath());
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
        for (var entry : colorsMap.entrySet()) {
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            keysArray.add(entry.getKey());

            entryObject.add("keys", keysArray);
            entryObject.addProperty("color", entry.getValue());
            resultColorsArray.add(entryObject);
        }

        JsonObject resultObject = new JsonObject();
        resultObject.add("data", resultColorsArray);
        return resultObject;
    }

    private static JsonObject encodeBlocksColorMap(Registry<Block> blockRegistry, Map<Block, Integer> colorsMap) {
        JsonArray resultColorsArray = new JsonArray();
        for (var entry : colorsMap.entrySet()) {
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            //? if <1.16 {
            /*ResourceLocation registryKey = blockRegistry.getKey(entry.getKey());
            keysArray.add(Objects.requireNonNull(registryKey).getPath());
            *///?} else if >=1.16 <1.21.5 {
            /*ResourceLocation registryKey = blockRegistry.getResourceKey(entry.getKey()).orElseThrow().location();
            *///?} else if >=1.21.5 <1.21.11 {
            /*ResourceLocation registryKey = blockRegistry.getKey(entry.getKey());
            *///?} else {
            Identifier registryKey = blockRegistry.getKey(entry.getKey());
            //?}
            //? if >=1.16 {
            keysArray.add(registryKey.getPath());
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
//? if <1.17 {
/*//        DynamicRegistryManager registryManager = DynamicRegistryManager.create();
        Registry<Biome> biomeRegistry = Registry.BIOME;
        Registry<Block> blockRegistry = Registry.BLOCK;
*///?} else if >=1.17 <=1.18 {
        /*RegistryAccess registryManager = RegistryAccess.builtin();
*///?} else if >1.18 <1.20 {
        /*RegistryAccess registryManager = RegistryAccess.BUILTIN.get();
*///?}
        //? if >=1.17 <1.20 {
        /*Registry<Biome> biomeRegistry = registryManager.registryOrThrow(Registry.BIOME_REGISTRY);
        Registry<Block> blockRegistry = registryManager.registryOrThrow(Registry.BLOCK_REGISTRY);
        *///?} else if >=1.20 {
        RegistryAccess registryManager = DGU.getWorld().registryAccess();
        //?}
        //? if >=1.20 <1.21.3 {
        /*Registry<Biome> biomeRegistry = registryManager.registryOrThrow(Registries.BIOME);
        Registry<Block> blockRegistry = registryManager.registryOrThrow(Registries.BLOCK);
        *///?} else if >=1.21.3 {
        Registry<Biome> biomeRegistry = registryManager.lookupOrThrow(Registries.BIOME);
        Registry<Block> blockRegistry = registryManager.lookupOrThrow(Registries.BLOCK);
        //?}

        BiomeTintColors biomeTintColors = generateBiomeTintColors(biomeRegistry);
        Map<Integer, Integer> redstoneColors = generateRedstoneTintColors();
        //? if <1.19 {
        /*Map<Block, Integer> constantTintColors = Collections.emptyMap();

        EnvType currentEnvironment = FabricLoader.getInstance().getEnvironmentType();
        if (currentEnvironment == EnvType.CLIENT) {
            constantTintColors = generateConstantTintColors();
        }
        *///?} else {
        Map<Block, Integer> constantTintColors = generateConstantTintColors();
        //?}

        JsonObject resultObject = new JsonObject();

        resultObject.add("grass", encodeBiomeColorMap(biomeRegistry, biomeTintColors.grassColoursMap));
        resultObject.add("foliage", encodeBiomeColorMap(biomeRegistry, biomeTintColors.foliageColoursMap));
        resultObject.add("water", encodeBiomeColorMap(biomeRegistry, biomeTintColors.waterColourMap));

        resultObject.add("redstone", encodeRedstoneColorMap(redstoneColors));
        resultObject.add("constant", encodeBlocksColorMap(blockRegistry, constantTintColors));

        return resultObject;
    }

    public static class BiomeTintColors {
        final Map<Integer, List<Biome>> grassColoursMap = new LinkedHashMap<>();
        final Map<Integer, List<Biome>> foliageColoursMap = new LinkedHashMap<>();
        final Map<Integer, List<Biome>> waterColourMap = new LinkedHashMap<>();
    }
}
