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

package com.snakybo.torch.object;

import com.snakybo.torch.util.debug.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ComponentInternal
{
	private static final Set<String> VALID_CALLBACKS = new HashSet<>(Arrays.asList(
			new String[] {
					"onCreate",
					"onStart",
					"onUpdate",
					"onPostUpdate",
					"onDestroy",
					"onPreRender",
					"onRender",
					"onPostRender",
					"onRenderGizmos",
			}));
	
	private static Map<Component, Map<String, Method>> callbacks = new HashMap<>();
		
	private ComponentInternal()
	{
		throw new AssertionError();
	}
	
	public static void addCallbacks(Component component)
	{
		if(callbacks.containsKey(component))
		{
			return;
		}
		
		Map<String, Method> methods = new HashMap<>();
		
		for(String methodName : VALID_CALLBACKS)
		{
			Method method = findMethodRecursive(component.getClass(), methodName);
			
			if(method != null)
			{
				methods.put(methodName, method);
			}
		}
		
		callbacks.put(component, methods);
	}
	
	public static void removeCallbacks(Component component)
	{
		callbacks.remove(component);
	}
	
	public static void invokeAll(GameObject gameObject, String name)
	{
		for(Component component : gameObject.components)
		{
			invoke(component, name);
		}
	}
	
	public static void invoke(Component component, String name)
	{
		if(!callbacks.containsKey(component) || !callbacks.get(component).containsKey(name))
		{
			return;
		}
		
		try
		{
			Method method = callbacks.get(component).get(name);
			method.setAccessible(true);
			method.invoke(component);
			method.setAccessible(false);
		}
		catch(IllegalAccessException | InvocationTargetException e)
		{
			Logger.logError(e.getMessage(), e);
		}
	}
	
	private static Method findMethodRecursive(Class<?> clazz, String name)
	{
		while(clazz != null)
		{
			try
			{
				return clazz.getDeclaredMethod(name);
			}
			catch(NoSuchMethodException e)
			{
				clazz = clazz.getSuperclass();
			}
		}
		
		return null;
	}
}
