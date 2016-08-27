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

import com.snakybo.torch.graphics.window.WindowInternal;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Keyboard
{
	static Map<Integer, Boolean> current;
	static Map<Integer, Boolean> last;
	
	private Keyboard()
	{
		throw new AssertionError();
	}
	
	/**
	 * Check if the {@link Key} is currently being pressed.
	 * @param id The {@link Key} to check.
	 */
	public static boolean isDown(Key id)
	{
		return current.get(id.id);
	}
	
	/**
	 * Check if the {@link Key} is currently not being pressed.
	 * @param id The {@link Key} to check.
	 * @return True if the specified {@link Key} is currently not being pressed.
	 */
	public static boolean isUp(Key id)
	{
		return !current.get(id.id);
	}
	
	/**
	 * Check if the {@link Key} is currently being pressed.
	 * @param id The {@link Key} to check.
	 * @return True the first frame {@link Key} is being pressed.
	 */
	public static boolean onDown(Key id)
	{
		return current.get(id.id) && !last.get(id.id);
	}
	
	/**
	 * Check if the {@link Key} is currently not being pressed.
	 * @param id The {@link Key} to check.
	 * @return True the first frame {@link Key} has been released.
	 */
	public static boolean onUp(Key id)
	{
		return !current.get(id.id) && last.get(id.id);
	}
	
	/**
	 * Set the value of the clipboard.
	 * @param string The new value of the clipboard.
	 */
	public static void setClipboardString(String string)
	{
		glfwSetClipboardString(WindowInternal.getNativeId(), string);
	}
	
	/**
	 * Get the value of the clipboard.
	 * @return The value of the clipboard.
	 */
	public static String getClipboardString()
	{
		return glfwGetClipboardString(WindowInternal.getNativeId());
	}
}
