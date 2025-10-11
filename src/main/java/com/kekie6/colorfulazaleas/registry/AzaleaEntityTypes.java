package com.kekie6.colorfulazaleas.registry;
/*

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.kekie6.colorfulazaleas.util.ColorfulTree;
import com.kekie6.colorfulazaleas.util.WoodSet;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

import com.terraformersmc.terraform.boat.*;
*/
// Unused class - was experimenting with creating boats - might delete later
public class AzaleaEntityTypes {

/*    public void generateBoatItem() {
        for (ColorfulTree tree : AzaleaBlocks.trees) {
            WoodSet woodSet = tree.getWoodSet();

            woodSet.setBoatItem(TerraformBoatItemHelper.registerBoatItem(, false));
        }
    }    */
/*    public void generateBoatItem() {
            ColorfulTree tree = AzaleaBlocks.trees[0];
            WoodSet woodSet = tree.getWoodSet();

            woodSet.setBoatItem(TerraformBoatItemHelper.registerBoatItem(ColorfulAzaleas.id("test_boat"), false));
        }*/
    }

/*
    public static final EntityType<BoatEntity> AZULE_BOAT = register("azule_boat",
            EntityType.Builder.create(getAzaleaBoatFactory(() -> AzaleaItems.AZULE_BOAT_ITEM), SpawnGroup.MISC)
                    .dropsNothing()
                    .dimensions(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .maxTrackingRange(10)
    );

    public static final EntityType<ChestBoatEntity> AZULE_CHEST_BOAT = register("azule_chest_boat",
            EntityType.Builder.create(getAzaleaChestBoatFactory(() -> AzaleaItems.AZULE_CHEST_BOAT_ITEM), SpawnGroup.MISC)
                    .dropsNothing()
                    .dimensions(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .maxTrackingRange(10)
    );

    // Custom factory just like vanilla’s getBoatFactory
    private static EntityType.EntityFactory<BoatEntity> getAzaleaBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new BoatEntity(type, world, itemSupplier);
    }

    private static EntityType.EntityFactory<ChestBoatEntity> getAzaleaChestBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new ChestBoatEntity(type, world, itemSupplier);
    }

    public static void register() {
        ColorfulAzaleas.LOGGER.info("Registered Azule boat entities.");
    }

    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(ColorfulAzaleas.MOD_ID, id));
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }*/

