package com.kekie6.colorfulazaleas.decorators;

import com.kekie6.colorfulazaleas.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.levelgen.feature.treedecorators.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class AzaleaTreeDecorators {
    
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, ColorfulAzaleas.MOD_ID);
    
    public static final TreeDecoratorType<ColorfulTreeDecorator> COLORFUL_TREE_DECORATOR = Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, ColorfulAzaleas.id("colorful_tree_decorator"), new TreeDecoratorType<>(ColorfulTreeDecorator.CODEC));
    
    public static void register(IEventBus modBus) {
        TREE_DECORATORS.register(modBus);
    }
    
}