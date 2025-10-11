package com.kekie6.colorfulazaleas.util;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import net.minecraft.block.Block;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class WoodSet {
    private final String WoodSet;
    private final Identifier AZALEA_BOATS_ID;

    private Block log;
    private Block wood;
    private Block strippedLog;
    private Block strippedWood;
    private Block planks;
    private Block stairs;
    private Block slab;
    private Block fence;
    private Block fenceGate;
    private Block door;
    private Block trapdoor;
    private Block pressurePlate;
    private Block button;
    private Block sign;
    private Block wallSign;
    private Block hangingSign;
    private Block wallHangingSign;

    private BoatItem boatItem;
    private BoatItem chestBoatItem;

    public WoodSet(String WoodSet) {
        this.WoodSet = WoodSet;
        this.AZALEA_BOATS_ID = ColorfulAzaleas.id(WoodSet + "_azalea");
    }

    public String getWoodSet() { return WoodSet; }
    public Identifier getAzaleaBoatsId() {
        return AZALEA_BOATS_ID;
    }
    public Block getLog() { return log; }
    public Block getWood() { return wood; }
    public Block getStrippedLog() { return strippedLog; }
    public Block getStrippedWood() { return strippedWood; }
    public Block getPlanks() { return planks; }
    public Block getStairs() { return stairs; }
    public Block getSlab() { return slab; }
    public Block getFence() { return fence; }
    public Block getFenceGate() { return fenceGate; }
    public Block getDoor() { return door; }
    public Block getTrapdoor() { return trapdoor; }
    public Block getPressurePlate() { return pressurePlate; }
    public Block getButton() { return button; }
    public Block getSign() { return sign; }
    public Block getWallSign() { return wallSign; }
    public Block getHangingSign() { return hangingSign; }
    public Block getWallHangingSign() { return wallHangingSign; }
    public BoatItem getBoatItem() {
        return boatItem;
    }
    public BoatItem getChestBoatItem() {
        return chestBoatItem;
    }

    public void setLog(Block block) { this.log = block; }
    public void setWood(Block block) { this.wood = block; }
    public void setStrippedLog(Block block) { this.strippedLog = block; }
    public void setStrippedWood(Block block) { this.strippedWood = block; }
    public void setPlanks(Block block) { this.planks = block; }
    public void setStairs(Block block) { this.stairs = block; }
    public void setSlab(Block block) { this.slab = block; }
    public void setFence(Block block) { this.fence = block; }
    public void setFenceGate(Block block) { this.fenceGate = block; }
    public void setDoor(Block block) { this.door = block; }
    public void setTrapdoor(Block block) { this.trapdoor = block; }
    public void setPressurePlate(Block block) { this.pressurePlate = block; }
    public void setButton(Block block) { this.button = block; }
    public void setSign(Block block) { this.sign = block; }
    public void setWallSign(Block block) { this.wallSign = block; }
    public void setHangingSign(Block block) { this.hangingSign = block; }
    public void setWallHangingSign(Block block) { this.wallHangingSign = block; }
    public void setBoatItem(BoatItem boatItem) {
        this.boatItem = boatItem;
    }
    public void setChestBoatItem(BoatItem chestBoatItem) {
        this.chestBoatItem = chestBoatItem;
    }

    public Block[] getWoodSetBlocks() {
        return new Block[] {
                log, wood, strippedLog, strippedWood,
                planks, stairs, slab, fence, fenceGate,
                door, trapdoor, pressurePlate, button,
                sign, wallSign, hangingSign, wallHangingSign
        };
    }

    public Block[] getWoodSetMinusSlabAndDoorBlocks() {
        return new Block[] {
                log, wood, strippedLog, strippedWood,
                planks, stairs, fence, fenceGate,
                trapdoor, pressurePlate, button,
                sign, wallSign, hangingSign, wallHangingSign
        };
    }

    public Block[] getLogAndWoodBlocks() {
        return new Block[] {
                log, wood, strippedLog, strippedWood
        };
    }

    public Item[] getLogAndWoodItems() {
        return Arrays.stream(getLogAndWoodBlocks())
                .map(Block::asItem)
                .toArray(Item[]::new);
    }
}
/*
    public BlockFamily getFamily() {
        return new BlockFamily.Builder(getPlanks())
                .stairs(getStairs())
                .slab(getSlab())
                .fence(getFence())
                .fenceGate(getFenceGate())
                .door(getDoor())
                .trapdoor(getTrapdoor())
                .pressurePlate(getPressurePlate())
                .button(getButton())
                .group("wooden")
                .unlockCriterionName("has_planks")
                .noGenerateModels()
                .build();
    }*/
/*

    public final BlockFamily ColorfulAzaleasWoodSetFamily = new BlockFamily.Builder(getPlanks())
            .stairs(getStairs())
            .slab(getSlab())
            .fence(getFence())
            .fenceGate(getFenceGate())
            .door(getDoor())
            .trapdoor(getTrapdoor())
            .pressurePlate(getPressurePlate())
            .button(getButton())
            .group("wooden")
            .unlockCriterionName("has_planks")
            .noGenerateModels()
            .build();
*/


