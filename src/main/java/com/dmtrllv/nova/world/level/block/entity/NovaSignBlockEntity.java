package com.dmtrllv.nova.world.level.block.entity;

import net.minecraft.core.BlockPos;
// import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NovaSignBlockEntity extends SignBlockEntity
{
	public NovaSignBlockEntity(BlockPos pos, BlockState state)
	{
		super(pos, state);
	}

	// @Override
	// public BlockEntityType<?> getType()
	// {
		// return NovaBlockEntityType.SIGN.get();
	// }
}
