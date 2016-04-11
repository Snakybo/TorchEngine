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

package com.snakybo.sengine.scene;

import java.util.HashSet;
import java.util.Set;

import com.snakybo.sengine.input.cursor.CursorEnterMode;
import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.object.GameObject;
import com.snakybo.sengine.object.GameObjectUtilities;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class SceneUtilities
{
	private static Set<GameObject> frameQueue = new HashSet<GameObject>();
	
	private SceneUtilities()
	{
		throw new AssertionError();
	}
	
	/**
	 * Actually load a new scene
	 */
	public static void loadScene()
	{
		SceneManager.doLoad();
	}
	
	/**
	 * Construct the frame queue, the frame queue contains all {@link GameObject}s which will receive updates this frame.
	 * It will also call {@link GameObjectUtilities#constructFrameQueue(GameObject)} for those {@link GameObject}s
	 */
	public static void constructFrameQueue()
	{
		frameQueue.clear();
		frameQueue.addAll(SceneManager.currentScene.gameObjects);
		
		// Construct the frame queue for all GameObjects in the frame queue
		for(GameObject gameObject : frameQueue)
		{
			GameObjectUtilities.constructFrameQueue(gameObject);
		}
	}
	
	/**
	 * Run an update cycle. The update cycle is:
	 * <ol>
	 * 	<li>Call {@link Component#start()} on all {@link Component}s if neccecary</li>
	 * 	<li>Call {@link Component#update()} on all {@link Component}s</li>
	 * 	<li>Call {@link Component#postUpdate()} on all {@link Component}s</li>
	 * </ol>
	 */
	public static void runUpdateCycle()
	{
		// Call start() all GameObjects in the frame queue if neccecary
		for(GameObject gameObject : frameQueue)
		{
			GameObjectUtilities.start(gameObject);
		}
		
		// Call update() all GameObjects in the frame queue
		for(GameObject gameObject : frameQueue)
		{
			GameObjectUtilities.update(gameObject);
		}
		
		// Call postUpdate() on all GameObjects in the frame queue
		for(GameObject gameObject : frameQueue)
		{
			GameObjectUtilities.postUpdate(gameObject);
		}
	}
	
	/**
	 * Run a render cycle. The render cycle is:
	 * <ol>
	 * 	<li>Clear the scene</li>
	 * 	<li>Call {@link Camera#render()} on all {@link Camera}s in the scene</li>
	 * 	<li>Render lighting and shadows</li>
	 * 	<li>Render post-process effects
	 */
	public static void runRenderCycle()
	{
		
	}
	
	/**
	 * Register a {@link GameObject}, this will add the {@link GameObject} to the scene
	 * @param gameObject - The {@link GameObject} to add
	 * @see GameObject
	 */
	public static void register(GameObject gameObject)
	{
		SceneManager.currentScene.gameObjects.add(gameObject);
	}
	
	/**
	 * Unregister a {@link GameObject}, this will remove the {@link GameObject} from the scene
	 * @param gameObject - The {@link GameObject} to remove
	 * @see GameObject
	 */
	public static void unregister(GameObject gameObject)
	{
		SceneManager.currentScene.gameObjects.remove(gameObject);
	}
	
	/**
	 * Notify all GameObjects that the cursor has entered or left the game window
	 * @param enterMode - The enter mode
	 */
	public static void notifyGameObjectsCursorEntered(CursorEnterMode enterMode)
	{
		for(GameObject gameObject : frameQueue)
		{
			GameObjectUtilities.notifyCursorEnter(gameObject, enterMode);
		}
	}
	
	/**
	 * Notify all GameObjects that a character has been pressed on the keyboard
	 * @param c - The character pressed
	 */
	public static void notifyGameObjectsCharPressed(char c)
	{
		for(GameObject gameObject : frameQueue)
		{
			GameObjectUtilities.notifyCharPressed(gameObject, c);
		}
	}
}
