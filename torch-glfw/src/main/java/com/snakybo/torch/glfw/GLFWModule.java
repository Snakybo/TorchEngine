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

import static org.lwjgl.glfw.GLFW.glfwGetVersionString;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.glfw.GLFWErrorCallback;

import com.snakybo.torch.debug.Debug;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.glfw.cursor.GLFWCursorController;
import com.snakybo.torch.glfw.input.joystick.GLFWJoystickController;
import com.snakybo.torch.glfw.input.keyboad.GLFWKeyboardController;
import com.snakybo.torch.glfw.input.mouse.GLFWMouseController;
import com.snakybo.torch.glfw.window.GLFWWindowController;
import com.snakybo.torch.module.Module;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class GLFWModule extends Module
{
	public GLFWModule()
	{
		super();
		
		LoggerInternal.log("Initializing GLFW", this);
		
		GLFWErrorCallback ecb = GLFWErrorCallback.createPrint(System.err);
		glfwSetErrorCallback(ecb);
		
		if(!glfwInit())
		{
			throw new RuntimeException("Unable to initialize GLFW");
		}
		
		if(Debug.LOG_LIBRARY_INFO)
		{
			LoggerInternal.log("Version: " + glfwGetVersionString(), "GLFW");
		}
		
		keyboard = new GLFWKeyboardController();
		mouse = new GLFWMouseController();
		joystick = new GLFWJoystickController();
		window = new GLFWWindowController();		
		cursor = new GLFWCursorController();
	}
	
	@Override
	public final void destroy()
	{
		super.destroy();
		
		LoggerInternal.log("Terminating GLFW", this);
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}
