package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.block.Block;
import net.minecraft.data.loottable.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        ColorfulAzaleas.LOGGER.info("Generating LootTable for " + ColorfulAzaleas.MOD_ID);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            for (Block block : woodSet.getWoodSetMinusSlabAndDoorBlocks()) {
                addDrop(block);
            }

            addDrop(woodSet.getSlab(), slabDrops(woodSet.getSlab()));
            addDrop(woodSet.getDoor(), doorDrops(woodSet.getDoor()));

            addDrop(woodSet.getShelf());

            for (Block block: tree.getLeavesAndDroopingBlocks()) {
                addDrop(block, leavesDrops(block, tree.getSapling(),
                        0.05f,
                        0.0625f,
                        0.083333336f,
                        0.1f));
            }

            addPottedPlantDrops(tree.getPottedSapling());
        }
    }
        // Not currently used
        @Override
        public BlockLootTableGenerator withConditions (ResourceCondition...conditions){
            return super.withConditions(conditions);
        }

        // Not currently used
        @Override
        public BiConsumer<RegistryKey<LootTable>, LootTable.Builder> withConditions
        (BiConsumer < RegistryKey < LootTable >, LootTable.Builder > exporter, ResourceCondition...conditions){
            return super.withConditions(exporter, conditions);
        }

}
