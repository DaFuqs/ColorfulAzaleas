package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.neoforged.neoforge.common.data.*;
import org.jspecify.annotations.*;

import java.util.concurrent.*;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, ColorfulAzaleas.MOD_ID, "en_us");
    }
    
    @Override
    protected void addTranslations() {
        ColorfulAzaleas.LOGGER.info("Generating en_us for " + ColorfulAzaleas.MOD_ID);

        // --- Iterate all registered trees ---
        /*if (AzaleaBlocks.trees == null) {
            AzaleaBlocks.init(); // ensure populated
        }*/

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            AzaleaColors color = tree.getColor();
            String colorName = capitalize(color.name().replace("_", " "));
            String title = capitalize(color.getTitle());

            // Leaves variants
            this.add(tree.getAzaleaLeaves(), colorName + " Azalea Leaves");
            this.add(tree.getFloweringLeaves(), "Flowering " + colorName + " Azalea Leaves");
            this.add(tree.getBloomingLeaves(), "Blooming " + colorName + " Azalea Leaves");
            this.add(tree.getDroopingLeaves(), "Drooping " + colorName + " Azalea Leaves");

            // Saplings
            this.add(tree.getSapling(), colorName + " Azalea Sapling");
            this.add(tree.getPottedSapling(), "Potted " + colorName + " Azalea Sapling");

            // Wood Set
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet != null) {
                this.add(woodSet.getLog(), title + " Azalea Log");
                this.add(woodSet.getWood(), title + " Azalea Wood");
                this.add(woodSet.getStrippedLog(), "Stripped " + title + " Azalea Log");
                this.add(woodSet.getStrippedWood(), "Stripped " + title + " Azalea Wood");
                this.add(woodSet.getPlanks(), title + " Azalea Planks");
                this.add(woodSet.getStairs(), title + " Azalea Stairs");
                this.add(woodSet.getSlab(), title + " Azalea Slab");
                this.add(woodSet.getFence(), title + " Azalea Fence");
                this.add(woodSet.getFenceGate(), title + " Azalea Fence Gate");
                this.add(woodSet.getDoor(), title + " Azalea Door");
                this.add(woodSet.getTrapdoor(), title + " Azalea Trapdoor");
                this.add(woodSet.getPressurePlate(), title + " Azalea Pressure Plate");
                this.add(woodSet.getButton(), title + " Azalea Button");
                
                this.add(woodSet.getSign(), title + " Azalea Sign");
                this.add(woodSet.getWallSign(), title + " Azalea Wall Sign");
                this.add(woodSet.getHangingSign(), title + " Azalea Hanging Sign");
                this.add(woodSet.getWallHangingSign(), title + " Azalea Wall Hanging Sign");
                
                this.add(woodSet.getBoatItem(), title + " Azalea Boat");
                this.add(woodSet.getChestBoatItem(), title + " Azalea Boat with Chest");
                this.add("entity.colorfulazaleas." + woodSet.getWoodSet() + "_azalea_chest_boat", title + " Azalea Boat with Chest");
                
                this.add(woodSet.getShelf(), title + " Azalea Shelf");
            }
        }

        // --- Add non-set block/item ---
        this.add(AzaleaBlocks.DROOPING_AZALEA_LEAVES, "Drooping Azalea Leaves");
        this.add(AzaleaItems.ICON_ITEM, "§fC§6o§dl§bo§er§af§cu§9l §dA§ez§aa§bl§ce§ea§fs§6!");

        // --- Add custom entries (manually defined) ---
        this.add("itemGroup.colorfulazaleas.colorful_azaleas", "Colorful Azaleas");

        // --- Tag groups (static) ---
        this.add("tag.colorfulazaleas.azaleas", "Azaleas");
        this.add("tag.colorfulazaleas.azalea_logs", "Azalea Logs");

        // --- Dynamically generated per-color tags ---
        for (AzaleaColors color : AzaleaColors.values()) {
            String title = capitalize(color.getTitle());
            this.add(
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
