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

package com.snakybo.sengine.input.keyboad;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;

import org.lwjgl.glfw.GLFWCharCallback;

import com.snakybo.sengine.scene.SceneUtilities;
import com.snakybo.sengine.window.WindowInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class KeyboardInternal
{
	private static class CharCallback extends GLFWCharCallback
	{
		@Override
		public void invoke(long window, int codepoint)
		{
			SceneUtilities.notifyGameObjectsCharPressed(Character.toChars(codepoint)[0]);
		}
	}
	
	private static GLFWCharCallback glfwCharCallback;
	
	private KeyboardInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Create callbacks
	 */
	public static void create()
	{
		glfwSetCharCallback(WindowInternal.window, glfwCharCallback = new CharCallback());
	}
	
	/**
	 * Release callbacks
	 */
	public static void destroy()
	{
		glfwCharCallback.release();
	}
	
	/**
	 * Update the key states, sets the last key states to the current, and polls GLFW for the current states
	 */
	public static void update()
	{
		for(int i = 0; i < Keyboard.last.length; i++)
		{
			Keyboard.last[i] = Keyboard.current[i];
		}
		
		for(int i = 0; i < Keyboard.current.length; i++)
		{
			int state = glfwGetKey(WindowInternal.window, i);
			boolean pressed = false;
			
			if(state == GLFW_PRESS || state == GLFW_REPEAT)
			{
				pressed = true;
			}
			
			Keyboard.current[i] = pressed;
		}
	}
}
