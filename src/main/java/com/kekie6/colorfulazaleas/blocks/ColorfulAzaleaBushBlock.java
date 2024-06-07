package com.kekie6.colorfulazaleas.blocks;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;

public class ColorfulAzaleaBushBlock extends AzaleaBlock {

    protected final TreeGrower treeGrower;

    public ColorfulAzaleaBushBlock(TreeGrower treeGrower, Properties properties) {
        super(properties);
        this.treeGrower = treeGrower;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
    }
}