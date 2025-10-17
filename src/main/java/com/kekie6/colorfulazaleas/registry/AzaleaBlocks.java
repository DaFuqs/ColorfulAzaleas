package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.blocks.ColorfulAzaleaBushBlock;
import com.kekie6.colorfulazaleas.blocks.DroopingLeavesBlock;
import com.kekie6.colorfulazaleas.blocks.AzaleaShelfBlock;
import com.kekie6.colorfulazaleas.util.AzaleaColors;
import com.kekie6.colorfulazaleas.util.AzaleaSignHelper;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.UntintedParticleLeavesBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
//import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.TintedParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.*;

import static net.minecraft.block.Blocks.createFlowerPotSettings;

public class AzaleaBlocks {

    public static ColorfulTree[] trees;

    public static final Block DROOPING_AZALEA_LEAVES =
            registerBlockWithItem("drooping_azalea_leaves",
                    new DroopingLeavesBlock(AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES)
                            .registryKey(blockKey("drooping_azalea_leaves"))
                            .noCollision()
                            .sounds(BlockSoundGroup.CAVE_VINES)));

    private static final BlockSetType BLOCK_SET_TYPE =
            BlockSetTypeBuilder.copyOf(BlockSetType.ACACIA)
                    .register(ColorfulAzaleas.id("colorful_azaleas"));

    private static final Map<String, WoodType> WOOD_TYPES = new HashMap<>();

    private static final WoodType WOOD_TYPE =
            new WoodTypeBuilder().register(ColorfulAzaleas.id("colorful_azaleas"), BLOCK_SET_TYPE);

    public static final List<Block> SHELF_BLOCKS = new ArrayList<>();

    public static void init() {
        trees = Arrays.stream(AzaleaColors.values())
                .map(AzaleaBlocks::createTree)
                .toArray(ColorfulTree[]::new);
    }

    private static ColorfulTree createTree(AzaleaColors color) {
        String title = color.getTitle(); // e.g. "titanium"
        String name = color.name().toLowerCase(Locale.ROOT); // e.g. "WHITE"

        ColorfulTree tree = new ColorfulTree(color);

        // --- Creates a unique wood type for this tree ---
        WoodType woodType = getOrCreateWoodType(title);

        // --- Leaves ---
        tree.setAzaleaLeaves(registerBlockWithItem(
                name + "_azalea_leaves",
                new UntintedParticleLeavesBlock(
                        0.01F,
                        TintedParticleEffect.create(ParticleTypes.TINTED_LEAVES, color.getTint()),
                        AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES)
                                .registryKey(blockKey(name + "_azalea_leaves"))
                )
        ));

        tree.setFloweringLeaves(registerBlockWithItem(
                name + "_flowering_azalea_leaves",
                new UntintedParticleLeavesBlock(
                        0.01F,
                        TintedParticleEffect.create(ParticleTypes.TINTED_LEAVES, color.getTint()),
                        AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES)
                                .registryKey(blockKey(name + "_flowering_azalea_leaves"))
                )
        ));

        tree.setBloomingLeaves(registerBlockWithItem(
                name + "_blooming_azalea_leaves",
                new UntintedParticleLeavesBlock(
                        0.01F,
                        TintedParticleEffect.create(ParticleTypes.TINTED_LEAVES, color.getTint()),
                        AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES)
                                .requiresTool()
                                .registryKey(blockKey(name + "_blooming_azalea_leaves"))
                )
        ));

        tree.setDroopingLeaves(registerBlockWithItem(
                name + "_drooping_azalea_leaves",
                new DroopingLeavesBlock(
                        AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES)
                                .noCollision()
                                .sounds(BlockSoundGroup.CAVE_VINES)
                                .registryKey(blockKey(name + "_drooping_azalea_leaves"))
                )
        ));

        // --- Sapling ---
        RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureKey =
                RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ColorfulAzaleas.id(name));

        SaplingGenerator treeGrower = new SaplingGenerator(
                ColorfulAzaleas.MOD_ID + ":" + name + "_azalea",
                Optional.empty(),
                Optional.of(configuredFeatureKey),
                Optional.empty()
        );

        Block sapling = registerBlockWithItem(
                name + "_azalea_sapling",
                new ColorfulAzaleaBushBlock(
                        treeGrower,
                        AbstractBlock.Settings.copy(Blocks.AZALEA)
                                .nonOpaque()
                                .registryKey(blockKey(name + "_azalea_sapling"))
                )
        );
        tree.setSapling(sapling);

        tree.setPottedSapling(registerBlock(
                "potted_" + name + "_azalea_sapling",
                new FlowerPotBlock(
                        sapling,
                        createFlowerPotSettings()
                                .registryKey(blockKey("potted_" + name + "_azalea_sapling"))
                )
        ));

        addBlockToAzaleaLootTable(sapling);
        CompostingChanceRegistry.INSTANCE.add(sapling, 0.65F);

        // --- Wood Set ---
        WoodSet woodSet = new WoodSet(title);

        woodSet.setLog(registerBlockWithItem(title + "_azalea_log",
                new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)
                        .registryKey(blockKey(title + "_azalea_log")))));

        woodSet.setWood(registerBlockWithItem(title + "_azalea_wood",
                new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)
                        .registryKey(blockKey(title + "_azalea_wood")))));

        woodSet.setStrippedLog(registerBlockWithItem("stripped_" + title + "_azalea_log",
                new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG)
                        .registryKey(blockKey("stripped_" + title + "_azalea_log")))));

        woodSet.setStrippedWood(registerBlockWithItem("stripped_" + title + "_azalea_wood",
                new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)
                        .registryKey(blockKey("stripped_" + title + "_azalea_wood")))));

        woodSet.setPlanks(registerBlockWithItem(title + "_azalea_planks",
                new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                        .registryKey(blockKey(title + "_azalea_planks")))));

        woodSet.setStairs(registerBlockWithItem(title + "_azalea_stairs",
                new StairsBlock(woodSet.getPlanks().getDefaultState(),
                        AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)
                                .registryKey(blockKey(title + "_azalea_stairs")))));

        woodSet.setSlab(registerBlockWithItem(title + "_azalea_slab",
                new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)
                        .registryKey(blockKey(title + "_azalea_slab")))));

        woodSet.setFence(registerBlockWithItem(title + "_azalea_fence",
                new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)
                        .registryKey(blockKey(title + "_azalea_fence")))));

        woodSet.setFenceGate(registerBlockWithItem(title + "_azalea_fence_gate",
                new FenceGateBlock(WOOD_TYPE,
                        AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE)
                                .registryKey(blockKey(title + "_azalea_fence_gate")))));

        woodSet.setDoor(registerBlockWithItem(title + "_azalea_door",
                new DoorBlock(BLOCK_SET_TYPE,
                        AbstractBlock.Settings.copy(Blocks.OAK_DOOR)
                                .registryKey(blockKey(title + "_azalea_door")))));

        woodSet.setTrapdoor(registerBlockWithItem(title + "_azalea_trapdoor",
                new TrapdoorBlock(BLOCK_SET_TYPE,
                        AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)
                                .registryKey(blockKey(title + "_azalea_trapdoor")))));

        woodSet.setPressurePlate(registerBlockWithItem(title + "_azalea_pressure_plate",
                new PressurePlateBlock(BLOCK_SET_TYPE,
                        AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)
                                .registryKey(blockKey(title + "_azalea_pressure_plate")))));

        woodSet.setButton(registerBlockWithItem(title + "_azalea_button",
                new ButtonBlock(BLOCK_SET_TYPE, 30,
                        AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)
                                .registryKey(blockKey(title + "_azalea_button")))));

        AzaleaShelfBlock shelf = new AzaleaShelfBlock(
                AbstractBlock.Settings.copy(Blocks.OAK_SHELF)
                        .registryKey(blockKey(title + "_azalea_shelf"))
        );
        registerBlockWithItem(title + "_azalea_shelf", shelf);
        SHELF_BLOCKS.add(shelf);
        woodSet.setShelf(shelf);
/*
        woodSet.setShelf(registerBlockWithItem(title + "_azalea_shelf",
                new AzaleaShelfBlock(AbstractBlock.Settings.copy(Blocks.OAK_SHELF)
                        .registryKey(blockKey(title + "_azalea_shelf")))));
*/

        // --- Sign Blocks & Items ---
        woodSet.setSign(AzaleaSignHelper.registerSignBlock(
                ColorfulAzaleas.id(title + "_azalea_sign"),
                s -> new SignBlock(woodType, s),
                blockSettings(title + "_azalea_sign", Blocks.OAK_SIGN)
        ));


        woodSet.setWallSign(AzaleaSignHelper.registerSignBlock(
                ColorfulAzaleas.id(title + "_azalea_wall_sign"),
                s -> new WallSignBlock(woodType, s),
                blockSettings(title + "_azalea_wall_sign", Blocks.OAK_WALL_SIGN)
        ));

        woodSet.setHangingSign(AzaleaSignHelper.registerSignBlock(
                ColorfulAzaleas.id(title + "_azalea_hanging_sign"),
                s -> new HangingSignBlock(woodType, s),
                //AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN).registryKey(AzaleaBlocks.blockKey(title + "_azalea_hanging_sign"))
                blockSettings(title + "_azalea_hanging_sign", Blocks.OAK_HANGING_SIGN)
        ));

        woodSet.setWallHangingSign(AzaleaSignHelper.registerSignBlock(
                ColorfulAzaleas.id(title + "_azalea_wall_hanging_sign"),
                s -> new WallHangingSignBlock(woodType, s),
                //AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN).registryKey(AzaleaBlocks.blockKey(title + "_azalea_wall_hanging_sign"))
                blockSettings(title + "_azalea_wall_hanging_sign", Blocks.OAK_WALL_HANGING_SIGN)
        ));

        AzaleaSignHelper.registerSignItems(woodSet, title);

        // Debugging hanging signs
        // System.out.println("Registering hanging sign: " + title + " -> " + woodSet.getHangingSign());

        // Debugging signs (Identifies their texture path) e.g. "Why is my sign black and purple?"
/*
        System.out.println("Registered sign: " + title + "_azalea_sign using wood type: " + woodType.name());
        System.out.println("Expected sign texture: " + TexturedRenderLayers.getSignTextureId(woodType));

        System.out.println("Registered hanging sign: " + title + "_azalea_hanging_sign using wood type: " + woodType.name());
        System.out.println("Expected hanging sign texture: " + TexturedRenderLayers.getHangingSignTextureId(woodType));
*/
        // --- Strippable Wood ---
        StrippableBlockRegistry.register(woodSet.getLog(), woodSet.getStrippedLog());
        StrippableBlockRegistry.register(woodSet.getWood(), woodSet.getStrippedWood());

        tree.setWoodSet(woodSet);

        return tree;
    }

    public static void addBlockToAzaleaLootTable(Block block) {
        LootTableEvents.MODIFY.register((key, builder, lootTableSource, provider) -> {
            if (Blocks.AZALEA_LEAVES.getLootTableKey().get().equals(key)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.01f))
                        .with(ItemEntry.builder(block));
                builder.pool(poolBuilder.build());
            }
        });
    }

    public static RegistryKey<Block> blockKey(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, ColorfulAzaleas.id(name));
    }

    public static Block registerBlockWithItem(String name, Block block) {
        Identifier id = ColorfulAzaleas.id(name);
        Registry.register(Registries.BLOCK, id, block);

        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
        Registry.register(Registries.ITEM, id,
                new BlockItem(block, new Item.Settings()
                        .registryKey(itemKey)
                        .useBlockPrefixedTranslationKey()));
        return block;
    }

    public static Block registerBlock(String name, Block block) {
        Identifier id = ColorfulAzaleas.id(name);
        Registry.register(Registries.BLOCK, id, block);
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
        WoodType woodType = new WoodTypeBuilder().register(id, BLOCK_SET_TYPE);
        WOOD_TYPES.put(title, woodType);

        return woodType;
    }

    private static AbstractBlock.Settings blockSettings(String name, Block base) {
        // Normalize name so wall variants use the same loot table as their base block
        String baseName = name
                .replace("_wall_hanging_sign", "_hanging_sign")
                .replace("_wall_sign", "_sign");

        RegistryKey<Block> blockKey = blockKey(name);

        Identifier lootTableId = ColorfulAzaleas.id("blocks/" + baseName);
        RegistryKey<LootTable> lootKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, lootTableId);
        // Debugging to identify block loot tables
        // Identifier id = ColorfulAzaleas.id(name);
        // System.out.println("[blockSettings Debug]: Block -> " + id);
        // System.out.println("[blockSettings Debug]: Loot table -> " + lootKey);
        // System.out.println("[blockSettings Debug]: Base name -> " + baseName);
        // System.out.println("[blockSettings Debug]: Base block -> " + base);

        return AbstractBlock.Settings.copy(base)
                .registryKey(blockKey)
                .lootTable(Optional.of(lootKey))
                .overrideTranslationKey("block." + ColorfulAzaleas.MOD_ID + "." + name);
    }
}
/* Template - might delete later
private static final WoodType TECAL_WOOD_TYPE =
        new WoodTypeBuilder().register(ColorfulAzaleas.id("tecal_azalea"), BLOCK_SET_TYPE);
*/

/*    private static AbstractBlock.Settings blockSettings(String name, Block base) {
        Identifier id = ColorfulAzaleas.id(name);
        RegistryKey<LootTable> lootKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, id.withPrefixedPath("blocks/"));
        System.out.println("[blockSettings Debug]: " + lootKey);
        System.out.println("[blockSettings Debug]: " + name);
        System.out.println("[blockSettings Debug]: " + base);
        return AbstractBlock.Settings.copy(base)
                .registryKey(blockKey(name))
                .lootTable(Optional.of(lootKey))
                .overrideTranslationKey("block." + ColorfulAzaleas.MOD_ID + "." + name);
    }*/