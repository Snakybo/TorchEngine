package com.snakybo.sengine.asset;

import com.snakybo.sengine.util.IDestroyable;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class Asset implements IDestroyable
{
	@Override
	public abstract void destroy();
}
