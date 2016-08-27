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

import com.snakybo.torch.object.GameObject;
import com.snakybo.torch.util.queue.IQueueProcessor;
import com.snakybo.torch.util.queue.QueueOperation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Krol
 * @since 1.0
 */
public final class SceneQueueProcessor implements IQueueProcessor
{
	@Override
	public final void processQueue()
	{
		Set<Scene> destroyed = new HashSet<>();
		
		for(Scene scene : Scene.allScenes)
		{
			Map<GameObject, QueueOperation> queueCopy = new HashMap<>(scene.queue);

			for(Map.Entry<GameObject, QueueOperation> e : queueCopy.entrySet())
			{
				switch(e.getValue())
				{
				case Add:
					scene.gameObjects.add(e.getKey());
					e.getKey().notify("onStart");
					scene.queue.remove(e.getKey());
					break;
				case Remove:
					scene.gameObjects.remove(e.getKey());
					e.getKey().notify("onDestroy");
					scene.queue.remove(e.getKey());
					break;
				}
			}

			if(scene.destroyed)
			{
				for(GameObject obj : scene.gameObjects)
				{
					obj.notify("onDestroy");
				}
				
				scene.gameObjects.clear();
				Scene.currentScene = Scene.currentScene == scene ? null : Scene.currentScene;
				
				destroyed.add(scene);
			}
		}
		
		for(Scene scene : destroyed)
		{
			Scene.allScenes.remove(scene);
		}
	}
}
