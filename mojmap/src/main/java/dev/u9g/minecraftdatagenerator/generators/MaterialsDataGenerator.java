//? if >=1.17 {
package dev.u9g.minecraftdatagenerator.generators;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
//? if <1.20.5 {
/*import dev.u9g.minecraftdatagenerator.mixin.MiningToolItemAccessor;
*///?}
//? if >=1.20 {
import dev.u9g.minecraftdatagenerator.util.DGU;
//?}
import net.minecraft.core.Registry;
//? if >=1.20.5 {
import net.minecraft.core.component.DataComponents;
//?}
//? if >=1.20 {
import net.minecraft.core.registries.Registries;
//?}
import net.minecraft.tags.BlockTags;
//? if <=1.18 {
/*import net.minecraft.tags.Tag;
*///?} else {
import net.minecraft.tags.TagKey;
//?}
//? if <1.21.5 {
/*import net.minecraft.world.item.DiggerItem;
*///?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
//? if <1.21.5 {
/*import net.minecraft.world.item.SwordItem;
*///?}
//? if >=1.20.5 <1.21.3 {
/*import net.minecraft.world.item.component.Tool;
*///?}
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
//? if <1.20 {
/*import net.minecraft.world.level.material.Material;
*///?}

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//TODO entire idea of linking materials to tool speeds is obsolete and just wrong now,
//TODO but we kinda have to support it to let old code work for computing digging times,
//TODO so for now we will handle materials as "virtual" ones based on which tools can break blocks
public class MaterialsDataGenerator implements IDataGenerator {

    private static final List<ImmutableList<String>> COMPOSITE_MATERIALS = ImmutableList.<ImmutableList<String>>builder()
            .add(ImmutableList.of("plant", makeMaterialNameForTag(BlockTags.MINEABLE_WITH_AXE)))
            .add(ImmutableList.of("gourd", makeMaterialNameForTag(BlockTags.MINEABLE_WITH_AXE)))
            .add(ImmutableList.of(makeMaterialNameForTag(BlockTags.LEAVES), makeMaterialNameForTag(BlockTags.MINEABLE_WITH_HOE)))
            .add(ImmutableList.of(makeMaterialNameForTag(BlockTags.LEAVES), makeMaterialNameForTag(BlockTags.MINEABLE_WITH_AXE), makeMaterialNameForTag(BlockTags.MINEABLE_WITH_HOE)))
            .add(ImmutableList.of("vine_or_glow_lichen", "plant", makeMaterialNameForTag(BlockTags.MINEABLE_WITH_AXE)
            )).build();
    //? if >=1.20.5 {

    private static final Map<String, Float> TOOL_SPEEDS = new HashMap<>() {{
        // Base speeds for each tool type
        put("wooden", 2.0f);
        put("stone", 4.0f);
        put("iron", 6.0f);
        put("diamond", 8.0f);
        put("netherite", 9.0f);
        put("golden", 12.0f);
    }};
    //?}

    //? if <=1.18 {
    /*private static String makeMaterialNameForTag(Tag<Block> tag) {
        Tag.Named<Block> identifiedTag = (Tag.Named<Block>) tag;
        return identifiedTag.getName().getPath();
    *///?} else if >=1.21.3 <1.21.5 {
    /*private static float getToolSpeed(Item item) {
    *///?} else if >=1.21.5 {
    private static Float getToolSpeed(Item item) {
    //?}
        //? if >=1.21.3 {
        String itemName = item.toString().toLowerCase();
        // Remove minecraft: prefix if present
        if (itemName.startsWith("minecraft:")) {
            itemName = itemName.substring("minecraft:".length());
        }
        for (Map.Entry<String, Float> entry : TOOL_SPEEDS.entrySet()) {
            if (itemName.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return 1.0f;
    }

        //?}
    //? if >1.18 {
    private static String makeMaterialNameForTag(TagKey<Block> tag) {
        return tag.location().getPath();
    //?}
    }

    private static void createCompositeMaterialInfo(List<MaterialInfo> allMaterials, List<String> combinedMaterials) {
        String compositeMaterialName = String.join(";", combinedMaterials);

        List<MaterialInfo> mappedMaterials = combinedMaterials.stream()
                .map(otherName -> allMaterials.stream()
                        .filter(other -> other.getMaterialName().equals(otherName))
                        .findFirst().orElseThrow(() -> new RuntimeException("Material not found with name " + otherName)))
                .collect(Collectors.toList());

        Predicate<BlockState> compositePredicate = blockState ->
                mappedMaterials.stream().allMatch(it -> it.getPredicate().test(blockState));

        MaterialInfo materialInfo = new MaterialInfo(compositeMaterialName, compositePredicate).includes(mappedMaterials);
        allMaterials.addFirst(materialInfo);
    }

    private static void createCompositeMaterial(Map<String, Map<Item, Float>> allMaterials, List<String> combinedMaterials) {
        String compositeMaterialName = String.join(";", combinedMaterials);

        Map<Item, Float> resultingToolSpeeds = new LinkedHashMap<>();
        combinedMaterials.stream()
                .map(allMaterials::get)
                //? if <1.18 {
                /*.forEach(resultingToolSpeeds::putAll);
                *///?} else if >=1.18 <=1.18 {
                /*.forEach(v -> {
                    System.out.println(v);
                    resultingToolSpeeds.putAll(v);
                });
                *///?} else {
                .forEach(resultingToolSpeeds::putAll);
                //?}
        allMaterials.put(compositeMaterialName, resultingToolSpeeds);
    }

    //? if >=1.20.5 <1.21.3 {
    /*private static float getToolSpeed(Item item) {
        String itemName = item.toString().toLowerCase();
    *///?}
        //? if >=1.21 <1.21.3 {
        /*// Remove minecraft: prefix if present
        if (itemName.startsWith("minecraft:")) {
            itemName = itemName.substring("minecraft:".length());
        }
        *///?}
        //? if >=1.20.5 <1.21.3 {
        /*for (Map.Entry<String, Float> entry : TOOL_SPEEDS.entrySet()) {
            if (itemName.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return 1.0f;
    }

        *///?}
    public static List<MaterialInfo> getGlobalMaterialInfo() {
        ArrayList<MaterialInfo> resultList = new ArrayList<>();

        resultList.add(new MaterialInfo("vine_or_glow_lichen", blockState -> blockState.is(Blocks.VINE) || blockState.is(Blocks.GLOW_LICHEN)));
        resultList.add(new MaterialInfo("coweb", blockState -> blockState.is(Blocks.COBWEB)));

        resultList.add(new MaterialInfo("leaves", blockState -> blockState.is(BlockTags.LEAVES)));
        resultList.add(new MaterialInfo("wool", blockState -> blockState.is(BlockTags.WOOL)));

        //? if <1.20 {
        /*resultList.add(new MaterialInfo("gourd", blockState -> blockState.getMaterial() == Material.VEGETABLE));
        resultList.add(new MaterialInfo("plant", blockState -> blockState.getMaterial() == Material.PLANT || blockState.getMaterial() == Material.REPLACEABLE_PLANT));
        *///?} else {
        // Block Materials were removed in 1.20 in favor of block tags
        resultList.add(new MaterialInfo("gourd", blockState -> blockState.is(Blocks.MELON) || blockState.is(Blocks.PUMPKIN) || blockState.is(Blocks.JACK_O_LANTERN)));
        // 'sword_efficient' tag is for all plants, and includes everything from the old PLANT and REPLACEABLE_PLANT materials (see https://minecraft.fandom.com/wiki/Tag#Blocks)
        resultList.add(new MaterialInfo("plant", blockState -> blockState.is(BlockTags.SWORD_EFFICIENT)));
        //?}

        HashSet<String> uniqueMaterialNames = new HashSet<>();

        //? if <1.20 {
        /*Registry<Item> itemRegistry = Registry.ITEM;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Item> itemRegistry = DGU.getWorld().registryAccess().registryOrThrow(Registries.ITEM);
        *///?} else {
        Registry<Item> itemRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.ITEM);
        //?}
        itemRegistry.forEach(item -> {
            //? if <1.21.5 {
            /*if (item instanceof DiggerItem toolItem) {
            *///?}
                //? if <=1.18 {
                /*Tag<Block> effectiveBlocks = ((MiningToolItemAccessor) toolItem).getEffectiveBlocks();
                *///?} else if >1.18 <1.20.5 {
                /*TagKey<Block> effectiveBlocks = ((MiningToolItemAccessor) toolItem).getEffectiveBlocks();
                *///?}
                //? if <1.20.5 {
                /*String materialName = makeMaterialNameForTag(effectiveBlocks);
                *///?} else if >=1.21.5 {
            if (item.components().get(DataComponents.TOOL) != null) {
                //?}
                //? if >=1.20.5 {
                item.components().get(DataComponents.TOOL).rules()
                //?}
                        //? if >=1.20.5 <1.21.3 {
                        /*.stream().map(Tool.Rule::blocks)
                        *///?} else if >=1.21.3 {
                        .stream().map(rule -> rule.blocks())
                        //?}
                        //? if >=1.20.5 {
                        .forEach(blocks -> {
                            Optional<TagKey<Block>> tagKey = blocks.unwrapKey();
                            if (tagKey.isPresent()) {
                                String materialName = makeMaterialNameForTag((tagKey.get()));
                        //?}

                //? if <1.20.5 {
                /*if (!uniqueMaterialNames.contains(materialName)) {
                    uniqueMaterialNames.add(materialName);
                    resultList.add(new MaterialInfo(materialName, blockState -> blockState.is(effectiveBlocks)));
                }
                *///?} else {
                                if (!uniqueMaterialNames.contains(materialName)) {
                                    uniqueMaterialNames.add(materialName);
                                    resultList.add(new MaterialInfo(materialName, blockState -> blockState.is(blocks)));
                                }
                            }
                        });
                //?}
            }
        });

        COMPOSITE_MATERIALS.forEach(values -> createCompositeMaterialInfo(resultList, values));
        return resultList;
    }

    @Override
    public String getDataName() {
        return "materials";
    }

    @Override
    public JsonElement generateDataJson() {
        //? if <1.20 {
        /*Registry<Item> itemRegistry = Registry.ITEM;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Item> itemRegistry = DGU.getWorld().registryAccess().registryOrThrow(Registries.ITEM);
        *///?} else {
        Registry<Item> itemRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.ITEM);
        //?}

        Map<String, Map<Item, Float>> materialMiningSpeeds = new LinkedHashMap<>();
        //? if <1.21.5 {
        /*materialMiningSpeeds.put("default", ImmutableMap.of());
        *///?} else {
        materialMiningSpeeds.put("default", new LinkedHashMap<>());
        //?}

        //Special materials used for shears and swords special mining speed logic
        Map<Item, Float> leavesMaterialSpeeds = new LinkedHashMap<>();
        Map<Item, Float> cowebMaterialSpeeds = new LinkedHashMap<>();
        Map<Item, Float> plantMaterialSpeeds = new LinkedHashMap<>();
        Map<Item, Float> gourdMaterialSpeeds = new LinkedHashMap<>();

        materialMiningSpeeds.put(makeMaterialNameForTag(BlockTags.LEAVES), leavesMaterialSpeeds);
        materialMiningSpeeds.put("coweb", cowebMaterialSpeeds);
        materialMiningSpeeds.put("plant", plantMaterialSpeeds);
        materialMiningSpeeds.put("gourd", gourdMaterialSpeeds);

        //Shears need special handling because they do not follow normal rules like tools
        leavesMaterialSpeeds.put(Items.SHEARS, 15.0f);
        cowebMaterialSpeeds.put(Items.SHEARS, 15.0f);
        //? if <1.21.5 {
        /*materialMiningSpeeds.put("vine_or_glow_lichen", ImmutableMap.of(Items.SHEARS, 2.0f));
        materialMiningSpeeds.put("wool", ImmutableMap.of(Items.SHEARS, 5.0f));
        *///?} else {

        Map<Item, Float> vineOrGlowLichenSpeeds = new LinkedHashMap<>();
        vineOrGlowLichenSpeeds.put(Items.SHEARS, 2.0f);
        materialMiningSpeeds.put("vine_or_glow_lichen", vineOrGlowLichenSpeeds);

        Map<Item, Float> woolSpeeds = new LinkedHashMap<>();
        woolSpeeds.put(Items.SHEARS, 5.0f);
        materialMiningSpeeds.put("wool", woolSpeeds);
        //?}

        itemRegistry.forEach(item -> {
            //Tools are handled rather easily and do not require anything else
            //? if <1.21.5 {
            /*if (item instanceof DiggerItem toolItem) {
            *///?}
                //? if <=1.18 {
                /*Tag<Block> effectiveBlocks = ((MiningToolItemAccessor) toolItem).getEffectiveBlocks();
                *///?} else if >1.18 <1.20.5 {
                /*TagKey<Block> effectiveBlocks = ((MiningToolItemAccessor) toolItem).getEffectiveBlocks();
                *///?}
                //? if <1.20.5 {
                /*String materialName = makeMaterialNameForTag(effectiveBlocks);

                Map<Item, Float> materialSpeeds = materialMiningSpeeds.computeIfAbsent(materialName, k -> new LinkedHashMap<>());
                float miningSpeed = ((MiningToolItemAccessor) toolItem).getMiningSpeed();
                materialSpeeds.put(item, miningSpeed);
                *///?} else if >=1.21.5 {
            if (item.components().get(DataComponents.TOOL) != null) {
                //?}
                //? if >=1.20.5 {
                item.components().get(DataComponents.TOOL).rules()
                //?}
                        //? if >=1.20.5 <1.21.3 {
                        /*.stream().map(Tool.Rule::blocks)
                        *///?} else if >=1.21.3 {
                        .stream().map(rule -> rule.blocks())
                        //?}
                        //? if >=1.20.5 {
                        .forEach(blocks -> {
                        //?}
                            //? if >=1.20.5 <1.21.3 {
                            /*Optional<TagKey<Block>> tagKey = blocks.unwrapKey();
                            if (tagKey.isPresent()) {
                                String materialName = makeMaterialNameForTag(tagKey.get());
                                Map<Item, Float> materialSpeeds = materialMiningSpeeds.computeIfAbsent(materialName, k -> new LinkedHashMap<>());
                                float baseSpeed = getToolSpeed(item);
                                materialSpeeds.put(item, baseSpeed);
                            }
                        });
                            *///?}
            //? if <1.21.3 {
            /*}
            *///?} else {
                                    Optional<TagKey<Block>> tagKey = blocks.unwrapKey();
                                    if (tagKey.isPresent()) {
                                        String materialName = makeMaterialNameForTag(tagKey.get());
            //?}

            //Swords require special treatment
            // Add sword speeds for special materials
            //Swords require special treatment
            //? if <1.21.3 {
            /*if (item instanceof SwordItem) {
                cowebMaterialSpeeds.put(item, 15.0f);
                plantMaterialSpeeds.put(item, 1.5f);
                leavesMaterialSpeeds.put(item, 1.5f);
                gourdMaterialSpeeds.put(item, 1.5f);
            }
        });
            *///?} else {
                                        Map<Item, Float> materialSpeeds = materialMiningSpeeds.computeIfAbsent(materialName, k -> new LinkedHashMap<>());
                                        float baseSpeed = getToolSpeed(item);
                                        materialSpeeds.put(item, baseSpeed);
                                    }
                                }
                        );

                //Swords require special treatment
            //?}
                //? if >=1.21.3 <1.21.5 {
                /*if (item instanceof SwordItem) {
                *///?} else if >=1.21.5 {
                if (itemRegistry.getKey(item).getPath().contains("sword")) {
                //?}
                    //? if >=1.21.3 {
                    cowebMaterialSpeeds.put(item, 15.0f);
                    plantMaterialSpeeds.put(item, 1.5f);
                    leavesMaterialSpeeds.put(item, 1.5f);
                    gourdMaterialSpeeds.put(item, 1.5f);
                }
            }});
                    //?}

        COMPOSITE_MATERIALS.forEach(values -> createCompositeMaterial(materialMiningSpeeds, values));

        JsonObject resultObject = new JsonObject();

        for (var entry : materialMiningSpeeds.entrySet()) {
            JsonObject toolSpeedsObject = new JsonObject();

            for (var toolEntry : entry.getValue().entrySet()) {
                int rawItemId = itemRegistry.getId(toolEntry.getKey());
                toolSpeedsObject.addProperty(Integer.toString(rawItemId), toolEntry.getValue());
            }
            resultObject.add(entry.getKey(), toolSpeedsObject);
        }

        return resultObject;
    }

    public static class MaterialInfo {
        private final String materialName;
        private final Predicate<BlockState> predicate;
        private final List<MaterialInfo> includedMaterials = new ArrayList<>();

        public MaterialInfo(String materialName, Predicate<BlockState> predicate) {
            this.materialName = materialName;
            this.predicate = predicate;
        }

        protected MaterialInfo includes(List<MaterialInfo> otherMaterials) {
            this.includedMaterials.addAll(otherMaterials);
            return this;
        }

        public String getMaterialName() {
            return materialName;
        }

        public Predicate<BlockState> getPredicate() {
            return predicate;
        }

        public boolean includesMaterial(MaterialInfo materialInfo) {
            return includedMaterials.contains(materialInfo);
        }

        @Override
        public String toString() {
            return materialName;
        }
    }
}
//?}
