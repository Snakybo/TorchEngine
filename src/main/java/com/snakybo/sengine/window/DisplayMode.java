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

package com.snakybo.sengine.window;

import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;

import org.lwjgl.glfw.GLFWVidMode;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class DisplayMode
{
	private final GLFWVidMode videoMode;
	private final long monitor;
	
	DisplayMode(long monitor, GLFWVidMode videoMode)
	{
		this.monitor = monitor;
		this.videoMode = videoMode;
	}
	
	@Override
	public String toString()
	{
		return    "Width=" + getWidth() +
				", Height=" + getHeight() +
				", Red Bits=" + getRedBits() +
				", Green Bits=" + getGreenBits() +
				", Blue Bits=" + getBlueBits() +
				", Refresh Rate=" + getRefreshRate();
	}
	
	/**
	 * @return Whether or not this is the native {@link DisplayMode} for this {@link Monitor}
	 */
	public final boolean isNativeDisplayMode()
	{
		GLFWVidMode n = glfwGetVideoMode(monitor);
		
		return n.width() == getWidth() &&
				n.height() == getHeight() &&
				n.redBits() == getRedBits() &&
				n.greenBits() == getGreenBits() &&
				n.blueBits() == getBlueBits() &&
				n.refreshRate() == getRefreshRate();
	}
	
	/**
	 * @return The width of this {@link DisplayMode}
	 */
	public final int getWidth()
	{
		return videoMode.width();
	}
	
	/**
	 * @return The height of this {@link DisplayMode}
	 */
	public final int getHeight()
	{
		return videoMode.height();
	}
	
	/**
	 * @return The amount of red bits this {@link DisplayMode} has
	 */
	public final int getRedBits()
	{
		return videoMode.redBits();
	}
	
	/**
	 * @return The amount of green bits this {@link DisplayMode} has
	 */
	public final int getGreenBits()
	{
		return videoMode.greenBits();
	}
	
	/**
	 * @return The amount of blue bits this {@link DisplayMode} has
	 */
	public final int getBlueBits()
	{
		return videoMode.blueBits();
	}
	
	/**
	 * @return The refresh rate of this {@link DisplayMode}
	 */
	public final int getRefreshRate()
	{
		return videoMode.refreshRate();
	}
}
