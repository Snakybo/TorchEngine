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

package com.snakybo.torch.graphics.camera;

import com.snakybo.torch.graphics.color.Color;
import com.snakybo.torch.component.Camera;
import com.snakybo.torch.object.GameObject;
import com.snakybo.torch.object.Transform;
import com.snakybo.torch.graphics.renderer.Renderer;
import com.snakybo.torch.scene.Scene;
import com.snakybo.torch.graphics.texture.Texture;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * <p>
 * The camera used internally by the engine, all {@link Camera}s internally
 * have a {@link CameraInternal} and are just a relay for the methods here.
 * </p>
 *
 * <p>
 * You most likely don't have a need to use this class,
 * instead use the provided {@link Camera} component.
 * </p>
 *
 * @see Camera
 *
 * @author Snakybo
 * @since 1.0
 */
public final class CameraInternal
{
	private static List<CameraInternal> cameras = new ArrayList<>();
	private static CameraInternal current;
	private static CameraInternal main;
	
	private CameraClearFlags clearFlags;
	
	private Matrix4f projection;	
	private Transform transform;
	private Color clearColor;
	private Skybox skybox;
	
	/**
	 * Create a new camera.
	 * @param projection The projection of the camera.
	 * @param clearFlags The {@link CameraClearFlags} to use.
	 */
	public CameraInternal(Matrix4f projection, CameraClearFlags clearFlags)
	{
		this.projection = projection;
		this.clearFlags = clearFlags;
		
		clearColor = new Color(0, 0, 0, 0);
		skybox = new Skybox();
		
		transform = new Transform();
		
		cameras.add(this);
		
		if(main == null)
		{
			main = this;
		}
	}
	
	/**
	 * Destroy the camera.
	 */
	public final void destroy()
	{
		cameras.remove(this);
		
		if(main == this)
		{
			main = cameras.size() > 0 ? cameras.get(0) : null;
		}
	}
	
	/**
	 * Make the camera render now.
	 */
	public final void render()
	{
		current = this;
		
		switch(clearFlags)
		{
		case Skybox:
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			break;
		case SolidColor:
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
			break;
		case DepthOnly:
			glClear(GL_DEPTH_BUFFER_BIT);
			break;
		}
		
		for(GameObject gameObject : Scene.getAllGameObjects())
		{
			Renderer.render(gameObject);
		}
		
		if(clearFlags == CameraClearFlags.Skybox)
		{
			skybox.render();
		}
		
		current = null;
	}
	
	/**
	 * Set the {@link CameraClearFlags} to use.
	 * @param clearFlags The new clear flags.
	 */
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		this.clearFlags = clearFlags;
	}
	
	/**
	 * Set the projection of the camera.
	 * @param projection The new projection.
	 */
	public final void setProjection(Matrix4f projection)
	{
		this.projection = projection;
	}
	
	/**
	 * Set the transform of the camera.
	 * @param transform The new transform.
	 */
	public final void setTransform(Transform transform)
	{
		this.transform = transform;
	}
	
	/**
	 * Set the skybox texture of the camera.
	 * @param texture The new texture of the skybox.
	 */
	public final void setSkybox(Texture texture)
	{
		skybox.setTexture(texture);
	}
	
	/**
	 * Set the clear color of the camera, only used in {@link CameraClearFlags#SolidColor}.
	 * @param clearColor The new clear color.
	 */
	public final void setClearColor(Color clearColor)
	{
		this.clearColor = clearColor;
	}
	
	/**
	 * @return The {@link CameraClearFlags} this camera is using.
	 */
	public final CameraClearFlags getClearFlags()
	{
		return clearFlags;
	}
	
	/**
	 * @return The projection of the camera.
	 */
	public final Matrix4f getProjection()
	{
		return new Matrix4f(projection);
	}
	
	/**
	 * @return The view projection of the camera.
	 */
	public final Matrix4f getView()
	{
		return new Matrix4f().translate(transform.getPosition()).rotate(transform.getRotation());
	}
	
	/**
	 * @return The transform of the camera.
	 */
	public final Transform getTransform()
	{
		return transform;
	}
	
	/**
	 * @return The clear color of the camera.
	 */
	public final Color getClearColor()
	{
		return clearColor;
	}
	
	/**
	 * Set the main camera, use {@link Camera#setMainCamera(Camera)} unless you specifically need an internal camera.
	 * @param camera
	 */
	public static void setMainCamera(CameraInternal camera)
	{
		main = camera;
	}
	
	/**
	 * Get all cameras, use {@link Camera#getCameras()} unless you specifically need an internal camera.
	 * @return All cameras.
	 */
	public static Iterable<CameraInternal> getCameras()
	{
		return cameras;
	}
	
	/**
	 * Get the main camera, use {@link Camera#getMainCamera()} unless you specifically need an internal camera.
	 * @return The main camera.
	 */
	public static CameraInternal getMainCamera()
	{
		return main;
	}
	
	/**
	 * Get the current camera, use {@link Camera#getCurrentCamera()} unless you specifically need an internal camera.
	 * @return The current camera.
	 */
	public static CameraInternal getCurrentCamera()
	{
		return current;
	}
}
