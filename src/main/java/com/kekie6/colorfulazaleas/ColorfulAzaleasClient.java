package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import com.terraformersmc.terraform.boat.api.client.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.render.*;

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
            BlockEntityType.SHELF.addSupportedBlock(woodSet.getShelf());
        }
    }
}