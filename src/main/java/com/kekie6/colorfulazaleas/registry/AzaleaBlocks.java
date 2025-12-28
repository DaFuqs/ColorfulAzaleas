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
        tree.setAzaleaLeaves(registerBlockWithItem(name + "_azalea_leaves", (Function<BlockBehaviour.Properties, Block>) properties -> new UntintedParticleLeavesBlock(0.01F, colorParticleOption, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES)));
        tree.setFloweringLeaves(registerBlockWithItem(name + "_flowering_azalea_leaves", (Function<BlockBehaviour.Properties, Block>) properties -> new UntintedParticleLeavesBlock(0.01F, colorParticleOption, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES)));
        tree.setBloomingLeaves(registerBlockWithItem(name + "_blooming_azalea_leaves", (Function<BlockBehaviour.Properties, Block>) properties -> new UntintedParticleLeavesBlock(0.01F, colorParticleOption, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).requiresCorrectToolForDrops()));
        tree.setDroopingLeaves(registerBlockWithItem(name + "_drooping_azalea_leaves", (Function<BlockBehaviour.Properties, Block>) properties -> new DroopingLeavesBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).noCollision().sound(SoundType.CAVE_VINES)));

        // --- Sapling ---
        ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, ColorfulAzaleas.id(name));
        TreeGrower treeGrower = new TreeGrower(ColorfulAzaleas.MOD_ID + ":" + name + "_azalea", Optional.empty(), Optional.of(configuredFeatureKey), Optional.empty());
        tree.setSapling(registerBlockWithItem(name + "_azalea_sapling", (Function<BlockBehaviour.Properties, Block>) properties -> new ColorfulAzaleaBushBlock(treeGrower, properties), BlockBehaviour.Properties.ofFullCopy(AZALEA).noOcclusion()));
        tree.setPottedSapling(registerBlock("potted_" + name + "_azalea_sapling",  (Function<BlockBehaviour.Properties, Block>) properties -> new FlowerPotBlock(() -> (FlowerPotBlock) FLOWER_POT, () -> tree.getSapling().get(), properties), flowerPotProperties()));

        // --- Wood Set ---
        AzaleaWoodSet woodSet = new AzaleaWoodSet(title);
        woodSet.setLog(registerBlockWithItem(title + "_azalea_log", (Function<BlockBehaviour.Properties, Block>) properties -> new RotatedPillarBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
        woodSet.setWood(registerBlockWithItem(title + "_azalea_wood", (Function<BlockBehaviour.Properties, Block>) properties -> new RotatedPillarBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
        woodSet.setStrippedLog(registerBlockWithItem("stripped_" + title + "_azalea_log", (Function<BlockBehaviour.Properties, Block>) properties -> new RotatedPillarBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
        woodSet.setStrippedWood(registerBlockWithItem("stripped_" + title + "_azalea_wood", (Function<BlockBehaviour.Properties, Block>) properties -> new RotatedPillarBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
        woodSet.setPlanks(registerBlockWithItem(title + "_azalea_planks",  (Function<BlockBehaviour.Properties, Block>) properties -> new Block(properties), BlockBehaviour.Properties.ofFullCopy(OAK_PLANKS).setId(blockKey(title + "_azalea_planks"))));
        woodSet.setStairs(registerBlockWithItem(title + "_azalea_stairs",  (Function<BlockBehaviour.Properties, Block>) properties -> new StairBlock(tree.getWoodSet().getPlanks().get().defaultBlockState(),properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));
        woodSet.setSlab(registerBlockWithItem(title + "_azalea_slab",  (Function<BlockBehaviour.Properties, Block>) properties -> new SlabBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
        woodSet.setFence(registerBlockWithItem(title + "_azalea_fence",  (Function<BlockBehaviour.Properties, Block>) properties -> new FenceBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));
        woodSet.setFenceGate(registerBlockWithItem(title + "_azalea_fence_gate", (Function<BlockBehaviour.Properties, Block>) properties -> new FenceGateBlock(woodType, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
        woodSet.setDoor(registerBlockWithItem(title + "_azalea_door", (Function<BlockBehaviour.Properties, Block>) properties -> new DoorBlock(BLOCK_SET_TYPE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));
        woodSet.setTrapdoor(registerBlockWithItem(title + "_azalea_trapdoor", (Function<BlockBehaviour.Properties, Block>) properties -> new TrapDoorBlock(BLOCK_SET_TYPE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
        woodSet.setPressurePlate(registerBlockWithItem(title + "_azalea_pressure_plate", (Function<BlockBehaviour.Properties, Block>) properties -> new PressurePlateBlock(BLOCK_SET_TYPE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
        woodSet.setButton(registerBlockWithItem(title + "_azalea_button", (Function<BlockBehaviour.Properties, Block>) properties -> new ButtonBlock(BLOCK_SET_TYPE, 30, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));

        // --- Shelf Block & Item ---
        woodSet.setShelf(registerBlockWithItem(title + "_azalea_shelf", (Function<BlockBehaviour.Properties, Block>) properties -> new ShelfBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SHELF)));

        // --- Sign Blocks & Items ---
        woodSet.setSign(registerBlock(title + "_azalea_sign", s -> new StandingSignBlock(woodType, s), signBlockSettings(title + "_azalea_sign", Blocks.OAK_SIGN)));
        woodSet.setWallSign(registerBlock(title + "_azalea_wall_sign", s -> new WallSignBlock(woodType, s), signBlockSettings(title + "_azalea_wall_sign", Blocks.OAK_WALL_SIGN)));
        woodSet.setHangingSign(registerBlock(title + "_azalea_hanging_sign", s -> new CeilingHangingSignBlock(woodType, s), signBlockSettings(title + "_azalea_hanging_sign", Blocks.OAK_HANGING_SIGN)));
        woodSet.setWallHangingSign(registerBlock(title + "_azalea_wall_hanging_sign", s -> new WallHangingSignBlock(woodType, s), signBlockSettings(title + "_azalea_wall_hanging_sign", Blocks.OAK_WALL_HANGING_SIGN)));

        registerSignItems(woodSet, title);

        tree.setWoodSet(woodSet);
        return tree;
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

    public static<T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Function<Block.Properties, T> blockFactory, Block.Properties settings) {
        DeferredBlock<T> b = BLOCKS.register(name, identifier -> blockFactory.apply(settings.setId(ResourceKey.create(Registries.BLOCK, identifier))));
        AzaleaItems.register(name, properties -> new BlockItem(b.get(), properties), new Item.Properties().useBlockDescriptionPrefix());
        return b;
    }
    
    public static<T extends Block> DeferredBlock<T> registerBlock(String name, Function<Block.Properties, T> blockFactory, Block.Properties settings) {
		return BLOCKS.register(name, identifier -> blockFactory.apply(settings.setId(ResourceKey.create(Registries.BLOCK, identifier))));
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