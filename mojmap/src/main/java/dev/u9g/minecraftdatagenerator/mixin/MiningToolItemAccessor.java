//? if <1.20.5 {
/*package dev.u9g.minecraftdatagenerator.mixin;

//? if >=1.17 <=1.18 {
/^import net.minecraft.tags.Tag;
^///?} else if >1.18 {
import net.minecraft.tags.TagKey;
//?}
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(DiggerItem.class)
public interface MiningToolItemAccessor {

    @Accessor("blocks")
    //? if <1.17 {
    /^Set<Block> getEffectiveBlocks();
    ^///?} else if >=1.17 <=1.18 {
    /^Tag<Block> getEffectiveBlocks();
    ^///?} else {
    TagKey<Block> getEffectiveBlocks();
    //?}

    @Accessor("speed")
    float getMiningSpeed();
}
*///?}
