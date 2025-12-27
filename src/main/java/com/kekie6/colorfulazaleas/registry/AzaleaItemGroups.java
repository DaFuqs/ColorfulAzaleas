package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

public class AzaleaItemGroups {
    
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ColorfulAzaleas.MOD_ID);
    
    public static final Supplier<CreativeModeTab> CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register("example", () -> CreativeModeTab.builder()
            //Set the title of the tab. Don't forget to add a translation!
            .title(Component.translatable("itemGroup.colorfulazaleas.colorful_azaleas"))
            //Set the icon of the tab.
            .icon(() -> new ItemStack(AzaleaItems.ICON_ITEM))
            //Add your items to the tab.
            .displayItems((params, entries) -> {
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
            .build()
    );

    public static void register(IEventBus modBus) {
        CREATIVE_MODE_TABS.register(modBus);
    }
}
