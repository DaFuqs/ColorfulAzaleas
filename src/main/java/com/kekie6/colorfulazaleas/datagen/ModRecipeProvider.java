package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

import static com.kekie6.colorfulazaleas.util.ModTags.Items.COLORFUL_AZALEA_LOGS_ITEMS;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                ColorfulAzaleas.LOGGER.info("Generating ModRecipes for " + ColorfulAzaleas.MOD_ID);

                for (ColorfulTree tree : AzaleaBlocks.trees) {
                    WoodSet woodSet = tree.getWoodSet();
                    if (woodSet == null) continue;

                    // Looks up the tag for this tree’s color
                    TagKey<Item> logTag = COLORFUL_AZALEA_LOGS_ITEMS.get(tree.getColor());
                    if (logTag != null) {
                        offerPlanksRecipe(woodSet.getPlanks(), logTag, 4);
                    }

                    ColorfulAzaleas.LOGGER.info("Generating recipes for: {}", woodSet.getWoodSet());

                    // --- Stairs ---
                    createStairsRecipe(woodSet.getStairs(), Ingredient.ofItem(woodSet.getPlanks()))
                            .group("wooden_stairs")
                            .criterion(hasItem(woodSet.getPlanks()), conditionsFromItem(woodSet.getPlanks()))
                            .offerTo(this.exporter);

                    // --- Slabs ---
                    createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, woodSet.getSlab(), Ingredient.ofItem(woodSet.getPlanks()))
                            .group("wooden_slab")
                            .criterion(hasItem(woodSet.getPlanks()), conditionsFromItem(woodSet.getPlanks()))
                            .offerTo(this.exporter);

                    // --- Doors ---
                    createDoorRecipe(woodSet.getDoor(), Ingredient.ofItem(woodSet.getPlanks()))
                            .group("wooden_door")
                            .criterion(hasItem(woodSet.getPlanks()), conditionsFromItem(woodSet.getPlanks()))
                            .offerTo(this.exporter);

                    // --- Trapdoors ---
                    createTrapdoorRecipe(woodSet.getTrapdoor(), Ingredient.ofItem(woodSet.getPlanks()))
                            .group("wooden_trapdoor")
                            .criterion(hasItem(woodSet.getPlanks()), conditionsFromItem(woodSet.getPlanks()))
                            .offerTo(this.exporter);

                    // --- Fence ---
                    createFenceRecipe(woodSet.getFence(), Ingredient.ofItem(woodSet.getPlanks()))
                            .group("wooden_fence")
                            .criterion(hasItem(woodSet.getPlanks()), conditionsFromItem(woodSet.getPlanks()))
                            .offerTo(this.exporter);

                    // --- Fence Gate ---
                    createFenceGateRecipe(woodSet.getFenceGate(), Ingredient.ofItem(woodSet.getPlanks()))
                            .group("wooden_fence_gate")
                            .criterion(hasItem(woodSet.getPlanks()), conditionsFromItem(woodSet.getPlanks()))
                            .offerTo(this.exporter);

                    // --- Pressure Plate ---
                    createPressurePlateRecipe(RecipeCategory.REDSTONE, woodSet.getPressurePlate(), Ingredient.ofItem(woodSet.getPlanks()))
                            .group("wooden_pressure_plate")
                            .criterion(hasItem(woodSet.getPlanks()), conditionsFromItem(woodSet.getPlanks()))
                            .offerTo(this.exporter);

                    // --- Button ---
                    createButtonRecipe(woodSet.getButton(), Ingredient.ofItem(woodSet.getPlanks()))
                            .group("wooden_button")
                            .criterion(hasItem(woodSet.getPlanks()), conditionsFromItem(woodSet.getPlanks()))
                            .offerTo(this.exporter);
                    // --- Signs & Hanging Signs ---
                    createSignRecipe(woodSet.getSign(), Ingredient.ofItem(woodSet.getPlanks()))
                            .group("wooden_sign")
                            .criterion(hasItem(woodSet.getPlanks()), conditionsFromItem(woodSet.getPlanks()))
                            .offerTo(this.exporter);

                    offerHangingSignRecipe(woodSet.getHangingSign(), woodSet.getStrippedLog());
                    // --- Wood & Stripped Wood ---
                    offerBarkBlockRecipe(woodSet.getWood(), woodSet.getLog());
                    offerBarkBlockRecipe(woodSet.getStrippedWood(), woodSet.getStrippedLog());
                    // --- Boat ---
                    offerBoatRecipe(woodSet.getBoatItem(), woodSet.getPlanks());
                    offerChestBoatRecipe(woodSet.getChestBoatItem(), woodSet.getBoatItem());
                    // --- Shelf ---
                    offerShelfRecipe(woodSet.getShelf(), woodSet.getStrippedLog());
                }


            }
        };
    }

    @Override
    public String getName() {
        return "ColorfulAzaleas Recipes";
    }
}