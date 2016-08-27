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

package com.snakybo.torch.graphics.color;

import com.snakybo.torch.util.MathUtils;

import java.io.Serializable;

/**
 * <p>
 * An RGBA color in the range [0-1]
 * </p>
 *
 * <p>
 * The color is immutable, meaning usage of the various setters, or mathematical operations in
 * this class will instead create a new {@link Color} object with the applied changes.
 * This instance of the color will not be changed.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Color implements Serializable
{
	/**
	 * <p>
	 * Completely transparent, no color.
	 * </p>
	 */
	public static final Color CLEAR = new Color(0, 0, 0, 0);
	
	/**
	 * <p>
	 * The color white.
	 * </p>
	 */
	public static final Color WHITE = new Color(1, 1, 1, 1);
	
	/**
	 * <p>
	 * The color light gray.
	 * </p>
	 */
	public static final Color LIGHT_GRAY = new Color(0.75f, 0.75f, 0.75f);
	
	/**
	 * <p>
	 * Alternative spelling for {@link #LIGHT_GRAY}
	 * </p>
	 */
	public static final Color LIGHT_GREY = LIGHT_GRAY;
	
	/**
	 * <p>
	 * The color gray.
	 * </p>
	 */
	public static final Color GRAY = new Color(0.50f, 0.50f, 0.50f);
	
	/**
	 * <p>
	 * Alternative spelling for {@link #GRAY};
	 * </p>
	 */
	public static final Color GREY = GRAY;
	
	/**
	 * <p>
	 * The color dark gray.
	 * </p>
	 */
	public static final Color DARK_GRAY = new Color(0.25f, 0.25f, 0.25f);
	
	/**
	 * <p>
	 * Alternative spelling for {@link #DARK_GRAY}
	 * </p>
	 */
	public static final Color DARK_GREY = DARK_GRAY;
	
	/**
	 * <p>
	 * The color black.
	 * </p>
	 */
	public static final Color BLACK = new Color(0, 0, 0, 1);
	
	/**
	 * <p>
	 * The color red.
	 * </p>
	 */
	public static final Color RED = new Color(1, 0, 0, 1);
	
	/**
	 * <p>
	 * The color pink.
	 * </p>
	 */
	public static final Color PINK = new Color(1, 0.68f, 0.68f, 1);
	
	/**
	 * <p>
	 * The color orange.
	 * </p>
	 */
	public static final Color ORANGE = new Color(1, 0.78f, 0, 1);
	
	/**
	 * <p>
	 * The color yellow.
	 * </p>
	 */
	public static final Color YELLOW = new Color(1, 1, 0, 1);
	
	/**
	 * <p>
	 * The color green.
	 * </p>
	 */
	public static final Color GREEN = new Color(0, 1, 0, 1);
	
	/**
	 * <p>
	 * The color magenta.
	 * </p>
	 */
	public static final Color MAGENTA = new Color(1, 0, 1, 1);
	
	/**
	 * <p>
	 * The color cyan.
	 * </p>
	 */
	public static final Color CYAN = new Color(0, 1, 1, 1);
	
	/**
	 * <p>
	 * The color blue.
	 * </p>
	 */
	public static final Color BLUE = new Color(0, 0, 1, 1);
	
	private final float r;
	private final float g;
	private final float b;
	private final float a;
	
	/**
	 * <p>
	 * Create a new color.
	 * </p>
	 */
	public Color()
	{
		this(0, 0, 0);
	}
	
	/**
	 * <p>
	 * Create a new color, specifying the red, green and blue component.
	 * </p>
	 *
	 * <p>
	 * Values are in the range [0-1].
	 * </p>
	 *
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 */
	public Color(float r, float g, float b)
	{
		this(r, g, b, 1);
	}
	
	/**
	 * <p>
	 * Create a new color, specifying the red, green, blue and alpha component.
	 * </p>
	 *
	 * <p>
	 * Values are in the range [0-1].
	 * </p>
	 *
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 * @param a The alpha component.
	 */
	public Color(float r, float g, float b, float a)
	{
		// Clamp the input values between [0-1]
		this.r = MathUtils.clamp01(r);
		this.g = MathUtils.clamp01(g);
		this.b = MathUtils.clamp01(b);
		this.a = MathUtils.clamp01(a);
	}
	
	/**
	 * <p>
	 * Copy another color.
	 * </p>
	 *
	 * @param other The color to copy.
	 */
	public Color(Color other)
	{
		this(other.r, other.g, other.b, other.a);
	}
	
	@Override
	public final String toString()
	{
		return "r=" + getRed() + ", g=" + getGreen() + ", b=" + getBlue() + ", a=" + getAlpha();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		
		if(o == null || getClass() != o.getClass())
		{
			return false;
		}
		
		Color color = (Color)o;
		
		if(Float.compare(color.r, r) != 0)
		{
			return false;
		}
		
		if(Float.compare(color.g, g) != 0)
		{
			return false;
		}
		
		if(Float.compare(color.b, b) != 0)
		{
			return false;
		}
		
		return Float.compare(color.a, a) == 0;
	}
	
	@Override
	public int hashCode()
	{
		int result = 0;
		
		result = result + r != +0.0f ? Float.floatToIntBits(r) : 0;
		result = 31 * result + (g != +0.0f ? Float.floatToIntBits(g) : 0);
		result = 31 * result + (b != +0.0f ? Float.floatToIntBits(b) : 0);
		result = 31 * result + (a != +0.0f ? Float.floatToIntBits(a) : 0);
		
		return result;
	}
	
	/**
	 * <p>
	 * Add two colors together, each component is added separately.
	 * </p>
	 *
	 * @param color The color to add.
	 * @return A new color, with the result applied.
	 */
	public final Color add(Color color)
	{
		return new Color(
				getRed() + color.getRed(),
				getGreen() + color.getGreen(),
				getBlue() + color.getBlue(),
				getAlpha() + color.getAlpha());
	}
	
	/**
	 * <p>
	 * Subtract the color from this one, each component is subtracted separately.
	 * </p>
	 *
	 * @param color The color to subtract.
	 * @return A new color, with the result applied.
	 */
	public final Color sub(Color color)
	{
		return new Color(
				getRed() - color.getRed(),
				getGreen() - color.getGreen(),
				getBlue() - color.getBlue(),
				getAlpha() - color.getAlpha());
	}
	
	/**
	 * <p>
	 * Multiply two colors together, each component is multiplied separately.
	 * </p>
	 *
	 * @param color The color to multiply.
	 * @return A new color, with the result applied.
	 */
	public final Color mul(Color color)
	{
		return new Color(
				getRed() * color.getRed(),
				getGreen() * color.getGreen(),
				getBlue() * color.getBlue(),
				getAlpha() * color.getAlpha());
	}
	
	/**
	 * <p>
	 * Divide the color by this one, each component is divided separately.
	 * </p>
	 *
	 * @param color The color to divide.
	 * @return A new color, with the result applied.
	 */
	public final Color div(Color color)
	{
		return new Color(
				getRed() / color.getRed(),
				getGreen() / color.getGreen(),
				getBlue() / color.getBlue(),
				getAlpha() / color.getAlpha());
	}
	
	/**
	 * <p>
	 * Set the red component of the color. Accepted values are in the range [0-1].
	 * </p>
	 *
	 * @param r The new red component.
	 * @return A new {@link Color}, with the new red component.
	 */
	public final Color setRed(float r)
	{
		return new Color(r, getGreen(), getBlue(), getAlpha());
	}
	
	/**
	 * <p>
	 * Set the green component of the color. Accepted value is in the range [0-1].
	 * </p>
	 *
	 * @param g The new green component.
	 * @return A new {@link Color}, with the new green component.
	 */
	public final Color setGreen(float g)
	{
		return new Color(getRed(), g, getBlue(), getAlpha());
	}
	
	/**
	 * <p>
	 * Set the blue component of the color. Accepted value is in the range [0-1].
	 * </p>
	 *
	 * @param b The new blue component.
	 * @return A new {@link Color}, with the new blue component.
	 */
	public final Color setBlue(float b)
	{
		return new Color(getRed(), getGreen(), b, getAlpha());
	}
	
	/**
	 * <p>
	 * Set the alpha component of the color. Accepted value is in the range [0-1].
	 * </p>
	 *
	 * @param a The new alpha component.
	 * @return A new {@link Color}, with the new alpha component.
	 */
	public final Color setAlpha(float a)
	{
		return new Color(getRed(), getGreen(), getBlue(), a);
	}
	
	/**
	 * <p>
	 * Get the red component.
	 * </p>
	 *
	 * @return The red component of the color.
	 */
	public final float getRed()
	{
		return r;
	}
	
	/**
	 * <p>
	 * Get the green component.
	 * </p>
	 *
	 * @return The green component of the color.
	 */
	public final float getGreen()
	{
		return g;
	}
	
	/**
	 * <p>
	 * Get the blue component.
	 * </p>
	 *
	 * @return The blue component of the color.
	 */
	public final float getBlue()
	{
		return b;
	}
	
	/**
	 * <p>
	 * Get the alpha component.
	 * </p>
	 *
	 * @return The alpha component of the color.
	 */
	public final float getAlpha()
	{
		return a;
	}
}
