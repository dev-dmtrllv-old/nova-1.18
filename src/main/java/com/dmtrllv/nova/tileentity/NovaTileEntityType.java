package com.dmtrllv.nova.tileentity;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.world.level.block.NovaBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NovaTileEntityType
{
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Nova.MOD_ID);

	public static final RegistryObject<BlockEntityType<NovaSignTileEntity>> SIGN = REGISTRY.register("sign", () -> BlockEntityType.Builder.of(NovaSignTileEntity::new, NovaBlocks.WHITE_OAK_SIGN.get(), NovaBlocks.WHITE_OAK_WALL_SIGN.get()).build(null));
}
