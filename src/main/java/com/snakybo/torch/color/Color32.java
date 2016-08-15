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

import java.io.Serializable;

/**
 * <p>
 * A RGBA color in the range [0-255]
 * </p>
 *
 * <p>
 * The color is immutable, meaning usage of the various setters, or mathematical operations in
 * this class will instead create a new {@link Color32} object with the applied changes.
 * This instance of the color will not be changed.
 * </p>
 *
 * @see Color
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Color32 implements Serializable
{
	/**
	 * Completely transparent, no color.
	 */
	public static final Color32 CLEAR = new Color32(0, 0, 0, 0);
	
	/**
	 * The color white.
	 */
	public static final Color32 WHITE = new Color32(255, 255, 255);
	
	/**
	 * The color light gray.
	 */
	public static final Color32 LIGHT_GRAY = new Color32(192, 192, 192);
	
	/**
	 * Alternative spelling for {@link #LIGHT_GRAY}
	 */
	public static final Color32 LIGHT_GREY = LIGHT_GRAY;
	
	/**
	 * The color gray.
	 */
	public static final Color32 GRAY = new Color32(128, 128, 128);
	
	/**
	 * Alternative spelling for {@link #GRAY};
	 */
	public static final Color32 GREY = GRAY;
	
	/**
	 * The color dark gray.
	 */
	public static final Color32 DARK_GRAY = new Color32(64, 64, 64);
	
	/**
	 * Alternative spelling for {@link #DARK_GRAY}
	 */
	public static final Color32 DARK_GREY = DARK_GRAY;
	
	/**
	 * The color light gray.
	 */
	public static final Color32 BLACK = new Color32(0, 0, 0);
	
	/**
	 * The color red.
	 */
	public static final Color32 RED = new Color32(255, 0, 0);
	
	/**
	 * The color pink.
	 */
	public static final Color32 PINK = new Color32(255, 175, 175);
	
	/**
	 * The color orange.
	 */
	public static final Color32 ORANGE = new Color32(255, 200, 0);
	
	/**
	 * The color yellow.
	 */
	public static final Color32 YELLOW = new Color32(255, 255, 0);
	
	/**
	 * The color green.
	 */
	public static final Color32 GREEN = new Color32(0, 255, 0);
	
	/**
	 * The color magenta.
	 */
	public static final Color32 MAGENTA = new Color32(255, 0, 255);
	
	/**
	 * The color cyan.
	 */
	public static final Color32 CYAN = new Color32(0, 255, 255);
	
	/**
	 * The color blue.
	 */
	public static final Color32 BLUE = new Color32(0, 0, 255);
	
	private final int value;
	
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
	 * Add two colors together, each component is added separately.
	 * @param color The color to add.
	 * @return A new color, with the result applied.
	 */
	public final Color32 add(Color color)
	{
		return new Color32(
				(int)(getRed() + color.getRed()),
				(int)(getGreen() + color.getGreen()),
				(int)(getBlue() + color.getBlue()),
				(int)(getAlpha() + color.getAlpha()));
	}
	
	/**
	 * Subtract the color from this one, each component is subtracted separately.
	 * @param color The color to subtract.
	 * @return A new color, with the result applied.
	 */
	public final Color32 sub(Color color)
	{
		return new Color32(
				(int)(getRed() - color.getRed()),
				(int)(getGreen() - color.getGreen()),
				(int)(getBlue() - color.getBlue()),
				(int)(getAlpha() - color.getAlpha()));
	}
	
	/**
	 * Multiply two colors together, each component is multiplied separately.
	 * @param color The color to multiply.
	 * @return A new color, with the result applied.
	 */
	public final Color32 mul(Color color)
	{
		return new Color32(
				(int)(getRed() * color.getRed()),
				(int)(getGreen() * color.getGreen()),
				(int)(getBlue() * color.getBlue()),
				(int)(getAlpha() * color.getAlpha()));
	}
	
	/**
	 * Divide the color by this one, each component is divided separately.
	 * @param color The color to divide.
	 * @return A new color, with the result applied.
	 */
	public final Color32 div(Color color)
	{
		return new Color32(
				(int)(getRed() / color.getRed()),
				(int)(getGreen() / color.getGreen()),
				(int)(getBlue() / color.getBlue()),
				(int)(getAlpha() / color.getAlpha()));
	}
	
	/**
	 * Set the red component of the color. Accepted value is in the range [0-255].
	 * @param r The new red component.
	 * @return A new {@link Color32}, with the new red component.
	 */
	public final Color32 setRed(int r)
	{
		return new Color32(r, getGreen(), getBlue(), getAlpha());
	}
	
	/**
	 * Set the green component of the color. Accepted value is in the range [0-255].
	 * @param g The new green component.
	 * @return A new {@link Color32}, with the new green component.
	 */
	public final Color32 setGreen(int g)
	{
		return new Color32(getRed(), g, getBlue(), getAlpha());
	}
	
	/**
	 * Set the blue component of the color. Accepted value is in the range [0-255].
	 * @param b The new blue component.
	 * @return A new {@link Color32}, with the new blue component.
	 */
	public final Color32 setBlue(int b)
	{
		return new Color32(getRed(), getGreen(), b, getAlpha());
	}
	
	/**
	 * Set the alpha component of the color. Accepted value is in the range [0-255].
	 * @param a The new alpha component.
	 * @return A new {@link Color32}, with the new alpha component.
	 */
	public final Color32 setAlpha(int a)
	{
		return new Color32(getRed(), getGreen(), getBlue(), a);
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
