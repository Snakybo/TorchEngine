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

/**
 * @author Snakybo
 * @since 1.0
 */
public interface IWindow
{
	/**
	 * Create a new window.
	 * @param windowProperties The window properties.
	 * @param windowMode The window mode.
	 */
	void create(WindowProperties windowProperties, WindowMode windowMode);
	
	/**
	 * Destroy the active window.
	 */
	void destroy();
	
	/**
	 * Bring the window to the front and give it focus.
	 */
	void focus();
	
	/**
	 * Check whether or not the window wants to close.
	 * @return Whether or not the window wants to close.
	 */
	boolean isCloseRequested();
	
	/**
	 * Check whether or not vsync is enabled.
	 * @return Whether or not vsync is enabled.
	 */
	boolean isVSyncEnabled();
	
	/**
	 * Set the size of the window.
	 * @param size The new size of the window.
	 */
	void setSize(Vector2f size);
	
	/**
	 * Enable or disable vsync.
	 * @param enabled Whether or not to enable vsync
	 */
	void setVSyncEnabled(boolean enabled);
	
	/**
	 * Get the size of the window.
	 * @return The size of the window.
	 */
	Vector2f getSize();
	
	/**
	 * Get the aspect ratio of the window.
	 * @return The aspect ratio of the window.
	 */
	default float getAspectRatio()
	{
		Vector2f size = getSize();
		return (float)size.x / (float)size.y;
	}
	
	/**
	 * Get the center of the window.
	 * @return The center of the window.
	 */
	default Vector2f getCenter()
	{
		return getSize().mul(0.5f);
	};
	
	/**
	 * Get the native ID of the window.
	 * @return The native ID of the window.
	 */
	long getNativeId();
}
