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

package com.snakybo.torch.glfw.window;

import static org.lwjgl.glfw.GLFW.GLFW_BLUE_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_DECORATED;
import static org.lwjgl.glfw.GLFW.GLFW_GREEN_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_RED_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_REFRESH_RATE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.Callbacks;

import com.snakybo.torch.TorchGame;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.glfw.input.mouse.GLFWMouseController;
import com.snakybo.torch.module.Module;
import com.snakybo.torch.scene.SceneInternal;
import com.snakybo.torch.window.IWindow;
import com.snakybo.torch.window.WindowMode;

/**
 * @author Snakybo
 * @since 1.0
 */
public class GLFWWindow implements IWindow
{
	private WindowMode windowMode;
	private WindowMode.Mode displayMode;
	
	private long windowId;
	
	private boolean vsyncEnabled;
	
	@Override
	public final void create(WindowMode windowMode, WindowMode.Mode displayMode)
	{
		if(this.windowMode != null || windowId != NULL)
		{
			destroy();
		}
		
		LoggerInternal.log("Creating window: " + windowMode, this);
		
		this.windowMode = windowMode;
		
		long monitor = NULL;
		
		glfwDefaultWindowHints();		
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		if(displayMode == WindowMode.Mode.Borderless)
		{
			glfwWindowHint(GLFW_RED_BITS, windowMode.getBitsPerPixel());
			glfwWindowHint(GLFW_GREEN_BITS, windowMode.getBitsPerPixel());
			glfwWindowHint(GLFW_BLUE_BITS, windowMode.getBitsPerPixel());
			glfwWindowHint(GLFW_REFRESH_RATE, windowMode.getFrequency());
			
			glfwWindowHint(GLFW_DECORATED, GL_FALSE);
		}
		else if(displayMode == WindowMode.Mode.Fullscreen)
		{
			if(!(windowMode instanceof GLFWWindowMode))
			{
				throw new IllegalArgumentException("Invalid WindowMode, expected GLFWWindowMode, got: " + windowMode.getClass());
			}
			
			GLFWWindowMode glfwWindowMode = (GLFWWindowMode)windowMode;
			monitor = glfwWindowMode.getMonitor();
		}
		
		windowId = glfwCreateWindow(windowMode.getWidth(), windowMode.getHeight(), TorchGame.getName(), monitor, NULL);
		if(windowId == NULL)
		{
			throw new RuntimeException("Unable to create GLFW window");
		}
		
		glfwSetWindowFocusCallback(windowId, (window, focus) -> SceneInternal.notify("onWindowFocus", new Class<?>[]{boolean.class}, new Object[]{focus}));
		glfwSetWindowIconifyCallback(windowId, (window, iconified) -> SceneInternal.notify("onWindowIconify", new Class<?>[]{boolean.class}, new Object[]{iconified}));
		
		glfwSetCharCallback(windowId, (window, codepoint) -> SceneInternal.notify("onCharPressed", new Class<?>[]{char.class}, new Object[]{(char)codepoint}));
		
		glfwSetCursorEnterCallback(windowId, (window, entered) -> SceneInternal.notify("onCursorEnter", new Class<?>[]{boolean.class}, new Object[]{entered}));
		glfwSetScrollCallback(windowId, (window, x, y) -> ((GLFWMouseController)Module.getInstance().getMouseController()).setScrollDelta((float)x, (float)y));
		
		glfwMakeContextCurrent(windowId);
		glfwShowWindow(windowId);
		setVSyncEnabled(true);
	}
	
	@Override
	public final void destroy()
	{
		LoggerInternal.log("Destroying window", this);
		
		Callbacks.glfwFreeCallbacks(windowId);
		
		glfwDestroyWindow(windowId);
		
		windowMode = null;
		windowId = NULL;
	}
	
	@Override
	public final boolean isCloseRequested()
	{
		return glfwWindowShouldClose(windowId);
	}
	
	@Override
	public final boolean isVSyncEnabled()
	{
		return vsyncEnabled;
	}
	
	// TODO: Don't recreate the window every time the size changes
	@Override
	public final void setSize(Vector2f size)
	{
		WindowMode wm = new WindowMode((int)size.x, (int)size.y,
				windowMode.getBitsPerPixel(),
				windowMode.getFrequency());
		
		create(wm, displayMode);
	}
	
	@Override
	public final void setVSyncEnabled(boolean enabled)
	{
		this.vsyncEnabled = enabled;
		
		glfwSwapInterval(enabled ? 1 : 0);
	}
	
	@Override
	public final Vector2f getSize()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetWindowSize(windowId, width, height);
		return new Vector2f(width.get(), height.get());
	}
	
	@Override
	public long getNativeId()
	{
		return windowId;
	}
}
