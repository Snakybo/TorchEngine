package com.snakybo.sengine.input.cursor;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

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
}
