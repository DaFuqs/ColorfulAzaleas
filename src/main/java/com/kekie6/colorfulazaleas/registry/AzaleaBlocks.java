package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.block.*;
import com.kekie6.colorfulazaleas.util.*;
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
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.*;
import java.util.function.*;

import static net.minecraft.world.level.block.Blocks.*;

public class AzaleaBlocks {
    
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ColorfulAzaleas.MOD_ID);
    
    private static final BlockSetType BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType("colorful_azaleas"));
    public static ColorfulTree[] TREES = Arrays.stream(AzaleaColor.values())
            .map(AzaleaBlocks::createTree)
            .toArray(ColorfulTree[]::new);

    
    public static final DeferredBlock<?> DROOPING_AZALEA_LEAVES = registerBlockWithItem(
            "drooping_azalea_leaves",
            DroopingLeavesBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).setId(blockKey("drooping_azalea_leaves")).noCollision().sound(SoundType.CAVE_VINES)
    );
    
    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    private static ColorfulTree createTree(AzaleaColor color) {
        String title = color.getTitle(); // e.g. "titanium"
        String name = color.name().toLowerCase(Locale.ROOT); // e.g. "WHITE"

        ColorfulTree tree = new ColorfulTree(color);
        
        Identifier id = ColorfulAzaleas.id(title + "_azalea");
        WoodType woodType = WoodType.register(new WoodType(id.toString(), BLOCK_SET_TYPE));

        // --- Leaves ---
        ColorParticleOption colorParticleOption = ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, color.getTint());
        tree.setAzaleaLeaves(registerBlockWithItem(name + "_azalea_leaves", new UntintedParticleLeavesBlock(0.01F, colorParticleOption, BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).setId(blockKey(name + "_azalea_leaves")))));
        tree.setFloweringLeaves(registerBlockWithItem(name + "_flowering_azalea_leaves", new UntintedParticleLeavesBlock(0.01F, colorParticleOption, BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).setId(blockKey(name + "_flowering_azalea_leaves")))));
        tree.setBloomingLeaves(registerBlockWithItem(name + "_blooming_azalea_leaves", new UntintedParticleLeavesBlock(0.01F, colorParticleOption, BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).requiresCorrectToolForDrops().setId(blockKey(name + "_blooming_azalea_leaves")))));
        tree.setDroopingLeaves(registerBlockWithItem(name + "_drooping_azalea_leaves", new DroopingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).noCollision().sound(SoundType.CAVE_VINES).setId(blockKey(name + "_drooping_azalea_leaves")))));

        // --- Sapling ---
        ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, ColorfulAzaleas.id(name));
        TreeGrower treeGrower = new TreeGrower(ColorfulAzaleas.MOD_ID + ":" + name + "_azalea", Optional.empty(), Optional.of(configuredFeatureKey), Optional.empty());
        ColorfulAzaleaBushBlock sapling = new ColorfulAzaleaBushBlock(treeGrower, BlockBehaviour.Properties.ofFullCopy(AZALEA).noOcclusion().setId(blockKey(name + "_azalea_sapling")));
        tree.setSapling(registerBlockWithItem(name + "_azalea_sapling", sapling));
        tree.setPottedSapling(registerBlock("potted_" + name + "_azalea_sapling", new FlowerPotBlock(() -> (FlowerPotBlock) FLOWER_POT, () -> sapling, flowerPotProperties().setId(blockKey("potted_" + name + "_azalea_sapling")))));

        // --- Wood Set ---
        AzaleaWoodSet woodSet = new AzaleaWoodSet(title);
        woodSet.setLog(registerBlockWithItem(title + "_azalea_log", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).setId(blockKey(title + "_azalea_log")))));
        woodSet.setWood(registerBlockWithItem(title + "_azalea_wood", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).setId(blockKey(title + "_azalea_wood")))));
        woodSet.setStrippedLog(registerBlockWithItem("stripped_" + title + "_azalea_log", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).setId(blockKey("stripped_" + title + "_azalea_log")))));
        woodSet.setStrippedWood(registerBlockWithItem("stripped_" + title + "_azalea_wood", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).setId(blockKey("stripped_" + title + "_azalea_wood")))));
        Block planks = new Block(BlockBehaviour.Properties.ofFullCopy(OAK_PLANKS).setId(blockKey(title + "_azalea_planks")));
        woodSet.setPlanks(registerBlockWithItem(title + "_azalea_planks", planks));
        woodSet.setStairs(registerBlockWithItem(title + "_azalea_stairs", new StairBlock(planks.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).setId(blockKey(title + "_azalea_stairs")))));
        woodSet.setSlab(registerBlockWithItem(title + "_azalea_slab", new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).setId(blockKey(title + "_azalea_slab")))));
        woodSet.setFence(registerBlockWithItem(title + "_azalea_fence", new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).setId(blockKey(title + "_azalea_fence")))));
        woodSet.setFenceGate(registerBlockWithItem(title + "_azalea_fence_gate", new FenceGateBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).setId(blockKey(title + "_azalea_fence_gate")))));
        woodSet.setDoor(registerBlockWithItem(title + "_azalea_door", new DoorBlock(BLOCK_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).setId(blockKey(title + "_azalea_door")))));
        woodSet.setTrapdoor(registerBlockWithItem(title + "_azalea_trapdoor", new TrapDoorBlock(BLOCK_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).setId(blockKey(title + "_azalea_trapdoor")))));
        woodSet.setPressurePlate(registerBlockWithItem(title + "_azalea_pressure_plate", new PressurePlateBlock(BLOCK_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).setId(blockKey(title + "_azalea_pressure_plate")))));
        woodSet.setButton(registerBlockWithItem(title + "_azalea_button", new ButtonBlock(BLOCK_SET_TYPE, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).setId(blockKey(title + "_azalea_button")))));

        // --- Shelf Block & Item ---
        ShelfBlock shelf = new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SHELF).setId(blockKey(title + "_azalea_shelf")));
        woodSet.setShelf(registerBlockWithItem(title + "_azalea_shelf", shelf));

        // --- Sign Blocks & Items ---
        woodSet.setSign(registerSignBlock(ColorfulAzaleas.id(title + "_azalea_sign"), s -> new StandingSignBlock(woodType, s), signBlockSettings(title + "_azalea_sign", Blocks.OAK_SIGN)));
        woodSet.setWallSign(registerSignBlock(ColorfulAzaleas.id(title + "_azalea_wall_sign"), s -> new WallSignBlock(woodType, s), signBlockSettings(title + "_azalea_wall_sign", Blocks.OAK_WALL_SIGN)));
        woodSet.setHangingSign(registerSignBlock(ColorfulAzaleas.id(title + "_azalea_hanging_sign"), s -> new CeilingHangingSignBlock(woodType, s), signBlockSettings(title + "_azalea_hanging_sign", Blocks.OAK_HANGING_SIGN)));
        woodSet.setWallHangingSign(registerSignBlock(ColorfulAzaleas.id(title + "_azalea_wall_hanging_sign"), s -> new WallHangingSignBlock(woodType, s), signBlockSettings(title + "_azalea_wall_hanging_sign", Blocks.OAK_WALL_HANGING_SIGN)));

        registerSignItems(woodSet, title);

        tree.setWoodSet(woodSet);
        return tree;
    }
    
    // Create a registry key from an Identifier and register the sign block
    public static<T extends SignBlock> DeferredBlock<T> registerSignBlock(Identifier id, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties settings) {
        return registerBlock(id.getPath(), factory.apply(settings));
    }
    
    public static void registerSignItems(AzaleaWoodSet woodSet, String title) {
        ResourceKey<Item> signItemKey = ResourceKey.create(Registries.ITEM, ColorfulAzaleas.id(title + "_azalea_sign"));
        AzaleaItems.register(
                signItemKey.identifier().getPath(),
                (Function<Item.Properties, Item>) properties -> new SignItem(woodSet.getSign().get(), woodSet.getWallSign().get(), properties),
                new Item.Properties().stacksTo(16).setId(signItemKey).useBlockDescriptionPrefix());
        
        // Hanging sign item
        ResourceKey<Item> hangingSignItemKey = ResourceKey.create(Registries.ITEM, ColorfulAzaleas.id(title + "_azalea_hanging_sign"));
        AzaleaItems.register(
                hangingSignItemKey.identifier().getPath(),
                (Function<Item.Properties, Item>) properties -> new HangingSignItem(woodSet.getHangingSign().get(), woodSet.getWallHangingSign().get(), properties),
                new Item.Properties().stacksTo(16).setId(signItemKey).useBlockDescriptionPrefix());
    }

    public static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, ColorfulAzaleas.id(name));
    }
    
    public static<T extends Block> DeferredBlock<T> registerBlockWithItem(String name, T block) {
        DeferredBlock<T> b = BLOCKS.register(name, identifier -> block);
        AzaleaItems.register(name, properties -> new BlockItem(b.get(), properties), new Item.Properties().useBlockDescriptionPrefix());
        return b;
    }

    public static<T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Function<Block.Properties, T> blockFactory, Block.Properties settings) {
        DeferredBlock<T> b = BLOCKS.register(name, identifier -> blockFactory.apply(settings.setId(ResourceKey.create(Registries.BLOCK, identifier))));
        AzaleaItems.register(name, properties -> new BlockItem(b.get(), properties), new Item.Properties().useBlockDescriptionPrefix());
        return b;
    }
    
    public static<T extends Block> DeferredBlock<T> registerBlock(String name, T block) {
		return BLOCKS.register(name, identifier -> block);
    }
    
    private static BlockBehaviour.Properties signBlockSettings(String name, Block base) {
        // Normalize name so sign wall variants use the same loot table as their base block
        String baseName = name
                .replace("_wall_hanging_sign", "_hanging_sign")
                .replace("_wall_sign", "_sign");

        ResourceKey<Block> blockKey = blockKey(name);

        Identifier lootTableId = ColorfulAzaleas.id("blocks/" + baseName);
        ResourceKey<LootTable> lootKey = ResourceKey.create(Registries.LOOT_TABLE, lootTableId);

        return BlockBehaviour.Properties.ofFullCopy(base)
                .setId(blockKey)
                .overrideLootTable(Optional.of(lootKey))
                .overrideDescription("block." + ColorfulAzaleas.MOD_ID + "." + name);
    }
}