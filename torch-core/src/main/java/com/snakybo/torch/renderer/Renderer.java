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

package com.snakybo.torch.renderer;

import com.snakybo.torch.module.Module;
import com.snakybo.torch.module.RenderModule;
import com.snakybo.torch.object.GameObject;
import com.sun.org.apache.xpath.internal.operations.Mod;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Renderer
{
	private static IRenderer renderer;

	private Renderer()
	{
		throw new AssertionError();
	}
	
	/**
	 * Render a single {@link GameObject}.
	 * @param gameObject The {@link GameObject} to render.
	 */
	public static void render(GameObject gameObject)
	{
		if(renderer == null)
		{
			renderer = Module.getModule(RenderModule.class).getRenderer();
		}

		renderer.render(gameObject);
	}
}
