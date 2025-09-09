package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.renderer.chunk.*;

public class ColorfulAzaleasClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (int i = 0; i < AzaleaBlocks.AzaleaColors.values().length; i++) {
            AzaleaBlocks.ColorfulTree tree = AzaleaBlocks.trees[i];
            BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT, tree.sapling, tree.pottedSapling, tree.floweringLeaves, tree.bloomingLeaves, tree.azaleaLeaves, tree.droopingLeaves);
            
            AzaleaBlocks.WoodSet wood = tree.woodSet;
            BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT, wood.door, wood.trapdoor);
        }
        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT, AzaleaBlocks.DROOPING_AZALEA_LEAVES);
    }
}