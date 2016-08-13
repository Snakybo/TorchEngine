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

import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.object.GameObject;
import com.snakybo.torch.object.GameObjectNotifier;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_EXTENSIONS;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetString;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Renderer
{
	private Renderer()
	{
		throw new AssertionError();
	}
	
	public static void create()
	{
		LoggerInternal.log("Creating OpenGL module");
		
		createCapabilities();
		
		LoggerInternal.log("OpenGL Vendor: " + glGetString(GL_VENDOR));
		LoggerInternal.log("OpenGL Renderer: " + glGetString(GL_RENDERER));
		LoggerInternal.log("OpenGL Version: " + glGetString(GL_VERSION));
		LoggerInternal.log("OpenGL Extensions: " + glGetString(GL_EXTENSIONS));
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}
	
	/**
	 * Render a single {@link GameObject}.
	 * @param gameObject The {@link GameObject} to render.
	 */
	public static void render(GameObject gameObject)
	{
		GameObjectNotifier.preRender(gameObject);
		GameObjectNotifier.render(gameObject);
		GameObjectNotifier.postRender(gameObject);
	}
}