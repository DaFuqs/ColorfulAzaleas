package com.kekie6.colorfulazaleas.block;

import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.redstone.*;
import net.minecraft.world.phys.shapes.*;
import org.jspecify.annotations.*;

public class DroopingLeavesBlock extends Block {

    public static final VoxelShape SHAPE = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final VoxelShape EXTENDED_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final BooleanProperty EXTENDED = BooleanProperty.create("extended");

    public DroopingLeavesBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(EXTENDED,false));
    }

    @Override
    public boolean canSurvive(@NonNull BlockState state, LevelReader world, BlockPos pos) {
        BlockPos up = pos.above();
        BlockState support = world.getBlockState(up);
        return support.is(BlockTags.LEAVES) || support.is(this);
    }

    @Override
    protected @NonNull BlockState updateShape(BlockState state, @NonNull LevelReader worldView, @NonNull ScheduledTickAccess scheduledTickView, @NonNull BlockPos pos, @NonNull Direction direction, @NonNull BlockPos blockPos2, @NonNull BlockState blockState2, @NonNull RandomSource random) {
        if (!state.canSurvive(worldView,pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        
        return super.updateShape(state, worldView, scheduledTickView, pos, direction, blockPos2, blockState2, random);
    }
    
    @Override
    protected void neighborChanged(BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull Block block, @Nullable Orientation wireOrientation, boolean isMoving) {
        if (!state.canSurvive(world, pos)) {
            world.removeBlock(pos, false);
        } else {
            BlockState newState = state.setValue(EXTENDED, world.getBlockState(pos.below()).is(this));
            world.setBlock(pos, newState, Block.UPDATE_ALL);
        }
    }

    public @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter blockView, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return state.getValue(EXTENDED) ? EXTENDED_SHAPE : SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(EXTENDED);
    }
}