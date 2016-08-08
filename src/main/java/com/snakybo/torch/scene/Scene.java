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

import com.snakybo.torch.interfaces.IDestroyable;
import com.snakybo.torch.object.GameObject;
import com.snakybo.torch.queue.QueueOperation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Scene implements IDestroyable
{
	static Set<Scene> allScenes;
	static Scene currentScene;
	
	Set<GameObject> gameObjects;
	Map<GameObject, QueueOperation> queue;
	
	boolean destroyed;
	
	static
	{
		allScenes = new HashSet<>();
	}
	
	public Scene()
	{
		gameObjects = new HashSet<>();
		queue = new HashMap<>();
		
		allScenes.add(this);
		
		if(currentScene == null)
		{
			makeCurrent();
		}
	}
	
	@Override
	public void destroy()
	{
		queue.clear();
		destroyed = true;
	}
	
	public void makeCurrent()
	{
		currentScene = this;
	}
	
	public final void addObject(GameObject obj)
	{
		if(!gameObjects.contains(obj) && !queue.containsKey(obj))
		{
			queue.put(obj, QueueOperation.Add);
		}
		else if(queue.containsKey(obj) && queue.get(obj) == QueueOperation.Remove)
		{
			queue.remove(obj);
		}
	}
	
	public final void removeObject(GameObject obj)
	{
		if(gameObjects.contains(obj) && !queue.containsKey(obj))
		{
			queue.put(obj, QueueOperation.Remove);
		}
		else if(queue.containsKey(obj) && queue.get(obj) == QueueOperation.Add)
		{
			queue.remove(obj);
		}
	}
	
	public final Iterable<GameObject> getAllGameObjects()
	{
		return gameObjects;
	}
	
	/**
	 * @return The number of {@link GameObject}s in the scene
	 */
	public final int getSize()
	{
		return gameObjects.size();
	}
	
	public static Iterable<Scene> getAllScenes()
	{
		return allScenes;
	}
	
	public static Scene getCurrentScene()
	{
		return currentScene;
	}
}
