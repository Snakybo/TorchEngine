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

/**
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
	
	protected void onCreate()
	{
	}
	
	/**
	 * Called once when the object was first created, just before {@link #onUpdate()} is called
	 */
	protected void onStart()
	{
	}
	
	/**
	 * Called every frame
	 */
	protected void onUpdate()
	{
	}
	
	/**
	 * Called every frame, after {@link #onUpdate()} has been called on every component in the scene
	 */
	protected void onPostUpdate()
	{
	}
	
	/**
	 * Called when a camera starts rendering the object
	 */
	protected void onPreRenderObject()
	{
	}
	
	/**
	 * Called when a camera renders the object
	 */
	protected void onRenderObject()
	{
	}
	
	/**
	 * Called when a camera has finished rendering the object
	 */
	protected void onPostRenderObject()
	{
	}
	
	/**
	 * Called when the component or parent {@link GameObject} has been destroyed
	 */
	protected void onDestroy()
	{
	}
	
	/**
	 * Add a component to the parent GameObject
	 * @param component The component to add
	 */
	public final <T extends Component> T addComponent(Class<T> component)
	{
		return gameObject.addComponent(component);
	}
	
	/**
	 * Attempt to retrieve a component of {@code type} from the parent GameObject
	 * @param <T> The type of the component to retrieve
	 * @param type The type of the component to retrieve 
	 * @return The component, or {@code null} if no component of {@code type} was found
	 * @see GameObject#getComponent(Class)
	 */
	public final <T extends Component> T getComponent(Class<T> type)
	{
		return gameObject.getComponent(type);
	}
	
	/**
	 * Attempt to retrieve all components of {@code type} from the parent GameObject
	 * @param <T> The type of the components to retrieve
	 * @param type The type of components to retrieve
	 * @return The components, it's empty if no components of {@code type} are found
	 * @see GameObject#getComponents(Class)
	 */
	public final <T extends Component> Iterable<T> getComponents(Class<T> type)
	{
		return gameObject.getComponents(type);
	}
	
	/**
	 * @return The parent {@link GameObject}
	 */
	public final GameObject getGameObject()
	{
		return gameObject;
	}
	
	/**
	 * @return The transform of the {@link GameObject}
	 */
	public final Transform getTransform()
	{
		return gameObject.getTransform();
	}
}
