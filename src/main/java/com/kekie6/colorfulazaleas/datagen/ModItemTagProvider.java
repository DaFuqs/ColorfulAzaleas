package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.registry.AzaleaBlocks;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.ModTags;
import com.kekie6.colorfulazaleas.util.WoodSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.*;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, @Nullable BlockTagProvider blockTagProvider) {
        super(output, registriesFuture, blockTagProvider);
    }

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.@NotNull WrapperLookup wrapperLookup) {
        ColorfulAzaleas.LOGGER.info("Generating ItemTags for " + ColorfulAzaleas.MOD_ID);

        valueLookupBuilder(ModTags.Items.AZALEAS)
                .add(Items.AZALEA);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            valueLookupBuilder(ModTags.Items.AZALEA_LOGS)
                    .add(woodSet.getLogAndWoodItems());

            valueLookupBuilder(ModTags.Items.AZALEAS)
                    .add(tree.getSapling().asItem());

            // Per-color tag (dynamic lookup)
            TagKey<Item> colorTag = ModTags.Items.COLORFUL_AZALEA_LOGS_ITEMS.get(tree.getColor());
            if (colorTag != null) {
                valueLookupBuilder(colorTag).add(woodSet.getLogAndWoodItems());
            }

            valueLookupBuilder(ItemTags.LOGS_THAT_BURN)
                    .add(woodSet.getLogAndWoodItems());

            valueLookupBuilder(ItemTags.PLANKS)
                    .add(woodSet.getPlanks().asItem());
            valueLookupBuilder(ItemTags.WOODEN_STAIRS)
                    .add(woodSet.getStairs().asItem());
            valueLookupBuilder(ItemTags.WOODEN_SLABS)
                    .add(woodSet.getSlab().asItem());
            valueLookupBuilder(ItemTags.WOODEN_FENCES)
                    .add(woodSet.getFence().asItem());
            valueLookupBuilder(ItemTags.FENCE_GATES)
                    .add(woodSet.getFenceGate().asItem());
            valueLookupBuilder(ItemTags.WOODEN_DOORS)
                    .add(woodSet.getDoor().asItem());
            valueLookupBuilder(ItemTags.WOODEN_TRAPDOORS)
                    .add(woodSet.getTrapdoor().asItem());
            valueLookupBuilder(ItemTags.WOODEN_PRESSURE_PLATES)
                    .add(woodSet.getPressurePlate().asItem());
            valueLookupBuilder(ItemTags.WOODEN_BUTTONS)
                    .add(woodSet.getButton().asItem());
            valueLookupBuilder(ItemTags.SIGNS)
                    .add(woodSet.getSign().asItem());
            valueLookupBuilder(ItemTags.HANGING_SIGNS)
                    .add(woodSet.getHangingSign().asItem());
            valueLookupBuilder(ItemTags.WOODEN_SHELVES)
                    .add(woodSet.getShelf().asItem());

            valueLookupBuilder(ItemTags.BOATS)
                    .add(woodSet.getBoatItem());
            valueLookupBuilder(ItemTags.CHEST_BOATS)
                    .add(woodSet.getChestBoatItem());

            valueLookupBuilder(ItemTags.SAPLINGS)
                    .add(tree.getSapling().asItem());
            valueLookupBuilder(ItemTags.LEAVES)
                    .add(tree.getLeavesItems());
            // Fabric C (Convention) Tags
            valueLookupBuilder(ModTags.Items.C_PLANKS_THAT_BURN)
                    .add(woodSet.getPlanks().asItem());
            valueLookupBuilder(ModTags.Items.C_WOODEN_FENCES)
                    .add(woodSet.getFence().asItem());
            valueLookupBuilder(ModTags.Items.C_FENCE_GATES_WOODEN)
                    .add(woodSet.getFenceGate().asItem());
            valueLookupBuilder(ModTags.Items.C_STRIPPED_LOGS)
                    .add(woodSet.getStrippedLog().asItem());
            valueLookupBuilder(ModTags.Items.C_STRIPPED_WOODS)
                    .add(woodSet.getStrippedWood().asItem());
        }
    }
}