package com.kekie6.colorfulazaleas;

import com.kekie6.colorfulazaleas.decorators.*;
import com.kekie6.colorfulazaleas.registry.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.tag.convention.v2.*;
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
        AzaleaBlocks.init();
        ColorfulAzaleasItemGroups.register();
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

}