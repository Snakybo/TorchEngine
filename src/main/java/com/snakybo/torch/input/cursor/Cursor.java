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

package com.snakybo.torch.input.cursor;

import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.graphics.texture.TextureInternal;
import com.snakybo.torch.graphics.window.Window;
import com.snakybo.torch.graphics.window.WindowInternal;
import com.snakybo.torch.input.mouse.Mouse;
import com.snakybo.torch.util.debug.LoggerInternal;
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
 * <p>
 * Proxy for the mouse cursor, allows you to manage various aspects of the cursor object.
 * </p>
 *
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
	 * <p>
	 * Check whether or not the cursor is currently visible.
	 * </p>
	 *
	 * @return Whether or not the cursor is currently visible.
	 */
	public static boolean isVisible()
	{
		return glfwGetInputMode(WindowInternal.getNativeId(), GLFW_CURSOR) == GLFW_CURSOR_NORMAL;
	}
	
	/**
	 * <p>
	 * Set the lock mode of the cursor.
	 * </p>
	 *
	 * @param lockMode The new {@link CursorLockMode}.
	 */
	public static void setLockMode(CursorLockMode lockMode)
	{
		Cursor.lockMode = lockMode;
		
		switch(lockMode)
		{
		case Locked:
			Mouse.setCursorPosition(Window.getCenter());
			break;
		}
	}
	
	/**
	 * <p>
	 * Set whether or not the cursor is currently visible.
	 * </p>
	 *
	 * @param visible Whether or not the cursor should be visible.
	 */
	public static void setVisible(boolean visible)
	{
		glfwSetInputMode(WindowInternal.getNativeId(), GLFW_CURSOR, visible ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);
	}
	
	/**
	 * <p>
	 * Set the cursor's shape to the operating system's arrow cursor.
	 * </p>
	 */
	public static void setShapeArrow()
	{
		setShape(glfwCreateStandardCursor(GLFW_ARROW_CURSOR));
	}
	
	/**
	 * <p>
	 * Set the cursor's shape to the operating system's I-Beam cursor.
	 * </p>
	 */
	public static void setShapeIBeam()
	{
		setShape(glfwCreateStandardCursor(GLFW_IBEAM_CURSOR));
	}
	
	/**
	 * <p>
	 * Set the cursor's shape to the operating system's crosshair cursor.
	 * </p>
	 */
	public static void setShapeCrosshair()
	{
		setShape(glfwCreateStandardCursor(GLFW_CROSSHAIR_CURSOR));
	}
	
	/**
	 * <p>
	 * Set the cursor's shape to the operating system's hand cursor.
	 * </p>
	 */
	public static void setShapeHand()
	{
		setShape(glfwCreateStandardCursor(GLFW_HAND_CURSOR));
	}
	
	/**
	 * <p>
	 * Set the cursor's shape to the operating system's horizontal resize cursor.
	 * </p>
	 */
	public static void setShapeHResize()
	{
		setShape(glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR));
	}
	
	/**
	 * <p>
	 * Set the cursor's shape to the operating system's vertical resize cursor.
	 * </p>
	 */
	public static void setShapeVResize()
	{
		setShape(glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR));
	}
	
	/**
	 * <p>
	 * Set the shape of the cursor to a custom image.
	 * </p>
	 *
	 * @param texture The cursor shape.
	 * @param hot The hot point of the shape.
	 */
	public static void setShape(Texture texture, Vector2f hot)
	{
		if(CursorShape.hasCursor(texture, hot))
		{
			setShape(CursorShape.getCursor(texture, hot));
			LoggerInternal.log("Loaded existing cursor shape");
		}
		else
		{
			GLFWImage image = GLFWImage.malloc().set(texture.getWidth(), texture.getHeight(), TextureInternal.getByteBuffer(texture));
			
			long cursor = glfwCreateCursor(image, Math.round(hot.x), Math.round(hot.y));
			CursorShape.addCursor(texture, hot, cursor);
			
			setShape(cursor);
			LoggerInternal.log("Created new cursor shape");
		}
	}
	
	/**
	 * <p>
	 * Get the current cursor lock mode.
	 * </p>
	 *
	 * @return The current cursor lock mode.
	 */
	public static CursorLockMode getLockMode()
	{
		return lockMode;
	}
	
	private static void setShape(long shape)
	{
		glfwSetCursor(WindowInternal.getNativeId(), shape);
	}
}
