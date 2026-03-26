package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.util.WoodSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class AzaleaBoatClient {
    public static void register(WoodSet woodSet) {

        if (woodSet.getBoatEntityType() != null) {
            ModelLayerLocation layer = boatLayer(woodSet);

            EntityRenderers.register(
                    woodSet.getBoatEntityType(),
                    context -> new BoatRenderer(context, layer)
            );
        }

        if (woodSet.getChestBoatEntityType() != null) {
            ModelLayerLocation layer = chestBoatLayer(woodSet);

            EntityRenderers.register(
                    woodSet.getChestBoatEntityType(),
                    context -> new BoatRenderer(context, layer)
            );
        }
    }

    public static ModelLayerLocation boatLayer(WoodSet woodSet) {
        return new ModelLayerLocation(
                woodSet.getAzaleaBoatsId().withPrefix("boat/"),
                "main"
        );
    }

    public static ModelLayerLocation chestBoatLayer(WoodSet woodSet) {
        return new ModelLayerLocation(
                woodSet.getAzaleaBoatsId().withPrefix("chest_boat/"),
                "main"
        );
    }
}
