package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.decorators.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import com.terraformersmc.terraform.boat.api.item.*;
import net.fabricmc.api.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.feature.treedecorators.*;
import org.slf4j.*;

public class ColorfulAzaleas implements ModInitializer {
    public static final String MOD_ID = "colorfulazaleas";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final TreeDecoratorType<ColorfulTreeDecorator> COLORFUL_TREE_DECORATOR = Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, id("colorful_tree_decorator"), new TreeDecoratorType<>(ColorfulTreeDecorator.CODEC));

    @Override
    public void onInitialize() {
        AzaleaItems.register();
        AzaleaBlocks.init();
        AzaleaItemGroups.register();

        // Boats
        registerAllAzaleaBoats();
    }

    public static void registerAllAzaleaBoats() {
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();

            woodSet.setBoatItem(TerraformBoatItemHelper.registerBoatItem(woodSet.getAzaleaBoatsId(), false));
            woodSet.setChestBoatItem(TerraformBoatItemHelper.registerBoatItem(woodSet.getAzaleaBoatsId(), true));
        }
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}