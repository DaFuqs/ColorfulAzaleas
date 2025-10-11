package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;

import net.minecraft.client.render.BlockRenderLayer;

public class ColorfulAzaleasClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorfulAzaleas.LOGGER.info("Generating Cutouts for " + ColorfulAzaleas.MOD_ID);
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                    tree.getSapling(),
                    tree.getPottedSapling(),
                    tree.getFloweringLeaves(),
                    tree.getBloomingLeaves(),
                    tree.getAzaleaLeaves(),
                    tree.getDroopingLeaves()
            );

            WoodSet woodSet = tree.getWoodSet();
            BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                    woodSet.getDoor(),
                    woodSet.getTrapdoor()
            );

            TerraformBoatClientHelper.registerModelLayers(woodSet.getAzaleaBoatsId());
        }

        BlockRenderLayerMap.putBlock(AzaleaBlocks.DROOPING_AZALEA_LEAVES, BlockRenderLayer.CUTOUT);

/*        EntityRendererRegistry.register(AzaleaEntityTypes.AZULE_BOAT, context ->
                new BoatEntityRenderer(context, false));

        EntityRendererRegistry.register(AzaleaEntityTypes.AZULE_CHEST_BOAT, context ->
                new BoatEntityRenderer(context, true));*/
    }
}
