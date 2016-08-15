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

package com.snakybo.torch.component.camera;

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.camera.CameraClearFlags;
import com.snakybo.torch.camera.CameraInternal;
import com.snakybo.torch.color.Color;
import com.snakybo.torch.serialized.Serialized;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.texture.Texture;
import com.snakybo.torch.texture.Texture2D;
import com.snakybo.torch.window.Window;
import org.joml.Matrix4f;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Camera extends Component
{
	private static Set<Camera> cameras = new HashSet<>();
	
	@Serialized	private float fieldOfView = 75;
	@Serialized	private float zNear = 0.01f;
	@Serialized	private float zFar = 1000f;
	@Serialized	CameraClearFlags clearFlags = CameraClearFlags.Skybox;
	@Serialized	Texture2D skyboxTexture = Assets.load(Texture2D.class, "skybox_default.png");
	
	private CameraInternal camera;
	
	@Override
	protected final void start()
	{
		Matrix4f projection = new Matrix4f().perspective((float)Math.toRadians(fieldOfView), Window.getAspectRatio(), zNear, zFar);
		camera = new CameraInternal(projection, clearFlags, skyboxTexture);
		
		cameras.add(this);
		camera.setTransform(getTransform());
	}
	
	@Override
	protected void destroy()
	{
		cameras.remove(this);
		camera.destroy();
	}
	
	/**
	 * Make the camera render now.
	 */
	public final void render()
	{
		camera.render();
	}
	
	/**
	 * Set the projection of the camera.
	 * @param projection The new projection.
	 */
	public final void setProjection(Matrix4f projection)
	{
		camera.setProjection(projection);
	}
	
	public final void setFieldOfView(float fieldOfView)
	{
		this.fieldOfView = fieldOfView;
		setProjection(new Matrix4f().perspective((float)Math.toRadians(fieldOfView), Window.getAspectRatio(), zNear, zFar));
	}
	
	public final void setNearClippingPlane(float zNear)
	{
		this.zNear = zNear;
		setProjection(new Matrix4f().perspective((float)Math.toRadians(fieldOfView), Window.getAspectRatio(), zNear, zFar));
	}
	
	public final void setFarClippingPlane(float zFar)
	{
		this.zFar = zFar;
		setProjection(new Matrix4f().perspective((float)Math.toRadians(fieldOfView), Window.getAspectRatio(), zNear, zFar));
	}
	
	/**
	 * Set the {@link CameraClearFlags} to use.
	 * @param clearFlags The new clear flags.
	 */
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		camera.setClearFlags(clearFlags);
	}
	
	public final void setSkybox(Texture texture)
	{
		camera.setSkybox(texture);
	}
	
	/**
	 * Set the clear color of the camera, only used in {@link CameraClearFlags#SolidColor}.
	 * @param clearColor The new clear color.
	 */
	public final void setClearColor(Color clearColor)
	{
		camera.setClearColor(clearColor);
	}
	
	/**
	 * Get the projection of this camera.
	 * @return The projection of the camera.
	 */
	public final Matrix4f getProjection()
	{
		return camera.getProjection();
	}
	
	public final float getFieldOfView()
	{
		return fieldOfView;
	}
	
	public final float getNearClippingPlane()
	{
		return zNear;
	}
	
	public final float getFarClippingPlane()
	{
		return zFar ;
	}
	
	/**
	 * Get the {@link CameraClearFlags} this camera is using.
	 * @return The {@link CameraClearFlags} this camera is using.
	 */
	public final CameraClearFlags getClearFlags()
	{
		return camera.getClearFlags();
	}
	
	/**
	 * Get the view projection of this camera.
	 * @return The view projection of the camera.
	 */
	public final Matrix4f getViewProjection()
	{
		return camera.getViewProjection();
	}
	
	/**
	 * Get the clear color of this camera.
	 * @return The clear color of the camera.
	 */
	public final Color getClearColor()
	{
		return camera.getClearColor();
	}
	
	public static void setMainCamera(Camera camera)
	{
		CameraInternal.setMainCamera(camera.camera);
	}
	
	/**
	 * Get all cameras.
	 * @return All cameras.
	 */
	public static Iterable<Camera> getCameras()
	{
		return cameras;
	}
	
	/**
	 * Get the current camera, this will return {@code null} if the engine is rendering a {@link CameraInternal}.
	 * @return The current camera or null.
	 */
	public static Camera getCurrentCamera()
	{
		for(Camera camera : cameras)
		{
			if(camera.camera == CameraInternal.getCurrentCamera())
			{
				return camera;
			}
		}
		
		return null;
	}
	
	public static Camera getMainCamera()
	{
		for(Camera camera : cameras)
		{
			if(camera.camera == CameraInternal.getMainCamera())
			{
				return camera;
			}
		}
		
		return null;
	}
}
