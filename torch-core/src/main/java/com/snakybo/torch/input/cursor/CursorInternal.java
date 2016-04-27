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

package com.snakybo.torch.input.cursor;

import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorEnterCallback;

import com.snakybo.torch.input.keyboad.Key;
import com.snakybo.torch.input.keyboad.Keyboard;
import com.snakybo.torch.input.mouse.Mouse;
import com.snakybo.torch.scene.SceneUtilities;
import com.snakybo.torch.window.Window;
import com.snakybo.torch.window.WindowInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public class CursorInternal
{
	private static class CursorEnterCallback extends GLFWCursorEnterCallback
	{
		@Override
		public void invoke(long window, int entered)
		{
			SceneUtilities.notifyGameObjectsCursorEntered(entered == 1 ? CursorEnterMode.Entered : CursorEnterMode.Left);
		}
	}
	
	private static GLFWCursorEnterCallback glfwCursorEnterCallback;
	
	private CursorInternal()
	{
		throw new AssertionError();
	}
	
	public static void create()
	{
		glfwSetCursorEnterCallback(WindowInternal.window, glfwCursorEnterCallback = new CursorEnterCallback());
	}
	
	public static void destroy()
	{
		glfwCursorEnterCallback.release();
	}
	
	public static void update()
	{
		// If shift+escape is pressed, always unlock the cursor
		if(Keyboard.isKeyDown(Key.LEFT_SHIFT) && Keyboard.onKeyDown(Key.ESCAPE))
		{
			Cursor.setLockMode(CursorLockMode.None);
			Cursor.setVisible(true);
		}
		
		// Handle the cursor's behaviour
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
		Vector2f center = Window.getCenter();
		Mouse.setCursorPosition((int)center.x, (int)center.y);
	}
	
	private static void handleConfinedCursor()
	{
		Vector2f cursorPosition = Mouse.getCursorPosition();
		
		if(cursorPosition.x < 0)
		{
			cursorPosition.x = 0;
		}
		else if(cursorPosition.x > Window.getWidth())
		{
			cursorPosition.x = Window.getWidth();
		}
		
		if(cursorPosition.y < 0)
		{
			cursorPosition.y = 0;
		}
		else if(cursorPosition.y > Window.getHeight())
		{
			cursorPosition.y = Window.getHeight();
		}
		
		Mouse.setCursorPosition(cursorPosition);	
	}
}
