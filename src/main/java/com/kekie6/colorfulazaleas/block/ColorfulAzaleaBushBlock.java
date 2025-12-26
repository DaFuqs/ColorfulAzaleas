package com.kekie6.colorfulazaleas.block;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;
import org.jspecify.annotations.*;

public class ColorfulAzaleaBushBlock extends AzaleaBlock {
    protected final TreeGrower treeGrower;

    public ColorfulAzaleaBushBlock(TreeGrower saplingGenerator, Properties settings) {
        super(settings);
        this.treeGrower = saplingGenerator;
    }

    @Override
    public void performBonemeal(@NonNull ServerLevel serverWorld, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        treeGrower.growTree(serverWorld, serverWorld.getChunkSource().getGenerator(), pos, state, random);
    }
}