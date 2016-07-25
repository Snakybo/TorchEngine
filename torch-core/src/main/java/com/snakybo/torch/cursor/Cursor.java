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

package com.snakybo.torch.cursor;

import com.snakybo.torch.bitmap.Bitmap;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.window.Window;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWImage;

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
import static org.lwjgl.glfw.GLFW.glfwGetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

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
	 * Is the cursor currently visible?
	 * @return Whether or not the cursor is currently visible.
	 */
	public static boolean isVisible()
	{
		return glfwGetInputMode(Window.getNativeId(), GLFW_CURSOR) == GLFW_CURSOR_NORMAL;
	}
	
	/**
	 * Set the lock mode of the cursor.
	 * @param lockMode The new {@link CursorLockMode}.
	 */
	public static void setLockMode(CursorLockMode lockMode)
	{
		Cursor.lockMode = lockMode;
	}
	
	/**
	 * Hide or show the cursor.
	 * @param visible Whether or not the cursor should be visible.
	 */
	public static void setVisible(boolean visible)
	{
		glfwSetInputMode(Window.getNativeId(), GLFW_CURSOR, visible ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);
	}
	
	/**
	 * Set the cursor's shape to the system's arrow cursor.
	 */
	public static void setShapeArrow()
	{
		setShape(glfwCreateStandardCursor(GLFW_ARROW_CURSOR));
	}
	
	/**
	 * Set the cursor's shape to the system's I beam cursor.
	 */
	public static void setShapeIBeam()
	{
		setShape(glfwCreateStandardCursor(GLFW_IBEAM_CURSOR));
	}
	
	/**
	 * Set the cursor's shape to the system's crosshair cursor.
	 */
	public static void setShapeCrosshair()
	{
		setShape(glfwCreateStandardCursor(GLFW_CROSSHAIR_CURSOR));
	}
	
	/**
	 * Set the cursor's shape to the system's hand cursor.
	 */
	public static void setShapeHand()
	{
		setShape(glfwCreateStandardCursor(GLFW_HAND_CURSOR));
	}
	
	/**
	 * Set the cursor's shape to the system's horizontal resize cursor.
	 */
	public static void setShapeHResize()
	{
		setShape(glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR));
	}
	
	/**
	 * Set the cursor's shape to the system's vertical resize cursor.
	 */
	public static void setShapeVResize()
	{
		setShape(glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR));
	}
	
	/**
	 * Set the cursor's shape.
	 * @param bitmap The cursor shape.
	 * @param hot The hot point of the shape.
	 */
	public static void setShape(Bitmap bitmap, Vector2f hot)
	{
		if(CursorShape.hasCursor(bitmap, hot))
		{
			setShape(CursorShape.getCursor(bitmap, hot));
			
			LoggerInternal.log("Loaded existing cursor shape", Cursor.class);
		}
		else
		{
			GLFWImage image = GLFWImage.malloc().set(bitmap.getWidth(), bitmap.getHeight(), bitmap.getByteByffer());
			
			long cursor = glfwCreateCursor(image, Math.round(hot.x), Math.round(hot.y));
			CursorShape.addCursor(bitmap, hot, cursor);
			
			setShape(cursor);
			
			LoggerInternal.log("Created new cursor shape", Cursor.class);
		}
	}
	
	/**
	 * Get the current cursor lock mode.
	 * @return The current cursor lock mode.
	 */
	public static CursorLockMode getLockMode()
	{
		return lockMode;
	}
	
	private static void setShape(long shape)
	{
		glfwSetCursor(Window.getNativeId(), shape);
	}
}
