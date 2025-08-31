package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

//import net.minecraft.client.renderer.RenderType;

public class ColorfulAzaleasClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (int i = 0; i < AzaleaBlocks.AzaleaColors.values().length; i++) {
            AzaleaBlocks.ColorfulTree tree = AzaleaBlocks.trees[i];
            BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT, tree.sapling, tree.pottedSapling, tree.floweringLeaves, tree.bloomingLeaves, tree.azaleaLeaves, tree.droopingLeaves);
            
            AzaleaBlocks.WoodSet wood = tree.woodSet;
            BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT, wood.door, wood.trapdoor);
        }
        BlockRenderLayerMap.putBlock(AzaleaBlocks.DROOPING_AZALEA_LEAVES, BlockRenderLayer.CUTOUT);
    }
}