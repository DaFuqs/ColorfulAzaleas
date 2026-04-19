package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.block.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import org.jspecify.annotations.*;

public class ModModelProvider extends ModelProvider {
    
    public ModModelProvider(PackOutput output) {
        super(output, ColorfulAzaleas.MOD_ID);
    }
    
    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        // Generate models and associated files here
        generateBlockStateModels(blockModels);
        generateItemModels(itemModels);
    }
    
    public void generateBlockStateModels(@NonNull BlockModelGenerators gen) {
        ColorfulAzaleas.LOGGER.info("Generating BlockState models for " + ColorfulAzaleas.MOD_ID);

        registerDroopingLeavesVariants(gen, AzaleaBlocks.DROOPING_AZALEA_LEAVES.get());

        for (ColorfulTree tree : AzaleaBlocks.TREES) {
            var woodSet = tree.getWoodSet();

            // --- Leaves ---
            // If the leaves are cross/tinted crosses and the default texture naming matches,
            // the simple helper is fine. If you need a custom mapping (like cube_bottom_top)
            // use the upload approach shown in registerCubeBottomTop below.
            gen.createTrivialBlock(tree.getAzaleaLeaves().get(), TexturedModel.LEAVES);
            gen.createTrivialBlock(tree.getFloweringLeaves().get(), TexturedModel.LEAVES);
            registerBloomingAzaleaLeaves(gen, tree.getBloomingLeaves().get());

            // Example of a special-case leaf (blooming) that uses cube_bottom_top with a different top/side:
            //        registerCubeBottomTop(gen, tree.getBloomingLeaves(),
            //        TextureMap.getId(tree.getAzaleaLeaves()), // particle/bottom from base leaves
            //        TextureMap.getSubId(tree.getFloweringLeaves(), "_plant") /* or proper top id */,
            //        Identifier.of(ColorfulAzaleas.MOD_ID, "block/" + "blue_blooming_azalea_leaves") // side
            // );

            // If blooming leaves use cube_bottom_top and require custom mappings, call registerCubeBottomTop (example above).
            // If not, the cross-based method above will typically work for "leaf-like" assets.

            // --- Drooping Leaves: register short + tall cross models mapped to EXTENDED ---
            registerDroopingLeavesVariants(gen, tree.getDroopingLeaves().get());

            // --- Sapling + Potted Sapling ---
            // registerAzalea uploads the standard azalea template (template_azalea)
            gen.createAzalea(tree.getSapling().get());

            // For potted azalea we need a custom texture map that references the non-"potted_" side/top textures:
            gen.createPlantWithDefaultItem(tree.getSapling().get(), tree.getPottedSapling().get(), BlockModelGenerators.PlantType.NOT_TINTED);

            // --- Wood Set (Logs/Planks/etc. ) ---
            // --- Logs and Stripped Logs ---
            gen.woodProvider(woodSet.getLog().get())
                    .logWithHorizontal(woodSet.getLog().get())
                    .wood(woodSet.getWood().get());

            gen.woodProvider(woodSet.getStrippedLog().get())
                    .logWithHorizontal(woodSet.getStrippedLog().get())
                    .wood(woodSet.getStrippedWood().get());

            // Planks -> create a texture pool and derive stairs/slab/etc.
            BlockModelGenerators.BlockFamilyProvider plankPool = gen.family(woodSet.getPlanks().get());
            plankPool.stairs(woodSet.getStairs().get());
            plankPool.slab(woodSet.getSlab().get());
            plankPool.fence(woodSet.getFence().get());
            plankPool.fenceGate(woodSet.getFenceGate().get());
            plankPool.pressurePlate(woodSet.getPressurePlate().get());
            plankPool.button(woodSet.getButton().get());
            // --- Door, Trapdoor, Sign, HangingSign ---
            gen.createDoor(woodSet.getDoor().get());
            gen.createOrientableTrapdoor(woodSet.getTrapdoor().get());
            registerSign(gen, woodSet.getSign().get(), woodSet.getWallSign().get());
            gen.createHangingSign(woodSet.getStrippedLog().get(), woodSet.getHangingSign().get(), woodSet.getWallHangingSign().get());
            // --- Shelf ---
            gen.createShelf(woodSet.getShelf().get(), woodSet.getStrippedLog().get());
        }
    }

    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        // We generally don't need to create GENERATED item models for block when the block model is uploaded and
        // a "parented" item model is created (see registerParentedItemModel usage below).
        ColorfulAzaleas.LOGGER.info("Generating Item models for " + ColorfulAzaleas.MOD_ID);

        itemModelGenerator.generateFlatItem(AzaleaItems.ICON_ITEM.get(), ModelTemplates.FLAT_ITEM);
        registerDroopingItem(itemModelGenerator, AzaleaBlocks.DROOPING_AZALEA_LEAVES.get());
        
        for (ColorfulTree tree : AzaleaBlocks.TREES) {
            AzaleaWoodSet woodSet = tree.getWoodSet();
            
            registerDroopingItem(itemModelGenerator, tree.getDroopingLeaves().get());
            itemModelGenerator.generateFlatItem(woodSet.getBoatItem().get(), ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(woodSet.getChestBoatItem().get(), ModelTemplates.FLAT_ITEM);
        }
    }

    private static void registerDroopingItem(ItemModelGenerators itemModelGenerator, Block droopingBlock) {
        Identifier id = ModelTemplates.FLAT_ITEM.create(droopingBlock.asItem(), TextureMapping.layer0(droopingBlock), itemModelGenerator.modelOutput);
        itemModelGenerator.itemModelOutput.accept(droopingBlock.asItem(), ItemModelUtils.plainModel(id));
    }

    private static void registerBloomingAzaleaLeaves(BlockModelGenerators gen, Block bloomingLeaves) {
        Identifier blockId = BuiltInRegistries.BLOCK.getKey(bloomingLeaves);
        String namespace = blockId.getNamespace();
        String path = blockId.getPath();
        String color = path.replace("_blooming_azalea_leaves", ""); // e.g. "blue"

        Material bottomTex = new Material(Identifier.fromNamespaceAndPath(namespace, "block/" + color + "_azalea_leaves"));
        Material topTex    = new Material(Identifier.fromNamespaceAndPath(namespace, "block/" + color + "_flowering_azalea_leaves"));
        Material sideTex   = new Material(Identifier.fromNamespaceAndPath(namespace, "block/" + color + "_blooming_azalea_leaves"));

        TextureMapping textures = new TextureMapping()
                .put(TextureSlot.PARTICLE, bottomTex)
                .put(TextureSlot.BOTTOM, bottomTex)
                .put(TextureSlot.TOP, topTex)
                .put(TextureSlot.SIDE, sideTex);

        Identifier modelId = ModelTemplates.CUBE_BOTTOM_TOP.create(bloomingLeaves, textures, gen.modelOutput);

        gen.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(
                        bloomingLeaves,
                        BlockModelGenerators.plainVariant(modelId)
                )
        );
    }

    private static void registerDroopingLeavesVariants(BlockModelGenerators gen, Block droopingBlock) {
        Identifier blockId = BuiltInRegistries.BLOCK.getKey(droopingBlock);
        String namespace = blockId.getNamespace();
        String path = blockId.getPath();

        Material shortTex = new Material(Identifier.fromNamespaceAndPath(namespace, "block/" + path));
        Material tallTex  = new Material(Identifier.fromNamespaceAndPath(namespace, "block/" + path + "_tall"));

        PropertyDispatch.C1<MultiVariant, Boolean> map = PropertyDispatch.initial(DroopingLeavesBlock.EXTENDED);
        map.select(false, BlockModelGenerators.plainVariant(ModelTemplates.CROSS.create(droopingBlock, new TextureMapping().put(TextureSlot.CROSS, shortTex).put(TextureSlot.PARTICLE, shortTex), gen.modelOutput)));
        map.select(true, BlockModelGenerators.plainVariant(ModelTemplates.CROSS.createWithSuffix(droopingBlock, "_tall", new TextureMapping().put(TextureSlot.CROSS, tallTex).put(TextureSlot.PARTICLE, tallTex), gen.modelOutput)));

        gen.blockStateOutput.accept(MultiVariantGenerator.dispatch(droopingBlock).with(map));
    }

    private static void registerSign(BlockModelGenerators gen, Block standingSign, Block wallSign) {
        Material signTexture = TextureMapping.getBlockTexture(standingSign);
        TextureMapping textures = TextureMapping.singleSlot(TextureSlot.PARTICLE, signTexture);
        MultiVariant variant = BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(standingSign, textures, gen.modelOutput));
        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(standingSign, variant));
        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(wallSign, variant));
        gen.registerSimpleFlatItemModel(standingSign.asItem());
    }
}