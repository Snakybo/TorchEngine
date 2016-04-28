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

package com.snakybo.torch.glfw;

import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback;

import org.lwjgl.glfw.Callbacks;

import com.snakybo.torch.input.mouse.MouseInternal;
import com.snakybo.torch.scene.SceneInternal;
import com.snakybo.torch.window.WindowInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWCallbacks
{
	private GLFWCallbacks()
	{
		throw new AssertionError();
	}
	
	public static void initialize()
	{
		long w = WindowInternal.window;
		
		glfwSetWindowFocusCallback(w, (window, focus) -> SceneInternal.notify("onWindowFocus", new Class<?>[]{boolean.class}, new Object[]{focus}));
		glfwSetWindowIconifyCallback(w, (window, iconified) -> SceneInternal.notify("onWindowIconify", new Class<?>[]{boolean.class}, new Object[]{iconified}));
		
		glfwSetCharCallback(w, (window, codepoint) -> SceneInternal.notify("onCharPressed", new Class<?>[]{char.class}, new Object[]{(char)codepoint}));
		
		glfwSetCursorEnterCallback(w, (window, entered) -> SceneInternal.notify("onCursorEnter", new Class<?>[]{boolean.class}, new Object[]{entered}));
		glfwSetScrollCallback(w, (window, x, y) -> MouseInternal.setScrollDelta((float)x, (float)y));
	}
	
	public static void destroy()
	{
		Callbacks.glfwFreeCallbacks(WindowInternal.window);
	}
}
