package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;

import java.util.concurrent.*;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        ColorfulAzaleas.LOGGER.info("Generating LootTable for " + ColorfulAzaleas.MOD_ID);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            for (Block block : woodSet.getWoodSetMinusSlabAndDoorBlocks()) {
                dropSelf(block);
            }

            add(woodSet.getSlab(), createSlabItemTable(woodSet.getSlab()));
            add(woodSet.getDoor(), createDoorTable(woodSet.getDoor()));

            for (Block block: tree.getLeavesAndDroopingBlocks()) {
                add(block, createLeavesDrops(block, tree.getSapling(),
                        0.05f,
                        0.0625f,
                        0.083333336f,
                        0.1f));
            }

            dropPottedContents(tree.getPottedSapling());
        }
    }
}
