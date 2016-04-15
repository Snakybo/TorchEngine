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

package com.snakybo.sengine.object;

import com.snakybo.sengine.input.cursor.CursorEnterMode;
import com.snakybo.sengine.scene.SceneUtilities;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GameObjectUtilities
{
	private GameObjectUtilities()
	{
		throw new AssertionError();
	}
	
	/**
	 * Notify a GameObject that the cursor has entered or left the game window
	 * @param gameObject - The GameObject to notify
	 * @param enterMode - The enter mode
	 */
	public static void notifyCursorEnter(GameObject gameObject, CursorEnterMode enterMode)
	{
		for(Component component : gameObject.frameQueue)
		{
			if(component.started)
			{
				component.onCursorEnter(enterMode);
			}
		}
	}
	
	/**
	 * Notify a GameObject that a character has been pressed on the keyboard
	 * @param gameObject - The GameObject to notify
	 * @param c - The character pressed
	 */
	public static void notifyCharPressed(GameObject gameObject, char c)
	{
		for(Component component : gameObject.frameQueue)
		{
			if(component.started)
			{
				component.onCharPressed(c);
			}
		}
	}
	
	/**
	 * Construct the frame queue of a {@link GameObject},
	 * the frame queue are the components which can receive callbacks this frame
	 * @param gameObject - The {@link GameObject} to construct the frame queue for
	 */
	public static void constructFrameQueue(GameObject gameObject)
	{
		gameObject.frameQueue.clear();
		gameObject.frameQueue.addAll(gameObject.components);
	}
	
	/**
	 * Call {@link Component#start()} on all components in the frame queue of a {@link GameObject} if neccecary
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#start()
	 */
	public static void start(GameObject gameObject)
	{
		// Call start() on all components that still require it
		for(Component component : gameObject.frameQueue)
		{
			if(!component.started)
			{
				component.started = true;
				component.start();
			}
		}
	}
	
	/**
	 * Call {@link Component#update()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#update()
	 */
	public static void update(GameObject gameObject)
	{		
		// Call update() on components
		for(Component component : gameObject.frameQueue)
		{
			component.update();
		}
	}
	
	/**
	 * Call {@link Component#postUpdate()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#postUpdate()
	 */
	public static void postUpdate(GameObject gameObject)
	{
		// Call postUpdate() on components
		for(Component component : gameObject.frameQueue)
		{
			component.postUpdate();
		}
	}
	
	/**
	 * Call {@link Component#preRenderObject()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#preRenderObject()
	 */
	public static void preRenderObject(GameObject gameObject)
	{
		// Call preRender() on components
		for(Component component : gameObject.frameQueue)
		{
			component.preRenderObject();
		}
	}
	
	/**
	 * Call {@link Component#renderObject()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#renderObject()
	 */
	public static void renderObject(GameObject gameObject)
	{
		// Call render() on components
		for(Component component : gameObject.frameQueue)
		{
			component.renderObject();
		}
	}
	
	/**
	 * Call {@link Component#postRenderObject()} on all components in the frame queue of a {@link GameObject}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#postRenderObject()
	 */
	public static void postRenderObject(GameObject gameObject)
	{
		// Call postRender() on components
		for(Component component : gameObject.frameQueue)
		{
			component.postRenderObject();
		}
	}
	
	/**
	 * Call {@link Component#destroy()} on all components in the frame queue of a {@link GameObject},
	 * and call {@link SceneUtilities#unregister(GameObject)}
	 * @param gameObject - The target {@link GameObject}
	 * @see Component#destroy()
	 */
	public static void destroy(GameObject gameObject)
	{
		// Also destroy all children of the GameObject
		for(Transform transform : gameObject.getTransform().getChildren())
		{
			destroy(transform.getGameObject());
		}
		
		// Let the components know they're being destroyed
		for(Component component : gameObject.frameQueue)
		{
			removeComponent(gameObject, component);
		}
		
		// Unregister the GameObject
		SceneUtilities.unregister(gameObject);
	}
	
	/**
	 * Remove a single {@link Component} from a {@link GameObject}, it will also call {@link Component#destroy()} on the component
	 * @param gameObject - The parent {@link GameObject}
	 * @param component - The {@link Component} to remove
	 * @see Component#destroy()
	 */
	public static void removeComponent(GameObject gameObject, Component component)
	{
		if(gameObject.components.remove(component))
		{
			// Call destroy() on the component, but only if it's in the frame queue
			if(gameObject.frameQueue.contains(component))
			{
				component.destroy();
			}
			
			// Remove the GameObject as parent of the component
			component.gameObject = null;
			
			// Restore the original name of the component
			String[] name = component.getName().split(":");
			component.setName(name[1]);
		}
	}
}
