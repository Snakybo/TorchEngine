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

package com.snakybo.torch.input.joystick;

import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * @author Snakybo
 * @since 1.0
 */
final class JoystickDevice
{
	float[] axes;
	
	boolean[] buttonsCurrent;
	boolean[] buttonsLast;
	
	private String name;
	
	private int numButtons;
	private int numAxes;
	
	private int id;
	
	public JoystickDevice(int joystick)
	{
		if(!Joystick.isJoystickPresent(joystick))
		{
			throw new IllegalArgumentException("Joystick with ID: " + joystick + " is not connected");
		}
		
		this.name = Joystick.getJoystickName(joystick);
		this.id = joystick;
		
		ByteBuffer buttons = glfwGetJoystickButtons(joystick);
		this.numButtons = buttons.capacity();
		
		FloatBuffer axes = glfwGetJoystickAxes(joystick);
		this.numAxes = axes.capacity();
		
		this.axes = new float[numAxes];
		
		this.buttonsCurrent = new boolean[numButtons];
		this.buttonsLast = new boolean[numButtons];
	}
	
	/**
	 * Returns true if the specified button is currently being pressed
	 * @param button The joystick button
	 * @return True if the button is currently being pressed
	 */
	public final boolean isButtonDown(int button)
	{
		if(button >= 0 && button < buttonsCurrent.length)
		{
			return buttonsCurrent[button];
		}
		
		return false;
	}
	
	/**
	 * Returns true the frame when the specified button was first pressed
	 * @param button The joystick button
	 * @return True if the button was pressed in this frame
	 */
	public final boolean onButtonDown(int button)
	{
		if(button >= 0 && button < buttonsCurrent.length)
		{
			return buttonsCurrent[button] && !buttonsLast[button];
		}
		
		return false;
	}
	
	/**
	 * Returns true the frame when the specified button was released
	 * @param button The joystick button
	 * @return True if the button was released in this frame
	 */
	public final boolean onButtonUp(int button)
	{
		if(button >= 0 && button < buttonsCurrent.length)
		{
			return !buttonsCurrent[button] && buttonsLast[button];
		}
		
		return false;
	}
	
	/**
	 * @return The name of the joystick
	 */
	public final String getName()
	{
		return name;
	}
	
	/**
	 * Get the current value of an axis
	 * @param axis The axis
	 * @return The current value of the axis
	 */
	public final float getAxis(int axis)
	{
		if(axis >= 0 && axis < buttonsCurrent.length)
		{
			return axes[axis];
		}
		
		return 0;
	}
	
	/**
	 * @return The number of buttons on the joystick
	 */
	public final int getNumButtons()
	{
		return numButtons;
	}
	
	/**
	 * @return The number of axes on the joystick
	 */
	public final int getNumAxes()
	{
		return numAxes;
	}
	
	/**
	 * @return The GLFW ID of the joystick
	 */
	public final int getId()
	{
		return id;
	}
}
