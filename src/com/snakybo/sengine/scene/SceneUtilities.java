package com.snakybo.sengine.scene;

import java.util.HashSet;
import java.util.Set;

import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.object.GameObject;
import com.snakybo.sengine.object.GameObjectUtilities;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class SceneUtilities
{
	private static Set<GameObject> frameQueue = new HashSet<GameObject>();
	
	private SceneUtilities()
	{
		throw new AssertionError();
	}
	
	/**
	 * Construct the frame queue, the frame queue contains all {@link GameObject}s which will receive updates this frame.
	 * It will also call {@link GameObjectUtilities#constructFrameQueue(GameObject)} for those {@link GameObject}s
	 */
	public static void constructFrameQueue()
	{
		frameQueue.clear();
		frameQueue.addAll(Scene.gameObjects);
		
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
		Scene.gameObjects.add(gameObject);
	}
	
	/**
	 * Unregister a {@link GameObject}, this will remove the {@link GameObject} from the scene
	 * @param gameObject - The {@link GameObject} to remove
	 * @see GameObject
	 */
	public static void unregister(GameObject gameObject)
	{
		Scene.gameObjects.remove(gameObject);
	}
}
