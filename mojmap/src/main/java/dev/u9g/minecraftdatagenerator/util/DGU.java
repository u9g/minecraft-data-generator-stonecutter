package dev.u9g.minecraftdatagenerator.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.locale.Language;
import net.minecraft.server.MinecraftServer;
//? if <1.16 {
/*import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
*///?}
import net.minecraft.world.level.Level;
//? if <1.16 {
/*import net.minecraft.world.level.dimension.DimensionType;
*///?}
//? if <1.17 {
/*import org.jetbrains.annotations.NotNull;
*///?}

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

    //? if <1.17 {
    /*@NotNull
    *///?}
    public static Level getWorld() {
        //? if <1.16 {
        /*return getCurrentlyRunningServer().getLevel(DimensionType.OVERWORLD);
    }

    public static ItemStack asStack(Item item) {
        return new ItemStack(item);
        *///?} else {
        return getCurrentlyRunningServer().overworld();
        //?}
    }
}
