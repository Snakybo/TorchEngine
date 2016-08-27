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
import com.snakybo.torch.util.interfaces.IDestroyable;
import com.snakybo.torch.util.queue.QueueOperation;
import com.snakybo.torch.scene.Scene;
import com.snakybo.torch.scene.SceneRegisterer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GameObject extends Object implements IDestroyable
{	
	Set<Component> components;
	Map<Component, QueueOperation> queue;
	
	Transform transform;
	Scene scene;
	
	boolean destroyed;
	
	/**
	 * Create a new {@link GameObject}
	 */
	public GameObject()
	{
		this("GameObject");
	}
	
	/**
	 * Create a new {@link GameObject} with the specified name
	 * @param name The name of the {@link GameObject}
	 */
	public GameObject(String name)
	{
		this(Scene.getCurrentScene(), name);
	}
	
	GameObject(Scene scene, String name)
	{
		super(name);
		
		this.scene = scene;
		SceneRegisterer.add(this, scene);
		
		components = new HashSet<>();
		queue = new HashMap<>();
		
		transform = new Transform();
		transform.gameObject = this;
	}
	
	@Override
	public void destroy()
	{
		destroyed = true;
	}
	
	final Component addComponentInternal(Class<?> component)
	{
		try
		{
			Component result = (Component)component.getConstructor().newInstance();
			queue.put(result, QueueOperation.Add);
			return result;
		}
		catch(ReflectiveOperationException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * Add a component to the GameObject
	 * @param component The component to add
	 */
	public final <T extends Component> T addComponent(Class<T> component)
	{
		return component.cast(addComponentInternal(component));
	}
	
	public final void removeComponent(Component component)
	{
		queue.put(component, QueueOperation.Remove);
	}
	
	public final void notify(String methodName, java.lang.Object... parameters)
	{
		GameObjectNotifier.notify(this, methodName, parameters);
	}
	
	/**
	 * Attempt to retrieve a component of {@code type} from the GameObject
	 * @param <T> The type of the component to retrieve
	 * @param type The type of component to retrieve
	 * @return The component, or {@code null} if no component of {@code type} was found
	 */
	public final <T extends Component> T getComponent(Class<T> type)
	{
		for(Component component : components)
		{
			if(component.getClass().equals(type))
			{
				return type.cast(component);
			}
		}
		
		return null;
	}
	
	/**
	 * Attempt to retrieve all components of {@code type} from the GameObject
	 * @param <T> The type of the component to retrieve
	 * @param type The type of component to retrieve
	 * @return The components, it's empty if no components of {@code type} are found
	 */
	public final <T extends Component> Iterable<T> getComponents(Class<T> type)
	{
		Set<T> result = new HashSet<>();
		
		for(Component component : components)
		{
			if(component.getClass().equals(type))
			{
				result.add(type.cast(component));
			}
		}
		
		return result;
	}
	
	/**
	 * @return The {@link Transform} attached to this {@link GameObject}
	 */
	public final Transform getTransform()
	{
		return transform;
	}
	
	public static void notifyAll(String methodName, java.lang.Object... parameters)
	{
		Scene.getCurrentScene().getAllGameObjects().forEach(gameObject -> gameObject.notify(methodName, parameters));
	}
}
