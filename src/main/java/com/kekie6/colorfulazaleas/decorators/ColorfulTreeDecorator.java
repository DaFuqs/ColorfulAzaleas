package com.kekie6.colorfulazaleas.decorators;

import com.kekie6.colorfulazaleas.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import net.minecraft.world.level.levelgen.feature.treedecorators.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ColorfulTreeDecorator extends TreeDecorator {

    public static final MapCodec<ColorfulTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("top_leaf_block").forGetter(ColorfulTreeDecorator::getTopLeafBlock),
            BlockStateProvider.CODEC.fieldOf("leaf_block").forGetter(ColorfulTreeDecorator::getLeafBlock),
            BlockStateProvider.CODEC.fieldOf("hanging_block").forGetter(ColorfulTreeDecorator::getHangingBlock),
            BlockStateProvider.CODEC.fieldOf("log_block").forGetter(ColorfulTreeDecorator::getLogBlock),
            IntProvider.CODEC.fieldOf("leaf_height").forGetter(ColorfulTreeDecorator::getLeafHeight),
            IntProvider.CODEC.fieldOf("hanging_height").forGetter(ColorfulTreeDecorator::getHangingHeight),
            ExtraCodecs.POSITIVE_FLOAT.fieldOf("chance").forGetter(ColorfulTreeDecorator::getChance)
    ).apply(instance, ColorfulTreeDecorator::new));

    public final BlockStateProvider topLeafBlock;
    public final BlockStateProvider leafBlock;
    public final BlockStateProvider hangingBlock;
    public final BlockStateProvider logBlock;
    public final IntProvider leafHeight;
    public final IntProvider hangingHeight;
    public final float chance;

    public ColorfulTreeDecorator(BlockStateProvider topLeafBlock, BlockStateProvider leafBlock, BlockStateProvider hangingBlock, BlockStateProvider logBlock, IntProvider leafHeight, IntProvider hangingHeight, float chance) {
        this.topLeafBlock = topLeafBlock;
        this.leafBlock = leafBlock;
        this.hangingBlock = hangingBlock;
        this.logBlock = logBlock;
        this.leafHeight = leafHeight;
        this.hangingHeight = hangingHeight;
        this.chance = chance;
    }

    public BlockStateProvider getTopLeafBlock() {
        return topLeafBlock;
    }

    public BlockStateProvider getLeafBlock() {
        return leafBlock;
    }

    public BlockStateProvider getHangingBlock() {
        return hangingBlock;
    }

    public BlockStateProvider getLogBlock() {
        return logBlock;
    }

    public IntProvider getLeafHeight() {
        return leafHeight;
    }

    public IntProvider getHangingHeight() {
        return hangingHeight;
    }

    public float getChance() {
        return chance;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return ColorfulAzaleas.COLORFUL_TREE_DECORATOR;
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();

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

        for (BlockPos leaf : context.leaves()) {
            boolean airBelow = context.isAir(leaf.below());
            if(airBelow) {
                context.setBlock(leaf, getTopLeafBlock().getState(random, leaf));
            }

            if(!airBelow) continue;
            if (random.nextFloat() >= chance) continue;

            // place down
            int hangingCount = getHangingHeight().sample(random);
            for (int i = 1; i <= hangingCount; i++) {
                BlockPos belowPos = leaf.below(i);
                if (context.isAir(belowPos)) {
                    context.setBlock(belowPos, getHangingBlock().getState(random, belowPos));
                }
            }

            // place up
            int leafCount = getLeafHeight().sample(random);
            for (int i = 0; i < leafCount; i++) {

                boolean leafAbove = context.leaves().contains(leaf.above(i + 1));
                BlockStateProvider provider = (i == leafCount - 1 || !leafAbove) ? getTopLeafBlock() : getLeafBlock();
                BlockPos currLeafPos = leaf.above(i);
                context.setBlock(currLeafPos, provider.getState(random, currLeafPos));

                if(!leafAbove) break;
            }
        }
    }
}