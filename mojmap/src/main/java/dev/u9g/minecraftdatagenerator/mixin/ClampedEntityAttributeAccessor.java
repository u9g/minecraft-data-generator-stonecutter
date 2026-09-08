//? if >=1.16 <1.17 {
/*package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RangedAttribute.class)
public interface ClampedEntityAttributeAccessor {
    @Accessor("minValue")
    double getMinValue();

    @Accessor("maxValue")
    double getMaxValue();
}
*///?}
