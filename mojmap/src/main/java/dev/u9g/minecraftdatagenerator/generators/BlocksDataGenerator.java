package dev.u9g.minecraftdatagenerator.generators;

import com.google.common.base.CaseFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if <1.17 {
/*import dev.u9g.minecraftdatagenerator.mixin.MiningToolItemAccessor;
*///?}
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
//? if >=1.20 <1.21.5 {
/*import net.minecraft.core.registries.BuiltInRegistries;
*///?}
//? if >=1.20 {
import net.minecraft.core.registries.Registries;
//?}
//? if <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
*///?} else {
import net.minecraft.resources.Identifier;
//?}
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
//? if <1.17 {
/*import net.minecraft.world.item.DiggerItem;
*///?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootContext;
//? if >=1.20 {
import net.minecraft.world.level.storage.loot.LootParams;
//?}
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
//? if >=1.17 {
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//?}

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlocksDataGenerator implements IDataGenerator {
    //? if <1.17 {
    /*private static List<Item> getItemsEffectiveForBlock(Block block) {
    *///?} else {

    private static final Logger logger = LoggerFactory.getLogger(BlocksDataGenerator.class);

    private static List<Item> getItemsEffectiveForBlock(BlockState blockState) {
    //?}
        //? if <1.20 {
        /*return Registry.ITEM.stream()
        *///?}
                //? if <1.17 {
                /*.filter(item -> item instanceof DiggerItem)
                .filter(item -> ((MiningToolItemAccessor) item).getEffectiveBlocks().contains(block))
                *///?} else if >=1.20 <1.21.3 {
        /*return DGU.getWorld().registryAccess().registryOrThrow(Registries.ITEM).stream()
                *///?} else if >=1.21.3 {
        return DGU.getWorld().registryAccess().lookupOrThrow(Registries.ITEM).stream()
                //?}
                //? if >=1.17 {
                .filter(item -> item.getDefaultInstance().isCorrectToolForDrops(blockState))
                //?}
                .collect(Collectors.toList());
    }

    //? if <1.17 {
    /*private static List<ItemStack> populateDropsIfPossible(BlockState blockState, Item firstToolItem) {
    *///?} else {
    private static void populateDropsIfPossible(BlockState blockState, Item firstToolItem, List<ItemStack> outDrops) {
    //?}
        //? if >=1.16 {
        MinecraftServer minecraftServer = DGU.getCurrentlyRunningServer();
        //?}
        //If we have local world context, we can actually evaluate loot tables and determine actual data
        //? if <1.16 {
        /*ServerLevel serverWorld = (ServerLevel) DGU.getWorld();
        *///?} else if >=1.16 <1.17 {
        /*ServerLevel serverWorld = minecraftServer.overworld();
        *///?}
        //? if <1.17 {
        /*LootContext.Builder lootContext = new LootContext.Builder(serverWorld)
        *///?}
                //? if >=1.16 <1.17 {
                /*.withParameter(LootContextParams.BLOCK_STATE, blockState)
                .withParameter(LootContextParams.BLOCK_POS, new BlockPos(0, 0, 0))
                .withParameter(LootContextParams.TOOL, firstToolItem.getDefaultInstance())
                *///?}
                //? if <1.17 {
                /*.withOptionalRandomSeed(0L);
        return blockState.getDrops(lootContext);
                *///?} else {
        if (minecraftServer != null) {
            //If we have local world context, we can actually evaluate loot tables and determine actual data
            ServerLevel serverWorld = minecraftServer.overworld();
                //?}
            //? if >=1.17 <1.20 {
            /*LootContext.Builder lootContext = new LootContext.Builder(serverWorld)
            *///?} else if >=1.20 {
            LootParams.Builder lootContextParameterSet = new LootParams.Builder(serverWorld)
            //?}
                    //? if >=1.17 {
                    .withParameter(LootContextParams.BLOCK_STATE, blockState)
                    .withParameter(LootContextParams.ORIGIN, Vec3.ZERO)
                    //?}
                    //? if >=1.17 <1.20 {
                    /*.withParameter(LootContextParams.TOOL, firstToolItem.getDefaultInstance())
                    .withOptionalRandomSeed(0L);
            outDrops.addAll(blockState.getDrops(lootContext));
                    *///?} else if >=1.20 {
                    .withParameter(LootContextParams.TOOL, firstToolItem.getDefaultInstance());
            outDrops.addAll(blockState.getDrops(lootContextParameterSet));
                    //?}
        //? if >=1.17 {
        } else {
            //If we're lacking world context to correctly determine drops, assume that default drop is ItemBlock stack in quantity of 1
            Item itemBlock = blockState.getBlock().asItem();
            if (itemBlock != Items.AIR) {
                outDrops.add(itemBlock.getDefaultInstance());
            }
        }
        //?}
    }

    private static String getPropertyTypeName(Property<?> property) {
        //Explicitly handle default minecraft properties
        if (property instanceof BooleanProperty) {
            return "bool";
        }
        if (property instanceof IntegerProperty) {
            return "int";
        }
        if (property instanceof EnumProperty) {
            return "enum";
        }

        //Use simple class name as fallback, this code will give something like
        //example_type for ExampleTypeProperty class name
        String rawPropertyName = property.getClass().getSimpleName().replace("Property", "");
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rawPropertyName);
    }

    private static <T extends Comparable<T>> JsonObject generateStateProperty(Property<T> property) {
        JsonObject propertyObject = new JsonObject();
        Collection<T> propertyValues = property.getPossibleValues();

        propertyObject.addProperty("name", property.getName());
        propertyObject.addProperty("type", getPropertyTypeName(property));
        propertyObject.addProperty("num_values", propertyValues.size());

        //Do not add values for vanilla boolean properties, they are known by default
        //? if <1.18 {
        /*if (!(property instanceof BooleanProperty)) {
        *///?} else if >=1.18 <=1.18 {
        /*if (!(property instanceof BooleanProperty) && !(property instanceof IntegerProperty && property.getName(propertyValues.iterator().next()).equals("0"))) {
        *///?} else {
        if (!(property instanceof BooleanProperty)) {
        //?}
            JsonArray propertyValuesArray = new JsonArray();
            for (T propertyValue : propertyValues) {
                propertyValuesArray.add(property.getName(propertyValue));
            }
            propertyObject.add("values", propertyValuesArray);
        }
        return propertyObject;
    }

    //? if <1.17 {
    /*public static JsonObject generateBlock(Block block) {
    *///?} else {
    private static String findMatchingBlockMaterial(BlockState blockState, List<MaterialsDataGenerator.MaterialInfo> materials) {
        List<MaterialsDataGenerator.MaterialInfo> matchingMaterials = materials.stream()
                .filter(material -> material.getPredicate().test(blockState))
                .collect(Collectors.toList());

        if (matchingMaterials.size() > 1) {
            var firstMaterial = matchingMaterials.getFirst();
            var otherMaterials = matchingMaterials.subList(1, matchingMaterials.size());

            if (!otherMaterials.stream().allMatch(firstMaterial::includesMaterial)) {
                logger.error("Block {} matches multiple materials: {}", blockState.getBlock(), matchingMaterials);
            }
        }
        if (matchingMaterials.isEmpty()) {
            return "default";
        }
    //?}
        //? if >=1.17 <1.20.5 {
        /*return matchingMaterials.getFirst().getMaterialName();
        *///?} else if >=1.20.5 <26.1 {
        /*return matchingMaterials.stream()
            .filter(m -> m.getMaterialName().startsWith("mineable/"))
            .findFirst()
            .map(MaterialsDataGenerator.MaterialInfo::getMaterialName)
            .orElseGet(() -> matchingMaterials.getFirst().getMaterialName());
        *///?} else if >=26.1 {
        return matchingMaterials.getFirst().getMaterialName();
        //?}
    //? if >=1.17 {
    }

    public static JsonObject generateBlock(List<MaterialsDataGenerator.MaterialInfo> materials, Block block) {
    //?}
        JsonObject blockDesc = new JsonObject();
        //? if >=1.21.5 {
        Registry<Block> blockRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.BLOCK);
        //?}

        List<BlockState> blockStates = block.getStateDefinition().getPossibleStates();
        BlockState defaultState = block.defaultBlockState();
        //? if <1.16 {
        /*ResourceLocation registryKey = Registry.BLOCK.getKey(block);
        *///?} else if >=1.16 <1.20 {
        /*ResourceLocation registryKey = Registry.BLOCK.getResourceKey(block).orElseThrow().location();
        *///?} else if >=1.20 <1.21.5 {
        /*ResourceLocation registryKey = BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow().location();
        *///?} else if >=1.21.5 <1.21.11 {
        /*ResourceLocation registryKey = blockRegistry.getKey(block);
        *///?} else {
        Identifier registryKey = blockRegistry.getKey(block);
        //?}
        String localizationKey = block.getDescriptionId();
        //? if <1.17 {
        /*List<Item> effectiveTools = getItemsEffectiveForBlock(block);
        *///?} else {
        List<Item> effectiveTools = getItemsEffectiveForBlock(defaultState);
        //?}

        //? if <1.20 {
        /*blockDesc.addProperty("id", Registry.BLOCK.getId(block));
        *///?} else if >=1.20 <1.21.5 {
        /*blockDesc.addProperty("id", BuiltInRegistries.BLOCK.getId(block));
        *///?} else {
        blockDesc.addProperty("id", blockRegistry.getId(block));
        //?}
        //? if >1.18 {
        blockDesc.addProperty("name", registryKey.getPath());
        //?}
        //? if >=1.18 {
        blockDesc.addProperty("displayName", DGU.translateText(localizationKey));
        //?}
        //? if <=1.18 {
        /*blockDesc.addProperty("name", registryKey.getPath());
        *///?}
        //? if <1.18 {
        /*blockDesc.addProperty("displayName", DGU.translateText(localizationKey));

        *///?}
        //? if <1.17 {
        /*float hardness = block.defaultBlockState().getDestroySpeed(null, null);

        blockDesc.addProperty("hardness", hardness);
        *///?}

        //? if >=1.17 {
        blockDesc.addProperty("hardness", block.defaultDestroyTime());
        //?}
        blockDesc.addProperty("resistance", block.getExplosionResistance());
        //? if <1.18 {
        /*blockDesc.addProperty("stackSize", block.asItem().getMaxStackSize());
        *///?}
        //? if <1.17 {
        /*blockDesc.addProperty("diggable", hardness != -1.0f && !(block instanceof AirBlock));
        JsonObject effTools = new JsonObject();
        effectiveTools.forEach(item -> effTools.addProperty(
                String.valueOf(Registry.ITEM.getId(item)), // key
        *///?}
                //? if <1.16 {
                /*item.getDestroySpeed(DGU.asStack(item), defaultState) // value
                *///?} else if >=1.16 <1.17 {
                /*item.getDestroySpeed(item.getDefaultInstance(), defaultState) // value
                *///?}
        //? if <1.17 {
        /*));
        blockDesc.add("effectiveTools", effTools);
        *///?} else if >=1.17 <1.18 {
        /*blockDesc.addProperty("diggable", block.defaultDestroyTime() != -1.0f && !(block instanceof AirBlock));
//        JsonObject effTools = new JsonObject();
//        effectiveTools.forEach(item -> effTools.addProperty(
//                String.valueOf(Registry.ITEM.getRawId(item)), // key
//                item.getMiningSpeedMultiplier(item.getDefaultStack(), defaultState) // value
//        ));
//        blockDesc.add("effectiveTools", effTools);
        blockDesc.addProperty("material", findMatchingBlockMaterial(defaultState, materials));
        *///?}
        //? if <1.18 {

        /*blockDesc.addProperty("transparent", !defaultState.canOcclude());
        blockDesc.addProperty("emitLight", defaultState.getLightEmission());
        blockDesc.addProperty("filterLight", defaultState.getLightBlock(EmptyBlockGetter.INSTANCE, BlockPos.ZERO));

        blockDesc.addProperty("defaultState", Block.getId(defaultState));
        *///?} else if >1.18 <1.20.5 {
        /*blockDesc.addProperty("stackSize", block.asItem().getMaxStackSize());
        *///?} else if >=1.20.5 {
        blockDesc.addProperty("stackSize", block.asItem().getDefaultMaxStackSize());
        //?}
        //? if >1.18 {
        blockDesc.addProperty("diggable", block.defaultDestroyTime() != -1.0f && !(block instanceof AirBlock));
//        JsonObject effTools = new JsonObject();
//        effectiveTools.forEach(item -> effTools.addProperty(
//                String.valueOf(Registry.ITEM.getRawId(item)), // key
//                item.getMiningSpeedMultiplier(item.getDefaultStack(), defaultState) // value
//        ));
//        blockDesc.add("effectiveTools", effTools);
        blockDesc.addProperty("material", findMatchingBlockMaterial(defaultState, materials));

        blockDesc.addProperty("transparent", !defaultState.canOcclude());
        blockDesc.addProperty("emitLight", defaultState.getLightEmission());
        //?}
        //? if >1.18 <1.21.3 {
        /*blockDesc.addProperty("filterLight", defaultState.getLightBlock(EmptyBlockGetter.INSTANCE, BlockPos.ZERO));
        *///?} else if >=1.21.3 <26.1 {
        /*blockDesc.addProperty("filterLight", defaultState.getLightBlock());
        *///?} else if >=26.1 {
        blockDesc.addProperty("filterLight", defaultState.getLightDampening());
        //?}
        //? if >1.18 {

        blockDesc.addProperty("defaultState", Block.getId(defaultState));
        //?}
        blockDesc.addProperty("minStateId", Block.getId(blockStates.getFirst()));
        blockDesc.addProperty("maxStateId", Block.getId(blockStates.getLast()));


        JsonArray stateProperties = new JsonArray();
        for (Property<?> property : block.getStateDefinition().getProperties()) {
            stateProperties.add(generateStateProperty(property));
        }
        blockDesc.add("states", stateProperties);
        // Let's not generate block drops...
        // List<ItemStack> actualBlockDrops = new ArrayList<>();
        // populateDropsIfPossible(defaultState, effectiveTools.isEmpty() ? Items.AIR : effectiveTools.getFirst(), actualBlockDrops);

//        List<ItemStack> drops = populateDropsIfPossible(defaultState, effectiveTools.stream().findFirst().orElse(Items.AIR));
        //? if >=1.16 <1.17 {
        /*List<ItemStack> drops = populateDropsIfPossible(defaultState, effectiveTools.stream().findFirst().orElse(Items.AIR));
        *///?} else if >=1.18 <=1.18 {
        /*// for (ItemStack dropStack : actualBlockDrops) {
        //     dropsArray.add(Item.getRawId(dropStack.getItem()));
        // }
        JsonArray dropsArray = new JsonArray();
        blockDesc.add("drops", dropsArray);
        blockDesc.addProperty("diggable", block.defaultDestroyTime() != -1.0f && !(block instanceof AirBlock));
        blockDesc.addProperty("transparent", !defaultState.canOcclude());
        blockDesc.addProperty("filterLight", defaultState.getLightBlock(EmptyBlockGetter.INSTANCE, BlockPos.ZERO));
        blockDesc.addProperty("emitLight", defaultState.getLightEmission());
        VoxelShape blockCollisionShape = defaultState.getCollisionShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
        blockDesc.addProperty("boundingBox", blockCollisionShape.isEmpty() ? "empty" : "block");
        blockDesc.addProperty("stackSize", block.asItem().getMaxStackSize());
        blockDesc.addProperty("material", findMatchingBlockMaterial(defaultState, materials));
        *///?}
        //? if >=1.17 {
        //Only add harvest tools if tool is required for harvesting this block
        if (defaultState.requiresCorrectToolForDrops()) {
        //?}
            //? if >=1.21.5 {
            Registry<Item> itemRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.ITEM);
            //?}
            //? if >=1.17 {
            JsonObject effectiveToolsObject = new JsonObject();
            for (Item effectiveItem : effectiveTools) {
            //?}
                //? if >=1.17 <1.21.5 {
                /*effectiveToolsObject.addProperty(Integer.toString(Item.getId(effectiveItem)), true);
                *///?} else if >=1.21.5 {
                effectiveToolsObject.addProperty(Integer.toString(itemRegistry.getId(effectiveItem)), true);
                //?}
            //? if >=1.17 {
            }
            blockDesc.add("harvestTools", effectiveToolsObject);
        }
            //?}
        //? if >=1.17 <1.18 {

        /*List<ItemStack> actualBlockDrops = new ArrayList<>();
        populateDropsIfPossible(defaultState, effectiveTools.isEmpty() ? Items.AIR : effectiveTools.getFirst(), actualBlockDrops);
        *///?}
        //? if <1.18 {

        /*JsonArray dropsArray = new JsonArray();
        *///?}
//        drops.forEach(dropped -> dropsArray.add(Item.getRawId(dropped.getItem())));
        //? if >=1.16 <1.17 {
        /*drops.forEach(dropped -> dropsArray.add(Item.getId(dropped.getItem())));
        *///?} else if >=1.17 <1.18 {
        /*for (ItemStack dropStack : actualBlockDrops) {
            dropsArray.add(Item.getId(dropStack.getItem()));
        }
        *///?}
        //? if <1.18 {
        /*blockDesc.add("drops", dropsArray);

        VoxelShape blockCollisionShape = defaultState.getCollisionShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
        blockDesc.addProperty("boundingBox", blockCollisionShape.isEmpty() ? "empty" : "block");

        *///?} else if >=1.18 <=1.18 {
        /*blockDesc.addProperty("defaultState", Block.getId(defaultState));
//        JsonObject effTools = new JsonObject();
//        effectiveTools.forEach(item -> effTools.addProperty(
//                String.valueOf(Registry.ITEM.getRawId(item)), // key
//                item.getMiningSpeedMultiplier(item.getDefaultStack(), defaultState) // value
//        ));
//        blockDesc.add("effectiveTools", effTools);
        *///?} else {

        List<ItemStack> actualBlockDrops = new ArrayList<>();
        populateDropsIfPossible(defaultState, effectiveTools.isEmpty() ? Items.AIR : effectiveTools.getFirst(), actualBlockDrops);

        JsonArray dropsArray = new JsonArray();
        //?}
        //? if >=1.21.5 {
        Registry<Item> itemRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.ITEM);
        //?}
        //? if >1.18 {
        for (ItemStack dropStack : actualBlockDrops) {
        //?}
            //? if >1.18 <1.21.5 {
            /*dropsArray.add(Item.getId(dropStack.getItem()));
            *///?} else if >=1.21.5 {
            dropsArray.add(itemRegistry.getId(dropStack.getItem()));
            //?}
        //? if >1.18 {
        }
        blockDesc.add("drops", dropsArray);

        VoxelShape blockCollisionShape = defaultState.getCollisionShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
        blockDesc.addProperty("boundingBox", blockCollisionShape.isEmpty() ? "empty" : "block");

        //?}
        return blockDesc;
    }

    @Override
    public String getDataName() {
        return "blocks";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultBlocksArray = new JsonArray();
        //? if >=1.17 <=1.18 {
        /*Registry<Block> blockRegistry = Registry.BLOCK;
        *///?} else if >=1.19 <1.20 {
        /*Registry<Block> blockRegistry = Registry.BLOCK;
        *///?}
        //? if >=1.17 {
        List<MaterialsDataGenerator.MaterialInfo> availableMaterials = MaterialsDataGenerator.getGlobalMaterialInfo();
        //?}
        //? if >=1.21.5 {
        Registry<Block> blockRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.BLOCK);
        //?}

        //? if <1.17 {
        /*Registry.BLOCK.forEach(block -> resultBlocksArray.add(generateBlock(block)));
        *///?} else if >=1.17 <=1.18 {
        /*blockRegistry.forEach(block -> resultBlocksArray.add(generateBlock(availableMaterials, block)));
        *///?} else if >1.18 <1.19 {
        /*Registry.BLOCK.forEach(block -> resultBlocksArray.add(generateBlock(availableMaterials, block)));
        *///?} else if >=1.19 <1.20 {
        /*blockRegistry.forEach(block -> resultBlocksArray.add(generateBlock(availableMaterials, block)));
        *///?} else if >=1.20 <1.21.5 {
        /*BuiltInRegistries.BLOCK.forEach(block -> resultBlocksArray.add(generateBlock(availableMaterials, block)));
        *///?} else {
        blockRegistry.forEach(block -> resultBlocksArray.add(generateBlock(availableMaterials, block)));
        //?}
        return resultBlocksArray;
    }
}
