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

package com.snakybo.torch.graphics.window;

import com.snakybo.torch.Game;
import com.snakybo.torch.event.Events;
import com.snakybo.torch.graphics.display.Display;
import com.snakybo.torch.graphics.display.DisplayMode;
import com.snakybo.torch.input.mouse.Mouse;
import com.snakybo.torch.util.debug.LoggerInternal;
import org.joml.Vector2f;
import org.lwjgl.glfw.Callbacks;

import static org.lwjgl.glfw.GLFW.GLFW_BLUE_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_DECORATED;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_GREEN_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_RED_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class WindowInternal
{
	private static DisplayMode cachedDisplayMode;
	private static WindowMode cachedWindowMode;
	
	private static long nativeId;
	
	private WindowInternal()
	{
		throw new AssertionError();
	}
	
	public static void create()
	{
		LoggerInternal.log("Creating GLFW window");
		
		cachedDisplayMode = Window.getDisplayMode();
		cachedWindowMode = Window.getWindowMode();
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		nativeId = glfwCreateWindow(Window.getWidth(), Window.getHeight(), Game.getName(), NULL, NULL);
		if(nativeId == NULL)
		{
			throw new RuntimeException("Unable to create GLFW window");
		}
		
		glfwSetWindowFocusCallback(nativeId, (window, focus) -> Events.onWindowFocus.getListeners().forEach(cb -> cb.invoke(focus)));
		glfwSetWindowIconifyCallback(nativeId, (window, iconified) -> Events.onWindowIconify.getListeners().forEach(cb -> cb.invoke(iconified)));
		
		glfwSetCharCallback(nativeId, (window, codepoint) -> Events.onCharPressed.getListeners().forEach(cb -> cb.invoke((char)codepoint)));
		
		glfwSetCursorEnterCallback(nativeId, (window, entered) -> Events.onCursorEnter.getListeners().forEach(cb -> cb.invoke(entered)));
		glfwSetScrollCallback(nativeId, (window, x, y) -> Mouse.setScrollDelta(new Vector2f((float)x, (float)y)));
		
		moveToCenter();
		
		glfwMakeContextCurrent(nativeId);
		glfwShowWindow(nativeId);
		Window.setVSyncEnabled(true);
	}
	
	public static void applyChanges()
	{
		DisplayMode displayMode = Window.getDisplayMode();
		long monitor = NULL;
		
		switch(Window.getWindowMode())
		{
		case Fullscreen:
			monitor = displayMode.getDisplay().getNativeId();
			break;
		case Borderless:
			glfwWindowHint(GLFW_RED_BITS, displayMode.getBitsPerPixel());
			glfwWindowHint(GLFW_GREEN_BITS, displayMode.getBitsPerPixel());
			glfwWindowHint(GLFW_BLUE_BITS, displayMode.getBitsPerPixel());
			glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
			break;
		case Windowed:
			glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
			break;
		}
		
		glfwSetWindowMonitor(nativeId,
				monitor,
				0,
				0,
				displayMode.getWidth(),
				displayMode.getHeight(),
				displayMode.getFrequency()
		);
		
		glViewport(0, 0, displayMode.getWidth(), displayMode.getHeight());
		Events.onWindowResize.getListeners().forEach((cb) -> cb.invoke());
		
		if(Window.getWindowMode() == WindowMode.Windowed)
		{
			moveToCenter();
		}
	}
	
	public static void pollEvents()
	{
		glfwPollEvents();
	}
	
	public static void update()
	{
		glfwSwapBuffers(nativeId);
	}
	
	public static void destroy()
	{
		LoggerInternal.log("Destroying window");
		
		Callbacks.glfwFreeCallbacks(nativeId);
		
		glfwDestroyWindow(nativeId);
		
		nativeId = NULL;
	}
	
	private static void moveToCenter()
	{
		DisplayMode dm = Display.getPrimaryMonitor().getNativeDisplayMode();
		int centerX = (dm.getWidth() / 2) - (Window.getWidth() / 2);
		int centerY = (dm.getHeight() / 2) - (Window.getHeight() / 2);
		
		glfwSetWindowPos(nativeId, centerX, centerY);
	}
	
	public static boolean isCloseRequested()
	{
		return glfwWindowShouldClose(nativeId);
	}
	
	public static long getNativeId()
	{
		return nativeId;
	}
}
