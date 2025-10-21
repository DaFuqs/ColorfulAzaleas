package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.ModTags;
import com.kekie6.colorfulazaleas.util.WoodSet;
import com.terraformersmc.terraform.boat.api.data.TerraformBoatData;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

    public ModEntityTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            var boatData = TerraformBoatData.getOptional(woodSet.getAzaleaBoatsId());
            if (boatData.isEmpty()) continue;

            // Retrieve the actual EntityType objects
            EntityType<?> boatEntity = boatData.get().boatEntityType();
            EntityType<?> chestBoatEntity = boatData.get().chestBoatEntityType();

            // Fabric C (Convention) Tags
            valueLookupBuilder(ModTags.Entity.C_BOATS)
                    .add(boatEntity)
                    .add(chestBoatEntity);
        }
    }
}
