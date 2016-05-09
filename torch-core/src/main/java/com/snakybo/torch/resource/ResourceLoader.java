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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import com.snakybo.torch.bitmap.BitmapResourceLoader;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.model.ModelResourceLoader;
import com.snakybo.torch.shader.ShaderResourceLoader;
import com.snakybo.torch.util.FileUtils;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ResourceLoader
{
	private static Map<String, IResourceLoader> loaders = new HashMap<String, IResourceLoader>();
	
	static
	{
		registerResourceLoader(new BitmapResourceLoader());
		registerResourceLoader(new ModelResourceLoader());
		registerResourceLoader(new ShaderResourceLoader());
	}
	
	private ResourceLoader()
	{
		throw new AssertionError();
	}
	
	private static void registerResourceLoader(IResourceLoader rl)
	{
		ResourceLoaderData rld = rl.getClass().getAnnotation(ResourceLoaderData.class);
		
		if(rld == null)
		{
			Logger.logException(new OperationNotSupportedException("A resource loader must have a ResourceLoaderData annotation"), "ResourceLoader");
			return;
		}
		
		String types = "";
		for(int i = 0; i < rld.types().length; i++)
		{
			String type = rld.types()[i];
			
			types += type;
			
			if(i < rld.types().length - 1)
			{
				types += ", ";
			}
			
			loaders.put(type, rl);
		}
		
		Logger.log("Registered resource loader: " + rl.getClass().getSimpleName() + " (" + types + ")", "ResourceLoader");
	}
	
	/**
	 * Load a resource from the specified URI.
	 * @param path The path to the resource.
	 * @return The resource, can be anything as
	 * long as the receiving class knows what to do with the data.
	 */
	public static Object load(URI path)
	{
		Path p = Paths.get(path);
		
		if(Files.exists(p))
		{
			String type = FileUtils.getExtension(path);
			
			if(loaders.containsKey(type))
			{
				return loaders.get(type).load(path);
			}
			
			Logger.logError("Unknown resource type: " + type, "ResourceLoader");
			return null;
		}
		
		Logger.logError("No resource found at: " + p, "ResourceLoader");
		return null;
	}
}
