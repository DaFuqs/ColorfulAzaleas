package com.kekie6.colorfulazaleas.util;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> AZALEA_LOGS = createTag("azalea_logs");

        // Fabric C BlockTags
        public static final TagKey<Block> C_FLOWERS = createCTag("flowers");
        public static final TagKey<Block> C_FENCE_GATES_WOODEN = createCTag("fence_gates/wooden");
        public static final TagKey<Block> C_WOODEN_FENCES = createCTag("fences/wooden");
        public static final TagKey<Block> C_PLANKS_THAT_BURN = createCTag("planks_that_burn");
        public static final TagKey<Block> C_STRIPPED_LOGS = createCTag("stripped_logs");
        public static final TagKey<Block> C_STRIPPED_WOODS = createCTag("stripped_woods");
        public static final TagKey<Block> C_WOODS = createCTag("woods");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, ColorfulAzaleas.id(name));
        }

        private static TagKey<Block> createCTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", name));
        }


    }

    public static class Items {
        public static final TagKey<Item> AZALEA_LOGS = createTag("azalea_logs");
        public static final TagKey<Item> AZALEAS = createTag("azaleas");

        // Fabric C ItemTags
        public static final TagKey<Item> C_FENCE_GATES_WOODEN = createCTag("fence_gates/wooden");
        public static final TagKey<Item> C_WOODEN_FENCES = createCTag("fences/wooden");
        public static final TagKey<Item> C_PLANKS_THAT_BURN = createCTag("planks_that_burn");
        public static final TagKey<Item> C_STRIPPED_LOGS = createCTag("stripped_logs");
        public static final TagKey<Item> C_STRIPPED_WOODS = createCTag("stripped_woods");

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

        private static TagKey<Item> createCTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", name));
        }
    }

    public static class Entity {
        // Fabric C EntityTags
        public static final TagKey<EntityType<?>> C_BOATS = createCTag("boats"); // Includes chest boats

        private static TagKey<EntityType<?>> createCTag(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of("c", name));
        }
    }
}
