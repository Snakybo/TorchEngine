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
import com.snakybo.torch.interfaces.IDestroyable;

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
public abstract class AssetData implements IDestroyable
{
	protected String name;
	
	private int useCount;
	
	public AssetData(String name)
	{
		this.name = name;
		this.useCount = 0;
		
		addUsage();
	}
	
	/**
	 * Destroy the asset immediately.
	 */
	@Override
	public abstract void destroy();
	
	/**
	 * Increment the use counter for the {@link AssetData}.
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
	 * Decrement the use counter for the {@link AssetData},
	 * when there are no more usages remaining, the data is unloaded.
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
