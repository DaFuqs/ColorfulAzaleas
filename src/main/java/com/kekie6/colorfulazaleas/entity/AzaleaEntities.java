package com.kekie6.colorfulazaleas.entity;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.vehicle.boat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;
import org.jspecify.annotations.*;

public class AzaleaEntities {
    
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(ColorfulAzaleas.MOD_ID);
    
    static {
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            
            DeferredHolder<EntityType<?>, EntityType<Boat>> boatEntityType = ENTITIES.registerEntityType(
                    woodSet.getWoodSet() + "_azalea_boat",
                    (@NonNull EntityType<Boat> entityType, @NonNull Level level) -> new Boat(entityType, level, woodSet::getBoatItem),
                    MobCategory.MISC,
                    builder -> builder.noLootTable()
                            .sized(1.375F, 0.5625F)
                            .eyeHeight(0.5625F)
                            .clientTrackingRange(10)
            );
            
            DeferredHolder<EntityType<?>, EntityType<ChestBoat>> chestBoatEntityType = ENTITIES.registerEntityType(
                    woodSet.getWoodSet() + "_azalea_chest_boat",
                    (@NonNull EntityType<ChestBoat> entityType, @NonNull Level level) -> new ChestBoat(entityType, level, woodSet::getChestBoatItem),
                    MobCategory.MISC,
                    builder -> builder.noLootTable()
                            .sized(1.375F, 0.5625F)
                            .eyeHeight(0.5625F)
                            .clientTrackingRange(10)
            );
            BoatItem boatItem = new BoatItem(boatEntityType.get(), new Item.Properties().stacksTo(1));
            BoatItem chestBoatItem = new BoatItem(chestBoatEntityType.get(), new Item.Properties().stacksTo(1));
            woodSet.setBoatItem(boatItem);
            woodSet.setChestBoatItem(chestBoatItem);
            woodSet.setBoatEntityType(boatEntityType.get());
            woodSet.setChestBoatEntityType(chestBoatEntityType.get());
        }
    }
    
    public static void init(IEventBus modBus) {
        ENTITIES.register(modBus);
    }

}