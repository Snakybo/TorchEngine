// The MIT License(MIT)
//
// Copyright(c) 2016 Kevin Krol
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.snakybo.torch.asset;

import com.snakybo.torch.debug.LoggerInternal;

/**
 * <p>
 * The internal data of an asset, keeps track of the number of uses a specific asset has,
 * if there are no more uses for an asset, it's automatically destroyed and unloaded from memory.
 * </p>
 *
 * @see Assets
 * @see Asset
 *
 * @author Snakybo
 * @since 1.0
 */
public abstract class AssetData
{
	protected String name;
	
	private int useCount;
	
	/**
	 * <p>
	 * Create a new {@code AssetData} instance.
	 * </p>
	 *
	 * @param name The name of the asset.
	 */
	public AssetData(String name)
	{
		this.name = name;
		this.useCount = 0;
		
		addUsage();
	}
	
	/**
	 * <p>
	 * Destroy the asset immediately.
	 * </p>
	 */
	public abstract void destroy();
	
	/**
	 * <p>
	 * Increment the number of uses this {@code AssetData} has.
	 * </p>
	 */
	public final void addUsage()
	{
		if(name != null && !name.isEmpty())
		{
			LoggerInternal.log("useCount increased for " + getClass().getSimpleName() + ":" + name);
			useCount++;
		}
		else
		{
			useCount = 1;
		}
	}
	
	/**
	 * <p>
	 * Decrement the number of uses this {@code AssetData} has.
	 * </p>
	 *
	 * <p>
	 * The {@code AssetData} is not unload until there are no more usages.
	 * </p>
	 */
	public final void removeUsage()
	{
		if(name != null && !name.isEmpty())
		{
			LoggerInternal.log("useCount decreased for " + getClass().getSimpleName() + ":" + name);
			useCount--;
			
			if(useCount <= 0)
			{
				LoggerInternal.log("No more usages remaining for " + getClass().getSimpleName() + ":" + name + ", destroying");
				destroy();
			}
		}
		else
		{
			LoggerInternal.log("No more usages remaining for runtime " + getClass().getSimpleName() + ", destroying");
			destroy();
		}
	}
}
