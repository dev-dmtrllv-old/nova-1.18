package com.dmtrllv.nova.world.level.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.dmtrllv.nova.world.events.BloodMoonEvent;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class MoonStoneBlock extends NovaLightBlock
{
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

	public MoonStoneBlock(Properties props)
	{
		super(props, 5);
		this.registerDefaultState(stateDefinition.any().setValue(LIT, false).setValue(LEVEL, 0).setValue(WATERLOGGED, false));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx)
	{
		return updateBlockState(defaultBlockState(), BloodMoonEvent.isActive(ctx.getLevel().dayTime()));
	}

	@Override
	public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos, int fortune, int silktouch)
	{
		return state.getValue(LIT) ? (silktouch == 0 ? 3 + RANDOM.nextInt(5) : 0) : 0;
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random random)
	{
		updateBlock(level, pos, state, false);
	}

	private BlockState updateBlockState(BlockState state, boolean isActive)
	{
		return state.setValue(LIT, isActive).setValue(LEVEL, isActive ? this.lightLevel : 0);
	}

	private void updateBlock(ServerLevel level, BlockPos pos, BlockState state, boolean scheduleTick)
	{
		level.setBlock(pos, updateBlockState(state, BloodMoonEvent.isActive(level.dayTime())), 3);
		if (scheduleTick)
			level.scheduleTick(new BlockPos(pos), this, 30);
	}

	private void updateBlock(Level level, BlockPos pos, BlockState state, boolean scheduleTick)
	{
		level.setBlock(pos, updateBlockState(state, BloodMoonEvent.isActive(level.dayTime())), 3);
		if (scheduleTick)
			level.scheduleTick(new BlockPos(pos), this, 30);
	}

	@Override
	public boolean isRandomlyTicking(BlockState p_49921_)
	{
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
	{
		updateBlock(level, pos, state, false);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random)
	{
		updateBlock(level, pos, state, true);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean b)
	{
		if (!state.is(state2.getBlock()))
			level.scheduleTick(new BlockPos(pos), this, 30);

		super.onPlace(state, level, pos, state2, b);
	}
}
