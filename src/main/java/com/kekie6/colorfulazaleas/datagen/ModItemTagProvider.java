package com.kekie6.colorfulazaleas.datagen;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider wrapperLookup) {
        ColorfulAzaleas.LOGGER.info("Generating ItemTags for " + ColorfulAzaleas.MOD_ID);

        addItems(ModTags.Items.AZALEAS, Items.AZALEA);

        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            if (woodSet == null) continue;

            addItems(ModTags.Items.AZALEA_LOGS, woodSet.getLogAndWoodItems());

            addItems(ModTags.Items.AZALEAS, tree.getSapling().asItem());

            // Per-color tag (dynamic lookup)
            TagKey<Item> colorTag = ModTags.Items.COLORFUL_AZALEA_LOGS_ITEMS.get(tree.getColor());
            if (colorTag != null) {
                addItems(colorTag, woodSet.getLogAndWoodItems());
            }

            addItems(BlockItemTags.LOGS_THAT_BURN, woodSet.getLogAndWoodItems());

            addItems(ItemTags.PLANKS, woodSet.getPlanks().asItem());
            addItems(ItemTags.WOODEN_STAIRS, woodSet.getStairs().asItem());
            addItems(ItemTags.WOODEN_SLABS, woodSet.getSlab().asItem());
            addItems(ItemTags.WOODEN_FENCES, woodSet.getFence().asItem());
            addItems(ItemTags.FENCE_GATES, woodSet.getFenceGate().asItem());
            addItems(ItemTags.WOODEN_DOORS, woodSet.getDoor().asItem());
            addItems(ItemTags.WOODEN_TRAPDOORS, woodSet.getTrapdoor().asItem());
            addItems(ItemTags.WOODEN_PRESSURE_PLATES, woodSet.getPressurePlate().asItem());
            addItems(ItemTags.WOODEN_BUTTONS, woodSet.getButton().asItem());
            addItems(ItemTags.SIGNS, woodSet.getSign().asItem());
            addItems(ItemTags.HANGING_SIGNS, woodSet.getHangingSign().asItem());
            addItems(ItemTags.WOODEN_SHELVES, woodSet.getShelf().asItem());

            addItems(ItemTags.BOATS, woodSet.getBoatItem());
            addItems(ItemTags.CHEST_BOATS, woodSet.getChestBoatItem());

            addItems(BlockItemTags.SAPLINGS, tree.getSapling().asItem());
            addItems(ItemTags.LEAVES, tree.getLeavesItems());

            // Fabric C / convention tags
            addItems(ModTags.Items.C_PLANKS_THAT_BURN, woodSet.getPlanks().asItem());
            addItems(ModTags.Items.C_WOODEN_FENCES, woodSet.getFence().asItem());
            addItems(ModTags.Items.C_FENCE_GATES_WOODEN, woodSet.getFenceGate().asItem());
            addItems(ModTags.Items.C_STRIPPED_LOGS, woodSet.getStrippedLog().asItem());
            addItems(ModTags.Items.C_STRIPPED_WOODS, woodSet.getStrippedWood().asItem());
        }
    }

    private void addItems(BlockItemTagId tag, Item... items) {
        addItems(tag.item(), items);
    }

    private void addItems(TagKey<Item> tag, Item... items) {
        var tagBuilder = builder(tag);

        for (Item item : items) {
            tagBuilder.add(key(item));
        }
    }

    private static ResourceKey<Item> key(Item item) {
        return item.builtInRegistryHolder().key();
    }
}
