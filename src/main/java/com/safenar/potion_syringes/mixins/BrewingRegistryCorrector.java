package com.safenar.potion_syringes.mixins;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.safenar.potion_syringes.registries.SyringeRecipeRegistry.*;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRegistryCorrector {

    @Inject(method = "hasRecipe", at = @At("HEAD"), cancellable = true)
    private static void includeCustomBrewingRecipesInHasRecipe(ItemStack input, ItemStack ingredient,
                                                               CallbackInfoReturnable<Boolean> cir) {
        if (hasCustomRecipe(input, ingredient))
            cir.setReturnValue(true);
    }

    @Inject(method = "craft", at = @At("TAIL"), cancellable = true)
    private static void makeCustomBrewingRecipesCraftable(ItemStack ingredient, ItemStack input,
                                                          CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(craftCustom(ingredient, input));
    }

    @Inject(method = "isValidIngredient", at = @At("HEAD"), cancellable = true)
    private static void makeCustomIngredientsWork(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (isCustomIngredientValid(stack))
            cir.setReturnValue(true);
    }
}
