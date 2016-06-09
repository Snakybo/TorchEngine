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

import java.util.HashMap;
import java.util.Map;

public final class AssetDatabase
{
	private static Map<String, Asset> loadedAssets = new HashMap<String, Asset>();
	
	private AssetDatabase()
	{
		throw new AssertionError();
	}
	
	public static Asset load(String file)
	{
		if(hasAsset(file))
		{
			return get(file);
		}
		
		// TODO: Asset loading
		return null;
	}
	
	public static AssetLoaderOperation loadAsync(String file)
	{
		if(hasAsset(file))
		{
			AssetLoaderOperation alo = new AssetLoaderOperation();
			alo.asset = get(file);
			alo.progress = 1.0f;
			return alo;
		}
		
		// TODO: Async asset loading
		return null;
	}
	
	public static <T extends Asset> T load(Class<T> clazz, String file)
	{
		return clazz.cast(load(file));
	}
	
	public static boolean unload(Asset asset)
	{
		for(Map.Entry<String, Asset> entry : loadedAssets.entrySet())
		{
			if(entry.getValue().equals(asset))
			{
				loadedAssets.remove(entry.getKey()).destroy();
				return true;
			}
		}
		
		return false;
	}
	
	static boolean hasAsset(String file)
	{
		return loadedAssets.containsKey(file);
	}
	
	static Asset add(String file, Asset asset)
	{
		if(!hasAsset(file))
		{
			loadedAssets.put(file, asset);
		}
		
		return loadedAssets.get(file);
	}
	
	static Asset get(String file)
	{
		if(hasAsset(file))
		{
			return loadedAssets.get(file);
		}
		
		return null;
	}
}
