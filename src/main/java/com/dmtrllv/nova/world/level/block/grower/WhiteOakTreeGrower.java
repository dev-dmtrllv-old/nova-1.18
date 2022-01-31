package com.dmtrllv.nova.world.level.block.grower;

import java.util.Random;

import com.dmtrllv.nova.data.worldgen.features.NovaConfiguredFeatures;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WhiteOakTreeGrower extends AbstractTreeGrower
{
	protected ConfiguredFeature<?, ?> getConfiguredFeature(Random random, boolean withBeeHive)
	{
		return withBeeHive ? NovaConfiguredFeatures.WHITE_OAK_BEES_005.get() : NovaConfiguredFeatures.WHITE_OAK.get();
	}
}
