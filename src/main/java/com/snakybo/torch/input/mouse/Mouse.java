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

package com.snakybo.torch.input.mouse;

import com.snakybo.torch.window.WindowInternal;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Mouse
{
	static final int LAST = MouseButton.BUTTON_8.id;
	
	static boolean[] current;
	static boolean[] last;
	
	static Vector2f mousePositionDelta;
	static Vector2f mousePosition;
	
	static Vector2f scrollDelta;
	
	private Mouse()
	{
		throw new AssertionError();
	}
	
	/**
	 * Check if the {@link MouseButton} is currently being pressed.
	 * @param id The {@link MouseButton} to check.
	 * @return True if the specified {@link MouseButton} is currently being pressed.
	 */
	public static boolean isDown(MouseButton id)
	{
		return current[id.id];
	}
	
	/**
	 * Check if the {@link MouseButton} is currently not being pressed.
	 * @param id The {@link MouseButton} to check.
	 * @return True if the specified {@link MouseButton} is currently not being pressed.
	 */
	public static boolean isUp(MouseButton id)
	{
		return !current[id.id];
	}
	
	/**
	 * Check if the {@link MouseButton} is currently being pressed.
	 * @param id The {@link MouseButton} to check.
	 * @return True the first frame {@link MouseButton} is being pressed.
	 */
	public static boolean onDown(MouseButton id)
	{
		return current[id.id] && !last[id.id];
	}
	
	/**
	 * Check if the {@link MouseButton} is currently not being pressed.
	 * @param id The {@link MouseButton} to check.
	 * @return True the first frame {@link MouseButton} has been released.
	 */
	public static boolean onUp(MouseButton id)
	{
		return !current[id.id] && last[id.id];
	}
	
	/**
	 * Set the cursor position.
	 * @param position The new position of the cursor.
	 */
	public static void setCursorPosition(Vector2f position)
	{
		glfwSetCursorPos(WindowInternal.getNativeId(), (int)position.x, (int)position.y);
		
		// Set the mouse position directly to prevent delta calculations
		mousePosition = position;
	}
	
	/**
	 * Set the scroll wheel delta.
	 * @param scrollDelta The new scroll wheel delta.
	 */
	public static void setScrollDelta(Vector2f scrollDelta)
	{
		Mouse.scrollDelta = scrollDelta;
	}
	
	/**
	 * Get the mouse scroll wheel delta.
	 * @return The mouse scroll wheel delta.
	 */
	public static Vector2f getScrollDelta()
	{
		return scrollDelta;
	}
	
	/**
	 * Get the cursor position.
	 * @return The cursor position.
	 */
	public static Vector2f getCursorPosition()
	{
		return new Vector2f(mousePosition);
	}
	
	/**
	 * Get the cursor position delta.
	 * @return The cursor position delta.
	 */
	public static Vector2f getCursorPositionDelta()
	{
		return new Vector2f(mousePositionDelta);
	}
}
