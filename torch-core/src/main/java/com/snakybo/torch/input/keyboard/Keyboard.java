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

package com.snakybo.torch.input.keyboard;

import com.snakybo.torch.module.WindowModule;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Keyboard
{
	private Keyboard()
	{
		throw new AssertionError();
	}
	
	/**
	 * Check if the {@link Key} is currently being pressed.
	 * <p>
	 * Shortcut to {@code WindowModule.getKeyboard().isDown(Key)}
	 * </p>
	 * @param id The {@link Key} to check.
	 * @return True if the specified {@link Key} is currently being pressed.
	 * @see IKeyboard#isDown(Key)
	 */
	public static boolean isDown(Key id)
	{
		return WindowModule.getInstance().getKeyboard().isDown(id);
	}
	
	/**
	 * Check if the {@link Key} is currently not being pressed.
	 * <p>
	 * Shortcut to {@code WindowModule.getKeyboard().isUp(Key)}
	 * </p>
	 * @param id The {@link Key} to check.
	 * @return True if the specified {@link Key} is currently not being pressed.
	 * @see IKeyboard#isUp(Key)
	 */
	public static boolean isUp(Key id)
	{
		return WindowModule.getInstance().getKeyboard().isUp(id);
	}
	
	/**
	 * Check if the {@link Key} is currently being pressed.
	 * <p>
	 * Shortcut to {@code WindowModule.getKeyboard().onDown(Key)}
	 * </p>
	 * @param id The {@link Key} to check.
	 * @return True the first frame {@link Key} is being pressed.
	 * @see IKeyboard#onDown(Key)
	 */
	public static boolean onDown(Key id)
	{
		return WindowModule.getInstance().getKeyboard().onDown(id);
	}
	
	/**
	 * Check if the {@link Key} is currently not being pressed.
	 * <p>
	 * Shortcut to {@code WindowModule.getKeyboard().onUp(Key)}
	 * </p>
	 * @param id The {@link Key} to check.
	 * @return True the first frame {@link Key} has been released.
	 * @see IKeyboard#onUp(Key)
	 */
	public static boolean onUp(Key id)
	{
		return WindowModule.getInstance().getKeyboard().onUp(id);
	}
	
	/**
	 * Set the value of the clipboard.
	 * <p>
	 * Shortcut to {@code WindowModule.getKeyboard().setClipboardString(String)}
	 * </p>
	 * @param string The new value of the clipboard.
	 * @see IKeyboard#setClipboardString(String)
	 */
	public static void setClipboardString(String string)
	{
		WindowModule.getInstance().getKeyboard().setClipboardString(string);
	}
	
	/**
	 * Get the value of the clipboard.
	 * <p>
	 * Shortcut to {@code WindowModule.getKeyboard().setClipboardString(String)}
	 * </p>
	 * @return The value of the clipboard.
	 * @see IKeyboard#getClipboardString()
	 */
	public static String getClipboardString()
	{
		return WindowModule.getInstance().getKeyboard().getClipboardString();
	}
}
