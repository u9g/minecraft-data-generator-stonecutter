//? if <1.17 {
/*package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MobEffect.class)
public interface StatusEffectAccessor {
    @Accessor("category")
    MobEffectCategory type();
}
*///?}
