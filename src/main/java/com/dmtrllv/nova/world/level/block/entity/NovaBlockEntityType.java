package com.dmtrllv.nova.world.level.block.entity;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.world.level.block.NovaBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NovaBlockEntityType
{
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Nova.MOD_ID);

	public static final RegistryObject<BlockEntityType<NovaSignBlockEntity>> SIGN = REGISTRY.register("sign", () -> BlockEntityType.Builder.of(NovaSignBlockEntity::new, NovaBlocks.WHITE_OAK_SIGN.get(), NovaBlocks.WHITE_OAK_WALL_SIGN.get()).build(null));
}
