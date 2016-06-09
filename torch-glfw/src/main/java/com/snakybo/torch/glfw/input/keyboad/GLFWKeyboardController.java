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

package com.snakybo.torch.glfw.input.keyboad;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import java.util.Map;

import com.snakybo.torch.input.keyboard.IKeyboard;
import com.snakybo.torch.module.ModuleController;
import com.snakybo.torch.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWKeyboardController extends ModuleController<IKeyboard>
{
	private final GLFWKeyboard keyboard;
	
	public GLFWKeyboardController()
	{
		keyboard = new GLFWKeyboard();
	}
	
	@Override
	public final void update()
	{
		for(Map.Entry<Integer, Boolean> entry : keyboard.current.entrySet())
		{
			keyboard.last.put(entry.getKey(), entry.getValue());
		}
		
		for(Integer id : keyboard.current.keySet())
		{
			int state = glfwGetKey(Window.getNativeId(), id);
			boolean pressed = false;
			
			if(state == GLFW_PRESS)
			{
				pressed = true;
			}
			
			keyboard.current.put(id, pressed);
		}
	}

	@Override
	public IKeyboard get()
	{
		return keyboard;
	}
}
