package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.block.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.fabricmc.fabric.api.client.datagen.v1.provider.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import org.jspecify.annotations.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockModelGenerators gen) {
        ColorfulAzaleas.LOGGER.info("Generating BlockState models for " + ColorfulAzaleas.MOD_ID);

        registerDroopingLeavesVariants(gen, AzaleaBlocks.DROOPING_AZALEA_LEAVES);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            var woodSet = tree.getWoodSet();

            // --- Leaves ---
            // If the leaves are cross/tinted crosses and the default texture naming matches,
            // the simple helper is fine. If you need a custom mapping (like cube_bottom_top)
            // use the upload approach shown in registerCubeBottomTop below.
            gen.createTrivialBlock(tree.getAzaleaLeaves(), TexturedModel.LEAVES);
            gen.createTrivialBlock(tree.getFloweringLeaves(), TexturedModel.LEAVES);
            registerBloomingAzaleaLeaves(gen, tree.getBloomingLeaves());

            // Example of a special-case leaf (blooming) that uses cube_bottom_top with a different top/side:
            //        registerCubeBottomTop(gen, tree.getBloomingLeaves(),
            //        TextureMap.getId(tree.getAzaleaLeaves()), // particle/bottom from base leaves
            //        TextureMap.getSubId(tree.getFloweringLeaves(), "_plant") /* or proper top id */,
            //        Identifier.of(ColorfulAzaleas.MOD_ID, "block/" + "blue_blooming_azalea_leaves") // side
            // );

            // If blooming leaves use cube_bottom_top and require custom mappings, call registerCubeBottomTop (example above).
            // If not, the cross-based method above will typically work for "leaf-like" assets.

            // --- Drooping Leaves: register short + tall cross models mapped to EXTENDED ---
            registerDroopingLeavesVariants(gen, tree.getDroopingLeaves());

            // --- Sapling + Potted Sapling ---
            // registerAzalea uploads the standard azalea template (template_azalea)
            gen.createAzalea(tree.getSapling());

            // For potted azalea we need a custom texture map that references the non-"potted_" side/top textures:
            gen.createPottedAzalea(tree.getPottedSapling());

            // --- Wood Set (Logs/Planks/etc. ) ---
            // --- Logs and Stripped Logs ---
            gen.woodProvider(woodSet.getLog())
                    .logWithHorizontal(woodSet.getLog())
                    .wood(woodSet.getWood());

            gen.woodProvider(woodSet.getStrippedLog())
                    .logWithHorizontal(woodSet.getStrippedLog())
                    .wood(woodSet.getStrippedWood());

            // Planks -> create a texture pool and derive stairs/slab/etc.
            BlockModelGenerators.BlockFamilyProvider plankPool = gen.family(woodSet.getPlanks());
            plankPool.stairs(woodSet.getStairs());
            plankPool.slab(woodSet.getSlab());
            plankPool.fence(woodSet.getFence());
            plankPool.fenceGate(woodSet.getFenceGate());
            plankPool.pressurePlate(woodSet.getPressurePlate());
            plankPool.button(woodSet.getButton());
            // --- Door, Trapdoor, Sign, HangingSign ---
            gen.createDoor(woodSet.getDoor());
            gen.createOrientableTrapdoor(woodSet.getTrapdoor());
            registerSign(gen, woodSet.getSign(), woodSet.getWallSign());
            gen.createHangingSign(woodSet.getStrippedLog(), woodSet.getHangingSign(), woodSet.getWallHangingSign());
            // --- Shelf ---
            gen.createShelf(woodSet.getShelf(), woodSet.getStrippedLog());
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        // We generally don't need to create GENERATED item models for block when the block model is uploaded and
        // a "parented" item model is created (see registerParentedItemModel usage below).
        ColorfulAzaleas.LOGGER.info("Generating Item models for " + ColorfulAzaleas.MOD_ID);

        itemModelGenerator.generateFlatItem(AzaleaItems.ICON_ITEM, ModelTemplates.FLAT_ITEM);
        registerDroopingItem(itemModelGenerator, AzaleaBlocks.DROOPING_AZALEA_LEAVES);
        
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            
            registerDroopingItem(itemModelGenerator, tree.getDroopingLeaves());
            itemModelGenerator.generateFlatItem(woodSet.getBoatItem(), ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(woodSet.getChestBoatItem(), ModelTemplates.FLAT_ITEM);
        }
    }

    private static void registerDroopingItem(ItemModelGenerators itemModelGenerator, Block droopingBlock) {
        Identifier id = ModelTemplates.FLAT_ITEM.create(
                droopingBlock.asItem(),
                TextureMapping.layer0(droopingBlock),
                itemModelGenerator.modelOutput
        );

        itemModelGenerator.itemModelOutput.accept(
                droopingBlock.asItem(),
                ItemModelUtils.plainModel(id)
        );
    }

    private static void registerBloomingAzaleaLeaves(BlockModelGenerators gen, Block bloomingLeaves) {

        Identifier blockId = BuiltInRegistries.BLOCK.getKey(bloomingLeaves);
        String namespace = blockId.getNamespace();
        String path = blockId.getPath();
        String color = path.replace("_blooming_azalea_leaves", "");

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
    // Uploads two 'cross' models (short & tall) and registers a variant map keyed by DroopingLeavesBlock.EXTENDED.
    // IMPORTANT: the second upload uses a variant suffix ("_tall") so the model id is unique.
    private static void registerDroopingLeavesVariants(BlockModelGenerators gen, Block droopingBlock) {

        Material shortTex = TextureMapping.getBlockTexture(droopingBlock);
        Material tallTex  = TextureMapping.getBlockTexture(droopingBlock, "_tall");

        PropertyDispatch.C1<MultiVariant, Boolean> map = PropertyDispatch.initial(DroopingLeavesBlock.EXTENDED);
        map.select(false, BlockModelGenerators.plainVariant(ModelTemplates.CROSS.create(droopingBlock, new TextureMapping().put(TextureSlot.CROSS, shortTex).put(TextureSlot.PARTICLE, shortTex), gen.modelOutput)));
        map.select(true, BlockModelGenerators.plainVariant(ModelTemplates.CROSS.createWithSuffix(droopingBlock, "_tall", new TextureMapping().put(TextureSlot.CROSS, tallTex).put(TextureSlot.PARTICLE, tallTex), gen.modelOutput)));

        gen.blockStateOutput.accept(MultiVariantGenerator.dispatch(droopingBlock).with(map));
    }

    private static void registerSign(BlockModelGenerators gen, Block standingSign, Block wallSign) {
        Material signTexture = TextureMapping.getBlockTexture(standingSign);

        // Make a texture map for the sign — usually you just point particle to the log/planks texture
        TextureMapping textures = TextureMapping.singleSlot(TextureSlot.PARTICLE, signTexture);

        // Upload particle model for the sign
        MultiVariant variant = BlockModelGenerators.plainVariant(
                ModelTemplates.PARTICLE_ONLY.create(standingSign, textures, gen.modelOutput)
        );

        // Register blockstates for both standing + wall sign
        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(standingSign, variant));
        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(wallSign, variant));

        // Register the item model
        gen.registerSimpleFlatItemModel(standingSign.asItem());
    }
}