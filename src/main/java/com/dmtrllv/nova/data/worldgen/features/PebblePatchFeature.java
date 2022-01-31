package com.dmtrllv.nova.data.worldgen.features;

import com.dmtrllv.nova.world.level.block.NovaBlocks;
import com.dmtrllv.nova.world.level.block.PebbleBlock;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Random;

public class PebblePatchFeature extends Feature<CountConfiguration>
{
	public PebblePatchFeature(Codec<CountConfiguration> codec)
	{
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<CountConfiguration> p_160316_)
	{
		int i = 0;
		Random random = p_160316_.random();
		WorldGenLevel worldgenlevel = p_160316_.level();
		BlockPos blockpos = p_160316_.origin();
		int j = p_160316_.config().count().sample(random);

		for (int k = 0; k < j; ++k)
		{
			int l = random.nextInt(8) - random.nextInt(8);
			int i1 = random.nextInt(8) - random.nextInt(8);
			int j1 = worldgenlevel.getHeight(Heightmap.Types.WORLD_SURFACE, blockpos.getX() + l, blockpos.getZ() + i1);
			BlockPos blockpos1 = new BlockPos(blockpos.getX() + l, j1, blockpos.getZ() + i1);
			int numOfPebbles = random.nextInt(4) + 1;
			BlockState blockstate = NovaBlocks.PEBBLE.get().defaultBlockState().setValue(PebbleBlock.PEBBLES, Integer.valueOf(numOfPebbles));
			if (blockstate.canSurvive(worldgenlevel, blockpos1))
			{
				worldgenlevel.setBlock(blockpos1, blockstate, 2);
				++i;
			}
		}

		return i > 0;
	}
}
