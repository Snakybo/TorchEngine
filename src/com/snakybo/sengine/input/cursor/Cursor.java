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

package com.snakybo.sengine.input.cursor;

import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CROSSHAIR_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_IBEAM_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_VRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWImage;

import com.snakybo.sengine.bitmap.Bitmap;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.window.WindowInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Cursor
{	
	static CursorLockMode lockMode = CursorLockMode.None;
	
	private Cursor()
	{
		throw new AssertionError();
	}
	
	/**
	 * Set the {@link CursorLockMode}
	 * @param lockMode - The new lock mode
	 * @see CursorLockMode
	 */
	public static void setLockMode(CursorLockMode lockMode)
	{
		Cursor.lockMode = lockMode;
	}
	
	/**
	 * Set whether or not the cursor should be visible
	 * @param visible - Whether or not the cursor should be visible
	 */
	public static void setVisible(boolean visible)
	{
		glfwSetInputMode(WindowInternal.window, GLFW_CURSOR, visible ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);
	}
	
	/**
	 * Set the cursor to a standard arrow shape
	 */
	public static void setShapeArrow()
	{
		setShape(glfwCreateStandardCursor(GLFW_ARROW_CURSOR));
	}
	
	/**
	 * Set the cursor to a standard IBeam shape
	 */
	public static void setShapeIBeam()
	{
		setShape(glfwCreateStandardCursor(GLFW_IBEAM_CURSOR));
	}
	
	/**
	 * Set the cursor to a standard crosshair shape
	 */
	public static void setShapeCrosshair()
	{
		setShape(glfwCreateStandardCursor(GLFW_CROSSHAIR_CURSOR));
	}
	
	/**
	 * Set the cursor to a standard hand shape
	 */
	public static void setShapeHand()
	{
		setShape(glfwCreateStandardCursor(GLFW_HAND_CURSOR));
	}
	
	/**
	 * Set the cursor to a standard Horizontal Resize shape
	 */
	public static void setShapeHResize()
	{
		setShape(glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR));
	}
	
	/**
	 * Set the cursor to a standard Vertical Resize shape
	 */
	public static void setShapeVResize()
	{
		setShape(glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR));
	}
	
	/**
	 * Set a custom cursor shape
	 * @param bitmap - The bitmap to use as cursor
	 */
	public static void setShape(Bitmap bitmap)
	{
		setShape(bitmap, new Vector2f(0, 0));
	}
	
	/**
	 * Set a custom cursor shape
	 * @param bitmap - The bitmap to use as cursor
	 * @param hot - The position that actually clicks
	 */
	public static void setShape(Bitmap bitmap, Vector2f hot)
	{
		if(CursorShape.hasCursor(bitmap, hot))
		{
			setShape(CursorShape.getCursor(bitmap, hot));
			
			LoggerInternal.log("Loaded existing cursor shape", "Cursor");
		}
		else
		{
			GLFWImage image = GLFWImage.malloc().set(bitmap.getWidth(), bitmap.getHeight(), bitmap.getByteByffer());
			
			long cursor = glfwCreateCursor(image, Math.round(hot.x), Math.round(hot.y));
			CursorShape.addCursor(bitmap, hot, cursor);
			
			setShape(cursor);
			
			LoggerInternal.log("Created new cursor shape", "Cursor");
		}
	}
	
	/**
	 * Set the shape of the cursor
	 * @param shape - The new shape of the cursor
	 */
	private static void setShape(long shape)
	{
		glfwSetCursor(WindowInternal.window, shape);
	}
}
