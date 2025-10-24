package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class AzaleaItemGroups {

    public static ItemGroup CREATIVE_MODE_TAB;

    public static void register() {
        CREATIVE_MODE_TAB = FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.colorfulazaleas.colorful_azaleas"))
                .icon(() -> new ItemStack(AzaleaItems.ICON_ITEM))
                .entries((itemDisplayParameters, entries) -> {
                    entries.add(AzaleaBlocks.DROOPING_AZALEA_LEAVES);

                    for (ColorfulTree tree : AzaleaBlocks.trees) {
                        entries.add(tree.getSapling());
                        entries.add(tree.getAzaleaLeaves());
                        entries.add(tree.getBloomingLeaves());
                        entries.add(tree.getFloweringLeaves());
                        entries.add(tree.getDroopingLeaves());

                        WoodSet woodSet = tree.getWoodSet();

                        entries.add(woodSet.getLog());
                        entries.add(woodSet.getWood());
                        entries.add(woodSet.getStrippedLog());
                        entries.add(woodSet.getStrippedWood());
                        entries.add(woodSet.getPlanks());
                        entries.add(woodSet.getStairs());
                        entries.add(woodSet.getSlab());
                        entries.add(woodSet.getFence());
                        entries.add(woodSet.getFenceGate());
                        entries.add(woodSet.getDoor());
                        entries.add(woodSet.getTrapdoor());
                        entries.add(woodSet.getPressurePlate());
                        entries.add(woodSet.getButton());
                        entries.add(woodSet.getSign());
                        entries.add(woodSet.getHangingSign());
                        entries.add(woodSet.getShelf());
                        entries.add(woodSet.getBoatItem());
                        entries.add(woodSet.getChestBoatItem());
                    }
                })
                .build();

        Registry.register(
                Registries.ITEM_GROUP,
                ColorfulAzaleas.id("colorful_azaleas"),
                CREATIVE_MODE_TAB
        );
    }
}
