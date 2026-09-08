package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.mixin.BiomeAccessor;
*///?}
//? if <1.9.4 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?} else if >=1.13 {
import dev.u9g.minecraftdatagenerator.util.DGU;
//?}
//? if >1.18 <1.20 {
/*import net.minecraft.tag.BiomeTags;
*///?} else if >=1.20 {
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
//?}
//? if >=1.9.4 {
import net.minecraft.util.Identifier;
//?}
//? if >=1.9.4 <1.13 {
/*import net.minecraft.util.registry.SimpleRegistry;
*///?}
//? if <1.13 {
/*import net.minecraft.world.biome.*;
*///?} else if >1.18 <1.20 {
/*import net.minecraft.util.registry.BuiltinRegistries;
*///?}
//? if >=1.17 <1.19 {
/*import net.minecraft.util.registry.DynamicRegistryManager;
*///?}
//? if >=1.13 <1.19 {
/*import net.minecraft.util.registry.Registry;
*///?} else if >=1.19.2 <1.20 {
/*import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
*///?}
//? if >1.18 <1.20 {
/*import net.minecraft.util.registry.RegistryKey;
*///?}
//? if >=1.13 {
import net.minecraft.world.biome.Biome;
//?}

//? if <1.14 {
/*import java.util.Locale;
*///?}
//? if <1.8.9 {
/*import java.util.SortedMap;
import java.util.TreeMap;
*///?} else if >=1.13 <1.16 {
/*import java.util.Objects;
*///?}

public class BiomesDataGenerator implements IDataGenerator {


    //? if <=1.18 {
    /*private static String guessBiomeDimensionFromCategory(Biome biome) {
    *///?}
        //? if <1.13 {
        /*if (biome instanceof NetherBiome) {
            return "nether";
        } else if (biome instanceof EndBiome) {
            return "end";
        }
        return "overworld";
        *///?} else if >=1.13 <=1.18 {
        /*return switch (biome.getCategory()) {
            case NETHER -> "nether";
            case THEEND -> "end";
            default -> "overworld";
        };
        *///?} else if >1.18 <1.20 {
    /*private static String guessBiomeDimensionFromCategory(RegistryKey<Biome> biome) {
        var biomeRegistry = BuiltinRegistries.BIOME;
        if (biomeRegistry.getEntry(biome).orElseThrow().isIn(BiomeTags.IS_NETHER)) {
        *///?} else {
    private static String guessBiomeDimensionFromCategory(Biome biome) {
        //?}
        //? if >=1.20 <1.21.3 {
        /*var biomeRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.BIOME);
        *///?} else if >=1.21.3 {
        var biomeRegistry = DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.BIOME);
        //?}
        //? if >=1.20 {
        if (biomeRegistry.getEntry(biome).isIn(BiomeTags.IS_NETHER)) {
        //?}
            //? if >1.18 {
            return "nether";
            //?}
        //? if >1.18 <1.20 {
        /*} else if (biomeRegistry.getEntry(biome).orElseThrow().isIn(BiomeTags.IS_END)) {
        *///?} else if >=1.20 {
        } else if (biomeRegistry.getEntry(biome).isIn(BiomeTags.IS_END)) {
        //?}
            //? if >1.18 {
            return "end";
        } else {
            return "overworld";
        }
            //?}
    }

    //? if <1.9.4 {
    /*public static JsonObject generateBiomeInfo(Biome biome) {
    *///?} else if >=1.9.4 <1.13 {
    /*public static JsonObject generateBiomeInfo(SimpleRegistry<Identifier, Biome> registry, Biome biome) {
    *///?} else if >=1.13 <1.19 {
    /*public static JsonObject generateBiomeInfo(Registry<Biome> registry, Biome biome) {
    *///?} else {
    private static String guessCategoryBasedOnName(String name, String dimension) {
        if (dimension.equals("nether")) {
            return "nether";
        } else if (dimension.equals("end")) {
            return "the_end";
        }

        if (name.contains("end")) {
            System.out.println();
        }

        if (name.contains("hills")) {
            return "extreme_hills";
        } else if (name.contains("ocean")) {
            return "ocean";
        } else if (name.contains("plains")) {
            return "plains";
        } else if (name.contains("ice") || name.contains("frozen")) {
            return "ice";
        } else if (name.contains("jungle")) {
            return "jungle";
        } else if (name.contains("desert")) {
            return "desert";
        } else if (name.contains("forest") || name.contains("grove")) {
            return "forest";
        } else if (name.contains("taiga")) {
            return "taiga";
        } else if (name.contains("swamp")) {
            return "swamp";
        } else if (name.contains("river")) {
            return "river";
        } else if (name.equals("the_end")) {
            return "the_end";
        } else if (name.contains("mushroom")) {
            return "mushroom";
        } else if (name.contains("beach") || name.equals("stony_shore")) {
            return "beach";
        } else if (name.contains("savanna")) {
            return "savanna";
        } else if (name.contains("badlands")) {
            return "mesa";
        } else if (name.contains("peaks") || name.equals("snowy_slopes") || name.equals("meadow")) {
            return "mountain";
        } else if (name.equals("the_void")) {
            return "none";
        } else if (name.contains("cave") || name.equals("deep_dark")) {
            return "underground";
        } else {
            System.out.println("Unable to find biome category for biome with name: '" + name + "'");
            return "none";
        }
    }

    //?}
    //? if >=1.19 <1.19.2 {
    /*public static JsonObject generateBiomeInfo(Biome biome) {
    *///?} else if >=1.19.2 {
    public static JsonObject generateBiomeInfo(Registry<Biome> registry, Biome biome) {
    //?}
        JsonObject biomeDesc = new JsonObject();
//        Identifier registryKey = registry.getIdentifier(biome);
        //? if >=1.13 <1.16 {
        /*Identifier registryKey = registry.getId(biome);
        String localizationKey = String.format("biome.%s.%s", Objects.requireNonNull(registryKey).getNamespace(), registryKey.getPath());
        *///?}

        //? if <1.9.4 {
        /*biomeDesc.addProperty("id", Registries.BIOMES.getRawId(biome));
        *///?} else if >=1.12.2 <1.13 {
        /*String name = String.join("_", ((BiomeAccessor) biome).name().toLowerCase(Locale.ENGLISH).split(" "));
        String displayName = ((BiomeAccessor) biome).name();
        *///?} else if >=1.16 <=1.18 {
        /*Identifier registryKey = registry.getKey(biome).orElseThrow().getValue();
        String localizationKey = String.format("biome.%s.%s", registryKey.getNamespace(), registryKey.getPath());
        *///?} else if >1.18 <1.19 {
        /*RegistryKey<Biome> registryKey = registry.getKey(biome).orElseThrow();
        *///?} else if >=1.19 <1.19.2 {
        /*RegistryKey<Biome> registryKey = BuiltinRegistries.BIOME.getKey(biome).orElseThrow();
        *///?} else if >=1.19.2 <1.20 {
        /*RegistryKey<Biome> registryKey = registry.getKey(biome).orElseThrow();
        *///?}
        //? if >1.18 <1.20 {
        /*Identifier identifier = registryKey.getValue();
        String localizationKey = String.format("biome.%s.%s", identifier.getNamespace(), identifier.getPath());
        *///?}

        //? if >=1.9.4 <1.19 {
        /*biomeDesc.addProperty("id", registry.getRawId(biome));
        *///?}
        //? if <1.12.2 {
        /*biomeDesc.addProperty("name", String.join("_", ((BiomeAccessor) biome).name().toLowerCase(Locale.ENGLISH).split(" ")));
        *///?} else if >=1.12.2 <1.13 {
        /*biomeDesc.addProperty("name", name);
        *///?}
        //? if <1.13 {
        /*biomeDesc.addProperty("category", category(biome));
        *///?}
        //? if <1.9.4 {
        /*biomeDesc.addProperty("temperature", biome.temperature);
        *///?} else if >=1.13 <=1.18 {
        /*biomeDesc.addProperty("name", registryKey.getPath());
        *///?} else if >1.18 <1.19 {
        /*biomeDesc.addProperty("name", identifier.getPath());
        *///?}

        //? if >=1.13 <1.14 {
        /*biomeDesc.addProperty("category", biome.getCategory().name().toLowerCase(Locale.ENGLISH));
        *///?} else if >=1.14 <=1.18 {
        /*biomeDesc.addProperty("category", biome.getCategory().getName());
        *///?} else if >1.18 <1.19 {
        /*//FIXME: this...
        biomeDesc.addProperty("category", "");
        *///?} else if >=1.19 <1.20 {
        /*String name = identifier.getPath();
        *///?}
        //? if >=1.19 <1.19.2 {
        /*biomeDesc.addProperty("id", BuiltinRegistries.BIOME.getRawId(biome));
        *///?} else if >=1.20 {
        Identifier registryKey = registry.getKey(biome).orElseThrow().getValue();
        String localizationKey = String.format("biome.%s.%s", registryKey.getNamespace(), registryKey.getPath());
        String name = registryKey.getPath();
        //?}
        //? if >=1.19.2 {
        biomeDesc.addProperty("id", registry.getRawId(biome));
        //?}
        //? if >=1.19 {
        biomeDesc.addProperty("name", name);
        //?}
        //? if >=1.19 <1.20 {
        /*String dimension = guessBiomeDimensionFromCategory(registryKey);
        *///?} else if >=1.20 {
        String dimension = guessBiomeDimensionFromCategory(biome);
        //?}
        //? if >=1.19 {
        biomeDesc.addProperty("category", guessCategoryBasedOnName(name, dimension));
        //?}
        //? if >=1.9.4 {
        biomeDesc.addProperty("temperature", biome.getTemperature());
        //?}
        //? if <1.13 {
        /*biomeDesc.addProperty("precipitation", precipitation(biome));
        *///?}
        //? if <1.9.4 {
        /*biomeDesc.addProperty("depth", biome.depth);
        *///?} else if >=1.13 <1.14 {
        /*biomeDesc.addProperty("precipitation", biome.getPrecipitation().name().toLowerCase(Locale.ENGLISH));
        *///?} else if >=1.14 <1.20 {
        /*biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());
        *///?}
        //? if >=1.9.4 <1.18 {
        /*biomeDesc.addProperty("depth", biome.getDepth());
        *///?} else if >=1.20 {
        //biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());// - removed in 1.19.4
        biomeDesc.addProperty("has_precipitation", biome.hasPrecipitation());
        //?}
        //biomeDesc.addProperty("depth", biome.getDepth()); - Doesn't exist anymore in minecraft source
        //? if <=1.18 {
        /*biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(biome));
        *///?}
        //? if <1.12.2 {
        /*biomeDesc.addProperty("displayName", ((BiomeAccessor) biome).name());
        *///?}
        //? if <1.9.4 {
        /*biomeDesc.addProperty("color", biome.getSkyColor(biome.temperature));
        biomeDesc.addProperty("rainfall", biome.downfall);
        *///?} else if >=1.12.2 <1.13 {
        /*biomeDesc.addProperty("displayName", displayName);
        *///?} else if >1.18 <1.19 {
        /*biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(registryKey));
        *///?} else if >=1.19 {
        biomeDesc.addProperty("dimension", dimension);
        //?}
        //? if >=1.13 {
        biomeDesc.addProperty("displayName", DGU.translateText(localizationKey));
        //?}
        //? if >=1.9.4 <1.15 {
        /*biomeDesc.addProperty("color", biome.getSkyColor(biome.getTemperature()));
        *///?} else if >=1.15 {
        biomeDesc.addProperty("color", biome.getSkyColor());
        //?}
        //? if >=1.9.4 <1.17 {
        /*biomeDesc.addProperty("rainfall", biome.getRainfall());
        *///?} else if >=1.17 <1.20 {
        /*biomeDesc.addProperty("rainfall", biome.getDownfall());
        *///?}
        //biomeDesc.addProperty("rainfall", biome.getDownfall());// - removed in 1.19.4

        return biomeDesc;
    //? if <1.13 {
    /*}

    private static String category(Biome biome) {
        if (biome instanceof ForestBiome) {
            return "forest";
        } else if (biome instanceof OceanBiome) {
            return "ocean";
        } else if (biome instanceof PlainsBiome) {
            return "plains";
        } else if (biome instanceof DesertBiome) {
            return "desert";
        } else if (biome instanceof ExtremeHillsBiome) {
            return "extreme_hills";
        } else if (biome instanceof TaigaBiome) {
            return "taiga";
        } else if (biome instanceof SwampBiome) {
            return "swamp";
        } else if (biome instanceof RiverBiome) {
            return "river";
        } else if (biome instanceof NetherBiome) {
            return "nether";
        } else if (biome instanceof EndBiome) {
            return "the_end";
        } else if (biome instanceof IceBiome) {
            return "icy";
        } else if (biome instanceof MushroomBiome) {
            return "mushroom";
        } else if (biome instanceof BeachBiome) {
            return "beach";
        } else if (biome instanceof JungleBiome) {
            return "jungle";
        } else if (biome instanceof SavannaBiome) {
            return "savanna";
        } else if (biome instanceof MesaBiome) {
            return "mesa";
    *///?}
        //? if <1.9.4 {
        /*} else if (biome instanceof StoneBeachBiome) {
        *///?} else if >=1.9.4 <1.13 {
        /*} else if (biome instanceof StoneBeachBiome || biome instanceof VoidBiome) {
        *///?}
            //? if <1.13 {
            /*return "none"; // Should StoneBeachBiome be beach too? this is how it is now in mcdata
        }
        throw new IllegalStateException("Unable to find biome category for " + biome.getClass().getName());
    }

    private static String precipitation(Biome biome) {
            *///?}
        //? if <1.9.4 {
        /*float rainfall = biome.downfall;
        float temperature = biome.temperature;
        *///?} else if >=1.9.4 <1.13 {
        /*float rainfall = biome.getRainfall();
        float temperature = biome.getTemperature();
        *///?}
        //? if <1.13 {
        /*if (rainfall == 0) {
            return "none";
        } else if (temperature < 0.2f) {
            return "snow";
        }
        return "rain";
        *///?}
    }

    @Override
    public String getDataName() {
        return "biomes";
    }

    @Override
    public JsonArray generateDataJson() {
        //? if <1.8.9 {
        /*SortedMap<Integer, JsonObject> biomes = new TreeMap<>();
        *///?} else {
        JsonArray biomesArray = new JsonArray();
        //?}
        //? if >=1.9.4 <1.13 {
        /*SimpleRegistry<Identifier, Biome> biomeRegistry = Biome.REGISTRY;
        *///?} else if >=1.13 <1.17 {
        /*Registry<Biome> biomeRegistry = Registry.BIOME;
        *///?} else if >=1.17 <=1.18 {
        /*DynamicRegistryManager registryManager = DynamicRegistryManager.create();
        Registry<Biome> biomeRegistry = registryManager.get(Registry.BIOME_KEY);
        *///?} else if >1.18 <1.19 {
        /*Registry<Biome> biomeRegistry = DynamicRegistryManager.BUILTIN.get().get(Registry.BIOME_KEY);
        *///?}

        //? if <1.9.4 {
        /*for (Biome biome : Registries.BIOMES) {
        *///?}
            //? if <1.8.9 {
            /*biomes.put(biome.id, generateBiomeInfo(biome));
            *///?} else if >=1.8.9 <1.9.4 {
            /*biomesArray.add(generateBiomeInfo(biome));
            *///?} else if >=1.9.4 <1.13 {
        /*for (Biome biome : biomeRegistry) {
            biomesArray.add(generateBiomeInfo(biomeRegistry, biome));
            *///?}
        //? if <1.13 {
        /*}
        *///?}
        //? if <1.8.9 {
        /*JsonArray biomesArray = new JsonArray();
        biomes.values().forEach(biomesArray::add);
        *///?} else if >=1.13 <1.19 {
        /*biomeRegistry.stream()
                .map(biome -> generateBiomeInfo(biomeRegistry, biome))
        *///?} else if >=1.19 <1.19.2 {
        /*BuiltinRegistries.BIOME.stream()
                .map(BiomesDataGenerator::generateBiomeInfo)
        *///?} else if >=1.19.2 <1.20 {
        /*DynamicRegistryManager registryManager = DynamicRegistryManager.BUILTIN.get();
        Registry<Biome> biomeRegistry = registryManager.get(Registry.BIOME_KEY);
        *///?} else if >=1.20 {
        DynamicRegistryManager registryManager = DGU.getWorld().getRegistryManager();
        //?}
        //? if >=1.20 <1.21.3 {
        /*Registry<Biome> biomeRegistry = registryManager.get(RegistryKeys.BIOME);
        *///?} else if >=1.21.3 {
        Registry<Biome> biomeRegistry = registryManager.getOrThrow(RegistryKeys.BIOME);
        //?}
        //? if >=1.19.2 {

        biomeRegistry.stream()
                .map(biome -> generateBiomeInfo(biomeRegistry, biome))
        //?}
                //? if >=1.13 {
                .forEach(biomesArray::add);
                //?}
        return biomesArray;
    }
}
