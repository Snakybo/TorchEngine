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

package com.snakybo.sengine.input.mouse;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import com.snakybo.sengine.window.WindowInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MouseInternal
{
	private MouseInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Initialize the mouse position and delta
	 */
	public static void create()
	{
		Mouse.mousePositionDelta = new Vector2f();
		Mouse.mousePosition = getCursorPosition();
	}
	
	/**
	 * Unused
	 */
	public static void destroy()
	{
	}
	
	/**
	 * Update the mouse button states, sets the last mouse button states to the current, and polls GLFW for the current states.
	 * Also updates the cursor's position and delta
	 */
	public static void update()
	{
		for(int i = 0; i < Mouse.last.length; i++)
		{
			Mouse.last[i] = Mouse.current[i];
		}
		
		for(int i = 0; i < Mouse.current.length; i++)
		{
			int state = glfwGetMouseButton(WindowInternal.window, i);
			boolean pressed = false;
			
			if(state == GLFW_PRESS || state == GLFW_REPEAT)
			{
				pressed = true;
			}
			
			Mouse.current[i] = pressed;
		}
		
		updateCursorPosition();
	}
	
	/**
	 * Update the cursor position and calculate the delta
	 */
	private static void updateCursorPosition()
	{
		Vector2f pos = getCursorPosition();
		
		float xDelta = pos.x - Mouse.mousePosition.x;
		float yDelta = pos.y - Mouse.mousePosition.y;
		
		Mouse.mousePosition = pos;
		Mouse.mousePositionDelta = new Vector2f(xDelta, yDelta);
	}
	
	/**
	 * Polls GLFW for the current cursor position
	 * @return The current cursor position
	 */
	private static Vector2f getCursorPosition()
	{
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(WindowInternal.window, xBuffer, yBuffer);
		
		return new Vector2f((float)xBuffer.get(), (float)yBuffer.get());
	}
}
