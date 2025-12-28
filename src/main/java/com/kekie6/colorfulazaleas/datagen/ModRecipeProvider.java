package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.jspecify.annotations.*;

import java.util.concurrent.*;

import static com.kekie6.colorfulazaleas.util.ModTags.Items.*;

public class ModRecipeProvider extends RecipeProvider {
	
	public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
		super(provider, output);
	}
	
	@Override
	protected void buildRecipes() {
		ColorfulAzaleas.LOGGER.info("Generating ModRecipes for " + ColorfulAzaleas.MOD_ID);
		
		for (ColorfulTree tree : AzaleaBlocks.TREES) {
			AzaleaWoodSet woodSet = tree.getWoodSet();
			if (woodSet == null) continue;
			
			// Looks up the tag for this tree’s color
			TagKey<Item> logTag = COLORFUL_AZALEA_LOGS_ITEMS.get(tree.getColor());
			if (logTag != null) {
				planksFromLogs(woodSet.getPlanks(), logTag, 4);
			}
			
			ColorfulAzaleas.LOGGER.info("Generating recipes for: {}", woodSet.getWoodSet());
			
			// --- Stairs ---
			stairBuilder(woodSet.getStairs(), Ingredient.of(woodSet.getPlanks()))
					.group("wooden_stairs")
					.unlockedBy(getHasName(woodSet.getPlanks()), has(woodSet.getPlanks()))
					.save(this.output);
			
			// --- Slabs ---
			slabBuilder(RecipeCategory.BUILDING_BLOCKS, woodSet.getSlab(), Ingredient.of(woodSet.getPlanks()))
					.group("wooden_slab")
					.unlockedBy(getHasName(woodSet.getPlanks()), has(woodSet.getPlanks()))
					.save(this.output);
			
			// --- Doors ---
			doorBuilder(woodSet.getDoor(), Ingredient.of(woodSet.getPlanks()))
					.group("wooden_door")
					.unlockedBy(getHasName(woodSet.getPlanks()), has(woodSet.getPlanks()))
					.save(this.output);
			
			// --- Trapdoors ---
			trapdoorBuilder(woodSet.getTrapdoor(), Ingredient.of(woodSet.getPlanks()))
					.group("wooden_trapdoor")
					.unlockedBy(getHasName(woodSet.getPlanks()), has(woodSet.getPlanks()))
					.save(this.output);
			
			// --- Fence ---
			fenceBuilder(woodSet.getFence(), Ingredient.of(woodSet.getPlanks()))
					.group("wooden_fence")
					.unlockedBy(getHasName(woodSet.getPlanks()), has(woodSet.getPlanks()))
					.save(this.output);
			
			// --- Fence Gate ---
			fenceGateBuilder(woodSet.getFenceGate(), Ingredient.of(woodSet.getPlanks()))
					.group("wooden_fence_gate")
					.unlockedBy(getHasName(woodSet.getPlanks()), has(woodSet.getPlanks()))
					.save(this.output);
			
			// --- Pressure Plate ---
			pressurePlateBuilder(RecipeCategory.REDSTONE, woodSet.getPressurePlate(), Ingredient.of(woodSet.getPlanks()))
					.group("wooden_pressure_plate")
					.unlockedBy(getHasName(woodSet.getPlanks()), has(woodSet.getPlanks()))
					.save(this.output);
			
			// --- Button ---
			buttonBuilder(woodSet.getButton(), Ingredient.of(woodSet.getPlanks()))
					.group("wooden_button")
					.unlockedBy(getHasName(woodSet.getPlanks()), has(woodSet.getPlanks()))
					.save(this.output);
			// --- Signs & Hanging Signs ---
			signBuilder(woodSet.getSign(), Ingredient.of(woodSet.getPlanks()))
					.group("wooden_sign")
					.unlockedBy(getHasName(woodSet.getPlanks()), has(woodSet.getPlanks()))
					.save(this.output);
			
			hangingSign(woodSet.getHangingSign(), woodSet.getStrippedLog());
			// --- Wood & Stripped Wood ---
			woodFromLogs(woodSet.getWood(), woodSet.getLog());
			woodFromLogs(woodSet.getStrippedWood(), woodSet.getStrippedLog());
			// --- Boat ---
			woodenBoat(woodSet.getBoatItem(), woodSet.getPlanks());
			chestBoat(woodSet.getChestBoatItem(), woodSet.getBoatItem());
			// --- Shelf ---
			shelf(woodSet.getShelf(), woodSet.getStrippedLog());
		}
		
	}
	
	public static class Runner extends RecipeProvider.Runner {
		
		public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(output, lookupProvider);
		}
		
		@Override
		protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput output) {
			return new ModRecipeProvider(provider, output);
		}
		
		@Override
		public @NonNull String getName() {
			return "ColorfulAzaleas Recipes";
		}
	}
	
}