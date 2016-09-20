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

package com.snakybo.torch.graphics.gizmo;

import com.snakybo.torch.graphics.camera.CameraInternal;
import com.snakybo.torch.graphics.material.Material;
import com.snakybo.torch.graphics.material.MaterialInternal;
import com.snakybo.torch.graphics.shader.ShaderInternal;
import com.snakybo.torch.util.color.Color;
import com.snakybo.torch.util.debug.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glPolygonMode;

/**
 * <p>
 * Used for drawing gizmos in the game view.
 * </p>
 *
 * <p>
 * Gizmos do not have a depth test, and are not affected by lighting effects.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Gizmos
{
	private static Material material;
	
	private static Color color;
	
	static
	{
		material = new Material("torch_internal/gizmos.glsl");
		
		setColor(Color.WHITE);
	}
	
	private Gizmos()
	{
		throw new AssertionError();
	}
	
	/**
	 * <p>
	 * Reset all gizmo properties.
	 * </p>
	 *
	 * <p>
	 * This is automatically called before {@code Component#onRenderGizmos()} is called,
	 * but you can also call it manually in your {@code onRenderGizmos} method to reset everything.
	 * </p>
	 */
	public static void reset()
	{
		setColor(Color.WHITE);
	}
	
	/**
	 * <p>
	 * Draw a solid cube at the target {@code position}.
	 * </p>
	 *
	 * @param position The position to draw the cube at.
	 * @param size The size of the cube.
	 */
	public static void drawCube(Vector3f position, Vector3f size)
	{
		if(!canRender())
		{
			return;
		}
		
		ShaderInternal.bind(material.getShader());
		
		updateMatrix(position, size);
		GizmoShapeCube.render(GL_TRIANGLES);
		
		ShaderInternal.unbind();
	}
	
	/**
	 * <p>
	 * Draw the wireframe of a cube at the target {@code position}.
	 * </p>
	 *
	 * @param position The position to draw the cube at.
	 * @param size The size of the cube.
	 */
	public static void drawCubeWireframe(Vector3f position, Vector3f size)
	{
		if(!canRender())
		{
			return;
		}
		
		ShaderInternal.bind(material.getShader());
		
		updateMatrix(position, size);
		GizmoShapeCubeWireframe.render(GL_LINES);
		
		ShaderInternal.unbind();
	}
	
	/**
	 * <p>
	 * Draw a solid sphere at the target {@code position}.
	 * </p>
	 *
	 * @param position The position to draw the sphere at.
	 * @param radius The radius of the sphere.
	 */
	public static void drawSphere(Vector3f position, float radius)
	{
		if(!canRender())
		{
			return;
		}
		
		ShaderInternal.bind(material.getShader());
		
		updateMatrix(position, new Vector3f(radius));
		GizmoShapeSphere.render(GL_QUADS);
		
		ShaderInternal.unbind();
	}
	
	/**
	 * <p>
	 * Draw a wireframe of a sphere at the target {@code position}.
	 * </p>
	 *
	 * @param position The position to draw the sphere at.
	 * @param radius The radius of the sphere.
	 */
	public static void drawSphereWireframe(Vector3f position, float radius)
	{
		if(!canRender())
		{
			return;
		}
		
		ShaderInternal.bind(material.getShader());
		
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		
		updateMatrix(position, new Vector3f(radius));
		GizmoShapeSphere.render(GL_QUADS);
		
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		
		ShaderInternal.unbind();
	}
	
	private static void updateMatrix(Vector3f position, Vector3f size)
	{
		material.setVector3f("color", new Vector3f(color.getRed(), color.getGreen(), color.getBlue()));
		
		MaterialInternal.updateBuiltInUniforms(material, CameraInternal.getMainCamera(), new Matrix4f().translate(position).scale(size));
		MaterialInternal.update(material);
	}
	
	private static boolean canRender()
	{
		if(!GizmosInternal.isInGizmoRenderPass)
		{
			Logger.logWarning("Gizmos can only be rendered from Component.onRenderGizmos()");
			return false;
		}
		
		return true;
	}
	
	/**
	 * <p>
	 * Set the color for the gizmos.
	 * </p>
	 *
	 * <p>
	 * All gizmos drawn after setting the color will be drawn with that color.
	 * </p>
	 *
	 * @param color The new color for the gizmos.
	 */
	public static void setColor(Color color)
	{
		color = color != null ? color : Color.WHITE;
		Gizmos.color = color;
	}
	
	/**
	 * <p>
	 * Get the color the gizmos are rendered with.
	 * </p>
	 *
	 * @return The color of the gizmos.
	 */
	public static Color getColor()
	{
		return color;
	}
}
