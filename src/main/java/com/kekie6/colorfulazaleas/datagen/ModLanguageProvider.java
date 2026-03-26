package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.core.*;
import org.jspecify.annotations.*;

import java.util.concurrent.*;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.@NonNull Provider wrapperLookup, @NonNull TranslationBuilder builder) {
        ColorfulAzaleas.LOGGER.info("Generating en_us for " + ColorfulAzaleas.MOD_ID);

        // --- Iterate all registered trees ---
        if (AzaleaBlocks.trees == null) {
            AzaleaBlocks.init(); // ensure populated
        }

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            AzaleaColors color = tree.getColor();
            String colorName = capitalize(color.name().replace("_", " "));
            String title = capitalize(color.getTitle());

            // Leaves variants
            builder.add(tree.getAzaleaLeaves(), colorName + " Azalea Leaves");
            builder.add(tree.getFloweringLeaves(), "Flowering " + colorName + " Azalea Leaves");
            builder.add(tree.getBloomingLeaves(), "Blooming " + colorName + " Azalea Leaves");
            builder.add(tree.getDroopingLeaves(), "Drooping " + colorName + " Azalea Leaves");

            // Saplings
            builder.add(tree.getSapling(), colorName + " Azalea Sapling");
            builder.add(tree.getPottedSapling(), "Potted " + colorName + " Azalea Sapling");

            // Wood Set
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet != null) {
                builder.add(woodSet.getLog(), title + " Azalea Log");
                builder.add(woodSet.getWood(), title + " Azalea Wood");
                builder.add(woodSet.getStrippedLog(), "Stripped " + title + " Azalea Log");
                builder.add(woodSet.getStrippedWood(), "Stripped " + title + " Azalea Wood");
                builder.add(woodSet.getPlanks(), title + " Azalea Planks");
                builder.add(woodSet.getStairs(), title + " Azalea Stairs");
                builder.add(woodSet.getSlab(), title + " Azalea Slab");
                builder.add(woodSet.getFence(), title + " Azalea Fence");
                builder.add(woodSet.getFenceGate(), title + " Azalea Fence Gate");
                builder.add(woodSet.getDoor(), title + " Azalea Door");
                builder.add(woodSet.getTrapdoor(), title + " Azalea Trapdoor");
                builder.add(woodSet.getPressurePlate(), title + " Azalea Pressure Plate");
                builder.add(woodSet.getButton(), title + " Azalea Button");

                builder.add(woodSet.getSign(), title + " Azalea Sign");
                builder.add(woodSet.getWallSign(), title + " Azalea Wall Sign");
                builder.add(woodSet.getHangingSign(), title + " Azalea Hanging Sign");
                builder.add(woodSet.getWallHangingSign(), title + " Azalea Wall Hanging Sign");

                builder.add(woodSet.getBoatItem(), title + " Azalea Boat");
                builder.add(woodSet.getChestBoatItem(), title + " Azalea Boat with Chest");
                builder.add("entity.colorfulazaleas." + woodSet.getWoodSet() + "_azalea_chest_boat", title + " Azalea Boat with Chest");

                builder.add(woodSet.getShelf(), title + " Azalea Shelf");
            }
        }

        // --- Add non-set block/item ---
        builder.add(AzaleaBlocks.DROOPING_AZALEA_LEAVES, "Drooping Azalea Leaves");
        builder.add(AzaleaItems.ICON_ITEM, "§fC§6o§dl§bo§er§af§cu§9l §dA§ez§aa§bl§ce§ea§fs§6!");

        // --- Add custom entries (manually defined) ---
        builder.add("itemGroup.colorfulazaleas.colorful_azaleas", "Colorful Azaleas");

        // --- Tag groups (static) ---
        builder.add("tag.colorfulazaleas.azaleas", "Azaleas");
        builder.add("tag.colorfulazaleas.azalea_logs", "Azalea Logs");

        // --- Dynamically generated per-color tags ---
        for (AzaleaColors color : AzaleaColors.values()) {
            String title = capitalize(color.getTitle());
            builder.add(
                    "tag.colorfulazaleas." + color.getTitle() + "_azalea_logs",
                    title + " Azalea Logs"
            );
        }
    }

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = input.toLowerCase().split(" ");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty())
                result.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1))
                        .append(" ");
        }
        return result.toString().trim();
    }
}
