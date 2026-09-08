package dev.u9g.minecraftdatagenerator.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
//? if >=1.14 {
import net.minecraft.item.ItemConvertible;
//?}
import net.minecraft.item.ItemStack;
//? if =1.13 {
/*import net.minecraft.item.Itemable;
*///?}
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Language;
import net.minecraft.world.World;
//? if >=1.13 {
import net.minecraft.world.dimension.DimensionType;
//?}
import org.jetbrains.annotations.NotNull;

public class DGU {
    //? if >=1.9.4 {
    @SuppressWarnings("deprecation")
    //?}
    public static MinecraftServer getCurrentlyRunningServer() {
        //? if <1.9.4 {
        /*return MinecraftServer.getServer();
        *///?} else {
        return (MinecraftServer) FabricLoader.getInstance().getGameInstance();
        //?}
    }

    public static String translateText(String translationKey) {
        //? if <1.13 {
        /*return Registries.LANGUAGE.translate(translationKey);
        *///?} else {
        return Language.getInstance().translate(translationKey);
        //?}
    }

    @NotNull
    public static World getWorld() {
        //? if <1.13 {
        /*return getCurrentlyRunningServer().getWorld();
        *///?} else if =1.13 {
        /*return getCurrentlyRunningServer().method_20312(DimensionType.OVERWORLD);
        *///?} else {
        return getCurrentlyRunningServer().getWorld(DimensionType.OVERWORLD);
        //?}
    }

    //? if <1.13 {
    /*public static ItemStack stackFor(Item ic) {
    *///?} else if =1.13 {
    /*public static ItemStack stackFor(Itemable ic) {
    *///?} else {
    public static ItemStack stackFor(ItemConvertible ic) {
    //?}
        return new ItemStack(ic);
    }
}
