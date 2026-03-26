package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.util.WoodSet;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.function.Supplier;

public class AzaleaBoatRegistry {
    public static void registerBoat(WoodSet woodSet, boolean chest) {
        Identifier id = woodSet.getAzaleaBoatsId();

        String suffix = chest ? "_chest_boat" : "_boat";
        Identifier fullId = Identifier.fromNamespaceAndPath(
                id.getNamespace(),
                id.getPath() + suffix
        );

        DelayedItemSupplier supplier = new DelayedItemSupplier();
        if (chest) {
            // --- Chest Boat ---

            EntityType<ChestBoat> entityType = Registry.register(
                    BuiltInRegistries.ENTITY_TYPE,
                    fullId,
                    EntityType.Builder.of(
                                    (EntityType<ChestBoat> type, Level level) -> new ChestBoat(type, level, supplier),
                                    MobCategory.MISC
                            )
                            .sized(1.375f, 0.5625f)
                            .clientTrackingRange(10)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, fullId))
            );

            ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, fullId);

            BoatItem item = Registry.register(
                    BuiltInRegistries.ITEM,
                    itemKey,
                    new BoatItem(entityType, new Item.Properties().stacksTo(1).setId(itemKey))
            );

            supplier.set(item);

            woodSet.setChestBoatItem(item);
            woodSet.setChestBoatEntityType(entityType);

            DispenserBlock.registerBehavior(item, new BoatDispenseItemBehavior(entityType));

        } else {
            // --- Boat ---

            EntityType<Boat> entityType = Registry.register(
                    BuiltInRegistries.ENTITY_TYPE,
                    fullId,
                    EntityType.Builder.of(
                                    (EntityType<Boat> type, Level level) -> new Boat(type, level, supplier),
                                    MobCategory.MISC
                            )
                            .sized(1.375f, 0.5625f)
                            .clientTrackingRange(10)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, fullId))
            );

            ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, fullId);

            BoatItem item = Registry.register(
                    BuiltInRegistries.ITEM,
                    itemKey,
                    new BoatItem(entityType, new Item.Properties().stacksTo(1).setId(itemKey))
            );

            supplier.set(item);

            woodSet.setBoatItem(item);
            woodSet.setBoatEntityType(entityType);

            DispenserBlock.registerBehavior(item, new BoatDispenseItemBehavior(entityType));

        }
    }

    private static class DelayedItemSupplier implements Supplier<Item> {
        private Item item;

        public void set(Item item) {
            this.item = item;
        }

        @Override
        public Item get() {
            return item;
        }
    }
}
