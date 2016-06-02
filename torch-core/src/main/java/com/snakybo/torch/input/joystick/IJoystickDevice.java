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

import com.snakybo.torch.input.IInput;

/**
 * @author Snakybo
 * @since 1.0
 */
public interface IJoystickDevice extends IInput<Integer>
{
	/**
	 * Get the name of this joystick.
	 * @return The name of this joystick.
	 */
	String getName();
	
	/**
	 * Get the value of an axis.
	 * @param id The id of the axis.
	 * @return The value of the axis.
	 */
	float getAxis(int id);
	
	/**
	 * Get the number of buttons on the joystick.
	 * @return The number of buttons on the joystick.
	 */
	int getNumButtons();
	
	/**
	 * Get the number of axes on the joystick.
	 * @return The number of axis on the joystick.
	 */
	int getNumAxes();
	
	/**
	 * Get the native ID of the joystick.
	 * @return The native Id of the joystick.
	 */
	long getNativeId();
}
