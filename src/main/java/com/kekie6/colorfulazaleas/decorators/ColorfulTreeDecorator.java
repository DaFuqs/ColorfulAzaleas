package com.kekie6.colorfulazaleas.decorators;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ColorfulTreeDecorator extends TreeDecorator {

    public static final MapCodec<ColorfulTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("leaf_block").forGetter(ColorfulTreeDecorator::getLeafBlock),
            BlockStateProvider.CODEC.fieldOf("log_block").forGetter(ColorfulTreeDecorator::getLogBlock)
    ).apply(instance, ColorfulTreeDecorator::new));

    public final BlockStateProvider leafBlock;
    public final BlockStateProvider logBlock;

    public ColorfulTreeDecorator(BlockStateProvider leafBlock, BlockStateProvider logBlock) {
        this.leafBlock = leafBlock;
        this.logBlock = logBlock;
    }

    public BlockStateProvider getLeafBlock() {
        return leafBlock;
    }

    public BlockStateProvider getLogBlock() {
        return logBlock;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return ColorfulAzaleas.COLORFUL_TREE_DECORATOR;
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();

        for (BlockPos leaf : context.leaves()) {
            if (random.nextFloat() >= 0.4f) continue;
            int limit = random.nextInt(2, 4);
            for (int i = 1; i <= limit; i++) {
                BlockPos belowPos = leaf.below(i);
                if (context.isAir(belowPos)) {
                    context.setBlock(belowPos, this.getLeafBlock().getState(random, belowPos));
                }
            }
        }

        ObjectArrayList<BlockPos> logs = new ObjectArrayList<>(context.logs());
        logs.sort(Comparator.comparingInt(Vec3i::getY).reversed());
        BlockPos bottomLog = logs.getFirst();
        for (Direction acceptablePos : Direction.Plane.HORIZONTAL) {
            if (random.nextFloat() >= 0.55f) continue;
            BlockPos placementPosition = bottomLog.relative(acceptablePos).immutable();
            if (context.isAir(placementPosition)) {
                context.setBlock(placementPosition, this.getLogBlock().getState(context.random(), placementPosition));
            }
        }
    }
}