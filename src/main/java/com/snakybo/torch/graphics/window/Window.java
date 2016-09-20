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

import com.snakybo.torch.graphics.display.Display;
import com.snakybo.torch.graphics.display.DisplayMode;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.glfwSwapInterval;

/**
 * <p>
 * Controller for the game window, allows you to change the resolution and vsync setting.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Window
{
	private static DisplayMode displayMode;
	private static WindowMode windowMode;
	
	private static boolean vsyncEnabled;
	
	static
	{
		displayMode = new DisplayMode(Display.getPrimaryMonitor(), 800, 600);
		windowMode = WindowMode.Windowed;
	}
	
	private Window()
	{
		throw new AssertionError();
	}
	
	/**
	 * <p>
	 * Enable or disable VSYNC.
	 * </p>
	 *
	 * @param enabled Whether or not VSYNC should be enabled.
	 */
	public static void setVSyncEnabled(boolean enabled)
	{
		vsyncEnabled = enabled;
		glfwSwapInterval(enabled ? 1 : 0);
	}
	
	/**
	 * <p>
	 * Set the resolution.
	 * </p>
	 *
	 * @param displayMode The new display mode.
	 * @param windowMode The new window mode.
	 */
	public static void setResolution(DisplayMode displayMode, WindowMode windowMode)
	{
		Window.displayMode = displayMode;
		Window.windowMode = windowMode;
		
		WindowInternal.applyChanges();
	}
	
	/**
	 * <p>
	 * Get whether or not VSYNC is enabled.
	 * </p>
	 *
	 * @return Whether or not VSYNC is enabled.
	 */
	public static boolean isVSyncEnabled()
	{
		return vsyncEnabled;
	}
	
	/**
	 * <p>
	 * Get the display mode.
	 * </p>
	 *
	 * @return The display mode.
	 */
	public static DisplayMode getDisplayMode()
	{
		return displayMode;
	}
	
	/**
	 * <p>
	 * Get the window mode.
	 * </p>
	 *
	 * @return The window mode.
	 */
	public static WindowMode getWindowMode()
	{
		return windowMode;
	}
	
	/**
	 * <p>
	 * Get the coordinates of the center pixel.
	 * </p>
	 *
	 * @return The coordinates of the center pixel.
	 */
	public static Vector2f getCenter()
	{
		return new Vector2f(getWidth(), getHeight()).mul(0.5f);
	}
	
	/**
	 * <p>
	 * Get the width of the game window.
	 * </p>
	 *
	 * @return The width of the game window.
	 */
	public static int getWidth()
	{
		return displayMode.getWidth();
	}
	
	/**
	 * <p>
	 * Get the height of the game window.
	 * </p>
	 *
	 * @return The height of game window.
	 */
	public static int getHeight()
	{
		return displayMode.getHeight();
	}
}
