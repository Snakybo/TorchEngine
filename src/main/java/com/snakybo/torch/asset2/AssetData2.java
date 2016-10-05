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
import com.snakybo.torch.util.debug.LoggerInternal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Holds internal data for assets, and keeps track of which assets are linked to the data.
 * If no more assets are linked, it will allow the last asset that called {@link #unlink(Asset2)} to
 * destroy the data.
 * </p>
 *
 * @see Assets
 * @see Asset2
 *
 * @author Snakybo
 * @since 1.0
 */
public final class AssetData2
{
	final Map<String, Object> properties;
	final List<Asset2> links;
	
	final Asset2 creator;
	final String name;
	
	AssetData2(Asset2 creator, String name)
	{
		this.creator = creator;
		this.name = name;
		
		properties = new HashMap<>();
		links = new ArrayList<>();
	}
	
	AssetData2(AssetData2 other)
	{
		this.creator = other.creator;
		this.name = other.name;
		
		properties = other.properties;
		links = new ArrayList<>();
	}
	
	@Override
	public final String toString()
	{
		String l = "links[";
		
		for(int i = 0; i < links.size(); i++)
		{
			Asset2 a = links.get(i);
			
			l += a.getClass().getSimpleName() + ":" + a.getName();
			
			if(i < links.size() - 1)
			{
				l += ", ";
			}
		}
		
		l += "]";
		
		String d = "properties[";
		
		for(Map.Entry<String, Object> entry : properties.entrySet())
		{
			d += entry.toString() + ", ";
		}
		
		d = d.substring(0, d.length() - 2) + "]";
		
		return getClass().getSimpleName() + ":" + name + " " + l + " " + d;
	}
	
	@Override
	public final int hashCode()
	{
		return 11 + properties.hashCode() ^ name.hashCode();
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
		
		AssetData2 ad = (AssetData2)o;
		return ad.properties.equals(properties) && name.equals(ad.name);
	}
	
	final void link(Asset2 asset)
	{
		links.add(asset);
		
		String name = getClass().getSimpleName() + ":" + this.name;
		LoggerInternal.log("Linked " + asset.getName() + " to " + name + " (count: " + links.size() + ")");
	}
	
	final boolean unlink(Asset2 asset)
	{
		String name = getClass().getSimpleName() + ":" + this.name;
		
		if(links.remove(asset))
		{
			LoggerInternal.log("Unlinked " + asset.getName() + " from " + name + " (count: " + links.size() + ")");
		}
		
		if(links.isEmpty())
		{
			LoggerInternal.log("No more links remaining for " + name + ", destroying.");
			
			if(this.name != null && !this.name.isEmpty())
			{
				Asset2Internal.remove(this.name);
			}
			
			return true;
		}
		
		return false;
	}
}
