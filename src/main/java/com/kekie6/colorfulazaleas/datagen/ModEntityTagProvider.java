package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
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

            // Fabric C / convention tags
            addEntities(
                    ModTags.Entity.C_BOATS,
                    woodSet.getBoatEntityType(),
                    woodSet.getChestBoatEntityType()
            );
        }
    }

    private void addEntities(TagKey<EntityType<?>> tag, EntityType<?>... entityTypes) {
        var tagBuilder = builder(tag);

        for (EntityType<?> entityType : entityTypes) {
            tagBuilder.add(key(entityType));
        }
    }

    private static ResourceKey<EntityType<?>> key(EntityType<?> entityType) {
        return entityType.builtInRegistryHolder().key();
    }
}