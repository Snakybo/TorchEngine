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

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.util.MathUtils;

import java.io.Serializable;

/**
 * <p>
 * A RGBA color in the range [0-1]
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
	 * Completely transparent, no color.
	 */
	public static final Color CLEAR = new Color(0, 0, 0, 0);
	
	/**
	 * The color white.
	 */
	public static final Color WHITE = new Color(1, 1, 1, 1);
	
	/**
	 * The color light gray.
	 */
	public static final Color LIGHT_GRAY = new Color(0.75f, 0.75f, 0.75f);
	
	/**
	 * Alternative spelling for {@link #LIGHT_GRAY}
	 */
	public static final Color LIGHT_GREY = LIGHT_GRAY;
	
	/**
	 * The color gray.
	 */
	public static final Color GRAY = new Color(0.50f, 0.50f, 0.50f);
	
	/**
	 * Alternative spelling for {@link #GRAY};
	 */
	public static final Color GREY = GRAY;
	
	/**
	 * The color dark gray.
	 */
	public static final Color DARK_GRAY = new Color(0.25f, 0.25f, 0.25f);
	
	/**
	 * Alternative spelling for {@link #DARK_GRAY}
	 */
	public static final Color DARK_GREY = DARK_GRAY;
	
	/**
	 * The color light gray.
	 */
	public static final Color BLACK = new Color(0, 0, 0, 1);
	
	/**
	 * The color red.
	 */
	public static final Color RED = new Color(1, 0, 0, 1);
	
	/**
	 * The color pink.
	 */
	public static final Color PINK = new Color(1, 0.68f, 0.68f, 1);
	
	/**
	 * The color orange.
	 */
	public static final Color ORANGE = new Color(1, 0.78f, 0, 1);
	
	/**
	 * The color yellow.
	 */
	public static final Color YELLOW = new Color(1, 1, 0, 1);
	
	/**
	 * The color green.
	 */
	public static final Color GREEN = new Color(0, 1, 0, 1);
	
	/**
	 * The color magenta.
	 */
	public static final Color MAGENTA = new Color(1, 0, 1, 1);
	
	/**
	 * The color cyan.
	 */
	public static final Color CYAN = new Color(0, 1, 1, 1);
	
	/**
	 * The color blue.
	 */
	public static final Color BLUE = new Color(0, 0, 1, 1);
	
	private final float r;
	private final float g;
	private final float b;
	private final float a;
	
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
		this.r = MathUtils.clamp01(r);
		this.g = MathUtils.clamp01(g);
		this.b = MathUtils.clamp01(b);
		this.a = MathUtils.clamp01(a);
	}
	
	/**
	 * Copy the value of another color.
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
	 * Add two colors together, each component is added separately.
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
	 * Subtract the color from this one, each component is subtracted separately.
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
	 * Multiply two colors together, each component is multiplied separately.
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
	 * Divide the color by this one, each component is divided separately.
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
	 * @return The red component of the color.
	 */
	public final float getRed()
	{
		return r;
	}
	
	/**
	 * @return The green component of the color.
	 */
	public final float getGreen()
	{
		return g;
	}
	
	/**
	 * @return The blue component of the color.
	 */
	public final float getBlue()
	{
		return b;
	}
	
	/**
	 * @return The alpha component of the color.
	 */
	public final float getAlpha()
	{
		return a;
	}
}
