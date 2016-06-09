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
import com.snakybo.torch.input.joystick.IJoystick;
import com.snakybo.torch.input.keyboard.IKeyboard;
import com.snakybo.torch.input.mouse.IMouse;
import com.snakybo.torch.interfaces.ICreatable;
import com.snakybo.torch.interfaces.IDestroyable;
import com.snakybo.torch.interfaces.IUpdatable;
import com.snakybo.torch.window.IWindow;

/**
 * @author Snakybo
 * @since 1.0
 */
public abstract class WindowModule implements IModule<WindowModule>, ICreatable, IUpdatable, IDestroyable
{
	protected ModuleController<IKeyboard> keyboard;
	protected ModuleController<IMouse> mouse;
	protected ModuleController<IJoystick> joystick;	
	protected ModuleController<IWindow> window;	
	protected ModuleController<ICursor> cursor;
	
	protected WindowModule()
	{
		Module.registerModule(this);
	}
	
	@Override
	public void create()
	{
		keyboard.create();
		mouse.create();
		joystick.create();
		cursor.create();
		window.create();
	}
	
	@Override
	public void update()
	{
		keyboard.update();
		mouse.update();
		joystick.update();
		cursor.update();
	}
	
	@Override
	public void destroy()
	{
		keyboard.destroy();
		mouse.destroy();
		joystick.destroy();
		cursor.destroy();
		window.destroy();
	}
	
	@Override
	public final Class<WindowModule> getModuleType()
	{
		return WindowModule.class;
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
}
