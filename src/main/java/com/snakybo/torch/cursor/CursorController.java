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

package com.snakybo.torch.cursor;

import com.snakybo.torch.input.keyboard.Key;
import com.snakybo.torch.input.keyboard.Keyboard;
import com.snakybo.torch.input.mouse.Mouse;
import com.snakybo.torch.window.Window;
import org.joml.Vector2f;

/**
 * <p>
 * The controller for the {@link Cursor}, used internally by the engine.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class CursorController
{
	private CursorController()
	{
		throw new AssertionError();
	}
	
	public static void update()
	{
		// If shift+escape is pressed, always unlock the cursor
		if(Keyboard.isDown(Key.LEFT_SHIFT) && Keyboard.onDown(Key.ESCAPE))
		{
			Cursor.setLockMode(CursorLockMode.None);
			Cursor.setVisible(true);
		}
		
		// Handle the cursor's behavior
		switch(Cursor.lockMode)
		{
		case Locked:
			handleLockedCursor();
			break;
		case Confined:
			handleConfinedCursor();
			break;
		default:
			break;
		}
	}
	
	private static void handleLockedCursor()
	{
		Mouse.setCursorPosition(Window.getCenter());
	}
	
	private static void handleConfinedCursor()
	{
		Vector2f cursorPosition = Mouse.getCursorPosition();
		Vector2f windowSize = Window.getSize();
		
		if(cursorPosition.x < 0)
		{
			cursorPosition.x = 0;
		}
		else if(cursorPosition.x > windowSize.x)
		{
			cursorPosition.x = windowSize.x;
		}
		
		if(cursorPosition.y < 0)
		{
			cursorPosition.y = 0;
		}
		else if(cursorPosition.y > windowSize.y)
		{
			cursorPosition.y = windowSize.y;
		}
		
		Mouse.setCursorPosition(cursorPosition);	
	}
}
