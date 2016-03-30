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

package com.snakybo.sengine.input.mouse;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

import org.joml.Vector2f;

import com.snakybo.sengine.window.WindowInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Mouse
{
	private static final int LAST = MouseButton.BUTTON_8.id;
	
	static boolean[] current = new boolean[LAST];
	static boolean[] last = new boolean[LAST];
	
	static Vector2f mousePositionDelta;
	static Vector2f mousePosition;
	
	private Mouse()
	{
		throw new AssertionError();
	}
	
	/**
	 * Set the cursor position
	 * @param position - The new position
	 * @see #setCursorPosition(int, int)
	 */
	public static void setCursorPosition(Vector2f position)
	{
		setCursorPosition((int)position.x, (int)position.y);
	}
	
	/**
	 * Set the cursor position
	 * @param x - The X position
	 * @param y - The Y position
	 * @see #setCursorPosition(Vector2f)
	 */
	public static void setCursorPosition(int x, int y)
	{
		glfwSetCursorPos(WindowInternal.window, x, y);
	}
	
	/**
	 * @return The position of the cursor
	 */
	public static Vector2f getCursorPosition()
	{
		return new Vector2f(mousePosition);
	}
	
	/**
	 * @return The delta of the cursors position
	 */
	public static Vector2f getCursorPositionDelta()
	{
		return new Vector2f(mousePositionDelta);
	}
	
	/**
	 * Returns true if the specified mouse button is currently held down
	 * @param mouseButton - The mouse button
	 * @return True if the mouse button is currently held down
	 * @see MouseButton
	 */
	public static boolean isMouseDown(MouseButton mouseButton)
	{
		return current[mouseButton.id];
	}
	
	/**
	 * Returns true the frame when the specified mouse button is pressed 
	 * @param mouseButton - The mouse button
	 * @return True if the mouse button was pressed in this frame
	 * @see MouseButton
	 */
	public static boolean onMouseDown(MouseButton mouseButton)
	{
		return current[mouseButton.id] && !last[mouseButton.id];
	}
	
	/**
	 * Returns true the frame when the specified mouse button was released
	 * @param mouseButton - The mouse button
	 * @return True if the mouse button was released in this frame
	 * @see MouseButton
	 */
	public static boolean onMouseUp(MouseButton mouseButton)
	{
		return !current[mouseButton.id] && last[mouseButton.id];
	}
}
