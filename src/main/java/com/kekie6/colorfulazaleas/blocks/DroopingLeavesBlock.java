package com.kekie6.colorfulazaleas.blocks;

import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.*;
/*
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.tags.BlockTags;
*/

public class DroopingLeavesBlock extends Block {

    public static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final VoxelShape EXTENDED_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final BooleanProperty EXTENDED = BooleanProperty.of("extended");

    public DroopingLeavesBlock(AbstractBlock.Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(EXTENDED,false));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView worldView, ScheduledTickView scheduledTickView, BlockPos pos, Direction direction, BlockPos blockPos2, BlockState blockState2, Random random) {
        if (!state.canPlaceAt(worldView,pos)) {
            return Blocks.AIR.getDefaultState();
        }
        
        return super.getStateForNeighborUpdate(state, worldView, scheduledTickView, pos, direction, blockPos2, blockState2, random);
    }
    
    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, @Nullable WireOrientation wireOrientation, boolean isMoving) {
        if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false);
        } else {
            BlockState newState = state.with(EXTENDED, world.getBlockState(pos.down()).isOf(this));
            world.setBlockState(pos, newState, Block.NOTIFY_ALL);
        }
    }

    public @NotNull VoxelShape getShape(BlockState state, BlockView blockView, BlockPos pos, ShapeContext context) {
        if (state.get(EXTENDED)) return EXTENDED_SHAPE;
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(EXTENDED);
    }

    public boolean canSurvive(BlockState state, WorldView worldView, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = worldView.getBlockState(blockPos);
        return blockState.isIn(BlockTags.LEAVES) || blockState.isOf(this);
    }
}