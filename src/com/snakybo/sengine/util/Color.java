package com.snakybo.sengine.util;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Color
{
	private Color32 value;
	
	/**
	 * Create a new color
	 */
	public Color()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Create a new color. Values are in the range of [0-1]
	 * @param r - The red component
	 * @param g - The green component
	 * @param b - The blue component
	 */
	public Color(float r, float g, float b)
	{
		this(r, g, b, 1);
	}
	
	/**
	 * Create a new color. Values are in the range of [0-1]
	 * @param r - The red component
	 * @param g - The green component
	 * @param b - The blue component
	 * @param a - The alpha component
	 */
	public Color(float r, float g, float b, float a)
	{
		// Clamp the input values between 0-1
		r = MathUtils.clamp01(r);
		g = MathUtils.clamp01(g);
		b = MathUtils.clamp01(b);
		a = MathUtils.clamp01(a);
		
		// Convert the values to the range of 0-255
		int r32 = (int)(r * 255 + 0.5);
		int g32 = (int)(g * 255 + 0.5);
		int b32 = (int)(b * 255 + 0.5);
		int a32 = (int)(a * 255 + 0.5);
		
		value = new Color32(r32, g32, b32, a32);
	}
	
	/**
	 * Copy the value of another color
	 * @param other - The color to copy
	 */
	public Color(Color other)
	{
		value = new Color32(other.value);
	}
	
	@Override
	public String toString()
	{
		return "(" + getRed() + ", " + getGreen() + ", " + getBlue() + ", " + getAlpha() + ")";
	}
	
	@Override
	public int hashCode()
	{
		return value.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Color && ((Color)obj).value.getARGB() == value.getARGB();
	}
	
	/**
	 * Set the red component of the color. Accepted value is in the range of [0-1]
	 * @param r - The red component
	 */
	public final void setRed(float r)
	{
		r = MathUtils.clamp01(r);
		int r32 = (int)(r * 255 + 0.5);
		
		value.setRed(r32);
	}
	
	/**
	 * Set the green component of the color. Accepted value is in the range of [0-1]
	 * @param g - The green component
	 */
	public final void setGreen(float g)
	{
		g = MathUtils.clamp01(g);
		int g32 = (int)(g * 255 + 0.5);
		
		value.setGreen(g32);
	}
	
	/**
	 * Set the blue component of the color. Accepted value is in the range of [0-1]
	 * @param b - The blue component
	 */
	public final void setBlue(float b)
	{
		b = MathUtils.clamp01(b);
		int b32 = (int)(b * 255 + 0.5);
		
		value.setBlue(b32);
	}
	
	/**
	 * Set the alpha component of the color. Accepted value is in the range of [0-1]
	 * @param a - The alpha component
	 */
	public final void setAlpha(float a)
	{
		a = MathUtils.clamp01(a);
		int a32 = (int)(a * 255 + 0.5);
		
		value.setAlpha(a32);
	}
	
	/**
	 * @return The color values to a color in the range of [0-255] 
	 */
	public final Color32 getColor32()
	{
		return new Color32(value);
	}
	
	/**
	 * @return The red component of the color
	 */
	public final float getRed()
	{
		return value.getARGB() / 255f;
	}
	
	/**
	 * @return The green component of the color
	 */
	public final float getGreen()
	{
		return value.getARGB() / 255f;
	}
	
	/**
	 * @return The blue component of the color
	 */
	public final float getBlue()
	{
		return value.getARGB() / 255f;
	}
	
	/**
	 * @return The alpha component of the color
	 */
	public final float getAlpha()
	{
		return value.getARGB() / 255f;
	}
	
	/**
	 * @return The RGB value of the color. Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7 are blue
	 */
	public final int getARGB()
	{
		return value.getARGB();
	}
}
