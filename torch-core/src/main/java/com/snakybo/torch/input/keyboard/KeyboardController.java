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

import com.snakybo.torch.window.Window;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class KeyboardController
{
	private KeyboardController()
	{
		throw new AssertionError();
	}
	
	public static void create()
	{
		Keyboard.current = new HashMap<>();
		Keyboard.last = new HashMap<>();
		
		for(Key key : Key.class.getEnumConstants())
		{
			Keyboard.current.put(key.id, false);
			Keyboard.last.put(key.id, false);
		}
	}
	
	public static void update()
	{
		for(Map.Entry<Integer, Boolean> entry : Keyboard.current.entrySet())
		{
			Keyboard.last.put(entry.getKey(), entry.getValue());
		}
		
		for(Integer id : Keyboard.current.keySet())
		{
			int state = glfwGetKey(Window.getNativeId(), id);
			boolean pressed = false;
			
			if(state == GLFW_PRESS)
			{
				pressed = true;
			}
			
			Keyboard.current.put(id, pressed);
		}
	}
}
