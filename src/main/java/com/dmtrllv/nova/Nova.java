package com.dmtrllv.nova;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.dmtrllv.nova.data.worldgen.features.NovaConfiguredFeatures;
import com.dmtrllv.nova.data.worldgen.features.NovaFeatures;
import com.dmtrllv.nova.data.worldgen.placement.NovaPlacements;
import com.dmtrllv.nova.item.NovaItems;
// import com.dmtrllv.nova.renderer.NovaSkyRenderHandler;
import com.dmtrllv.nova.world.NovaBiomes;
import com.dmtrllv.nova.world.events.BloodMoonEvent;
import com.dmtrllv.nova.world.level.block.NovaBlocks;
import com.dmtrllv.nova.world.level.block.NovaWoodType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Nova.MOD_ID)
public class Nova
{
	public static final String MOD_ID = "nova";

	public static final Logger LOGGER = LogManager.getLogger();

	public Nova()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
	
		NovaBlocks.REGISTRY.register(bus);
		NovaItems.REGISTRY.register(bus);
		NovaBiomes.REGISTRY.register(bus);
		NovaFeatures.REGISTRY.register(bus);

		bus.addListener(this::onCommonSetup);
		bus.addListener(this::onClientSetup);
		
		MinecraftForge.EVENT_BUS.addListener(NovaBiomes::onBiomeLoading);
		MinecraftForge.EVENT_BUS.addListener(BloodMoonEvent::onTick);
		// MinecraftForge.EVENT_BUS.addListener(NovaSkyRenderHandler::initialize);
        // MinecraftForge.EVENT_BUS.addListener(NovaSkyRenderHandler::onFogDensityEvent);
        // MinecraftForge.EVENT_BUS.addListener(NovaSkyRenderHandler::onFogColorEvent);
	}

	@OnlyIn(Dist.CLIENT)
	private void initColors()
	{
		Minecraft m = Minecraft.getInstance();

		BlockColors blockColors = m.getBlockColors();

		blockColors.register((a, b, c, d) ->
		{
			return b != null && c != null ? BiomeColors.getAverageFoliageColor(b, c) : FoliageColor.getDefaultColor();
		}, NovaBlocks.WHITE_OAK_LEAVES.get());

		m.getItemColors().register((a, b) ->
		{
			BlockItem blockItem = ((BlockItem)a.getItem());
			return blockColors.getColor(blockItem.getBlock().defaultBlockState(), null, null, b);
		}, NovaBlocks.WHITE_OAK_LEAVES.get());
	}

	private void onCommonSetup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			NovaConfiguredFeatures.REGISTRY.register();
			NovaPlacements.REGISTRY.register();

			NovaBiomes.registerBiomes();
		});
	}

	private void onClientSetup(final FMLClientSetupEvent event)
	{
		event.enqueueWork(() ->
		{
			initColors();

			NovaWoodType.registerWoodTypes();
			NovaBlocks.setCutOutRenderers();
			NovaBlocks.setSolidRenderers();
		});
	}
}
