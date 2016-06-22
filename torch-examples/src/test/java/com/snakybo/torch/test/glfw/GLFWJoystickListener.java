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

package com.snakybo.torch.test.glfw;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.input.joystick.IJoystickDevice;
import com.snakybo.torch.input.joystick.Joystick;
import com.snakybo.torch.object.Component;

/**
 * @author Snakybo
 * @since 1.0
 */
public class GLFWJoystickListener extends Component
{
	@Override
	protected void start()
	{
		Logger.log("Joystick Present: " + Joystick.isJoystickPresent());
		
		if(Joystick.isJoystickPresent())
		{
			Logger.log("Number of joysticks present: " + Joystick.getNumJoysticksPresent());
			
			for(IJoystickDevice device : Joystick.getJoysticks())
			{
				Logger.log("Joystick Name: " + device.getName());
				Logger.log("    Number of buttons: " + device.getNumButtons());
				Logger.log("    Number of axes: " + device.getNumAxes());
			}
		}
	}
	
	@Override
	protected void update()
	{
		if(Joystick.isJoystickPresent())
		{
			IJoystickDevice device = Joystick.getJoystick();
			
			for(int i = 0; i < device.getNumButtons(); i++)
			{
				if(device.onDown(i))
				{
					Logger.log("Pressed button: " + i);
				}
				
				if(device.onUp(i))
				{
					Logger.log("Released button: " + i);
				}
			}
			
			for(int i = 0; i < device.getNumAxes(); i++)
			{
				float axis = device.getAxis(i);
				
				if(axis >= 0.1f || axis <= -0.1f)
				{
					Logger.log("Axis " + i + ": " + axis);	
				}
			}
		}
	}
}
