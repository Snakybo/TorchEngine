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

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class JoystickInternal
{
	private JoystickInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Update the state of all joysticks
	 */
	public static void update()
	{
		// Check if joysticks have been added or removed
		if(Joystick.getNumJoysticksPresent() != Joystick.devices.size())
		{
			// Check if any joysticks have been removed
			Set<JoystickDevice> toRemove = new HashSet<JoystickDevice>();
			for(JoystickDevice device : Joystick.devices)
			{
				if(!Joystick.isJoystickPresent(device.getId()) ||!device.getName().equals(Joystick.getJoystickName(device.getId())))
				{
					toRemove.add(device);
					continue;
				}
			}
			
			// Remove any removed joysticks
			for(JoystickDevice device : toRemove)
			{
				Joystick.devices.remove(device);
			}
			
			// Check if any joysticks have been added
			Set<Integer> toAdd = new HashSet<Integer>();
			for(int i : Joystick.getJoysticksPresent())
			{
				boolean present = false;
				
				for(JoystickDevice device : Joystick.devices)
				{
					if(device.getId() == i)
					{
						present = true;
					}
				}
				
				if(!present)
				{
					toAdd.add(i);
				}
			}
			
			// Add any added joysticks
			for(Integer i : toAdd)
			{
				Joystick.devices.add(new JoystickDevice(i));
			}
		}
		
		// Update all joysticks
		for(JoystickDevice device : Joystick.devices)
		{
			for(int i = 0; i < device.getNumButtons(); i++)
			{
				device.buttonsLast[i] = device.buttonsCurrent[i];
			}
			
			ByteBuffer buttons = glfwGetJoystickButtons(device.getId());
			for(int i = 0; i < buttons.capacity(); i++)
			{
				device.buttonsCurrent[i] = buttons.get(i) == GLFW_PRESS;
			}
			
			FloatBuffer axes = glfwGetJoystickAxes(device.getId());
			for(int i = 0; i < axes.capacity(); i++)
			{
				device.axes[i] = axes.get(i);
			}
		}
	}
}
