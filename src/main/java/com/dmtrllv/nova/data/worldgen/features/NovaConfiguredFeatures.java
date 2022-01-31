package com.dmtrllv.nova.data.worldgen.features;

import java.util.List;
import java.util.OptionalInt;

import com.dmtrllv.nova.util.NovaRegistry;
import com.dmtrllv.nova.util.NovaRegistryObject;
import com.dmtrllv.nova.world.level.block.NovaBlocks;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.registries.RegistryObject;

public final class NovaConfiguredFeatures
{
	public static final NovaRegistry<ConfiguredFeature<?, ?>> REGISTRY = new NovaRegistry<>(BuiltinRegistries.CONFIGURED_FEATURE);

	public static final NovaRegistryObject<ConfiguredFeature<?, ?>> PEBBLES_PATCH = REGISTRY.register("pebble_patch", () -> NovaFeatures.PEBBLE_PATCH.get().configured(new CountConfiguration(20)));

	// private static final BeehiveDecorator BEEHIVE_0002 = new BeehiveDecorator(0.002F);
	// private static final BeehiveDecorator BEEHIVE_002 = new BeehiveDecorator(0.02F);
	private static final BeehiveDecorator BEEHIVE_005 = new BeehiveDecorator(0.05F);
	// private static final BeehiveDecorator BEEHIVE = new BeehiveDecorator(1.0F);

	
	public static final NovaRegistryObject<ConfiguredFeature<?, ?>> WHITE_OAK = REGISTRY.register("white_oak", () -> Feature.TREE.configured(createWhiteOak().build()));
	public static final NovaRegistryObject<ConfiguredFeature<?, ?>> FANCY_WHITE_OAK = REGISTRY.register("fancy_white_oak", () -> Feature.TREE.configured(createFancyWhiteOak().build()));
	
	public static final NovaRegistryObject<ConfiguredFeature<?, ?>> WHITE_OAK_BEES_005 = REGISTRY.register("white_oak_bees_005", () -> Feature.TREE.configured(createWhiteOak().decorators(List.of(BEEHIVE_005)).build()));
	public static final NovaRegistryObject<ConfiguredFeature<?, ?>> FANCY_WHITE_OAK_BEES_005 = REGISTRY.register("fancy_white_oak_bees_005", () -> Feature.TREE.configured(createFancyWhiteOak().decorators(List.of(BEEHIVE_005)).build()));

	public static final NovaRegistryObject<ConfiguredFeature<?, ?>> WHITE_OAK_TREES_PLAINS = REGISTRY.register("trees_plains", () -> Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WHITE_OAK_BEES_005.get().placed(), 0.16F), new WeightedPlacedFeature(FANCY_WHITE_OAK_BEES_005.get().placed(), 0.16F), new WeightedPlacedFeature(TreeFeatures.FANCY_OAK_BEES_005.placed(), 0.33333334F)), TreeFeatures.OAK_BEES_005.placed())));

	private static TreeConfiguration.TreeConfigurationBuilder createWhiteOak()
	{
		return createTree(NovaBlocks.WHITE_OAK_LOG, NovaBlocks.WHITE_OAK_LEAVES, 4, 2, 0, 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder createFancyWhiteOak()
	{
		return createFancyTree(NovaBlocks.WHITE_OAK_LOG, NovaBlocks.WHITE_OAK_LEAVES, 3, 11, 0, 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder createTree(RegistryObject<Block> trunkBlock, RegistryObject<Block> leavesBlock, int baseHeight, int randHeightA, int randHeightB, int leavesRadius)
	{
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(trunkBlock.get()), new StraightTrunkPlacer(baseHeight, randHeightA, randHeightB), BlockStateProvider.simple(leavesBlock.get()), new BlobFoliagePlacer(ConstantInt.of(leavesRadius), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
	}

	private static TreeConfiguration.TreeConfigurationBuilder createFancyTree(RegistryObject<Block> trunkBlock, RegistryObject<Block> leavesBlock, int baseHeight, int randHeightA, int randHeightB, int leavesRadius)
	{
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(trunkBlock.get()), new FancyTrunkPlacer(baseHeight, randHeightA, randHeightB), BlockStateProvider.simple(Blocks.OAK_LEAVES), new FancyFoliagePlacer(ConstantInt.of(leavesRadius), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))));
	}
}
