package com.safenar.potion_syringes.registries;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import org.apache.commons.compress.utils.Lists;

import java.util.Iterator;
import java.util.List;

//because BrewingRecipeRegistry can-fucking-not support taking potion as ingredient and normal items
// (like glass bottles) as output
public class SyringeRecipeRegistry extends BrewingRecipeRegistry {

    public static final List<Recipe<Item>> ITEM_RECIPES = Lists.newArrayList();

    public static void registerPotionToItemRecipe(Item potion, Item from, Item to) {
        if (!(potion instanceof PotionItem)) {
            throw new IllegalArgumentException("Expected a potion, got: " + Registries.ITEM.getId(from));
        } else if (!(to instanceof PotionItem)) {
            throw new IllegalArgumentException("Expected a potion, got: " + Registries.ITEM.getId(to));
        } else {
            ITEM_RECIPES.add(new Recipe<>(from, Ingredient.ofItems(potion), to));
        }
    }

    public static boolean hasCustomRecipe(ItemStack input, ItemStack ingredient) {
        Item item = input.getItem();
        Iterator<Recipe<Item>> var3 = ITEM_RECIPES.iterator();

        Recipe<Item> recipe;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            recipe = var3.next();
        } while (recipe.input != item || !recipe.ingredient.test(ingredient));

        return true;
    }

    //ingredient=top slot
    //input=bottom slot
    public static ItemStack craftCustom(ItemStack ingredient, ItemStack input) {
        if (!input.isEmpty()) {
            Potion potion = PotionUtil.getPotion(ingredient);
            Item item = input.getItem();
            Iterator<Recipe<Item>> var4 = ITEM_RECIPES.iterator();

            Recipe<Item> recipe;
            while (var4.hasNext()) {
                recipe = var4.next();
                if (recipe.input == item && recipe.ingredient.test(ingredient)) {
                    return PotionUtil.setPotion(new ItemStack(recipe.output), potion);
                }
            }
        }
        return input;
    }

    public static boolean isCustomIngredientValid(ItemStack stack) {
        Iterator<Recipe<Item>> var1 = ITEM_RECIPES.iterator();

        Recipe<Item> recipe;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            recipe = var1.next();
        } while (!recipe.ingredient.test(stack));

        return true;
    }

    //potion=ingredient
    //from=input
    public record Recipe<T>(T input, Ingredient ingredient, T output) {
    }
}
