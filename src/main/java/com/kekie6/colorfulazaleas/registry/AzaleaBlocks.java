package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.blocks.*;
import net.fabricmc.fabric.api.loot.v2.*;
import net.fabricmc.fabric.api.object.builder.v1.block.type.*;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.*;

import java.util.*;

public class AzaleaBlocks {

    public static ColorfulTree[] trees;
    public static final Block DROOPING_AZALEA_LEAVES = registerBlockWithItem("drooping_azalea_leaves", new DroopingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).setId(blockKey("drooping_azalea_leaves")).noCollission().sound(SoundType.CAVE_VINES)));

    public static void init() {
        trees = new ColorfulTree[AzaleaColors.values().length];
        for (int i = 0; i < AzaleaColors.values().length; i++) {
            trees[i] = new ColorfulTree(AzaleaColors.values()[i]);
        }
    }

    public enum AzaleaColors {
        orange("tecal"),
        yellow("fiss"),
        red("roze"),
        blue("azule"),
        pink("bright"),
        purple("walnut"),
        white("titanium");

        final String title;

        AzaleaColors(String title) {
            this.title = title;
        }
    }

    public static class ColorfulTree {
        public final WoodSet woodSet;
        public final Block sapling;
        public final Block pottedSapling;
        public final Block azaleaLeaves;
        public final Block floweringLeaves;
        public final Block bloomingLeaves;
        public final Block droopingLeaves;

        public ColorfulTree(AzaleaColors color) {
            String name = color.name();
            this.woodSet = new WoodSet(color);
            this.azaleaLeaves = registerBlockWithItem(name + "_azalea_leaves", new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).setId(blockKey("_azalea_leaves"))));
            this.floweringLeaves = registerBlockWithItem(name + "_flowering_azalea_leaves", new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).setId(blockKey("_flowering_azalea_leaves"))));
            this.bloomingLeaves = registerBlockWithItem(name + "_blooming_azalea_leaves", new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).requiresCorrectToolForDrops().setId(blockKey("_blooming_azalea_leaves"))));
            this.droopingLeaves = registerBlockWithItem(name + "_drooping_azalea_leaves", new DroopingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).noCollission().sound(SoundType.CAVE_VINES).setId(blockKey("_drooping_azalea_leaves"))));

            ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, ColorfulAzaleas.id(name));
            TreeGrower treeGrower = new TreeGrower(ColorfulAzaleas.MOD_ID + ":" + name + "_azalea", Optional.empty(), Optional.of(configuredFeatureKey), Optional.empty());
            this.sapling = registerBlockWithItem(name + "_azalea_sapling", new ColorfulAzaleaBushBlock(treeGrower, BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA).noOcclusion().setId(blockKey(name + "_azalea_sapling"))));
			this.pottedSapling = registerBlock("potted_" + name + "_azalea_sapling", new FlowerPotBlock(this.sapling, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_AZALEA).setId(blockKey("potted_" + name + "_azalea_sapling"))));

			addBlockToAzaleaLootTable(sapling);
			CompostingChanceRegistry.INSTANCE.add(sapling, 0.65F);
		}

    }

    public static void addBlockToAzaleaLootTable(Block block) {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
			if (Blocks.AZALEA_LEAVES.getLootTable().equals(key)) {
				LootPool.Builder poolBuilder1 = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.when(LootItemRandomChanceCondition.randomChance(0.01f))
						.add(LootItem.lootTableItem(block));
				tableBuilder.pool(poolBuilder1.build());
			}
		});
    }

    public static class WoodSet {
        public final String name;

        public static final BlockSetType BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.ACACIA).register(ColorfulAzaleas.id("colorful_azaleas"));
        public static final WoodType WOOD_TYPE = new WoodTypeBuilder().register(ColorfulAzaleas.id("colorful_azaleas"), BLOCK_SET_TYPE);

        public final Block log;
        public final Block wood;
        public final Block stripped_log;
        public final Block stripped_wood;
        public final Block planks;
        public final Block stairs;
        public final Block slab;
        public final Block fence;
        public final Block fence_gate;
        public final Block door;
        public final Block trapdoor;
        public final Block pressure_plate;
        public final Block button;

        public WoodSet(AzaleaColors color) {
            this.name = color.title;

            this.log = registerBlockWithItem(name + "_azalea_log", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).setId(blockKey(name + "_azalea_log"))));
            this.wood = registerBlockWithItem(name + "_azalea_wood", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).setId(blockKey(name + "_azalea_wood"))));
            this.stripped_log = registerBlockWithItem("stripped_" + name + "_azalea_log", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).setId(blockKey("stripped_" + name + "_azalea_log"))));
            this.stripped_wood = registerBlockWithItem("stripped_" + name + "_azalea_wood", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).setId(blockKey("stripped_" + name + "_azalea_wood"))));
            this.planks = registerBlockWithItem(name + "_azalea_planks", new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(blockKey(name + "_azalea_planks"))));
            this.stairs = registerBlockWithItem(name + "_azalea_stairs", new StairBlock(planks.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(planks).setId(blockKey(name + "_azalea_stairs"))));
            this.slab = registerBlockWithItem(name + "_azalea_slab", new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).setId(blockKey(name + "_azalea_slab"))));
            this.fence = registerBlockWithItem(name + "_azalea_fence", new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).setId(blockKey(name + "_azalea_fence"))));
            this.fence_gate = registerBlockWithItem(name + "_azalea_fence_gate", new FenceGateBlock(WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).setId(blockKey(name + "_azalea_fence_gate"))));
			this.door = registerBlockWithItem(name + "_azalea_door", new DoorBlock(BLOCK_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).setId(blockKey(name + "_azalea_door"))));
			this.trapdoor = registerBlockWithItem(name + "_azalea_trapdoor", new TrapDoorBlock(BLOCK_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).setId(blockKey(name + "_azalea_trapdoor"))));
			this.pressure_plate = registerBlockWithItem(name + "_azalea_pressure_plate", new PressurePlateBlock(BLOCK_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).setId(blockKey(name + "_azalea_pressure_plate"))));
            this.button = registerBlockWithItem(name + "_azalea_button", new ButtonBlock(BLOCK_SET_TYPE, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).setId(blockKey(name + "_azalea_button"))));

            StrippableBlockRegistry.register(log, stripped_log);
            StrippableBlockRegistry.register(wood, stripped_wood);
        }
    }
    
    public static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, ColorfulAzaleas.id(name));
    }
    
    public static Block registerBlockWithItem(String name, Block block) {
        ResourceLocation resourceLocation = ColorfulAzaleas.id(name);
        Registry.register(BuiltInRegistries.BLOCK, resourceLocation, block);
        
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, resourceLocation);
        Registry.register(BuiltInRegistries.ITEM, resourceLocation, new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix()));
        return block;
    }

    public static Block registerBlock(String name, Block block) {
        ResourceLocation resourceLocation = ColorfulAzaleas.id(name);
        Registry.register(BuiltInRegistries.BLOCK, resourceLocation, block);
        return block;
    }
}