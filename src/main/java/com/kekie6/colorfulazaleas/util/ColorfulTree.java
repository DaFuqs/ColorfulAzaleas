package com.kekie6.colorfulazaleas.util;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.*;

import java.util.*;

public class ColorfulTree {
    private final AzaleaColor color;
    private final String colorTitle;
    private final int tint;

    private AzaleaWoodSet woodSet;
    private DeferredBlock<?> sapling;
    private DeferredBlock<?> pottedSapling;
    private DeferredBlock<?> azaleaLeaves;
    private DeferredBlock<?> floweringLeaves;
    private DeferredBlock<?> bloomingLeaves;
    private DeferredBlock<?> droopingLeaves;

    public ColorfulTree(AzaleaColor color) {
        this.color = color;
        this.colorTitle = color.getTitle();
        this.tint = color.getTint();
    }

    public AzaleaColor getColor() { return color; }
    public String getColorTitle() { return colorTitle; }
    public int getTint() { return tint; }

    public AzaleaWoodSet getWoodSet() { return woodSet; }
    public DeferredBlock<?> getSapling() { return sapling; }
    public DeferredBlock<?> getPottedSapling() { return pottedSapling; }
    public DeferredBlock<?> getAzaleaLeaves() { return azaleaLeaves; }
    public DeferredBlock<?> getFloweringLeaves() { return floweringLeaves; }
    public DeferredBlock<?> getBloomingLeaves() { return bloomingLeaves; }
    public DeferredBlock<?> getDroopingLeaves() { return droopingLeaves; }

    public void setWoodSet(AzaleaWoodSet woodSet) { this.woodSet = woodSet; }
    public void setSapling(DeferredBlock<?> block) { this.sapling = block; }
    public void setPottedSapling(DeferredBlock<?> block) { this.pottedSapling = block; }
    public void setAzaleaLeaves(DeferredBlock<?> block) { this.azaleaLeaves = block; }
    public void setFloweringLeaves(DeferredBlock<?> block) { this.floweringLeaves = block; }
    public void setBloomingLeaves(DeferredBlock<?> block) { this.bloomingLeaves = block; }
    public void setDroopingLeaves(DeferredBlock<?> block) { this.droopingLeaves = block; }

    public Block[] getLeavesAndDroopingBlocks() {
        return new Block[] {
                azaleaLeaves.get(), floweringLeaves.get(), bloomingLeaves.get(), droopingLeaves.get()
        };
    }

    public Block[] getLeavesBlocks() {
        return new Block[] {
                azaleaLeaves.get(), floweringLeaves.get(), bloomingLeaves.get()
        };
    }

    public Item[] getLeavesItems() {
        return Arrays.stream(getLeavesBlocks())
                .map(Block::asItem)
                .toArray(Item[]::new);
    }
}
