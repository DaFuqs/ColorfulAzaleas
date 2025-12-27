package com.kekie6.colorfulazaleas.registry;

import com.kekie6.colorfulazaleas.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

public class AzaleaItems {
    
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ColorfulAzaleas.MOD_ID);
    
    public static DeferredItem<Item> ICON_ITEM = register("icon_item", Item::new, new Item.Properties());

    public static void register(IEventBus modBus) {
        ColorfulAzaleas.LOGGER.info("Registering items for " + ColorfulAzaleas.MOD_ID);
        ITEMS.register(modBus);
    }

    public static<T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        return ITEMS.register(name, identifier -> itemFactory.apply(settings.setId(ResourceKey.create(Registries.ITEM, identifier))));
    }
    
}
