package com.kekie6.colorfulazaleas.util;

import com.kekie6.colorfulazaleas.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;

import java.util.function.*;

public class AzaleaSignHelper {

    private AzaleaSignHelper() {} // Utility

    // Register a sign block and automatically add it to the corresponding block entity type
    public static <T extends SignBlock> T registerSignBlock(ResourceKey<Block> key, T block) {
        if (block instanceof StandingSignBlock || block instanceof WallSignBlock) {
            BlockEntityType.SIGN.addValidBlock(block);
        } else if (block instanceof CeilingHangingSignBlock || block instanceof WallHangingSignBlock) {
            BlockEntityType.HANGING_SIGN.addValidBlock(block);
        } else {
            throw new IllegalArgumentException("Block must be a vanilla sign type");
        }

        return Registry.register(BuiltInRegistries.BLOCK, key.identifier(), block);
    }

    // Create a registry key from an Identifier and register the sign block
    public static <T extends SignBlock> T registerSignBlock(Identifier id, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties settings) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
        settings.setId(key);
        return registerSignBlock(key, factory.apply(settings));
    }

    public static void registerSignItems(WoodSet woodSet, String title) {
        // Sign item
        ResourceKey<Item> signItemKey = ResourceKey.create(Registries.ITEM, ColorfulAzaleas.id(title + "_azalea_sign"));
        Registry.register(
                BuiltInRegistries.ITEM,
                signItemKey.identifier(),
                new SignItem(
                        woodSet.getSign(),
                        woodSet.getWallSign(),
                        new net.minecraft.world.item.Item.Properties()
                                .stacksTo(16)
                                .setId(signItemKey)
                                .useBlockDescriptionPrefix()
                )
        );

        // Hanging sign item
        ResourceKey<Item> hangingSignItemKey = ResourceKey.create(Registries.ITEM, ColorfulAzaleas.id(title + "_azalea_hanging_sign"));
        Registry.register(
                BuiltInRegistries.ITEM,
                hangingSignItemKey.identifier(),
                new HangingSignItem(
                        woodSet.getHangingSign(),
                        woodSet.getWallHangingSign(),
                        new net.minecraft.world.item.Item.Properties()
                                .stacksTo(16)
                                .setId(hangingSignItemKey)
                                .useBlockDescriptionPrefix()
                )
        );
    }
}
