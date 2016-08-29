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

/**
 * <p>
 * Used internally by the engine.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class GameObjectInternal
{
	private GameObjectInternal()
	{
		throw new AssertionError();
	}
	
	public static void processAdditions(GameObject gameObject)
	{
		gameObject.componentsToAdd.forEach(Component::onStart);
		gameObject.componentsToAdd.clear();
	}
	
	public static void processRemovals(GameObject gameObject)
	{
		gameObject.componentsToRemove.forEach(Component::onDestroy);
		gameObject.components.removeAll(gameObject.componentsToRemove);
		gameObject.componentsToRemove.clear();
	}
	
	public static void onUpdate(GameObject gameObject)
	{
		gameObject.components.forEach(Component::onUpdate);
	}
	
	public static void onPostUpdate(GameObject gameObject)
	{
		gameObject.components.forEach(Component::onPostUpdate);
	}
	
	public static void onPreRender(GameObject gameObject)
	{
		gameObject.components.forEach(Component::onPreRenderObject);
	}
	
	public static void onRender(GameObject gameObject)
	{
		gameObject.components.forEach(Component::onRenderObject);
	}
	
	public static void onPostRender(GameObject gameObject)
	{
		gameObject.components.forEach(Component::onPostRenderObject);
	}
	
	public static void onRenderGizmos(GameObject gameObject)
	{
		gameObject.components.forEach(Component::onRenderGizmos);
	}
}
