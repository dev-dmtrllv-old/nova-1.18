package com.dmtrllv.nova.world.level.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.WoodType;

public final class NovaWoodType
{
	private static List<WoodType> woodTypes = new ArrayList<>();

	public static final WoodType WHITE_OAK = register("white_oak");

	private static WoodType register(String name)
	{
		WoodType t = WoodType.create(name);
		woodTypes.add(t);
		return t;
	}

	public static void registerWoodTypes()
	{
		woodTypes.forEach((woodType) -> Sheets.addWoodType(woodType));
	}
}
