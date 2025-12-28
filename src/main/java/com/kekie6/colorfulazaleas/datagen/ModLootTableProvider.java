package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.data.loot.*;
import net.minecraft.world.flag.*;
import net.minecraft.world.level.block.*;
import org.jspecify.annotations.*;

import java.util.*;

// https://docs.neoforged.net/docs/resources/server/loottables/#loot-table
public class ModLootTableProvider extends BlockLootSubProvider {
    
    public ModLootTableProvider(HolderLookup.Provider lookupProvider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
	}
    
    // The contents of this Iterable are used for validation.
    // We return an Iterable over our block registry's values here.
    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return AzaleaBlocks.BLOCKS.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
    }
    
    @Override
    protected void generate() {
        ColorfulAzaleas.LOGGER.info("Generating LootTables for " + ColorfulAzaleas.MOD_ID);
        
        dropSelf(AzaleaBlocks.DROOPING_AZALEA_LEAVES.get());
        
        for (ColorfulTree tree : AzaleaBlocks.TREES) {
            AzaleaWoodSet woodSet = tree.getWoodSet();
            
            for (Block block : woodSet.getWoodSetMinusSlabAndDoorBlocks()) {
                dropSelf(block);
            }
            
            dropSelf(tree.getSapling().get());
            add(woodSet.getSlab().get(), createSlabItemTable(woodSet.getSlab().get()));
            add(woodSet.getDoor().get(), createDoorTable(woodSet.getDoor().get()));
            
            for (Block block : tree.getLeavesAndDroopingBlocks()) {
                add(block, createLeavesDrops(block, tree.getSapling().get(),
                        0.05f,
                        0.0625f,
                        0.083333336f,
                        0.1f));
            }
            
            dropPottedContents(tree.getPottedSapling().get());
        }
    }
    
}
