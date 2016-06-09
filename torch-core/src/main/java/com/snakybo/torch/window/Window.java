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

package com.snakybo.torch.window;

import org.joml.Vector2f;

import com.snakybo.torch.module.Module;
import com.snakybo.torch.module.WindowModule;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Window
{
	private Window()
	{
		throw new AssertionError();
	}
	
	/**
	 * Create a new window.
	 * @param windowProperties The window properties.
	 * @param windowMode The window mode.
	 */
	public static void create(WindowProperties windowProperties, WindowMode windowMode)
	{
		Module.getModule(WindowModule.class).getWindow().create(windowProperties, windowMode);
	}
	
	/**
	 * Destroy the active window.
	 */
	public static void destroy()
	{
		Module.getModule(WindowModule.class).getWindow().destroy();
	}
	
	/**
	 * Bring the window to the front and give it focus.
	 */
	public static void focus()
	{
		Module.getModule(WindowModule.class).getWindow().focus();
	}
	
	/**
	 * Check whether or not the window wants to close.
	 * @return Whether or not the window wants to close.
	 */
	public static boolean isCloseRequested()
	{
		return Module.getModule(WindowModule.class).getWindow().isCloseRequested();
	}
	
	/**
	 * Check whether or not vsync is enabled.
	 * @return Whether or not vsync is enabled.
	 */
	public static boolean isVSyncEnabled()
	{
		return Module.getModule(WindowModule.class).getWindow().isVSyncEnabled();
	}
	
	/**
	 * Set the size of the window.
	 * @param size The new size of the window.
	 */
	public static void setSize(Vector2f size)
	{
		Module.getModule(WindowModule.class).getWindow().setSize(size);
	}
	
	/**
	 * Enable or disable vsync.
	 * @param enabled Whether or not to enable vsync
	 */
	public static void setVSyncEnabled(boolean enabled)
	{
		Module.getModule(WindowModule.class).getWindow().setVSyncEnabled(enabled);
	}
	
	/**
	 * Set the size of the window.
	 * @param size The new size of the window.
	 */
	public static Vector2f getSize()
	{
		return Module.getModule(WindowModule.class).getWindow().getSize();
	}
	
	/**
	 * Get the aspect ratio of the window.
	 * @return The aspect ratio of the window.
	 */
	public static float getAspectRatio()
	{
		return Module.getModule(WindowModule.class).getWindow().getAspectRatio();
	}
	
	/**
	 * Get the center of the window.
	 * @return The center of the window.
	 */
	public static Vector2f getCenter()
	{
		return Module.getModule(WindowModule.class).getWindow().getCenter();
	}
	
	/**
	 * Get the native ID of the window.
	 * @return The native ID of the window.
	 */
	public static long getNativeId()
	{
		return Module.getModule(WindowModule.class).getWindow().getNativeId();
	}
}
