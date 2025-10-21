package com.kekie6.colorfulazaleas.decorators;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import org.jetbrains.annotations.*;
import java.util.Comparator;

public class ColorfulTreeDecorator extends TreeDecorator {

    public static final MapCodec<ColorfulTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStateProvider.TYPE_CODEC.fieldOf("top_leaf_block").forGetter(ColorfulTreeDecorator::getTopLeafBlock),
            BlockStateProvider.TYPE_CODEC.fieldOf("leaf_block").forGetter(ColorfulTreeDecorator::getLeafBlock),
            BlockStateProvider.TYPE_CODEC.fieldOf("hanging_block").forGetter(ColorfulTreeDecorator::getHangingBlock),
            BlockStateProvider.TYPE_CODEC.fieldOf("log_block").forGetter(ColorfulTreeDecorator::getLogBlock),
            IntProvider.VALUE_CODEC.fieldOf("leaf_height").forGetter(ColorfulTreeDecorator::getLeafHeight),
            IntProvider.VALUE_CODEC.fieldOf("hanging_height").forGetter(ColorfulTreeDecorator::getHangingHeight),
            Codecs.POSITIVE_FLOAT.fieldOf("chance").forGetter(ColorfulTreeDecorator::getChance)
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
    protected @NotNull TreeDecoratorType<?> getType() {
        return ColorfulAzaleas.COLORFUL_TREE_DECORATOR;
    }

    @Override
    public void generate(Generator context) {
        Random random = context.getRandom();

        ObjectArrayList<BlockPos> logs = new ObjectArrayList<>(context.getLogPositions());
        logs.sort(Comparator.comparingInt(Vec3i::getY).reversed());
        BlockPos bottomLog = logs.getFirst();
        for (Direction acceptablePos : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() >= 0.55f) continue;
            BlockPos placementPosition = bottomLog.offset(acceptablePos).toImmutable();
            if (context.isAir(placementPosition)) {
                context.replace(placementPosition, this.getLogBlock().get(context.getRandom(), placementPosition));
            }
        }

        for (BlockPos leaf : context.getLeavesPositions()) {
            boolean airBelow = context.isAir(leaf.down());
            if(airBelow) {
                context.replace(leaf, getTopLeafBlock().get(random, leaf));
            }

            if(!airBelow) continue;
            if (random.nextFloat() >= chance) continue;

            // place down
            int hangingCount = getHangingHeight().get(random);
            for (int i = 1; i <= hangingCount; i++) {
                BlockPos belowPos = leaf.down(i);
                if (context.isAir(belowPos)) {
                    context.replace(belowPos, getHangingBlock().get(random, belowPos));
                }
            }

            // place up
            int leafCount = getLeafHeight().get(random);
            for (int i = 0; i < leafCount; i++) {

                boolean leafAbove = context.getLeavesPositions().contains(leaf.up(i + 1));
                BlockStateProvider provider = (i == leafCount - 1 || !leafAbove) ? getTopLeafBlock() : getLeafBlock();
                BlockPos currLeafPos = leaf.up(i);
                context.replace(currLeafPos, provider.get(random, currLeafPos));

                if(!leafAbove) break;
            }
        }
    }
}