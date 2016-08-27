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

package com.snakybo.torch.scene;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.object.GameObject;
import com.snakybo.torch.object.GameObjectLoader;
import com.snakybo.torch.xml.GameObjectParser;
import com.snakybo.torch.xml.SceneParser;
import com.snakybo.torch.xml.XMLParser;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Scene
{
	static Collection<GameObject> gameObjectsToAdd = new HashSet<>();
	static Collection<GameObject> gameObjectsToRemove = new HashSet<>();
	
	static Set<GameObject> gameObjects = new HashSet<>();
	
	private Scene()
	{
		throw new AssertionError();
	}
	
	public static GameObject[] getAllGameObjects()
	{
		return gameObjects.toArray(new GameObject[gameObjects.size()]);
	}
	
	public static GameObject[] getGameObjectsByName(String name)
	{
		List<GameObject> result = new ArrayList<>();
		
		for(GameObject gameObject : gameObjects)
		{
			if(gameObject.getName().equals(name))
			{
				result.add(gameObject);
			}
		}
		
		return result.toArray(new GameObject[result.size()]);
	}
	
	public static GameObject getGameObjectByName(String name)
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
	public static int getSize()
	{
		return gameObjects.size();
	}
	
	public static void create()
	{
		gameObjectsToRemove.addAll(gameObjects);
		gameObjects.clear();
	}
	
	public static void load(String name)
	{
		LoggerInternal.log("Begin loading of scene: " + name);
		
		try
		{
			SceneParser.SceneData sceneData = (SceneParser.SceneData)XMLParser.decode(name + ".scene");
			
			create();
			
			for(GameObjectParser.GameObjectData gameObjectData : sceneData.gameObjectData)
			{
				GameObjectLoader.load(gameObjectData);
			}
		}
		catch(NoSuchFileException e)
		{
			Logger.logError(e.getMessage(), e);
		}
	}
}
