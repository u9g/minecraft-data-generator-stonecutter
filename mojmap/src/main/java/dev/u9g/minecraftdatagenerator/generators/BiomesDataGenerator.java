package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.core.Registry;
//? if >1.18 <1.20 {
/*import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
*///?}
//? if >1.18 {
import net.minecraft.tags.BiomeTags;
//?}
//? if >=1.21.11 {
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
//?}
import net.minecraft.world.level.biome.Biome;


public class BiomesDataGenerator implements IDataGenerator {
    //? if <=1.18 {

    /*private static String guessBiomeDimensionFromCategory(Biome biome) {
        return switch (biome.getBiomeCategory()) {
            case NETHER -> "nether";
            case THEEND -> "end";
            default -> "overworld";
        };
    *///?} else if >1.18 <1.20 {
    /*private static String guessBiomeDimensionFromCategory(ResourceKey<Biome> biome) {
    *///?} else {
    private static String guessBiomeDimensionFromCategory(Biome biome) {
    //?}
        //? if >1.18 {
        var biomeRegistry = DGU.<Biome>registry("biome");
        //?}
        //? if >1.18 <1.20 {
        /*if (biomeRegistry.getHolder(biome).orElseThrow().is(BiomeTags.IS_NETHER)) {
        *///?} else if >=1.20 {
        if (biomeRegistry.wrapAsHolder(biome).is(BiomeTags.IS_NETHER)) {
        //?}
            //? if >1.18 {
            return "nether";
            //?}
        //? if >1.18 <1.20 {
        /*} else if (biomeRegistry.getHolder(biome).orElseThrow().is(BiomeTags.IS_END)) {
        *///?} else if >=1.20 {
        } else if (biomeRegistry.wrapAsHolder(biome).is(BiomeTags.IS_END)) {
        //?}
            //? if >1.18 {
            return "end";
        } else {
            return "overworld";
        }
            //?}
    }

    //? if >=1.19 {
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
    public static JsonObject generateBiomeInfo(Registry<Biome> registry, Biome biome) {
        JsonObject biomeDesc = new JsonObject();
        //? if <=1.18 {
        /*var registryKey = registry.getKey(biome);
        String localizationKey = String.format("biome.%s.%s", registryKey.getNamespace(), registryKey.getPath());
        *///?} else if >1.18 <1.20 {
        /*ResourceKey<Biome> registryKey = registry.getResourceKey(biome).orElseThrow();
        ResourceLocation identifier = registryKey.location();
        String localizationKey = String.format("biome.%s.%s", identifier.getNamespace(), identifier.getPath());
        *///?}

        //? if >=1.19 <1.20 {
        /*String name = identifier.getPath();
        *///?} else if >=1.20 {
        var registryKey = registry.getKey(biome);
        String localizationKey = String.format("biome.%s.%s", registryKey.getNamespace(), registryKey.getPath());
        String name = registryKey.getPath();
        //?}
        biomeDesc.addProperty("id", registry.getId(biome));
        //? if <=1.18 {
        /*biomeDesc.addProperty("name", registryKey.getPath());
        *///?} else if >1.18 <1.19 {
        /*biomeDesc.addProperty("name", identifier.getPath());
        *///?}

        //? if <=1.18 {
        /*biomeDesc.addProperty("category", biome.getBiomeCategory().getName());
        *///?}
        //? if <1.17 {
        /*biomeDesc.addProperty("temperature", biome.getTemperature());
        *///?} else if >1.18 <1.19 {
        /*//FIXME: this...
        biomeDesc.addProperty("category", "");
        *///?} else if >=1.19 {
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
        //? if >=1.17 {
        biomeDesc.addProperty("temperature", biome.getBaseTemperature());
        //?}
        //? if <1.20 {
        /*biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());
        *///?}
        //? if <1.18 {
        /*biomeDesc.addProperty("depth", biome.getDepth());
        *///?} else if >=1.20 {
        //biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());// - removed in 1.19.4
        biomeDesc.addProperty("has_precipitation", biome.hasPrecipitation());
        //?}
        //biomeDesc.addProperty("depth", biome.getDepth()); - Doesn't exist anymore in minecraft source
        //? if <=1.18 {
        /*biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(biome));
        *///?} else if >1.18 <1.19 {
        /*biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(registryKey));
        *///?} else {
        biomeDesc.addProperty("dimension", dimension);
        //?}
        biomeDesc.addProperty("displayName", DGU.translateText(localizationKey));
        //? if <1.21.11 {
        /*biomeDesc.addProperty("color", biome.getSkyColor());
        *///?}
        //? if <1.20 {
        /*biomeDesc.addProperty("rainfall", biome.getDownfall());
        *///?} else if >=1.21.11 {
        // In 1.21.11, sky color moved to EnvironmentAttributes
        EnvironmentAttributeMap.Entry<Integer, ?> skyColorEntry = biome.getAttributes().get(EnvironmentAttributes.SKY_COLOR);
        int skyColor = 0;
        if (skyColorEntry != null && skyColorEntry.argument() instanceof Integer) {
            // convert to RGB
            skyColor = (Integer) skyColorEntry.argument() & 0xFFFFFF;
        }
        biomeDesc.addProperty("color", skyColor);
        //?}
        //biomeDesc.addProperty("rainfall", biome.getDownfall());// - removed in 1.19.4

        return biomeDesc;
    }

    @Override
    public String getDataName() {
        return "biomes";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray biomesArray = new JsonArray();
        Registry<Biome> biomeRegistry = DGU.registry("biome");


        biomeRegistry.stream()
                .map(biome -> generateBiomeInfo(biomeRegistry, biome))
                .forEach(biomesArray::add);
        return biomesArray;
    }
}
