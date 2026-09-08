//? if <1.16 {
/*package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(Language.class)
public interface LanguageAccessor {
    @Accessor("storage")
    Map<String, String> translations();
}
*///?}
