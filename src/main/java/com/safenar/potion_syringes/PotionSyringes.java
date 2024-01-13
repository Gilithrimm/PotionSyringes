package com.safenar.potion_syringes;

import com.safenar.potion_syringes.items.Syringe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import static com.safenar.potion_syringes.registries.SyringeRecipeRegistry.registerPotionToItemRecipe;
import static net.minecraft.registry.Registry.register;

public class PotionSyringes implements ModInitializer {
	public static final Syringe SYRINGE = new Syringe(new FabricItemSettings()
			.maxCount(8));
	public static final Item EMPTY_SYRINGE = new Item(new FabricItemSettings());

	@Override
	public void onInitialize() {
		register(Registries.ITEM, new Identifier("potion_syringes",
				"syringe"), SYRINGE);
		register(Registries.ITEM, new Identifier("potion_syringes",
				"empty_syringe"), EMPTY_SYRINGE);

		registerPotionToItemRecipe(Items.POTION, EMPTY_SYRINGE, SYRINGE);
	}
}
