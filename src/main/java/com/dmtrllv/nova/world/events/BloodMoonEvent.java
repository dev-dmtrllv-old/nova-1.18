package com.dmtrllv.nova.world.events;

import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.WorldTickEvent;

public final class BloodMoonEvent
{
	private static final double BLOOD_MOON_DAYS_PER_CYCLE = 31;
	private static final double NIGHT_START_TICK = 12500;
	private static final double NIGHT_END_TICK = 24000;
	private static final float NIGHT_TIDE = (float) (NIGHT_END_TICK - NIGHT_START_TICK) / 8;
	private static final float MAX_RED_ADDITIVE = 0.15F;
	private static final float SLOPE_RED = MAX_RED_ADDITIVE / NIGHT_TIDE;

	private static double timeInDay = 0;

	private static boolean isActive = false;

	public static boolean isBloodMoonActive()
	{
		return isActive;
	}

	public static float getRedColor(float redIn)
	{
		if (isActive)
		{
			double timeInNight = timeInDay - NIGHT_START_TICK;

			if (timeInNight < NIGHT_TIDE) // the night color cycle has started
				return redIn + (float) (SLOPE_RED * timeInNight);
			else if (timeInNight > (NIGHT_TIDE * 8)) // the night color cycle has passed
				return redIn;
			else if (timeInNight > (NIGHT_TIDE * 7)) // the night color cycle is decreasing here
				return redIn + MAX_RED_ADDITIVE - (float) (SLOPE_RED * (timeInNight - (NIGHT_TIDE * 8)));
			else // else we are at the highest point
				return redIn + MAX_RED_ADDITIVE;
		}
		return redIn;
	}

	public static void onTick(WorldTickEvent event)
	{
		if (event.world.dimension() == Level.OVERWORLD && event.phase == Phase.START)
		{
			boolean active = isActive;

			long worldTime = event.world.dayTime();

			double d = Math.floor(worldTime / 24000);
			timeInDay = worldTime % 24000;

			if (d % BLOOD_MOON_DAYS_PER_CYCLE == 0)
				active = timeInDay >= NIGHT_START_TICK && timeInDay <= NIGHT_END_TICK;
			else
				active = false;

			isActive = active;

			// check WanderingTraderSpawner
		}
	}

}
