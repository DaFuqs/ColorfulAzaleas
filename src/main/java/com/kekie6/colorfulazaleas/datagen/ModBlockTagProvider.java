package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.ModTags;
import com.kekie6.colorfulazaleas.util.WoodSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        ColorfulAzaleas.LOGGER.info("Generating BlockTags for " + ColorfulAzaleas.MOD_ID);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            valueLookupBuilder(ModTags.Blocks.AZALEA_LOGS)
                    .add(woodSet.getLogAndWoodBlocks());

            valueLookupBuilder(BlockTags.AXE_MINEABLE)
                    .add(woodSet.getWoodSetBlocks());

            valueLookupBuilder(BlockTags.HOE_MINEABLE)
                    .add(tree.getLeavesAndDroopingBlocks());

            valueLookupBuilder(BlockTags.LOGS_THAT_BURN)
                    .add(woodSet.getLogAndWoodBlocks());

            valueLookupBuilder(BlockTags.OVERWORLD_NATURAL_LOGS)
                    .add(woodSet.getLog());

            valueLookupBuilder(BlockTags.PLANKS)
                    .add(woodSet.getPlanks());
            valueLookupBuilder(BlockTags.WOODEN_STAIRS)
                    .add(woodSet.getStairs());
            valueLookupBuilder(BlockTags.WOODEN_SLABS)
                    .add(woodSet.getSlab());
            valueLookupBuilder(BlockTags.WOODEN_FENCES)
                    .add(woodSet.getFence());
            valueLookupBuilder(BlockTags.FENCE_GATES)
                    .add(woodSet.getFenceGate());
            valueLookupBuilder(BlockTags.WOODEN_DOORS)
                    .add(woodSet.getDoor());
            valueLookupBuilder(BlockTags.WOODEN_TRAPDOORS)
                    .add(woodSet.getTrapdoor());
            valueLookupBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
                    .add(woodSet.getPressurePlate());
            valueLookupBuilder(BlockTags.WOODEN_BUTTONS)
                    .add(woodSet.getButton());
            valueLookupBuilder(BlockTags.STANDING_SIGNS)
                    .add(woodSet.getSign());
            valueLookupBuilder(BlockTags.WALL_SIGNS)
                    .add(woodSet.getWallSign());
            valueLookupBuilder(BlockTags.CEILING_HANGING_SIGNS)
                    .add(woodSet.getHangingSign());
            valueLookupBuilder(BlockTags.WALL_HANGING_SIGNS)
                    .add(woodSet.getWallHangingSign());
            valueLookupBuilder(BlockTags.WOODEN_SHELVES)
                    .add(woodSet.getShelf());

            valueLookupBuilder(BlockTags.SAPLINGS)
                    .add(tree.getSapling());
            valueLookupBuilder(BlockTags.LEAVES)
                    .add(tree.getLeavesBlocks());
            valueLookupBuilder(BlockTags.BEE_ATTRACTIVE)
                    .add(tree.getSapling())
                    .add(tree.getFloweringLeaves());
            valueLookupBuilder(BlockTags.FLOWERS)
                    .add(tree.getSapling())
                    .add(tree.getFloweringLeaves());
            valueLookupBuilder(BlockTags.FLOWER_POTS)
                    .add(tree.getPottedSapling());
            // Fabric C (Convention) Tags
            valueLookupBuilder(ModTags.Blocks.C_PLANKS_THAT_BURN)
                    .add(woodSet.getPlanks());
            valueLookupBuilder(ModTags.Blocks.C_FLOWERS)
                    .add(tree.getSapling());
            valueLookupBuilder(ModTags.Blocks.C_WOODEN_FENCES)
                    .add(woodSet.getFence());
            valueLookupBuilder(ModTags.Blocks.C_FENCE_GATES_WOODEN)
                    .add(woodSet.getFenceGate());
            valueLookupBuilder(ModTags.Blocks.C_STRIPPED_LOGS)
                    .add(woodSet.getStrippedLog());
            valueLookupBuilder(ModTags.Blocks.C_STRIPPED_WOODS)
                    .add(woodSet.getStrippedWood());
            valueLookupBuilder(ModTags.Blocks.C_WOODS)
                    .add(woodSet.getWood());
        }
    }
}