package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class AzaleaItems {
    public static Item ICON_ITEM = register("icon_item", Item::new, new Item.Settings());

    public static void register() {
        ColorfulAzaleas.LOGGER.info("Registering items for " + ColorfulAzaleas.MOD_ID);
    }

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ColorfulAzaleas.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }
}
