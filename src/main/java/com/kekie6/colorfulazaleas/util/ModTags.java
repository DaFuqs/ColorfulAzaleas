package com.kekie6.colorfulazaleas.util;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> AZALEA_LOGS = createTag("azalea_logs");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, ColorfulAzaleas.id(name));
        }
    }

    public static class Items {
        public static final TagKey<Item> AZALEA_LOGS = createTag("azalea_logs");
        public static final TagKey<Item> AZALEAS = createTag("azaleas");

        // Dynamically store per-color log item tags
        public static final Map<AzaleaColors, TagKey<Item>> COLORFUL_AZALEA_LOGS_ITEMS =
                Arrays.stream(AzaleaColors.values())
                        .collect(Collectors.toMap(
                                color -> color,
                                color -> createTag(color.getTitle() + "_azalea_logs")
                        ));

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, ColorfulAzaleas.id(name));
        }
    }
}
