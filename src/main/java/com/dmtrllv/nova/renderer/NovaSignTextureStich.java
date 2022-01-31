package com.dmtrllv.nova.renderer;

import com.dmtrllv.nova.Nova;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.api.distmarker.Dist;

@OnlyIn(Dist.CLIENT)
public class NovaSignTextureStich
{
	public static final Logger logger = LogManager.getLogger();

	public static final ResourceLocation WHITE_OAK = new ResourceLocation(Nova.MOD_ID, "entity/signs/white_oak");

	public static void onStitchEvent(final TextureStitchEvent.Pre event)
	{
		ResourceLocation stitching = event.getAtlas().location();

		if (stitching.equals(Sheets.SIGN_SHEET))
			event.addSprite(WHITE_OAK);
	}
}
