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

package com.snakybo.torch.input.mouse;

/**
 * @author Snakybo
 * @since 1.0
 */
public enum MouseButton
{
	/** Mouse button. */
	BUTTON_1      (0x0),
	
	/** Mouse button. */
	BUTTON_2      (0x1),
	
	/** Mouse button. */
	BUTTON_3      (0x2),
	
	/** Mouse button. */
	BUTTON_4      (0x3),
	
	/** Mouse button. */
	BUTTON_5      (0x4),
	
	/** Mouse button. */
	BUTTON_6      (0x5),
	
	/** Mouse button. */
	BUTTON_7      (0x6),
	
	/** Mouse button. */
	BUTTON_8      (0x7),
	
	/** The same as {@link #BUTTON_1} */
	LEFT          (BUTTON_1),
	
	/** The same as {@link #BUTTON_2} */
	RIGHT         (BUTTON_2),
	
	/** The same as {@link #BUTTON_3} */
	MIDDLE        (BUTTON_3);
	
	public final int id;
	
	MouseButton(MouseButton mouseButton)
	{
		this(mouseButton.id);
	}
	
	MouseButton(int id)
	{
		this.id = id;
	}
	
	@Override
	public final String toString()
	{
		switch(this)
		{
		case BUTTON_1:
		case LEFT:
			return "Mouse Button 1";
		case BUTTON_2:
		case RIGHT:
			return "Mouse Button 2";
		case BUTTON_3:
		case MIDDLE:
			return "Mouse Button 3";
		case BUTTON_4:
			return "Mouse Button 4";
		case BUTTON_5:
			return "Mouse Button 5";
		case BUTTON_6:
			return "Mouse Button 6";
		case BUTTON_7:
			return "Mouse Button 7";
		case BUTTON_8:
			return "Mouse Button 8";
		default:
			return "Unknown";
		}
	}
}
