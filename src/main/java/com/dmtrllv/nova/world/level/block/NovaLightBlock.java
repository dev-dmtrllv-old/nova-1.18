package com.dmtrllv.nova.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NovaLightBlock extends LightBlock
{
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

	protected final int lightLevel;

	public NovaLightBlock(Properties props, int lightLevel)
	{
		super(props);
		this.lightLevel = lightLevel;
		this.registerDefaultState(stateDefinition.any().setValue(LIT, true).setValue(LEVEL, this.lightLevel).setValue(WATERLOGGED, false));
	}

	@Override
	public RenderShape getRenderShape(BlockState state)
	{
		return RenderShape.MODEL;
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state)
	{
		return false;
	}

	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
	{
		return Shapes.block();
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
	{
		return InteractionResult.PASS;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2)
	{
		return state;
	}

	@Override
	public FluidState getFluidState(BlockState state)
	{
		return Fluids.EMPTY.defaultFluidState();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(LIT, LEVEL, WATERLOGGED);
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter p_153664_, BlockPos p_153665_, BlockState p_153666_)
	{
		return new ItemStack(this);
	}
}
