package com.dmtrllv.nova.world.level.block;

import java.util.Arrays;
import java.util.function.ToIntFunction;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.world.level.block.grower.WhiteOakTreeGrower;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NovaBlocks
{
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Nova.MOD_ID);

	@OnlyIn(Dist.CLIENT)
	public static void setCutOutRenderers()
	{
		Arrays.asList(WHITE_OAK_DOOR, WHITE_OAK_TRAPDOOR, WHITE_OAK_SAPLING).forEach((RegistryObject<Block> b) -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
	}

	@OnlyIn(Dist.CLIENT)
	public static void setSolidRenderers()
	{
		Arrays.asList(PEBBLE).forEach(b -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.solid()));
	}

	public static boolean never(BlockState state, BlockGetter getter, BlockPos pos)
	{
		return false;
	}

	public static Boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType)
	{
		return false;
	}

	public static StrippableBlock strippablelog(MaterialColor color, RegistryObject<Block> strippedVariant)
	{
		return new StrippableBlock(BlockBehaviour.Properties.of(Material.WOOD, color).strength(2.0F).sound(SoundType.WOOD), strippedVariant);
	}

	private static RotatedPillarBlock log(MaterialColor color)
	{
		return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD));
	}

	private static LeavesBlock leaves()
	{
		return leaves(SoundType.GRASS);
	}

	private static Boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType)
	{
		// Blocks.ACACIA_LOG
		return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
	}

	private static LeavesBlock leaves(SoundType soundType)
	{
		return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(soundType).noOcclusion().isValidSpawn(NovaBlocks::ocelotOrParrot).isSuffocating(NovaBlocks::never).isViewBlocking(NovaBlocks::never));
	}

	// private static RotatedPillarBlock log(MaterialColor p_50789_, MaterialColor
	// p_50790_)
	// {
	// return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD,
	// (blockState) -> {
	// return blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ?
	// p_50789_ : p_50790_;
	// }).strength(2.0F).sound(SoundType.WOOD));
	// }

	private static ToIntFunction<BlockState> litBlockEmission()
	{
		return litBlockEmission(BlockStateProperties.LEVEL);
	}

	private static ToIntFunction<BlockState> litBlockEmission(IntegerProperty prop)
	{
		return (state) -> state.getValue(prop);
	}

	// private static ToIntFunction<BlockState> litBlockEmission(Function<Integer, Integer> sup)
	// {
	// 	return litBlockEmission(BlockStateProperties.LEVEL, sup);
	// }

	// private static ToIntFunction<BlockState> litBlockEmission(IntegerProperty prop, Function<Integer, Integer> sup)
	// {
	// 	return (state) -> sup.apply(state.getValue(prop));
	// }

	public static final RegistryObject<Block> STRIPPED_WHITE_OAK_LOG = REGISTRY.register("stripped_white_oak_log", () -> log(MaterialColor.QUARTZ));
	public static final RegistryObject<Block> WHITE_OAK_LOG = REGISTRY.register("white_oak_log", () -> strippablelog(MaterialColor.QUARTZ, STRIPPED_WHITE_OAK_LOG));
	public static final RegistryObject<Block> STRIPPED_WHITE_OAK_WOOD = REGISTRY.register("stripped_white_oak_wood", () -> log(MaterialColor.QUARTZ));
	public static final RegistryObject<Block> WHITE_OAK_WOOD = REGISTRY.register("white_oak_wood", () -> strippablelog(MaterialColor.QUARTZ, STRIPPED_WHITE_OAK_WOOD));
	public static final RegistryObject<Block> WHITE_OAK_PLANKS = REGISTRY.register("white_oak_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.QUARTZ).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WHITE_OAK_STAIRS = REGISTRY.register("white_oak_stairs", () -> new StairBlock(() -> WHITE_OAK_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(WHITE_OAK_PLANKS.get())));
	public static final RegistryObject<Block> WHITE_OAK_SLAB = REGISTRY.register("white_oak_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WHITE_OAK_PLANKS.get())));
	public static final RegistryObject<Block> WHITE_OAK_DOOR = REGISTRY.register("white_oak_door", () -> new DoorBlock(Block.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> WHITE_OAK_TRAPDOOR = REGISTRY.register("white_oak_trapdoor", () -> new TrapDoorBlock(Block.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(NovaBlocks::never)));
	public static final RegistryObject<Block> WHITE_OAK_BUTTON = REGISTRY.register("white_oak_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WHITE_OAK_PRESSURE_PLATE = REGISTRY.register("white_oak_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).noCollission().strength(0.5F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WHITE_OAK_FENCE = REGISTRY.register("white_oak_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WHITE_OAK_FENCE_GATE = REGISTRY.register("white_oak_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

	// public static final RegistryObject<Block> WHITE_OAK_SIGN =
	// REGISTRY.register("white_oak_sign", () -> new
	// NovaStandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD),
	// NovaWoodType.WHITE_OAK));
	// public static final RegistryObject<Block> WHITE_OAK_WALL_SIGN =
	// REGISTRY.register("white_oak_wall_sign", () -> new
	// NovaWallSignBlock(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(()
	// -> WHITE_OAK_SIGN.get()), NovaWoodType.WHITE_OAK));

	public static final RegistryObject<Block> WHITE_OAK_SAPLING = REGISTRY.register("white_oak_sapling", () -> new SaplingBlock(new WhiteOakTreeGrower(), BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> WHITE_OAK_LEAVES = REGISTRY.register("white_oak_leaves", () -> leaves());
	public static final RegistryObject<Block> PEBBLE = REGISTRY.register("pebble", () -> new PebbleBlock(BlockBehaviour.Properties.of(Material.STONE).strength(0.5F).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Block> MOON_STONE_ORE = REGISTRY.register("moon_stone_ore", () -> new MoonStoneBlock(BlockBehaviour.Properties.of(Material.STONE).lightLevel(litBlockEmission()).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
}
