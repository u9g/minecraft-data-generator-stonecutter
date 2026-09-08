package dev.u9g.minecraftdatagenerator.util;

//? if >=1.21.5 {
import dev.u9g.minecraftdatagenerator.util.DGU;
//?}
import net.minecraft.core.BlockPos;
//? if >=1.16 {
import net.minecraft.core.Direction;
//?}
//? if >=1.17 {
import net.minecraft.core.Registry;
//?}
//? if >=1.17 <1.20 {
/*import net.minecraft.core.RegistryAccess;
*///?} else if >=1.20 {
import net.minecraft.core.registries.Registries;
//?}
//? if <26.1 {
/*import net.minecraft.world.level.BlockAndTintGetter;
*///?}
//? if <1.21.5 {
/*import net.minecraft.world.level.ColorResolver;
*///?} else if >=26.1 {
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.world.level.CardinalLighting;
//?}
import net.minecraft.world.level.LightLayer;
//? if >=1.17 {
import net.minecraft.world.level.biome.Biome;
//?}
//? if >=1.21.5 {
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
//?}
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

    //? if <1.17 {
    /*public int getBottomY() {
    *///?} else if >=1.17 <1.21.3 {
    /*public int getMinBuildHeight() {
    *///?} else if >=1.21.3 <1.21.5 {
    /*public int getMinY() {
    *///?} else {
    public int getBottomY() {
    //?}
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
        *///?} else if >=1.17 <=1.18 {
        /*RegistryAccess registryManager = RegistryAccess.builtin();
        *///?} else if >1.18 <1.20 {
        /*RegistryAccess registryManager = RegistryAccess.BUILTIN.get();
        *///?}
        //? if >=1.17 <1.20 {
        /*Registry<Biome> biomeRegistry = registryManager.registryOrThrow(Registry.BIOME_REGISTRY);
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Biome> biomeRegistry = DGU.getWorld().registryAccess().registryOrThrow(Registries.BIOME);
        *///?}
        //? if >=1.17 <1.21.3 {
        /*Biome plainsBiome = biomeRegistry.get(Biomes.PLAINS);
        *///?} else if >=1.21.5 {
    public int getBlockTint(BlockPos pos, net.minecraft.world.level.ColorResolver colorResolver) {
        //?}
        //? if >=1.21.3 {
        Registry<Biome> biomeRegistry = DGU.getWorld().registryAccess().lookupOrThrow(Registries.BIOME);
        //?}
        //? if >=1.21.3 <1.21.5 {
        /*Biome plainsBiome = biomeRegistry.getValue(Biomes.PLAINS);
        *///?} else if >=1.21.5 {
        Biome plainsBiome = biomeRegistry.get(Biomes.PLAINS).orElse(null).value();
        //?}
        //? if >=1.17 {

        return colorResolver.getColor(plainsBiome, pos.getX(), pos.getY());
        //?}
    }

    @Override
    public int getBrightness(LightLayer type, BlockPos pos) {
        //? if <1.21.3 {
        /*return type == LightLayer.SKY ? getMaxLightLevel() : 0;
        *///?} else {
        return type == LightLayer.SKY ? 15 : 0;
        //?}
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
