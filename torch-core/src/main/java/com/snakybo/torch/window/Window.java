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
	 * <p>
	 * Shortcut to {@code WindowModule.getWindow().create(WindowMode, WindowMode.Mode)}
	 * </p>
	 * @param windowMode The window mode.
	 * @param displayMode The display mode.
	 * @see IWindow#create(WindowMode, com.snakybo.torch.window.WindowMode.Mode)
	 */
	public static void create(WindowMode windowMode, WindowMode.Mode displayMode)
	{
		WindowModule.getInstance().getWindow().create(windowMode, displayMode);
	}
	
	/**
	 * Destroy the active window.
	 * <p>
	 * Shortcut to {@code WindowModule.getWindow().destroy()}
	 * </p>
	 * @see IWindow#destroy()
	 */
	public static void destroy()
	{
		WindowModule.getInstance().getWindow().destroy();
	}
	
	/**
	 * Bring the window to the front and give it focus.
	 * <p>
	 * Shortcut to {@code WindowModule.getInstance().getWindow().focus()}
	 * </p>
	 * @see IWindow#focus()
	 */
	public static void focus()
	{
		WindowModule.getInstance().getWindow().focus();
	}
	
	/**
	 * Check whether or not the window wants to close.
	 * <p>
	 * Shortcut to {@code WindowModule.getWindow().isCloseRequested()}
	 * </p>
	 * @return Whether or not the window wants to close.
	 * @see IWindow#isCloseRequested()
	 */
	public static boolean isCloseRequested()
	{
		return WindowModule.getInstance().getWindow().isCloseRequested();
	}
	
	/**
	 * Check whether or not vsync is enabled.
	 * <p>
	 * Shortcut to {@code WindowModule.getWindow().isVSyncEnabled()}
	 * </p>
	 * @return Whether or not vsync is enabled.
	 * @see IWindow#isVSyncEnabled()
	 */
	public static boolean isVSyncEnabled()
	{
		return WindowModule.getInstance().getWindow().isVSyncEnabled();
	}
	
	/**
	 * Set the size of the window.
	 * <p>
	 * Shortcut to {@code WindowModule.getWindow().setSize(Vector2f)}
	 * </p>
	 * @param size The new size of the window.
	 * @see IWindow#setSize(Vector2f)
	 */
	public static void setSize(Vector2f size)
	{
		WindowModule.getInstance().getWindow().setSize(size);
	}
	
	/**
	 * Enable or disable vsync.
	 * <p>
	 * Shortcut to {@code WindowModule.getWindow().setVSyncEnabled(boolean)}
	 * </p>
	 * @param enabled Whether or not to enable vsync
	 * @see IWindow#setVSyncEnabled(boolean)
	 */
	public static void setVSyncEnabled(boolean enabled)
	{
		WindowModule.getInstance().getWindow().setVSyncEnabled(enabled);
	}
	
	/**
	 * Set the size of the window.
	 * <p>
	 * Shortcut to {@code WindowModule.getWindow().getSize()}
	 * </p>
	 * @param size The new size of the window.
	 * @see IWindow#getSize()
	 */
	public static Vector2f getSize()
	{
		return WindowModule.getInstance().getWindow().getSize();
	}
	
	/**
	 * Get the aspect ratio of the window.
	 * <p>
	 * Shortcut to {@code WindowModule.getWindow().getAspectRatio()}
	 * </p>
	 * @return The aspect ratio of the window.
	 * @see IWindow#getAspectRatio()
	 */
	public static float getAspectRatio()
	{
		return WindowModule.getInstance().getWindow().getAspectRatio();
	}
	
	/**
	 * Get the center of the window.
	 * <p>
	 * Shortcut to {@code WindowModule.getWindow().getCenter()}
	 * </p>
	 * @return The center of the window.
	 * @see IWindow#getCenter()
	 */
	public static Vector2f getCenter()
	{
		return WindowModule.getInstance().getWindow().getCenter();
	}
	
	/**
	 * Get the native ID of the window.
	 *  <p>
	 * Shortcut to {@code WindowModule.getWindow().getNativeId()}
	 * </p>
	 * @return The native ID of the window.
	 * @see IWindow#getNativeId()
	 */
	public static long getNativeId()
	{
		return WindowModule.getInstance().getWindow().getNativeId();
	}
}
