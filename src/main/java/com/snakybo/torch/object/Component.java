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

import com.snakybo.torch.graphics.gizmo.Gizmos;

/**
 * <p>
 * Base class for all components.
 * </p>
 *
 * <p>
 * All components on a {@link GameObject} are a subclass of this class.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public abstract class Component extends TorchObject
{	
	transient GameObject gameObject;
	
	public Component()
	{
		super("Component");
		
		String name = getClass().getSimpleName();
		if(!name.isEmpty())
		{
			setName(name);
		}
		else
		{
			setName("Anonymous Component");
		}
	}
	
	/**
	 * <p>
	 * Called as soon as the component is created.
	 * </p>
	 */
	protected void onCreate()
	{
	}
	
	/**
	 * <p>
	 * Called just before the first time {@link #onUpdate()} is called.
	 * </p>
	 */
	protected void onStart()
	{
	}
	
	/**
	 * <p>
	 * Called every frame.
	 * </p>
	 */
	protected void onUpdate()
	{
	}
	
	/**
	 * <p>
	 * Called every frame, after {@link #onUpdate()} has been called on every component in the scene.
	 * </p>
	 */
	protected void onPostUpdate()
	{
	}
	
	/**
	 * <p>
	 * Called when a camera starts rendering the object.
	 * </p>
	 */
	protected void onPreRenderObject()
	{
	}
	
	/**
	 * <p>
	 * Called when a camera renders the object.
	 * </p>
	 */
	protected void onRenderObject()
	{
	}
	
	/**
	 * <p>
	 * Called when a camera has finished rendering the object
	 * </p>
	 */
	protected void onPostRenderObject()
	{
	}
	
	/**
	 * <p>
	 * Called after everything in the scene has been rendered,
	 * you can only render {@link Gizmos} from here.
	 * </p>
	 */
	protected void onRenderGizmos()
	{
	}
	
	/**
	 * <p>
	 * Called when the component or parent {@link GameObject} has been destroyed
	 * </p>
	 */
	protected void onDestroy()
	{
	}
	
	/**
	 * <p>
	 * Add a component to the parent {@code GameObject}.
	 * </p>
	 *
	 * @param component The component to add.
	 * @param <T> The class of the component to add.
	 * @return The component.
	 */
	public final <T extends Component> T addComponent(Class<T> component)
	{
		return gameObject.addComponent(component);
	}
	
	/**
	 * <p>
	 * Attempt to retrieve a component of {@code type} from the parent {@code GameObject}.
	 * </p>
	 *
	 * @param <T> The type of the component to retrieve.
	 * @param type The type of the component to retrieve.
	 * @return The component, or {@code null} if no component of {@code type} was found.
	 * @see GameObject#getComponent(Class)
	 */
	public final <T extends Component> T getComponent(Class<T> type)
	{
		return gameObject.getComponent(type);
	}
	
	/**
	 * <p>
	 * Attempt to retrieve all components of {@code type} from the parent {@code GameObject}.
	 * </p>
	 *
	 * @param <T> The type of the components to retrieve.
	 * @param type The type of components to retrieve.
	 * @return The components, it's empty if no components of {@code type} are found.
	 * @see GameObject#getComponents(Class)
	 */
	public final <T extends Component> Iterable<T> getComponents(Class<T> type)
	{
		return gameObject.getComponents(type);
	}
	
	/**
	 * <p>
	 * Get the parent {@code GameObject}.
	 * </p>
	 * @return The parent {@link GameObject}
	 */
	public final GameObject getGameObject()
	{
		return gameObject;
	}
	
	/**
	 * <p>
	 * Get the {@link Transform} of the parent {@code GameObject}.
	 * </p>
	 * @return The {@code transform} of the parent {@link GameObject}
	 */
	public final Transform getTransform()
	{
		return gameObject.getTransform();
	}
}
