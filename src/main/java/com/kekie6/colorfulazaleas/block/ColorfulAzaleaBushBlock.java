package com.kekie6.colorfulazaleas.block;

import net.minecraft.block.AzaleaBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

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