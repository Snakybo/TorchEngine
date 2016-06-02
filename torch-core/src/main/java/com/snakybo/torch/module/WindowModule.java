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

package com.snakybo.torch.module;

import com.snakybo.torch.cursor.ICursor;
import com.snakybo.torch.cursor.ICursorController;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.input.IInputController;
import com.snakybo.torch.input.joystick.IJoystick;
import com.snakybo.torch.input.keyboard.IKeyboard;
import com.snakybo.torch.input.mouse.IMouse;
import com.snakybo.torch.window.IWindow;
import com.snakybo.torch.window.IWindowController;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class WindowModule extends Module
{
	private static WindowModule instance;
	
	protected IInputController<IKeyboard> keyboard;
	protected IInputController<IMouse> mouse;
	protected IInputController<IJoystick> joystick;
	
	protected IWindowController window;
	
	protected ICursorController cursor;
	
	protected WindowModule()
	{
		if(instance != null)
		{
			throw new RuntimeException("There can't be more as one WindowModule");
		}
		
		LoggerInternal.log("Created WindowModule", this);
		
		instance = this;
	}
	
	@Override
	public void create()
	{
		keyboard.create();
		mouse.create();
		joystick.create();
		window.create();
		cursor.create();
	}
	
	@Override
	public void destroy()
	{
		if(instance != this)
		{
			throw new RuntimeException("Error while trying to destroy module");
		}
		
		keyboard.destroy();
		mouse.destroy();
		joystick.destroy();
		window.destroy();
		cursor.destroy();
		
		instance = null;
	}
	
	/**
	 * Get the keyboard controller.
	 * @return The keyboard controller.
	 */
	public final IInputController<IKeyboard> getKeyboardController()
	{
		return keyboard;
	}
	
	/**
	 * Get the mouse controller.
	 * @return The mouse controller.
	 */
	public final IInputController<IMouse> getMouseController()
	{
		return mouse;
	}
	
	/**
	 * Get the joystick controller.
	 * @return The joystick controller.
	 */
	public final IInputController<IJoystick> getJoystickController()
	{
		return joystick;
	}
	
	/**
	 * Get the window controller.
	 * @return The window controller.
	 */
	public final IWindowController getWindowController()
	{
		return window;
	}
	
	/**
	 * Get the cursor controller.
	 * @return The cursor controller.
	 */
	public final ICursorController getCursorController()
	{
		return cursor;
	}
	
	/**
	 * Get the keyboard.
	 * @return The keyboard.
	 */
	public final IKeyboard getKeyboard()
	{
		return keyboard.get();
	}
	
	/**
	 * Get the mouse.
	 * @return The mouse.
	 */
	public final IMouse getMouse()
	{
		return mouse.get();
	}
	
	/**
	 * Get the joystick.
	 * @return The joystick.
	 */
	public final IJoystick getJoystick()
	{
		return joystick.get();
	}
	
	/**
	 * Get the window.
	 * @return The window.
	 */
	public final IWindow getWindow()
	{
		return window.get();
	}
	
	/**
	 * Get the cursor.
	 * @return The cursor.
	 */
	public final ICursor getCursor()
	{
		return cursor.get();
	}
	
	/**
	 * Get the instance of the active module.
	 * @return The instance of the active module.
	 */
	public static WindowModule getInstance()
	{
		if(instance == null)
		{
			throw new RuntimeException("No module found");
		}
		
		return instance;
	}
}
