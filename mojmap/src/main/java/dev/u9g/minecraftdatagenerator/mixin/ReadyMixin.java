package dev.u9g.minecraftdatagenerator.mixin;

import dev.u9g.minecraftdatagenerator.MinecraftDataGenerator;
import dev.u9g.minecraftdatagenerator.util.DGU;
//? if <1.21.5 {
/*import net.minecraft.DetectedVersion;
*///?} else {
import net.minecraft.SharedConstants;
//?}
import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public class ReadyMixin {
    //? if <1.21 {
    /*@Inject(method = "initServer()Z", at = @At("HEAD"))
    private void constructor(CallbackInfoReturnable<Boolean> cir) {
        ((DedicatedServer) (Object) this).setPort(0);
    }
    *///?}

    @Inject(method = "initServer()Z", at = @At("TAIL"))
    private void init(CallbackInfoReturnable<Boolean> cir) {
        MinecraftDataGenerator.start(
                //? if <1.16 {
                /*DetectedVersion.tryDetectVersion().getName(),
                *///?} else if >=1.16 <1.21.5 {
                /*DetectedVersion.BUILT_IN.getName(),
                *///?}
                //? if <1.21 {
                /*DGU.getCurrentlyRunningServer().getServerDirectory().toPath()
                *///?} else if >=1.21.5 <1.21.9 {
                /*SharedConstants.VERSION_STRING,
                *///?} else if >=1.21.9 {
                SharedConstants.getCurrentVersion().name(),
                //?}
                //? if >=1.21 {
                DGU.getCurrentlyRunningServer().getServerDirectory()
                //?}
        );
    }
}
