package com.dmtrllv.nova.data.worldgen.placement;

import com.dmtrllv.nova.data.worldgen.features.NovaConfiguredFeatures;
import com.dmtrllv.nova.util.NovaRegistry;
import com.dmtrllv.nova.util.NovaRegistryObject;
import com.dmtrllv.nova.world.level.block.NovaBlocks;
import com.google.common.base.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

public final class NovaPlacements
{
	public static final NovaRegistry<PlacedFeature> REGISTRY = new NovaRegistry<PlacedFeature>(BuiltinRegistries.PLACED_FEATURE);

	private static final NovaRegistryObject<PlacedFeature> register(String name, Supplier<PlacedFeature> sup)
	{
		return REGISTRY.register(name, sup);
	}

	public static final NovaRegistryObject<PlacedFeature> PEBBLE_PATCH = register("pebble_patch", () -> NovaConfiguredFeatures.PEBBLES_PATCH.get().placed(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
	public static final NovaRegistryObject<PlacedFeature> WHITE_OAK_PLAINS = register("white_oak_plains", () -> NovaConfiguredFeatures.WHITE_OAK_TREES_PLAINS.get().placed(PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(NovaBlocks.WHITE_OAK_SAPLING.get().defaultBlockState(), BlockPos.ZERO)), BiomeFilter.biome()));
}
