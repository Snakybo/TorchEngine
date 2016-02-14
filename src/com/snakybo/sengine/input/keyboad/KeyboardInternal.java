package com.snakybo.sengine.input.keyboad;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import com.snakybo.sengine.window.WindowImplementation;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public final class KeyboardInternal
{
	private KeyboardInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Unused
	 */
	public static void create()
	{
	}
	
	/**
	 * Unused
	 */
	public static void destroy()
	{
	}
	
	/**
	 * Update the key states, sets the last key states to the current, and polls GLFW for the current states
	 */
	public static void update()
	{
		for(int i = 0; i < Keyboard.last.length; i++)
		{
			Keyboard.last[i] = Keyboard.current[i];
		}
		
		for(int i = 0; i < Keyboard.current.length; i++)
		{
			int state = glfwGetKey(WindowImplementation.window, i);
			boolean pressed = false;
			
			if(state == GLFW_PRESS || state == GLFW_REPEAT)
			{
				pressed = true;
			}
			
			Keyboard.current[i] = pressed;
		}
	}
}
