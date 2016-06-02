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

import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;

import java.util.HashMap;
import java.util.Map;

import com.snakybo.torch.input.keyboard.IKeyboard;
import com.snakybo.torch.input.keyboard.Key;
import com.snakybo.torch.window.Window;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWKeyboard implements IKeyboard
{	
	Map<Integer, Boolean> current;
	Map<Integer, Boolean> last;
	
	public GLFWKeyboard()
	{
		current = new HashMap<Integer, Boolean>();
		last = new HashMap<Integer, Boolean>();
		
		for(Key key : Key.class.getEnumConstants())
		{
			current.put(key.id, false);
			last.put(key.id, false);
		}
	}
	
	@Override
	public final boolean isDown(Key id)
	{
		return current.get(id.id);
	}
	
	@Override
	public final boolean isUp(Key id)
	{
		return !current.get(id.id);
	}
	
	@Override
	public final boolean onDown(Key id)
	{
		return current.get(id.id) && !last.get(id.id);
	}
	
	@Override
	public final boolean onUp(Key id)
	{
		return !current.get(id.id) && last.get(id.id);
	}

	@Override
	public final void setClipboardString(String string)
	{
		glfwSetClipboardString(Window.getNativeId(), string);
	}

	@Override
	public final String getClipboardString()
	{
		return glfwGetClipboardString(Window.getNativeId());
	}
}
