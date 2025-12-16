package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.block.DroopingLeavesBlock;
import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import com.kekie6.colorfulazaleas.registry.AzaleaItems;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.*;
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

            // --- Drooping Leaves: register short + tall cross models mapped to EXTENDED ---
            registerDroopingLeavesVariants(gen, tree.getDroopingLeaves());

            // --- Sapling + Potted Sapling ---
            // registerAzalea uploads the standard azalea template (template_azalea)
            gen.registerAzalea(tree.getSapling());

            // For potted azalea we need a custom texture map that references the non-"potted_" side/top textures:
            registerPottedAzaleaWithUnpottedSideTop(gen, tree.getPottedSapling());

            // --- Wood Set (Logs/Planks/etc. ) ---
            // --- Logs and Stripped Logs ---
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
            plankPool.pressurePlate(woodSet.getPressurePlate());
            plankPool.button(woodSet.getButton());
            // --- Door, Trapdoor, Sign, HangingSign ---
            gen.registerDoor(woodSet.getDoor());
            gen.registerTrapdoor(woodSet.getTrapdoor());
            registerSign(gen, woodSet.getSign(), woodSet.getWallSign());
            gen.registerHangingSign(woodSet.getStrippedLog(), woodSet.getHangingSign(), woodSet.getWallHangingSign());
            // --- Shelf ---
            gen.registerShelf(woodSet.getShelf(), woodSet.getStrippedLog());
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // We generally don't need to create GENERATED item models for block when the block model is uploaded and
        // a "parented" item model is created (see registerParentedItemModel usage below).
        ColorfulAzaleas.LOGGER.info("Generating Item models for " + ColorfulAzaleas.MOD_ID);

        itemModelGenerator.register(AzaleaItems.ICON_ITEM, Models.GENERATED);
        registerDroopingItem(itemModelGenerator, AzaleaBlocks.DROOPING_AZALEA_LEAVES);
        
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            
            registerDroopingItem(itemModelGenerator, tree.getDroopingLeaves());
            itemModelGenerator.register(woodSet.getBoatItem(), Models.GENERATED);
            itemModelGenerator.register(woodSet.getChestBoatItem(), Models.GENERATED);
        }
    }
    
    private static void registerDroopingItem(ItemModelGenerator itemModelGenerator, Block droopingBlock) {
        Identifier blockId = Registries.BLOCK.getId(droopingBlock);
        Identifier id = Models.GENERATED.upload(droopingBlock.asItem(), TextureMap.layer0(Identifier.of(blockId.getNamespace(), "block/" + blockId.getPath())), itemModelGenerator.modelCollector);
        itemModelGenerator.output.accept(droopingBlock.asItem(), ItemModels.basic(id));
    }
    
    // Special handling for potted azalea bushes, we want:
     // - "plant" to remain the potted plant texture (textureMap.getSubId(block,"_plant"))
     // - "side" and "top" to reference the unpotted variants (i.e. remove the "potted_" prefix from the block id)
     // This avoids having to duplicate PNGs: we reuse the same "blue_azalea_sapling_side" and "..._top".
    private static void registerPottedAzaleaWithUnpottedSideTop(BlockStateModelGenerator gen, Block pottedBlock) {
        // Block registry id: e.g. "colorfulazaleas:potted_blue_azalea_sapling"
        Identifier blockId = Registries.BLOCK.getId(pottedBlock);
        String namespace = blockId.getNamespace();
        String path = blockId.getPath(); // e.g. "potted_blue_azalea_sapling"

        // Remove leading "potted_" if present
        String unpottedPath = path.replaceFirst("^potted_", "");

        // Textures need to be full paths like "block/<path>" because TextureMap expects that form
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
    // Uploads two 'cross' models (short & tall) and registers a variant map keyed by DroopingLeavesBlock.EXTENDED.
    // IMPORTANT: the second upload uses a variant suffix ("_tall") so the model id is unique.
    private static void registerDroopingLeavesVariants(BlockStateModelGenerator gen, Block droopingBlock) {
        Identifier blockId = Registries.BLOCK.getId(droopingBlock);
        String namespace = blockId.getNamespace();
        String path = blockId.getPath();

        Identifier shortTex = Identifier.of(namespace, "block/" + path);
        Identifier tallTex  = Identifier.of(namespace, "block/" + path + "_tall");

        BlockStateVariantMap.SingleProperty<WeightedVariant, Boolean> map = BlockStateVariantMap.models(DroopingLeavesBlock.EXTENDED);
        map.register(false, BlockStateModelGenerator.createWeightedVariant(Models.CROSS.upload(droopingBlock, new TextureMap().put(TextureKey.CROSS, shortTex).put(TextureKey.PARTICLE, shortTex), gen.modelCollector)));
        map.register(true, BlockStateModelGenerator.createWeightedVariant(Models.CROSS.upload(droopingBlock, "_tall", new TextureMap().put(TextureKey.CROSS, tallTex).put(TextureKey.PARTICLE, tallTex), gen.modelCollector)));

        gen.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(droopingBlock).with(map));
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