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

/**
 * @author Snakybo
 * @since 1.0
 */
public class Object
{
	private String name;
	
	public Object(String name)
	{
		setName(name);
	}
	
	/**
	 * @see #getName()
	 */
	@Override
	public String toString()
	{
		return getName();
	}
	
	/**
	 * Set the name of the object
	 * @param name The new name
	 */
	public void setName(String name)
	{
		if(name == null || name.isEmpty())
		{
			Logger.logError("Invalid name for object", this);
			return;
		}
		
		this.name = name;
	}
	
	/**
	 * @return The name of the object
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Destroy an object, can be used on any {@link GameObject} or {@link Component}
	 * @param object The object to destroy
	 * @see GameObject
	 * @see Component
	 */
	public static void destroy(Object object)
	{
		if(object instanceof GameObject)
		{
			GameObjectInternal.destroy((GameObject)object);
		}
		else if(object instanceof Component)
		{
			Component c = (Component)object;
			GameObjectInternal.removeComponent(c.gameObject, c);
		}
	}
}