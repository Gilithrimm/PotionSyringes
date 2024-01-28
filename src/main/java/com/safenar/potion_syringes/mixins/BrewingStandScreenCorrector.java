package com.safenar.potion_syringes.mixins;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.safenar.potion_syringes.PotionSyringes.EMPTY_SYRINGE;
import static com.safenar.potion_syringes.PotionSyringes.SYRINGE;

@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$PotionSlot")
public class BrewingStandScreenCorrector {

	@Inject(method = "matches", at = @At("HEAD"), cancellable = true)
	private static void makeInputSlotIncludeSyringes(ItemStack stack,
													 CallbackInfoReturnable<Boolean> cir) {
		if (stack.isOf(EMPTY_SYRINGE) || stack.isOf(SYRINGE))
			cir.setReturnValue(true);
	}
}
