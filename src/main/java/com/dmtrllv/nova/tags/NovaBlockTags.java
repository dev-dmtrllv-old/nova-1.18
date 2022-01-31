package com.dmtrllv.nova.tags;

import com.dmtrllv.nova.Nova;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public final class NovaBlockTags
{
	private static final Tags.IOptionalNamedTag<Block> createTag(String name)
	{
		return BlockTags.createOptional(new ResourceLocation(Nova.MOD_ID, name));
	}

	// private static final Tags.IOptionalNamedTag<Block> createForgeTag(String name)
	// {
	// 	return BlockTags.createOptional(new ResourceLocation("forge", name));
	// }

	public static final Tags.IOptionalNamedTag<Block> PEBBLE_MINABLE = createTag("mineable/pebble");

}
