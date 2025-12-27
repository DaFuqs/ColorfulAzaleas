package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;
import net.neoforged.neoforge.common.data.*;
import org.jspecify.annotations.*;

import java.util.concurrent.*;

public class ModBlockTagProvider extends BlockTagsProvider {
    
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ColorfulAzaleas.MOD_ID);
    }
    
    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        ColorfulAzaleas.LOGGER.info("Generating BlockTags for " + ColorfulAzaleas.MOD_ID);

        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(AzaleaBlocks.DROOPING_AZALEA_LEAVES);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            this.tag(ModTags.Blocks.AZALEA_LOGS)
                    .add(woodSet.getLogAndWoodBlocks());

            this.tag(BlockTags.MINEABLE_WITH_AXE)
                    .add(woodSet.getWoodSetBlocks());

            this.tag(BlockTags.MINEABLE_WITH_HOE)
                    .add(tree.getLeavesAndDroopingBlocks());

            this.tag(BlockTags.LOGS_THAT_BURN)
                    .add(woodSet.getLogAndWoodBlocks());

            this.tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                    .add(woodSet.getLog());

            this.tag(BlockTags.PLANKS)
                    .add(woodSet.getPlanks());
            this.tag(BlockTags.WOODEN_STAIRS)
                    .add(woodSet.getStairs());
            this.tag(BlockTags.WOODEN_SLABS)
                    .add(woodSet.getSlab());
            this.tag(BlockTags.WOODEN_FENCES)
                    .add(woodSet.getFence());
            this.tag(BlockTags.FENCE_GATES)
                    .add(woodSet.getFenceGate());
            this.tag(BlockTags.WOODEN_DOORS)
                    .add(woodSet.getDoor());
            this.tag(BlockTags.WOODEN_TRAPDOORS)
                    .add(woodSet.getTrapdoor());
            this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
                    .add(woodSet.getPressurePlate());
            this.tag(BlockTags.WOODEN_BUTTONS)
                    .add(woodSet.getButton());
            this.tag(BlockTags.STANDING_SIGNS)
                    .add(woodSet.getSign());
            this.tag(BlockTags.WALL_SIGNS)
                    .add(woodSet.getWallSign());
            this.tag(BlockTags.CEILING_HANGING_SIGNS)
                    .add(woodSet.getHangingSign());
            this.tag(BlockTags.WALL_HANGING_SIGNS)
                    .add(woodSet.getWallHangingSign());
            this.tag(BlockTags.WOODEN_SHELVES)
                    .add(woodSet.getShelf());

            this.tag(BlockTags.SAPLINGS)
                    .add(tree.getSapling());
            this.tag(BlockTags.LEAVES)
                    .add(tree.getLeavesBlocks());
            this.tag(BlockTags.BEE_ATTRACTIVE)
                    .add(tree.getSapling())
                    .add(tree.getFloweringLeaves());
            this.tag(BlockTags.FLOWERS)
                    .add(tree.getSapling())
                    .add(tree.getFloweringLeaves());
            this.tag(BlockTags.FLOWER_POTS)
                    .add(tree.getPottedSapling());
            // Fabric C (Convention) Tags
            this.tag(ModTags.Blocks.C_PLANKS_THAT_BURN)
                    .add(woodSet.getPlanks());
            this.tag(ModTags.Blocks.C_FLOWERS)
                    .add(tree.getSapling());
            this.tag(ModTags.Blocks.C_WOODEN_FENCES)
                    .add(woodSet.getFence());
            this.tag(ModTags.Blocks.C_FENCE_GATES_WOODEN)
                    .add(woodSet.getFenceGate());
            this.tag(ModTags.Blocks.C_STRIPPED_LOGS)
                    .add(woodSet.getStrippedLog());
            this.tag(ModTags.Blocks.C_STRIPPED_WOODS)
                    .add(woodSet.getStrippedWood());
            this.tag(ModTags.Blocks.C_WOODS)
                    .add(woodSet.getWood());
        }
    }
}