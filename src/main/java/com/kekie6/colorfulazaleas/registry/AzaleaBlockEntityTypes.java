package com.kekie6.colorfulazaleas.registry;


import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.entities.AzaleaShelfBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AzaleaBlockEntityTypes {
    public static BlockEntityType<AzaleaShelfBlockEntity> AZALEA_SHELF_ENTITY;

            public static void init() {
                AZALEA_SHELF_ENTITY = register(
                    "azalea_shelf",
                    AzaleaShelfBlockEntity::new,
                    AzaleaBlocks.SHELF_BLOCKS.toArray(new Block[0])
            );}

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                ColorfulAzaleas.id(name),
                FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}