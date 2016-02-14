package com.snakybo.sengine.input.mouse;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

import org.joml.Vector2f;

import com.snakybo.sengine.rendering.WindowImplementation;

/**
 * @author Kevin
 * @since Feb 14, 2016
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
		glfwSetCursorPos(WindowImplementation.window, x, y);
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
