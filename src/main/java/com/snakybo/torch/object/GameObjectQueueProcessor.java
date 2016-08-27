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

import com.snakybo.torch.util.queue.IQueueProcessor;
import com.snakybo.torch.util.queue.QueueOperation;
import com.snakybo.torch.scene.Scene;
import com.snakybo.torch.scene.SceneRegisterer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin Krol
 * @since 1.0
 */
public final class GameObjectQueueProcessor implements IQueueProcessor
{
	@Override
	public final void processQueue()
	{
		for(GameObject gameObject : Scene.getCurrentScene().getAllGameObjects())
		{
			Map<Component, QueueOperation> queueCopy = new HashMap<>(gameObject.queue);

			for(Map.Entry<Component, QueueOperation> e : queueCopy.entrySet())
			{
				switch(e.getValue())
				{
				case Add:
					addComponent(gameObject, e.getKey());
					break;
				case Remove:
					removeComponent(gameObject, e.getKey(), true);
					break;
				}
			}

			if(gameObject.destroyed)
			{
				List<GameObject> queue = new ArrayList<>();
				queue.add(gameObject);

				// Destroy all children of the GameObject
				for(Transform transform : gameObject.transform.getChildren())
				{
					queue.add(transform.gameObject);
				}

				for(GameObject obj : queue)
				{
					// Destroy all components
					for(Component component : gameObject.components)
					{
						removeComponent(obj, component, false);
					}

					// Remove the GameObject from the scene
					SceneRegisterer.remove(obj, obj.scene);
				}
			}
		}
	}
	
	private void addComponent(GameObject gameObject, Component component)
	{
		gameObject.queue.remove(component);

		// Set the name of the component to include the GameObject's name
		component.setName(gameObject.getName() + ":" + component.getName());
		component.gameObject = gameObject;
		
		gameObject.components.add(component);
		component.onStart();
	}
	
	private void removeComponent(GameObject gameObject, Component component, boolean unregister)
	{
		gameObject.queue.remove(component);

		component.onDestroy();
		
		// Restore the original name of the component
		component.setName(component.getName().split(":")[1]);
		component.gameObject = null;
		
		if(unregister)
		{
			gameObject.components.remove(component);
		}
	}
}
