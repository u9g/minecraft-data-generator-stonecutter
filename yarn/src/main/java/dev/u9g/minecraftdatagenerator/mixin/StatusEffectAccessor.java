package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.entity.effect.StatusEffect;
//? if >=1.8.9 <1.9.4 {
/*import net.minecraft.util.Identifier;
*///?} else if >=1.14 {
import net.minecraft.entity.effect.StatusEffectType;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//? if >=1.8.9 <1.9.4 {
/*import java.util.Map;

*///?}
@Mixin(StatusEffect.class)
public interface StatusEffectAccessor {
    //? if <1.8.9 {
    /*@Accessor("STATUS_EFFECTS")
    static StatusEffect[] STATUS_EFFECTS() {
    *///?} else if >=1.8.9 <1.9.4 {
    /*@Accessor("STATUS_EFFECTS_BY_ID")
    static Map<Identifier, StatusEffect> STATUS_EFFECTS_BY_ID() {
    *///?}
        //? if <1.9.4 {
        /*throw new IllegalStateException();
    }

        *///?}
    //? if <1.14 {
    /*@Accessor("negative")
    boolean negative();
    *///?} else {
    @Accessor("type")
    StatusEffectType type();
    //?}
}
