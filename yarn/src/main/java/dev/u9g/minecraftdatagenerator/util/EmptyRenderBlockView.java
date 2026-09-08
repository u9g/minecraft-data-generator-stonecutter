//? if >=1.15 {
package dev.u9g.minecraftdatagenerator.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
//? if >=1.20 {
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
//?}
import net.minecraft.util.math.BlockPos;
//? if >=1.16 {
import net.minecraft.util.math.Direction;
//?}
//? if >=1.17 <1.20 {
/*import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
*///?}
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.LightType;
//? if <1.17 {
/*import net.minecraft.world.biome.Biomes;
*///?} else {
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
//?}
//? if >=1.20 {
import net.minecraft.world.biome.ColorResolver;
//?}
import net.minecraft.world.chunk.light.LightingProvider;
//? if <1.20 {
/*import net.minecraft.world.level.ColorResolver;
*///?}
import org.jetbrains.annotations.Nullable;

public enum EmptyRenderBlockView implements BlockRenderView {
    INSTANCE;

    @Nullable
    public BlockEntity getBlockEntity(BlockPos pos) {
        return null;
    }

    public BlockState getBlockState(BlockPos pos) {
        return Blocks.AIR.getDefaultState();
    }

    public FluidState getFluidState(BlockPos pos) {
        return Fluids.EMPTY.getDefaultState();
    }

    public int getBottomY() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }


    //? if >=1.16 {
    @Override
    public float getBrightness(Direction direction, boolean shaded) {
        return 0.0f;
    }

    //?}
    @Override
    public LightingProvider getLightingProvider() {
        return null;
    }

    @Override
    public int getColor(BlockPos pos, ColorResolver colorResolver) {
        //? if <1.17 {
        /*return colorResolver.getColor(Biomes.PLAINS, pos.getX(), pos.getY());
        *///?} else if >=1.17 <=1.18 {
        /*DynamicRegistryManager registryManager = DynamicRegistryManager.create();
        *///?} else if >1.18 <1.20 {
        /*DynamicRegistryManager registryManager = DynamicRegistryManager.BUILTIN.get();
        *///?}
        //? if >=1.17 <1.20 {
        /*Registry<Biome> biomeRegistry = registryManager.get(Registry.BIOME_KEY);
        *///?} else if >=1.20 <1.21.3 {
        /*Registry<Biome> biomeRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.BIOME);
        *///?} else if >=1.21.3 {
        Registry<Biome> biomeRegistry = DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.BIOME);
        //?}
        //? if >=1.17 {
        Biome plainsBiome = biomeRegistry.get(BiomeKeys.PLAINS);

        return colorResolver.getColor(plainsBiome, pos.getX(), pos.getY());
        //?}
    }

    @Override
    public int getLightLevel(LightType type, BlockPos pos) {
        //? if <1.21.3 {
        /*return type == LightType.SKY ? getMaxLightLevel() : 0;
        *///?} else {
        return type == LightType.SKY ? 15 : 0;
        //?}
    }

    @Override
    public int getBaseLightLevel(BlockPos pos, int ambientDarkness) {
        return ambientDarkness;
    }
}
//?}
