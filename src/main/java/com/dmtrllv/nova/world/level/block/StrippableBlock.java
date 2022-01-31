package com.dmtrllv.nova.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

public class StrippableBlock extends RotatedPillarBlock
{
	private final RegistryObject<Block> strippedBlock;

	public StrippableBlock(Properties properties, RegistryObject<Block> strippedBlock)
	{
		super(properties);
		this.strippedBlock = strippedBlock;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayResult)
	{
		if (player.getItemInHand(hand).getItem() instanceof AxeItem)
		{
			BlockState bs = world.getBlockState(pos);
			world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!world.isClientSide())
				world.setBlock(pos, strippedBlock.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, bs.getValue(RotatedPillarBlock.AXIS)), 11);
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}
}
