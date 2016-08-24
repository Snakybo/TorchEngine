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

package com.snakybo.torch.component;

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.camera.CameraClearFlags;
import com.snakybo.torch.camera.CameraInternal;
import com.snakybo.torch.color.Color;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.serialized.SerializedField;
import com.snakybo.torch.texture.Texture;
import com.snakybo.torch.texture.Texture2D;
import com.snakybo.torch.window.Window;
import org.joml.Matrix4f;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * The camera component. Everything in the camera's viewport
 * is rendered automatically every frame.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Camera extends Component
{
	private static Set<Camera> cameras = new HashSet<>();
	
	@SerializedField private float fieldOfView = 75;
	@SerializedField private float zNear = 0.01f;
	@SerializedField private float zFar = 1000f;
	@SerializedField private CameraClearFlags clearFlags = CameraClearFlags.Skybox;
	@SerializedField private Texture2D skyboxTexture = Assets.load(Texture2D.class, "skybox_default.png");
	@SerializedField private Color clearColor = Color.BLACK;
	
	private CameraInternal camera;
	private boolean changed;
	
	@Override
	protected final void onStart()
	{
		Matrix4f projection = new Matrix4f().perspective((float)Math.toRadians(fieldOfView), Window.getAspectRatio(), zNear, zFar);
		
		camera = new CameraInternal(projection, clearFlags);
		camera.setTransform(getTransform());
		camera.setSkybox(skyboxTexture);
		camera.setClearColor(clearColor);
		
		cameras.add(this);
	}
	
	@Override
	protected final void onDestroy()
	{
		cameras.remove(this);
		camera.destroy();
	}
	
	@Override
	protected final void onPostUpdate()
	{
		if(changed)
		{
			changed = false;
			setProjection(new Matrix4f().perspective((float)Math.toRadians(fieldOfView), Window.getAspectRatio(), zNear, zFar));
		}
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
	
	/**
	 * Set the field of fiew of the camera.
	 * @param fieldOfView The new field of view.
	 */
	public final void setFieldOfView(float fieldOfView)
	{
		this.fieldOfView = fieldOfView;
		this.changed = true;
	}
	
	/**
	 * Set the near clipping plane of the camera.
	 * @param zNear The new near clipping plane.
	 */
	public final void setNearClippingPlane(float zNear)
	{
		this.zNear = zNear;
		this.changed = true;
	}
	
	/**
	 * Set the far clipping plane of the camera.
	 * @param zFar The new far clipping plane.
	 */
	public final void setFarClippingPlane(float zFar)
	{
		this.zFar = zFar;
		this.changed = true;
	}
	
	/**
	 * Set the {@link CameraClearFlags} to use.
	 * @param clearFlags The new clear flags.
	 */
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		camera.setClearFlags(clearFlags);
	}
	
	/**
	 * Set the texture of the skybox.
	 * @param texture The new texture of the skybox.
	 */
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
	 * @return The projection.
	 */
	public final Matrix4f getProjection()
	{
		return camera.getProjection();
	}
	
	/**
	 * @return The field of view.
	 */
	public final float getFieldOfView()
	{
		return fieldOfView;
	}
	
	/**
	 * @return The near clipping plane.
	 */
	public final float getNearClippingPlane()
	{
		return zNear;
	}
	
	/**
	 * @return The far clipping plane.
	 */
	public final float getFarClippingPlane()
	{
		return zFar ;
	}
	
	/**
	 * @return The {@link CameraClearFlags} this camera is using.
	 */
	public final CameraClearFlags getClearFlags()
	{
		return camera.getClearFlags();
	}
	
	/**
	 * @return The view of the camera.
	 */
	public final Matrix4f getView()
	{
		return camera.getView();
	}
	
	/**
	 * @return The clear color of the camera.
	 */
	public final Color getClearColor()
	{
		return camera.getClearColor();
	}
	
	/**
	 * Set the main camera.
	 * @param camera The new main camera.
	 */
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
	 * Get the main camera.
	 * @return The main camera.
	 */
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
}
