//? if <1.9.4 {
/*package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(Enchantment.class)
public interface EnchantmentAccessor {
    //? if <1.8.9 {
    /^@Accessor("ALL_ENCHANTMENTS")
    static Enchantment[] ALL_ENCHANTMENTS() {
    ^///?} else {
    @Accessor("ENCHANTMENT_MAP")
    static Map<Identifier, Enchantment> ENCHANTMENT_MAP() {
    //?}
        throw new IllegalStateException();
    }
}
*///?}
