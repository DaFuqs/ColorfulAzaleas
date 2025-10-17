package com.kekie6.colorfulazaleas.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShelfBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShelfBlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public abstract class AzaleaShelfInit implements ModInitializer {
/*
    public static Block AZULE_AZALEA_SHELF;
    public static Block ROSE_AZALEA_SHELF;
    public static Block TEAL_AZALEA_SHELF;

    public static BlockEntityType<AzaleaShelfBlockEntity> AZALEA_SHELF_ENTITY;

    @Override
    public void onInitialize() {
        // Register custom shelf blocks
        AZULE_AZALEA_SHELF = registerShelfBlock("azule_azalea_shelf");
        ROSE_AZALEA_SHELF = registerShelfBlock("rose_azalea_shelf");
        TEAL_AZALEA_SHELF = registerShelfBlock("teal_azalea_shelf");

        // ✅ Use Fabric's BlockEntityType builder (since Minecraft’s is now locked down)
        AZALEA_SHELF_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of("colorfulazaleas", "azalea_shelf"),
                FabricBlockEntityTypeBuilder
                        .create(AzaleaShelfBlockEntity::new, AZULE_AZALEA_SHELF, ROSE_AZALEA_SHELF, TEAL_AZALEA_SHELF)
                        .build()
        );

        System.out.println("[ColorfulAzaleas] ✅ Shelf entity and blocks registered successfully for 1.21.9!");
    }

    private static Block registerShelfBlock(String name) {
        Identifier id = Identifier.of("colorfulazaleas", name);
        Block block = new AzaleaShelfBlock(AbstractBlock.Settings.create()
                .strength(2.0F)
                .sounds(BlockSoundGroup.WOOD)
                .nonOpaque()
        );
        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
        return block;
    }

    public static class AzaleaShelfBlock extends ShelfBlock {
        public AzaleaShelfBlock(AbstractBlock.Settings settings) {
            super(settings);
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new AzaleaShelfBlockEntity(pos, state);
        }

        public boolean hasBlockEntity() {
            return true;
        }
    }

    public static class AzaleaShelfBlockEntity extends ShelfBlockEntity {
        public AzaleaShelfBlockEntity(BlockPos pos, BlockState state) {
            super(pos, state);
        }

        @Override
        public BlockEntityType<?> getType() {
            return AZALEA_SHELF_ENTITY;
        }
    }*/
}
