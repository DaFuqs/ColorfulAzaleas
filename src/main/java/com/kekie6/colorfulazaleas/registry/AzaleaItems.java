package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;
// Unused class - Might delete later - was experimenting
public class AzaleaItems {
/*

    public static final BoatItem AZULE_BOAT_ITEM = registerItem(
            "azule_boat",
            settings -> new BoatItem(AzaleaEntityTypes.AZULE_BOAT, settings.maxCount(1))
    );

    public static final BoatItem AZULE_CHEST_BOAT_ITEM = registerItem(
            "azule_chest_boat",
            settings -> new BoatItem(AzaleaEntityTypes.AZULE_CHEST_BOAT, settings.maxCount(1))
    );
*/

    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of(ColorfulAzaleas.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(
                        RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ColorfulAzaleas.MOD_ID, name)))
                )
        );
    }

    private static Item registerBoatItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of(ColorfulAzaleas.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(
                        RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ColorfulAzaleas.MOD_ID, name)))
                )
        );
    }

    public static void registerModItems() {
        ColorfulAzaleas.LOGGER.info("Registering Mod Items for " + ColorfulAzaleas.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
/*            entries.add(AZULE_BOAT_ITEM);
            entries.add(AZULE_CHEST_BOAT_ITEM);*/
        });
    }
}
