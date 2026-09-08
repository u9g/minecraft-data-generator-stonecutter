package dev.u9g.minecraftdatagenerator.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.locale.Language;
//? if >=1.17 <1.20 {
/*import net.minecraft.resources.ResourceKey;
*///?}
//? if <1.20 {
/*import net.minecraft.resources.ResourceLocation;
*///?}
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Objects;

public class DGU {
    @SuppressWarnings("deprecation")
    public static MinecraftServer getCurrentlyRunningServer() {
        return (MinecraftServer) FabricLoader.getInstance().getGameInstance();
    }

    public static String translateText(String translationKey) {
        //? if <1.16 {
        /*return Language.getInstance().getElement(translationKey);
        *///?} else {
        return Language.getInstance().getOrDefault(translationKey);
        //?}
    }

    public static Level getWorld() {
        //? if <1.16 {
        /*return getCurrentlyRunningServer().getLevel(DimensionType.OVERWORLD);
        *///?} else {
        return getCurrentlyRunningServer().overworld();
        //?}
    }

    /** The registry with the given vanilla name, static or dynamic; dynamic names omit the worldgen/ prefix. */
    @SuppressWarnings("unchecked")
    public static <T> Registry<T> registry(String name) {
        //? if <1.17 {
        /*return (Registry<T>) Objects.requireNonNull(Registry.REGISTRY.get(new ResourceLocation(name)));
        *///?} else if >=1.17 <1.20 {
        /*Registry<?> builtin = Registry.REGISTRY.get(new ResourceLocation(name));
        if (builtin != null) {
            return (Registry<T>) builtin;
        }
        return getWorld().registryAccess().registryOrThrow(ResourceKey.createRegistryKey(new ResourceLocation("worldgen/" + name)));
        *///?} else {
        return (Registry<T>) getWorld().registryAccess().registries()
        //?}
                //? if >=1.20 <1.21.11 {
                /*.filter(entry -> entry.key().location().getPath().replaceFirst("^worldgen/", "").equals(name))
                *///?} else if >=1.21.11 {
                .filter(entry -> entry.key().identifier().getPath().replaceFirst("^worldgen/", "").equals(name))
                //?}
                //? if >=1.20 {
                .findFirst().orElseThrow().value();
                //?}
    }
}
