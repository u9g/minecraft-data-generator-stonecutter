package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ToolItem;
//? if >=1.14 {
import net.minecraft.item.MiningToolItem;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

//? if <1.14 {
/*@Mixin(ToolItem.class)
*///?} else {
@Mixin(MiningToolItem.class)
//?}
public interface MiningToolItemAccessor {

    @Accessor
    Set<Block> getEffectiveBlocks();
}
