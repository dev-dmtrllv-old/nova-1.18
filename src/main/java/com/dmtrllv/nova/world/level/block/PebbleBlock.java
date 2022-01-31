package com.dmtrllv.nova.world.level.block;

import javax.annotation.Nullable;
import javax.imageio.ImageReader;

import com.dmtrllv.nova.item.NovaItems;
import com.dmtrllv.nova.state.properties.NovaBlockStateProperties;

import org.spongepowered.asm.mixin.injection.selectors.ISelectorContext;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;

public class PebbleBlock extends BushBlock
{
	protected static final VoxelShape ONE_PEBBLE_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
	protected static final VoxelShape MULTIPLE_PEBBLES_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

	public static final IntegerProperty PEBBLES = NovaBlockStateProperties.PEBBLES;

	public PebbleBlock(BlockBehaviour.Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(PEBBLES, Integer.valueOf(1)));
	}

	private boolean canSurviveOnBlock(BlockState s)
	{
		return s.is(Blocks.GRASS_BLOCK) || s.is(Blocks.DIRT) || s.is(Blocks.COARSE_DIRT) || s.is(Blocks.PODZOL) || s.is(Blocks.FARMLAND) || s.is(Blocks.DIRT_PATH) || s.is(Tags.Blocks.STONE) || s.is(Tags.Blocks.SAND) || s.is(Tags.Blocks.ORES);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos)
	{
		return canSurviveOnBlock(reader.getBlockState(pos.below()));
	}

	@Override
	public BlockState updateShape(BlockState s, Direction dir, BlockState s2, LevelAccessor world, BlockPos pos, BlockPos pos2)
	{
		return !s.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(s, dir, s2, world, pos, pos2);
	}

	private void decreasePebbles(LevelAccessor world, BlockPos blockPos, BlockState blockState)
	{
		world.playSound(null, blockPos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + world.getRandom().nextFloat() * 0.2F);
		int i = blockState.getValue(PEBBLES);
		if (i <= 1)
		{
			world.destroyBlock(blockPos, false);
		}
		else
		{
			world.setBlock(blockPos, blockState.setValue(PEBBLES, Integer.valueOf(i - 1)), 2);
			world.levelEvent(2001, blockPos, Block.getId(blockState));
		}

	}

	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity entity, ItemStack stack)
	{
		super.playerDestroy(level, player, pos, state, entity, stack);
		this.decreasePebbles(level, pos, state);
	}

	@SuppressWarnings("deprecation")
	public boolean canBeReplaced(BlockState p_196253_1_, BlockPlaceContext p_196253_2_)
	{
		return p_196253_2_.getItemInHand().getItem() == this.asItem() && p_196253_1_.getValue(PEBBLES) < 4 ? true : super.canBeReplaced(p_196253_1_, p_196253_2_);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext ctx)
	{
		BlockState blockstate = ctx.getLevel().getBlockState(ctx.getClickedPos());
		return blockstate.is(this) ? blockstate.setValue(PEBBLES, Integer.valueOf(Math.min(4, blockstate.getValue(PEBBLES) + 1))) : super.getStateForPlacement(ctx);
	}

	public VoxelShape getShape(BlockState blockState, ImageReader reader, BlockPos blockPos, ISelectorContext ctx)
	{
		return blockState.getValue(PEBBLES) > 1 ? MULTIPLE_PEBBLES_AABB : ONE_PEBBLE_AABB;
	}
	
	public VoxelShape getShape(BlockState blockState, BlockGetter reader, BlockPos blockPos, CollisionContext ctx)
	{
		return blockState.getValue(PEBBLES) > 1 ? MULTIPLE_PEBBLES_AABB : ONE_PEBBLE_AABB;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(PEBBLES);
	}

	@Override @SuppressWarnings("deprecation")
	public void neighborChanged(BlockState blockState, Level level, BlockPos posA, Block block, BlockPos posB, boolean bool)
	{
		super.neighborChanged(blockState, level, posA, block, posB, bool);

		if (!canSurviveOnBlock(level.getBlockState(posA.below())))
		{
			int l = blockState.getValue(PEBBLES).intValue() - 1;
			for (int i = 0; i < l; i++)
				dropResources(blockState, level, posA, null, null, new ItemStack(NovaItems.PEBBLE.get()));
		}
	}

	@Override
	public VoxelShape getCollisionShape(BlockState blockState, BlockGetter getter, BlockPos pos, CollisionContext ctx)
	{
		return getShape(blockState, getter, pos, ctx);
	}

	// public BlockBehaviour.OffsetType getOffsetType() {
	// 	return BlockBehaviour.OffsetType.XYZ;
	//  }
}
