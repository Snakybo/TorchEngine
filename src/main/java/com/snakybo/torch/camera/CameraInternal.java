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

package com.snakybo.torch.camera;

import com.snakybo.torch.asset.AssetLoader;
import com.snakybo.torch.color.Color;
import com.snakybo.torch.component.camera.Camera;
import com.snakybo.torch.object.Transform;
import com.snakybo.torch.renderer.Renderer;
import com.snakybo.torch.scene.Scene;
import com.snakybo.torch.texture.Texture;
import com.snakybo.torch.texture.Texture2D;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class CameraInternal
{
	private static Set<CameraInternal> cameras = new HashSet<>();
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
		skybox = new Skybox(AssetLoader.load(Texture2D.class, "skybox_default.png"));
		
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
		
		Scene.getCurrentScene().getAllGameObjects().forEach(Renderer::render);
		
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
	 * Get the {@link CameraClearFlags} this camera is using.
	 * @return The {@link CameraClearFlags} this camera is using.
	 */
	public final CameraClearFlags getClearFlags()
	{
		return clearFlags;
	}
	
	/**
	 * Get the projection of this camera.
	 * @return The projection of the camera.
	 */
	public final Matrix4f getProjection()
	{
		return new Matrix4f(projection);
	}
	
	/**
	 * Get the view projection of this camera.
	 * @return The view projection of the camera.
	 */
	public final Matrix4f getViewProjection()
	{
		Vector3f position = transform.getPosition().mul(-1);
		Quaternionf rotation = transform.getRotation().conjugate();
		
		Matrix4f viewProjection = getProjection();
		viewProjection.translate(position);
		viewProjection.rotate(rotation);
		
		return viewProjection;
	}
	
	/**
	 * Get the clear color of this camera.
	 * @return The clear color of the camera.
	 */
	public final Color getClearColor()
	{
		return clearColor;
	}
	
	
	public static void setMainCamera(CameraInternal camera)
	{
		main = camera;
	}
	
	/**
	 * Get all cameras.
	 * @return All cameras.
	 */
	public static Iterable<CameraInternal> getCameras()
	{
		return cameras;
	}
	
	/**
	 * Get the current camera, use {@link Camera#getCurrentCamera()} unless you have to access internal cameras.
	 * @return The current camera.
	 */
	public static CameraInternal getCurrentCamera()
	{
		return current;
	}
	
	public static CameraInternal getMainCamera()
	{
		return main;
	}
}
