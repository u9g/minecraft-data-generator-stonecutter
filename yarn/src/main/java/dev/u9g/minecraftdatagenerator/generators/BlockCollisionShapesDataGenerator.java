package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
//? if <1.12.2 {
/*import com.google.gson.JsonPrimitive;
*///?}
//? if >=1.8.9 <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.DGU;
*///?}
//? if <1.13 {
/*import dev.u9g.minecraftdatagenerator.util.Registries;
*///?} else {
import com.google.gson.JsonPrimitive;
//?}
//? if >=1.13 <1.14 {
/*import dev.u9g.minecraftdatagenerator.util.EmptyBlockView;
*///?} else if >=1.20 {
import dev.u9g.minecraftdatagenerator.util.DGU;
//?}
import net.minecraft.block.Block;
//? if <1.8.9 {
/*import net.minecraft.client.Texture;
*///?} else {
import net.minecraft.block.BlockState;
//?}
//? if >=1.20 {
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
//?}
//? if >=1.13 {
import net.minecraft.util.Identifier;
//?}
//? if >=1.8.9 {
import net.minecraft.util.math.BlockPos;
//?}
//? if <1.13 {
/*import net.minecraft.util.math.Box;
*///?} else if >=1.13 <1.20 {
/*import net.minecraft.util.registry.Registry;
*///?}
//? if >=1.13 <1.14 {
/*import net.minecraft.util.shapes.VoxelShape;
*///?} else if >=1.14 {
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.EmptyBlockView;
//?}

//? if <1.8.9 {
/*import java.lang.reflect.Field;
*///?}
//? if <1.13 {
/*import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
*///?} else {
import java.util.*;
//?}

public class BlockCollisionShapesDataGenerator implements IDataGenerator {
    //? if <1.8.9 {
    /*private static final Box ENTITY_BOX = Box.of(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D);
    *///?} else if >=1.8.9 <1.13 {
    /*private static final Box ENTITY_BOX = new Box(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D);
    *///?}
    //? if <1.13 {

    /*private static String nameOf(Block block) {
    *///?}
        //? if <1.8.9 {
        /*return Objects.requireNonNull(Registries.BLOCKS.getId(block));
        *///?} else if >=1.8.9 <1.13 {
        /*return Objects.requireNonNull(Registries.BLOCKS.getIdentifier(block)).getPath();
        *///?}
    //? if <1.13 {
    /*}

    private static JsonArray jsonOf(Box box) {
        JsonArray arr = new JsonArray();
        if (box == null) return arr;
    *///?}
        //? if <1.12.2 {
        /*arr.add(new JsonPrimitive(box.minX));
        arr.add(new JsonPrimitive(box.minY));
        arr.add(new JsonPrimitive(box.minZ));
        arr.add(new JsonPrimitive(box.maxX));
        arr.add(new JsonPrimitive(box.maxY));
        arr.add(new JsonPrimitive(box.maxZ));
        *///?} else if >=1.12.2 <1.13 {
        /*arr.add(box.minX);
        arr.add(box.minY);
        arr.add(box.minZ);
        arr.add(box.maxX);
        arr.add(box.maxY);
        arr.add(box.maxZ);
        *///?}
        //? if <1.13 {
        /*return arr;
    }
        *///?}

    @Override
    public String getDataName() {
        return "blockCollisionShapes";
    }

    @Override
    public JsonObject generateDataJson() {
        //? if <1.13 {
        /*ShapeCache shapeCache = new ShapeCache();
        JsonObject blocksObject = new JsonObject();
        *///?}

        //? if <1.13 {
        /*for (Block block : Registries.BLOCKS) {
            Object val = shapeCache.addShapesFrom(block);
            if (val instanceof JsonArray) {
                blocksObject.add(nameOf(block), (JsonElement) val);
            } else {
                blocksObject.addProperty(nameOf(block), (Integer) val);
            }
        *///?} else if >=1.13 <1.20 {
        /*Registry<Block> blockRegistry = Registry.BLOCK;
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Block> blockRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.BLOCK);
        *///?} else {
        Registry<Block> blockRegistry = DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.BLOCK);
        //?}
        //? if >=1.13 {
        BlockShapesCache blockShapesCache = new BlockShapesCache();
        //?}
        //? if >=1.13 <1.14 {
        /*for (Block block : (Iterable<Block>) blockRegistry) {
            blockShapesCache.processBlock(block);
        *///?}
        //? if <1.14 {
        /*}
        *///?} else {

        blockRegistry.forEach(blockShapesCache::processBlock);
        //?}

        JsonObject resultObject = new JsonObject();
        //? if <1.13 {
        /*resultObject.add("blocks", blocksObject);
        resultObject.add("shapes", shapeCache.toJSON());
        *///?} else {

        resultObject.add("blocks", blockShapesCache.dumpBlockShapeIndices(blockRegistry));
        resultObject.add("shapes", blockShapesCache.dumpShapesObject());

        //?}
        return resultObject;
    }

    //? if <1.13 {
    /*public static class ShapeCache {
        private final ArrayList<Shapes> shapesCache = new ArrayList<>();
    *///?} else {
    private static class BlockShapesCache {
        public final Map<VoxelShape, Integer> uniqueBlockShapes = new LinkedHashMap<>();
        public final Map<Block, List<Integer>> blockCollisionShapes = new LinkedHashMap<>();
        private int lastCollisionShapeId = 0;
    //?}

        //? if <1.13 {
        /*public Object addShapesFrom(Block block) {
            List<Integer> indexesOfBoxesInTheShapesCache = new ArrayList<>();
        *///?}
            //? if <1.8.9 {
            /*for (Field field : block.getClass().getDeclaredFields()) {
                if (!field.getType().getName().equals("[Lnet.minecraft.client.ItemIcon;")) { // ItemIcon[]
                    continue;
            *///?} else if >=1.8.9 <1.13 {
            /*for (BlockState state : block.getStateManager().getBlockStates().reverse()) {
                List<Box> boxes = new ArrayList<>();
                try {
            *///?}
                    //? if >=1.8.9 <1.9.4 {
                    /*// Fix needed for StairsBlock because it requires the state of the block to be set
                    DGU.getWorld().setBlockState(BlockPos.ORIGIN, state);
                    block.appendCollisionBoxes(DGU.getWorld(), BlockPos.ORIGIN, state, ENTITY_BOX, boxes, null);
                    *///?} else if >=1.9.4 <1.11.2 {
                    /*state.addCollisionBoxesToList(DGU.getWorld(), BlockPos.ORIGIN, ENTITY_BOX, boxes, null);
                    *///?} else if >=1.11.2 <1.13 {
                    /*state.appendCollisionBoxes(DGU.getWorld(), BlockPos.ORIGIN, ENTITY_BOX, boxes, null, true);
                    *///?}
                //? if >=1.8.9 <1.13 {
                /*} catch (Exception e) {
                    e.printStackTrace();
                *///?} else if >=1.13 {
        public void processBlock(Block block) {
                //?}
            //? if >=1.13 <1.14 {
            /*List<BlockState> blockStates = block.getStateManager().getBlockStates();
            *///?} else if >=1.14 <1.15 {
            /*List<BlockState> blockStates = block.getStateFactory().getStates();
            *///?} else if >=1.15 {
            List<BlockState> blockStates = block.getStateManager().getStates();
            //?}
            //? if >=1.13 {
            List<Integer> blockCollisionShapes = new ArrayList<>();

            for (BlockState blockState : blockStates) {
                VoxelShape blockShape = blockState.getCollisionShape(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);
                Integer blockShapeIndex = uniqueBlockShapes.get(blockShape);

                if (blockShapeIndex == null) {
                    blockShapeIndex = lastCollisionShapeId++;
                    uniqueBlockShapes.put(blockShape, blockShapeIndex);
            //?}
                }
                //? if <1.8.9 {
                /*try {
                    field.setAccessible(true);
                    Texture[] icons = (Texture[]) field.get(block);
                    return icons.length;
                } catch (Exception err) {
                    err.printStackTrace();
                *///?} else if >=1.8.9 <1.13 {
                /*Shapes thisBlockStateShapes = new Shapes(boxes);
                int indexOfThisBlockStatesShapes = shapesCache.indexOf(thisBlockStateShapes);
                if (indexOfThisBlockStatesShapes != -1) {
                    indexesOfBoxesInTheShapesCache.add(indexOfThisBlockStatesShapes);
                } else {
                    shapesCache.add(thisBlockStateShapes);
                    indexesOfBoxesInTheShapesCache.add(shapesCache.size() - 1);
                *///?}
                //? if <1.13 {
                /*}
                *///?} else {
                blockCollisionShapes.add(blockShapeIndex);
                //?}
            }
            //? if <1.8.9 {
            /*// old way
//            new Throwable("find block damages based on ItemIcon[]").printStackTrace();
//            for (BlockState state : block.getStateManager().getBlockStates().reverse()) {
//                List<Box> boxes = new ArrayList<>();
//                try {
//                    block.appendCollisionBoxes(DGU.getWorld(), BlockPos.ORIGIN, state, ENTITY_BOX, boxes, null);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Shapes thisBlockStateShapes = new Shapes(boxes);
//                int indexOfThisBlockStatesShapes = shapesCache.indexOf(thisBlockStateShapes);
//                if (indexOfThisBlockStatesShapes != -1) {
//                    indexesOfBoxesInTheShapesCache.add(indexOfThisBlockStatesShapes);
//                } else {
//                    shapesCache.add(thisBlockStateShapes);
//                    indexesOfBoxesInTheShapesCache.add(shapesCache.size() - 1);
//                }
//            }
//            if (indexesOfBoxesInTheShapesCache.stream().distinct().count() < 2) {
//                return indexesOfBoxesInTheShapesCache.getFirst();
//            } else {
//                JsonArray shapeIndexes = new JsonArray();
//                indexesOfBoxesInTheShapesCache.forEach(shapeIndex -> shapeIndexes.add(new JsonPrimitive(shapeIndex)));
//                return shapeIndexes;
//            }
            return 1;
            *///?} else if >=1.8.9 <1.13 {
            /*if (indexesOfBoxesInTheShapesCache.stream().distinct().count() < 2) {
                return indexesOfBoxesInTheShapesCache.getFirst();
            } else {
                JsonArray shapeIndexes = new JsonArray();
            *///?}
                //? if >=1.8.9 <1.12.2 {
                /*indexesOfBoxesInTheShapesCache.forEach(shapeIndex -> shapeIndexes.add(new JsonPrimitive(shapeIndex)));
                *///?} else if >=1.12.2 <1.13 {
                /*indexesOfBoxesInTheShapesCache.forEach(shapeIndexes::add);
                *///?}
                //? if >=1.8.9 <1.13 {
                /*return shapeIndexes;
            }
                *///?} else if >=1.13 {

            this.blockCollisionShapes.put(block, blockCollisionShapes);
                //?}
        }

        //? if <1.13 {
        /*public JsonObject toJSON() {
            JsonObject shapes = new JsonObject();
            int i = 0;
            for (Shapes s : shapesCache) {
                shapes.add(String.valueOf(i++), s.toJSON());
        *///?} else {
        public JsonObject dumpBlockShapeIndices(Registry<Block> blockRegistry) {
            JsonObject resultObject = new JsonObject();

        //?}
            //? if >=1.13 <1.14 {
            /*for (Map.Entry<Block, List<Integer>> entry : blockCollisionShapes.entrySet()) {
            *///?} else if >=1.14 {
            for (var entry : blockCollisionShapes.entrySet()) {
            //?}
                //? if >=1.13 {
                List<Integer> blockCollisions = entry.getValue();
                long distinctShapesCount = blockCollisions.stream().distinct().count();
                JsonElement blockCollision;
                if (distinctShapesCount == 1L) {
                    blockCollision = new JsonPrimitive(blockCollisions.getFirst());
                } else {
                    blockCollision = new JsonArray();
                    for (int collisionId : blockCollisions) {
                        ((JsonArray) blockCollision).add(collisionId);
                    }
                }

                //?}
                //? if >=1.13 <1.16 {
                /*Identifier registryKey = blockRegistry.getId(entry.getKey());
                resultObject.add(Objects.requireNonNull(registryKey).getPath(), blockCollision);
                *///?} else if >=1.16 {
                Identifier registryKey = blockRegistry.getKey(entry.getKey()).orElseThrow().getValue();
                resultObject.add(registryKey.getPath(), blockCollision);
                //?}
            }
            //? if <1.13 {
            /*return shapes;
            *///?} else {

            return resultObject;
            //?}
        }

        //? if <1.13 {
        /*private record Shapes(List<Box> boxes) {
        *///?} else {
        public JsonObject dumpShapesObject() {
            JsonObject shapesObject = new JsonObject();
        //?}

            //? if <1.13 {
            /*public JsonArray toJSON() {
                JsonArray arr = new JsonArray();
                boxes.forEach(box -> arr.add(jsonOf(box)));
                return arr;
            *///?} else if >=1.13 <1.14 {
            /*for (Map.Entry<VoxelShape, Integer> entry : uniqueBlockShapes.entrySet()) {
            *///?} else {
            for (var entry : uniqueBlockShapes.entrySet()) {
            //?}
                //? if >=1.13 {
                JsonArray boxesArray = new JsonArray();
                entry.getKey().forEachBox((x1, y1, z1, x2, y2, z2) -> {
                    JsonArray oneBoxJsonArray = new JsonArray();

                    oneBoxJsonArray.add(x1);
                    oneBoxJsonArray.add(y1);
                    oneBoxJsonArray.add(z1);

                    oneBoxJsonArray.add(x2);
                    oneBoxJsonArray.add(y2);
                    oneBoxJsonArray.add(z2);

                    boxesArray.add(oneBoxJsonArray);
                });
                shapesObject.add(Integer.toString(entry.getValue()), boxesArray);
                //?}
            }
            //? if <1.13 {

            /*@Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Shapes shapes = (Shapes) o;
                return Objects.equals(boxes, shapes.boxes);
            }

            *///?} else {
            return shapesObject;
            //?}
        }
    }
}
