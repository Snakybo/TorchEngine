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

import com.snakybo.torch.annotation.SerializedField;
import com.snakybo.torch.graphics.camera.CameraClearFlags;
import com.snakybo.torch.graphics.camera.CameraInternal;
import com.snakybo.torch.graphics.texture.Cubemap;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.util.Rect;
import com.snakybo.torch.util.color.Color;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Camera component, everything in the view of the camera is rendered every frame.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Camera extends Component
{
	private static Map<CameraInternal, Camera> cameras = new HashMap<>();
	
	@SerializedField private float viewportX;
	@SerializedField private float viewportY;
	@SerializedField private float viewportW;
	@SerializedField private float viewportH;
	@SerializedField private float fieldOfView;
	@SerializedField private float zNear;
	@SerializedField private float zFar;
	@SerializedField private int depth;
	
	@SerializedField private CameraClearFlags clearFlags;
	@SerializedField private Cubemap skybox;
	@SerializedField private Color clearColor;
	
	private CameraInternal camera;
	
	protected final void onCreate()
	{
		Rect viewport = new Rect(viewportX, viewportY, viewportW, viewportH);
		
		camera = new CameraInternal(viewport, fieldOfView, zNear, zFar, depth);
		camera.setClearFlags(clearFlags);
		camera.setSkybox(skybox);
		camera.setClearColor(clearColor);
		
		camera.setPosition(getTransform().getPosition());
		camera.setRotation(getTransform().getRotation());
		
		cameras.put(camera, this);
	}
	
	protected final void onPostUpdate()
	{
		camera.setPosition(getTransform().getPosition());
		camera.setRotation(getTransform().getRotation());
	}
	
	protected final void onDestroy()
	{
		camera.destroy();
		cameras.remove(camera);
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
	 * Set the clear flags.
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
	 * Set the skybox texture.
	 * </p>
	 *
	 * @param skybox The new skybox texture.
	 */
	public final void setSkybox(Cubemap skybox)
	{
		camera.setSkybox(skybox);
	}
	
	/**
	 * <p>
	 * Set the clear color.
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
	 * Manually set a projection matrix,
	 * note that this gets overwritten when you make a call to:
	 * </p>
	 *
	 * <ul>
	 *  <li>{@link #setFieldOfView(float)}</li>
	 *  <li>{@link #setNearClipPlane(float)}</li>
	 *  <li>{@link #setFarClipPlane(float)}</li>
	 * </ul>
	 *
	 * @param projection The new projection.
	 */
	public final void setProjection(Matrix4f projection)
	{
		camera.setProjection(projection);
	}
	
	/**
	 * <p>
	 * Set the viewport.
	 * </p>
	 *
	 * @param viewport The new viewport.
	 */
	public final void setViewport(Rect viewport)
	{
		camera.setViewport(viewport);
	}
	
	/**
	 * <p>
	 * Set the field of view.
	 * </p>
	 *
	 * @param fieldOfView The new field of view.
	 */
	public final void setFieldOfView(float fieldOfView)
	{
		camera.setFieldOfView(fieldOfView);
	}
	
	/**
	 * <p>
	 * Set the near clipping plane (zNear).
	 * </p>
	 *
	 * @param zNear The new near clipping plane.
	 */
	public final void setNearClipPlane(float zNear)
	{
		camera.setNearClipPlane(zNear);
	}
	
	/**
	 * <p>
	 * Set the far clipping plane (zFar).
	 * </p>
	 *
	 * @param zFar The new far clipping plane.
	 */
	public final void setFarClipPlane(float zFar)
	{
		camera.setFarClipPlane(zFar);
	}
	
	/**
	 * <p>
	 * Set the depth.
	 * </p>
	 *
	 * <p>
	 * Cameras with a lower depth value will be rendered first.
	 * </p>
	 *
	 * @param depth The new depth.
	 */
	public final void setDepth(int depth)
	{
		camera.setDepth(depth);
	}
	
	/**
	 * <p>
	 * Get the clear flags.
	 * </p>
	 *
	 * @return The clear flags.
	 */
	public final CameraClearFlags getClearFlags()
	{
		return camera.getClearFlags();
	}
	
	/**
	 * <p>
	 * Get the skybox texture.
	 * </p>
	 *
	 * @return The skybox texture.
	 */
	public final Cubemap getSkybox()
	{
		return camera.getSkybox();
	}
	
	/**
	 * <p>
	 * Get the clear color.
	 * </p>
	 *
	 * @return The clear color.
	 */
	public final Color getClearColor()
	{
		return camera.getClearColor();
	}
	
	/**
	 * <p>
	 * Get the projection matrix.
	 * </p>
	 *
	 * @return The projection matrix.
	 */
	public final Matrix4f getProjection()
	{
		return camera.getProjection();
	}
	
	/**
	 * <p>
	 * Get the view matrix.
	 * </p>
	 *
	 * @return The view matrix.
	 */
	public final Matrix4f getViewMatrix()
	{
		return camera.getViewMatrix();
	}
	
	/**
	 * <p>
	 * Get the viewport.
	 * </p>
	 *
	 * @return The viewport.
	 */
	public final Rect getViewport()
	{
		return camera.getViewport();
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
		return camera.getFieldOfView();
	}
	
	/**
	 * <p>
	 * Get the near clipping plane (zNear).
	 * </p>
	 *
	 * @return The near clipping plane.
	 */
	public final float getNearClipPlane()
	{
		return camera.getNearClipPlane();
	}
	
	/**
	 * <p>
	 * Get the far clipping plane (zFar).
	 * </p>
	 *
	 * @return The far clipping plane.
	 */
	public final float getFarClipPlane()
	{
		return camera.getFarClipPlane();
	}
	
	/**
	 * <p>
	 * Get the depth.
	 * </p>
	 *
	 * @return The depth.
	 */
	public final int getDepth()
	{
		return depth;
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
	 * Get the main camera.
	 * </p>
	 *
	 * @return The main camera.
	 */
	public static Camera getMainCamera()
	{
		for(Map.Entry<CameraInternal, Camera> camera : cameras.entrySet())
		{
			if(camera.getKey() == CameraInternal.getMainCamera())
			{
				return camera.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * Get the current camera.
	 * </p>
	 *
	 * <p>
	 * The current camera is the camera that's currently rendering, can be {@code null}.
	 * </p>
	 *
	 * @return The current camera.
	 */
	public static Camera getCurrentCamera()
	{
		for(Map.Entry<CameraInternal, Camera> camera : cameras.entrySet())
		{
			if(camera.getKey() == CameraInternal.getCurrentCamera())
			{
				return camera.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * Get all cameras.
	 * </p>
	 *
	 * @return All cameras.
	 */
	public static Iterable<Camera> getAllCameras()
	{
		return cameras.values();
	}
	
	/**
	 * <p>
	 * Get all cameras, sorted by their {@code depth}.
	 * </p>
	 *
	 * @return The cameras sorted by their {@code depth}.
	 */
	public static Camera[] getAllCamerasSorted()
	{
		List<Camera> result = new ArrayList<>(cameras.values());
		
		Collections.sort(result, (o1, o2) ->
		{
			if(o1.getDepth() == o2.getDepth())
			{
				return 0;
			}
			
			return o1.getDepth() < o2.getDepth() ? -1 : 1;
		});
		
		return result.toArray(new Camera[result.size()]);
	}
	
	public static CameraInternal getInternalCamera(Camera camera)
	{
		return camera.camera;
	}
}
