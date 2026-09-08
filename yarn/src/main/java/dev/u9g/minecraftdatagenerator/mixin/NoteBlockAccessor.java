//? if >=1.8.9 <1.13 {
/*package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.NoteBlock;
//? if >=1.9.4 {
import net.minecraft.sound.Sound;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(NoteBlock.class)
public interface NoteBlockAccessor {
    @Accessor("TUNES")
    //? if <1.9.4 {
    /^static List<String> TUNES() {
    ^///?} else {
    static List<Sound> TUNES() {
    //?}
        return null;
    }
}
*///?}
