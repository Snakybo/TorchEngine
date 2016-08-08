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

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.reflection.ReflectionUtil;

import java.lang.reflect.Method;

/**
 * @author Kevin Krol
 * @since 1.0
 */
public final class GameObjectNotifier
{
	public static void notify(GameObject obj, String methodName, java.lang.Object... parameters)
	{
		Class<?>[] parameterTypes = ReflectionUtil.getObjectTypes(parameters);
		
		try
		{
			Method target = Component.class.getDeclaredMethod(methodName, parameterTypes);
			
			if(target == null)
			{
				throw new NoSuchMethodError("No method found with name \"" + methodName + "\" in Component.class");
			}
			
			for(Component component : obj.components)
			{
				target.invoke(component, parameters);
			}
		}
		catch(Exception e)
		{
			Logger.logError(e.toString(), e);
		}
	}
	
	public static void update(GameObject gameObject)
	{
		gameObject.components.forEach(Component::update);
	}
	
	public static void postUpdate(GameObject gameObject)
	{
		gameObject.components.forEach(Component::postUpdate);
	}
	
	public static void preRender(GameObject gameObject)
	{
		gameObject.components.forEach(Component::preRenderObject);
	}
	
	public static void render(GameObject gameObject)
	{
		gameObject.components.forEach(Component::renderObject);
	}
	
	public static void postRender(GameObject gameObject)
	{
		gameObject.components.forEach(Component::postRenderObject);
	}

	public static void destroy(GameObject gameObject)
	{
		gameObject.components.forEach(Component::destroy);
	}
}
