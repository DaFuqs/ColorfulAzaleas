package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.blocks.DroopingLeavesBlock;
import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.block.Block;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {
        ColorfulAzaleas.LOGGER.info("Generating BlockState models for " + ColorfulAzaleas.MOD_ID);

        registerDroopingLeavesVariants(gen, AzaleaBlocks.DROOPING_AZALEA_LEAVES);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            var woodSet = tree.getWoodSet();

            // --- Leaves ---
            // If the leaves are cross/tinted crosses and the default texture naming matches,
            // the simple helper is fine. If you need a custom mapping (like cube_bottom_top)
            // use the upload approach shown in registerCubeBottomTop below.
            gen.registerSingleton(tree.getAzaleaLeaves(), TexturedModel.LEAVES);
            gen.registerSingleton(tree.getFloweringLeaves(), TexturedModel.LEAVES);
            registerBloomingAzaleaLeaves(gen, tree.getBloomingLeaves());

            // Example of a special-case leaf (blooming) that uses cube_bottom_top with a different top/side:
            //        registerCubeBottomTop(gen, tree.getBloomingLeaves(),
            //        TextureMap.getId(tree.getAzaleaLeaves()), // particle/bottom from base leaves
            //        TextureMap.getSubId(tree.getFloweringLeaves(), "_plant") /* or proper top id */,
            //        Identifier.of(ColorfulAzaleas.MOD_ID, "block/" + "blue_blooming_azalea_leaves") // side
            // );

            // If blooming leaves use cube_bottom_top and require custom mappings, call registerCubeBottomTop (example above).
            // If not, the cross-based method above will typically work for "leaf-like" assets.

            // --- DROOPING LEAVES: register short + tall cross models mapped to EXTENDED ---
            registerDroopingLeavesVariants(gen, tree.getDroopingLeaves());

            // --- Sapling + potted sapling ---
            // registerAzalea uploads the standard azalea template (template_azalea)
            gen.registerAzalea(tree.getSapling());

            // For potted azalea we need a custom texture map that references the non-"potted_" side/top textures:
            registerPottedAzaleaWithUnpottedSideTop(gen, tree.getPottedSapling());

            // --- Wood set (logs/planks/... ) ---
            // Logs and stripped logs
            gen.createLogTexturePool(woodSet.getLog())
                    .log(woodSet.getLog())
                    .wood(woodSet.getWood());

            gen.createLogTexturePool(woodSet.getStrippedLog())
                    .log(woodSet.getStrippedLog())
                    .wood(woodSet.getStrippedWood());

            // Planks -> create a texture pool and derive stairs/slab/etc.
            BlockStateModelGenerator.BlockTexturePool plankPool = gen.registerCubeAllModelTexturePool(woodSet.getPlanks());
            plankPool.stairs(woodSet.getStairs());
            plankPool.slab(woodSet.getSlab());
            plankPool.fence(woodSet.getFence());
            plankPool.fenceGate(woodSet.getFenceGate());

            gen.registerDoor(woodSet.getDoor());
            gen.registerTrapdoor(woodSet.getTrapdoor());
            gen.registerHangingSign(woodSet.getStrippedLog(), woodSet.getHangingSign(), woodSet.getWallHangingSign());

            plankPool.pressurePlate(woodSet.getPressurePlate());
            plankPool.button(woodSet.getButton());
            registerSign(gen, woodSet.getSign(), woodSet.getWallSign());
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // We generally don't need to create GENERATED item models for blocks when the block model is uploaded and
        // a "parented" item model is created (see registerParentedItemModel usage below).
        ColorfulAzaleas.LOGGER.info("Generating Item models for " + ColorfulAzaleas.MOD_ID);

        itemModelGenerator.register(AzaleaBlocks.DROOPING_AZALEA_LEAVES.asItem(), Models.GENERATED);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();

            itemModelGenerator.register(tree.getDroopingLeaves().asItem(), Models.GENERATED);
            itemModelGenerator.register(woodSet.getBoatItem(), Models.GENERATED);
            itemModelGenerator.register(woodSet.getChestBoatItem(), Models.GENERATED);
        }
    }
    /**
     * Special handling for potted azalea bushes: we want:
     *  - "plant" to remain the potted plant texture (textureMap.getSubId(block,"_plant"))
     *  - "side" and "top" to reference the unpotted variants (i.e. remove the "potted_" prefix from the block id)
     * This avoids having to duplicate PNGs: we reuse the same "blue_azalea_sapling_side" and "..._top".
     */
    private static void registerPottedAzaleaWithUnpottedSideTop(BlockStateModelGenerator gen, Block pottedBlock) {
        // Block registry id: e.g. "colorfulazaleas:potted_blue_azalea_sapling"
        Identifier blockId = Registries.BLOCK.getId(pottedBlock);
        String namespace = blockId.getNamespace();
        String path = blockId.getPath(); // e.g. "potted_blue_azalea_sapling"

        // remove leading "potted_" if present
        String unpottedPath = path.replaceFirst("^potted_", "");

        // textures need to be full paths like "block/<path>" because TextureMap expects that form
        Identifier plantTex = TextureMap.getSubId(pottedBlock, "_plant"); // still "block/<potted>_plant"
        Identifier sideTex = Identifier.of(namespace, "block/" + unpottedPath + "_side");
        Identifier topTex  = Identifier.of(namespace, "block/" + unpottedPath + "_top");

        TextureMap textures = new TextureMap()
                .put(TextureKey.PLANT, plantTex)
                .put(TextureKey.SIDE, sideTex)
                .put(TextureKey.TOP, topTex);

        // Upload using the potted azalea template model
        Identifier modelId = Models.TEMPLATE_POTTED_AZALEA_BUSH.upload(pottedBlock, textures, gen.modelCollector);

        // Register blockstate
        gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(pottedBlock, BlockStateModelGenerator.createWeightedVariant(modelId)));
    }

    private static void registerBloomingAzaleaLeaves(BlockStateModelGenerator gen, Block bloomingLeaves) {
        Identifier blockId = Registries.BLOCK.getId(bloomingLeaves);
        String namespace = blockId.getNamespace();
        String path = blockId.getPath();
        String color = path.replace("_blooming_azalea_leaves", ""); // e.g. "blue"

        Identifier bottomTex   = Identifier.of(namespace, "block/" + color + "_azalea_leaves");
        Identifier topTex      = Identifier.of(namespace, "block/" + color + "_flowering_azalea_leaves");
        Identifier sideTex     = Identifier.of(namespace, "block/" + color + "_blooming_azalea_leaves");

        TextureMap textures = new TextureMap()
                .put(TextureKey.PARTICLE, bottomTex)
                .put(TextureKey.BOTTOM, bottomTex)
                .put(TextureKey.TOP, topTex)
                .put(TextureKey.SIDE, sideTex);

        Identifier modelId = Models.CUBE_BOTTOM_TOP.upload(bloomingLeaves, textures, gen.modelCollector);

        gen.blockStateCollector.accept(
                BlockStateModelGenerator.createSingletonBlockState(
                        bloomingLeaves,
                        BlockStateModelGenerator.createWeightedVariant(modelId)
                )
        );
    }
    /**
     * Uploads two 'cross' models (short & tall) and registers a variant map keyed by DroopingLeavesBlock.EXTENDED.
     * IMPORTANT: the second upload uses a variant suffix ("_tall") so the model id is unique.
     */
    private static void registerDroopingLeavesVariants(BlockStateModelGenerator gen, Block droopingBlock) {
        // Computes texture identifiers (block/namespace/path[_tall])
        Identifier blockId = Registries.BLOCK.getId(droopingBlock);
        String namespace = blockId.getNamespace();
        String path = blockId.getPath(); // e.g. "blue_drooping_azalea_leaves"

        Identifier shortTex = Identifier.of(namespace, "block/" + path);
        Identifier tallTex  = Identifier.of(namespace, "block/" + path + "_tall");

        // Texture maps for cross model (cross + particle)
        TextureMap shortMap = new TextureMap().put(TextureKey.CROSS, shortTex).put(TextureKey.PARTICLE, shortTex);
        TextureMap tallMap  = new TextureMap().put(TextureKey.CROSS, tallTex).put(TextureKey.PARTICLE, tallTex);

        // Upload short model -> models/block/<path>.json
        Identifier shortModel = Models.CROSS.upload(droopingBlock, shortMap, gen.modelCollector);

        // Upload tall model using variant suffix -> models/block/<path>_tall.json
        // NOTE: upload overload accepting a variant string is used here to create a distinct filename.
        Identifier tallModel = Models.CROSS.upload(droopingBlock, "_tall", tallMap, gen.modelCollector);

        // Create WeightedVariants
        WeightedVariant shortVariant = BlockStateModelGenerator.createWeightedVariant(shortModel);
        WeightedVariant tallVariant  = BlockStateModelGenerator.createWeightedVariant(tallModel);

        // Map the boolean property EXTENDED -> short/tall
        BlockStateVariantMap.SingleProperty<WeightedVariant, Boolean> map =
                BlockStateVariantMap.models(DroopingLeavesBlock.EXTENDED);

        map.register(false, shortVariant);
        map.register(true, tallVariant);

        // Accept the variant mapping as the block state definition
        gen.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(droopingBlock).with(map));

        // (Optional) If you want the item form to use the short model, register a parented item model.
        // gen.registerParentedItemModel(droopingBlock, shortModel);
        // But avoid registering an item alias that would collide with others.
    }

    private static void registerSign(BlockStateModelGenerator gen, Block standingSign, Block wallSign) {
        Identifier signTexture = Identifier.of(Registries.BLOCK.getId(standingSign).getNamespace(),
                "item/" + Registries.BLOCK.getId(standingSign).getPath());
        // Make a texture map for the sign — usually you just point particle to the log/planks texture
        TextureMap textures = TextureMap.of(TextureKey.PARTICLE, signTexture);

        // Upload particle model for the sign
        WeightedVariant variant = BlockStateModelGenerator.createWeightedVariant(
                Models.PARTICLE.upload(standingSign, textures, gen.modelCollector)
        );

        // Register blockstates for both standing + wall sign
        gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(standingSign, variant));
        gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(wallSign, variant));

        // Register the item model
        gen.registerItemModel(standingSign.asItem());
    }
}
// Below is more experimenting with textures.
/*
// Ensure the item model points to the block model
        gen.registerParentedItemModel(bloomingLeaves, modelId);
*/

/*

private void registerBloomingLeaves(BlockStateModelGenerator gen, Block block) {
    // Build your custom texture map
    TextureMap textures = new TextureMap()
            .put(TextureKey.PARTICLE, Identifier.of("colorfulazaleas", "block/blue_azalea_leaves"))
            .put(TextureKey.BOTTOM, Identifier.of("colorfulazaleas", "block/blue_azalea_leaves"))
            .put(TextureKey.TOP, Identifier.of("colorfulazaleas", "block/blue_flowering_azalea_leaves"))
            .put(TextureKey.SIDE, Identifier.of("colorfulazaleas", "block/blue_blooming_azalea_leaves"));

    // Upload the model manually
    Identifier modelId = Models.CUBE_BOTTOM_TOP.upload(block, textures, gen.modelCollector);

    // Hook that model into the blockstate JSON
    WeightedVariant variant = BlockStateModelGenerator.createSingletonVariant(modelId);
    gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, variant));

    // Also add an item model that just points at the block model
    gen.registerParentedItemModel(block, modelId);
}*/
/*

*/
/**
 * Uploads a cube_bottom_top model for `block` using the explicit texture identifiers,
 * registers the blockstate and registers a parented item model pointing at that model.
 *//*

private static void registerCubeBottomTop(BlockStateModelGenerator gen, Block block, Identifier particleOrBottom, Identifier top, Identifier side) {
    TextureMap textures = new TextureMap()
            .put(TextureKey.PARTICLE, particleOrBottom)
            .put(TextureKey.BOTTOM, particleOrBottom)
            .put(TextureKey.TOP, top)
            .put(TextureKey.SIDE, side);

    // upload the model, get an Identifier back
    Identifier modelId = Models.CUBE_BOTTOM_TOP.upload(block, textures, gen.modelCollector);

    // register blockstate using that model
    gen.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, BlockStateModelGenerator.createWeightedVariant(modelId)));
}
*/
