package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.block.*;
import com.kekie6.colorfulazaleas.util.*;
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
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.*;
import java.util.function.*;

import static net.minecraft.world.level.block.Blocks.*;

public class AzaleaBlocks {
    
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ColorfulAzaleas.MOD_ID);
    
    public static ColorfulTree[] trees = Arrays.stream(AzaleaColors.values())
            .map(AzaleaBlocks::createTree)
            .toArray(ColorfulTree[]::new);

    private static final BlockSetType BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType("colorful_azaleas"));
    private static final Map<String, WoodType> WOOD_TYPES = new HashMap<>();
    private static final WoodType WOOD_TYPE = WoodType.register(new WoodType(ColorfulAzaleas.id("colorful_azaleas").toString(), BLOCK_SET_TYPE));
    public static final List<Block> SHELF_BLOCKS = new ArrayList<>();
    
    public static final Block DROOPING_AZALEA_LEAVES = registerBlockWithItem(
            "drooping_azalea_leaves",
            new DroopingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).setId(blockKey("drooping_azalea_leaves")).noCollision().sound(SoundType.CAVE_VINES))
    );
    
    public static void init(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    private static ColorfulTree createTree(AzaleaColors color) {
        String title = color.getTitle(); // e.g. "titanium"
        String name = color.name().toLowerCase(Locale.ROOT); // e.g. "WHITE"

        ColorfulTree tree = new ColorfulTree(color);

        // --- Creates a unique wood type for this tree ---
        WoodType woodType = getOrCreateWoodType(title);

        // --- Leaves ---
        Block leaves = new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, color.getTint()), BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).setId(blockKey(name + "_azalea_leaves")));
        tree.setAzaleaLeaves(registerBlockWithItem(name + "_azalea_leaves", leaves));
        UntintedParticleLeavesBlock floweringLeaves = new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, color.getTint()), BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).setId(blockKey(name + "_flowering_azalea_leaves")));
        tree.setFloweringLeaves(registerBlockWithItem(name + "_flowering_azalea_leaves", floweringLeaves));
        UntintedParticleLeavesBlock bloomingLeaves = new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, color.getTint()), BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).requiresCorrectToolForDrops().setId(blockKey(name + "_blooming_azalea_leaves")));
        tree.setBloomingLeaves(registerBlockWithItem(name + "_blooming_azalea_leaves", bloomingLeaves));
        DroopingLeavesBlock droopingLeaves = new DroopingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA_LEAVES).noCollision().sound(SoundType.CAVE_VINES).setId(blockKey(name + "_drooping_azalea_leaves")));
        tree.setDroopingLeaves(registerBlockWithItem(name + "_drooping_azalea_leaves", droopingLeaves));

        // --- Sapling ---
        ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, ColorfulAzaleas.id(name));
        TreeGrower treeGrower = new TreeGrower(ColorfulAzaleas.MOD_ID + ":" + name + "_azalea", Optional.empty(), Optional.of(configuredFeatureKey), Optional.empty());
        Block sapling = registerBlockWithItem(name + "_azalea_sapling", new ColorfulAzaleaBushBlock(treeGrower, BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA).noOcclusion().setId(blockKey(name + "_azalea_sapling"))));
        tree.setSapling(sapling);
        tree.setPottedSapling(registerBlock("potted_" + name + "_azalea_sapling", new FlowerPotBlock(() -> (FlowerPotBlock) FLOWER_POT, () -> sapling, flowerPotProperties().setId(blockKey("potted_" + name + "_azalea_sapling")))));

        // --- Wood Set ---
        WoodSet woodSet = new WoodSet(title);
        woodSet.setLog(registerBlockWithItem(title + "_azalea_log", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).setId(blockKey(title + "_azalea_log")))));
        woodSet.setWood(registerBlockWithItem(title + "_azalea_wood", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).setId(blockKey(title + "_azalea_wood")))));
        woodSet.setStrippedLog(registerBlockWithItem("stripped_" + title + "_azalea_log", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).setId(blockKey("stripped_" + title + "_azalea_log")))));
        woodSet.setStrippedWood(registerBlockWithItem("stripped_" + title + "_azalea_wood", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).setId(blockKey("stripped_" + title + "_azalea_wood")))));
        woodSet.setPlanks(registerBlockWithItem(title + "_azalea_planks", new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).setId(blockKey(title + "_azalea_planks")))));
        woodSet.setStairs(registerBlockWithItem(title + "_azalea_stairs", new StairBlock(woodSet.getPlanks().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).setId(blockKey(title + "_azalea_stairs")))));
        woodSet.setSlab(registerBlockWithItem(title + "_azalea_slab", new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).setId(blockKey(title + "_azalea_slab")))));
        woodSet.setFence(registerBlockWithItem(title + "_azalea_fence", new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).setId(blockKey(title + "_azalea_fence")))));
        woodSet.setFenceGate(registerBlockWithItem(title + "_azalea_fence_gate", new FenceGateBlock(WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).setId(blockKey(title + "_azalea_fence_gate")))));
        woodSet.setDoor(registerBlockWithItem(title + "_azalea_door", new DoorBlock(BLOCK_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).setId(blockKey(title + "_azalea_door")))));
        woodSet.setTrapdoor(registerBlockWithItem(title + "_azalea_trapdoor", new TrapDoorBlock(BLOCK_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).setId(blockKey(title + "_azalea_trapdoor")))));
        woodSet.setPressurePlate(registerBlockWithItem(title + "_azalea_pressure_plate", new PressurePlateBlock(BLOCK_SET_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).setId(blockKey(title + "_azalea_pressure_plate")))));
        woodSet.setButton(registerBlockWithItem(title + "_azalea_button", new ButtonBlock(BLOCK_SET_TYPE, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).setId(blockKey(title + "_azalea_button")))));

        // --- Shelf Block & Item ---
        ShelfBlock shelf = new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SHELF).setId(blockKey(title + "_azalea_shelf")));
        registerBlockWithItem(title + "_azalea_shelf", shelf);
        SHELF_BLOCKS.add(shelf);
        woodSet.setShelf(shelf);

        // --- Sign Blocks & Items ---
        woodSet.setSign(AzaleaSignHelper.registerSignBlock(ColorfulAzaleas.id(title + "_azalea_sign"), s -> new StandingSignBlock(woodType, s), blockSettings(title + "_azalea_sign", Blocks.OAK_SIGN)));
        woodSet.setWallSign(AzaleaSignHelper.registerSignBlock(ColorfulAzaleas.id(title + "_azalea_wall_sign"), s -> new WallSignBlock(woodType, s), blockSettings(title + "_azalea_wall_sign", Blocks.OAK_WALL_SIGN)));
        woodSet.setHangingSign(AzaleaSignHelper.registerSignBlock(ColorfulAzaleas.id(title + "_azalea_hanging_sign"), s -> new CeilingHangingSignBlock(woodType, s), blockSettings(title + "_azalea_hanging_sign", Blocks.OAK_HANGING_SIGN)));
        woodSet.setWallHangingSign(AzaleaSignHelper.registerSignBlock(ColorfulAzaleas.id(title + "_azalea_wall_hanging_sign"), s -> new WallHangingSignBlock(woodType, s), blockSettings(title + "_azalea_wall_hanging_sign", Blocks.OAK_WALL_HANGING_SIGN)));

        AzaleaSignHelper.registerSignItems(woodSet, title);

        tree.setWoodSet(woodSet);
        return tree;
    }

    public static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, ColorfulAzaleas.id(name));
    }

    public static Block registerBlockWithItem(String name, Block block) {
        Identifier id = ColorfulAzaleas.id(name);
        Registry.register(BuiltInRegistries.BLOCK, id, block);

        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
        Registry.register(BuiltInRegistries.ITEM, id,
                new BlockItem(block, new net.minecraft.world.item.Item.Properties()
                        .setId(itemKey)
                        .useBlockDescriptionPrefix()));
        return block;
    }

    public static Block registerBlock(String name, Block block) {
        Identifier id = ColorfulAzaleas.id(name);
        Registry.register(BuiltInRegistries.BLOCK, id, block);
        return block;
    }

    private static WoodType getOrCreateWoodType(String title) {
        // Normalize (Just in case)
        title = title.toLowerCase(Locale.ROOT);

        // Cache lookup (Important so signs use same instance across registration)
        if (WOOD_TYPES.containsKey(title)) {
            return WOOD_TYPES.get(title);
        }

        Identifier id = ColorfulAzaleas.id(title + "_azalea");
        WoodType woodType = WoodType.register(new WoodType(id.toString(), BLOCK_SET_TYPE));
        WOOD_TYPES.put(title, woodType);

        return woodType;
    }

    private static BlockBehaviour.Properties blockSettings(String name, Block base) {
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