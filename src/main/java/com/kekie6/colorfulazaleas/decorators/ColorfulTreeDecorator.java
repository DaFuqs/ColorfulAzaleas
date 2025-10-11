package com.kekie6.colorfulazaleas.decorators;

import com.kekie6.colorfulazaleas.ColorfulAzaleas;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import org.jetbrains.annotations.*;
import java.util.Comparator;

public class ColorfulTreeDecorator extends TreeDecorator {

    public static final MapCodec<ColorfulTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStateProvider.TYPE_CODEC.fieldOf("leaf_block").forGetter(ColorfulTreeDecorator::getLeafBlock),
            BlockStateProvider.TYPE_CODEC.fieldOf("log_block").forGetter(ColorfulTreeDecorator::getLogBlock)
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
    protected @NotNull TreeDecoratorType<?> getType() {
        return ColorfulAzaleas.COLORFUL_TREE_DECORATOR;
    }

    @Override
    public void generate(Generator context) {
        Random random = context.getRandom();

        for (BlockPos leaf : context.getLeavesPositions()) {
            if (random.nextFloat() >= 0.4f) continue;
            int limit = random.nextBetweenExclusive(2, 4);
            for (int i = 1; i <= limit; i++) {
                BlockPos belowPos = leaf.down(i);
                if (context.isAir(belowPos)) {
                    context.replace(belowPos, this.getLeafBlock().get(random, belowPos));
                }
            }
        }

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
    }
}