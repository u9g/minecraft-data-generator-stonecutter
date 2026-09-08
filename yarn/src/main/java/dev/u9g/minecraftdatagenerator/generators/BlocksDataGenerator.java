package dev.u9g.minecraftdatagenerator.generators;

//? if >=1.8.9 {
import com.google.common.base.CaseFormat;
//?}
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//? if >=1.8.9 <1.12.2 {
/*import com.google.gson.JsonPrimitive;
*///?}
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.mixin.BlockAccessor;
*///?}
//? if <1.17 {
/*import dev.u9g.minecraftdatagenerator.mixin.MiningToolItemAccessor;
*///?}
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if >=1.9.4 <1.14 {
/*import dev.u9g.minecraftdatagenerator.util.EmptyBlockView;
*///?}
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?}
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
//? if >=1.8.9 {
import net.minecraft.block.BlockState;
//?}
//? if <1.13 {
/*import net.minecraft.block.TransparentBlock;
*///?}
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
//? if <1.14 {
/*import net.minecraft.item.ToolItem;
*///?} else if >=1.14 <1.15 {
/*import net.minecraft.item.Items;
*///?} else if >=1.16 {
import net.minecraft.item.Items;
//?}
//? if >=1.14 <1.17 {
/*import net.minecraft.item.MiningToolItem;
*///?}
//? if >=1.15 <1.20 {
/*import net.minecraft.loot.context.LootContext;
*///?} else if >=1.20 <1.21.3 {
/*import net.minecraft.loot.context.LootContextParameterSet;
*///?}
//? if >=1.16 {
import net.minecraft.loot.context.LootContextParameters;
//?}
//? if >=1.21.3 {
import net.minecraft.loot.context.LootWorldContext;
//?}
//? if >=1.20 {
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
//?}
//? if >=1.16 {
import net.minecraft.server.MinecraftServer;
//?}
//? if >=1.14 {
import net.minecraft.server.world.ServerWorld;
//?}
//? if >=1.8.9 {
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
//?}
//? if >=1.8.9 <1.14 {
/*import net.minecraft.state.property.IntProperty;
*///?} else if >=1.14 <1.15 {
/*import net.minecraft.state.property.IntegerProperty;
*///?} else if >=1.15 {
import net.minecraft.state.property.IntProperty;
//?}
//? if >=1.8.9 {
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
//?}
//? if >=1.17 {
import net.minecraft.util.math.Vec3d;
//?}
//? if >=1.13 <1.20 {
/*import net.minecraft.util.registry.Registry;
*///?}
//? if >=1.13 <1.14 {
/*import net.minecraft.util.shapes.VoxelShape;
*///?} else if >=1.14 {
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.EmptyBlockView;
//?}
//? if >=1.14 <1.15 {
/*import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;
*///?} else if >=1.17 {
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//?}

//? if <1.14 {
/*import java.util.ArrayList;
*///?} else if >=1.17 <1.18 {
/*import java.util.ArrayList;
*///?} else if >1.18 {
import java.util.ArrayList;
//?}
//? if >=1.8.9 {
import java.util.Collection;
//?}
import java.util.List;
//? if <1.15 {
/*import java.util.Objects;
*///?}
//? if >=1.13 {
import java.util.stream.Collectors;
//?}

public class BlocksDataGenerator implements IDataGenerator {
    //? if <1.17 {
    /*private static List<Item> getItemsEffectiveForBlock(Block block) {
    *///?}
        //? if <1.13 {
        /*List<Item> items = new ArrayList<>();
        for (Item item : Registries.ITEMS) {
            if (item instanceof ToolItem && ((MiningToolItemAccessor) item).getEffectiveBlocks().contains(block)) {
                items.add(item);
            }
        }
        return items;
        *///?} else if >=1.17 {

    private static final Logger logger = LoggerFactory.getLogger(BlocksDataGenerator.class);

    private static List<Item> getItemsEffectiveForBlock(BlockState blockState) {
        //?}
        //? if >=1.13 <1.20 {
        /*return Registry.ITEM.stream()
        *///?}
                //? if >=1.13 <1.14 {
                /*.filter(item -> item instanceof ToolItem)
                *///?} else if >=1.14 <1.17 {
                /*.filter(item -> item instanceof MiningToolItem)
                *///?}
                //? if >=1.13 <1.17 {
                /*.filter(item -> ((MiningToolItemAccessor) item).getEffectiveBlocks().contains(block))
                *///?} else if >=1.20 <1.21.3 {
        /*return DGU.getWorld().getRegistryManager().get(RegistryKeys.ITEM).stream()
                *///?} else if >=1.21.3 {
        return DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ITEM).stream()
                //?}
                //? if >=1.17 {
                .filter(item -> item.getDefaultStack().isSuitableFor(blockState))
                //?}
                //? if >=1.13 {
                .collect(Collectors.toList());
                //?}
    }

    //? if <1.8.9 {
    /*private static List<ItemStack> populateDropsIfPossible(Item firstToolItem) {
    *///?} else if >=1.8.9 <1.17 {
    /*private static List<ItemStack> populateDropsIfPossible(BlockState blockState, Item firstToolItem) {
    *///?}
        //? if <1.14 {
        /*return new ArrayList<>();
        *///?} else if >=1.17 {
    private static void populateDropsIfPossible(BlockState blockState, Item firstToolItem, List<ItemStack> outDrops) {
        //?}
        //? if >=1.16 {
        MinecraftServer minecraftServer = DGU.getCurrentlyRunningServer();
        //?}
        //If we have local world context, we can actually evaluate loot tables and determine actual data
        //? if >=1.14 <1.16 {
        /*ServerWorld serverWorld = (ServerWorld) DGU.getWorld();
        *///?}
        //? if >=1.14 <1.15 {
        /*var lootContext = new LootContext.Builder(serverWorld)
                .put(LootContextParameters.POSITION, BlockPos.ORIGIN)
                .put(LootContextParameters.TOOL, DGU.stackFor(firstToolItem));
        blockState.getDroppedStacks(lootContext);
        *///?} else if >=1.16 <1.17 {
        /*ServerWorld serverWorld = minecraftServer.getOverworld();
        *///?}
        //? if >=1.15 <1.17 {
        /*LootContext.Builder lootContext = new LootContext.Builder(serverWorld)
        *///?}
                //? if >=1.15 <1.16 {
                /*.setRandom(0L);
                *///?} else if >=1.16 <1.17 {
                /*.parameter(LootContextParameters.BLOCK_STATE, blockState)
                .parameter(LootContextParameters.POSITION, new BlockPos(0, 0, 0))
                .parameter(LootContextParameters.TOOL, firstToolItem.getStackForRender())
                .random(0L);
                *///?}
        //? if >=1.14 <1.17 {
        /*return blockState.getDroppedStacks(lootContext);
        *///?} else if >=1.17 {
        if (minecraftServer != null) {
            //If we have local world context, we can actually evaluate loot tables and determine actual data
            ServerWorld serverWorld = minecraftServer.getOverworld();
        //?}
            //? if >=1.17 <1.20 {
            /*LootContext.Builder lootContext = new LootContext.Builder(serverWorld)
                    .parameter(LootContextParameters.BLOCK_STATE, blockState)
                    .parameter(LootContextParameters.ORIGIN, Vec3d.ZERO)
                    .parameter(LootContextParameters.TOOL, firstToolItem.getDefaultStack())
                    .random(0L);
            outDrops.addAll(blockState.getDroppedStacks(lootContext));
            *///?} else if >=1.20 <1.21.3 {
            /*LootContextParameterSet.Builder lootContextParameterSet = new LootContextParameterSet.Builder(serverWorld)
            *///?} else if >=1.21.3 {
            LootWorldContext.Builder lootContextParameterSet = new LootWorldContext.Builder(serverWorld)
            //?}
                    //? if >=1.20 {
                    .add(LootContextParameters.BLOCK_STATE, blockState)
                    .add(LootContextParameters.ORIGIN, Vec3d.ZERO)
                    .add(LootContextParameters.TOOL, firstToolItem.getDefaultStack());
            outDrops.addAll(blockState.getDroppedStacks(lootContextParameterSet));
                    //?}
        //? if >=1.17 {
        } else {
            //If we're lacking world context to correctly determine drops, assume that default drop is ItemBlock stack in quantity of 1
            Item itemBlock = blockState.getBlock().asItem();
            if (itemBlock != Items.AIR) {
                outDrops.add(itemBlock.getDefaultStack());
            }
        }
        //?}
    //? if >=1.8.9 {
    }

    private static String getPropertyTypeName(Property<?> property) {
        //Explicitly handle default minecraft properties
        if (property instanceof BooleanProperty) {
            return "bool";
        }
    //?}
        //? if >=1.8.9 <1.14 {
        /*if (property instanceof IntProperty) {
        *///?} else if >=1.14 <1.15 {
        /*if (property instanceof IntegerProperty) {
        *///?} else if >=1.15 {
        if (property instanceof IntProperty) {
        //?}
            //? if >=1.8.9 {
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
        Collection<T> propertyValues = property.getValues();

        propertyObject.addProperty("name", property.getName());
        propertyObject.addProperty("type", getPropertyTypeName(property));
        propertyObject.addProperty("num_values", propertyValues.size());

        //Do not add values for vanilla boolean properties, they are known by default
            //?}
        //? if >=1.8.9 <1.18 {
        /*if (!(property instanceof BooleanProperty)) {
        *///?} else if >=1.18 <=1.18 {
        /*if (!(property instanceof BooleanProperty) && !(property instanceof IntProperty && property.name(propertyValues.iterator().next()).equals("0"))) {
        *///?} else if >1.18 {
        if (!(property instanceof BooleanProperty)) {
        //?}
            //? if >=1.8.9 {
            JsonArray propertyValuesArray = new JsonArray();
            for (T propertyValue : propertyValues) {
            //?}
                //? if >=1.8.9 <1.12.2 {
                /*propertyValuesArray.add(new JsonPrimitive(property.name(propertyValue)));
                *///?} else if >=1.12.2 <1.14 {
                /*propertyValuesArray.add(property.name(propertyValue));
                *///?} else if >=1.14 <1.15 {
                /*propertyValuesArray.add(property.getValueAsString(propertyValue));
                *///?} else if >=1.15 {
                propertyValuesArray.add(property.name(propertyValue));
                //?}
            //? if >=1.8.9 {
            }
            propertyObject.add("values", propertyValuesArray);
        }
        return propertyObject;
            //?}
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
        *///?} else if >=1.20.5 {
        return matchingMaterials.stream()
            .filter(m -> m.getMaterialName().startsWith("mineable/"))
            .findFirst()
            .map(MaterialsDataGenerator.MaterialInfo::getMaterialName)
            .orElseGet(() -> matchingMaterials.getFirst().getMaterialName());
        //?}
    //? if >=1.17 {
    }

    public static JsonObject generateBlock(List<MaterialsDataGenerator.MaterialInfo> materials, Block block) {
    //?}
        JsonObject blockDesc = new JsonObject();

        //? if <1.8.9 {
        /*String registryKey = Registries.BLOCKS.getId(block);
        *///?} else if >=1.8.9 <1.14 {
        /*List<BlockState> blockStates = block.getStateManager().getBlockStates();
        *///?} else if >=1.14 <1.15 {
        /*List<BlockState> blockStates = block.getStateFactory().getStates();
        *///?} else {
        List<BlockState> blockStates = block.getStateManager().getStates();
        //?}
        //? if >=1.8.9 {
        BlockState defaultState = block.getDefaultState();
        //?}
        //? if >=1.8.9 <1.13 {
        /*Identifier registryKey = Registries.BLOCKS.getIdentifier(block);
        *///?} else if >=1.13 <1.16 {
        /*Identifier registryKey = Registry.BLOCK.getId(block);
        *///?} else if >=1.16 <1.20 {
        /*Identifier registryKey = Registry.BLOCK.getKey(block).orElseThrow().getValue();
        *///?} else if >=1.20 {
        Identifier registryKey = Registries.BLOCK.getKey(block).orElseThrow().getValue();
        //?}
        //? if >=1.13 {
        String localizationKey = block.getTranslationKey();
        //?}
        //? if <1.17 {
        /*List<Item> effectiveTools = getItemsEffectiveForBlock(block);
        *///?} else {
        List<Item> effectiveTools = getItemsEffectiveForBlock(defaultState);
        //?}

        //? if <1.13 {
        /*blockDesc.addProperty("id", Registries.BLOCKS.getRawId(block));
        *///?}
        //? if <1.8.9 {
        /*blockDesc.addProperty("name", Objects.requireNonNull(registryKey));
        *///?} else if >=1.13 <1.20 {
        /*blockDesc.addProperty("id", Registry.BLOCK.getRawId(block));
        *///?}
        //? if >=1.8.9 <1.15 {
        /*blockDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        *///?}
        //? if <1.11.2 {
        /*if (!block.getTranslatedName().startsWith("tile.")) {
            blockDesc.addProperty("displayName", block.getTranslatedName());
        }
        *///?} else if >=1.11.2 <1.13 {
        /*blockDesc.addProperty("displayName", block.getTranslatedName());
        *///?} else if >=1.20 {
        blockDesc.addProperty("id", Registries.BLOCK.getRawId(block));
        //?}
        //? if >1.18 {
        blockDesc.addProperty("name", registryKey.getPath());
        //?}
        //? if >=1.18 {
        blockDesc.addProperty("displayName", DGU.translateText(localizationKey));
        //?}
        //? if >=1.15 <=1.18 {
        /*blockDesc.addProperty("name", registryKey.getPath());
        *///?}
        //? if >=1.13 <1.18 {
        /*blockDesc.addProperty("displayName", DGU.translateText(localizationKey));
        *///?}

        //? if <1.8.9 {
        /*float hardness = block.method_471(null, 0, 0, 0);
        *///?} else if >=1.8.9 <1.9.4 {
        /*float hardness = block.getStrength(null, null);
        *///?} else if >=1.9.4 <1.17 {
        /*float hardness = block.getDefaultState().getHardness(null, null);
        *///?}
        //? if <1.17 {

        /*blockDesc.addProperty("hardness", hardness);
        *///?}
        //? if <1.13 {
        /*blockDesc.addProperty("resistance", ((BlockAccessor) block).getBlastResistance());
        *///?}
        //? if >=1.11.2 <1.13 {
        /*blockDesc.addProperty("stackSize", Item.fromBlock(block).getMaxCount());
        *///?}

        //? if >=1.17 {
        blockDesc.addProperty("hardness", block.getHardness());
        //?}
        //? if >=1.13 {
        blockDesc.addProperty("resistance", block.getBlastResistance());
        //?}
        //? if >=1.13 <1.14 {
        /*blockDesc.addProperty("stackSize", block.getItem().getMaxCount());
        *///?} else if >=1.14 <1.15 {
        /*blockDesc.addProperty("stackSize", block.asItem().getMaxAmount());
        *///?} else if >=1.15 <1.18 {
        /*blockDesc.addProperty("stackSize", block.asItem().getMaxCount());
        *///?}
        //? if <1.17 {
        /*blockDesc.addProperty("diggable", hardness != -1.0f && !(block instanceof AirBlock));
        JsonObject effTools = new JsonObject();
        effectiveTools.forEach(item -> effTools.addProperty(
        *///?}
                //? if <1.13 {
                /*String.valueOf(Registries.ITEMS.getRawId(item)), // key
                *///?}
                //? if <1.9.4 {
                /*item.getMiningSpeedMultiplier(DGU.stackFor(item), block) // value
                *///?} else if >=1.13 <1.17 {
                /*String.valueOf(Registry.ITEM.getRawId(item)), // key
                *///?}
                //? if >=1.9.4 <1.15 {
                /*item.getBlockBreakingSpeed(DGU.stackFor(item), defaultState) // value
                *///?} else if >=1.15 <1.16 {
                /*item.getMiningSpeed(DGU.asStack(item), defaultState) // value
                *///?} else if >=1.16 <1.17 {
                /*item.getMiningSpeedMultiplier(item.getStackForRender(), defaultState) // value
                *///?}
        //? if <1.17 {
        /*));
        blockDesc.add("effectiveTools", effTools);
        *///?}
        //? if <1.13 {
        /*blockDesc.addProperty("transparent", block instanceof TransparentBlock);
        *///?}
        //? if <1.9.4 {
        /*blockDesc.addProperty("emitLight", block.getLightLevel());
        blockDesc.addProperty("filterLight", block.getOpacity());
        *///?} else if >=1.17 <1.18 {
        /*blockDesc.addProperty("diggable", block.getHardness() != -1.0f && !(block instanceof AirBlock));
//        JsonObject effTools = new JsonObject();
//        effectiveTools.forEach(item -> effTools.addProperty(
//                String.valueOf(Registry.ITEM.getRawId(item)), // key
//                item.getMiningSpeedMultiplier(item.getDefaultStack(), defaultState) // value
//        ));
//        blockDesc.add("effectiveTools", effTools);
        blockDesc.addProperty("material", findMatchingBlockMaterial(defaultState, materials));
        *///?}

        //? if >=1.13 <1.15 {
        /*blockDesc.addProperty("transparent", !defaultState.isFullOpaque(EmptyBlockView.INSTANCE, BlockPos.ORIGIN));
        *///?} else if >=1.15 <1.18 {
        /*blockDesc.addProperty("transparent", !defaultState.isOpaque());
        *///?}
        //? if >=1.9.4 <1.18 {
        /*blockDesc.addProperty("emitLight", defaultState.getLuminance());
        *///?}
        //? if >=1.9.4 <1.13 {
        /*blockDesc.addProperty("filterLight", block.getDefaultState().getOpacity());
        *///?} else if >=1.13 <1.15 {
        /*blockDesc.addProperty("filterLight", block.getLightSubtracted(block.getDefaultState(), EmptyBlockView.INSTANCE, BlockPos.ORIGIN));
        *///?} else if >=1.15 <1.18 {
        /*blockDesc.addProperty("filterLight", defaultState.getOpacity(EmptyBlockView.INSTANCE, BlockPos.ORIGIN));
        *///?}

        //? if <1.8.9 {
        /*blockDesc.addProperty("boundingBox", boundingBox(block));
        *///?} else if >=1.13 <1.18 {
        /*blockDesc.addProperty("defaultState", Block.getRawIdFromState(defaultState));
        *///?} else if >1.18 {
        blockDesc.addProperty("stackSize", block.asItem().getMaxCount());
        blockDesc.addProperty("diggable", block.getHardness() != -1.0f && !(block instanceof AirBlock));
//        JsonObject effTools = new JsonObject();
//        effectiveTools.forEach(item -> effTools.addProperty(
//                String.valueOf(Registry.ITEM.getRawId(item)), // key
//                item.getMiningSpeedMultiplier(item.getDefaultStack(), defaultState) // value
//        ));
//        blockDesc.add("effectiveTools", effTools);
        blockDesc.addProperty("material", findMatchingBlockMaterial(defaultState, materials));

        blockDesc.addProperty("transparent", !defaultState.isOpaque());
        blockDesc.addProperty("emitLight", defaultState.getLuminance());
        //?}
        //? if >1.18 <1.21.3 {
        /*blockDesc.addProperty("filterLight", defaultState.getOpacity(EmptyBlockView.INSTANCE, BlockPos.ORIGIN));
        *///?} else if >=1.21.3 {
        blockDesc.addProperty("filterLight", defaultState.getOpacity());
        //?}
        //? if >1.18 {

        blockDesc.addProperty("defaultState", Block.getRawIdFromState(defaultState));
        //?}
        //? if >=1.13 {
        blockDesc.addProperty("minStateId", Block.getRawIdFromState(blockStates.getFirst()));
        blockDesc.addProperty("maxStateId", Block.getRawIdFromState(blockStates.getLast()));
        //?}


        //? if >=1.8.9 {
        JsonArray stateProperties = new JsonArray();
        //?}
        //? if >=1.8.9 <1.14 {
        /*for (Property<?> property : block.getStateManager().getProperties()) {
        *///?} else if >=1.14 <1.15 {
        /*for (Property<?> property : block.getStateFactory().getProperties()) {
        *///?} else if >=1.15 {
        for (Property<?> property : block.getStateManager().getProperties()) {
        //?}
            //? if >=1.8.9 {
            stateProperties.add(generateStateProperty(property));
        }
        blockDesc.add("states", stateProperties);
            //?}
        //? if >=1.8.9 <1.14 {
        /*blockDesc.add("drops", new JsonArray());
        *///?}
        //? if >=1.8.9 <1.13 {
        /*blockDesc.addProperty("boundingBox", boundingBox(block, defaultState));
        *///?}
        // Let's not generate block drops...
        // List<ItemStack> actualBlockDrops = new ArrayList<>();
        // populateDropsIfPossible(defaultState, effectiveTools.isEmpty() ? Items.AIR : effectiveTools.getFirst(), actualBlockDrops);

        //? if >=1.14 <1.15 {
        /*List<ItemStack> drops = populateDropsIfPossible(defaultState, effectiveTools.stream().findFirst().orElse(Items.AIR));
        *///?}
//        List<ItemStack> drops = populateDropsIfPossible(defaultState, effectiveTools.stream().findFirst().orElse(Items.AIR));
        //? if >=1.16 <1.17 {
        /*List<ItemStack> drops = populateDropsIfPossible(defaultState, effectiveTools.stream().findFirst().orElse(Items.AIR));
        *///?} else if >=1.18 <=1.18 {
        /*// for (ItemStack dropStack : actualBlockDrops) {
        //     dropsArray.add(Item.getRawId(dropStack.getItem()));
        // }
        JsonArray dropsArray = new JsonArray();
        blockDesc.add("drops", dropsArray);
        blockDesc.addProperty("diggable", block.getHardness() != -1.0f && !(block instanceof AirBlock));
        blockDesc.addProperty("transparent", !defaultState.isOpaque());
        blockDesc.addProperty("filterLight", defaultState.getOpacity(EmptyBlockView.INSTANCE, BlockPos.ORIGIN));
        blockDesc.addProperty("emitLight", defaultState.getLuminance());
        VoxelShape blockCollisionShape = defaultState.getCollisionShape(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);
        blockDesc.addProperty("boundingBox", blockCollisionShape.isEmpty() ? "empty" : "block");
        blockDesc.addProperty("stackSize", block.asItem().getMaxCount());
        blockDesc.addProperty("material", findMatchingBlockMaterial(defaultState, materials));
        *///?}
        //? if >=1.17 {
        //Only add harvest tools if tool is required for harvesting this block
        if (defaultState.isToolRequired()) {
            JsonObject effectiveToolsObject = new JsonObject();
            for (Item effectiveItem : effectiveTools) {
                effectiveToolsObject.addProperty(Integer.toString(Item.getRawId(effectiveItem)), true);
            }
            blockDesc.add("harvestTools", effectiveToolsObject);
        }
        //?}
        //? if >=1.17 <1.18 {

        /*List<ItemStack> actualBlockDrops = new ArrayList<>();
        populateDropsIfPossible(defaultState, effectiveTools.isEmpty() ? Items.AIR : effectiveTools.getFirst(), actualBlockDrops);
        *///?}
        //? if >=1.14 <1.18 {

        /*JsonArray dropsArray = new JsonArray();
        *///?}
        //? if >=1.14 <1.15 {
        /*drops.forEach(dropped -> dropsArray.add(Item.getRawIdByItem(dropped.getItem())));
        *///?}
//        drops.forEach(dropped -> dropsArray.add(Item.getRawId(dropped.getItem())));
        //? if >=1.16 <1.17 {
        /*drops.forEach(dropped -> dropsArray.add(Item.getRawId(dropped.getItem())));
        *///?} else if >=1.17 <1.18 {
        /*for (ItemStack dropStack : actualBlockDrops) {
            dropsArray.add(Item.getRawId(dropStack.getItem()));
        }
        *///?}
        //? if >=1.14 <1.18 {
        /*blockDesc.add("drops", dropsArray);

        *///?}
        //? if >=1.13 <1.18 {
        /*VoxelShape blockCollisionShape = defaultState.getCollisionShape(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);
        blockDesc.addProperty("boundingBox", blockCollisionShape.isEmpty() ? "empty" : "block");
        *///?}

        //? if >=1.18 <=1.18 {
        /*blockDesc.addProperty("defaultState", Block.getRawIdFromState(defaultState));
//        JsonObject effTools = new JsonObject();
//        effectiveTools.forEach(item -> effTools.addProperty(
//                String.valueOf(Registry.ITEM.getRawId(item)), // key
//                item.getMiningSpeedMultiplier(item.getDefaultStack(), defaultState) // value
//        ));
//        blockDesc.add("effectiveTools", effTools);
        *///?} else if >1.18 {

        List<ItemStack> actualBlockDrops = new ArrayList<>();
        populateDropsIfPossible(defaultState, effectiveTools.isEmpty() ? Items.AIR : effectiveTools.getFirst(), actualBlockDrops);

        JsonArray dropsArray = new JsonArray();
        for (ItemStack dropStack : actualBlockDrops) {
            dropsArray.add(Item.getRawId(dropStack.getItem()));
        }
        blockDesc.add("drops", dropsArray);

        VoxelShape blockCollisionShape = defaultState.getCollisionShape(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);
        blockDesc.addProperty("boundingBox", blockCollisionShape.isEmpty() ? "empty" : "block");

        //?}
        return blockDesc;
    //? if <1.13 {
    /*}

    *///?}
    //? if <1.8.9 {
    /*private static String boundingBox(Block block) {
        if (block.getBoundingBox(DGU.getWorld(), 0, 0, 0) == null) {
    *///?} else if >=1.8.9 <1.13 {
    /*private static String boundingBox(Block block, BlockState state) {
    *///?}
        //? if >=1.8.9 <1.9.4 {
        /*// Fix needed for StairsBlock because it requires the state of the block to be set
        DGU.getWorld().setBlockState(BlockPos.ORIGIN, state);
        if (block.getCollisionBox(DGU.getWorld(), BlockPos.ORIGIN, state) == null) {
        *///?} else if >=1.9.4 <1.10.2 {
        /*if (block.getDefaultState().getCollisionBox(EmptyBlockView.INSTANCE, BlockPos.ORIGIN) == Block.EMPTY_BOX) {
        *///?} else if >=1.10.2 <1.11.2 {
        /*if (block.getDefaultState().getCollisionBox(EmptyBlockView.INSTANCE, BlockPos.ORIGIN) == null) {
        *///?} else if >=1.11.2 <1.13 {
        /*if (block.getCollisionBox(state, EmptyBlockView.INSTANCE, BlockPos.ORIGIN) == null) {
        *///?}
            //? if <1.13 {
            /*return "empty";
        }
        return "block";
            *///?}
    //? if <1.11.2 {
    /*}

    private static Item getItemFromBlock(Block block) {
    *///?}
        //? if <1.8.9 {
        /*return Registries.ITEMS.get(Registries.BLOCKS.getId(block));
        *///?} else if >=1.8.9 <1.11.2 {
        /*return Registries.ITEMS.get(Registries.BLOCKS.getIdentifier(block));
        *///?}
    }

    @Override
    public String getDataName() {
        return "blocks";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultBlocksArray = new JsonArray();
        //? if <1.13 {
        /*for (Block block : Registries.BLOCKS) {
        *///?} else if >=1.13 <1.14 {
        /*Registry<Block> blockRegistry = Registry.BLOCK;
        for (Block block : (Iterable<Block>) blockRegistry) {
        *///?}
            //? if <1.14 {
            /*resultBlocksArray.add(generateBlock(block));
        }
            *///?} else if >=1.17 <=1.18 {
        /*Registry<Block> blockRegistry = Registry.BLOCK;
            *///?} else if >=1.19 <1.20 {
        /*Registry<Block> blockRegistry = Registry.BLOCK;
            *///?}
        //? if >=1.17 {
        List<MaterialsDataGenerator.MaterialInfo> availableMaterials = MaterialsDataGenerator.getGlobalMaterialInfo();
        //?}

        //? if >=1.14 <1.17 {
        /*Registry.BLOCK.forEach(block -> resultBlocksArray.add(generateBlock(block)));
        *///?} else if >=1.17 <=1.18 {
        /*blockRegistry.forEach(block -> resultBlocksArray.add(generateBlock(availableMaterials, block)));
        *///?} else if >1.18 <1.19 {
        /*Registry.BLOCK.forEach(block -> resultBlocksArray.add(generateBlock(availableMaterials, block)));
        *///?} else if >=1.19 <1.20 {
        /*blockRegistry.forEach(block -> resultBlocksArray.add(generateBlock(availableMaterials, block)));
        *///?} else if >=1.20 {
        Registries.BLOCK.forEach(block -> resultBlocksArray.add(generateBlock(availableMaterials, block)));
        //?}
        return resultBlocksArray;
    }
}
