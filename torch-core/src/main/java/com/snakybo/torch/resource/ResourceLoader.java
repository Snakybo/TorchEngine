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

package com.snakybo.torch.resource;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.snakybo.torch.bitmap.BitmapLoader;
import com.snakybo.torch.debug.Logger;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ResourceLoader
{
	private static Map<String, IResourceLoader> loaders = new HashMap<String, IResourceLoader>();
	
	static
	{
		IResourceLoader bitmapLoader = new BitmapLoader();
		
		loaders.put("png", bitmapLoader);
		loaders.put("jpg", bitmapLoader);
	}
	
	private ResourceLoader()
	{
	}
	
	/**
	 * Load a resource, the type determines what {@link IResourceLoader} to use.
	 * @param path The path to the resource.
	 * @param type The type of the resource, usually the file extension.
	 * @return The resource, can be anything as
	 * long as the receiving class knows what to do with the data.
	 */
	public static Object load(URI path, String type)
	{
		if(loaders.containsKey(type))
		{
			return loaders.get(type).load(path);
		}
		
		Logger.logWarning("Unknown resource type: " + type);
		return null;
	}
}
