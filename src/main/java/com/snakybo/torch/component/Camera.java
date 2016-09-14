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
import com.snakybo.torch.graphics.camera.CameraClearFlags;
import com.snakybo.torch.graphics.camera.CameraInternal;
import com.snakybo.torch.graphics.color.Color;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.graphics.window.Window;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.util.serialized.SerializedField;
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
	@SerializedField private Texture skyboxTexture = Assets.load(Texture.class, "torch_internal/skybox_default.png");
	@SerializedField private Color clearColor = Color.BLACK;
	
	private CameraInternal camera;
	private boolean changed;
	
	@Override
	protected void onCreate()
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
	 * <p>
	 * Make the camera render now.
	 * </p>
	 */
	public final void render()
	{
		camera.render();
	}
	
	/**
	 * <p>
	 * Set the projection of the camera.
	 * </p>
	 *
	 * @param projection The new projection.
	 */
	public final void setProjection(Matrix4f projection)
	{
		camera.setProjection(projection);
	}
	
	/**
	 * <p>
	 * Set the field of view of the camera.
	 * </p>
	 *
	 * @param fieldOfView The new field of view.
	 */
	public final void setFieldOfView(float fieldOfView)
	{
		this.fieldOfView = fieldOfView;
		this.changed = true;
	}
	
	/**
	 * <p>
	 * Set the near clipping plane of the camera.
	 * </p>
	 *
	 * @param zNear The new near clipping plane.
	 */
	public final void setNearClippingPlane(float zNear)
	{
		this.zNear = zNear;
		this.changed = true;
	}
	
	/**
	 * <p>
	 * Set the far clipping plane of the camera.
	 * </p>
	 *
	 * @param zFar The new far clipping plane.
	 */
	public final void setFarClippingPlane(float zFar)
	{
		this.zFar = zFar;
		this.changed = true;
	}
	
	/**
	 * <p>
	 * Set the {@link CameraClearFlags} to use.
	 * </p>
	 *
	 * @param clearFlags The new clear flags.
	 */
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		camera.setClearFlags(clearFlags);
	}
	
	/**
	 * <p>
	 * Set the texture of the skybox.
	 * </p>
	 *
	 * @param texture The new texture of the skybox.
	 */
	public final void setSkybox(Texture texture)
	{
		camera.setSkybox(texture);
	}
	
	/**
	 * <p>
	 * Set the clear color of the camera, only used in {@link CameraClearFlags#SolidColor}.
	 * </p>
	 *
	 * @param clearColor The new clear color.
	 */
	public final void setClearColor(Color clearColor)
	{
		camera.setClearColor(clearColor);
	}
	
	/**
	 * <p>
	 * Get the projection matrix.
	 * </p>
	 *
	 * @return The projection matrix.
	 */
	public final Matrix4f getProjectionMatrix()
	{
		return camera.getProjectionMatrix();
	}
	
	/**
	 * <p>
	 * Get the field of view.
	 * </p>
	 *
	 * @return The field of view.
	 */
	public final float getFieldOfView()
	{
		return fieldOfView;
	}
	
	/**
	 * <p>
	 * Get the near clipping plane.
	 * </p>
	 *
	 * @return The near clipping plane.
	 */
	public final float getNearClippingPlane()
	{
		return zNear;
	}
	
	/**
	 * <p>
	 * Get the far clipping plane.
	 * </p>
	 *
	 * @return The far clipping plane.
	 */
	public final float getFarClippingPlane()
	{
		return zFar ;
	}
	
	/**
	 * <p>
	 * Get the {@link CameraClearFlags}.
	 * </p>
	 *
	 * @return The {@link CameraClearFlags} this camera is using.
	 */
	public final CameraClearFlags getClearFlags()
	{
		return camera.getClearFlags();
	}
	
	/**
	 * <p>
	 * Get the view matrix.
	 * </p>
	 *
	 * @return The view matrix of the camera.
	 */
	public final Matrix4f getViewMatrix()
	{
		return camera.getViewMatrix();
	}
	
	/**
	 * <p>
	 * Get the clear color.
	 * </p>
	 *
	 * @return The clear color of the camera.
	 */
	public final Color getClearColor()
	{
		return camera.getClearColor();
	}
	
	/**
	 * <p>
	 * Set the main camera.
	 * </p>
	 *
	 * @param camera The new main camera.
	 */
	public static void setMainCamera(Camera camera)
	{
		CameraInternal.setMainCamera(camera.camera);
	}
	
	/**
	 * <p>
	 * Get an {@code Iterator} containing all cameras.
	 * </p>
	 *
	 * @return All cameras.
	 */
	public static Iterable<Camera> getCameras()
	{
		return cameras;
	}
	
	/**
	 * <p>
	 * Get the main camera.
	 * </p>
	 *
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
	 * <p>
	 * Get the current camera, this will return {@code null} if
	 * the engine is rendering a {@link CameraInternal}.
	 * </p>
	 *
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
