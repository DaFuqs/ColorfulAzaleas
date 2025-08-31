package com.kekie6.colorfulazaleas.blocks;

import net.minecraft.block.AzaleaBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
/*
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
*/

public class ColorfulAzaleaBushBlock extends AzaleaBlock {

    protected final SaplingGenerator treeGrower;

    public ColorfulAzaleaBushBlock(SaplingGenerator saplingGenerator, Settings settings) {
        super(settings);
        this.treeGrower = saplingGenerator;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random, BlockPos pos, BlockState state) {
        treeGrower.generate(serverWorld, serverWorld.getChunkManager().getChunkGenerator(), pos, state, random);
    }
}