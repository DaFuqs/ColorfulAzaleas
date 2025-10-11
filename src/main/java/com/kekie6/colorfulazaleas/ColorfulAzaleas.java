package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.decorators.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.fabricmc.api.*;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import net.minecraft.util.Identifier;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import org.slf4j.*;

public class ColorfulAzaleas implements ModInitializer {
    public static final String MOD_ID = "colorfulazaleas";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final TreeDecoratorType<ColorfulTreeDecorator> COLORFUL_TREE_DECORATOR = Registry.register(Registries.TREE_DECORATOR_TYPE, id("colorful_tree_decorator"), new TreeDecoratorType<>(ColorfulTreeDecorator.CODEC));

    @Override
    public void onInitialize() {
        AzaleaBlocks.init();
        AzaleaItems.registerModItems();
        ColorfulAzaleasItemGroups.register();

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
        return Identifier.of(MOD_ID, name);
    }

}