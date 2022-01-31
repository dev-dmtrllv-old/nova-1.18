package com.dmtrllv.nova.world.level.block;

import com.dmtrllv.nova.world.level.block.entity.NovaSignBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class NovaWallSignBlock extends WallSignBlock
{
	public NovaWallSignBlock(Properties p_i225766_1_, WoodType p_i225766_2_)
	{
		super(p_i225766_1_, p_i225766_2_);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new NovaSignBlockEntity(pos, state);
	}
}
