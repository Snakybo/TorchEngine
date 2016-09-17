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

package com.snakybo.torch.graphics;

import com.snakybo.torch.graphics.camera.CameraClearFlags;
import com.snakybo.torch.graphics.camera.CameraInternal;
import com.snakybo.torch.graphics.color.Color;
import com.snakybo.torch.graphics.gizmo.Gizmos;
import com.snakybo.torch.graphics.gizmo.GizmosInternal;
import com.snakybo.torch.object.GameObject;
import com.snakybo.torch.object.GameObjectInternal;
import com.snakybo.torch.scene.SceneInternal;
import com.snakybo.torch.util.debug.LoggerInternal;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_EXTENSIONS;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetString;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class RenderingEngine
{
	private RenderingEngine()
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
		
		glEnable(GL_TEXTURE_2D);
	}
	
	public static void render(CameraInternal camera)
	{
		switch(camera.getClearFlags())
		{
		case Skybox:
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			break;
		case SolidColor:
			Color cc = camera.getClearColor();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glClearColor(cc.getRed(), cc.getGreen(), cc.getBlue(), cc.getAlpha());
			break;
		case DepthOnly:
			glClear(GL_DEPTH_BUFFER_BIT);
			break;
		}
		
		renderObjects(SceneInternal.getAllInitializedGameObjects());
		
		if(camera.getClearFlags() == CameraClearFlags.Skybox)
		{
			camera.getSkybox().render();
		}
	}
	
	public static void renderGizmos()
	{
		GizmosInternal.isInGizmoRenderPass = true;
		
		for(GameObject gameObject : SceneInternal.getAllInitializedGameObjects())
		{
			Gizmos.reset();
			
			glDisable(GL_DEPTH_TEST);
			glDisable(GL_CULL_FACE);
			
			GameObjectInternal.onRenderGizmos(gameObject);
		}
		
		GizmosInternal.isInGizmoRenderPass = false;
	}
	
	private static void renderObjects(Iterable<GameObject> gameObjects)
	{
		for(GameObject gameObject : gameObjects)
		{
			glEnable(GL_DEPTH_TEST);
			glDepthFunc(GL_LESS);
			
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			glEnable(GL_CULL_FACE);
			glCullFace(GL_BACK);
			
			GameObjectInternal.onPreRender(gameObject);
			GameObjectInternal.onRender(gameObject);
			GameObjectInternal.onPostRender(gameObject);
		}
	}
}
