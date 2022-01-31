package com.dmtrllv.nova.data.worldgen.features;

import com.dmtrllv.nova.Nova;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NovaFeatures
{
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, Nova.MOD_ID);

	public static final RegistryObject<Feature<CountConfiguration>> PEBBLE_PATCH = REGISTRY.register("pebble_patch", () -> new PebblePatchFeature(CountConfiguration.CODEC));
}
