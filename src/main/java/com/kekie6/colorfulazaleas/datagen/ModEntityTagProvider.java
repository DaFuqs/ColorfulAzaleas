package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import org.jspecify.annotations.*;

import java.util.concurrent.*;

public class ModEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

    public ModEntityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
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
