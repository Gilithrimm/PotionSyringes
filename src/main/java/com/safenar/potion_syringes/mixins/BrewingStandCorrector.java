package com.safenar.potion_syringes.mixins;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.safenar.potion_syringes.PotionSyringes.EMPTY_SYRINGE;
import static com.safenar.potion_syringes.PotionSyringes.SYRINGE;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandCorrector {
	@Inject(method = "isValid", at = @At("TAIL"), cancellable = true)
	private void makeInputSlotsIncludeSyringes(int slot, ItemStack stack,
											   CallbackInfoReturnable<Boolean> cir) {
		if (stack.isOf(EMPTY_SYRINGE) || stack.isOf(SYRINGE))
			cir.setReturnValue(true);
	}
}
