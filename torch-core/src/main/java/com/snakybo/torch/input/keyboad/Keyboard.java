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

package com.snakybo.torch.input.keyboad;

import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;

import java.util.HashMap;
import java.util.Map;

import com.snakybo.torch.window.WindowInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Keyboard
{	
	private static final int LAST = Key.MENU.id;
	
	static boolean[] current = new boolean[LAST];
	static boolean[] last = new boolean[LAST];
	
	private static Map<String, Key> inputMapping = new HashMap<String, Key>();
	
	private Keyboard()
	{
		throw new AssertionError();
	}
	
	/**
	 * Register a new input map
	 * @param name The name of the map
	 * @param key The key to assign to the map
	 */
	public static void addInputMapping(String name, Key key)
	{
		inputMapping.put(name, key);
	}
	
	/**
	 * Unregister an input map
	 * @param name The name of the map to unassign
	 */
	public static void removeInputMapping(String name)
	{
		inputMapping.remove(name);
	}
	
	/**
	 * Returns true if the key assigned to the specified map is currently held down
	 * @param map The name of the map
	 * @return True if the key assigned to the map is currently held down
	 */
	public static boolean isMapDown(String map)
	{
		if(!inputMapping.containsKey(map))
		{
			return false;
		}
		
		return isKeyDown(inputMapping.get(map));
	}
	
	/**
	 * Returns true if the specified key is currently held down
	 * @param key The key
	 * @return True if the key is currently held down
	 * @see Key
	 */
	public static boolean isKeyDown(Key key)
	{
		return current[key.id];
	}
	
	/**
	 * Returns true the frame when the specified key assigned to the map is pressed 
	 * @param map The name of the map
	 * @return True if the key assigned to the map was pressed in this frame
	 */
	public static boolean onMapDown(String map)
	{
		if(!inputMapping.containsKey(map))
		{
			return false;
		}
		
		return onKeyDown(inputMapping.get(map));
	}
	
	/**
	 * Returns true the frame when the specified key is pressed 
	 * @param key The key
	 * @return True if the key was pressed in this frame
	 * @see Key
	 */
	public static boolean onKeyDown(Key key)
	{
		return current[key.id] && !last[key.id];
	}
	
	/**
	 * Returns true the frame when the specified key assigned to the map was released
	 * @param map The name of the map
	 * @return True if the key assigned to the map was released in this frame
	 */
	public static boolean onMapUp(String map)
	{
		if(!inputMapping.containsKey(map))
		{
			return false;
		}
		
		return onKeyUp(inputMapping.get(map));
	}
	
	/**
	 * Returns true the frame when the specified key was released
	 * @param key The key
	 * @return True if the key was released in this frame
	 * @see Key
	 */
	public static boolean onKeyUp(Key key)
	{
		return !current[key.id] && last[key.id];
	}

	/**
	 * Set the value of the Clipboard
	 * @param string The new value of the clipboard
	 */
	public static void setClipboardString(String string)
	{
		glfwSetClipboardString(WindowInternal.window, string);
	}

	/**
	 * @return Get the current string stored on the clipboard
	 */
	public static String getClipboardString()
	{
		return glfwGetClipboardString(WindowInternal.window);
	}
}
