package com.dmtrllv.nova.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NovaSignTileEntity extends SignBlockEntity
{

	public NovaSignTileEntity(BlockPos pos, BlockState state)
	{
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType()
	{
		return NovaTileEntityType.SIGN.get();
	}
}
