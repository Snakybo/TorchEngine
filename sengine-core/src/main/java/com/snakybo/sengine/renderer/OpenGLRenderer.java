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

package com.snakybo.sengine.renderer;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_EXTENSIONS;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetString;

import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.object.GameObject;
import com.snakybo.sengine.object.GameObjectUtilities;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class OpenGLRenderer
{
	private OpenGLRenderer()
	{
		throw new AssertionError();
	}
	
	/**
	 * Create the OpenGL renderer
	 */
	public static void create()
	{
		createCapabilities();
		
		LoggerInternal.log("Vendor: " + glGetString(GL_VENDOR), "OpenGL");
		LoggerInternal.log("Renderer: " + glGetString(GL_RENDERER), "OpenGL");
		LoggerInternal.log("Version: " + glGetString(GL_VERSION), "OpenGL");
		LoggerInternal.log("Extensions: " + glGetString(GL_EXTENSIONS), "OpenGL");
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
	}
	
	/**
	 * Render a single object
	 * @param gameObject The object to render
	 */
	public static void renderObject(GameObject gameObject)
	{
		GameObjectUtilities.preRenderObject(gameObject);
		GameObjectUtilities.renderObject(gameObject);
		GameObjectUtilities.postRenderObject(gameObject);
	}
}
