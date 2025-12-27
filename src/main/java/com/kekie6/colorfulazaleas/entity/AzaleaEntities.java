package com.kekie6.colorfulazaleas.entity;

import com.kekie6.colorfulazaleas.*;
import com.kekie6.colorfulazaleas.block.*;
import com.kekie6.colorfulazaleas.registry.*;
import com.kekie6.colorfulazaleas.util.*;
import com.mojang.datafixers.util.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.vehicle.boat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.storage.loot.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.*;
import java.util.function.*;

import static net.minecraft.world.level.block.Blocks.*;

public class AzaleaEntities {
    
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(ColorfulAzaleas.MOD_ID);
    
    static {
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();
            
            
            DeferredHolder<EntityType<Boat>, EntityType<Boat>> boatEntityType = ENTITIES.registerEntityType(
                    woodSet.getWoodSet() + "_azalea_boat",
                    (entityType, level) -> new Boat((EntityType<? extends Boat>) entityType, level, woodSet::getBoatItem),
                    MobCategory.MISC,
                    builder -> builder.noLootTable()
                            .sized(1.375F, 0.5625F)
                            .eyeHeight(0.5625F)
                            .clientTrackingRange(10)
            );
            
            DeferredHolder<EntityType<ChestBoat>, EntityType<ChestBoat>> chestBoatEntityType = ENTITIES.registerEntityType(
                    woodSet.getWoodSet() + "_azalea_chest_boat",
                    (entityType, level) -> new Boat((EntityType<? extends ChestBoat>) entityType, level, woodSet::getChestBoatItem),
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