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

package com.snakybo.sengine.input;

import java.util.HashMap;
import java.util.Map;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.input.keyboad.Key;
import com.snakybo.sengine.input.keyboad.Keyboard;
import com.snakybo.sengine.input.mouse.Mouse;
import com.snakybo.sengine.input.mouse.MouseButton;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Input
{
	private static class InputData<T>
	{
		private T id;
		
		public InputData(T id)
		{
			this.id = id;
		}
		
		public final T get()
		{
			return id;
		}
	}
	
	private static class KeyInputData extends InputData<Key>
	{
		public KeyInputData(Key key)
		{
			super(key);
		}
	}
	
	private static class MouseButtonInputData extends InputData<MouseButton>
	{
		public MouseButtonInputData(MouseButton mouseButton)
		{
			super(mouseButton);
		}
	}
	
	private static Map<String, InputData<?>> data;
	
	static
	{
		data = new HashMap<String, InputData<?>>();
	}
	
	private Input()
	{
		throw new AssertionError();
	}
	
	/**
	 * Check if the key or mouse button assigned to {@code name} is currently being pressed
	 * @param name - The assigned name
	 * @return Whether the key or mouse button is currently being pressed
	 */
	public static boolean isDown(String name)
	{
		if(!data.containsKey(name))
		{
			Logger.logError("Nothing is assigned to: " + name, "Input");
			return false;
		}
		
		InputData<?> inputData = data.get(name);
		Object value = inputData.get();
		
		if(inputData.getClass() == KeyInputData.class)
		{
			Key key = (Key)inputData.get();
			return Keyboard.isKeyDown(key);
		}
		else if(inputData.getClass() == MouseButtonInputData.class)
		{
			MouseButton mouseButton = (MouseButton)inputData.get();
			return Mouse.isMouseDown(mouseButton);
		}
		
		Logger.logError("Unknown input type: " + value.getClass(), "Input");
		return false;
	}
	
	/**
	 * @param name - The assigned name
	 * @return True on the first frame the key or mouse button with {@code name} has been pressed
	 */
	public static boolean onDown(String name)
	{
		if(!data.containsKey(name))
		{
			Logger.logError("Nothing is assigned to: " + name, "Input");
			return false;
		}
		
		InputData<?> inputData = data.get(name);
		Object value = inputData.get();
		
		if(inputData.getClass() == KeyInputData.class)
		{
			Key key = (Key)inputData.get();
			return Keyboard.onKeyDown(key);
		}
		else if(inputData.getClass() == MouseButtonInputData.class)
		{
			MouseButton mouseButton = (MouseButton)inputData.get();
			return Mouse.onMouseDown(mouseButton);
		}
		
		Logger.logError("Unknown input type: " + value.getClass(), "Input");
		return false;
	}
	
	/**
	 * @param name - The assigned name
	 * @return True on the first frame the key or mouse button with {@code name} has been released
	 */
	public static boolean onUp(String name)
	{
		if(!data.containsKey(name))
		{
			Logger.logError("Nothing is assigned to: " + name, "Input");
			return false;
		}
		
		InputData<?> inputData = data.get(name);
		Object value = inputData.get();
		
		if(inputData.getClass() == KeyInputData.class)
		{
			Key key = (Key)inputData.get();
			return Keyboard.onKeyUp(key);
		}
		else if(inputData.getClass() == MouseButtonInputData.class)
		{
			MouseButton mouseButton = (MouseButton)inputData.get();
			return Mouse.onMouseUp(mouseButton);
		}
		
		Logger.logError("Unknown input type: " + value.getClass(), "Input");
		return false;
	}

	/**
	 * Remove the assignment with {@code name}
	 * @param name - The name of the key or mouse button to unassign
	 */
	public static void remove(String name)
	{
		data.remove(name);
	}
	
	/**
	 * @param key - The key
	 * @return Whether or not the specified key has been assigned
	 */
	public static boolean isAssigned(Key key)
	{
		return getAssigned(key) != null;
	}
	
	/**
	 * @param mouseButton - The mouse button
	 * @return Whether or not the specified mouse button has been assigned
	 */
	public static boolean isAssigned(MouseButton mouseButton)
	{
		return getAssigned(mouseButton) != null;
	}
	
	/**
	 * Assign a key
	 * @param name - The name to assign the key to
	 * @param key - The key to assign
	 */
	public static void set(String name, Key key)
	{
		data.put(name, new KeyInputData(key));
	}
	
	/**
	 * Assign a mouse button
	 * @param name - The name to assign the mouse button
	 * @param mouseButton - The mouse button to assign
	 */
	public static void set(String name, MouseButton mouseButton)
	{
		data.put(name, new MouseButtonInputData(mouseButton));
	}
	
	/**
	 * @param key - The key to get the assigned name from
	 * @return The assigned name of the key
	 */
	public static String getAssigned(Key key)
	{
		for(Map.Entry<String, InputData<?>> kvp : data.entrySet())
		{
			if(kvp.getValue().getClass() == KeyInputData.class)
			{
				Key k = (Key)kvp.getValue().get();
				
				if(key == k)
				{
					return kvp.getKey();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @param mouseButton - The mouse button to get the assigned name from
	 * @return The assigned name of the mouse button
	 */
	public static String getAssigned(MouseButton mouseButton)
	{
		for(Map.Entry<String, InputData<?>> kvp : data.entrySet())
		{
			if(kvp.getValue().getClass() == MouseButtonInputData.class)
			{
				MouseButton k = (MouseButton)kvp.getValue().get();
				
				if(mouseButton == k)
				{
					return kvp.getKey();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @return All assigned names
	 */
	public static Iterable<String> getAllAssignedNames()
	{
		return data.keySet();
	}
	
	/**
	 * @return All assigned names and key/mouse button names
	 */
	public static Iterable<Map.Entry<String, String>> getAllAssigned()
	{
		Map<String, String> result = new HashMap<String, String>();
		
		for(Map.Entry<String, InputData<?>> kvp : data.entrySet())
		{
			String value = null;
			
			if(kvp.getValue().getClass() == KeyInputData.class)
			{
				value = ((Key)kvp.getValue().get()).toString();
			}
			else if(kvp.getValue().getClass() == MouseButtonInputData.class)
			{
				value = ((MouseButton)kvp.getValue().get()).toString();
			}
			
			result.put(kvp.getKey(), value);
		}
		
		return result.entrySet();
	}
}
