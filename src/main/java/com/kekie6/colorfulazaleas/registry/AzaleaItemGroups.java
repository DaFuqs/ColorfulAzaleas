package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.util.*;
import net.fabricmc.fabric.api.itemgroup.v1.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;

public class AzaleaItemGroups {

    public static CreativeModeTab CREATIVE_MODE_TAB;

    public static void register() {
        CREATIVE_MODE_TAB = FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.colorfulazaleas.colorful_azaleas"))
                .icon(() -> new ItemStack(AzaleaItems.ICON_ITEM))
                .displayItems((itemDisplayParameters, entries) -> {
                    entries.accept(AzaleaBlocks.DROOPING_AZALEA_LEAVES);

                    for (ColorfulTree tree : AzaleaBlocks.trees) {
                        entries.accept(tree.getSapling());
                        entries.accept(tree.getAzaleaLeaves());
                        entries.accept(tree.getBloomingLeaves());
                        entries.accept(tree.getFloweringLeaves());
                        entries.accept(tree.getDroopingLeaves());

                        WoodSet woodSet = tree.getWoodSet();

                        entries.accept(woodSet.getLog());
                        entries.accept(woodSet.getWood());
                        entries.accept(woodSet.getStrippedLog());
                        entries.accept(woodSet.getStrippedWood());
                        entries.accept(woodSet.getPlanks());
                        entries.accept(woodSet.getStairs());
                        entries.accept(woodSet.getSlab());
                        entries.accept(woodSet.getFence());
                        entries.accept(woodSet.getFenceGate());
                        entries.accept(woodSet.getDoor());
                        entries.accept(woodSet.getTrapdoor());
                        entries.accept(woodSet.getPressurePlate());
                        entries.accept(woodSet.getButton());
                        entries.accept(woodSet.getSign());
                        entries.accept(woodSet.getHangingSign());
                        entries.accept(woodSet.getShelf());
                        entries.accept(woodSet.getBoatItem());
                        entries.accept(woodSet.getChestBoatItem());
                    }
                })
                .build();

        Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                ColorfulAzaleas.id("colorful_azaleas"),
                CREATIVE_MODE_TAB
        );
    }
}
