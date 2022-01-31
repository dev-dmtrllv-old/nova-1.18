package com.dmtrllv.nova.world;

import java.util.ArrayList;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.data.worldgen.placement.NovaPlacements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class NovaBiomes
{
	private static final class BiomeInfo
	{
		private String name;
		private BiomeDictionary.Type[] types;

		public BiomeInfo(String name, BiomeDictionary.Type... types)
		{
			this.name = name;
			this.types = types;
		}

		public void register()
		{
			ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Nova.MOD_ID, name));
			BiomeManager.addAdditionalOverworldBiomes(key);
			BiomeDictionary.addTypes(key, types);
		}
	};

	private static final Logger LOGGER = LogManager.getLogger();

	public static final ArrayList<BiomeInfo> BIOMES = new ArrayList<>();
	public static final DeferredRegister<Biome> REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, Nova.MOD_ID);
	public static final RegistryObject<Biome> WHITE_OAK_PLAINS = register("white_oak_plains", () -> whiteOakPlains(false, false, false), BiomeDictionary.Type.DRY, BiomeDictionary.Type.PLAINS);

	public static final RegistryObject<Biome> register(String name, Supplier<Biome> supplier, BiomeDictionary.Type... types)
	{
		BIOMES.add(new BiomeInfo(name, types));
		return REGISTRY.register(name, supplier);
	}

	// private static final Optional<ConfiguredFeature<?, ?>> resolve(PlacedFeature
	// feature, ConfiguredFeature<?, ?> match)
	// {
	// return feature.getFeatures().filter(configuredFeature ->
	// configuredFeature.equals(match)).findFirst();
	// }

	public static final void onBiomeLoading(BiomeLoadingEvent biome)
	{
		if (biome.getCategory() == Biome.BiomeCategory.PLAINS || biome.getCategory() == Biome.BiomeCategory.RIVER || biome.getCategory() == Biome.BiomeCategory.FOREST || biome.getCategory() == Biome.BiomeCategory.BEACH)
		{
			biome.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> NovaPlacements.PEBBLE_PATCH.get());
			LOGGER.info("Added pebbles to " + biome.getName().toString() + "!");
		}
		if(biome.getName().compareTo(Biomes.SUNFLOWER_PLAINS.getRegistryName()) == 0)
		{
			biome.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).removeIf((x) -> x.get().getFeatures().anyMatch(f -> (boolean)(f.feature.getRegistryName().compareTo(VegetationFeatures.TREES_PLAINS.feature.getRegistryName()) == 0)));
			biome.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> NovaPlacements.WHITE_OAK_PLAINS.get());
		}
		// if (biome.getCategory() == Biome.BiomeCategory.PLAINS)
		// {
		// 	LogManager.getLogger().info("Changed " + biome.getName().toString() + " biome!");
		// }
	}

	public static final void registerBiomes()
	{
		for (BiomeInfo info : BIOMES)
			info.register();
	}

	// #region biome create methods
	private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music music)
	{
		return biome(precipitation, category, temperature, downfall, 4159204, 329011, mobSpawnSettings, generationSettings, music);
	}

	private static Biome biome(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder mobSpwanSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music music)
	{
		return (new Biome.BiomeBuilder()).precipitation(precipitation).biomeCategory(category).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(mobSpwanSettings.build()).generationSettings(generationSettings.build()).build();
	}

	private static final int calculateSkyColor(float f)
	{
		float f2 = f / 3.0F;
		f2 = Mth.clamp(f2, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - f2 * 0.05F, 0.5F + f2 * 0.1F, 1.0F);
	}

	private static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder)
	{
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addDefaultSprings(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
	}

	public static void addPlainVegetation(BiomeGenerationSettings.Builder builder, PlacedFeature treePlaceFeature)
	{
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, treePlaceFeature);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_PLAINS);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
	}

	public static Biome whiteOakPlains(boolean withSunflowers, boolean snowy, boolean iceSpikes)
	{
		MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();
		globalOverworldGeneration(biomegenerationsettings$builder);
		if (snowy)
		{
			mobspawnsettings$builder.creatureGenerationProbability(0.07F);
			BiomeDefaultFeatures.snowySpawns(mobspawnsettings$builder);
			if (iceSpikes)
			{
				biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE);
				biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);
			}
		} else
		{
			BiomeDefaultFeatures.plainsSpawns(mobspawnsettings$builder);
			BiomeDefaultFeatures.addPlainGrass(biomegenerationsettings$builder);
			if (withSunflowers)
				biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUNFLOWER);
		}

		BiomeDefaultFeatures.addDefaultOres(biomegenerationsettings$builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
		if (snowy)
		{
			BiomeDefaultFeatures.addSnowyTrees(biomegenerationsettings$builder);
			BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
			BiomeDefaultFeatures.addDefaultGrass(biomegenerationsettings$builder);
		} else
		{
			addPlainVegetation(biomegenerationsettings$builder, NovaPlacements.WHITE_OAK_PLAINS.get());
		}

		BiomeDefaultFeatures.addDefaultMushrooms(biomegenerationsettings$builder);

		if (withSunflowers)
		{
			biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
			biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
		} else
		{
			BiomeDefaultFeatures.addDefaultExtraVegetation(biomegenerationsettings$builder);
		}

		float f = snowy ? 0.0F : 0.8F;
		return biome(snowy ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN, snowy ? Biome.BiomeCategory.ICY : Biome.BiomeCategory.PLAINS, f, snowy ? 0.5F : 0.4F, mobspawnsettings$builder, biomegenerationsettings$builder, null);
	}
	// #endregion
}
