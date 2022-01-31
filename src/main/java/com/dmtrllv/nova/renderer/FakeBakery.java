package com.dmtrllv.nova.renderer;

import java.util.Set;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

public final class FakeBakery extends ModelBakery
{
	private FakeBakery(ResourceManager resourceManagerIn, BlockColors blockColorsIn, ProfilerFiller profilerIn, int maxMipmapLevel)
	{
		super(resourceManagerIn, blockColorsIn, profilerIn, maxMipmapLevel);
	}

	public static Set<Material> getBuiltinTextures()
	{
		return ModelBakery.UNREFERENCED_TEXTURES;
	}
}
