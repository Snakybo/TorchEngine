package com.snakybo.sengine.input.mouse;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import com.snakybo.sengine.window.WindowImplementation;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public final class MouseInternal
{	
	private MouseInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Initialize the mouse position and delta
	 */
	public static void create()
	{
		Mouse.mousePositionDelta = new Vector2f();
		Mouse.mousePosition = getCursorPosition();
	}
	
	/**
	 * Unused
	 */
	public static void destroy()
	{
	}
	
	/**
	 * Update the mouse button states, sets the last mouse button states to the current, and polls GLFW for the current states.
	 * Also updates the cursor's position and delta
	 */
	public static void update()
	{
		for(int i = 0; i < Mouse.last.length; i++)
		{
			Mouse.last[i] = Mouse.current[i];
		}
		
		for(int i = 0; i < Mouse.current.length; i++)
		{
			int state = glfwGetMouseButton(WindowImplementation.window, i);
			boolean pressed = false;
			
			if(state == GLFW_PRESS || state == GLFW_REPEAT)
			{
				pressed = true;
			}
			
			Mouse.current[i] = pressed;
		}
		
		updateCursorPosition();
	}
	
	/**
	 * Update the cursor position and calculate the delta
	 */
	private static void updateCursorPosition()
	{
		Vector2f pos = getCursorPosition();
		
		float xDelta = pos.x - Mouse.mousePosition.x;
		float yDelta = pos.y - Mouse.mousePosition.y;
		
		Mouse.mousePosition = pos;
		Mouse.mousePositionDelta = new Vector2f(xDelta, yDelta);
	}
	
	/**
	 * Polls GLFW for the current cursor position
	 * @return The current cursor position
	 */
	private static Vector2f getCursorPosition()
	{
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(WindowImplementation.window, xBuffer, yBuffer);
		
		return new Vector2f((float)xBuffer.get(), (float)yBuffer.get());
	}
}
