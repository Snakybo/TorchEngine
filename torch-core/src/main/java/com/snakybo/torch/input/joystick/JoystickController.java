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

import org.lwjgl.glfw.GLFWJoystickCallback;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_CONNECTED;
import static org.lwjgl.glfw.GLFW.GLFW_DISCONNECTED;
import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;
import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;
import static org.lwjgl.glfw.GLFW.glfwJoystickPresent;
import static org.lwjgl.glfw.GLFW.glfwSetJoystickCallback;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class JoystickController
{
	private static class JoystickCallback extends GLFWJoystickCallback
	{
		@Override
		public void invoke(int joystickId, int event)
		{
			if(event == GLFW_CONNECTED)
			{
				for(JoystickDevice device : Joystick.devices)
				{
					if((int)device.getNativeId() == joystickId)
					{
						throw new RuntimeException("Unable to add joystick, another joystick with ID " + joystickId + " is already connected.");
					}
				}
				
				Joystick.devices.add(new JoystickDevice(joystickId));
			}
			else if(event == GLFW_DISCONNECTED)
			{
				JoystickDevice target = null;
				
				for(JoystickDevice device : Joystick.devices)
				{
					if((int)device.getNativeId() == joystickId)
					{
						target = device;
						break;
					}
				}
				
				if(target == null)
				{
					throw new RuntimeException("Unable to remove joystick, no joystick with ID " + joystickId + " is present.");
				}
				
				Joystick.devices.remove(target);
			}
		}
	}
	
	private JoystickController()
	{
		throw new AssertionError();
	}
	
	public static void create()
	{
		Joystick.devices = new ArrayList<>();
		
		for(int i = GLFW_JOYSTICK_1; i < GLFW_JOYSTICK_LAST; i++)
		{
			if(glfwJoystickPresent(i))
			{
				Joystick.devices.add(new JoystickDevice(i));
			}
		}
		
		glfwSetJoystickCallback(new JoystickCallback());
	}
	
	public static void update()
	{
		for(JoystickDevice device : Joystick.devices)
		{
			System.arraycopy(device.current, 0, device.last, 0, device.getNumButtons());
			
			ByteBuffer buttons = glfwGetJoystickButtons((int)device.getNativeId());
			for(int i = 0; i < buttons.capacity(); i++)
			{
				device.current[i] = buttons.get(i) == GLFW_PRESS;
			}
			
			FloatBuffer axes = glfwGetJoystickAxes((int)device.getNativeId());
			for(int i = 0; i < axes.capacity(); i++)
			{
				device.axes[i] = axes.get(i);
			}
		}
	}
	
	public static void destroy()
	{
		glfwSetJoystickCallback(null).free();
	}
}
