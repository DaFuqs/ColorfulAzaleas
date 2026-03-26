package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.world.level.block.entity.*;

public class ColorfulAzaleasClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorfulAzaleas.LOGGER.info("Generating Cutouts for " + ColorfulAzaleas.MOD_ID);
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();

            AzaleaBoatClient.register(woodSet);

            ModelLayerRegistry.registerModelLayer(
                    AzaleaBoatClient.boatLayer(woodSet),
                    BoatModel::createBoatModel
            );

            ModelLayerRegistry.registerModelLayer(
                    AzaleaBoatClient.chestBoatLayer(woodSet),
                    BoatModel::createChestBoatModel
            );

            BlockEntityType.SHELF.addValidBlock(woodSet.getShelf());
        }
    }
}