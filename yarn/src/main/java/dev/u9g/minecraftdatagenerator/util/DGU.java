package dev.u9g.minecraftdatagenerator.util;

//? if >=1.9.4 {
import net.fabricmc.loader.api.FabricLoader;
//?}
//? if <1.13 {
/*import net.minecraft.item.Item;
*///?} else if >=1.14 <1.15 {
/*import net.minecraft.item.ItemConvertible;
*///?} else if >=1.15 <1.16 {
/*import net.minecraft.item.Item;
*///?}
//? if <1.16 {
/*import net.minecraft.item.ItemStack;
*///?}
//? if >=1.13 <1.14 {
/*import net.minecraft.item.Itemable;
*///?}
import net.minecraft.server.MinecraftServer;
//? if >=1.13 {
import net.minecraft.util.Language;
//?}
import net.minecraft.world.World;
//? if >=1.13 <1.16 {
/*import net.minecraft.world.dimension.DimensionType;
*///?}
//? if <1.17 {
/*import org.jetbrains.annotations.NotNull;
*///?}

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
        *///?} else if >=1.13 <1.16 {
        /*return Language.getInstance().translate(translationKey);
        *///?} else {
        return Language.getInstance().get(translationKey);
        //?}
    }

    //? if <1.17 {
    /*@NotNull
    *///?}
    public static World getWorld() {
        //? if <1.13 {
        /*return getCurrentlyRunningServer().getWorld();
        *///?} else if >=1.13 <1.14 {
        /*return getCurrentlyRunningServer().method_20312(DimensionType.OVERWORLD);
        *///?} else if >=1.14 <1.16 {
        /*return getCurrentlyRunningServer().getWorld(DimensionType.OVERWORLD);
        *///?}
    //? if <1.16 {
    /*}

    *///?}
    //? if <1.13 {
    /*public static ItemStack stackFor(Item ic) {
    *///?} else if >=1.13 <1.14 {
    /*public static ItemStack stackFor(Itemable ic) {
    *///?} else if >=1.14 <1.15 {
    /*public static ItemStack stackFor(ItemConvertible ic) {
    *///?}
        //? if <1.15 {
        /*return new ItemStack(ic);
        *///?} else if >=1.15 <1.16 {
    /*public static ItemStack asStack(Item item) {
        return new ItemStack(item);
        *///?} else {
        return getCurrentlyRunningServer().getOverworld();
        //?}
    }
}
