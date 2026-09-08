//? if <1.13 {
/*package dev.u9g.minecraftdatagenerator.util;

import dev.u9g.minecraftdatagenerator.mixin.BiomeAccessor;
//? if <1.9.4 {
/^import dev.u9g.minecraftdatagenerator.mixin.EnchantmentAccessor;
^///?}
//? if <1.11.2 {
/^import dev.u9g.minecraftdatagenerator.mixin.EntityTypeAccessor;
^///?}
import dev.u9g.minecraftdatagenerator.mixin.StatusEffectAccessor;
//? if <1.8.9 {
/^import dev.u9g.minecraftdatagenerator.registryview.RegistryBackedRegistryView;
import dev.u9g.minecraftdatagenerator.registryview.RegistryView;
import dev.u9g.minecraftdatagenerator.registryview.TableBackedRegistryView;
^///?}
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
//? if <1.8.9 {
/^import org.apache.commons.lang3.StringUtils;
^///?}

import java.util.Locale;
import java.util.Map;

public class Registries {
    //? if <1.8.9 {
    /^public static final Language LANGUAGE;
    public static final RegistryView<String, Biome> BIOMES;
    public static final RegistryView<String, Block> BLOCKS;
    public static final RegistryView<String, Item> ITEMS;
    public static final RegistryView<String, StatusEffect> STATUS_EFFECTS;
    public static final RegistryView<String, Enchantment> ENCHANTMENTS;
    public static final RegistryView<String, Class<? extends Entity>> ENTITY_TYPES;
    ^///?} else if =1.8.9 {
    /^public static final SimpleRegistry<String, Biome> BIOMES = setupBiomeRegistry();
    ^///?} else {
    public static final SimpleRegistry<Identifier, Biome> BIOMES = Biome.REGISTRY;
    //?}
    //? if >=1.8.9 {
    public static final SimpleRegistry<Identifier, Block> BLOCKS = Block.REGISTRY;
    public static final SimpleRegistry<Identifier, Item> ITEMS = Item.REGISTRY;
    //?}
    //? if =1.8.9 {
    /^public static final SimpleRegistry<Identifier, StatusEffect> STATUS_EFFECTS = setupStatusEffectRegistry();
    public static final SimpleRegistry<Identifier, Enchantment> ENCHANTMENTS = setupEnchantmentRegistry();
    public static final SimpleRegistry<Identifier, Class<? extends Entity>> ENTITY_TYPES = setupEntityTypesRegistry();
    ^///?} else if >=1.9.4 {
    public static final SimpleRegistry<Identifier, StatusEffect> STATUS_EFFECTS = StatusEffect.REGISTRY;
    public static final SimpleRegistry<Identifier, Enchantment> ENCHANTMENTS = Enchantment.REGISTRY;
    //?}
    //? if >=1.9.4 <1.11.2 {
    /^public static final SimpleRegistry<Identifier, Class<? extends Entity>> ENTITY_TYPES = new SimpleRegistry<>();
    ^///?} else if >=1.11.2 {
    public static final SimpleRegistry<Identifier, Class<? extends Entity>> ENTITY_TYPES = EntityType.REGISTRY;
    //?}
    //? if >=1.8.9 {
    public static final Language LANGUAGE = new Language();
    //?}

    //? if <1.8.9 {
    /^static {
        LANGUAGE = new Language();
        BIOMES = setupBiomeRegistry();
        BLOCKS = new RegistryBackedRegistryView<>(Block.REGISTRY);
        ITEMS = new RegistryBackedRegistryView<>(Item.REGISTRY);
        STATUS_EFFECTS = setupStatusEffectRegistry();
        ENCHANTMENTS = setupEnchantmentRegistry();
        ENTITY_TYPES = setupEntityTypesRegistry();
    }

    private static RegistryView<String, Class<? extends Entity>> setupEntityTypesRegistry() {
        TableBackedRegistryView.Builder<String, Class<? extends Entity>> registry = new TableBackedRegistryView.Builder<>();
    ^///?} else if =1.8.9 {
    /^private static SimpleRegistry<Identifier, Class<? extends Entity>> setupEntityTypesRegistry() {
        SimpleRegistry<Identifier, Class<? extends Entity>> registry = new SimpleRegistry<>();
    ^///?} else if >=1.9.4 <1.11.2 {
    /^static {
    ^///?}
        //? if <1.11.2 {
        /^for (Map.Entry<Integer, Class<? extends Entity>> entry : EntityTypeAccessor.ID_CLASS_MAP().entrySet()) {
            String name = EntityTypeAccessor.CLASS_NAME_MAP().get(entry.getValue());
            if (name.equals("Mob") || name.equals("Monster")) {
                continue;
            }
        ^///?}
            //? if <1.8.9 {
            /^registry.add(name, entry.getKey(), entry.getValue());
            ^///?} else if =1.8.9 {
            /^registry.add(entry.getKey(), new Identifier(name), entry.getValue());
            ^///?} else if >=1.9.4 <1.11.2 {
            /^ENTITY_TYPES.add(entry.getKey(), new Identifier(name), entry.getValue());
            ^///?}
        //? if <1.11.2 {
        /^}
        ^///?}

        //? if <1.8.9 {
        /^return registry.build();
        ^///?} else if =1.8.9 {
        /^return registry;
        ^///?}
    //? if <1.9.4 {
    /^}

    ^///?}
    //? if <1.8.9 {
    /^private static RegistryView<String, Enchantment> setupEnchantmentRegistry() {
        TableBackedRegistryView.Builder<String, Enchantment> registry = new TableBackedRegistryView.Builder<>();
        for (Enchantment enchantment : EnchantmentAccessor.ALL_ENCHANTMENTS()) {
            if (enchantment == null) continue;
            String translatedName = Registries.LANGUAGE.translate(enchantment.getTranslationKey());
            registry.add(String.join("", translatedName.toLowerCase(Locale.ENGLISH).split(" ")), enchantment.id, enchantment);
    ^///?} else if =1.8.9 {
    /^private static SimpleRegistry<Identifier, Enchantment> setupEnchantmentRegistry() {
        SimpleRegistry<Identifier, Enchantment> registry = new SimpleRegistry<>();
        for (Map.Entry<Identifier, Enchantment> entry : EnchantmentAccessor.ENCHANTMENT_MAP().entrySet()) {
            registry.add(entry.getValue().id, entry.getKey(), entry.getValue());
    ^///?}
        //? if <1.9.4 {
        /^}
        ^///?}
        //? if <1.8.9 {
        /^return registry.build();
        ^///?} else if =1.8.9 {
        /^return registry;
        ^///?}
    //? if <1.9.4 {
    /^}

    ^///?}
    //? if <1.8.9 {
    /^private static RegistryView<String, Biome> setupBiomeRegistry() {
        TableBackedRegistryView.Builder<String, Biome> builder = new TableBackedRegistryView.Builder<>();
    ^///?} else if =1.8.9 {
    /^private static SimpleRegistry<String, Biome> setupBiomeRegistry() {
        SimpleRegistry<String, Biome> registry = new SimpleRegistry<>();
    ^///?}
        //? if <1.9.4 {
        /^for (Biome biome : BiomeAccessor.BIOMESET()) {
        ^///?}
            //? if <1.8.9 {
            /^builder.add(biome.name, biome.id, biome);
            ^///?} else if =1.8.9 {
            /^registry.add(biome.id, biome.name, biome);
            ^///?}
        //? if <1.9.4 {
        /^}
        ^///?}
        //? if <1.8.9 {
        /^return builder.build();
        ^///?} else if =1.8.9 {
        /^return registry;
        ^///?}
    //? if <1.9.4 {
    /^}

    ^///?}
    //? if <1.8.9 {
    /^private static RegistryView<String, StatusEffect> setupStatusEffectRegistry() {
        TableBackedRegistryView.Builder<String, StatusEffect> builder = new TableBackedRegistryView.Builder<>();
        for (StatusEffect effect : StatusEffectAccessor.STATUS_EFFECTS()) {
            if (effect == null) continue;
            String[] words = Registries.LANGUAGE.translate(effect.getTranslationKey()).split(" ");
            builder.add(StringUtils.join(words, ""), effect.id, effect);
    ^///?} else if =1.8.9 {
    /^private static SimpleRegistry<Identifier, StatusEffect> setupStatusEffectRegistry() {
        SimpleRegistry<Identifier, StatusEffect> registry = new SimpleRegistry<>();
        for (Map.Entry<Identifier, StatusEffect> entry : StatusEffectAccessor.STATUS_EFFECTS_BY_ID().entrySet()) {
            registry.add(entry.getValue().id, entry.getKey(), entry.getValue());
    ^///?}
        //? if <1.9.4 {
        /^}
        ^///?}
        //? if <1.8.9 {
        /^return builder.build();
        ^///?} else if =1.8.9 {
        /^return registry;
        ^///?}
    //? if <1.11.2 {
    /^}
    ^///?}
}
*///?}
