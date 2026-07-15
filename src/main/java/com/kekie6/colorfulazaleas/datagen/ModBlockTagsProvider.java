package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.data.*;
import org.jspecify.annotations.*;

import java.util.concurrent.*;

public class ModBlockTagsProvider extends BlockTagsProvider {
    
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ColorfulAzaleas.MOD_ID);
    }
    
    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        ColorfulAzaleas.LOGGER.info("Generating BlockTags for " + ColorfulAzaleas.MOD_ID);

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(AzaleaBlocks.DROOPING_AZALEA_LEAVES.get());

        for (ColorfulTree tree : AzaleaBlocks.TREES) {
            AzaleaWoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            this.tag(ModTags.Blocks.AZALEA_LOGS).add(woodSet.getLogAndWoodBlocks());
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(woodSet.getWoodSetBlocks());
            this.tag(BlockTags.MINEABLE_WITH_HOE).add(tree.getLeavesAndDroopingBlocks());
            this.tag(BlockTags.LOGS_THAT_BURN).add(woodSet.getLogAndWoodBlocks());
            this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(woodSet.getLog().get());
            this.tag(BlockTags.PLANKS).add(woodSet.getPlanks().get());
            this.tag(BlockTags.WOODEN_STAIRS).add(woodSet.getStairs().get());
            this.tag(BlockTags.WOODEN_SLABS).add(woodSet.getSlab().get());
            this.tag(BlockTags.WOODEN_FENCES).add(woodSet.getFence().get());
            this.tag(BlockTags.FENCE_GATES).add(woodSet.getFenceGate().get());
            this.tag(BlockTags.WOODEN_DOORS).add(woodSet.getDoor().get());
            this.tag(BlockTags.WOODEN_TRAPDOORS).add(woodSet.getTrapdoor().get());
            this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(woodSet.getPressurePlate().get());
            this.tag(BlockTags.WOODEN_BUTTONS).add(woodSet.getButton().get());
            this.tag(BlockTags.STANDING_SIGNS).add(woodSet.getSign().get());
            this.tag(BlockTags.WALL_SIGNS).add(woodSet.getWallSign().get());
            this.tag(BlockTags.CEILING_HANGING_SIGNS).add(woodSet.getHangingSign().get());
            this.tag(BlockTags.WALL_HANGING_SIGNS).add(woodSet.getWallHangingSign().get());
            this.tag(BlockTags.WOODEN_SHELVES).add(woodSet.getShelf().get());

            this.tag(BlockTags.SAPLINGS).add(tree.getSapling().get());
            this.tag(BlockTags.LEAVES).add(tree.getLeavesBlocks());
            this.tag(BlockTags.BEE_ATTRACTIVE).add(tree.getSapling().get()).add(tree.getFloweringLeaves().get());
            this.tag(BlockTags.FLOWERS).add(tree.getSapling().get()).add(tree.getFloweringLeaves().get());
            this.tag(BlockTags.FLOWER_POTS).add(tree.getPottedSapling().get());

            // Convention Tags
            this.tag(ModTags.Blocks.C_PLANKS_THAT_BURN).add(woodSet.getPlanks().get());
            this.tag(ModTags.Blocks.C_FLOWERS).add(tree.getSapling().get());
            this.tag(ModTags.Blocks.C_WOODEN_FENCES).add(woodSet.getFence().get());
            this.tag(ModTags.Blocks.C_FENCE_GATES_WOODEN).add(woodSet.getFenceGate().get());
            this.tag(ModTags.Blocks.C_STRIPPED_LOGS).add(woodSet.getStrippedLog().get());
            this.tag(ModTags.Blocks.C_STRIPPED_WOODS).add(woodSet.getStrippedWood().get());
            this.tag(ModTags.Blocks.C_WOODS).add(woodSet.getWood().get());
        }
    }

    protected record Appender(TagAppender<Block> app) implements TagAppender<Block> {
        @Override
        public ModBlockTagsProvider.Appender add(ResourceKey<Block> element) {
            app.add(element);
            return this;
        }

        @Override
        public ModBlockTagsProvider.Appender addOptional(ResourceKey<Block> element) {
            app.addOptional(element);
            return this;
        }

        @Override
        public ModBlockTagsProvider.Appender addTag(TagKey<Block> tag) {
            app.addTag(tag);
            return this;
        }

        @Override
        public ModBlockTagsProvider.Appender addOptionalTag(TagKey<Block> tag) {
            app.addOptionalTag(tag);
            return this;
        }

        @Override
        public ModBlockTagsProvider.Appender add(TagEntry entry) {
            app.add(entry);
            return this;
        }

        @Override
        public ModBlockTagsProvider.Appender replace(boolean value) {
            app.replace(value);
            return this;
        }

        @Override
        public ModBlockTagsProvider.Appender remove(ResourceKey<Block> element) {
            app.remove(element);
            return this;
        }

        @Override
        public ModBlockTagsProvider.Appender remove(TagKey<Block> tag) {
            app.remove(tag);
            return this;
        }

        public ModBlockTagsProvider.Appender add(Block... blocks) {
            for (Block block : blocks) {
                add(BuiltInRegistries.BLOCK.wrapAsHolder(block).getKey());
            }
            return this;
        }

        public ModBlockTagsProvider.Appender addAll(Iterable<Block> blocks) {
            for (Block block : blocks) {
                add(block);
            }
            return this;
        }
    }

    @Override
    protected ModBlockTagsProvider.Appender tag(TagKey<Block> tag) {
        return new ModBlockTagsProvider.Appender(super.tag(tag));
    }
    
}