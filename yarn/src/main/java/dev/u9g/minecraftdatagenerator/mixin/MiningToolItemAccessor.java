//? if <1.20.5 {
/*package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.Block;
//? if <1.14 {
/^import net.minecraft.item.ToolItem;
^///?} else {
import net.minecraft.item.MiningToolItem;
//?}
//? if >=1.17 <=1.18 {
/^import net.minecraft.tag.Tag;
^///?} else if >1.18 <1.20 {
/^import net.minecraft.tag.TagKey;
^///?} else if >=1.20 {
import net.minecraft.registry.tag.TagKey;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
//? if <1.17 {

/^import java.util.Set;
^///?}

//? if <1.14 {
/^@Mixin(ToolItem.class)
^///?} else {
@Mixin(MiningToolItem.class)
//?}
public interface MiningToolItemAccessor {

    @Accessor
    //? if <1.17 {
    /^Set<Block> getEffectiveBlocks();
    ^///?} else if >=1.17 <=1.18 {
    /^Tag<Block> getEffectiveBlocks();
    ^///?} else {
    TagKey<Block> getEffectiveBlocks();
    //?}
    //? if >=1.15 {

    @Accessor
    float getMiningSpeed();
    //?}
}
*///?}
