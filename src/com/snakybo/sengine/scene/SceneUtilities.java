package com.snakybo.sengine.scene;

import com.snakybo.sengine.object.GameObject;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class SceneUtilities
{
	private SceneUtilities()
	{
		throw new AssertionError();
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
