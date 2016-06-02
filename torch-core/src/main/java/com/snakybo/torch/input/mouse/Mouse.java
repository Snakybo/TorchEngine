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

import org.joml.Vector2f;

import com.snakybo.torch.module.Module;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Mouse
{
	private Mouse()
	{
		throw new AssertionError();
	}
	
	/**
	 * Check if the {@link MouseButton} is currently being pressed.
	 * <p>
	 * Shortcut to {@code Module.getMouse().isDown(MouseButton)}
	 * </p>
	 * @param id The {@link MouseButton} to check.
	 * @return True if the specified {@link MouseButton} is currently being pressed.
	 * @see IMouse#isDown(MouseButton)
	 */
	public static boolean isDown(MouseButton id)
	{
		return Module.getInstance().getMouse().isDown(id);
	}
	
	/**
	 * Check if the {@link MouseButton} is currently not being pressed.
	 * <p>
	 * Shortcut to {@code Module.getMouse().isUp(MouseButton)}
	 * </p>
	 * @param id The {@link MouseButton} to check.
	 * @return True if the specified {@link MouseButton} is currently not being pressed.
	 * @see IMouse#isUp(MouseButton)
	 */
	public static boolean isUp(MouseButton id)
	{
		return Module.getInstance().getMouse().isUp(id);
	}
	
	/**
	 * Check if the {@link MouseButton} is currently being pressed.
	 * <p>
	 * Shortcut to {@code Module.getMouse().onDown(MouseButton)}
	 * </p>
	 * @param id The {@link MouseButton} to check.
	 * @return True the first frame {@link MouseButton} is being pressed.
	 * @see IMouse#onDown(MouseButton)
	 */
	public static boolean onDown(MouseButton id)
	{
		return Module.getInstance().getMouse().onDown(id);
	}
	
	/**
	 * Check if the {@link MouseButton} is currently not being pressed.
	 * <p>
	 * Shortcut to {@code Module.getMouse().onUp(MouseButton)}
	 * </p>
	 * @param id The {@link MouseButton} to check.
	 * @return True the first frame {@link MouseButton} has been released.
	 * @see IMouse#onUp(MouseButton)
	 */
	public static boolean onUp(MouseButton id)
	{
		return Module.getInstance().getMouse().onUp(id);
	}
	
	/**
	 * Set the cursor position.
	 * <p>
	 * Shortcut to {@code Module.getMouse().setCursorPosition(Vector2f)}
	 * </p>
	 * @param position The new position of the cursor.
	 * @see IMouse#setCursorPosition(Vector2f)
	 */
	public static void setCursorPosition(Vector2f position)
	{
		Module.getInstance().getMouse().setCursorPosition(position);
	}
	
	/**
	 * Get the mouse scroll wheel delta.
	 * <p>
	 * Shortcut to {@code Module.getMouse().getScrollDelta()}
	 * </p>
	 * @return The mouse scroll wheel delta.
	 * @see IMouse#getScrollDelta()
	 */
	public static Vector2f getScrollDelta()
	{
		return Module.getInstance().getMouse().getScrollDelta();
	}
	
	/**
	 * Get the cursor position.
	 * <p>
	 * Shortcut to {@code Module.getMouse().getCursorPosition()}
	 * </p>
	 * @return The cursor position.
	 * @see IMouse#getCursorPosition()
	 */
	public static Vector2f getCursorPosition()
	{
		return Module.getInstance().getMouse().getCursorPosition();
	}
	
	/**
	 * Get the cursor position delta.
	 * <p>
	 * Shortcut to {@code Module.getMouse().getCursorPositionDelta()}
	 * </p>
	 * @return The cursor position delta.
	 * @see IMouse#getCursorPositionDelta()
	 */
	public static Vector2f getCursorPositionDelta()
	{
		return Module.getInstance().getMouse().getCursorPositionDelta();
	}
}
