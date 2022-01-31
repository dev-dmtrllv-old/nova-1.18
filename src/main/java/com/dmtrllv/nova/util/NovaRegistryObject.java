package com.dmtrllv.nova.util;

import java.util.function.Supplier;

import javax.annotation.Nullable;

public class NovaRegistryObject<T>
{
	@Nullable
	private T value = null;

	private final Supplier<T> supplier;

	private final String id;

	public NovaRegistryObject(String id, Supplier<T> supplier)
	{
		this.id = id;
		this.supplier = supplier;
	}

	public T get()
	{
		if (value == null)
		{
			value = supplier.get();
		}
		return value;
	}

	public String getID()
	{
		return id;
	}
}
