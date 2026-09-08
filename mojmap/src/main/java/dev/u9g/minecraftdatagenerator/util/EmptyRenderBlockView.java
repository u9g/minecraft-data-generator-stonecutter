package dev.u9g.minecraftdatagenerator.util;

import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
//? if <26.1 {
/*import net.minecraft.world.level.BlockAndTintGetter;
*///?}
import net.minecraft.world.level.ColorResolver;
//? if >=26.1 {
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.world.level.CardinalLighting;
//?}
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public enum EmptyRenderBlockView implements BlockAndTintGetter {
    INSTANCE;

    @Nullable
    public BlockEntity getBlockEntity(BlockPos pos) {
        return null;
    }

    public BlockState getBlockState(BlockPos pos) {
        return Blocks.AIR.defaultBlockState();
    }

    public FluidState getFluidState(BlockPos pos) {
        return Fluids.EMPTY.defaultFluidState();
    }

    //? if <1.17 || >=1.21.5 {
    public int getBottomY() {
    //?} else if >=1.17 <1.21.3 {
    /*public int getMinBuildHeight() {
    *///?} else {
    /*public int getMinY() {
    *///?}
        return 0;
    }

    //? if <1.17 {
    /*public int getMaxBuildHeight() {
    *///?} else if >=1.21.5 {
    @Override
    //?}
    //? if >=1.17 {
    public int getHeight() {
    //?}
        //? if <1.21.5 {
        /*return 0;
        *///?} else {
        return 384;
        //?}
    }

    //? if >=1.16 {

    @Override
    //?}
    //? if >=1.16 <26.1 {
    /*public float getShade(Direction direction, boolean shaded) {
        return 0.0f;
    *///?} else if >=26.1 {
    public CardinalLighting cardinalLighting() {
        return CardinalLighting.DEFAULT;
    //?}
    //? if >=1.16 {
    }

    //?}
    @Override
    public LevelLightEngine getLightEngine() {
        return null;
    }

    @Override
    //? if <1.21.5 {
    /*public int getBlockTint(BlockPos pos, ColorResolver colorResolver) {
    *///?}
        //? if <1.17 {
        /*return colorResolver.getColor(Biomes.PLAINS, pos.getX(), pos.getY());
        *///?} else if >=1.17 <1.21.3 {
        /*Biome plainsBiome = DGU.<Biome>registry("biome").get(Biomes.PLAINS);
        *///?} else if >=1.21.5 {
    public int getBlockTint(BlockPos pos, net.minecraft.world.level.ColorResolver colorResolver) {
        //?}
        //? if >=1.21.3 {
        Biome plainsBiome = DGU.<Biome>registry("biome").get(Biomes.PLAINS).orElse(null).value();
        //?}
        //? if >=1.17 {

        return colorResolver.getColor(plainsBiome, pos.getX(), pos.getY());
        //?}
    }

    @Override
    public int getBrightness(LightLayer type, BlockPos pos) {
        return type == LightLayer.SKY ? 15 : 0;
    }

    //? if <1.21.5 {
    /*@Override
    public int getRawBrightness(BlockPos pos, int ambientDarkness) {
    *///?} else {
    public int getBaseLightLevel(BlockPos pos, int ambientDarkness) {
    //?}
        return ambientDarkness;
    }
    //? if >=1.21.5 {

    @Override
    public int getMinY() {
        return -64;
    }
    //?}
}
