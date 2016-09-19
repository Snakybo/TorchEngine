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

import com.snakybo.torch.scene.SceneInternal;
import com.snakybo.torch.util.debug.Logger;

import java.io.Serializable;

/**
 * <p>
 * Base class for all {@link GameObject}s and {@link Component}s.
 * </p>
 *
 * @see GameObject
 * @see Component
 *
 * @author Snakybo
 * @since 1.0
 */
public class TorchObject implements Serializable
{
	private String name;
	
	@Override
	public String toString()
	{
		return getName();
	}
	
	/**
	 * <p>
	 * Set the name.
	 * </p>
	 *
	 * @param name The new name.
	 */
	public final void setName(String name)
	{
		if(name == null || name.isEmpty())
		{
			Logger.logError("Invalid name for object");
			return;
		}
		
		this.name = name;
	}
	
	/**
	 * <p>.
	 * Get the name.
	 * </p>
	 *
	 * @return The name.
	 */
	public final String getName()
	{
		return name;
	}
	
	/**
	 * <p>
	 * Destroy an {@code Object}.
	 * </p>
	 *
	 * <p>
	 * This method has a slightly different behaviour depending on what the type of the {@code obj} is:
	 * </p>
	 *
	 * <p>
	 * If the {@code obj} is a {@link Component}, it will simply schedule the component for removal.
	 * </p>
	 *
	 * <p>
	 * If the {@code obj} is a {@link GameObject}, it will work in a recursive manner, it will first schedule the
	 * {@code GameObject} itself, and then all components for removal.
	 * </p>
	 *
	 * @param obj The object to destroy.
	 */
	public static void destroy(TorchObject obj)
	{
		if(obj instanceof GameObject)
		{
			GameObject gameObject = (GameObject)obj;
			gameObject.components.forEach(TorchObject::destroy);
			SceneInternal.remove(gameObject);
		}
		else if(obj instanceof Component)
		{
			Component component = (Component)obj;
			component.getGameObject().componentsToRemove.add(component);
		}
	}
}