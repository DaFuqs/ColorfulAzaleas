package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.core.*;
import org.jspecify.annotations.*;

import java.util.concurrent.*;

public class ModEntityTagProvider extends FabricTagsProvider.EntityTypeTagsProvider {

    public ModEntityTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            // Fabric C (Convention) Tags
            valueLookupBuilder(ModTags.Entity.C_BOATS)
                    .add(woodSet.getBoatEntityType())
                    .add(woodSet.getChestBoatEntityType());
        }
    }
}
