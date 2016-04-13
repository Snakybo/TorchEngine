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

package com.snakybo.sengine.resource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ResourceDatabase
{
	private static Map<String, Integer> useCount = new HashMap<String, Integer>();
	private static Map<String, Resource> resources = new HashMap<String, Resource>();
	
	private static Set<Object> sources = new HashSet<Object>();
	
	private ResourceDatabase()
	{
		throw new AssertionError();
	}
	
	/**
	 * Register a new resource, doing this will add the {@link Resource} to the database
	 * @param resourceName - The name of the resource
	 * @param resource - The resource to register
	 * @param source - The source of the registration
	 * @return The {@link Resource}
	 */
	public static Resource register(String resourceName, Resource resource, Object source)
	{
		if(!useCount.containsKey(resourceName))
		{
			useCount.put(resourceName, 0);
			resources.put(resourceName, resource);
		}
		
		return link(resourceName, source);
	}
	
	/**
	 * Link an object to an {@link Resource}, this will increase the usage-counter by 1
	 * @param resourceName - The name of the resource
	 * @param source - The object to link to the resource
	 * @return The {@link Resource} registered with the same {@code resourceName}
	 */
	public static Resource link(String resourceName, Object source)
	{
		if(useCount.containsKey(resourceName))
		{
			int usages = useCount.get(resourceName);
			useCount.put(resourceName, usages + 1);			
			sources.add(source);
			
			return resources.get(resourceName);
		}
		
		return null;
	}
	
	/**
	 * Unlink an object from an {@link Resource}, this will decrease the usage-counter by 1.
	 * If the usage counter becomes 0 as a result of this operation, it will automatically destroy the resource
	 * @param resourceName - The name of the resource
	 * @param source - The object to unlink
	 */
	public static void unlink(String resourceName, Object source)
	{
		if(sources.contains(source) && useCount.containsKey(resourceName))
		{
			int usages = useCount.get(resourceName);
			useCount.put(resourceName, usages - 1);
			sources.remove(source);
			
			// Delete the resource if necessary
			if(usages - 1 <= 0)
			{
				Resource resource = resources.get(resourceName);
				
				useCount.remove(resourceName);
				resources.remove(resourceName);
				
				resource.destroy();
			}
		}
	}
	
	public static <T extends Resource> T load(Class<T> resourceType, String resourceName, Object source, ResourceImporter<T> importer)
	{
		if(hasResource(resourceName))
		{
			return resourceType.cast(ResourceDatabase.link(resourceName, source));
		}
		else
		{
			try
			{
				return resourceType.cast(ResourceDatabase.register(resourceName, importer.importResource(), source));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * Check whether or not an {@link Resource} has already been registered
	 * @param resourceName - The name of the {@link Resource}
	 * @return Whether or not the {@link Resource} has been registered
	 */
	public static boolean hasResource(String resourceName)
	{
		if(resources.containsKey(resourceName))
		{
			return true;
		}
		
		return false;
	}
}
