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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickName;

/**
 * <p>
 * The {@code JoystickDevice} represents a single joystick or game controller.
 * </p>
 *
 * <p>
 * Input from the joystick is polled once every frame.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class JoystickDevice
{
	float[] axes;
	
	boolean[] current;
	boolean[] last;
	
	private String name;
	
	private int numButtons;
	private int numAxes;
	
	private int nativeId;
	
	JoystickDevice(int joystickId)
	{
		this.nativeId = joystickId;
		
		name = glfwGetJoystickName(joystickId);
		
		ByteBuffer nativeButtons = glfwGetJoystickButtons(joystickId);
		numButtons = nativeButtons.capacity();
		current = new boolean[numButtons];
		last = new boolean[numButtons];
		
		FloatBuffer nativeAxes = glfwGetJoystickAxes(joystickId);
		numAxes = nativeAxes.capacity();
		axes = new float[numAxes];
	}
	
	@Override
	public final String toString()
	{
		return getName();
	}
	
	/**
	 * <p>
	 * Check if the button with the given {@code id} is currently down.
	 * </p>
	 *
	 * @param id The ID of the button.
	 * @return Whether or not the button is currently down.
	 */
	public final boolean isDown(int id)
	{
		return id >= 0 && id < current.length && current[id];
	}
	
	/**
	 * <p>
	 * Check if the button with the given {@code id} is currently up.
	 * </p>
	 *
	 * @param id The ID of the button.
	 * @return Whether or not the button is currently up.
	 */
	public final boolean isUp(int id)
	{
		return id >= 0 && id < current.length && !current[id];
	}
	
	/**
	 * <p>
	 * Returns {@code true} on the first frame the button with the given {@code id} was down.
	 * </p>
	 *
	 * @param id The ID of the button.
	 * @return Whether or not this is the first frame the button is don.
	 */
	public final boolean onDown(int id)
	{
		return id >= 0 && id < current.length && current[id] && !last[id];
	}
	
	/**
	 * <p>
	 * Returns {@code true} on the first frame the button with the given {@code id} was up.
	 * </p>
	 *
	 * @param id The ID of the button.
	 * @return Whether or not this is the first frame the button is up.
	 */
	public final boolean onUp(int id)
	{
		return id >= 0 && id < current.length && !current[id] && last[id];
	}
	
	/**
	 * <p>
	 * Get the name of this device.
	 * </p>
	 *
	 * @return The name of this device.
	 */
	public final String getName()
	{
		return name;
	}
	
	/**
	 * <p>
	 * Get the current value of the axis with the given {@code id}.
	 * </p>
	 *
	 * @param id The ID of the axis.
	 * @return The value of the axis.
	 */
	public final float getAxis(int id)
	{
		return id >= 0 && id < axes.length ? axes[id] : 0;
	}
	
	/**
	 * <p>
	 * Get the number of buttons on the joystick.
	 * </p>
	 *
	 * @return The number of buttons on the joystick.
	 */
	public final int getNumButtons()
	{
		return numButtons;
	}
	
	/**
	 * <p>
	 * Get the number of axes on the joystick.
	 * </p>
	 *
	 * @return The number of axes on the joystick.
	 */
	public final int getNumAxes()
	{
		return numAxes;
	}
	
	public final long getNativeId()
	{
		return nativeId;
	}
}
