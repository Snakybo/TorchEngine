package com.snakybo.sengine.scene;

import java.util.HashSet;
import java.util.Set;

import com.snakybo.sengine.object.GameObject;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class Scene
{
	static Set<GameObject> gameObjects = new HashSet<GameObject>();
	
	private Scene()
	{
		throw new AssertionError();
	}
	
	/**
	 * Find a {@link GameObject} with the specified name
	 * @param name - The name of the {@link GameObject}
	 * @return The {@link GameObject} with the specified name, or {@code null} if no {@link GameObject} was found
	 */
	public static GameObject find(String name)
	{
		for(GameObject gameObject : gameObjects)
		{
			if(gameObject.getName().equals(name))
			{
				return gameObject;
			}
		}
		
		return null;
	}
	
	/**
	 * @return The number of {@link GameObject}s in the scene
	 */
	public static int getSceneSize()
	{
		return gameObjects.size();
	}
}
