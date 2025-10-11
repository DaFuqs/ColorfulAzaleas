package com.kekie6.colorfulazaleas.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Arrays;

public class ColorfulTree {
    private final AzaleaColors color;
    private final String colorTitle;
    private final int tint;

    private WoodSet woodSet;
    private Block sapling;
    private Block pottedSapling;
    private Block azaleaLeaves;
    private Block floweringLeaves;
    private Block bloomingLeaves;
    private Block droopingLeaves;

    public ColorfulTree(AzaleaColors color) {
        this.color = color;
        this.colorTitle = color.getTitle();
        this.tint = color.getTint();
    }

    public AzaleaColors getColor() { return color; }
    public String getColorTitle() { return colorTitle; }
    public int getTint() { return tint; }

    public WoodSet getWoodSet() { return woodSet; }
    public Block getSapling() { return sapling; }
    public Block getPottedSapling() { return pottedSapling; }
    public Block getAzaleaLeaves() { return azaleaLeaves; }
    public Block getFloweringLeaves() { return floweringLeaves; }
    public Block getBloomingLeaves() { return bloomingLeaves; }
    public Block getDroopingLeaves() { return droopingLeaves; }

    public void setWoodSet(WoodSet woodSet) { this.woodSet = woodSet; }
    public void setSapling(Block block) { this.sapling = block; }
    public void setPottedSapling(Block block) { this.pottedSapling = block; }
    public void setAzaleaLeaves(Block block) { this.azaleaLeaves = block; }
    public void setFloweringLeaves(Block block) { this.floweringLeaves = block; }
    public void setBloomingLeaves(Block block) { this.bloomingLeaves = block; }
    public void setDroopingLeaves(Block block) { this.droopingLeaves = block; }

    public Block[] getSaplingLeavesPotBlocks() {
        return new Block[] {
                sapling, pottedSapling,azaleaLeaves, floweringLeaves,
                bloomingLeaves, droopingLeaves
        };
    }

    public Block[] getSaplingAndLeavesBlocks() {
        return new Block[] {
                sapling, azaleaLeaves, floweringLeaves,
                bloomingLeaves, droopingLeaves
        };
    }

    public Block[] getLeavesAndDroopingBlocks() {
        return new Block[] {
                azaleaLeaves, floweringLeaves, bloomingLeaves, droopingLeaves
        };
    }

    public Block[] getLeavesBlocks() {
        return new Block[] {
                azaleaLeaves, floweringLeaves, bloomingLeaves
        };
    }

    public Item[] getLeavesItems() {
        return Arrays.stream(getLeavesBlocks())
                .map(Block::asItem)
                .toArray(Item[]::new);
    }
}
