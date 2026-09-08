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
import dev.u9g.minecraftdatagenerator.mixin.MiningToolItemAccessor;
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
*///?} else {
import net.minecraft.item.Items;
import net.minecraft.item.MiningToolItem;
import net.minecraft.server.world.ServerWorld;
//?}
//? if >=1.8.9 {
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
//?}
//? if >=1.8.9 <1.14 {
/*import net.minecraft.state.property.IntProperty;
*///?} else if >=1.14 {
import net.minecraft.state.property.IntegerProperty;
//?}
//? if >=1.8.9 {
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
//?}
//? if >=1.13 {
import net.minecraft.util.registry.Registry;
//?}
//? if >=1.13 <1.14 {
/*import net.minecraft.util.shapes.VoxelShape;
*///?} else if >=1.14 {
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.EmptyBlockView;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;
//?}

//? if <1.14 {
/*import java.util.ArrayList;
*///?}
//? if >=1.8.9 {
import java.util.Collection;
//?}
import java.util.List;
import java.util.Objects;
//? if >=1.13 {
import java.util.stream.Collectors;
//?}

public class BlocksDataGenerator implements IDataGenerator {
    private static List<Item> getItemsEffectiveForBlock(Block block) {
        //? if <1.13 {
        /*List<Item> items = new ArrayList<>();
        for (Item item : Registries.ITEMS) {
            if (item instanceof ToolItem && ((MiningToolItemAccessor) item).getEffectiveBlocks().contains(block)) {
                items.add(item);
            }
        }
        return items;
        *///?} else {
        return Registry.ITEM.stream()
        //?}
                //? if >=1.13 <1.14 {
                /*.filter(item -> item instanceof ToolItem)
                *///?} else if >=1.14 {
                .filter(item -> item instanceof MiningToolItem)
                //?}
                //? if >=1.13 {
                .filter(item -> ((MiningToolItemAccessor) item).getEffectiveBlocks().contains(block))
                .collect(Collectors.toList());
                //?}
    }

    //? if <1.8.9 {
    /*private static List<ItemStack> populateDropsIfPossible(Item firstToolItem) {
    *///?} else {
    private static List<ItemStack> populateDropsIfPossible(BlockState blockState, Item firstToolItem) {
    //?}
        //? if <1.14 {
        /*return new ArrayList<>();
        *///?} else {
        //If we have local world context, we can actually evaluate loot tables and determine actual data
        ServerWorld serverWorld = (ServerWorld) DGU.getWorld();
        var lootContext = new LootContext.Builder(serverWorld)
                .put(LootContextParameters.POSITION, BlockPos.ORIGIN)
                .put(LootContextParameters.TOOL, DGU.stackFor(firstToolItem));
        blockState.getDroppedStacks(lootContext);
        return blockState.getDroppedStacks(lootContext);
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
        *///?} else if >=1.14 {
        if (property instanceof IntegerProperty) {
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
        if (!(property instanceof BooleanProperty)) {
            JsonArray propertyValuesArray = new JsonArray();
            for (T propertyValue : propertyValues) {
            //?}
                //? if >=1.8.9 <1.12.2 {
                /*propertyValuesArray.add(new JsonPrimitive(property.name(propertyValue)));
                *///?} else if >=1.12.2 <1.14 {
                /*propertyValuesArray.add(property.name(propertyValue));
                *///?} else if >=1.14 {
                propertyValuesArray.add(property.getValueAsString(propertyValue));
                //?}
            //? if >=1.8.9 {
            }
            propertyObject.add("values", propertyValuesArray);
        }
        return propertyObject;
            //?}
    }

    public static JsonObject generateBlock(Block block) {
        JsonObject blockDesc = new JsonObject();

        //? if <1.8.9 {
        /*String registryKey = Registries.BLOCKS.getId(block);
        *///?} else if >=1.8.9 <1.14 {
        /*List<BlockState> blockStates = block.getStateManager().getBlockStates();
        *///?} else {
        List<BlockState> blockStates = block.getStateFactory().getStates();
        //?}
        //? if >=1.8.9 {
        BlockState defaultState = block.getDefaultState();
        //?}
        //? if >=1.8.9 <1.13 {
        /*Identifier registryKey = Registries.BLOCKS.getIdentifier(block);
        *///?} else if >=1.13 {
        Identifier registryKey = Registry.BLOCK.getId(block);
        String localizationKey = block.getTranslationKey();
        //?}
        List<Item> effectiveTools = getItemsEffectiveForBlock(block);

        //? if <1.13 {
        /*blockDesc.addProperty("id", Registries.BLOCKS.getRawId(block));
        *///?}
        //? if <1.8.9 {
        /*blockDesc.addProperty("name", Objects.requireNonNull(registryKey));
        *///?} else if >=1.13 {
        blockDesc.addProperty("id", Registry.BLOCK.getRawId(block));
        //?}
        //? if >=1.8.9 {
        blockDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        //?}
        //? if <1.11.2 {
        /*if (!block.getTranslatedName().startsWith("tile.")) {
            blockDesc.addProperty("displayName", block.getTranslatedName());
        }
        *///?} else if >=1.11.2 <1.13 {
        /*blockDesc.addProperty("displayName", block.getTranslatedName());
        *///?} else {
        blockDesc.addProperty("displayName", DGU.translateText(localizationKey));
        //?}

        //? if <1.8.9 {
        /*float hardness = block.method_471(null, 0, 0, 0);
        *///?} else if >=1.8.9 <1.9.4 {
        /*float hardness = block.getStrength(null, null);
        *///?} else {
        float hardness = block.getDefaultState().getHardness(null, null);
        //?}

        blockDesc.addProperty("hardness", hardness);
        //? if <1.13 {
        /*blockDesc.addProperty("resistance", ((BlockAccessor) block).getBlastResistance());
        *///?}
        //? if >=1.11.2 <1.13 {
        /*blockDesc.addProperty("stackSize", Item.fromBlock(block).getMaxCount());
        *///?} else if >=1.13 {
        blockDesc.addProperty("resistance", block.getBlastResistance());
        //?}
        //? if >=1.13 <1.14 {
        /*blockDesc.addProperty("stackSize", block.getItem().getMaxCount());
        *///?} else if >=1.14 {
        blockDesc.addProperty("stackSize", block.asItem().getMaxAmount());
        //?}
        blockDesc.addProperty("diggable", hardness != -1.0f && !(block instanceof AirBlock));
        JsonObject effTools = new JsonObject();
        effectiveTools.forEach(item -> effTools.addProperty(
                //? if <1.13 {
                /*String.valueOf(Registries.ITEMS.getRawId(item)), // key
                *///?}
                //? if <1.9.4 {
                /*item.getMiningSpeedMultiplier(DGU.stackFor(item), block) // value
                *///?} else if >=1.13 {
                String.valueOf(Registry.ITEM.getRawId(item)), // key
                //?}
                //? if >=1.9.4 {
                item.getBlockBreakingSpeed(DGU.stackFor(item), defaultState) // value
                //?}
        ));
        blockDesc.add("effectiveTools", effTools);
        //? if <1.13 {
        /*blockDesc.addProperty("transparent", block instanceof TransparentBlock);
        *///?}
        //? if <1.9.4 {
        /*blockDesc.addProperty("emitLight", block.getLightLevel());
        blockDesc.addProperty("filterLight", block.getOpacity());
        *///?} else if >=1.13 {

        blockDesc.addProperty("transparent", !defaultState.isFullOpaque(EmptyBlockView.INSTANCE, BlockPos.ORIGIN));
        //?}
        //? if >=1.9.4 {
        blockDesc.addProperty("emitLight", defaultState.getLuminance());
        //?}
        //? if >=1.9.4 <1.13 {
        /*blockDesc.addProperty("filterLight", block.getDefaultState().getOpacity());
        *///?} else if >=1.13 {
        blockDesc.addProperty("filterLight", block.getLightSubtracted(block.getDefaultState(), EmptyBlockView.INSTANCE, BlockPos.ORIGIN));
        //?}

        //? if <1.8.9 {
        /*blockDesc.addProperty("boundingBox", boundingBox(block));
        *///?} else if >=1.13 {
        blockDesc.addProperty("defaultState", Block.getRawIdFromState(defaultState));
        blockDesc.addProperty("minStateId", Block.getRawIdFromState(blockStates.getFirst()));
        blockDesc.addProperty("maxStateId", Block.getRawIdFromState(blockStates.getLast()));
        //?}
        //? if >=1.8.9 {
        JsonArray stateProperties = new JsonArray();
        //?}
        //? if >=1.8.9 <1.14 {
        /*for (Property<?> property : block.getStateManager().getProperties()) {
        *///?} else if >=1.14 {
        for (Property<?> property : block.getStateFactory().getProperties()) {
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
        *///?} else if >=1.14 {

        List<ItemStack> drops = populateDropsIfPossible(defaultState, effectiveTools.stream().findFirst().orElse(Items.AIR));

        JsonArray dropsArray = new JsonArray();
        drops.forEach(dropped -> dropsArray.add(Item.getRawIdByItem(dropped.getItem())));
        blockDesc.add("drops", dropsArray);

        //?}
        //? if >=1.13 {
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
            *///?} else {

        Registry.BLOCK.forEach(block -> resultBlocksArray.add(generateBlock(block)));
            //?}
        return resultBlocksArray;
    }
}
