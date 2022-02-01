package com.dmtrllv.nova.item;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.tags.NovaBlockTags;
import com.dmtrllv.nova.world.level.block.NovaBlocks;
import com.google.common.base.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NovaItems
{
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Nova.MOD_ID);

	// #region Register methods
	private static RegistryObject<Item> register(String name, RegistryObject<Block> block, CreativeModeTab group)
	{
		return REGISTRY.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(group)));
	}


	private static RegistryObject<Item> register(String name)
	{
		return register(name, () -> new Item(new Item.Properties().tab(NovaItemGroup.NOVA_MOD_ITEMS)));
	}

	// private static RegistryObject<Item> register(String name, CreativeModeTab tab)
	// {
	// 	return register(name, () -> new Item(new Item.Properties().tab(tab)));
	// }

	private static RegistryObject<Item> register(String name, Supplier<? extends Item> sup)
	{
		return REGISTRY.register(name, () -> sup.get());
	}

	private static RegistryObject<Item> register(String name, RegistryObject<Block> block)
	{
		return register(name, block, NovaItemGroup.NOVA_MOD_ITEMS);
	}
	// #endregion

	public static final RegistryObject<Item> PEBBLE = register("pebble", NovaBlocks.PEBBLE);
	public static final RegistryObject<Item> WHITE_OAK_LOG = register("white_oak_log", NovaBlocks.WHITE_OAK_LOG);
	public static final RegistryObject<Item> WHITE_OAK_WOOD = register("white_oak_wood", NovaBlocks.WHITE_OAK_WOOD);
	public static final RegistryObject<Item> STRIPPED_WHITE_OAK_LOG = register("stripped_white_oak_log", NovaBlocks.STRIPPED_WHITE_OAK_LOG);
	public static final RegistryObject<Item> STRIPPED_WHITE_OAK_WOOD = register("stripped_white_oak_wood", NovaBlocks.STRIPPED_WHITE_OAK_WOOD);
	public static final RegistryObject<Item> WHITE_OAK_PLANKS = register("white_oak_planks", NovaBlocks.WHITE_OAK_PLANKS);
	public static final RegistryObject<Item> WHITE_OAK_STAIRS = register("white_oak_stairs", NovaBlocks.WHITE_OAK_STAIRS);
	public static final RegistryObject<Item> WHITE_OAK_SLAB = register("white_oak_slab", NovaBlocks.WHITE_OAK_SLAB);
	public static final RegistryObject<Item> WHITE_OAK_DOOR = register("white_oak_door", NovaBlocks.WHITE_OAK_DOOR);
	public static final RegistryObject<Item> WHITE_OAK_TRAPDOOR = register("white_oak_trapdoor", NovaBlocks.WHITE_OAK_TRAPDOOR);
	public static final RegistryObject<Item> WHITE_OAK_BUTTON = register("white_oak_button", NovaBlocks.WHITE_OAK_BUTTON);
	public static final RegistryObject<Item> WHITE_OAK_PRESSURE_PLATE = register("white_oak_pressure_plate", NovaBlocks.WHITE_OAK_PRESSURE_PLATE);
	public static final RegistryObject<Item> WHITE_OAK_FENCE = register("white_oak_fence", NovaBlocks.WHITE_OAK_FENCE);
	public static final RegistryObject<Item> WHITE_OAK_FENCE_GATE = register("white_oak_fence_gate", NovaBlocks.WHITE_OAK_FENCE_GATE);
	
	// public static final RegistryObject<Item> WHITE_OAK_SIGN = register("white_oak_sign", () -> new SignItem((new Item.Properties()).stacksTo(16).tab(NovaItemGroup.NOVA_MOD_ITEMS), NovaBlocks.WHITE_OAK_SIGN.get(), NovaBlocks.WHITE_OAK_WALL_SIGN.get()));

	public static final RegistryObject<Item> WHITE_OAK_SAPLING = register("white_oak_sapling", NovaBlocks.WHITE_OAK_SAPLING);
	public static final RegistryObject<Item> WHITE_OAK_LEAVES = register("white_oak_leaves", NovaBlocks.WHITE_OAK_LEAVES);
	public static final RegistryObject<Item> PEBBLE_TOOL = register("pebble_tool", () -> new DiggerItem(0.8F, -2.8F,  NovaItemTier.PEBBLE, NovaBlockTags.PEBBLE_MINABLE, new Item.Properties().tab(NovaItemGroup.NOVA_MOD_ITEMS)));
	public static final RegistryObject<Item> MOON_STONE_ORE = register("moon_stone_ore", NovaBlocks.MOON_STONE_ORE);
	public static final RegistryObject<Item> MOON_STONE = register("moon_stone");
}
