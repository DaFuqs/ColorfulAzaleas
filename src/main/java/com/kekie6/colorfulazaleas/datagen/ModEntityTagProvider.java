package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.world.entity.*;
import org.jspecify.annotations.*;

import java.util.concurrent.*;

public class ModEntityTagProvider extends EntityTypeTagsProvider {
    
    public ModEntityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ColorfulAzaleas.MOD_ID);
    }
    
    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        for (ColorfulTree tree : AzaleaBlocks.TREES) {
            AzaleaWoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;
            
            // Retrieve the actual EntityType objects
            EntityType<?> boatEntity = woodSet.getBoatEntityType().get();
            EntityType<?> chestBoatEntity = woodSet.getChestBoatEntityType().get();

            // Fabric C (Convention) Tags
            this.tag(ModTags.Entity.C_BOATS)
                    .add(boatEntity)
                    .add(chestBoatEntity);
        }
    }
}
