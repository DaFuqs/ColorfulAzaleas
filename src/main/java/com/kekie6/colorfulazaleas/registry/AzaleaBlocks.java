package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.blocks.*;
import net.fabricmc.fabric.api.loot.v3.*;
import net.fabricmc.fabric.api.object.builder.v1.block.type.*;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.block.*;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.*;
/*
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
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
*/



public class AzaleaBlocks {

    public static ColorfulTree[] trees;
    public static final Block DROOPING_AZALEA_LEAVES = registerBlockWithItem("drooping_azalea_leaves", new DroopingLeavesBlock(AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES).registryKey(blockKey("drooping_azalea_leaves")).noCollision().sounds(BlockSoundGroup.CAVE_VINES)));

    public static void init() {
        trees = new ColorfulTree[AzaleaColors.values().length];
        for (int i = 0; i < AzaleaColors.values().length; i++) {
            trees[i] = new ColorfulTree(AzaleaColors.values()[i]);
        }
    }

    public enum AzaleaColors {
        orange("tecal", 0xFFfd9919),
        yellow("fiss", 0xFFffbb3c),
        red("roze", 0xFFd93a2a),
        blue("azule", 0xFF2ae8e2),
        pink("bright", 0xFFfcb9d6),
        purple("walnut", 0xFFb844e9),
        white("titanium", 0xFFe8fafa);

        final String title;
        final int tint;

        AzaleaColors(String title, int tint) {
            this.title = title;
            this.tint = tint;
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
            this.azaleaLeaves = registerBlockWithItem(name + "_azalea_leaves", new UntintedParticleLeavesBlock(0.01F, EntityEffectParticleEffect.create(ParticleTypes.TINTED_LEAVES, color.tint), AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES).registryKey(blockKey("_azalea_leaves"))));
            this.floweringLeaves = registerBlockWithItem(name + "_flowering_azalea_leaves", new UntintedParticleLeavesBlock(0.01F, EntityEffectParticleEffect.create(ParticleTypes.TINTED_LEAVES, color.tint), AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES).registryKey(blockKey("_flowering_azalea_leaves"))));
            this.bloomingLeaves = registerBlockWithItem(name + "_blooming_azalea_leaves", new UntintedParticleLeavesBlock(0.01F, EntityEffectParticleEffect.create(ParticleTypes.TINTED_LEAVES, color.tint), AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES).requiresTool().registryKey(blockKey("_blooming_azalea_leaves"))));
            this.droopingLeaves = registerBlockWithItem(name + "_drooping_azalea_leaves", new DroopingLeavesBlock(AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES).noCollision().sounds(BlockSoundGroup.CAVE_VINES).registryKey(blockKey("_drooping_azalea_leaves"))));
            //RegistryKey<ConfiguredFeature
            RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureKey = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ColorfulAzaleas.id(name));
            SaplingGenerator treeGrower = new SaplingGenerator(ColorfulAzaleas.MOD_ID + ":" + name + "_azalea", Optional.empty(), Optional.of(configuredFeatureKey), Optional.empty());
            this.sapling = registerBlockWithItem(name + "_azalea_sapling", new ColorfulAzaleaBushBlock(treeGrower, AbstractBlock.Settings.copy(Blocks.AZALEA).nonOpaque().registryKey(blockKey(name + "_azalea_sapling"))));
			this.pottedSapling = registerBlock("potted_" + name + "_azalea_sapling", new FlowerPotBlock(this.sapling, AbstractBlock.Settings.copy(Blocks.POTTED_AZALEA_BUSH).registryKey(blockKey("potted_" + name + "_azalea_sapling"))));

			addBlockToAzaleaLootTable(sapling);
			CompostingChanceRegistry.INSTANCE.add(sapling, 0.65F);
		}

    }

    public static void addBlockToAzaleaLootTable(Block block) {
        LootTableEvents.MODIFY.register((key, builder, lootTableSource, provider) -> {
			if (Blocks.AZALEA_LEAVES.getLootTableKey().get().equals(key)) {
				LootPool.Builder poolBuilder1 = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(0.01f))
						.with(ItemEntry.builder(block));
				builder.pool(poolBuilder1.build());
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

            this.log = registerBlockWithItem(name + "_azalea_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).registryKey(blockKey(name + "_azalea_log"))));
            this.wood = registerBlockWithItem(name + "_azalea_wood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).registryKey(blockKey(name + "_azalea_wood"))));
            this.stripped_log = registerBlockWithItem("stripped_" + name + "_azalea_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG).registryKey(blockKey("stripped_" + name + "_azalea_log"))));
            this.stripped_wood = registerBlockWithItem("stripped_" + name + "_azalea_wood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD).registryKey(blockKey("stripped_" + name + "_azalea_wood"))));
            this.planks = registerBlockWithItem(name + "_azalea_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).registryKey(blockKey(name + "_azalea_planks"))));
            this.stairs = registerBlockWithItem(name + "_azalea_stairs", new StairsBlock(planks.getDefaultState(), AbstractBlock.Settings.copy(planks).registryKey(blockKey(name + "_azalea_stairs"))));
            this.slab = registerBlockWithItem(name + "_azalea_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).registryKey(blockKey(name + "_azalea_slab"))));
            this.fence = registerBlockWithItem(name + "_azalea_fence", new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE).registryKey(blockKey(name + "_azalea_fence"))));
            this.fence_gate = registerBlockWithItem(name + "_azalea_fence_gate", new FenceGateBlock(WOOD_TYPE, AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE).registryKey(blockKey(name + "_azalea_fence_gate"))));
			this.door = registerBlockWithItem(name + "_azalea_door", new DoorBlock(BLOCK_SET_TYPE, AbstractBlock.Settings.copy(Blocks.OAK_DOOR).registryKey(blockKey(name + "_azalea_door"))));
			this.trapdoor = registerBlockWithItem(name + "_azalea_trapdoor", new TrapdoorBlock(BLOCK_SET_TYPE, AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR).registryKey(blockKey(name + "_azalea_trapdoor"))));
			this.pressure_plate = registerBlockWithItem(name + "_azalea_pressure_plate", new PressurePlateBlock(BLOCK_SET_TYPE, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE).registryKey(blockKey(name + "_azalea_pressure_plate"))));
            this.button = registerBlockWithItem(name + "_azalea_button", new ButtonBlock(BLOCK_SET_TYPE, 30, AbstractBlock.Settings.copy(Blocks.OAK_BUTTON).registryKey(blockKey(name + "_azalea_button"))));

            StrippableBlockRegistry.register(log, stripped_log);
            StrippableBlockRegistry.register(wood, stripped_wood);
        }
    }
    
    public static RegistryKey<Block> blockKey(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, ColorfulAzaleas.id(name));
    }
    
    public static Block registerBlockWithItem(String name, Block block) {
        Identifier resourceLocation = ColorfulAzaleas.id(name);
        Registry.register(Registries.BLOCK, resourceLocation, block);
        
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, resourceLocation);
        Registry.register(Registries.ITEM, resourceLocation, new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey()));
        return block;
    }

    public static Block registerBlock(String name, Block block) {
        Identifier resourceLocation = ColorfulAzaleas.id(name);
        Registry.register(Registries.BLOCK, resourceLocation, block);
        return block;
    }
}