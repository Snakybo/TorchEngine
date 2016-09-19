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

package com.snakybo.torch.util.monitor;

/**
 * <p>
 * A {@code DisplayMode} is a container class with all the information to create a window.
 * </p>
 *
 * <p>
 * A {@code DisplayMode} can be created manually using one of the constructors, but there is no guarantee
 * the user's system has support for it.
 * </p>
 *
 * <p>
 * You can also get all available display modes for a monitor using {@link Monitor#getAllDisplayModes()}, or just
 * the native display mode using {@link Monitor#getNativeDisplayMode()}.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public class DisplayMode
{
	private Monitor monitor;
	
	private int width;
	private int height;
	
	private int bpp;
	private int freq;

	/**
	 * <p>
	 * Create a new {@code DisplayMode}.
	 * </p>
	 *
	 * @param monitor The monitor this display mode belongs to.
	 * @param width The width.
	 * @param height The height.
	 */
	public DisplayMode(Monitor monitor, int width, int height)
	{
		this(monitor, width, height, 0, 0);
	}
	
	/**
	 * <p>
	 * Create a new {@code DisplayMode}.
	 * </p>
	 *
	 * @param monitor The monitor this display mode belongs to.
	 * @param width The width.
	 * @param height The height.
	 * @param bpp The number of bits per pixel.
	 * @param freq The refresh rate.
	 */
	public DisplayMode(Monitor monitor, int width, int height, int bpp, int freq)
	{
		this.monitor = monitor;
		this.width = width;
		this.height = height;
		this.bpp = bpp;
		this.freq = freq;
	}
	
	@Override
	public final String toString()
	{
		return width + "x" + height + "x" + bpp + " @" + freq + "Hz";
	}
	
	@Override
	public final int hashCode()
	{
		return width ^ height ^ bpp ^ freq;
	}
	
	@Override
	public final boolean equals(Object obj)
	{
		if(obj == null || !(obj instanceof DisplayMode))
		{
			return false;
		}
		
		DisplayMode dm = (DisplayMode)obj;
		return dm.width == width
				&& dm.height == height
				&& dm.bpp == bpp
				&& dm.freq == freq;
	}
	
	/**
	 * <p>
	 * Get the monitor this {@code DisplayMode} belongs to.
	 * </p>
	 *
	 * @return The monitor.
	 */
	public final Monitor getMonitor()
	{
		return monitor;
	}
	
	/**
	 * <p>
	 * Get the width.
	 * </p>
	 *
	 * @return The width.
	 */
	public final int getWidth()
	{
		return width;
	}
	
	/**
	 * <p>
	 * Get the height.
	 * </p>
	 *
	 * @return The height.
	 */
	public final int getHeight()
	{
		return height;
	}
	
	/**
	 * <p>
	 * Get the number of bits per pixel.
	 * </p>
	 *
	 * <p>
	 * Bits per pixel is not stored as a separate value for the R, G, B, and A channels.
	 * This value is used for all 4.
	 * </p>
	 *
	 * @return The number of bits per pixel.
	 */
	public final int getBitsPerPixel()
	{
		return bpp;
	}
	
	/**
	 * <p>
	 * Get the refresh rate.
	 * </p>
	 *
	 * @return The refresh rate.
	 */
	public final int getFrequency()
	{
		return freq;
	}
}
