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
import com.snakybo.torch.object.GameObjectNotifier;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class SceneInternal
{
	private SceneInternal()
	{
		throw new AssertionError();
	}
	
	public static void processAdditions()
	{
		Scene.gameObjectsToAdd.forEach(GameObjectNotifier::start);
		Scene.gameObjectsToAdd.clear();
	}
	
	public static void processRemovals()
	{
		Scene.gameObjectsToRemove.forEach(GameObjectNotifier::destroy);
		Scene.gameObjects.removeAll(Scene.gameObjectsToRemove);
		Scene.gameObjectsToRemove.clear();
	}
	
	public static void add(GameObject obj)
	{
		Scene.gameObjectsToAdd.add(obj);
		Scene.gameObjects.add(obj);
	}
	
	public static void remove(GameObject obj)
	{
		Scene.gameObjectsToRemove.add(obj);
	}
}
