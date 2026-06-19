package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import org.jspecify.annotations.*;

import java.util.concurrent.*;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        ColorfulAzaleas.LOGGER.info("Generating BlockTags for " + ColorfulAzaleas.MOD_ID);

        addBlocks(BlockTags.MINEABLE_WITH_HOE, AzaleaBlocks.DROOPING_AZALEA_LEAVES);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            addBlocks(ModTags.Blocks.AZALEA_LOGS, woodSet.getLogAndWoodBlocks());

            addBlocks(BlockTags.MINEABLE_WITH_AXE, woodSet.getWoodSetBlocks());
            addBlocks(BlockTags.MINEABLE_WITH_HOE, tree.getLeavesAndDroopingBlocks());

            addBlocks(BlockItemTags.LOGS_THAT_BURN, woodSet.getLogAndWoodBlocks());
            addBlocks(BlockTags.OVERWORLD_NATURAL_LOGS, woodSet.getLog());

            addBlocks(BlockTags.PLANKS, woodSet.getPlanks());
            addBlocks(BlockTags.WOODEN_STAIRS, woodSet.getStairs());
            addBlocks(BlockTags.WOODEN_SLABS, woodSet.getSlab());
            addBlocks(BlockTags.WOODEN_FENCES, woodSet.getFence());
            addBlocks(BlockTags.FENCE_GATES, woodSet.getFenceGate());
            addBlocks(BlockTags.WOODEN_DOORS, woodSet.getDoor());
            addBlocks(BlockTags.WOODEN_TRAPDOORS, woodSet.getTrapdoor());
            addBlocks(BlockTags.WOODEN_PRESSURE_PLATES, woodSet.getPressurePlate());
            addBlocks(BlockTags.WOODEN_BUTTONS, woodSet.getButton());
            addBlocks(BlockTags.STANDING_SIGNS, woodSet.getSign());
            addBlocks(BlockTags.WALL_SIGNS, woodSet.getWallSign());
            addBlocks(BlockTags.CEILING_HANGING_SIGNS, woodSet.getHangingSign());
            addBlocks(BlockTags.WALL_HANGING_SIGNS, woodSet.getWallHangingSign());
            addBlocks(BlockTags.WOODEN_SHELVES, woodSet.getShelf());

            addBlocks(BlockItemTags.SAPLINGS, tree.getSapling());
            addBlocks(BlockTags.LEAVES, tree.getLeavesBlocks());
            addBlocks(BlockTags.BEE_ATTRACTIVE, tree.getSapling(), tree.getFloweringLeaves());
            addBlocks(BlockTags.FLOWERS, tree.getSapling(), tree.getFloweringLeaves());
            addBlocks(BlockTags.FLOWER_POTS, tree.getPottedSapling());

            // Fabric C / convention tags
            addBlocks(ModTags.Blocks.C_PLANKS_THAT_BURN, woodSet.getPlanks());
            addBlocks(ModTags.Blocks.C_FLOWERS, tree.getSapling());
            addBlocks(ModTags.Blocks.C_WOODEN_FENCES, woodSet.getFence());
            addBlocks(ModTags.Blocks.C_FENCE_GATES_WOODEN, woodSet.getFenceGate());
            addBlocks(ModTags.Blocks.C_STRIPPED_LOGS, woodSet.getStrippedLog());
            addBlocks(ModTags.Blocks.C_STRIPPED_WOODS, woodSet.getStrippedWood());
            addBlocks(ModTags.Blocks.C_WOODS, woodSet.getWood());
        }
    }

    private void addBlocks(BlockItemTagId tag, Block... blocks) {
        addBlocks(tag.block(), blocks);
    }

    private void addBlocks(TagKey<Block> tag, Block... blocks) {
        var tagBuilder = builder(tag);

        for (Block block : blocks) {
            tagBuilder.add(key(block));
        }
    }

    private static ResourceKey<Block> key(Block block) {
        return block.builtInRegistryHolder().key();
    }
}