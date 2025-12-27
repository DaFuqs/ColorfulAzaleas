package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.data.loot.*;
import net.minecraft.resources.*;
import net.minecraft.world.flag.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.*;
import org.jspecify.annotations.*;

import java.util.*;
import java.util.function.*;

public class ModLootTableProvider extends BlockLootSubProvider {
    
    public ModLootTableProvider(HolderLookup.Provider lookupProvider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
	}
    
    @Override
    protected void generate() {
        ColorfulAzaleas.LOGGER.info("Generating LootTables for " + ColorfulAzaleas.MOD_ID);
        
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;
            
            for (Block block : woodSet.getWoodSetMinusSlabAndDoorBlocks()) {
                dropSelf(block);
            }
            
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
