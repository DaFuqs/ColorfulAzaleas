package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.client.render.AzaleaShelfBlockEntityRenderer;
import com.kekie6.colorfulazaleas.registry.AzaleaBlockEntityTypes;
import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;

import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class ColorfulAzaleasClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorfulAzaleas.LOGGER.info("Generating Cutouts for " + ColorfulAzaleas.MOD_ID);
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();

            BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                    tree.getSapling(),
                    tree.getPottedSapling(),
                    tree.getFloweringLeaves(),
                    tree.getBloomingLeaves(),
                    tree.getAzaleaLeaves(),
                    tree.getDroopingLeaves()
            );

            BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                    woodSet.getDoor(),
                    woodSet.getTrapdoor()
            );

            TerraformBoatClientHelper.registerModelLayers(woodSet.getAzaleaBoatsId());
        }

        BlockRenderLayerMap.putBlock(AzaleaBlocks.DROOPING_AZALEA_LEAVES, BlockRenderLayer.CUTOUT);

        BlockEntityRendererFactories.register(AzaleaBlockEntityTypes.AZALEA_SHELF_ENTITY, AzaleaShelfBlockEntityRenderer::new);
    }
}


/*        EntityRendererRegistry.register(AzaleaBlockEntityTypes.AZULE_BOAT, context ->
                new BoatEntityRenderer(context, false));

        EntityRendererRegistry.register(AzaleaBlockEntityTypes.AZULE_CHEST_BOAT, context ->
                new BoatEntityRenderer(context, true));*/