package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.minecraft.core.*;
import org.jspecify.annotations.*;

public class ColorfulAzaleasDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModLanguageProvider::new);
        pack.addProvider(ModEntityTagProvider::new);
    }

    @Override
    public void buildRegistry(@NonNull RegistrySetBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
    }

    @Override
    public void addJsonKeySortOrders(@NonNull JsonKeySortOrderCallback callback) {
        DataGeneratorEntrypoint.super.addJsonKeySortOrders(callback);
    }
}
