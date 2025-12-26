package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

public class AzaleaItems {
    
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ColorfulAzaleas.MOD_ID);
    
    public static Item ICON_ITEM = register("icon_item", Item::new, new Item.Properties());

    public static void register(IEventBus modBus) {
        ColorfulAzaleas.LOGGER.info("Registering items for " + ColorfulAzaleas.MOD_ID);
        ITEMS.register(modBus);
    }

    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ColorfulAzaleas.MOD_ID, name));
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }
}
