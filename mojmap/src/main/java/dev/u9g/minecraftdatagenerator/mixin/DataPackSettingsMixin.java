//? if >=1.20 {
/*
 * SoulFire
 * Copyright (C) 2024  AlexProgrammerDE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.u9g.minecraftdatagenerator.mixin;

//? if <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
*///?} else {
import net.minecraft.resources.Identifier;
//?}
//? if >=1.21.5 {
import net.minecraft.server.packs.repository.PackRepository;
//?}
import net.minecraft.world.flag.FeatureFlags;
//? if <1.21.5 {
/*import net.minecraft.world.level.DataPackConfig;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

//? if <1.21.5 {
/*@Mixin(DataPackConfig.class)
*///?} else {
@Mixin(PackRepository.class)
//?}
public class DataPackSettingsMixin {
  //? if <1.21.5 {
  /*@Inject(method = "getEnabled", at = @At("HEAD"), cancellable = true)
  *///?} else {
  @Inject(method = "getSelectedIds", at = @At("HEAD"), cancellable = true)
  //?}
  public void getEnabled(CallbackInfoReturnable<List<String>> cir) {
    //? if <1.21.11 {
    /*cir.setReturnValue(FeatureFlags.REGISTRY.toNames(FeatureFlags.REGISTRY.allFlags()).stream().map(ResourceLocation::getPath).toList());
    *///?} else {
    cir.setReturnValue(FeatureFlags.REGISTRY.toNames(FeatureFlags.REGISTRY.allFlags()).stream().map(Identifier::getPath).toList());
    //?}
  }
}
//?}
