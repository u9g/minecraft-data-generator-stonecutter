//? if >=1.8.9 <1.14 {
/*package dev.u9g.minecraftdatagenerator.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
//? if >=1.13 {
import net.minecraft.fluid.FluidState;
//?}
import net.minecraft.util.math.BlockPos;
//? if <1.13 {
/^import net.minecraft.util.math.Direction;
^///?}
import net.minecraft.world.BlockView;
//? if <1.13 {
/^import net.minecraft.world.biome.Biome;
import net.minecraft.world.level.LevelGeneratorType;
^///?}
import org.jetbrains.annotations.Nullable;

//? if <1.12.2 {
/^public class EmptyBlockView implements BlockView {
    public static final EmptyBlockView INSTANCE = new EmptyBlockView();
^///?} else {
public enum EmptyBlockView implements BlockView {
    INSTANCE;
//?}

    @Nullable
    public BlockEntity getBlockEntity(BlockPos pos) {
        return null;
    }

    //? if <1.13 {
    /^@Override
    public int getLight(BlockPos pos, int minBlockLight) {
        return 0;
    }

    ^///?}
    public BlockState getBlockState(BlockPos pos) {
        return Blocks.AIR.getDefaultState();
    }

    @Override
    //? if <1.13 {
    /^public boolean isAir(BlockPos pos) {
        return false;
    }

    @Override
    public Biome getBiome(BlockPos pos) {
    ^///?} else {
    public FluidState getFluidState(BlockPos blockPos) {
    //?}
        return null;
    }

    @Override
    //? if <1.10.2 {
    /^public boolean isEmpty() {
        return false;
    }

    @Override
    ^///?}
    //? if <1.13 {
    /^public int getStrongRedstonePower(BlockPos pos, Direction direction) {
        return 0;
    }

    @Override
    public LevelGeneratorType getGeneratorType() {
        return null;
    ^///?} else {
    public int getMaxLightLevel() {
        return 15;
    //?}
    }
}
*///?}
