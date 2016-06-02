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

import com.snakybo.torch.module.WindowModule;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Joystick
{
	private Joystick()
	{
		throw new AssertionError();
	}
	
	/**
	 * Check whether or not a joystick is present.
	 * <p>
	 * Shortcut to {@code Module.getInstance().getJoystick().isJoystickPresent()}
	 * </p>
	 * @return Whether or not a joystick is present.
	 * @see IJoystick#isJoystickPresent()
	 */
	public static boolean isJoystickPresent()
	{
		return WindowModule.getInstance().getJoystick().isJoystickPresent();
	}
	
	/**
	 * Get the first available joystick.
	 * <p>
	 * Shortcut to {@code Module.getInstance().getJoystick().getJoystick()}
	 * </p>
	 * @return The first available joystick.
	 * @see IJoystick#getJoystick()
	 */
	public static IJoystickDevice getJoystick()
	{
		return WindowModule.getInstance().getJoystick().getJoystick();
	}
	
	/**
	 * Get all available joysticks.
	 * <p>
	 * Shortcut to {@code Module.getInstance().getJoystick().getJoysticks()}
	 * </p>
	 * @return All available joysticks.
	 * @see IJoystick#getJoysticks()
	 */
	public static IJoystickDevice[] getJoysticks()
	{
		return WindowModule.getInstance().getJoystick().getJoysticks();
	}
	
	/**
	 * Get the number of joysticks present.
	 * <p>
	 * Shortcut to {@code Module.getInstance().getJoystick().getNumJoysticksPresent()}
	 * </p>
	 * @return The number of joysticks present.
	 * @see IJoystick#getNumJoysticksPresent()
	 */
	public static int getNumJoysticksPresent()
	{
		return WindowModule.getInstance().getJoystick().getNumJoysticksPresent();
	}
}
