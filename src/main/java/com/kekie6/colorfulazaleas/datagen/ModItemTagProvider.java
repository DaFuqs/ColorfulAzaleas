package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.data.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public class ModItemTagProvider extends ItemTagsProvider {
    
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ColorfulAzaleas.MOD_ID);
    }
    
    @Override
    protected void addTags(HolderLookup.@NotNull Provider wrapperLookup) {
        ColorfulAzaleas.LOGGER.info("Generating ItemTags for " + ColorfulAzaleas.MOD_ID);

        this.tag(ModTags.Items.AZALEAS)
                .add(Items.AZALEA);

        for (ColorfulTree tree : AzaleaBlocks.TREES) {
            AzaleaWoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            this.tag(ModTags.Items.AZALEA_LOGS)
                    .add(woodSet.getLogAndWoodItems());

            this.tag(ModTags.Items.AZALEAS)
                    .add(tree.getSapling().asItem());

            // Per-color tag (dynamic lookup)
            TagKey<Item> colorTag = ModTags.Items.COLORFUL_AZALEA_LOGS_ITEMS.get(tree.getColor());
            if (colorTag != null) {
                this.tag(colorTag).add(woodSet.getLogAndWoodItems());
            }

            this.tag(ItemTags.LOGS_THAT_BURN)
                    .add(woodSet.getLogAndWoodItems());

            this.tag(ItemTags.PLANKS)
                    .add(woodSet.getPlanks().asItem());
            this.tag(ItemTags.WOODEN_STAIRS)
                    .add(woodSet.getStairs().asItem());
            this.tag(ItemTags.WOODEN_SLABS)
                    .add(woodSet.getSlab().asItem());
            this.tag(ItemTags.WOODEN_FENCES)
                    .add(woodSet.getFence().asItem());
            this.tag(ItemTags.FENCE_GATES)
                    .add(woodSet.getFenceGate().asItem());
            this.tag(ItemTags.WOODEN_DOORS)
                    .add(woodSet.getDoor().asItem());
            this.tag(ItemTags.WOODEN_TRAPDOORS)
                    .add(woodSet.getTrapdoor().asItem());
            this.tag(ItemTags.WOODEN_PRESSURE_PLATES)
                    .add(woodSet.getPressurePlate().asItem());
            this.tag(ItemTags.WOODEN_BUTTONS)
                    .add(woodSet.getButton().asItem());
            this.tag(ItemTags.SIGNS)
                    .add(woodSet.getSign().asItem());
            this.tag(ItemTags.HANGING_SIGNS)
                    .add(woodSet.getHangingSign().asItem());
            this.tag(ItemTags.WOODEN_SHELVES)
                    .add(woodSet.getShelf().asItem());

            this.tag(ItemTags.BOATS)
                    .add(woodSet.getBoatItem().get());
            this.tag(ItemTags.CHEST_BOATS)
                    .add(woodSet.getChestBoatItem().get());

            this.tag(ItemTags.SAPLINGS)
                    .add(tree.getSapling().asItem());

            this.tag(ModTags.Items.AZALEA_SAPLINGS)
                    .add(tree.getSapling().asItem());
            
            this.tag(ItemTags.LEAVES)
                    .add(tree.getLeavesItems());
            
            // Convention Tags ("C" namepsace)
            this.tag(ModTags.Items.C_PLANKS_THAT_BURN)
                    .add(woodSet.getPlanks().asItem());
            this.tag(ModTags.Items.C_WOODEN_FENCES)
                    .add(woodSet.getFence().asItem());
            this.tag(ModTags.Items.C_FENCE_GATES_WOODEN)
                    .add(woodSet.getFenceGate().asItem());
            this.tag(ModTags.Items.C_STRIPPED_LOGS)
                    .add(woodSet.getStrippedLog().asItem());
            this.tag(ModTags.Items.C_STRIPPED_WOODS)
                    .add(woodSet.getStrippedWood().asItem());
        }
    }
}