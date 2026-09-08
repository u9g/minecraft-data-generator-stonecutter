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
//? if >=1.9.4 {
import net.minecraft.util.Identifier;
//?}
//? if >=1.9.4 <1.13 {
/*import net.minecraft.util.registry.SimpleRegistry;
*///?}
//? if <1.13 {
/*import net.minecraft.world.biome.*;
*///?} else {
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
//?}

//? if <1.14 {
/*import java.util.Locale;
*///?}
//? if <1.8.9 {
/*import java.util.SortedMap;
import java.util.TreeMap;
*///?} else if >=1.13 {
import java.util.Objects;
//?}

public class BiomesDataGenerator implements IDataGenerator {

    private static String guessBiomeDimensionFromCategory(Biome biome) {
        //? if <1.13 {
        /*if (biome instanceof NetherBiome) {
            return "nether";
        } else if (biome instanceof EndBiome) {
            return "end";
        }
        return "overworld";
        *///?} else {
        return switch (biome.getCategory()) {
            case NETHER -> "nether";
            case THEEND -> "end";
            default -> "overworld";
        };
        //?}
    }

    //? if <1.9.4 {
    /*public static JsonObject generateBiomeInfo(Biome biome) {
    *///?} else if >=1.9.4 <1.13 {
    /*public static JsonObject generateBiomeInfo(SimpleRegistry<Identifier, Biome> registry, Biome biome) {
    *///?} else {
    public static JsonObject generateBiomeInfo(Registry<Biome> registry, Biome biome) {
    //?}
        JsonObject biomeDesc = new JsonObject();
//        Identifier registryKey = registry.getIdentifier(biome);
        //? if >=1.13 {
        Identifier registryKey = registry.getId(biome);
        String localizationKey = String.format("biome.%s.%s", Objects.requireNonNull(registryKey).getNamespace(), registryKey.getPath());
        //?}

        //? if <1.9.4 {
        /*biomeDesc.addProperty("id", Registries.BIOMES.getRawId(biome));
        *///?} else if >=1.12.2 <1.13 {
        /*String name = String.join("_", ((BiomeAccessor) biome).name().toLowerCase(Locale.ENGLISH).split(" "));
        String displayName = ((BiomeAccessor) biome).name();
        *///?}
        //? if >=1.9.4 {
        biomeDesc.addProperty("id", registry.getRawId(biome));
        //?}
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
        *///?} else if >=1.13 {
        biomeDesc.addProperty("name", registryKey.getPath());

        //?}
        //? if >=1.13 <1.14 {
        /*biomeDesc.addProperty("category", biome.getCategory().name().toLowerCase(Locale.ENGLISH));
        *///?} else if >=1.14 {
        biomeDesc.addProperty("category", biome.getCategory().getName());
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
        *///?} else if >=1.14 {
        biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());
        //?}
        //? if >=1.9.4 {
        biomeDesc.addProperty("depth", biome.getDepth());
        //?}
        biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(biome));
        //? if <1.12.2 {
        /*biomeDesc.addProperty("displayName", ((BiomeAccessor) biome).name());
        *///?}
        //? if <1.9.4 {
        /*biomeDesc.addProperty("color", biome.getSkyColor(biome.temperature));
        biomeDesc.addProperty("rainfall", biome.downfall);
        *///?} else if >=1.12.2 <1.13 {
        /*biomeDesc.addProperty("displayName", displayName);
        *///?} else if >=1.13 {
        biomeDesc.addProperty("displayName", DGU.translateText(localizationKey));
        //?}
        //? if >=1.9.4 {
        biomeDesc.addProperty("color", biome.getSkyColor(biome.getTemperature()));
        biomeDesc.addProperty("rainfall", biome.getRainfall());
        //?}

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
        *///?} else if >=1.13 {
        Registry<Biome> biomeRegistry = Registry.BIOME;
        //?}

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
        *///?} else if >=1.13 {
        biomeRegistry.stream()
                .map(biome -> generateBiomeInfo(biomeRegistry, biome))
                .forEach(biomesArray::add);
        //?}
        return biomesArray;
    }
}
