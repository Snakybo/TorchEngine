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

package com.snakybo.torch.glfw.input.mouse;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import com.snakybo.torch.input.mouse.IMouse;
import com.snakybo.torch.module.ModuleController;
import com.snakybo.torch.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWMouseController extends ModuleController<IMouse>
{
	private final GLFWMouse mouse;
	
	public GLFWMouseController()
	{
		mouse = new GLFWMouse();
	}
	
	@Override
	public void create()
	{
		mouse.mousePosition = getCursorPosition();
	}

	@Override
	public final void update()
	{
		mouse.scrollDelta = new Vector2f(0, 0);
		
		for(int i = 0; i < mouse.last.length; i++)
		{
			mouse.last[i] = mouse.current[i];
		}
		
		for(int i = 0; i < mouse.current.length; i++)
		{
			int state = glfwGetMouseButton(Window.getNativeId(), i);
			boolean pressed = false;
			
			if(state == GLFW_PRESS || state == GLFW_REPEAT)
			{
				pressed = true;
			}
			
			mouse.current[i] = pressed;
		}
		
		updateCursorPosition();
	}
	
	@Override
	public final IMouse get()
	{
		return mouse;
	}

	private final void updateCursorPosition()
	{
		Vector2f pos = getCursorPosition();
		
		float xDelta = pos.x - mouse.mousePosition.x;
		float yDelta = pos.y - mouse.mousePosition.y;
		
		mouse.mousePosition = pos;
		mouse.mousePositionDelta = new Vector2f(xDelta, yDelta);
	}
	
	private final Vector2f getCursorPosition()
	{
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Window.getNativeId(), xBuffer, yBuffer);
		
		return new Vector2f((float)xBuffer.get(), (float)yBuffer.get());
	}
}
