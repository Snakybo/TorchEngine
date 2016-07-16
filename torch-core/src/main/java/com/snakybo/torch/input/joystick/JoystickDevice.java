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
	
	public JoystickDevice(int joystickId)
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
	
	public boolean isDown(Integer id)
	{
		return id >= 0 && id < current.length && current[id];
	}
	
	public boolean isUp(Integer id)
	{
		return id >= 0 && id < current.length && !current[id];
	}
	
	public boolean onDown(Integer id)
	{
		return id >= 0 && id < current.length && current[id] && !last[id];
	}
	
	public boolean onUp(Integer id)
	{
		return id >= 0 && id < current.length && !current[id] && last[id];
	}
	
	public String getName()
	{
		return name;
	}
	
	public float getAxis(int id)
	{
		return id >= 0 && id < axes.length ? axes[id] : 0;
	}
	
	public int getNumButtons()
	{
		return numButtons;
	}
	
	public int getNumAxes()
	{
		return numAxes;
	}
	
	public long getNativeId()
	{
		return nativeId;
	}
}
