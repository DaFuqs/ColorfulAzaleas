package com.kekie6.colorfulazaleas.util;

import com.kekie6.colorfulazaleas.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.vehicle.boat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.*;

import java.util.*;

public class AzaleaWoodSet {
    private final String WoodSet;
    private final Identifier AZALEA_BOATS_ID;

    private DeferredBlock<?> log;
    private DeferredBlock<?> wood;
    private DeferredBlock<?> strippedLog;
    private DeferredBlock<?> strippedWood;
    private DeferredBlock<?> planks;
    private DeferredBlock<?> stairs;
    private DeferredBlock<?> slab;
    private DeferredBlock<?> fence;
    private DeferredBlock<?> fenceGate;
    private DeferredBlock<?> door;
    private DeferredBlock<?> trapdoor;
    private DeferredBlock<?> pressurePlate;
    private DeferredBlock<?> button;
    private DeferredBlock<?> sign;
    private DeferredBlock<?> wallSign;
    private DeferredBlock<?> hangingSign;
    private DeferredBlock<?> wallHangingSign;
    private DeferredBlock<?> shelf;

    private DeferredItem<BoatItem> boatItem;
    private DeferredItem<BoatItem> chestBoatItem;
    
    private DeferredHolder<EntityType<?>, EntityType<Boat>> boatEntityType;
    private DeferredHolder<EntityType<?>, EntityType<ChestBoat>> chestBoatEntityType;

    public AzaleaWoodSet(String WoodSet) {
        this.WoodSet = WoodSet;
        this.AZALEA_BOATS_ID = ColorfulAzaleas.id(WoodSet + "_azalea");
    }

    public String getWoodSet() { return WoodSet; }
    public Identifier getAzaleaBoatsId() {
        return AZALEA_BOATS_ID;
    }
    public DeferredBlock<?> getLog() { return log; }
    public DeferredBlock<?> getWood() { return wood; }
    public DeferredBlock<?> getStrippedLog() { return strippedLog; }
    public DeferredBlock<?> getStrippedWood() { return strippedWood; }
    public DeferredBlock<?> getPlanks() { return planks; }
    public DeferredBlock<?> getStairs() { return stairs; }
    public DeferredBlock<?> getSlab() { return slab; }
    public DeferredBlock<?> getFence() { return fence; }
    public DeferredBlock<?> getFenceGate() { return fenceGate; }
    public DeferredBlock<?> getDoor() { return door; }
    public DeferredBlock<?> getTrapdoor() { return trapdoor; }
    public DeferredBlock<?> getPressurePlate() { return pressurePlate; }
    public DeferredBlock<?> getButton() { return button; }
    public DeferredBlock<?> getSign() { return sign; }
    public DeferredBlock<?> getWallSign() { return wallSign; }
    public DeferredBlock<?> getHangingSign() { return hangingSign; }
    public DeferredBlock<?> getWallHangingSign() { return wallHangingSign; }
    public DeferredBlock<?> getShelf() {
        return shelf;
    }
    public DeferredItem<BoatItem> getBoatItem() {
        return boatItem;
    }
    public DeferredItem<BoatItem> getChestBoatItem() {
        return chestBoatItem;
    }
    public DeferredHolder<EntityType<?>, EntityType<Boat>> getBoatEntityType() {
        return boatEntityType;
    }
    public DeferredHolder<EntityType<?>, EntityType<ChestBoat>> getChestBoatEntityType() {
        return chestBoatEntityType;
    }

    public void setLog(DeferredBlock<?> block) { this.log = block; }
    public void setWood(DeferredBlock<?> block) { this.wood = block; }
    public void setStrippedLog(DeferredBlock<?> block) { this.strippedLog = block; }
    public void setStrippedWood(DeferredBlock<?> block) { this.strippedWood = block; }
    public void setPlanks(DeferredBlock<?> block) { this.planks = block; }
    public void setStairs(DeferredBlock<?> block) { this.stairs = block; }
    public void setSlab(DeferredBlock<?> block) { this.slab = block; }
    public void setFence(DeferredBlock<?> block) { this.fence = block; }
    public void setFenceGate(DeferredBlock<?> block) { this.fenceGate = block; }
    public void setDoor(DeferredBlock<?> block) { this.door = block; }
    public void setTrapdoor(DeferredBlock<?> block) { this.trapdoor = block; }
    public void setPressurePlate(DeferredBlock<?> block) { this.pressurePlate = block; }
    public void setButton(DeferredBlock<?> block) { this.button = block; }
    public void setSign(DeferredBlock<?> block) { this.sign = block; }
    public void setWallSign(DeferredBlock<?> block) { this.wallSign = block; }
    public void setHangingSign(DeferredBlock<?> block) { this.hangingSign = block; }
    public void setWallHangingSign(DeferredBlock<?> block) { this.wallHangingSign = block; }
    public void setShelf(DeferredBlock<?> shelf) {
        this.shelf = shelf;
    }
    public void setBoatItem(DeferredItem<BoatItem> boatItem) {
        this.boatItem = boatItem;
    }
    public void setChestBoatItem(DeferredItem<BoatItem> chestBoatItem) {
        this.chestBoatItem = chestBoatItem;
    }
    public void setBoatEntityType(DeferredHolder<EntityType<?>, EntityType<Boat>> boatEntityType) {
        this.boatEntityType = boatEntityType;
    }
    public void setChestBoatEntityType(DeferredHolder<EntityType<?>, EntityType<ChestBoat>> chestBoatEntityType) {
        this.chestBoatEntityType = chestBoatEntityType;
    }

    public Block[] getWoodSetBlocks() {
        return new Block[] {
                log.get(), wood.get(), strippedLog.get(), strippedWood.get(),
                planks.get(), stairs.get(), slab.get(), fence.get(), fenceGate.get(),
                door.get(), trapdoor.get(), pressurePlate.get(), button.get(),
                sign.get(), wallSign.get(), hangingSign.get(), wallHangingSign.get(), shelf.get()
        };
    }

    public Block[] getWoodSetMinusSlabAndDoorBlocks() {
        return new Block[] {
                log.get(), wood.get(), strippedLog.get(), strippedWood.get(),
                planks.get(), stairs.get(), fence.get(), fenceGate.get(),
                trapdoor.get(), pressurePlate.get(), button.get(),
                sign.get(), wallSign.get(), hangingSign.get(), wallHangingSign.get(), shelf.get()
        };
    }

    public Block[] getLogAndWoodBlocks() {
        return new Block[] {
                log.get(), wood.get(), strippedLog.get(), strippedWood.get()
        };
    }

    public Item[] getLogAndWoodItems() {
        return (Item[]) Arrays.stream(getLogAndWoodBlocks())
                .map(Block::asItem)
                .toArray();
    }
}