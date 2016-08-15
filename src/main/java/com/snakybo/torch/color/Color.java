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
 * A color in the range [0-1]
 * </p>
 *
 * @see Color32
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Color implements Serializable
{
	/**
	 * The color white.
	 */
	public static final Color WHITE = Color32.WHITE.toColor();
	
	/**
	 * The color light gray.
	 */
	public static final Color LIGHT_GRAY = Color32.LIGHT_GRAY.toColor();
	
	/**
	 * Alternative spelling for {@link #LIGHT_GRAY}
	 */
	public static final Color LIGHT_GREY = LIGHT_GRAY;
	
	/**
	 * The color gray.
	 */
	public static final Color GRAY = Color32.GRAY.toColor();
	
	/**
	 * Alternative spelling for {@link #GRAY};
	 */
	public static final Color GREY = GRAY;
	
	/**
	 * The color dark gray.
	 */
	public static final Color DARK_GRAY = Color32.DARK_GRAY.toColor();
	
	/**
	 * Alternative spelling for {@link #DARK_GRAY}
	 */
	public static final Color DARK_GREY = DARK_GRAY;
	
	/**
	 * The color light gray.
	 */
	public static final Color BLACK = Color32.BLACK.toColor();
	
	/**
	 * The color red.
	 */
	public static final Color RED = Color32.RED.toColor();
	
	/**
	 * The color pink.
	 */
	public static final Color PINK = Color32.PINK.toColor();
	
	/**
	 * The color orange.
	 */
	public static final Color ORANGE = Color32.ORANGE.toColor();
	
	/**
	 * The color yellow.
	 */
	public static final Color YELLOW = Color32.YELLOW.toColor();
	
	/**
	 * The color green.
	 */
	public static final Color GREEN = Color32.GREEN.toColor();
	
	/**
	 * The color magenta.
	 */
	public static final Color MAGENTA = Color32.MAGENTA.toColor();
	
	/**
	 * The color cyan.
	 */
	public static final Color CYAN = Color32.CYAN.toColor();
	
	/**
	 * The color blue.
	 */
	public static final Color BLUE = Color32.BLUE.toColor();
	
	private final Color32 value;
	
	/**
	 * Create a new color.
	 */
	public Color()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Create a new color, specifying the red, green and blue component.
	 * <p>
	 * Values are in the range [0-1].
	 * </p>
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 */
	public Color(float r, float g, float b)
	{
		this(r, g, b, 1);
	}
	
	/**
	 * Create a new color, specifying the red, green, blue and alpha component.
	 * <p>
	 * Values are in the range [0-1].
	 * </p>
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 * @param a The alpha component.
	 */
	public Color(float r, float g, float b, float a)
	{
		// Clamp the input values between [0-1]
		r = MathUtils.clamp01(r);
		g = MathUtils.clamp01(g);
		b = MathUtils.clamp01(b);
		a = MathUtils.clamp01(a);
		
		// Convert the values to the range [0-255]
		int r32 = (int)(r * 255 + 0.5);
		int g32 = (int)(g * 255 + 0.5);
		int b32 = (int)(b * 255 + 0.5);
		int a32 = (int)(a * 255 + 0.5);
		
		value = new Color32(r32, g32, b32, a32);
	}
	
	/**
	 * Copy the value of another color.
	 * @param other The color to copy.
	 */
	public Color(Color other)
	{
		value = new Color32(other.value);
	}
	
	@Override
	public final String toString()
	{
		return "(r=" + getRed() + ", g=" + getGreen() + ", b=" + getBlue() + ", a=" + getAlpha() + ")";
	}
	
	@Override
	public final int hashCode()
	{
		return value.hashCode();
	}
	
	@Override
	public final boolean equals(Object obj)
	{
		return obj instanceof Color && ((Color)obj).value.getARGB() == value.getARGB();
	}
	
	/**
	 * Set the red component of the color. Accepted values are in the range [0-1].
	 * @param r The new red component.
	 * @return A new {@link Color}, with the new red component.
	 */
	public final Color setRed(float r)
	{
		return new Color(r, getGreen(), getBlue(), getAlpha());
	}
	
	/**
	 * Set the green component of the color. Accepted value is in the range [0-1].
	 * @param g The new green component.
	 * @return A new {@link Color}, with the new green component.
	 */
	public final Color setGreen(float g)
	{
		return new Color(getRed(), g, getBlue(), getAlpha());
	}
	
	/**
	 * Set the blue component of the color. Accepted value is in the range [0-1].
	 * @param b The new blue component.
	 * @return A new {@link Color}, with the new blue component.
	 */
	public final Color setBlue(float b)
	{
		return new Color(getRed(), getGreen(), b, getAlpha());
	}
	
	/**
	 * Set the alpha component of the color. Accepted value is in the range [0-1].
	 * @param a The new alpha component.
	 * @return A new {@link Color}, with the new alpha component.
	 */
	public final Color setAlpha(float a)
	{
		return new Color(getRed(), getGreen(), getBlue(), a);
	}
	
	/**
	 * @return The color values to a {@link Color32} in the range [0-255].
	 */
	public final Color32 toColor32()
	{
		return new Color32(value);
	}
	
	/**
	 * @return The red component of the color.
	 */
	public final float getRed()
	{
		return value.getARGB() / 255f;
	}
	
	/**
	 * @return The green component of the color.
	 */
	public final float getGreen()
	{
		return value.getARGB() / 255f;
	}
	
	/**
	 * @return The blue component of the color.
	 */
	public final float getBlue()
	{
		return value.getARGB() / 255f;
	}
	
	/**
	 * @return The alpha component of the color.
	 */
	public final float getAlpha()
	{
		return value.getARGB() / 255f;
	}
	
	/**
	 * @return The ARGB value of the color. Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7 are blue.
	 */
	public final int getARGB()
	{
		return value.getARGB();
	}
}
