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

package com.snakybo.torch.glfw.cursor;

import org.joml.Vector2f;

import com.snakybo.torch.cursor.CursorLockMode;
import com.snakybo.torch.cursor.ICursor;
import com.snakybo.torch.cursor.ICursorController;
import com.snakybo.torch.input.keyboard.Key;
import com.snakybo.torch.input.keyboard.Keyboard;
import com.snakybo.torch.input.mouse.Mouse;
import com.snakybo.torch.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWCursorController implements ICursorController
{
	private final GLFWCursor cursor;
	
	public GLFWCursorController()
	{
		cursor = new GLFWCursor();
	}
	
	@Override
	public void create()
	{	
	}

	@Override
	public final void update()
	{
		// If shift+escape is pressed, always unlock the cursor
		if(Keyboard.isDown(Key.LEFT_SHIFT) && Keyboard.onDown(Key.ESCAPE))
		{
			cursor.setLockMode(CursorLockMode.None);
			cursor.setVisible(true);
		}
		
		// Handle the cursor's behavior
		switch(cursor.lockMode)
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

	@Override
	public final void destroy()
	{
	}

	@Override
	public final ICursor get()
	{
		return cursor;
	}	
	
	private final void handleLockedCursor()
	{
		Mouse.setCursorPosition(Window.getCenter());
	}
	
	private final void handleConfinedCursor()
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
