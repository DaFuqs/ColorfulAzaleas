package com.kekie6.colorfulazaleas.util;

import com.kekie6.colorfulazaleas.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;

import java.util.*;
import java.util.stream.*;

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
            return TagKey.create(Registries.BLOCK, ColorfulAzaleas.id(name));
        }

        private static TagKey<Block> createCTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", name));
        }


    }

    public static class Items {
        public static final TagKey<Item> AZALEA_LOGS = createTag("azalea_logs");
        public static final TagKey<Item> AZALEAS = createTag("azaleas");
        public static final TagKey<Item> AZALEA_SAPLINGS = createTag("azalea_saplings");
        
        // Fabric C ItemTags
        public static final TagKey<Item> C_FENCE_GATES_WOODEN = createCTag("fence_gates/wooden");
        public static final TagKey<Item> C_WOODEN_FENCES = createCTag("fences/wooden");
        public static final TagKey<Item> C_PLANKS_THAT_BURN = createCTag("planks_that_burn");
        public static final TagKey<Item> C_STRIPPED_LOGS = createCTag("stripped_logs");
        public static final TagKey<Item> C_STRIPPED_WOODS = createCTag("stripped_woods");

        // Dynamically store per-color log item tags
        public static final Map<AzaleaColor, TagKey<Item>> COLORFUL_AZALEA_LOGS_ITEMS =
                Arrays.stream(AzaleaColor.values())
                        .collect(Collectors.toMap(
                                color -> color,
                                color -> createTag(color.getTitle() + "_azalea_logs")
                        ));

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, ColorfulAzaleas.id(name));
        }

        private static TagKey<Item> createCTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", name));
        }
    }

    public static class Entity {
        // Fabric C EntityTags
        public static final TagKey<EntityType<?>> C_BOATS = createCTag("boats"); // Includes chest boats

        private static TagKey<EntityType<?>> createCTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("c", name));
        }
    }
}
