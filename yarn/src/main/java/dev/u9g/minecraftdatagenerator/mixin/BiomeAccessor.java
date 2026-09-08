//? if <1.13 {
/*package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(Biome.class)
public interface BiomeAccessor {
    //? if <1.9.4 {
    /^@Accessor("BIOMESET")
    static Set<Biome> BIOMESET() {
        throw new IllegalStateException();
    }

    ^///?}
    @Accessor("waterColor")
    int waterColor();

    @Accessor("name")
    String name();
}
*///?}
