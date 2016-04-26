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

import com.snakybo.torch.input.cursor.CursorEnterMode;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class Component extends Object
{	
	GameObject gameObject;
	
	boolean started;
	
	public Component()
	{
		super("Component");
		
		String name = getClass().getSimpleName();
		if(name != null && !name.isEmpty())
		{
			setName(name);
		}
		else
		{
			setName("Anonymous Component");
		}
	}
	
	/**
	 * Called once when the object was first created, just before {@link #update()} is called
	 */
	protected void start()
	{
	}
	
	/**
	 * Called every frame
	 */
	protected void update()
	{
	}
	
	/**
	 * Called every frame, after {@link #update()} has been called on every component in the scene
	 */
	protected void postUpdate()
	{
	}
	
	/**
	 * Called when a camera starts rendering the object
	 */
	protected void preRenderObject()
	{
	}
	
	/**
	 * Called when a camera renders the object
	 */
	protected void renderObject()
	{
	}
	
	/**
	 * Called when a camera has finished rendering the object
	 */
	protected void postRenderObject()
	{
	}
	
	/**
	 * Called when the component or parent {@link GameObject} has been destroyed by {@link #destroy(Object)}
	 */
	protected void destroy()
	{
	}
	
	/**
	 * Called when the cursor has entered or left the game window
	 * @param enterMode The enter mode
	 */
	protected void onCursorEnter(CursorEnterMode enterMode)
	{
	}
	
	/**
	 * Called when a character on the keyboard has been pressed. Useful for text input
	 * @param c The char
	 */
	protected void onCharPressed(char c)
	{
	}
	
	/**
	 * Add a component to the parent GameObject
	 * @param component The component to add
	 */
	public final void addComponent(Component component)
	{
		gameObject.addComponent(component);
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
