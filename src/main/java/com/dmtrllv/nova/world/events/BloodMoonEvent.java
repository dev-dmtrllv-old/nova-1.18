package com.dmtrllv.nova.world.events;

import net.minecraftforge.event.TickEvent.WorldTickEvent;

public final class BloodMoonEvent
{
	private static final double BLOOD_MOON_DAYS_PER_CYCLE = 31;
	private static final double NIGHT_START_TICK = 12575;
	private static final double NIGHT_END_TICK = 22550;

	public static boolean isActive(long worldTime)
	{
		double d = Math.floor(worldTime / 24000);
		double timeInDay = worldTime % 24000;

		if (d % BLOOD_MOON_DAYS_PER_CYCLE == 0)
			return timeInDay >= NIGHT_START_TICK && timeInDay <= NIGHT_END_TICK;
		
		return false;
	}

	public static void onTick(WorldTickEvent event)
	{
		
	}

}
