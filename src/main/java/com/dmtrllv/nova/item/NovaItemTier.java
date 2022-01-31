package com.dmtrllv.nova.item;

import java.util.function.Supplier;

import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

public enum NovaItemTier implements Tier
{	
	PEBBLE(0, 59, 2.0F, 0.0F, 15, () ->
	{
		return Ingredient.of(ItemTags.PLANKS);
	});

	private final int level;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;

	@SuppressWarnings("deprecation")
	private final LazyLoadedValue<Ingredient> repairIngredient;

	@SuppressWarnings("deprecation")
	private NovaItemTier(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient)
	{
		this.level = level;
		this.uses = uses;
		this.speed = speed;
		this.damage = damage;
		this.enchantmentValue = enchantmentValue;
		this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
	}

	public int getUses()
	{
		return this.uses;
	}

	public float getSpeed()
	{
		return this.speed;
	}

	public float getAttackDamageBonus()
	{
		return this.damage;
	}

	public int getLevel()
	{
		return this.level;
	}

	public int getEnchantmentValue()
	{
		return this.enchantmentValue;
	}

	@SuppressWarnings("deprecation")
	public Ingredient getRepairIngredient()
	{
		return this.repairIngredient.get();
	}

	@javax.annotation.Nullable
	public net.minecraft.tags.Tag<net.minecraft.world.level.block.Block> getTag()
	{
		return switch (this)
		{
		case PEBBLE -> Tags.Blocks.NEEDS_WOOD_TOOL;
		};
	}

}
