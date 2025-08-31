package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.*;
import net.fabricmc.fabric.api.itemgroup.v1.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;


public class ColorfulAzaleasItemGroups {

    public static final ItemGroup CREATIVE_MODE_TAB = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.colorfulazaleas.colorful_azaleas"))
            .icon(() -> new ItemStack(AzaleaBlocks.trees[5].sapling.asItem()))
            .entries((itemDisplayParameters, entries) -> {
                entries.add(AzaleaBlocks.DROOPING_AZALEA_LEAVES);

                for (AzaleaBlocks.ColorfulTree tree : AzaleaBlocks.trees) {
                    entries.add(tree.sapling);
                    entries.add(tree.azaleaLeaves);
                    entries.add(tree.bloomingLeaves);
                    entries.add(tree.floweringLeaves);
                    entries.add(tree.droopingLeaves);

                    AzaleaBlocks.WoodSet woodSet = tree.woodSet;
                    entries.add(woodSet.log);
                    entries.add(woodSet.wood);
                    entries.add(woodSet.stripped_log);
                    entries.add(woodSet.stripped_wood);
                    entries.add(woodSet.planks);
                    entries.add(woodSet.stairs);
                    entries.add(woodSet.slab);
                    entries.add(woodSet.fence);
                    entries.add(woodSet.fence_gate);
                    entries.add(woodSet.door);
                    entries.add(woodSet.trapdoor);
                    entries.add(woodSet.pressure_plate);
                    entries.add(woodSet.button);
                }
            })
            .build();


    public static void register() {
        Registry.register(Registries.ITEM_GROUP,
                ColorfulAzaleas.id("colorful_azaleas"),
                CREATIVE_MODE_TAB
        );
    }

}