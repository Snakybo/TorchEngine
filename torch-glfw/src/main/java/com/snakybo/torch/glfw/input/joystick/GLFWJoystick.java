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

package com.snakybo.torch.glfw.input.joystick;

import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;
import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_LAST;
import static org.lwjgl.glfw.GLFW.glfwJoystickPresent;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.torch.input.joystick.IJoystick;
import com.snakybo.torch.input.joystick.IJoystickDevice;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWJoystick implements IJoystick
{
	List<GLFWJoystickDevice> devices;
	
	public GLFWJoystick()
	{
		devices = new ArrayList<GLFWJoystickDevice>();
		
		for(int i = GLFW_JOYSTICK_1; i < GLFW_JOYSTICK_LAST; i++)
		{
			if(glfwJoystickPresent(i))
			{
				devices.add(new GLFWJoystickDevice(i));
			}
		}
	}
	
	@Override
	public boolean isJoystickPresent()
	{
		return devices.size() > 0;
	}

	@Override
	public IJoystickDevice getJoystick()
	{
		if(isJoystickPresent())
		{
			return devices.get(0);
		}
		
		return null;
	}

	@Override
	public IJoystickDevice[] getJoysticks()
	{
		List<IJoystickDevice> result = new ArrayList<IJoystickDevice>();
		
		for(IJoystickDevice joystick : devices)
		{
			result.add(joystick);
		}
		
		return result.toArray(new IJoystickDevice[result.size()]);
	}

	@Override
	public int getNumJoysticksPresent()
	{
		return devices.size();
	}
}
