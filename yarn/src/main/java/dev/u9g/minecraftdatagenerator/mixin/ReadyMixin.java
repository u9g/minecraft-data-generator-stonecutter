package dev.u9g.minecraftdatagenerator.mixin;

import dev.u9g.minecraftdatagenerator.MinecraftDataGenerator;
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if >=1.14 {
import net.minecraft.MinecraftVersion;
//?}
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(MinecraftDedicatedServer.class)
public class ReadyMixin {
    @Inject(method = "setupServer()Z", at = @At("HEAD"))
    private void constructor(CallbackInfoReturnable<Boolean> cir) {
        ((MinecraftDedicatedServer) (Object) this).setServerPort(0);
    }

    @Inject(method = "setupServer()Z", at = @At("TAIL"))
    private void init(CallbackInfoReturnable<Boolean> cir) {
        //? if <1.8.9 {
        /*System.setProperty("fabric.development", "false");
        *///?}
        MinecraftDataGenerator.start(
                //? if <1.14 {
                /*DGU.getCurrentlyRunningServer().getVersion(),
                *///?}
                //? if <1.8.9 {
                /*(new File(".")).toPath()
                *///?} else if >=1.14 {
                MinecraftVersion.create().getName(),
                //?}
                //? if >=1.8.9 {
                DGU.getCurrentlyRunningServer().getRunDirectory().toPath()
                //?}
        );
    }
}
