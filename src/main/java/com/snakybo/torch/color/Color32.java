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

package com.snakybo.torch.color;

import com.snakybo.torch.util.MathUtils;

/**
 * <p>
 * A color in the range [0-255]
 * </p>
 *
 * @see Color
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Color32
{
	private int value;
	
	/**
	 * Create a new color.
	 */
	public Color32()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Create a new color, specifying the red, green and blue component.
	 * <p>
	 * Values are in the range [0-255].
	 * </p>
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 */
	public Color32(int r, int g, int b)
	{
		this(r, g, b, 255);
	}
	
	/**
	 * Create a new color, specifying the red, green blue and alpha component.
	 * <p>
	 * Values are in the range [0-255].
	 * </p>
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 * @param a The alpha component.
	 */
	public Color32(int r, int g, int b, int a)
	{
		// Clamp the input values between 0-255
		r = MathUtils.clamp(r, 0, 255);
		g = MathUtils.clamp(g, 0, 255);
		b = MathUtils.clamp(b, 0, 255);
		a = MathUtils.clamp(a, 0, 255);
		
		value = ((a & 0xFF) << 24) |
				((r & 0xFF) << 16) |
				((g & 0xFF) << 8)  |
				(b & 0xFF);
	}
	
	/**
	 * Copy the value of another color.
	 * @param other The color to copy.
	 */
	public Color32(Color32 other)
	{
		value = other.value;
	}
	
	@Override
	public final String toString()
	{
		return "(r=" + getRed() + ", g=" + getGreen() + ", b=" + getBlue() + ", a=" + getAlpha() + ")";
	}
	
	@Override
	public final int hashCode()
	{
		return value;
	}
	
	@Override
	public final boolean equals(Object obj)
	{
		return obj instanceof Color32 && ((Color32)obj).value == value;
	}
	
	/**
	 * Set the red component of the color. Accepted value is in the range [0-255].
	 * @param r The new red component.
	 */
	public final void setRed(int r)
	{
		value = ((getAlpha() & 0xFF) << 24) |
				((MathUtils.clamp(r, 0, 255) & 0xFF) << 16) |
				((getGreen() & 0xFF) << 8)  |
				(getBlue() & 0xFF);
	}
	
	/**
	 * Set the green component of the color. Accepted value is in the range [0-255].
	 * @param g The new green component.
	 */
	public final void setGreen(int g)
	{
		value = ((getAlpha() & 0xFF) << 24) |
				((getRed() & 0xFF) << 16) |
				((MathUtils.clamp(g, 0, 255) & 0xFF) << 8)  |
				(getBlue() & 0xFF);
	}
	
	/**
	 * Set the blue component of the color. Accepted value is in the range [0-255].
	 * @param b The new blue component.
	 */
	public final void setBlue(int b)
	{
		value = ((getAlpha() & 0xFF) << 24) |
				((getRed() & 0xFF) << 16) |
				((getGreen() & 0xFF) << 8)  |
				((MathUtils.clamp(b, 0, 255) & 0xFF));
	}
	
	/**
	 * Set the alpha component of the color. Accepted value is in the range [0-255].
	 * @param a The new alpha component.
	 */
	public final void setAlpha(int a)
	{
		value = ((MathUtils.clamp(a, 0, 255) & 0xFF) << 24) |
				((getRed() & 0xFF) << 16) |
				((getGreen() & 0xFF) << 8)  |
				(getBlue() & 0xFF);
	}
	
	/**
	 * @return The color values to a {@link Color} in the range [0-1].
	 */
	public final Color toColor()
	{
		return new Color(getRed() / 255f, getGreen() / 255f, getBlue() / 255f, getAlpha() / 255f);
	}
	
	/**
	 * @return The red component of the color.
	 */
	public final int getRed()
	{
		return (getARGB() >> 16) & 0xFF;
	}
	
	/**
	 * @return The green component of the color.
	 */
	public final int getGreen()
	{
		return (getARGB() >> 8) & 0xFF;
	}
	
	/**
	 * @return The blue component of the color.
	 */
	public final int getBlue()
	{
		return getARGB() & 0xFF;
	}
	
	/**
	 * @return The alpha component of the color.
	 */
	public final int getAlpha()
	{
		return getARGB() >> 24 & 0xFF;
	}
	
	/**
	 * @return The ARGB value of the color. Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7 are blue.
	 */
	public final int getARGB()
	{
		return value;
	}
}
