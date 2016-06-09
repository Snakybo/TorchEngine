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

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

import org.joml.Vector2f;

import com.snakybo.torch.input.mouse.IMouse;
import com.snakybo.torch.input.mouse.MouseButton;
import com.snakybo.torch.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWMouse implements IMouse
{
	private static final int LAST = MouseButton.BUTTON_8.id;
	
	boolean[] current;
	boolean[] last;
	
	Vector2f mousePositionDelta;
	Vector2f mousePosition;
	
	Vector2f scrollDelta;
	
	public GLFWMouse()
	{
		current = new boolean[LAST];
		last = new boolean[LAST];
		
		mousePositionDelta = new Vector2f();
		mousePosition = new Vector2f();
		
		scrollDelta = new Vector2f();
	}
	
	@Override
	public final boolean isDown(MouseButton id)
	{
		return current[id.id];
	}

	@Override
	public final boolean isUp(MouseButton id)
	{
		return !current[id.id];
	}

	@Override
	public final boolean onDown(MouseButton id)
	{
		return current[id.id] && !last[id.id];
	}

	@Override
	public final boolean onUp(MouseButton id)
	{
		return !current[id.id] && last[id.id];
	}
	
	@Override
	public final void setCursorPosition(Vector2f position)
	{
		glfwSetCursorPos(Window.getNativeId(), (int)position.x, (int)position.y);
	}
	
	/**
	 * Set the scroll delta.
	 * @param x The new X delta.
	 * @param y The new Y delta.
	 */
	public final void setScrollDelta(float x, float y)
	{
		scrollDelta = new Vector2f(x, y);
	}
	
	@Override
	public final Vector2f getCursorPosition()
	{
		return new Vector2f(mousePosition);
	}
	
	@Override
	public final Vector2f getCursorPositionDelta()
	{
		return new Vector2f(mousePositionDelta);
	}
	
	@Override
	public final Vector2f getScrollDelta()
	{
		return scrollDelta;
	}
}
