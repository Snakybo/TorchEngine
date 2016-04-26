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

package input;

import com.snakybo.torch.TorchGame;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.input.joystick.Joystick;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.object.GameObject;

/**
 * @author Snakybo
 * @since 1.0
 */
public class JoystickExample extends TorchGame
{
	public JoystickExample()
	{
		super("Joystick Test");
	}
	
	@Override
	protected void onCreate()
	{
		GameObject joystickManager = new GameObject();
		joystickManager.addComponent(new Component()
		{
			@Override
			protected void update()
			{
				Logger.log("=========================");
				Logger.log("Num joysticks present: " + Joystick.getNumJoysticksPresent());
				
				for(int i : Joystick.getJoysticksPresent())
				{
					Logger.log(" Name: " + Joystick.getJoystickName(i));
					Logger.log("   Num buttons: " + Joystick.getNumButtons(i));
					
					for(int j = 0; j < Joystick.getNumButtons(i); j++)
					{
						if(Joystick.onButtonDown(i, j))
						{
							Logger.log("    onButtonDown: " + j);
						}
						
						if(Joystick.isButtonDown(i, j))
						{
							Logger.log("    isButtonDown: " + j);
						}
						
						if(Joystick.onButtonUp(i, j))
						{
							Logger.log("    onButtonUp: " + j);
						}
					}
					
					Logger.log("   Num axes: " + Joystick.getNumAxes(i));
					for(int j = 0; j < Joystick.getNumAxes(i); j++)
					{
						Logger.log("    Axis " + j + ": " + Joystick.getAxis(i, j));
					}
				}
			}
		});
	}
}
