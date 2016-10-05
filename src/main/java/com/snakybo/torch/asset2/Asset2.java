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

package com.snakybo.torch.asset2;

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.object.TorchObject;

/**
 * <p>
 * An asset, loadable with {@link Assets#load(String)}.
 * </p>
 *
 * <p>
 * Assets internally use a reusable {@link AssetData2} instance to store their data.
 * </p>
 *
 * <p>
 * Creating an asset by name ({@code new MyAsset("asset")}) will create a new asset,
 * but if another asset with the same name already exists, it will use the same data as that asset.
 * </p>
 *
 * <p>
 * This means that the name of an asset usually is something unique to a certain type of asset, e.g.
 * a file path {@code textures/sample.png"}.
 * </p>
 *
 * <p>
 * Creating an asset by another asset ({@code new MyAsset(otherAsset)} will create a copy
 * of the original, but they will not use the same {@link AssetData2} instance.
 * </p>
 *
 * <p>
 * It creates a new instance of the {@link AssetData2}, and fills it with the same data
 * as the original.
 * </p>
 *
 * @see Assets
 * @see AssetData2
 *
 * @author Snakybo
 * @since 1.0
 */
public abstract class Asset2 extends TorchObject
{
	protected AssetData2 data;
	
	protected Asset2(String name)
	{
		setName(name);
		setData(Asset2Internal.get(this, name));
	}
	
	@Override
	public final String toString()
	{
		return data.toString();
	}
	
	@Override
	public final int hashCode()
	{
		return 15 + 64 * data.hashCode();
	}
	
	@Override
	public final boolean equals(Object o)
	{
		if(o == this)
		{
			return true;
		}
		
		if(o == null || !getClass().equals(o.getClass()))
		{
			return false;
		}
		
		Asset2 a = (Asset2)o;
		return data == a.data;
	}
	
	protected abstract void onDestroy();
	
	/**
	 * <p>
	 * Destroy the asset, this disables the asset,
	 * but does not necessarily unload data bound to the Asset.
	 * </p>
	 */
	public final void destroy()
	{
		setData(null);
	}
	
	private void setData(AssetData2 d)
	{
		if(data != null)
		{
			if(data.unlink(this))
			{
				onDestroy();
			}
			
			data = null;
		}
		
		if(d != null)
		{
			data = d;
			data.link(this);
		}
	}
	
	protected final boolean isCreator()
	{
		return data.creator == this;
	}
	
	protected final void setProperty(String name, Object value)
	{
		data.properties.put(name, value);
	}
	
	protected final Object getProperty(String name)
	{
		return data.properties.get(name);
	}
}
