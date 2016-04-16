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

package com.snakybo.sengine.input.joystick;

import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;
import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickName;
import static org.lwjgl.glfw.GLFW.glfwJoystickPresent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Joystick
{
	static List<JoystickDevice> devices = new ArrayList<JoystickDevice>();
	
	private Joystick()
	{
		throw new AssertionError();
	}
	
	/**
	 * @return Whether or not there is any joystick present
	 */
	public static boolean isJoystickPresent()
	{
		return isJoystickPresent(GLFW_JOYSTICK_1);
	}
	
	/**
	 * Check if the specified joystick is present
	 * @param joystick The joystick ID to check
	 * @return Whether or not the joystick with the specified ID is present
	 */
	public static boolean isJoystickPresent(int joystick)
	{
		return glfwJoystickPresent(joystick) == GLFW_TRUE ? true : false;
	}
	
	/**
	 * Returns true if the specified button is currently being pressed on any joystick
	 * @param button The joystick button
	 * @return True if the button is currently being pressed
	 */
	public static boolean isButtonDown(int button)
	{
		for(JoystickDevice device : devices)
		{
			if(device.isButtonDown(button))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true if the specified button is currently being pressed on the specified joystick
	 * @param joystick The joystick to poll
	 * @param button The joystick button
	 * @return True if the button is currently being pressed
	 */
	public static boolean isButtonDown(int joystick, int button)
	{
		for(JoystickDevice device : devices)
		{
			if(device.getId() == joystick)
			{
				return device.isButtonDown(button);
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true the frame when the specified button was first pressed on any joystick
	 * @param button The joystick button
	 * @return True if the button was pressed in this frame
	 */
	public static boolean onButtonDown(int button)
	{
		for(JoystickDevice device : devices)
		{
			if(device.onButtonDown(button))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true the frame when the specified button was first pressed on the specified joystick
	 * @param joystick The joystick to poll
	 * @param button The joystick button
	 * @return True if the button was pressed in this frame
	 */
	public static boolean onButtonDown(int joystick, int button)
	{
		for(JoystickDevice device : devices)
		{
			if(device.getId() == joystick)
			{
				return device.onButtonDown(button);
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true the frame when the specified button was released on any joystick
	 * @param button The joystick button
	 * @return True if the button was released in this frame
	 */
	public static boolean onButtonUp(int button)
	{
		for(JoystickDevice device : devices)
		{
			if(device.onButtonUp(button))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true the frame when the specified button was released on the specified joystick
	 * @param joystick The joystick to poll
	 * @param button The joystick button
	 * @return True if the button was released in this frame
	 */
	public static boolean onButtonUp(int joystick, int button)
	{
		for(JoystickDevice device : devices)
		{
			if(device.getId() == joystick)
			{
				return device.onButtonUp(button);
			}
		}
		
		return false;
	}
	
	/**
	 * Get the current value of a joystick axis
	 * @param joystick The joystick to poll
	 * @param axis The axis
	 * @return The current value of the axis
	 */
	public static float getAxis(int joystick, int axis)
	{
		for(JoystickDevice device : devices)
		{
			if(device.getId() == joystick)
			{
				return device.getAxis(axis);
			}
		}
		
		return 0;
	}
	
	/**
	 * @param joystick The joystick to poll
	 * @return The amount of buttons on the joystick
	 */
	public static int getNumButtons(int joystick)
	{
		for(JoystickDevice device : devices)
		{
			if(device.getId() == joystick)
			{
				return device.getNumButtons();
			}
		}
		
		return 0;
	}
	
	/**
	 * @param joystick The joystick to poll
	 * @return The amount of axes on the joystick
	 */
	public static int getNumAxes(int joystick)
	{
		for(JoystickDevice device : devices)
		{
			if(device.getId() == joystick)
			{
				return device.getNumAxes();
			}
		}
		
		return 0;
	}
	
	/**
	 * @return The amount of joysticks or game controllers present
	 */
	public static int getNumJoysticksPresent()
	{
		return getJoysticksPresent().length;
	}
	
	/**
	 * @return The ID's of all present joysticks
	 */
	public static int[] getJoysticksPresent()
	{
		List<Integer> present = new ArrayList<Integer>();

		for(int i = GLFW_JOYSTICK_1; i < GLFW_JOYSTICK_LAST; i++)
		{
			if(Joystick.isJoystickPresent(i))
			{
				present.add(i);
			}
		}
		
		int[] result = new int[present.size()];
		for(int i = 0; i < result.length; i++)
		{
			result[i] = present.get(i);
		}
		
		return result;
	}
	
	/**
	 * @return The names of all present joysticks
	 */
	public static String[] getJoystickNames()
	{
		List<String> result = new ArrayList<String>();
		
		for(int i : getJoysticksPresent())
		{
			result.add(getJoystickName(i));
		}
		
		return result.toArray(new String[result.size()]);
	}
	
	/**
	 * @param joystick The joystick to poll
	 * @return The name of the specified joystick
	 */
	public static String getJoystickName(int joystick)
	{
		return glfwGetJoystickName(joystick);
	}
}
