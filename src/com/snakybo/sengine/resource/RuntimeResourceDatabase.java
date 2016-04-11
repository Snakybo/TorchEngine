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
public final class RuntimeResourceDatabase
{
	private static Map<String, Integer> useCount = new HashMap<String, Integer>();
	private static Map<String, RuntimeResource> runtimeResources = new HashMap<String, RuntimeResource>();
	
	private static Set<Object> sources = new HashSet<Object>();
	
	private RuntimeResourceDatabase()
	{
		throw new AssertionError();
	}
	
	/**
	 * Register a new resource, doing this will add the {@link RuntimeResource} to the database
	 * @param resourceName - The name of the resource
	 * @param runtimeResource - The resource to register
	 * @param source - The source of the registration
	 * @return The {@link RuntimeResource}
	 */
	public static RuntimeResource register(String resourceName, RuntimeResource runtimeResource, Object source)
	{
		if(!useCount.containsKey(resourceName))
		{
			useCount.put(resourceName, 0);
			runtimeResources.put(resourceName, runtimeResource);
		}
		
		return link(resourceName, source);
	}
	
	/**
	 * Link an object to an {@link RuntimeResource}, this will increase the usage-counter by 1
	 * @param resourceName - The name of the resource
	 * @param source - The object to link to the resource
	 * @return The {@link RuntimeResource} registered with the same {@code resourceName}
	 */
	public static RuntimeResource link(String resourceName, Object source)
	{
		if(useCount.containsKey(resourceName))
		{
			int usages = useCount.get(resourceName);
			useCount.put(resourceName, usages + 1);			
			sources.add(source);
			
			return runtimeResources.get(resourceName);
		}
		
		return null;
	}
	
	/**
	 * Unlink an object from an {@link RuntimeResource}, this will decrease the usage-counter by 1.
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
				RuntimeResource runtimeResource = runtimeResources.get(resourceName);
				
				useCount.remove(resourceName);
				runtimeResources.remove(resourceName);
				
				runtimeResource.destroy();
			}
		}
	}
	
	/**
	 * Check whether or not an {@link RuntimeResource} has already been registered
	 * @param resourceName - The name of the {@link RuntimeResource}
	 * @return Whether or not the {@link RuntimeResource} has been registered
	 */
	public static boolean hasResource(String resourceName)
	{
		if(runtimeResources.containsKey(resourceName))
		{
			return true;
		}
		
		return false;
	}
}
