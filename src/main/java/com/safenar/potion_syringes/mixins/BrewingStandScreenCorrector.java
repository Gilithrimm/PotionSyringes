package com.safenar.potion_syringes.mixins;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$PotionSlot")
public class BrewingStandScreenCorrector {

    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private static void makeInputSlotIncludeEverything(ItemStack stack,
                                                       CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);//hack
    }
}
