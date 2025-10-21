package com.kekie6.colorfulazaleas.util;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Function;

public class AzaleaSignHelper {

    private AzaleaSignHelper() {} // Utility

    // Register a sign block and automatically add it to the corresponding block entity type
    public static <T extends AbstractSignBlock> T registerSignBlock(RegistryKey<Block> key, T block) {
        if (block instanceof SignBlock || block instanceof WallSignBlock) {
            BlockEntityType.SIGN.addSupportedBlock(block);
        } else if (block instanceof HangingSignBlock || block instanceof WallHangingSignBlock) {
            BlockEntityType.HANGING_SIGN.addSupportedBlock(block);
        } else {
            throw new IllegalArgumentException("Block must be a vanilla sign type");
        }
        // Debugging: Print loot table location immediately after registration
        // Optional lootTableId = block.getLootTableKey();
        // System.out.println("[LootTable Debug] Registered block: " + key + " -> Loot table: " + lootTableId);

        return Registry.register(Registries.BLOCK, key.getValue(), block);
    }

    // Create a registry key from an Identifier and register the sign block
    public static <T extends AbstractSignBlock> T registerSignBlock(Identifier id, Function<AbstractBlock.Settings, T> factory, AbstractBlock.Settings settings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        settings.registryKey(key); // Applies registry key to settings
        return registerSignBlock(key, factory.apply(settings));
    }

    public static void registerSignItems(WoodSet woodSet, String title) {
        // Sign item
        RegistryKey<Item> signItemKey = RegistryKey.of(RegistryKeys.ITEM, ColorfulAzaleas.id(title + "_azalea_sign"));
        Registry.register(
                Registries.ITEM,
                signItemKey.getValue(),
                new SignItem(
                        woodSet.getSign(),
                        woodSet.getWallSign(),
                        new Item.Settings()
                                .maxCount(16)
                                .registryKey(signItemKey)
                                .useBlockPrefixedTranslationKey()
                )
        );

        // Hanging sign item
        RegistryKey<Item> hangingSignItemKey = RegistryKey.of(RegistryKeys.ITEM, ColorfulAzaleas.id(title + "_azalea_hanging_sign"));
        Registry.register(
                Registries.ITEM,
                hangingSignItemKey.getValue(),
                new HangingSignItem(
                        woodSet.getHangingSign(),
                        woodSet.getWallHangingSign(),
                        new Item.Settings()
                                .maxCount(16)
                                .registryKey(hangingSignItemKey)
                                .useBlockPrefixedTranslationKey()
                )
        );
    }
}
