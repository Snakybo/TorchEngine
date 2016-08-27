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
import com.snakybo.torch.scene.SceneInternal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * The {@code GameObject}.
 * </p>
 *
 * <p>
 * All objects visible in the {@code scene} are {@code GameObjects}.
 * A {@code GameObject} consists of one or multiple {@link Component}s.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class GameObject extends TorchObject
{
	Collection<Component> componentsToAdd;
	Collection<Component> componentsToRemove;
	
	Set<Component> components;
	
	private Transform transform;
	
	/**
	 * <p>
	 * Create a new {@code GameObject}.
	 * </p>
	 */
	public GameObject()
	{
		this("GameObject");
	}
	
	/**
	 * <p>
	 * Create a new {@code GameObject} with the specified name.
	 * </p>
	 *
	 * @param name The name of the {@link GameObject}.
	 */
	public GameObject(String name)
	{
		super(name);
		
		componentsToAdd = new HashSet<>();
		componentsToRemove = new HashSet<>();
		
		components = new HashSet<>();
		
		transform = new Transform();
		transform.gameObject = this;
		
		SceneInternal.add(this);
	}
	
	final Component addComponentInternal(Class<?> component)
	{
		try
		{
			Component result = (Component)component.getConstructor().newInstance();
			result.gameObject = this;
			
			componentsToAdd.add(result);
			components.add(result);
			
			result.onCreate();
			
			return result;
		}
		catch(ReflectiveOperationException e)
		{
			Logger.logError(e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * Add a {@link Component} to the {@code GameObject}.
	 * </p>
	 *
	 * @param component The component to add.
	 */
	public final <T extends Component> T addComponent(Class<T> component)
	{
		return component.cast(addComponentInternal(component));
	}
	
	/**<p>
	 * Attempt to retrieve a component of {@code type} from the {@code GameObject}.
	 * </p>
	 *
	 * @param <T> The type of the component to retrieve.
	 * @param type The type of component to retrieve.
	 * @return The component, or {@code null} if no component of {@code type} was found.
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
	 * <p>
	 * Attempt to retrieve all components of {@code type} from the {@code GameObject}.
	 * </p>
	 *
	 * @param <T> The type of the component to retrieve.
	 * @param type The type of component to retrieve.
	 * @return The components, it's empty if no components of {@code type} are found.
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
	 * <p>
	 * Get the {@link Transform} attached to this {@code GameObject}.
	 * </p>
	 * @return The {@link Transform} attached to this {@code GameObject}
	 */
	public final Transform getTransform()
	{
		return transform;
	}
}
