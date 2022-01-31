package com.dmtrllv.nova.world.level.block;

import com.dmtrllv.nova.world.level.block.entity.NovaSignBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class NovaStandingSignBlock extends StandingSignBlock
{

	public NovaStandingSignBlock(Properties props, WoodType woodType)
	{
		super(props, woodType);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new NovaSignBlockEntity(pos, state);
	}
}
